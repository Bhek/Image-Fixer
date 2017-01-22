package com.bhegarty.imagefixer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.io.InputStream;

public class LoadImageActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private int PICK_IMAGE_REQUEST = 1;
    private boolean emulation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        if (emulation) {
            loadImage();
        } else {
            selectImage();
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void loadImage() {
        AssetManager assetManager = getResources().getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("test.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            showImage(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                showImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImage(Bitmap bitmap) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);

        Bitmap newBitmap = processImage(bitmap);

        imageView.setImageBitmap(newBitmap);
    }

    private Bitmap processImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Mat image = new Mat(width, height, CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap, image);

        for (int i = 0; i < height; i++) {
            double[] averages = getAveragePixels(image.get(i, 1623), image.get(i, 1633));
            for (int j = 1624; j < 1632; j++) {
                image.put(i, j, averages);
            }
        }

        Utils.matToBitmap(image, bitmap);
        return bitmap;
    }

    private double[] getAveragePixels(double[] startPixels, double[] endPixels) {
        double[] averages = new double[4];
        for (int i = 0; i < 4; i++) {
            double average = startPixels[i] + endPixels[i];
            average = average / 2;
            averages[i] = average;
        }
        return averages;
    }
}
