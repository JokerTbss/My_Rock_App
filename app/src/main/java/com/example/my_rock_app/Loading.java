package com.example.my_rock_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class Loading extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        String result = getIntent().getStringExtra("results");

        TextView resultView = findViewById(R.id.textview_l);
        resultView.setText(result);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //reminder :  change main_screen with the analyse screen (that have information)
                Intent i = new Intent(Loading.this,main_screen.class);
                startActivity(i);

                finish();

            }
        },1000);

    }
}