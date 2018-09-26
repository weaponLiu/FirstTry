package com.example.lwp.blue.baseThread;

/**
 * Created by WP on 2018/4/20.
 */
public class BaseThread extends Thread {
    public boolean stopFlag;
    public BaseThread() {
        super();
        ThreadCollector.addThread(this);
    }

    @Override
    public void interrupt() {
        stopFlag = true;
        super.interrupt();
        ThreadCollector.removeThread(this);
    }
}
