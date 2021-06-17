package com.Thread;

import com.Server;

import java.util.Timer;
import java.util.TimerTask;

//用于清理服务器缓存的线程,每隔10分钟清理一次服务器缓存
public class ClearThread implements Runnable{

    @Override
    public void run() {
        while (true){
            Server.memory.clear();
            try {
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
