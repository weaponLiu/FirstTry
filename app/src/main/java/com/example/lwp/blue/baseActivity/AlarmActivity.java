package com.example.lwp.blue.baseActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.R;

/**
 * Created by WP on 2018/4/13.
 */
public class AlarmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //装载音乐
        final MediaPlayer mediaPlayer = MediaPlayer.create(AlarmActivity.this, R.raw.today);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        //弹出框
        AlertDialog.Builder builder = new AlertDialog.Builder(AlarmActivity.this);
        AlertDialog dialog=builder.setTitle("警告")
                .setMessage("蓝牙已断开，请注意")
                .setCancelable(false)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mediaPlayer.stop();
                        Intent intent1 = new Intent(AlarmActivity.this, MainActivity.class);
                        startActivity(intent1);
                        AlarmActivity.this.finish();
                    }
                }).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setCanceledOnTouchOutside(false);//点击屏幕不消失
        if (!dialog.isShowing()){//此时提示框未显示
            dialog.show();
        }
    }
}
