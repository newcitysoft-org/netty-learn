package com.newcitysoft.study.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 17:28
 */
public class PathDemo {
    public static void main(String[] args) {
        // Creating an Absolute Path
//        Path path = Paths.get("d:\\data\\123.txt");
//        // Creating a Relative Path
//        Path projects = Paths.get("d:\\data", "projects");
//        Path file     = Paths.get("d:\\data", "projects\\a-project\\myfile.txt");
        Path currentDir = Paths.get(".");
        System.out.println(currentDir.toAbsolutePath());
        Path parentDir = Paths.get("..");
        System.out.println(parentDir.toAbsolutePath());
        Path currentDir2 = Paths.get("d:\\data\\projects\\.\\a-project");
        System.out.println(currentDir2);
        String path = "d:\\data\\projects\\a-project\\..\\another-project";
        Path parentDir2 = Paths.get(path);
        System.out.println(parentDir2);

        String originalPath =
                "d:\\data\\projects\\a-project\\..\\another-project";

        Path path1 = Paths.get(originalPath);
        System.out.println("path1 = " + path1);

        Path path2 = path1.normalize();
        System.out.println("path2 = " + path2);
    }
}
