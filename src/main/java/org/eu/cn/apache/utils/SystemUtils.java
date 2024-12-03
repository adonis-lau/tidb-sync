package org.eu.cn.apache.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author adonis lau
 * @email adonis.liu@pharmeyes.com
 * @date 2024-12-03
 */
@Slf4j
public class SystemUtils extends org.apache.commons.lang3.SystemUtils {

    /**
     * 获取项目根目录
     *
     * @return 项目根目录
     */
    public static String getProjectRootDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * 获取当前项目的类路径
     * <p>
     * 此方法用于获取当前运行线程的上下文类加载器所定义的类路径这对于理解应用程序的结构，
     * 特别是在动态加载资源或解决类加载问题时非常有用
     *
     * @return 当前项目的类路径
     */
    public static String getCurrentClasspath() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String currentClasspath = Objects.requireNonNull(classLoader.getResource("")).getPath();
        // 当前操作系统
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Win")) {
            // 删除path中最前面的/
            currentClasspath = currentClasspath.substring(1, currentClasspath.length() - 1);
        }
        return currentClasspath;
    }

    public static List<String> findEnableJsonFiles(String directoryPath) {
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            return walk
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(string -> string.endsWith(".enable.json"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return List.of();
        }
    }
}
