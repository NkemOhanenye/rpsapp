package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void openCameraGame (View view) {

        Intent intent = new Intent(this, DisplayCameraLayout.class);

        startActivity(intent);
    }

    public void openButtonGame (View view) {

        Intent intent = new Intent(this, DisplayButtonChoice.class);

        startActivity(intent);
    }
}