package com.example.androidmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.example.androidmap.InformationDB.OperateInDB;
import com.example.androidmap.InformationDB.locate;
import com.example.androidmap.UserDB.UserDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import java.util.ArrayList;

public class TraceActivity extends AppCompatActivity {

    MapView mMapView;
    private Context mContext;
    /**
     * 当前定位与标记点的判断距离,小于这个距离说明已到达标记点
     */
    private static final int DISTANCE_TO_MARKER = 20;
    /**
     * 异常距离，如果超过这个距离，则说明移动距离异常,避免定位抖动造成的误差
     */
    private static final int DISTANCE_ERROR = 50;
    private AMap mAMap;
    private LatLng currentLatLng;
    UserDB infoDB;
    OperateInDB OpInfoDB;
    ArrayList<locate> trace =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);

        infoDB = new UserDB(this);
        OpInfoDB = new OperateInDB(this);
        mMapView =findViewById(R.id.traceMap);
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mContext = this;
        //初始化地图
        initMap();
        trace = OpInfoDB.getTrace(RecordActivity.selectTime);
        PolylineOptions line = new PolylineOptions();
        currentLatLng = new LatLng(trace.get(0).getX(),trace.get(0).getY());
        for(locate l:trace){
            LatLng lastLatLng = currentLatLng;
            currentLatLng = new LatLng(l.getX(),l.getY());
            //绘制移动路线
            float movedDistance = AMapUtils.calculateLineDistance(currentLatLng, lastLatLng);
            if (movedDistance > DISTANCE_ERROR || movedDistance == 0) {
                continue;
            }
            line.add(lastLatLng, currentLatLng).width(10).color(Color.argb(255, 1, 1, 1));
            mAMap.addPolyline(new PolylineOptions().add(lastLatLng, currentLatLng).width(10).color(Color.argb(255, 1, 1, 1)));
        }
        //mAMap.addPolyline(line);
    }
    /**
     * 初始化地图参数
     */
    private void initMap() {
        mAMap = mMapView.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.interval(1000);
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        mAMap.setMyLocationEnabled(true);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.getUiSettings().setCompassEnabled(true);
        mAMap.getUiSettings().setScaleControlsEnabled(true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
