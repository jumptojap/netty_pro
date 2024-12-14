package cn.itcast.netty.c1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ClassName: TestFilesCopy
 * Package: cn.itcast.netty.c1
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/12/14 - 14:59
 * Version: v1.0
 */
public class TestFilesCopy {
    public static void main(String[] args) throws IOException {
        String source = "C:\\Users\\wjsww\\Desktop\\insgram";
        String target = "C:\\Users\\wjsww\\Desktop\\insgram_copy";
        Files.walk(Paths.get(source)).forEach((path) -> {
            try {
                String targetName = path.toString().replace(source, target);
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                } else if (Files.isRegularFile(path)){
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
