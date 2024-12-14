package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

/**
 * ClassName: TestByteBufferReadWrite
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 9:14
 * Version: v1.0
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte)0x61);
        ByteBufferUtil.debugAll(buffer);
        buffer.put(new byte[]{0x62,0x63,0x64});
        ByteBufferUtil.debugAll(buffer);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);
        System.out.println((char)buffer.get());
        System.out.println(buffer);
        buffer.compact();
        System.out.println(buffer);
    }
}
