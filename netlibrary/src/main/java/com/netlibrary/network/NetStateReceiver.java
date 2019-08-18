package com.netlibrary.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.netlibrary.network.annotation.NetWork;
import com.netlibrary.network.bean.MethodManager;
import com.netlibrary.network.type.NetType;
import com.netlibrary.network.utils.Constants;
import com.netlibrary.network.utils.NetworkUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creator :Wen
 * DataTime: 2019/8/18
 * Description:
 */
public class NetStateReceiver extends BroadcastReceiver {
    private NetType netType;

    public NetStateReceiver() {
        netType = NetType.NONE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction()== null){
            Log.e(Constants.LOG_TAG,"异常");
            return;
        }
        //处理广播的事件
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)){
            Log.e(Constants.LOG_TAG,"网络发生了变更");
            netType = NetworkUtils.getNetType(); // 赋值网络发生变更

            if (NetworkUtils.isNetworkAvailable()){
                Log.e(Constants.LOG_TAG,"网络连接成功");

            }else {
                Log.e(Constants.LOG_TAG,"网络连接失败");
            }
            NetWorkManager.getDefault().post(netType);
        }
    }


}
