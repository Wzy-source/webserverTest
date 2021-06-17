package com.Service;

import com.Util.PropertiesUtil;
import com.Request_Response.Request;
import com.Request_Response.Response;
import com.Server;

public class LoginService extends AbstractService {

    public LoginService(Request request, Response response) {
        super(request, response);
    }

    //提供登录服务
    //如果账号不存在，返回 User noExists
    //如果账号存在，但是密码错误，返回错误信息，Password Error
    //密码正确，返回 LoginIn Success
    @Override
    public void service() {
        String name=request.getBodyParam("name");
        String password=request.getBodyParam("password");

        System.out.println(name);
        System.out.println(password);

        String s=PropertiesUtil.get(name);

        if(s!=null){
            System.out.println(s);

            if(password.equals(s)){
                response.addBodyContent("message","LoginIn Success!");
                Server.memory.put(name,password);
            }else {
                response.addBodyContent("message","Password Error!");
            }
        }else{
            response.addBodyContent("message","User no exists!");
        }

    }


}
