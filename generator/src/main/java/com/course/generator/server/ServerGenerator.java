package com.course.generator.server;

import com.course.generator.utils.FreemarkerUtil;

/**
 * @author zmq
 * @date 2020/10/10 6:13 下午
 */
public class ServerGenerator {

    static String toPath = "generator/src/main/java/com/course/generator/test/";

    public static void main(String[] args) throws Exception{
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator(toPath+"Test.java");
    }
}
