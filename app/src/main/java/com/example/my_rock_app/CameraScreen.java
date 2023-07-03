package com.example.my_rock_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Size;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;

    private ImageCapture imageCapture;

    private View view;




    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageAnalysis imageAnalysis;

    public CameraScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CameraScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static CameraScreen newInstance(String param1, String param2) {
        CameraScreen fragment = new CameraScreen();
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
        view = inflater.inflate(R.layout.fragment_camera_screen, container, false);

        Button test = view.findViewById(R.id.test_icon);


        // Request camera permission if not granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);

        } else {
            startCamera(view);
        }


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if clicked start imagecapture
                captureImage();

            }
        });

        Button back = view.findViewById(R.id.back_icon2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_cameraScreen_to_main_screen);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void captureImage(){
            long timestamp = System.currentTimeMillis();

            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


                imageCapture.takePicture(new ImageCapture.OutputFileOptions.Builder(
                                getActivity().getContentResolver(),
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                        ).build(),
                        ContextCompat.getMainExecutor(requireContext()),
                        new ImageCapture.OnImageSavedCallback() {
                            @Override
                            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                                Toast.makeText(requireContext(), "Image has been saved and file has been created", Toast.LENGTH_LONG).show();
                                String imagePath = outputFileResults.getSavedUri().getPath();
                                Bundle arg = new Bundle();
                                arg.putString("imagePath", imagePath);
                                NavOptions navOptions = new NavOptions.Builder()
                                        .setPopUpTo(R.id.cameraScreen, true)
                                        .build();
                                Navigation.findNavController(view)
                                        .navigate(R.id.action_cameraScreen_to_picAnalyse, arg, navOptions);
                            }

                            @Override
                            public void onError(@NonNull ImageCaptureException exception) {
                                Toast.makeText(requireContext(), "Image could not be saved " + exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

    }



    public void startCamera(View view) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Set up the preview
                Preview preview = new Preview.Builder()
                        .build();

                // Set the output resolution of the preview
                Size resolution = new Size(720, 1280);
                preview.setSurfaceProvider(((PreviewView) view.findViewById(R.id.previewView)).getSurfaceProvider());

                //Set up imagecapture
                imageCapture = new ImageCapture.Builder()
                        .build();

                //Set up ImageAnalysis
                imageAnalysis = new ImageAnalysis.Builder()
                        .build();

                // Select the back camera as the default
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                // Unbind any bound camera before binding
                cameraProvider.unbindAll();

                // Bind the camera to the lifecycle
                Camera camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        preview,
                        imageCapture,
                        imageAnalysis
                );

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

}