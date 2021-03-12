package com.elgchat.server.restrain;

import com.elgchat.server.http.Request;
import com.elgchat.server.http.Response;


/**
 * @author jianghai
 */
public interface Servlet {

    /**
     * 初始化Servlet
     * @throws Exception 异常
     */
    void  init() throws Exception;


    /**
     * 销毁Servlet
     * @throws Exception 异常
     */
    void  destroy() throws Exception;

    /**
     * 具体服务实现
     * @param request  自定义request
     * @param response 自定义response
     * @throws Exception 异常
     */
    void service(Request request, Response response) throws Exception;
}
