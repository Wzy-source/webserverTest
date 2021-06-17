package com.Request_Response;

import com.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class Response {

    //两个常量，空格和换行符
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";

    private OutputStream outputStream;

    //用于将信息写入响应报文
    private BufferedWriter writer;

    //响应报文的ContentType
    private String contentType="text/plain";
    public void setContentType(String ContentType){
        this.contentType=ContentType;
    }

    //Response的状态码
    private int state=200;

    public void setState(int state) {
        this.state = state;
    }

    //用于创建响应头
    private StringBuilder headerBuilder;

    //用于构建响应体
    private StringBuilder bodyBuilder;

    //用于存储头部键值对
    private Map<String, String> headers;

    //用于存取响应体键值对
    private Map<String,String>body;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
        writer=new BufferedWriter(new OutputStreamWriter(outputStream));
        headerBuilder=new StringBuilder();
        bodyBuilder=new StringBuilder();
        headers=new HashMap<>();
        body=new HashMap<>();
    }

    //向响应体中添加键值对
    public void addBodyContent(String key,String value){
        body.put(key,value);
    }

    //向响应体中添加信息
    public void addHeadContent(String key,String value){
       headers.put(key,value);
    }

    //在响应头中添加Location字段
    public void setLocation(String newURL){
        StringBuilder url=new StringBuilder();
        url.append("http://").append(Server.ServerIP).append(newURL);
        this.addHeadContent("Location",url.toString());
    }


    //创建响应行
    private void buildResponseLine(){
        headerBuilder.append("HTTP/1.1").append(BLANK).append(state).append(BLANK);
        switch (state){
            case 200: headerBuilder.append("OK").append(CRLF);
            break;

            case 301:
                headerBuilder.append("Moved Permanently").append(CRLF);
                break;

            case 302:
                headerBuilder.append("Moved Temporarily").append(CRLF);
                break;

            case 304:
                headerBuilder.append("Not Modified").append(CRLF);
                break;

            case 404:
                headerBuilder.append("NOT FOUND").append(CRLF);
                break;

            case 405:
                headerBuilder.append("Not Allowed").append(CRLF);
                break;

            case 500:
                headerBuilder.append("Internal Server Error").append(CRLF);
                break;
        }
    }


    //创建响应头
    private void buildResponseHead(){
        headerBuilder.append("Date: ").append(new Date()).append(CRLF);
        headerBuilder.append("Content-type: ").append(contentType).append(CRLF);
        Iterator<String>iterator=headers.keySet().iterator();
        while(iterator.hasNext()){
            String key=iterator.next();
            String value=headers.get(key);
            headerBuilder.append(key).append(": ").append(value).append(CRLF);
        }

        headerBuilder.append("Content-length:").append(bodyBuilder.length()).append(CRLF);
        if(bodyBuilder.length()!=0){
            headerBuilder.append(CRLF);
        }
    }


    //创建响应体
    private void buildResponseBody(){

        int n=body.keySet().size();
        Iterator<String>iterator=body.keySet().iterator();
        int i=0;
        switch (contentType){

            case ContentType.JSON:
                bodyBuilder.append("{").append(CRLF);
                while(iterator.hasNext()){
                    String key=iterator.next();
                    String value=body.get(key);
                    bodyBuilder.append("    ").append('"').append(key).append('"').append(":").append('"').append(value).append('"');
                    if(i!=n-1){
                        bodyBuilder.append(",");
                    }
                    bodyBuilder.append(CRLF);
                    i+=1;
                }
                bodyBuilder.append("}").append(CRLF);
                break;


            case ContentType.X_WWW_form_urlencoded:
                while(iterator.hasNext()){
                    String key=iterator.next();
                    String value=body.get(key);
                    bodyBuilder.append(key).append("=").append(value);
                    if(i!=n-1){
                        bodyBuilder.append("&");
                    }
                    i+=1;
                }
                break;


            case ContentType.DEFAULT_CONTENT_TYPE:
                while(iterator.hasNext()){
                    String key=iterator.next();
                    String value=body.get(key);
                    bodyBuilder.append('"').append(key).append('"').append(":").append('"').append(value).append('"');

                    i+=1;
                }
                break;
        }
    }



    //对Service提供的生成响应报文的方法
    public void generateResponse() throws IOException {
        //依次生成响应行，响应头，响应体
        buildResponseBody();
        buildResponseLine();
        buildResponseHead();
        //向响应报文写入信息

        System.out.println(headerBuilder.toString()+bodyBuilder.toString());

        writer.append(headerBuilder);
        writer.append(bodyBuilder);
        writer.flush();
    }

}
