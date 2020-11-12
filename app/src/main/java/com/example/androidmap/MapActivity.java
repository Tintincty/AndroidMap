package com.example.androidmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.example.androidmap.InformationDB.Information;
import com.example.androidmap.InformationDB.OperateInDB;
import com.example.androidmap.Login_and_Register.LoginActivity;
import com.example.androidmap.UserDB.User;
import com.example.androidmap.UserDB.UserDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author tintincty
 */
public class MapActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener {

    TextView mTvDistance;
    TextView mTvSpeed;
    MapView mMapView;
    Button stopButton;
    User u = LoginActivity.u;
    UserDB infoDB;
    OperateInDB OpInfoDB;
    SimpleDateFormat formatter;
    Date curDate;

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
    private ArrayList<Marker> markers;
    private LatLng currentLatLng;
    private long totalDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Information info = new Information(u.getUserName(),formatter.format(curDate),String.valueOf(totalDistance));
                OpInfoDB.save(info);
                finish();
            }
        });
        infoDB = new UserDB(this);
        OpInfoDB = new OperateInDB(this);
        mTvDistance = findViewById(R.id.Distance);
        mTvSpeed = findViewById(R.id.Speed);
        mMapView =findViewById(R.id.traceMap);
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mContext = this;
        //初始化地图
        initMap();
        //设置位置变化的监听
        mAMap.setOnMyLocationChangeListener(this);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        curDate =  new Date(System.currentTimeMillis());
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
        myLocationStyle.interval(500);
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        mAMap.setMyLocationEnabled(true);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.getUiSettings().setCompassEnabled(true);
        mAMap.getUiSettings().setScaleControlsEnabled(true);
    }

    /**
     * 位置变化的监听操作
     *
     * @param location 位置变化后的位置
     */
    @Override
    public void onMyLocationChange(Location location) {
        //首次定位时设置当前经纬度
        if (currentLatLng == null) {
            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            OpInfoDB.saveLocate(u.getUserName(),formatter.format(curDate),String.valueOf(currentLatLng.latitude),String.valueOf(currentLatLng.longitude));
        }
        LatLng lastLatLng = currentLatLng;
        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        OpInfoDB.saveLocate(u.getUserName(),formatter.format(curDate),String.valueOf(currentLatLng.latitude),String.valueOf(currentLatLng.longitude));
        //计算当前定位与前一次定位的距离，如果距离异常或是距离为0,则不做任何操作
        float movedDistance = AMapUtils.calculateLineDistance(currentLatLng, lastLatLng);
        if (movedDistance > DISTANCE_ERROR || movedDistance == 0) {
            return;
        }
        //绘制移动路线
        mAMap.addPolyline(new PolylineOptions().add(lastLatLng, currentLatLng).width(10).color(Color.argb(255, 1, 1, 1)));
        totalDistance += movedDistance;
        //在界面上显示总里程和当前的速度
        displayInfo(totalDistance, location.getSpeed());
    }

    /**
     * 在界面上显示总路程和当前的速度
     *
     * @param totalDistance 总路程
     * @param speed         当前速度
     */
    @SuppressLint("DefaultLocale")
    private void displayInfo(long totalDistance, float speed) {
        mTvDistance.setText(String.format("Total distance：%d m", totalDistance));
        mTvSpeed.setText(String.format("Current speed: %s m/s", speed));
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