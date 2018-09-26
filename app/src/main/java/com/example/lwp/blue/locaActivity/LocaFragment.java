package com.example.lwp.blue.locaActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.lwp.blue.R;

/**
 * Created by Lwp on 2018/4/9.
 */
public class LocaFragment  extends Fragment {
    public LocationClient mLocationClient;
    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.activity_loca,container,false);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

      //  setContentView(R.layout.activity_main);
        mapView = (MapView) view.findViewById(R.id.bmapView);

        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        initLocation();    //定期刷新

        //这里其实得弄运行时权限
        mLocationClient.start();

        Button locaButton2 = (Button) view.findViewById(R.id.activity_loca_bu2);
        locaButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locaHistoryActivityIntent = new Intent(getActivity(),LocaHistoryActivity.class);
                startActivity(locaHistoryActivityIntent);
            }
        });
        return view;
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
       // option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location){
            if(location.getLocType() == BDLocation.TypeGpsLocation
                    ||location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }
        }
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
