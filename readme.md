#项目介绍
我们理解了Apache Tomcat 的实现原理及配置，那么开始实现一个简易版的Tomcat，命名叫TinyCat。

虽然我们实现的是一个简易版的Tomcat，但作为一个web应用服务器，那我们也需要完成基本功能，即可以通过浏览器发送http请求，
可以接受并解析请求，进行处理，处理完成后将结果可以返回给客户端。

那具体的功能有哪些呢？
1. 提供服务，接受请求（TCP/IP，即socket通信）
2. 请求、相应封装相应的request、response对象
3. 客户端请求资源：资源分为静态（html）和动态资源（servlet）
4. 资源返回给客户端浏览器


# 项目结构
* http  http相关包
  * Request  request对象
  * RequestProcessor
  * Response    Response对象
* restrain  约束文件
  * HttpServlet Servlet实现
  * Servlet  接口定义
* util 工具
  * HttpProtocolUtil    http响应头工具类
  * StaticResourceUtIl  静态资源读取类
* BootStrap     启动类
* ElgchatServlet  业务servlet，继承自HttpServlet



# 项目启动
    在BootStrap中使用main方法启动
    启动后访问 
        localhost:8080/   应返回404
        localhost:8080/index.html   应返回resources/index.html 页面
        localhost:8080/tiny   应返回在web.xml中配置的，调用ElgchatServlet 进行调用 do get/do post方法
    