package com.hunteron.hd.api.restful;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by andysong on 14-3-11.
 */
public class ListApiServlet extends HttpServlet {



    @Override
    protected final void doGet(HttpServletRequest req,
                               HttpServletResponse resp) throws ServletException,
            IOException {


        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        Future<String> bodyText = asyncHttpClient.preparePost("http://tool.17mon.cn/ip.php").addParameter("ip", "182.173.100.197").execute(new AsyncCompletionHandler<String>(){

            @Override
            public String onCompleted(Response response) throws Exception{
                // Do something with the Response
                // ...

                return response.getResponseBody();
            }

            @Override
            public void onThrowable(Throwable t){
                // Something wrong happened.
            }
        });;




        //resp.getOutputStream().write("Hello, this is hd api. 欢迎来到HD的API世界。".getBytes());
        try {
            resp.getOutputStream().write(bodyText.get().getBytes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


}
