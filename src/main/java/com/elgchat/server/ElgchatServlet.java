package com.elgchat.server;

import com.elgchat.server.restrain.HttpServlet;
import com.elgchat.server.http.Request;
import com.elgchat.server.http.Response;
import com.elgchat.server.util.HttpProtocolUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义实现servlet
 * @author jianghai
 */
public class ElgchatServlet extends HttpServlet {

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }

    /**
     * do get 实现
     *
     * @param request  request
     * @param response response
     */
    @Override
    public void doGet(Request request, Response response){
        String content = "<h1>ElgchatServlet GET 请求</h1>";

        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes(StandardCharsets.UTF_8).length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * post实现
     *
     * @param request  request
     * @param response response
     */
    @Override
    public void doPost(Request request, Response response) {

        String content = "<h1>ElgchatServlet POST 请求</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes(StandardCharsets.UTF_8).length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
