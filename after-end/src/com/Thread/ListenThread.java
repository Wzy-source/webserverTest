package com.Thread;

import com.Server;

import java.util.Scanner;

//监听线程，用于监听控制台
public class ListenThread implements Runnable {
    //用于从控制台获取输入
    Scanner sc;

    public ListenThread(){
        sc=new Scanner(System.in);
    }


    //不断监听控制台输入，一旦控制台输入EXIT，那么将关闭服务器
    @Override
    public void run() {
        while(true){
            while (sc.hasNext()){
                if("EXIT".equals(sc.nextLine())){
                    Server.isRunning=false;
                }
            }
        }
    }
}
