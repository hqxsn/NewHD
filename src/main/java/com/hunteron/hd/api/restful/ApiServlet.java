package com.hunteron.hd.api.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by andysong on 14-3-12.
 */
public class ApiServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected final void doGet(HttpServletRequest req,
                               HttpServletResponse resp) throws ServletException,
            IOException {


        String handlerPath = req.getServletPath();
        String restModelPath = req.getPathInfo();



    }

    @Override
    protected final void doPost(HttpServletRequest req,
                                HttpServletResponse resp) throws ServletException,
            IOException {

        String handlerPath = req.getServletPath();
        String restModelPath = req.getPathInfo();


    }


    @Override
    protected final void doPut(HttpServletRequest req,
                               HttpServletResponse resp) throws ServletException,
            IOException {
    }



    @Override
    protected final void doDelete(HttpServletRequest req,
                                  HttpServletResponse resp) throws ServletException,
            IOException {

    }

    @Override
    protected void doTrace(HttpServletRequest req,
                           HttpServletResponse resp) throws ServletException,
            IOException {
    }

}
