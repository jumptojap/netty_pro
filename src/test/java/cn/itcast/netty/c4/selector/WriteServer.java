package cn.itcast.netty.c4.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * ClassName: WriteServer
 * Package: cn.itcast.netty.c4.selector
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 20:37
 * Version: v1.0
 */
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey sscKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        while(true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    //向客户端发送大量数据
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    //实际写入的字节数
                    int writeLen = sc.write(buffer);
                    System.out.println(writeLen);
                    //是否还有剩余内容
                    if(buffer.hasRemaining()){
                        //关注可写事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        //把未写完的数据挂到scKey上
                        scKey.attach(buffer);
                    }
                }else if(key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    //实际写入的字节数
                    int writeLen = sc.write(buffer);
                    System.out.println(writeLen);
                    //清理操作
                    if(!buffer.hasRemaining()){
                        key.attach(null);//需要清除buffer
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);//不需要关注可写事件了
                    }
                }
            }
        }
    }
}
