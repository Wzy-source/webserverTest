package com.Thread;

import com.Request_Response.ContentType;
import com.Request_Response.Request;
import com.Request_Response.Response;
import com.Service.Service;

import java.io.IOException;
import java.net.Socket;

//用于处理请求的线程
public class ProcessThread implements Runnable{
    private Socket socket;

    public ProcessThread(Socket socket){
        this.socket=socket;
    }


    //处理请求的具体方法
    @Override
    public void run() {
        try {
            //封装请求
            Request request=new Request(socket.getInputStream());

            Response response=new Response(socket.getOutputStream());
            //处理请求
            Service service=new Service(request,response);
            service.processAll();

            response.addHeadContent("Connection","keep-alive");

            //更改响应报文响应体的格式
            // response.setContentType(ContentType.JSON);

            //跨域处理
            response.addHeadContent("Access-Control-Allow-Origin",request.getHeaderParam("Origin"));
            response.addHeadContent("Access-Control-Allow-Methods","GET,POST");
            response.addHeadContent("Access-Control-Allow-Headers","*");
            response.addHeadContent("Access-Control-Allow-Credentials","true");

            //生成响应报文
            response.generateResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
