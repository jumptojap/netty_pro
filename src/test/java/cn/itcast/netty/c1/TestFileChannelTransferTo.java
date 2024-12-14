package cn.itcast.netty.c1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * ClassName: TestFileChannelTransferTo
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 13:15
 * Version: v1.0
 */
public class TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel()) {
            // 效率高，底层会利用操作系统零拷贝进行优化，传输有上限，2g数据
            long size = from.size();
            for(long left = size; left > 0; ){
                left = left - from.transferTo(size - left, left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
