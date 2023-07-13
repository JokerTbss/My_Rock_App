package com.example.my_rock_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.my_rock_app.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PicAnalyse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PicAnalyse extends Fragment {

    private static int imageSize = 224;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private Bundle arg2;

    public PicAnalyse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PicAnalyse.
     */
    // TODO: Rename and change types and number of parameters
    public static PicAnalyse newInstance(String param1, String param2) {
        PicAnalyse fragment = new PicAnalyse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pic_analyse, container, false);

        //implementation for the image captured before
        ImageView imageView = view.findViewById(R.id.image_view);
        File imagefile = null;

        arg2 = new Bundle();

        Bundle arg = getArguments();

        if (arg != null && arg.containsKey("imagePath")){
            String imagepath = arg.getString("imagePath");
            if (imagepath != null){
                imagefile = new File(imagepath);
                arg2.putString("imagePath", imagepath);
                try{
                    imageView.setImageURI(Uri.fromFile(imagefile));}
                catch(Exception e){
                    Toast.makeText(requireContext(), "image didn't work", Toast.LENGTH_LONG).show();
                }

            }
        }

        Button return_pic= view.findViewById(R.id.retake_picture);
        return_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_picAnalyse_to_cameraScreen);
            }

        });



        Button out = view.findViewById(R.id.exit);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_picAnalyse_to_main_screen);

            }
        });




        Button analyse = view.findViewById(R.id.analyse);
        File finalImagefile = imagefile;
        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(finalImagefile != null) {
                    Bitmap bitImage = BitmapFactory.decodeFile(finalImagefile.getAbsolutePath());
                    bitImage = Bitmap.createScaledBitmap(bitImage, imageSize, imageSize, false);



                    classifyImage(bitImage);

                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.picAnalyse, true)
                            .build();
                    Navigation.findNavController(view)
                            .navigate(R.id.action_picAnalyse_to_rock_analyser, arg2, navOptions);
                } else {
                    Toast.makeText(requireContext(), "No imagefile found", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void classifyImage(Bitmap image) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(requireContext());

            /*int imageSize = Math.min(image.getWidth(), image.getHeight());
            int [] imagePixels = new int[imageSize * imageSize];
            image.getPixels(imagePixels, 0, imageSize, 0, 0, imageSize, imageSize);*/


            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);
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



            String s = "";
            for (int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            arg2.putString("results", t + s);
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
}