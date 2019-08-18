package com.ws.luban;

/**
 * Creator :Wen
 * DataTime: 2019/8/18
 * Description:
 */
public class JNITools {

    static {
        System.loadLibrary("native-lib");
    }

    public static native int addNum(int num1, int num2);
}
