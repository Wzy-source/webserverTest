package com;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private static Map<String,String> map=new HashMap<>();
    static {
        map.put("/register","com.Service.RegisterService");
        map.put("/login","com.Service.LoginService");
        map.put("/register_old","old");
        map.put("/login_old","old");
        map.put("/register_other","other");
        map.put("/login_other","other");
    }

    public static String get(String url){
        return map.get(url);
    }
}
