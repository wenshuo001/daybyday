package com.ws.luban;

import android.app.Application;

import com.netlibrary.network.NetWorkManager;

/**
 * Creator :Wen
 * DataTime: 2019/8/17
 * Description:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getDefault().init(this);
    }
}
