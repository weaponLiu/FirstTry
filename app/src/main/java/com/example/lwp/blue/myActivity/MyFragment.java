package com.example.lwp.blue.myActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lwp.blue.R;
import com.example.lwp.blue.locaActivity.LocaHistoryActivity;

/**
 * Created by Lwp on 2018/4/9.
 */
public class MyFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my,container,false);

        Button myButton1 = (Button)view.findViewById(R.id.activity_my_bu1);
        Button myButton2 = (Button)view.findViewById(R.id.activity_my_bu2);
        Button myButton3 = (Button)view.findViewById(R.id.activity_my_bu3);

        myButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myActivityIntent = new Intent(getActivity(),MyActivityButtonOne.class);
                startActivity(myActivityIntent);
            }
        });
        myButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myActivityIntent = new Intent(getActivity(),MyActivityButtonTwo.class);
                startActivity(myActivityIntent);
            }
        });
        myButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myActivityIntent = new Intent(getActivity(),MyActivityButtonThree.class);
                startActivity(myActivityIntent);
            }
        });

        return view;
    }
}
