package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DisplayButtonChoice extends AppCompatActivity {
    private static String options[] ={"Rock","Paper","Scissor"};
    private static String choice = "";
    //int uopt;
    //TextView tv;

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

    /*public void rock(View view) {
        uopt = 0;
        Toast.makeText(this, "You opted" + options[uopt], Toast.LENGTH_SHORT).show();
    }

    public void paper(View view) {
        uopt=1;
        Toast.makeText(this, "You opted" + options[uopt], Toast.LENGTH_SHORT).show();
    }

    public void scissor(View view) {

        uopt = 2;
        Toast.makeText(this, "You opted" + options[uopt], Toast.LENGTH_SHORT).show();
    }

    public void play(View view){
        Random r= new Random();
        int oopt = r.nextInt(2);
        if(uopt == oopt){
            tv.setText("Game Draw !!!! Becaues you opted "+ options[uopt]+ "and system opt"+oopt);
        }
        else if ((oopt == 0 && uopt ==1)|| (oopt ==1 && uopt ==2) ||(oopt ==2 && uopt == 0)){
            tv.setText("YOU won !!!! Because you opted" + options[uopt]+ " and system opted"+ options[oopt]);
        }
        else {
            tv.setText("You lose !!! Becaues you opted" +options[uopt]+ "   and sytem opted "+options[oopt]);
        }
    }*/
}
