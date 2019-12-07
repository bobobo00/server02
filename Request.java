package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;


/**
 * @ClassName Request
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/7 11:42]
 * @Version 1.0
 **/

public class Request {
    //协议信息
    private String requestInfo;
    //请求方式
    private String method;
    //请求url
    private String url;
    //请求参数
    private String queryInfo;
    //常量
    private final String CRLF="\r\n";
    //存储参数
    private Map<String,List<String>> parameter=null;

    public Request(InputStream is){
        parameter=new HashMap<>();
        byte[] datas=new byte[1024*1024];
        int len=-1;
        try {
            len=is.read(datas);
            requestInfo=new String(datas,0,len);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestInfo();
    }


    public Request(Socket socket) throws IOException {
        this(socket.getInputStream());
    }
    //分解请求消息
    private void parseRequestInfo()  {
        this.method=this.requestInfo.substring(0,this.requestInfo.indexOf("/")).trim();
       System.out.println("method:"+this.method);
        this.url=this.requestInfo.substring(this.requestInfo.indexOf("/")+1,
                this.requestInfo.indexOf("HTTP/")).trim();
        if(this.url.indexOf("?")!=-1){
            String[] datas=this.url.split("\\?");
            this.url=datas[0];
            this.queryInfo=datas[1];
        }
       System.out.println("url:"+this.url);
        if(this.method.equals("POST")){
            String str=this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            if(null==this.queryInfo){
                this.queryInfo=str;
            }else{
                this.queryInfo+="&"+str;
            }
        }
        if(null==this.queryInfo){
            this.queryInfo="";
        }
       System.out.println("queryInfo:"+this.queryInfo);
        this.queryInfo=decode(this.queryInfo,"Unicode");
        createParameterMap();
    }
    //处理请求参数为Map
    private void createParameterMap() {
        //1、分割字符串 &
        String[] parameters=this.queryInfo.split("&");
        for(String str:parameters){
            //2、再次分割字符串  =
            String[] kv=str.split("=");
            kv=Arrays.copyOf(kv,2);
            ////获取key和value
            String key=kv[0];
            String value=kv[1]==null?null:decode(kv[1],"Unicode");
            if(!this.parameter.containsKey(key)){
                this.parameter.put(key,new ArrayList<>());
            }
            this.parameter.get(key).add(value);
        }

    }
    /**
     * 处理中文
     * @return
     */
    private String decode(String info,String charset){
        try {
            return java.net.URLDecoder.decode(info,charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 通过name获取对应的多个值
     * @param key
     * @return
     */
    public String[] getParameter(String key){
       List<String> values=this.parameter.get(key);
       if(null==values||values.size()<1){
           return null;
       }
       return values.toArray(new String[0]);
    }
    /**
     * 通过name获取对应的一个值
     * @param key
     * @return
     */
    public String getParameterValue(String key){
        List<String> values=this.parameter.get(key);
        if(null==values||values.size()<1){
            return null;
        }
        return values.get(0);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryInfo() {
        return queryInfo;
    }
}
