package com.example.demopmxt;


import com.example.demopmxt.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.UUID;

@SpringBootTest
class DemopmxtApplicationTests {

    @Test
   public void contextLoads() {

        // 指定要重命名的图片所在的文件夹路径
        String folderPath = "C:\\Users\\25194\\Desktop\\img";

        // 创建 File 对象
        File folder = new File(folderPath);
         int i =1;
        // 获取文件夹内的所有文件和目录
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                // 检查是否是文件以及是否是图片文件
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")
                        || file.getName().endsWith(".png") || file.getName().endsWith(".gif"))) {
                    // 生成新的文件名
                    String newFileName = i++  + ".jpg";
                    // 创建新的 File 对象
                    File newFile = new File(folderPath + File.separator + newFileName);

                    // 重命名文件
                    boolean isRenamed = file.renameTo(newFile);

                    if (isRenamed) {
                        System.out.println("文件 " + file.getName() + " 已重命名为 " + newFileName);
                    } else {
                        System.out.println("文件 " + file.getName() + " 重命名失败");
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在或无法访问");
        }

    }

}
