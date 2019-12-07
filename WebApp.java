package server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @ClassName WebApp
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 11:29]
 * @Version 1.0
 **/

public class WebApp {
    private static WebContext webContext;
        static{
            //SAX解析
            try{
                //1、获取解析工厂
                SAXParserFactory factory=SAXParserFactory.newInstance();
                //2、从解析工厂获取解析器
                SAXParser parser=factory.newSAXParser();
                //3、编写处理器
                Handler handler=new Handler();
                //4、加载文档 Document 注册处理器
                parser.parse(Thread.currentThread().getContextClassLoader().
                        getResourceAsStream("web.xml"),handler);
                //5、解析
                webContext=new WebContext(handler.getEntities(),handler.getMappings());
            }catch (Exception e){
                System.out.println("解析配置文件错误");
            }
        }
    /**
     * 通过url获取配置文件对应的servlet
     * @param url
     * @return
     */
    public static Servlet getServletFromUrl(String url){
        String className=webContext.getClz("/"+url);
        Class clz;
        try {
            clz=Class.forName(className);
            Servlet servlet=(Servlet)clz.newInstance();
            return servlet;
        } catch (Exception e) {
            System.out.println("反射异常！");
        }
        return null;
    }
}
