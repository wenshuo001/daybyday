package com.netlibrary.network;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.netlibrary.network.annotation.NetWork;
import com.netlibrary.network.bean.MethodManager;
import com.netlibrary.network.core.NetWorkCallbackImpl;
import com.netlibrary.network.listener.NetChanggeObserver;
import com.netlibrary.network.type.NetType;
import com.netlibrary.network.utils.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creator :Wen
 * DataTime: 2019/8/17
 * Description:网络管理类
 */
public class NetWorkManager {
    private static volatile NetWorkManager instance;
    private Application application;

    private NetStateReceiver receiver;

    private Map<Object,List<MethodManager>> networkList;

    public NetWorkManager() {
        receiver = new NetStateReceiver();
        networkList = new HashMap<>();
    }

//    public void register(Object object) {
//        receiver.register(object);
//    }
//    public void setReceiver2(NetChanggeObserver observer) {
//        this.receiver2.setListener(observer);
//    }




    public static NetWorkManager getDefault() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                if (instance == null) {
                    instance = new NetWorkManager();
                }
            }
        }
        return instance;
    }

    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("...");
        }
        return application;
    }

    @SuppressLint("MissingPermission")
    public void init(Application application) {
        this.application = application;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new NetWorkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null)
                connectivityManager.registerNetworkCallback(request, networkCallback);
        } else {
            //动态注册广播
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
            application.registerReceiver(receiver, filter);
        }
    }


    public void register(Object object){
        List<MethodManager> methodList = networkList.get(object);
        if (methodList == null){
            //收集
            methodList = findAnntationMethod(object);
            networkList.put(object,methodList);
        }
    }

    public void unregister(Object object){
        List<MethodManager> methodList = networkList.get(object);
        if (methodList == null){
            //清除
            if (networkList.containsKey(object)){
                networkList.remove(object);
            }
        }
    }

    private List<MethodManager> findAnntationMethod(Object object) {
        List<MethodManager> methodManagerList = new ArrayList<>();

        Class<?> clazz =object.getClass();

        Method[] methods = clazz.getMethods();

        for (Method method: methods){

            NetWork netWork = method.getAnnotation(NetWork.class);
            if (netWork == null){
                continue;
            }
            //获取方法的返回值
            //method.getGenericReturnType();
            //获取方法的参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length !=1){
                throw new RuntimeException(method.getName()+"方法的参数有且只有一个");
            }
            MethodManager manager= new MethodManager(parameterTypes[0],netWork.netType(),method);
            methodManagerList.add(manager);
        }
        return methodManagerList;
    }

    //网络匹配
    public void post(NetType netType){
        if (networkList.isEmpty()) return;
        Set<Object> keySet = networkList.keySet();
        //获取MainActivity对象
        for (Object getter: keySet){
            //获取MainActivity对象中所有的订阅方法
            List<MethodManager> methodManagers = networkList.get(getter);
            if (methodManagers != null){
                for (MethodManager manager:methodManagers){
                    //注解 参数匹配
                    if (manager.getType().isAssignableFrom(netType.getClass()));
                    switch (manager.getNetType()){
                        case AUTO:
                            invoke(manager,getter,netType);
                            break;
                        case WIFI:
                            if (netType == NetType.WIFI || netType == NetType.NONE){
                                invoke(manager,getter,netType);
                            }
                            break;
                        case CMWAP:
                        case CMNET:
                            if (netType == NetType.CMWAP || netType == NetType.CMNET|| netType == NetType.NONE){
                                invoke(manager,getter,netType);
                            }
                            break;
                    }
                }
            }
        }
    }

    private void invoke(MethodManager manager, Object getter, NetType netType) {
        try {
            manager.getMethod().invoke(getter,netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
