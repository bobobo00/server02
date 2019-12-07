package User;

import server.Request;
import server.Response;
import server.Servlet;

/**
 * @ClassName RegistServlet
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 12:51]
 * @Version 1.0
 **/

public class RegistServlet implements Servlet {
    @Override
    public void sevice(Request request, Response response) {
        //关注业务逻辑
        String uname = request.getParameterValue("uname");
        String[] favs =request.getParameter("fav");
        response.print("<html>");
        response.print("<head>");
        response.print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">" );
        response.print("<title>");
        response.print("注册成功");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.println("你注册的信息为:"+uname);
        response.println("你选择的聊天背景为:");
        for(String v:favs) {
            if(v.equals("0")) {
                response.print("简约型");
            }else if(v.equals("1")) {
                response.print("清新型");
            }else if(v.equals("2")) {
                response.print("萝莉型");
            }
        }
        response.print("</body>");
        response.print("</html>");
    }
}
