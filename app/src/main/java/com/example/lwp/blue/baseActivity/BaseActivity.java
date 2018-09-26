package com.example.lwp.blue.baseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.R;

/**
 * Created by WP on 2018/4/12.
 */
public class BaseActivity extends ActionBarActivity {

    private ForceOffLineReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ActivityCollector.addActivity(this);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
            receiver = null;
        }
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        //这里接受的要改为 蓝牙的广播
//        intentFilter.addAction("BluetoothDevice.ACTION_ACL_DISCONNECTED");
//        //测试
//        intentFilter.addAction("BluetoothDevice.ACTION_ACL_CONNECTED");
//        //测试
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        receiver = new ForceOffLineReceiver();
        registerReceiver(receiver, intentFilter);
    }


    class ForceOffLineReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            //清空设备名字文件
            SharedPreferences.Editor editor = getSharedPreferences(
                    "data",MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            SharedPreferences.Editor editor2 = getSharedPreferences(
                    "namedata",MODE_PRIVATE).edit();
            editor2.clear();
            editor2.apply();
            ActivityCollector.finishAll();//
            //启动一个活动窗口，显示断开提示
            Intent intent1 = new Intent(context, AlarmActivity.class);
            context.startActivity(intent1);
            //启动一个服务，记录当前位置信息，这位置信息LocaHistoryActivity需要
            Intent intent2 = new Intent(context,LocaHistoryService.class);
            context.startService(intent2);
        }
    }
}

