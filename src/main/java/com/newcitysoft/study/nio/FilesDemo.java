package com.newcitysoft.study.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * {@link Files}
 * @see <a href="http://tutorials.jenkov.com/java-nio/files.html">http://tutorials.jenkov.com/java-nio/files.html</a>
 * @author lixin.tian@renren-inc.com
 * @date 2018/3/6 17:46
 */
public class FilesDemo {
    public static void exist() {
        Path path = Paths.get("d:/data/logging.properties");
        boolean pathExists =
                Files.exists(path,
                        new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});

        System.out.println(pathExists);
    }

    public static void createDirectory() throws IOException {
        Path path = Paths.get("d:\\data\\test");
        Path newDir = Files.createDirectory(path);
        System.out.println(newDir);
    }

    public static void copy(boolean isForce) throws IOException {
        Path srcPath = Paths.get("d:\\data\\test\\123.txt");
        Path desPath = Paths.get("d:\\data\\test\\456.txt");

        if(isForce){
            Files.copy(srcPath, desPath, StandardCopyOption.REPLACE_EXISTING);
        }else {
            Files.copy(srcPath, desPath);
        }
    }

    public static void move() throws IOException {
        Path srcPath = Paths.get("d:\\data\\test\\123.txt");
        Path desPath = Paths.get("d:\\data\\test\\test\\456.txt");
        Files.move(srcPath, desPath,
                StandardCopyOption.REPLACE_EXISTING);
    }

    public static void delete() throws IOException {
        Path desPath = Paths.get("d:\\data\\test\\test\\456.txt");
        Files.delete(desPath);
    }

    public static void search() {
        Path rootPath = Paths.get("data");
        String fileToFind = File.separator + "README.txt";

        try {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileString = file.toAbsolutePath().toString();
                    //System.out.println("pathString = " + fileString);

                    if(fileString.endsWith(fileToFind)){
                        System.out.println("file found at path: " + file.toAbsolutePath());
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void walkFileTree() throws IOException {
        Path path = Paths.get("");
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visit file: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visit file failed: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) throws IOException {
//        createDirectory();
//        copy(false);
//        move();
        delete();
    }
}
