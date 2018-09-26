package com.example.lwp.blue.IntroActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lwp.blue.MainActivity;
import com.example.lwp.blue.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lwp on 2018/4/9.
 */
public class IntroFragment extends Fragment {
    private List<Intro> introList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_intro, container, false);
        initIntro();
        IntroAdapter adapter = new IntroAdapter( getActivity(), R.layout.activity_intro_item, introList);

        ListView listView = (ListView) view.findViewById(R.id.activity_intro_list_view);
        listView.setAdapter(adapter);
        return view;
    }

    private void initIntro(){
        Intro intro1 = new Intro("",
                "        感谢您选用蓝牙防丢器！",
                "        本防丢器是一款蓝牙型号为 HC-06 的产品。通过在手机端连接，来将用户容易丢失的物品（例如：钥匙、行李箱等物品）捆绑在一起，实现物品防丢失，寻物等，甚至您还可以使用在宝宝或者宠物身上，您再也不用担心他们丢失了。");
        introList.add(intro1);
        Intro intro2 = new Intro("开机与连接",
                "1. 按下装置按钮即开机，再次按下则关机。开机后打开app，点击右上角搜索图标搜索设备，找到对应蓝牙设备，接下来点击“连接”按钮即可",
                "2. 若蓝牙断开，手机会有“滴，滴，滴”的报警。此时也可再次选择蓝牙设备，点击“连接”即可");
        introList.add(intro2);
        Intro intro3 = new Intro(" 断点定位",
                "1. 在“定位”界面中，有两个按钮。点击“我的位置”按钮，可以在地图上显示出当前位置；点击“遗失记录”可以显示断点位置信息",
                "2. 若手机与蓝牙设备断开，手机会记录下断点位置，通过点击“遗失记录”可以看到断点位置信息，点击信息可在地图上显示");
        introList.add(intro3);
        Intro intro4 = new Intro("","以上是产品的大致介绍，如果有什么问题欢迎反馈给我们！谢谢！",
                "                                                                       ");
        introList.add(intro4);
    }
}
