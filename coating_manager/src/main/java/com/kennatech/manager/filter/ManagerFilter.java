package com.kennatech.manager.filter;

import com.kennatech.coating.utils.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求前pre或者后post执行
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序，数字越小，表示越先执行
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启true表示开启
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return 任何对象的值，即使是null，都表示继续执行
     * requestContext.setSendZuulResponse(false)表示不再继续执行
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了后台过滤器！");
        RequestContext requestContext = RequestContext.getCurrentContext();

        // request域
        HttpServletRequest request = requestContext.getRequest();

        // 对zuul路径转发的请求发行
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        // 如果url带有list（所有微服务的查询所有），就放行
        if (request.getRequestURI().indexOf("list") > 0) {
            return null;
        }

        // 如果url带有item（所有微服务的findById），就放行
        if (request.getRequestURI().indexOf("item") > 0) {
            return null;
        }


        // 如果是登录，就放行
        if (request.getRequestURI().indexOf("login") > 0) {
            return null;
        }


        // 得到头信息
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)) {
            if (header.startsWith("Deer ")) {
                String token = header.substring(5);
                try {
                    //  检验jwt令牌
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles.equals("super") || roles.equals("general")) {
                        // 把头信息转发下去，并且放行
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                } catch (Exception e) { // 令牌检验发生异常，说明没通过校验
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);// 终止运行
                }
            }
        }

        // 这里我们通过setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，
        // 然后通过 setResponseStatusCode(403) 设置了其返回的错误码
        requestContext.setSendZuulResponse(false);// 终止运行
        requestContext.setResponseStatusCode(403);

        String result = "{ \"flag\": false,\n" +
                "    \"code\": 20001,\n" +
                "    \"message\": \"權限不足\"}";

//        requestContext.setResponseBody("權限不足");
//        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        requestContext.setResponseBody(result);

        requestContext.getResponse().setContentType("text/json;charset=utf-8");

        return null;
    }
}

