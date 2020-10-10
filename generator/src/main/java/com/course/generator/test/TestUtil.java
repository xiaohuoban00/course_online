package com.course.generator.test;

import com.course.generator.utils.FreemarkerUtil;
import freemarker.template.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author zmq
 * @date 2020/10/10 5:30 下午
 */
public class TestUtil {
    static String ftlPath = "generator/src/main/java/com/course/generator/test/";
    static String toPath = "generator/src/main/java/com/course/generator/test/";

    public static void main(String[] args) throws Exception{
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        System.out.println(ftlPath);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_30));
        Template template = configuration.getTemplate("test.ftl");

        System.out.println(toPath);

        FileWriter fileWriter = new FileWriter(toPath + "Test.java");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        template.process(null,bufferedWriter);
        bufferedWriter.flush();
        fileWriter.close();
    }
}
