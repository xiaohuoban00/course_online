package com.course.generator.server;

import com.course.generator.utils.FreemarkerUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zmq
 * @date 2020/10/10 6:13 下午
 */
public class ServerGenerator {

    static String toServicePath = "server/src/main/java/com/course/server/service/";
    static String toServiceImplPath = "server/src/main/java/com/course/server/service/impl/";
    static String toControllerPath = "system/src/main/java/com/course/system/controller/admin/";
    static String toMapperPath = "server/src/main/java/com/course/server/mapper/";

    public static void main(String[] args) throws Exception{
        String Domain = "Resource";
        String domain = "resource";
        String module = "system";
        Map<String,Object> map = new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("module",module);

        FreemarkerUtil.initConfig("iService.ftl");
        FreemarkerUtil.generator(toServicePath+"I"+Domain+"Service.java",map);

        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServiceImplPath+Domain+"ServiceImpl.java",map);

        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath+Domain+"Controller.java",map);

        FreemarkerUtil.initConfig("mapper.ftl");
        FreemarkerUtil.generator(toMapperPath+Domain+"Mapper.java",map);
    }
}
