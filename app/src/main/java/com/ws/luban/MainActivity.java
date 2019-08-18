package com.ws.luban;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.netlibrary.network.NetWorkManager;
import com.netlibrary.network.annotation.NetWork;
import com.netlibrary.network.listener.NetChanggeObserver;
import com.netlibrary.network.type.NetType;
import com.netlibrary.network.utils.Constants;

public class MainActivity extends AppCompatActivity implements NetChanggeObserver {





        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // NetWorkManager.getDefault().setReceiver2(this);
        NetWorkManager.getDefault().register(this);
        int num = JNITools.addNum(12,1);
        Toast.makeText(getApplicationContext(),""+num,Toast.LENGTH_SHORT).show();
    }

    @NetWork()
    public void netWork(NetType netType){
        switch (netType){
            case WIFI:
                Log.e(Constants.LOG_TAG,"WIFI");
                break;
            case CMNET:
                Log.e(Constants.LOG_TAG,"CMNET");
                break;
            case CMWAP:
                Log.e(Constants.LOG_TAG,"CMWAP");
                break;
            case NONE:
                Log.e(Constants.LOG_TAG,"NONE");
                break;
        }
    }

    @Override
    public void onConnect(NetType netType) {
        Log.e(Constants.LOG_TAG,netType.name());
    }

    @Override
    public void onDisConnect() {
        Log.e(Constants.LOG_TAG,"onDisConnect");
    }

    public void requestPower() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CHANGE_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CHANGE_NETWORK_STATE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CHANGE_NETWORK_STATE,}, 1);
            }
        }
    }
}
