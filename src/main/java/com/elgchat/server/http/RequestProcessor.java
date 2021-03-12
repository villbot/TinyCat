package com.elgchat.server.http;

import com.elgchat.server.restrain.HttpServlet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author jianghai
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProcessor extends Thread {

    private Socket socket;

    private Map<String, HttpServlet> servletMap;

    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();
            //封装request对象和response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            if (servletMap.containsKey(request.getUrl())) {
                HttpServlet httpServlet = servletMap.get(request.getUrl());

                httpServlet.service(request, response);
            } else {
                response.outputHtml(request.getUrl());
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
