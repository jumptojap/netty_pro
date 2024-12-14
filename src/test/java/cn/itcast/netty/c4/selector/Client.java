package cn.itcast.netty.c4.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

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
        socketChannel.write(Charset.defaultCharset().encode("0123456789abcdef333"));
        socketChannel.write(Charset.defaultCharset().encode("0123456789abcdef333\n"));
        System.out.println("waiting...");
        System.in.read();
    }
}
