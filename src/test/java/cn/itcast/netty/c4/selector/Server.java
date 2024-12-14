package cn.itcast.netty.c4.selector;

import cn.itcast.netty.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * ClassName: Server
 * Package: cn.itcast.netty.c4
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 15:11
 * Version: v1.0
 */
@Slf4j
public class Server {
    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            char c = (char) source.get(i);
            if(c == '\n'){
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    byte b = source.get();
                    target.put(b);
                }
                System.out.println(target);
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
    public static void main(String[] args) throws IOException {
        //创建selector，管理多个channel
        Selector selector = Selector.open();

        //ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        //把channel注册到selector
        //SelectionKey事件发生时，可以通过它得到是什么事件，哪个channel发生的事件
        SelectionKey serverKey = server.register(selector, 0, null);
        //只关注accept事件
        serverKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key{}",serverKey);

        server.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            //调用selector的select方法，没有事件才阻塞，有事件才恢复运行
            //select在事件未处理时，不会阻塞，事件发生后要么处理要么取消，不能置之不理
            selector.select();
            //处理事件，拿到所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //处理key时，需要从selectedKeys中删除，否则下次处理会有问题
                iterator.remove();
                log.debug("key{}",key);
                //key.cancel();
                //区分事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    //将bytebuffer作为附件关联到selectionKey上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                }else if(key.isReadable()){
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();//获取channel
                        int readLen = channel.read(buffer);
                        if(readLen == -1){
                            key.cancel();
                        }else{
                            //buffer.flip();
                            //ByteBufferUtil.debugAll(buffer);
                            //System.out.println(Charset.defaultCharset().decode(buffer));
                            split(buffer);
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel(); // 客户端断开了，需要将key取消（从selector的key集合中删除）
                    }
                }

            }
        }
    }
}
