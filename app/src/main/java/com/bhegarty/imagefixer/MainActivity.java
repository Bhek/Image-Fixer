package com.bhegarty.imagefixer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity /*implements CameraBridgeViewBase.CvCameraViewListener2*/ {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static Uri uri;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
//    private CameraBridgeViewBase mOpenCvCameraView;

    static {
        System.loadLibrary("opencv_java3");
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        if (isCameraPermissionGranted()) {
////            mOpenCvCameraView.enableView();
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        if (isCameraPermissionGranted()) {
//            mOpenCvCameraView.enableView();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.MainCameraView);
//        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(LOG_TAG, "onActivityResult: " + resultCode);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.v(LOG_TAG, "Result Okay");

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
//            intent.putExtra("image", imageBitmap);
            startActivity(intent);
        }
    }

//    @Override
//    public void onPause()
//    {
//        super.onPause();
////        if (mOpenCvCameraView != null)
////            mOpenCvCameraView.disableView();
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
////        if (mOpenCvCameraView != null)
////            mOpenCvCameraView.disableView();
//    }
//
//    public void onCameraViewStarted(int width, int height) {
//    }
//
//    public void onCameraViewStopped() {
//    }

//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        Mat inputImage = inputFrame.rgba();
////        Mat newImage = mOpenCvCameraView.getRotation()
//        int height = inputFrame.rgba().height();
//        int width = inputFrame.rgba().width();
//
//        Mat newImage = Imgproc.getRotationMatrix2D(new Point(width/2, height/2), 90, 1);
//
//        Log.i(LOG_TAG, "HEIGHT: " + inputFrame.rgba().height());
//        Log.i(LOG_TAG, "WIDTH: " + inputFrame.rgba().width());
////        return inputFrame.rgba();
//        return newImage;
//    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(LOG_TAG, "Permission is granted");
                return true;
            } else {

                Log.v(LOG_TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(LOG_TAG, "Permission is granted");
            return true;
        }
    }
}
