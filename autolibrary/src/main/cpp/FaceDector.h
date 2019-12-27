#include <android/bitmap.h>
#include <opencv2/opencv.hpp>

using namespace cv;
#include <vector>
using std::vector;//有分号

class FaceDetector{
public:
    //加载文件
    static void loadCascade(const char *filename);
    //识别矩阵，返回脸的矩形列表
    static vector<Rect> detectorFace(Mat &src);
};