package com.netlibrary.network.listener;

import com.netlibrary.network.type.NetType;

/**
 * Creator :Wen
 * DataTime: 2019/8/17
 * Description: 网络监听接口
 */
public interface NetChanggeObserver {

    void onConnect(NetType netType);

    void onDisConnect();
}
