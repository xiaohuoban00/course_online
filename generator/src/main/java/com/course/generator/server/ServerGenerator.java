package com.course.generator.server;

import com.course.generator.utils.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zmq
 * @date 2020/10/10 6:13 下午
 */
public class ServerGenerator {

    static String toPath = "server/src/main/java/com/course/server/service/";

    public static void main(String[] args) throws Exception{
        FreemarkerUtil.initConfig("iService.ftl");
        String Domain = "Section";
        String domain = "section";
        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        FreemarkerUtil.generator(toPath+"I"+Domain+"Service.java",map);
    }
}
