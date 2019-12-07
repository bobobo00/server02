package User;

import server.Request;
import server.Response;
import server.Servlet;

/**
 * @ClassName LoginServlet
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 12:51]
 * @Version 1.0
 **/

public class LoginServlet implements Servlet {
    @Override
    public void sevice(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">" );
        response.print("<title>");
        response.print("第一个servlet");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来:"+request.getParameterValue("uname"));
        response.print("</body>");
        response.print("</html>");
    }
}
