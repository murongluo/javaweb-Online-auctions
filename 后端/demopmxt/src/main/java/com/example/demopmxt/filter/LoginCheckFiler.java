package com.example.demopmxt.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.demopmxt.pojo.Result;
import com.example.demopmxt.util.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
@CrossOrigin
@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFiler implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse rep= (HttpServletResponse) servletResponse;
        String url =req.getRequestURI().toString();
        if(url.contains("login")){
            log.info("pass");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String jwt=req.getHeader("token");
        if ( !StringUtils.hasLength(jwt)){
            log.info("not token");
            Result error =Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            rep.getWriter().write(notLogin);
            return;
        }
        try {
            JwtUtils.parseJWT(jwt);

        }catch (Exception e){
            e.printStackTrace();
            log.info("not token");
            Result error =Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            rep.getWriter().write(notLogin);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
