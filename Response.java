package server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @ClassName Response
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 12:13]
 * @Version 1.0
 **/

public class Response {
    private Socket client;
    private DataOutputStream bw;
    private final String BLANK=" ";
    private final String CRLF="\r\n";
    private int length=0;//正文的字节数
    //正文
    private StringBuilder content=null;
    ////协议头（状态行与请求头 回车）信息
    private StringBuilder headInfo;

    public Response(Socket client) {
        this.client = client;
        try {
            bw = new DataOutputStream(this.client.getOutputStream());
            content=new StringBuilder();
            headInfo=new StringBuilder();
        } catch (IOException e) {
            content=null;
            headInfo=null;
        }
    }
    public Response() { }

    public void print(String con){
        content.append(con);
        length=content.toString().getBytes().length;
    }
    public void println(String con){
        content.append(con).append(CRLF);
        length=content.toString().getBytes().length+CRLF.getBytes().length;
    }

    public void pushToBrowser(int code){
        if(this.content==null){
            code=505;
        }
        if(null!=this.content){
            createHeadInfo(code);
            String info=this.headInfo.toString()+this.content.toString();
            try {
                bw.writeUTF(info);
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
         }
    }
    //构建头信息
    private void createHeadInfo(int code) {
        //1、响应行: HTTP/1.1 200 OK
        //
        this.headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code){
            case 200:this.headInfo.append("OK");break;
            case 404:this.headInfo.append("NOT FOUND");break;
            case 505:this.headInfo.append("SERVER ERROR");break;
        }
        this.headInfo.append(CRLF);
        //2、响应头(最后一行存在空行):
		/*Date:Mon,31Dec209904:25:57GMT
		Server:shsxt Server/0.0.1;charset=GBK
		Content-type:text/html
		Content-length:39725426*/
		this.headInfo.append("Date:").append(new Date()).append(CRLF);
		this.headInfo.append("Server:").append("SimpleServer/1.0;charSet='utf-8'").append(CRLF);
		this.headInfo.append("Content-type:text/html").append(CRLF);
		this.headInfo.append("Content-length:").append(length).append(CRLF);
    }

}
