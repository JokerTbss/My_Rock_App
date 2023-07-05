package com.example.my_rock_app;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.lifecycle.ViewModelProvider;

import com.google.mlkit.vision.common.InputImage;

public class ImageAnalyzer implements ImageAnalysis.Analyzer{

    private static ImageProxy latestProxy;


    @OptIn(markerClass = ExperimentalGetImage.class)
    @Override
    public void  analyze(@NonNull ImageProxy imageProxy) {
        latestProxy = imageProxy;

        Image image = imageProxy.getImage();
        if (imageProxy != null){
            //InputImage inputImage =
              //      InputImage.fromMediaImage(image, imageProxy.getImageInfo().getRotationDegrees());

            //Add object classification and identification here
        }


        imageProxy.close();
    }

    public static ImageProxy getLatestProxy() {
        return latestProxy;
    }
}
