package com.example.lwp.blue.DeviceActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.baseThread.BaseThread;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by WP on 2018/4/20.
 */
//实时判断蓝牙连接线程
public class RssiThread extends BaseThread {
    private static final String TAG = "RssiThread";
    BluetoothSocket socket;
    BluetoothDevice device;
    BluetoothAdapter bluetoothAdapter;
    int position;
    // UUID号，表示不同的数据协议
    private final String UUID_STR = "00001101-0000-1000-8000-00805F9B34FB";
    public RssiThread(BluetoothAdapter bluetoothAdapter,BluetoothSocket socket, BluetoothDevice device, int position) {
        super();
        this.bluetoothAdapter = bluetoothAdapter;
        this.socket = socket;
        this.device = device;
        this.position = position;
        stopFlag = false;
    }


    @Override
    public void run() {
        try {
            Log.d(TAG, "connected:（调试）蓝牙连接中 ");sleep(5000);} catch (InterruptedException e) {// TODO Auto-generated catch block
            e.printStackTrace();}
        try {
            socket.close();
            Log.d(TAG, "connected:（调试）断开搜索中 ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!stopFlag){
            bluetoothAdapter.startDiscovery();
            try {sleep(5000);} catch (InterruptedException e) {// TODO Auto-generated catch block
                e.printStackTrace();}
            bluetoothAdapter.cancelDiscovery();
            BluetoothDevice mDevice;
            BluetoothSocket tmp = null;
            mDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(UUID
                        .fromString(UUID_STR));
                //   Toast.makeText(getApplicationContext(), "开始连接", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Log.d(TAG, "connected:（调试）蓝牙连接第一步失败 ");;
            }

            socket = tmp;

            try {
                // 连接远程服务器设备
                socket.connect();
            } catch (IOException connectException) {
                Log.d(TAG, "connected:（调试）蓝牙连接第二步失败 ");
                try {socket.close();} catch (IOException closeException) {}
            }
            try {Log.d(TAG, "connected:（调试）蓝牙连接中 ");sleep(5000);} catch (InterruptedException e) {// TODO Auto-generated catch block
                e.printStackTrace();}
            try {
                socket.close();
                Log.d(TAG, "connected:（调试）断开搜索中 ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
};