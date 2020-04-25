package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DisplayButtonChoice extends AppCompatActivity {
    private static String options[] ={"Rock","Paper","Scissor"};
    private static String choice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_button_choice);
    }

    public void playerChoice(View view) {

        Intent intent = new Intent(this, DisplayButtonResults.class);
        switch (view.getId()){
            case R.id.btnRock:
                choice = options[0];
                intent.putExtra("playerChoice", choice);
                break;
            case R.id.btnPaper:
                choice = options[1];
                intent.putExtra("playerChoice", choice);
                break;
            case R.id.btnScissors:
                choice = options[2];
                intent.putExtra("playerChoice", choice);
                break;
        }
        startActivity(intent);
    }
}
