package cn.itcast.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * ClassName: Client
 * Package: cn.itcast.netty.c4
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 15:21
 * Version: v1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        System.out.println("waiting...");
    }
}
