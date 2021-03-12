package com.elgchat.server.http;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

/**
 * 把请求信息封装为request
 * @author jianghai
 */
@Data
@NoArgsConstructor
public class Request {

    /**
     * 请求方式，get post
     */
    private String method;

    /**
     * url
     */
    private String url;

    /**
     * 请求协议
     */
    private String agreement;

    /**
     * 输入流
     */
    private InputStream inputStream;


    /**
     * 构造器，输入流传入
     * @param inputStream 输入流
     */
    public Request(InputStream inputStream) throws IOException {

        this.inputStream = inputStream;

        int availableLength = 0;
        while (availableLength == 0) {
            //数据流长度
            availableLength = inputStream.available();
        }

        byte[] bytes = new byte[availableLength];
        int read = inputStream.read(bytes);

        System.out.println("=======>>>请求信息\n" + read + ":" + new String(bytes));

        String inputStr = new String(bytes);
        String[] split = inputStr.split("\\n");

        String[] strings = split[0].split(" ");

        this.method = strings[0];
        this.url = strings[1];
        this.agreement = strings[2];

        System.out.println("=======>>>解析request-method：" + method);
        System.out.println("=======>>>解析request-url：" + url);
        System.out.println("=======>>>解析request-agreement：" + agreement);
    }
}
