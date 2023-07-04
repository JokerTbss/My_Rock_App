package com.example.my_rock_app;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.lifecycle.ViewModelProvider;

public class ImageAnalyzer implements ImageAnalysis.Analyzer{

    SharedViewModel sharedViewModel = new ViewModelProvider(this).get()
    @Override
    public void analyze(@NonNull ImageProxy image) {
        sharedViewModel.
    }
}
