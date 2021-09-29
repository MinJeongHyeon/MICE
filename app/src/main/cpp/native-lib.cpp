#include <jni.h>
#include <opencv2/opencv.hpp>
using namespace cv;
using namespace std;

extern "C" JNIEXPORT void JNICALL
Java_com_makguksu_mice_Camera_ImageCropping(
        JNIEnv* env, jobject thiz,
        jlong mat_addr_input) {
    Mat &src = *(Mat *) mat_addr_input;
    Mat mask;
    Mat converted;
    vector<vector<Point>> contours;
    vector<Vec4i> hierarchy;

    // HSV로 색 변환
    cvtColor(src, converted, COLOR_BGR2HSV);
    // 색 범위 지정 및 색 검출
    Scalar lower = Scalar(95, 48, 80);
    Scalar upper = Scalar(115, 255, 255);
    inRange(converted, lower, upper, mask);
    // 가우시안 블러
    GaussianBlur(mask, mask, Size(3, 3), 0);
    // 컨투어 찾기
    findContours(mask, contours, hierarchy,
                 RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

    // 컨투어가 있을 경우에 실행
    if (!contours.empty()) {
        int max_contour = -1;
        int max_area = -1;
        // 가장 큰 컨투어 찾기
        for (int i = 0; i < contours.size(); i++) {
            int area = contourArea(contours[i]);
            if (area > max_area) {
                max_area = area;
                max_contour = i;
            }
        }
        Scalar color1(0, 255, 255);
        Scalar color2(255, 255, 0);

        // 가장 큰 컨투어 뽑아내기
        vector<vector<Point>> contours2;
        contours2.push_back(contours[max_contour]);

        // 컨투어의 외접 사각형 그리기
        Rect r = boundingRect(contours2[0]);
        src = src(r);
    }
}


extern "C" JNIEXPORT void JNICALL
    Java_com_makguksu_mice_Camera_ImageProcessing(
        JNIEnv* env, jobject thiz,
        jlong mat_addr_input, jlong mat_addr_output) {
    // 객체 및 변수 선언
    Mat &src = *(Mat *)mat_addr_input;
    Mat &dst = *(Mat *)mat_addr_output;
    Mat mask;
    Mat converted;
    vector<vector<Point>> contours;
    vector<Vec4i> hierarchy;

    // HSV로 색 변환
    cvtColor(src,converted, COLOR_BGR2HSV);
    // 색 범위 지정 및 색 검출
    Scalar lower = Scalar(95, 48, 80);
    Scalar upper = Scalar(115, 255, 255);
    inRange(converted, lower, upper, mask);
    // 가우시안 블러
    GaussianBlur(mask, mask, Size(3, 3), 0);
    // 컨투어 찾기
    findContours(mask, contours, hierarchy,
                 RETR_EXTERNAL, CHAIN_APPROX_SIMPLE);

    // 컨투어가 있을 경우에 실행
    if(!contours.empty()) {
        int max_contour = -1;
        int max_area = -1;
        // 가장 큰 컨투어 찾기
        for (int i=0; i< contours.size(); i++) {
            int area = contourArea(contours[i]);
            if (area > max_area){
                max_area = area;
                max_contour = i;
            }
        }
        Scalar color1( 0, 255, 255 );
        Scalar color2( 255, 255, 0 );

        // 가장 큰 컨투어 뽑아내기
        vector<vector<Point>> contours2;
        contours2.push_back(contours[max_contour]);
        // 컨투어 그리기
        drawContours(dst, contours2, -1, color1, 4);
        // 컨투어의 외접 사각형 그리기
        Rect r = boundingRect(contours2[0]);
        rectangle(dst, Point(r.x, r.y), Point(r.x+r.width, r.y+r.height), color2, 4);
   }
}
