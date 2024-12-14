package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

/**
 * ClassName: TestByteBufferAllocate
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 9:27
 * Version: v1.0
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        //堆内存，读写效率较低，收到GC影响
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //直接内存，读写效率高(少一次拷贝)受GC影响小
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(16);
        System.out.println(buffer.getClass());
        System.out.println(buffer1.getClass());
    }
}
