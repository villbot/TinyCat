package com.elgchat.server.http;

import com.elgchat.server.util.HttpProtocolUtil;
import com.elgchat.server.util.StaticResourceUtIl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 封装response对象，需要依赖于outputStream
 * 该对象需要提供核心方法
 *
 * @author jianghai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private OutputStream outputStream;

    /**
     * 核心输入html方法
     *
     * @param path 路径
     */
    public void outputHtml(String path) throws IOException {

        //获取绝对路径
        String absoluteResourcePath = StaticResourceUtIl.getAbsoluteResourcePath(path);

        //获取静态文件
        File file = new File(absoluteResourcePath);

        //验证是否存在
        if (!file.exists() || !file.isFile()) {
            String httpHeader404 = HttpProtocolUtil.getHttpHeader404();
            System.out.println("===============>>> 没有到资源返回信息404");
            output(httpHeader404);
            return;
        }
        StaticResourceUtIl.outputStaticResource(new FileInputStream(file),outputStream);


    }

    /**
     * 输出指定字符串
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
    }
}
