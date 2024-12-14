package cn.itcast.netty.c4;

import cn.itcast.netty.c1.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

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
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式,单线程来处理
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //创建了服务器
        ServerSocketChannel server = ServerSocketChannel.open();
        //切换成非阻塞模式
        server.configureBlocking(false);
        //绑定一个监听端口
        server.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        //accept建立与客户端的连接
        while(true){
            //channel用于与客户端通信\
            //log.debug("connecting...");
            SocketChannel channel = server.accept(); //阻塞方法，线程停止运行，由于前面切换成了非阻塞模式，这边会收到影响
            if(channel != null){
                log.debug("connected...{}",channel);
                channel.configureBlocking(false);// 非阻塞模式
                channels.add(channel);
            }
            //接收客户端发送的数据
            for (SocketChannel socketChannel : channels) {
                //log.debug("before read...{}",socketChannel);
                int readLen = socketChannel.read(buffer);//阻塞方法，会受到socketChannel非阻塞模式的影响，没读到数据返回0
                if(readLen > 0){
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                    buffer.clear();
                    log.debug("after read...{}",socketChannel);
                }
            }
        }
    }
}
