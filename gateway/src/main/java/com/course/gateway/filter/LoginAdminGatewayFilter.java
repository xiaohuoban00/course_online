package com.course.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zmq
 * @date 2020/11/1 10:57 下午
 */
@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!path.contains("/admin/")) {
            return chain.filter(exchange);
        }
        if (path.contains("/system/admin/user/login")
                || path.contains("/system/admin/user/logout")
                || path.contains("/system/admin/kaptcha")
                || path.contains("/file/admin/oss-simple")
                || path.contains("/file/admin/get-auth")) {
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        Object object = redisTemplate.opsForValue().get(token);
        if (object == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } else {
            redisTemplate.opsForValue().set(token,JSONObject.toJSONString(object),3600,TimeUnit.SECONDS);
            //校验权限
            /*boolean exist = false;
            JSONObject loginUserDto = JSON.parseObject(String.valueOf(object));
            JSONArray requests = loginUserDto.getJSONArray("requests");
            // 遍历所有【权限请求】，判断当前请求的地址是否在【权限请求】里
            for (Object o : requests) {
                String request = (String) o;
                if (path.contains(request)) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }*/

            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
