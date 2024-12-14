package cn.itcast.netty.c1;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ClassName: TestByteBufferString
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 9:43
 * Version: v1.0
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        byte[] bytes = "hello".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(bytes);
        System.out.println(buffer);
        System.out.println(Charset.defaultCharset());
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode("hello");
        System.out.println(byteBuffer);
        ByteBuffer buffer1 = ByteBuffer.wrap("hello".getBytes());
        System.out.println(buffer1);
        String str1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(str1);
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);
        String str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str2);
    }
}
