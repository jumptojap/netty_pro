package cn.itcast.netty.c4.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * ClassName: TestClient
 * Package: cn.itcast.netty.c4.test
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/15 - 18:30
 * Version: v1.0
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("1234567890abcde"));
        System.in.read();

    }
}
