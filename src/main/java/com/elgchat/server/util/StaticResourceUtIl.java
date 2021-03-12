package com.elgchat.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author jianghai
 */
public class StaticResourceUtIl {

    /**
     * 获取资源的绝对路径
     *
     * @param path path
     * @return 绝对路径
     */
    public static String getAbsoluteResourcePath(String path) {
        String absoluteResourcePath = StaticResourceUtIl.class.getResource("/").getPath();
        return absoluteResourcePath.replaceAll("\\\\", "/") + path;
    }

    /**
     * 读取静态资源，通过输出流进行输出
     *
     * @param inputStream 文件输入流
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int availableLength = 0;
        while (availableLength == 0) {
            //数据流长度
            availableLength = inputStream.available();
        }
        //输出http请求流
        outputStream.write(HttpProtocolUtil.getHttpHeader200(availableLength).getBytes(StandardCharsets.UTF_8));

        //读取内容输出
        //已经读取的内容长度
        long written = 0;

        //计划每次缓冲的长度
        int byteSize = 1024;

        byte[] bytes = new byte[byteSize];

        while (written < availableLength) {

            //剩余未读取大小不足计划长度，那就按照真实长度处理
            if (written + byteSize > availableLength) {
                //剩余的文件长度
                byteSize = (int) (availableLength - written);
                bytes = new byte[byteSize];
            }
            int read1 = inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            written += byteSize;

            System.out.println("=======>>> 找到资源返回信息：\n"+new String(bytes));
        }
    }
}
