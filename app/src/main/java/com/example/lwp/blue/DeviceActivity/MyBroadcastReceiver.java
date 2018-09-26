package com.example.lwp.blue.DeviceActivity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.baseThread.ThreadCollector;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public MyBroadcastReceiver() {
    }
    private static final String TAG = "DeviceFragment";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

                SharedPreferences pref = context.getSharedPreferences("namedata", Context.MODE_PRIVATE);
                String device = pref.getString("device",null);
                boolean firstConnected = true;
                String action = intent.getAction();
                // 扫描到新的蓝牙设备
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // 获得蓝牙设备对象
                    BluetoothDevice onedevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                    double distance =getDistance(rssi);
                    if ((device !=null)&&(onedevice.getName().equals(device))){
                        Log.d(TAG, "onReceive: （调试）主广播接收器 found，rssi:"+rssi + onedevice.getName()+distance);
                        if(distance > 5){
                            Log.d(TAG, "onReceive: （调试）主广播接收器 距离超标断开啦！！");
                            ThreadCollector.ThreadList.get(0).interrupt();

                            ThreadCollector.clearAll();
                            Intent intent3 = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                            context.sendBroadcast(intent3);
//                            Intent intent2 = new Intent("com.BluetoothDevice.DISCONNECTED");
//                            context.sendBroadcast(intent2);
                        }
                    }
                }
                else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                    BluetoothDevice mdevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(device == null){
                        //蓝牙断开服务
                        Intent Broadcaseintent = new Intent(context,SaveConnectedNameService.class);
                        Broadcaseintent.putExtra("device", mdevice.getName());
                        context.startService(Broadcaseintent);
                    }
                    Log.d(TAG, "onReceive: （调试）主广播接收器connected"+mdevice.getName());
                }
            }
    /**
     * 根据Rssi获得返回的距离,返回数据单位为m
     * @param rssi
     * @return
     */
    //A和n的值，需要根据实际环境进行检测得出
    private static final double A_Value=60;//**A - 发射端和接收端相隔1米时的信号强度*//*
    private static final double n_Value=2.9;//** n - 环境衰减因子*//*
    public static double getDistance(int rssi){
        int iRssi = Math.abs(rssi);
        double power = (iRssi-A_Value)/(10*n_Value);
        return Math.pow(10,power);
    }
}
