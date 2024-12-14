package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

/**
 * ClassName: TestBufferRead
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 9:35
 * Version: v1.0
 */
public class TestBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();
        buffer.get(new byte[4]);
        System.out.println(buffer);
        buffer.rewind();
        System.out.println(buffer);
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark();
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer);
        buffer.reset();
        System.out.println(buffer);

        System.out.println((char) buffer.get(3));
        System.out.println(buffer);
    }
}
