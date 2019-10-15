package com.kennatech.coating.appointment.interceptor;

import com.kennatech.coating.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("---- 进入了JWT拦截器 ----");

        // 无论如何都放行。具体能不能操作还是在具体的操作中去判断。
        // 拦截器只是负责把头请求头中包含token的令牌进行一个解析验证。
        String header = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(header)) {
            // 如果有包含有Authorization头信息，就对其进行解析
            // 前后端约定使用 Authorization:"Deer "+ token 这样的请求头发送jwt令牌

            if (header.startsWith("Deer ")) {
                // 截掉"Deer ",得到token
                String token = header.substring(5);

                // 对令牌进行验证
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    String adminId = claims.getId();

                    if (!StringUtils.isEmpty(roles)) {
                        // 将roles存入request域，鉴权时根据这个判断
                        request.setAttribute("roles", roles);
                        request.setAttribute("adminId",adminId);
                    }

                } catch (Exception e) {
                    throw new RuntimeException("令牌错误");
                }
            }
        }
        return true;
    }
}
