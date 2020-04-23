package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv=findViewById(R.id.tv);
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