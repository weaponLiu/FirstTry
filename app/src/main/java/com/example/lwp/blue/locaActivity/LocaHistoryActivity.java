package com.example.lwp.blue.locaActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lwp.blue.R;
import com.example.lwp.blue.baseActivity.BaseActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lwp on 2018/4/10.
 */
public class LocaHistoryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loca_history);
        findViewById(R.id.activity_loca_history_bu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //搜索位置文件信息，填进本Activity（待定）
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String dataTime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" +
                c.get(Calendar.DAY_OF_MONTH) + "   " + c.get(Calendar.HOUR_OF_DAY) + ":" +
                c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        SharedPreferences preferences = getSharedPreferences("locaHistoryData",MODE_PRIVATE);
        String data = preferences.getString("historyData","");

        ((TextView) findViewById(R.id.activity_loca_history_text)).setText(dataTime+"\n"+data);
    }
}
