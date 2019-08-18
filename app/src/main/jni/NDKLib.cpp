//
// Created by Wenshuo on 2019/8/18.
//


#include <jni.h>
#include "com_ws_luban_JNITools.h"
JNIEXPORT jint JNICALL Java_com_ws_luban_JNITools_addNum(JNIEnv *, jobject jobject1, jint num1, jint num2){
    return num1 +num2;
}
