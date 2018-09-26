package com.example.lwp.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.lwp.blue.DeviceActivity.DeviceFragment;
import com.example.lwp.blue.DeviceActivity.RssiThread;
import com.example.lwp.blue.IntroActivity.IntroFragment;
import com.example.lwp.blue.baseActivity.BaseActivity;
import com.example.lwp.blue.locaActivity.LocaFragment;
import com.example.lwp.blue.myActivity.MyFragment;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        clearFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new DeviceFragment());
        //背景初始化设置
        View myDevic = findViewById(R.id.device);
        myDevic.setBackgroundColor(getResources().getColor(R.color.darkgrey));
        

    }

/*功能：监听下状态栏，显示对应的Fragment*/
    //切换Fragment
    private void replaceFragment(Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout, fragment);
   //     transaction.addToBackStack(null);
        transaction.commit();
    }
    //背景监听并设置背景和切换Fragment
    View temp;int backgroundFlag=0;
    public void changeBackground(View v){
        //解除前面一开始的初始化
        if(backgroundFlag == 0){
            backgroundFlag=1;
            findViewById(R.id.device).setBackgroundColor(getResources().getColor(R.color.c1));}

        //选中改变背景色
        if(temp == null)temp = v;
        if(!(temp == v)){
            temp.setBackgroundColor(getResources().getColor(R.color.c1));
        }
        v.setBackgroundColor(getResources().getColor(R.color.darkgrey));
        temp = v;
        int id = v.getId();
        //触发事件
        setFragment(id);

    }
    public void setFragment(int id)
    {
        switch (id){
            case R.id.device:replaceFragment(new DeviceFragment());break;
            case R.id.loca:replaceFragment(new LocaFragment());break;
            case R.id.my:replaceFragment( new MyFragment());break;
            case R.id.intro:replaceFragment(new IntroFragment());break;
            default:;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void clearFile(){
        Log.d(TAG, "onDestory: hhhh");
        SharedPreferences.Editor editor = getSharedPreferences(
                "data",MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        SharedPreferences.Editor editor2 = getSharedPreferences(
                "namedata",MODE_PRIVATE).edit();
        editor2.clear();
        editor2.apply();
        SharedPreferences.Editor editor3 = getSharedPreferences(
                "locaHistoryData",MODE_PRIVATE).edit();
        editor3.clear();
        editor3.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     //   Log.d(TAG, "onSaveInstanceState: hhhh");
       // 缩起来就执行
    }

    @Override
    protected void onStop() {
        super.onStop();
    //    Log.d(TAG, "onStop: hhhh");
        // 缩起来就执行
    }
}