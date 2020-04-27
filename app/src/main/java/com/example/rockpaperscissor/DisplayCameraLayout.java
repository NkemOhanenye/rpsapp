package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;


public class DisplayCameraLayout extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static String TAG = "DisplayCameraLayout";
    JavaCameraView javaCameraView;

    List<String> hand = new ArrayList<>();
    private static String choice = "";

    // enables the camera to keep running
    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(DisplayCameraLayout.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
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
        int max_area = 0;
        int savedContour = 0;

        // draws the contours
        List<MatOfPoint> contours = new ArrayList<>();
        Rect ROI = new Rect(0, 0, mRgba.cols(), mRgba.rows());
        //Mat gray = new Mat(mRgba, ROI);
        Mat gray = new Mat(mRgba, ROI);
        Mat cannyOutput = new Mat();
        Mat hierarchy = new Mat();
        Imgproc.cvtColor(mRgba, gray, Imgproc.COLOR_RGB2GRAY);
        // blurs the image to try and get the focal point
        Imgproc.GaussianBlur(gray, gray, new Size(7, 7), 2, 2);
        // uses Canny's matricies to find the contour lines of an image
        Imgproc.Canny(gray, cannyOutput, 150, 200);
        // finds the matricies
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        List<MatOfPoint> hullList = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            Imgproc.convexHull(contour, hull, false);
            Point[] contourArray = contour.toArray();
            Point[] hullPoints = new Point[hull.rows()];
            List<Integer> hullContourIdxList = hull.toList();
            for (int i = 0; i < hullContourIdxList.size(); i++) {
                hullPoints[i] = contourArray[hullContourIdxList.get(i)];
            }
            hullList.add(new MatOfPoint(hullPoints));
        }

        for (int i = 0; i < contours.size(); i++) {
            double area = Imgproc.contourArea(contours.get(i));
            if (area > max_area) {
                max_area = (int) area;
                savedContour = i;
            }
        }
        Imgproc.drawContours(mRgba, contours, savedContour, new Scalar(255, 0, 0), 3);
        Imgproc.drawContours(mRgba, hullList, savedContour, new Scalar(0, 0, 255), 3);

        // flips the video
        Core.flip(mRgba.t(), cannyOutput, 1);
        Imgproc.resize(cannyOutput, cannyOutput, mRgba.size());

        // write the directions on the screen
        Imgproc.putText(cannyOutput, getText(R.string.make_hand_gesture).toString(), new Point(10, 50), Core.FONT_HERSHEY_PLAIN, 2.5, new Scalar(255, 255, 255), 3);


        // output finger result, when 50 values in list the last value is chosen as the answer
        if (contours.size() == 5) {
            Imgproc.putText(cannyOutput, "Paper", new Point(30, 100), Core.FONT_HERSHEY_PLAIN, 4, new Scalar(255, 255, 255), 3);
            hand.add("Paper");
            /*if (hand.size() >= 50) {
                choice = hand.get(hand.size() - 1);
                hand.clear();
                Intent intent = new Intent(this, DisplayCameraResults.class);
                intent.putExtra("playerChoice", choice);
                startActivity(intent);
            }*/
        } else if (contours.size() > 8) {
            Imgproc.putText(cannyOutput, "Scissors", new Point(30, 100), Core.FONT_HERSHEY_PLAIN, 4, new Scalar(255, 255, 255), 3);
            hand.add("Scissors");
            /*if (hand.size() >= 50) {
                choice = hand.get(hand.size() - 1);
                hand.clear();
                Intent intent = new Intent(this, DisplayCameraResults.class);
                intent.putExtra("playerChoice", choice);

                startActivity(intent);
            }*/
        } else if (contours.size() > 0) {
            Imgproc.putText(cannyOutput, "Rock", new Point(30, 100), Core.FONT_HERSHEY_PLAIN, 4, new Scalar(255, 255, 255), 3);
            hand.add("Rock");
            /*if (hand.size() >= 50) {
                choice = hand.get(hand.size() - 1);
                hand.clear();
                Intent intent = new Intent(this, DisplayCameraResults.class);
                intent.putExtra("playerChoice", choice);
                startActivity(intent);
            }*/
        }

        return cannyOutput;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    /**
     * Handles the cases in which the camera is closed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null) {
            javaCameraView.disableView();
        }
    }

    /**
     * checks to see if the debugger is running, if there is a crash, the camera closes
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV is Configured or Connected Successfully.");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        } else {
            Log.d(TAG, "OpenCV not working or loaded.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }
}
