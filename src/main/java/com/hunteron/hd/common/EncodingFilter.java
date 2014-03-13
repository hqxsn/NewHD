package com.hunteron.hd.common;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by andysong on 14-3-12.
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        preProcess(req, rep);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    void preProcess(HttpServletRequest req, HttpServletResponse rep) throws UnsupportedEncodingException {
        String charset = req.getCharacterEncoding();
        if (charset == null) {
            // if no charset specified in
            // Content-Type: Application/Json,
            // defaults to utf8
            req.setCharacterEncoding("UTF-8");
            rep.setCharacterEncoding("UTF-8");
        }


    }


}
