package com.netlibrary.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.netlibrary.network.NetWorkManager;
import com.netlibrary.network.type.NetType;

/**
 * Creator :Wen
 * DataTime: 2019/8/17
 * Description:
 */
public class NetworkUtils {

    /**
     * 网络是否可用
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager  = (ConnectivityManager)NetWorkManager.getDefault().
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo[] info =connectivityManager.getAllNetworkInfo();
        if (info!=null){
            for (NetworkInfo anInfo : info){
                if (anInfo.getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static NetType getNetType(){
        ConnectivityManager connectivityManager  = (ConnectivityManager)NetWorkManager.getDefault().
                getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return NetType.NONE;
        //获取当前激活的网络连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null){
            return NetType.NONE;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE){
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                return NetType.CMNET;
            }else {
                return NetType.CMWAP;
            }
        }else if (nType == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }
}
