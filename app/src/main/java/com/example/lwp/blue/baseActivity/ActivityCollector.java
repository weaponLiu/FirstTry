package com.example.lwp.blue.baseActivity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WP on 2018/4/12.
 */
public class ActivityCollector{
    public static List<Activity> activityList = new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }
    //保存当前Activity
    public static void saveNowActivity(Activity act){
        for (Activity activity:activityList){
            if(activity != act)
                if(!activity.isFinishing()){
                    activity.finish();
                }
        }
        activityList.clear();
    }
    public static void finishAll(){
        for (Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activityList.clear();
    }
}


