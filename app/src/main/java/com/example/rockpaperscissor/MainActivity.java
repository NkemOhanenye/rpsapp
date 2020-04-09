package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pd.mlgame.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String options[] ={"Rock","Paper","Scissor"};
    int uopt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv);
    }

    public void rock(View view) {
        uopt=0;
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
    }
}