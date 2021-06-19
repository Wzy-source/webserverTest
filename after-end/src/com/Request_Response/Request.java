package com.Request_Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Request {
    private InputStream inputStream;

    private String requestURL;
    private String contentType;
    private String requestMethod;
    private Map<String,String>headers;
    private Map<String,String>body;

    Scanner input;

    String[] all;

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        byte[]data=new byte[1024*1024];

        int len=this.inputStream.read(data);

        //获取请求报文的所有内容
        String s=new String(data,0,len);

        System.out.println("Request为");
        System.out.println(s);
        //对请求报文进行划分
        all=s.split("\r\n");

        headers=new HashMap<>();
        body=new HashMap<>();
        init();


    }

    public String getRequestMethod(){
        return requestMethod;
    }

    public String getRequestURL(){
        return requestURL;
    }

    //获取请求头中指定key的value
    public String getHeaderParam(String key){
        return headers.get(key);
    }

    //获取请求体中指定key的value
    public String getBodyParam(String key){
        return body.get(key);
    }


    //初始化Request对象
    public void init(){

            //获取请求行内容
            String params[]=all[0].split(" ");
            requestMethod=params[0];
            requestURL=params[1];


            //获取请求头内容
            int begin=Integer.MAX_VALUE;
            for(int i=1;i<all.length;i+=1){
                String line=all[i];
                if(line.equals("")){
                    begin=i+1;
                    break;
                }
                int index=line.indexOf(":");
                headers.put(line.substring(0,index),line.substring(index+2));
            }


            //初始化contentType
            contentType=getHeaderParam("Content-Type");


            //获取请求体内容
            switch (contentType){
                case ContentType.JSON:
                    for(int i=begin+1;i<all.length-1;i+=1){
                        String line=all[i];
                        int middle=line.indexOf(":");
                        int b=line.indexOf('"');
                        int e=line.lastIndexOf('"');
                        body.put(line.substring(b+1,middle-1),line.substring(middle+2,e));
                    }
                    break;


                case ContentType.X_WWW_form_urlencoded:
                    for(int i=begin;i<all.length;i+=1){
                        String line=all[i];
                        String[]strs=line.split("&");
                        for(String s:strs){
                            int index=s.indexOf("=");
                            body.put(s.substring(0,index),s.substring(index+1));
                        }
                    }
                    break;


                case ContentType.DEFAULT_CONTENT_TYPE:
                    for(int i=begin;i<all.length;i+=1){
                        String line=all[i];
                        int index=line.indexOf(":");
                        body.put(line.substring(0,index),line.substring(index+1));
                    }
                    break;
            }

            System.out.println();

    }
}
