package com.example.lwp.blue.myActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.lwp.blue.R;
import com.example.lwp.blue.baseActivity.BaseActivity;

/**
 * Created by WP on 2018/4/11.
 */
public class MyActivityButtonOne extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_button_one);
        findViewById(R.id.activity_my_button_one_bu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}