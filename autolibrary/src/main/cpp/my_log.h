//
// Created by zdd on 2019/2/25.
//

#ifndef NDKDEMO_MYLOG_H
#define NDKDEMO_MYLOG_H


#include <android/log.h>

#define NLP_LOG_TAG "zddNDK"



#define LOGOPEN 1

#if(LOGOPEN==1)

#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, NLP_LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, NLP_LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, NLP_LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, NLP_LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, NLP_LOG_TAG, __VA_ARGS__)

#else

#define LOGV(...) NULL
#define LOGD(...) NULL
#define LOGI(...) NULL
#define LOGW(...) NULL
#define LOGE(...) NULL

#endif

#endif
