package com.zdd.autolibrary.opencv;

import android.graphics.Bitmap;

import com.zdd.autolibrary.model.ImgRect;

/**
 * Created by zdd on 2019/12/26
 */
public class OpencvHelper {

    //图片灰度化
    public static native Bitmap cvtBitmap(Bitmap bitmap, Bitmap.Config argb8888);

    //图片匹配，match采用5效果最好
    public static native ImgRect matchBitmap(Bitmap bigBitmap, Bitmap smallBitmap, Bitmap.Config argb8888, int match);

    //图片提取文字区域,只能识别出简单的黑白图片
    public static native Bitmap txtAreaBitmap(Bitmap bitmap, Bitmap.Config argb8888);

    public static native int faceDetector(Bitmap bitmap, Bitmap.Config argb8888, String path);
}
