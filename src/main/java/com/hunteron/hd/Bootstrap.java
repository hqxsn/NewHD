package com.hunteron.hd;

import com.hunteron.hd.api.restful.ApiServlet;
import com.hunteron.hd.api.restful.ListApiServlet;
import com.hunteron.hd.common.EncodingFilter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.GzipFilter;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.util.EnumSet;

public class Bootstrap {

    static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    static void addShutdownHook(final Server server) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("Sleeping 2 seconds and quitting.");

                try {
                    server.stop();
                } catch (Exception e) {
                    logger.warn("Jetty server stop failed", e);
                }

                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    logger.warn("Couldn't sleep", e);
                }
            }
        });
    }

    public static void main(String[] args) {

        QueuedThreadPool threadPool = new QueuedThreadPool(100, 10);

        Server threadServer = new Server(threadPool);

        Server server = new Server(8080);
        server.setServer(threadServer);


        int MAX_POST_SIZE = 1024 * 1024 * 10;

        try {
            HandlerCollection handlers = new HandlerCollection();
            ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
            ServletContextHandler servletContext = new ServletContextHandler(contextHandlerCollection, "/hd", ServletContextHandler.SESSIONS);

            servletContext.setMaxFormContentSize(MAX_POST_SIZE);


            // * relevant servlets
            servletContext.addFilter(EncodingFilter.class, "/hd/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.ERROR));
            servletContext.addFilter(GzipFilter.class, "/hd/api/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE, DispatcherType.FORWARD, DispatcherType.ERROR));

            //Api List
            servletContext.addServlet(new ServletHolder(
                    new ListApiServlet()),
                    "/ls");

            //API entry
            servletContext.addServlet(new ServletHolder(
                    new ApiServlet()),
                    "/api/*");


            //log4j initialization
            RequestLogHandler requestLogHandler = new RequestLogHandler();
            NCSARequestLog requestLog = new NCSARequestLog(
                    "jetty-yyyy_mm_dd.request.log");
            requestLog.setRetainDays(1);
            requestLog.setAppend(true);
            requestLog.setExtended(false);
            requestLog.setLogTimeZone("GMT");
            requestLogHandler.setRequestLog(requestLog);
            handlers.setHandlers(new Handler[]{contextHandlerCollection,
                    requestLogHandler});
            server.setHandler(handlers);


            org.eclipse.jetty.util.log.Log.setLog(new org.eclipse.jetty.util.log.Slf4jLog());


            //let's go
            server.start();
            logger.info("Server is started");
        } catch (Exception e) {
            logger.error("Unable to start server", e);

        }

    }
}
