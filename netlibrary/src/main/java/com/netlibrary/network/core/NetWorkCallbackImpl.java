package com.netlibrary.network.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.netlibrary.network.NetWorkManager;
import com.netlibrary.network.type.NetType;
import com.netlibrary.network.utils.Constants;

/**
 * Creator :Wen
 * DataTime: 2019/8/18
 * Description:
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetWorkCallbackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG,"网络已连接");

    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG,"网络已中断");

    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                Log.e(Constants.LOG_TAG,"网络发生变更为 WIFI");
                NetWorkManager.getDefault().post(NetType.WIFI);
            }else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                Log.e(Constants.LOG_TAG,"网络发生变更为 蜂窝网络 也就是流量");
                NetWorkManager.getDefault().post(NetType.CMWAP);
            }
        }
    }
}
