package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DisplayCameraLayout extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "DisplayCameraLayout";
    JavaCameraView javaCameraView;

    // enables the camera to keep running
    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(DisplayCameraLayout.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    javaCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
            super.onManagerConnected(status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_camera_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // keeps the screen on

        // opens the camera
        javaCameraView = (JavaCameraView) findViewById(R.id.my_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(DisplayCameraLayout.this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
    }

    @Override
    public void onCameraViewStopped() {
    }

     @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
         Mat mRgba = inputFrame.rgba();
         List<MatOfPoint> contours = new ArrayList<>();
         Mat gray = new Mat();
         Mat hierarchy = new Mat();
         // blurs the image to try and get the focal point
         Imgproc.GaussianBlur(mRgba, gray, new Size(7, 7), 2,  2);
         // uses Canny's matricies to find the contour lines of an image
         Imgproc.Canny(mRgba, gray, 120, 200);
         // finds the matricies
         Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0,0));

         //hierarchy.release();

         for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++){
             Imgproc.drawContours(mRgba, contours, contourIdx, new Scalar(250,0,0), 3);
         }

         return mRgba;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    /** Handles the cases in which the camera is closed */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null){
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null){
            javaCameraView.disableView();
        }
    }

    /** checks to see if the debugger is running, if there is a crash, the camera closes */
    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCV is Configured or Connected Successfully.");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        } else {
          Log.d(TAG, "OpenCV not working or loaded.");
          OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }
}
