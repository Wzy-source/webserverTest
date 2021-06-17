package com.Service;

import com.*;
import com.Request_Response.Request;
import com.Request_Response.Response;

import java.lang.reflect.InvocationTargetException;

public class Service {
    private Request request;
    private Response response;


    public Service(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    //核心方法，用于处理请求
    public void processAll(){
        if(process500()){
            if(process405()){
                if(process304()){
                    //根据请求的URL，获取对应的映射
                    String mapper= Mapper.get(request.getRequestURL());
                    if(mapper==null){
                        //如果mapper为空，说明请求的资源在服务器上不存在，返回404
                        process404();
                    }else if(mapper.equals("other")){
                        //触发状态码302
                        process302();
                    }else if(mapper.equals("old")){
                        //触发状态码301
                        process301();
                    }else{
                        //请求的资源在服务器上存在，并且URL正确，触发状态码200
                        process200(mapper);
                    }
                }
            }
        }
    }


    private void process200(String className){
        response.setState(200);
        try {
            AbstractService service=(AbstractService) Class.forName(className).getConstructors()[0].newInstance(request,response);
            service.service();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void process302(){
        response.setState(302);
        response.setLocation(request.getRequestURL().substring(0,request.getRequestURL().indexOf("_")));

        //添加的跨域处理
        response.addBodyContent("Access-Control-Allow-Origin","http://"+Server.ServerIP+request.getRequestURL().substring(0,request.getRequestURL().indexOf("_")));
        response.addBodyContent("Access-Control-Allow-Credentials","true");
        response.addBodyContent("Access-Control-Allow-Headers","Content-Type,Location");


        response.addBodyContent("message","URL Moved Temporarily");
    }

    private void process301(){
        response.setState(301);
        response.setLocation(request.getRequestURL().substring(0,request.getRequestURL().indexOf("_")));

        //添加的跨域处理
        response.addBodyContent("Access-Control-Allow-Origin","http://"+Server.ServerIP+request.getRequestURL().substring(0,request.getRequestURL().indexOf("_")));
        response.addBodyContent("Access-Control-Allow-Credentials","true");
        response.addBodyContent("Access-Control-Allow-Headers","Content-Type,Location");


        response.addBodyContent("message","URL Moved Permanently");
    }

    private void process404(){
        response.setState(404);
        response.addBodyContent("message","Resource No Exists");
    }

    private boolean process304(){
        //如果请求方法为GET，并且请求的URL中包含了Login，并且请求报文的信息，存在于服务器的缓存中,设置状态码为304，返回false
        if(request.getRequestMethod().equals("GET")
                &&request.getRequestURL().contains("login")
                &&request.getBodyParam("password").equals(Server.memory.get(request.getBodyParam("name")))
                ){
            response.setState(304);
            response.addBodyContent("message","Recently Used");
            return false;
        }
        return true;
    }




    private boolean process500(){
        //如果服务器正在运行返回true
        if(Server.isRunning){
            return true;
        }
        //否则状态码设置为500，返回false
        response.setState(500);
        response.addBodyContent("message","Server Closed");
        return false;
    }


    //处理405
    private boolean process405(){
        //如果请求方法为GET或者POST，返回true
        if("GET".equals(request.getRequestMethod())||"POST".equals(request.getRequestMethod())){
            return true;
        }
        //否则状态码设置为405，返回false
        response.setState(405);
        response.addBodyContent("message","Method not Allowed");
        return false;
    }
}
