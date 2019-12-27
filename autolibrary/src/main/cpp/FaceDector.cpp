#include "FaceDector.h"

CascadeClassifier cascadeClassifier;
//人脸检测
vector<Rect> FaceDetector::detectorFace(Mat &src) {
    vector<Rect> faces;//脸的数组
    Mat temp_mat;//用于存放识别到的图像临时矩阵
    cvtColor(src, temp_mat, COLOR_BGRA2GRAY);//灰度图,加快解析速度
    equalizeHist(temp_mat, temp_mat);//直方图均衡化
    //多尺度人脸检测
    cascadeClassifier.detectMultiScale(temp_mat, faces,  1.1,3,0, Size(300,300));
    return faces;
}

void FaceDetector::loadCascade(const char *filename) {
    cascadeClassifier.load(filename);
}