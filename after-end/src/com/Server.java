package com;

import com.Thread.ListenThread;
import com.Thread.ClearThread;
import com.Thread.ProcessThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//服务器
public class Server{
    //表示该服务器是否正在运行
    public static boolean isRunning=true;

    public static final String ServerIP="localhost:8081";

    //服务器缓存
    public static Map<String,String> memory;

    public static void main(String[]args) throws IOException {
        //创建服务器
        ServerSocket server=new ServerSocket(8081);

        //创建缓存
        memory=new HashMap<>();

        //开启缓存清除线程
        new Thread(new ClearThread()).start();

        //开启监听线程
        new Thread(new ListenThread()).start();


        //创建线程池用于执行请求处理任务
        ThreadPoolExecutor executor=new ThreadPoolExecutor
                (10,15,100, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));

        while(true){
            //阻塞等待请求
            System.out.println("等待连接");
            Socket socket=server.accept();
            //创建处理线程，处理请求
            if(socket!=null) {
                //实现长连接
                socket.setKeepAlive(true);
                executor.execute(new ProcessThread(socket));
            }
        }

    }

}


