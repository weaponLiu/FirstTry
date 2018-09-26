package com.example.lwp.blue.baseThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WP on 2018/4/20.
 */
public class ThreadCollector {
    public static List<Thread> ThreadList = new ArrayList<Thread>();
    public static void addThread(Thread thread){
        ThreadList.add(thread);
    }
    public static void removeThread(Thread Thread){
        ThreadList.remove(Thread);
    }
    public static void clearAll(){
        for (Thread thread:ThreadList){
            if(!thread.isInterrupted()){
                Thread.interrupted();
            }
        }
        ThreadList.clear();
    }
}
