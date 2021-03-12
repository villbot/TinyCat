package com.elgchat.server.restrain;

import com.elgchat.server.http.Request;
import com.elgchat.server.http.Response;
import com.elgchat.server.restrain.Servlet;

/**
 * @author jianghai
 */
public abstract class HttpServlet implements Servlet {

    /**
     * do get 实现
     * @param request request
     * @param response request
     * @throws InterruptedException 异常
     */
    public abstract void doGet(Request request, Response response) throws InterruptedException;

    /**
     * post实现
     * @param request request
     * @param response response
     */
    public abstract void doPost(Request request,Response response);

    @Override
    public void service(Request request, Response response) throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        }

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request,response);
        }
    }
}
