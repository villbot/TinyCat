package com.elgchat.server;

import com.elgchat.server.restrain.HttpServlet;
import com.elgchat.server.http.RequestProcessor;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * TinyCat主类
 *
 * @author jianghai
 */
public class BootStrap {

    /**
     * TinyCat程序的启动入口
     *
     * @param args args
     */
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            //启动TinyCat
            bootStrap.start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 定义 url ————> Class  全局
     */
    private final Map<String, HttpServlet> servletMap = new HashMap<>();

    /**
     * tinyCat 启动需要初始化展开的操作
     */
    public void start(int port) throws Exception {
        //加载解析web.xml 初始化Servlet
        loadInitServlet();

        //定义线程池
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
        //定义socket
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("======>>> TinyCat start on port：" + port);

        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
        }

    }


    /**
     * 加载解析web.xml 初始化Servlet
     */
    private void loadInitServlet() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document read = saxReader.read(resourceAsStream);
            Element rootElement = read.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");

            if (selectNodes.size() > 0) {
                for (Element element : selectNodes) {
                    Node servletNameElement = element.selectSingleNode("//servlet-name");
                    String servletName = servletNameElement.getStringValue();

                    Node servletClassElement = element.selectSingleNode("//servlet-class");
                    String servletClass = servletClassElement.getStringValue();

                    //根据servlet-name的值找到url
                    Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                    String urlPattern = servletMapping.selectSingleNode("//url-pattern").getStringValue();
                    servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
