package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

/**
 * ClassName: TestByteBufferExam
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 10:10
 * Version: v1.0
 */
public class TestByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

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
}
