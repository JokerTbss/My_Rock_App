package com.example.my_rock_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ThumbnailUtils;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.my_rock_app.ml.ModelUnquant;
import com.google.mlkit.vision.common.InputImage;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageAnalyzer implements ImageAnalysis.Analyzer{

    private static ImageProxy latestProxy;
    private static int imageSize = 224;

    private Context context;

    public ImageAnalyzer(Context context) {
        this.context = context;
    }



    @OptIn(markerClass = ExperimentalGetImage.class)
    @Override
    public void  analyze(@NonNull ImageProxy imageProxy) {
        latestProxy = imageProxy;

        Bitmap image = imageProxy.toBitmap();
        
        //int dimension = Math.min(image.getWidth(), image.getHeight());
        //image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
        
        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
        classifyImage(image, context);

        imageProxy.close();
    }

    private void classifyImage(Bitmap image, Context context) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(context);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for (int i = 0; i < imageSize; i++){
                for (int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; //RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8 ) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++){
                if (confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String [] classes = {"Basalt","Coal", "Granite", "Limestone", "Marble", "Quartzite", "Sandstone"};

            String t = "";
            t = classes[maxPos];

            //result is a TextView
            //result.setText(classes[maxPos]);

            String s = "";
            for (int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            //confidende is also a TextView in the original code
            //confidence.setText(s);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    public static ImageProxy getLatestProxy() {
        return latestProxy;
    }
}
