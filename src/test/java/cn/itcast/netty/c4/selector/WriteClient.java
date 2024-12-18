package cn.itcast.netty.c4.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ClassName: WriteClient
 * Package: cn.itcast.netty.c4.selector
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 20:47
 * Version: v1.0
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));

        //接收数据
        int count = 0;
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }
    }
}
