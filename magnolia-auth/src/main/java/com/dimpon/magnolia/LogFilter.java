package com.dimpon.magnolia;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
@WebFilter(filterName = "LogFilter",
        urlPatterns = {"/"},
        initParams = {
                @WebInitParam(name = "mood", value = "awake")})
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request1 = (HttpServletRequest) request;

        log.info("*** doFilter " + request1.getRequestURL() + " " + Thread.currentThread());

        String collect = request1.getParameterMap().entrySet().stream()
                .map(e -> "[" + e.getKey() + "=" + Arrays.stream(e.getValue()).collect(Collectors.joining("|")) + "]")
                .collect(Collectors.joining(","));
        log.info("*** doFilter " + collect + " " + Thread.currentThread());


       StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request1.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String s =  headerNames.nextElement();
            sb.append(s);
            sb.append("-");
            sb.append(request1.getHeader(s));
            sb.append(", ");
        }
        log.info("*** doFilter " + sb + " " + Thread.currentThread());

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
