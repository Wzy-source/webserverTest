package com.Util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class PropertiesUtil {

    private static final String FILE_NAME="D:\\桌面\\webserverTest\\after-end\\src\\com\\File\\User";
    private static Scanner sc;

    private static PrintWriter writer;

    private static Map<String,String>map;

    static {
        map=new HashMap<>();
        try {
            FileInputStream fileInputStream=new FileInputStream(FILE_NAME);
            FileOutputStream fileOutputStream=new FileOutputStream(FILE_NAME,true);
            sc=new Scanner(fileInputStream);
            writer=new PrintWriter(fileOutputStream);

            while(sc.hasNext()){
                String line=sc.nextLine();
                System.out.println(line);
                int index=line.indexOf(",");
                map.put(line.substring(0,index),line.substring(index+1));
                System.out.println(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PropertiesUtil() {
    }

    public static void put(String key, String value) {
        map.put(key,value);
        writer.write(key+","+value+"\n");
        writer.flush();
    }

    public static String get(String key) {
        return map.get(key);
    }


}
