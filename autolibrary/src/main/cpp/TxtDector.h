#include <android/bitmap.h>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc/types_c.h>
#include <vector>

using namespace cv;

using std::vector;//有分号

extern "C" {

Mat preprocess(Mat gray);

vector<RotatedRect> findTextRegion(Mat img);

Mat detectTextRegion(Mat img);
}
