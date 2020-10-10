package com.course.generator.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @author zmq
 * @date 2020/10/10 6:05 下午
 */
public class FreemarkerUtil {

    static String ftlPath = "generator/src/main/java/com/course/generator/ftl/";

    static Template template;

    public static void initConfig(String ftlName) throws Exception{
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_30));
        template = configuration.getTemplate(ftlName);
    }

    public static void generator(String fileName) throws Exception{
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        template.process(null,bufferedWriter);
        bufferedWriter.flush();
        fileWriter.close();
    }
}
