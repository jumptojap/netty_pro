package cn.itcast.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: test
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/13 - 15:56
 * Version: v1.0
 */
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true){
                //从channel读取数据，向buffer写入
                int len = channel.read(buffer);
                log.debug("读到的字节数 {}", len);
                // 没有内容
                if(len == -1)
                    break;
                //打印buffer
                buffer.flip(); // 切换到读模式f
                while(buffer.hasRemaining()){ // 是否还有剩余数据
                    byte b = buffer.get();
                    log.debug("实际字节 {}", (char)b);
                }
                // 切换为写模式
                buffer.clear();
            }

        } catch (IOException e) {
        }
    }
}
