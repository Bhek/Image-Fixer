package com.bhegarty.imagefixer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class ImageActivity extends Activity {

//    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/CorrectImages/";
public static final String DATA_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/CorrectImages/";

    private static final String LOG_TAG = ImageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreate");

        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.v(LOG_TAG, "ERROR: Creation of directory " + DATA_PATH + " on sdcard failed");
                return;
            }
            else {
                Log.v(LOG_TAG, "Created directory " + DATA_PATH + " on sdcard");
            }
        } else {
            Log.v(LOG_TAG, "Directory exists");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }
}
