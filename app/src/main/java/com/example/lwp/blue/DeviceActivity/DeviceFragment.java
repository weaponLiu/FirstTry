package com.example.lwp.blue.DeviceActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Lwp on 2018/4/9.
 */
public class DeviceFragment extends Fragment implements AdapterView.OnItemClickListener{

    BluetoothAdapter bluetoothAdapter;                    //蓝牙适配器
    ArrayList<BluetoothDevice> deviceList =
            new ArrayList<BluetoothDevice>();               //设备容器

    ArrayList<String> deviceNameList =
            new ArrayList<String>();                         //设备名字容器
    ArrayAdapter<String> deviceNameAdapter;                //设备名字适配器
    ListView listView;
    int devicePosition=-1;                                    //设备位置
    BluetoothSocket mSocket;
    BluetoothDevice mDevice;

    BroadcastReceiver mReceiver;
    ProgressDialog progressDialog;

    private static final String TAG = "DeviceFragment";
    boolean connectedFlag = false;
    //蓝牙实时连接线程
    public  RssiThread rssiThread ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: mycout");
        View view = inflater.inflate(R.layout.activity_device, container, false);
        //初始化和搜索已配对设备
        initView(view);             //不传view参数就会抛出空指针异常
        openAndFindBTDevice();
        //搜索设备按钮
        ImageButton button1 = (ImageButton)view.findViewById(R.id.activity_device_find);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 用来接收到设备查找到的广播和扫描完成的广播
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);//查找设备
//                filter.addAction("com.BluetoothDevice.DISCONNECTED");       //报警
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);       //查找完成
                filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);              //连接设备
                filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);          //断开连接

                // 动态注册广播接收器
                // 用来接收扫描到的设备信息
                getActivity().registerReceiver(mReceiver, filter);
                if (!bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.startDiscovery();
                }
                progressDialog.setMessage("搜索中...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        });


        return view;
    }
/*蓝牙：初始化*/
    public void initView(View view) {
        SharedPreferences pref = getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        Set<String> nameset = pref.getStringSet("NameListOnService", null);
        if(nameset!=null){
            Iterator<String> iterator = nameset.iterator();
            while (iterator.hasNext()){
                deviceNameList.add(iterator.next());
            }
        }

        progressDialog = new ProgressDialog(getActivity());
        listView = (ListView) view.findViewById(R.id.activity_device_list);

        deviceNameAdapter = new ArrayAdapter<String>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                deviceNameList);

       listView.setAdapter(deviceNameAdapter);
        listView.setOnItemClickListener(this);
        //    广播接收器接收设备并保存刷新
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // 扫描到新的蓝牙设备
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // 获得蓝牙设备对象
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_RSSI);
                 //   Log.d(TAG, "onReceive: （调试）findOne：" + device.getName() + "--rssi:" + getDistance(rssi) + "米");
                    if(!connectedFlag){
                        //防止设备对象添加重复
                        if (deviceList.contains(device)) {
                            return;
                        }
                        deviceNameList.add(device.getName());
                        deviceList.add(device);
                        deviceNameAdapter.notifyDataSetChanged();
                    }

                }/*else if (action.equals("com.BluetoothDevice.DISCONNECTED")) {
                    if(!rssiThread.isInterrupted()){
                        rssiThread.interrupt();
                        Log.d(TAG, "onReceive:(调试) 蓝牙断开清除线程！！");
                    }
                    else{
                        Log.d(TAG, "onReceive:(调试) 蓝牙断开清除线程（MainActivity.rrsiThread本就为空）！！");
                    }
                  //  getActivity().unregisterReceiver(mReceiver);
                }*/
                else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    // 扫描完成，关闭显示进度条
                    Log.d(TAG, "onReceive: （调试）finish");
                    progressDialog.dismiss();
                } else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
//                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    connectedFlag = true;
                  //  Log.d(TAG, "onReceive: （调试）connected");
                }/*else if(action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
                    rssiThread.interrupt();
                }*/
            }
        };
    }
/*蓝牙：打开设备*/
    //打开蓝牙设备，并先存入设备和显示已配对设备名称
    private void openAndFindBTDevice() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e(TAG, "Your device is not support Bluetooth!");
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
        } else {
            findBTDevice();
        }
    }
    private void findBTDevice() {
        // 用来保存已经配对的蓝牙设备对象
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                // 将已经配对设备信息添加到ListView中
                deviceNameList.add(device.getName());
                deviceList.add(device);
            }
        }

        deviceNameAdapter.notifyDataSetChanged();
    }

/*蓝牙：点击连接*/
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        bluetoothAdapter.cancelDiscovery();//停止扫描
        final int mPosition = position;
        Log.d(TAG, "connected:（调试）连接中 ");
        connected(deviceList.get(mPosition), mPosition);
    }
    // UUID号，表示不同的数据协议
    private final String UUID_STR = "00001101-0000-1000-8000-00805F9B34FB";
    //连接指定蓝牙设备
    private void connected(final BluetoothDevice device, final int position) {

        BluetoothSocket tmp = null;
        mDevice = device;

        try {
            tmp = device.createRfcommSocketToServiceRecord(UUID
                    .fromString(UUID_STR));
            //   Toast.makeText(getApplicationContext(), "开始连接", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d(TAG, "connected:（调试）蓝牙连接第一步失败 ");;
        }

        mSocket = tmp;

        try {
            // 连接远程服务器设备
            mSocket.connect();

            String name = deviceNameList.get(position) + "\t(已连接)";
            devicePosition = position;
            deviceNameList.remove(position);
            deviceNameList.add(position, name);
            deviceNameAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "蓝牙已连接", Toast.LENGTH_SHORT).show();
            //开启新线程定时监测连接状态和距离
            rssiThread = new RssiThread(bluetoothAdapter,mSocket,mDevice,position);
            rssiThread.start();
        } catch (IOException connectException) {
            Log.d(TAG, "connected:（调试）蓝牙连接第二步失败 ");
            try {mSocket.close();} catch (IOException closeException) {}
        }
        //启动服务，保存现场
        Intent intent = new Intent(getActivity(),SaveDeviceNameService.class);
        intent.putStringArrayListExtra("allDeviceNameList",deviceNameList);
        getActivity().startService(intent);
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