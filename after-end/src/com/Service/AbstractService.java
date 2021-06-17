package com.Service;

import com.Mapper;
import com.Request_Response.Request;
import com.Request_Response.Response;

public abstract class AbstractService {
    Response response;
    Request request;

    public AbstractService(Request request,Response response){
        this.request=request;
        this.response=response;
    }

    public abstract void service();

    public static void main(String[]args) throws ClassNotFoundException {
        Class clazz=Class.forName(Mapper.get("/login"));
    }
}
