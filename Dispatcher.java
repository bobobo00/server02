package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @ClassName Dispatcher
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 12:39]
 * @Version 1.0
 **/

public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;
    public Dispatcher(Socket client) {
        this.client = client;
        try {
            this.request=new Request(client);
            this.response=new Response(client);
        } catch (IOException e) {
           release();
        }
    }
    @Override
    public void run() {
        if(null==request.getUrl()){
            InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
            byte[] datas=new byte[1024*1024];
            try {
                int len=is.read(datas);
                response.println(new String(datas,0,len));
                response.pushToBrowser(200);
                release();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
            if (servlet != null) {
                servlet.sevice(request,response);
                response.pushToBrowser(200);
            } else {
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                byte[] data = new byte[1024 * 1024];
                try {
                    int len = is.read(data);
                    response.println(new String(data, 0, len));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.pushToBrowser(404);
            }
        }catch(Exception e){
        response.pushToBrowser(505);
    }
    release();
}
    //释放资源
    private void release() {
        try {
            client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
