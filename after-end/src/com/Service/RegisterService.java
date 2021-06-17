package com.Service;

import com.Util.PropertiesUtil;
import com.Request_Response.Request;
import com.Request_Response.Response;

public class RegisterService extends AbstractService {

    public RegisterService(Request request, Response response) {
        super(request, response);
    }

    //如果
    @Override
    public void service() {
        String name=request.getBodyParam("name");
        String password=request.getBodyParam("password");
        if(PropertiesUtil.get(name)!=null){
            response.addBodyContent("message","The user has been existed");
        }else{
            PropertiesUtil.put(name,password);
            response.addBodyContent("message","Register Success!");
        }
    }

}
