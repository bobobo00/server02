package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName Server
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 12:33]
 * @Version 1.0
 **/

public class Server {
    private ServerSocket server;
    private boolean isRunning;
    public static void main(String[] args) {
        Server server=new Server();
        server.start();
    }
    //启动服务
   public  void start() {
       try {
           server=new ServerSocket(9996);
           System.out.println("启动服务");
           this.isRunning=true;
           receive();
       } catch (IOException e) {
           System.out.println("505");
           stop();
       }
   }
    //接受连接处理
    public void receive(){
        while(isRunning){
            try {
                Socket client=server.accept();
                System.out.println("连接成功！");
                new Thread(new Dispatcher(client)).start();
            } catch (Exception e) {
                System.out.println("NOT FOUND!");
            }
        }
    }
   public void stop(){
        isRunning=false;
       try {
           server.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
