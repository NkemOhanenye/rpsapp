package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class DisplayButtonResults extends AppCompatActivity {

    String options[] ={"Rock","Paper","Scissor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_button_results);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

        // all reference variables that need to be created
        assert bundle != null;
        String choice = bundle.getString("playerChoice");
        TextView result = findViewById(R.id.lblResults);
        TextView playerChoice = findViewById(R.id.lblPlayerChoice);
        TextView comChoice = findViewById(R.id.lblComChoice);
        Button btnResult = findViewById(R.id.btnResult);

        playerChoice.setText(choice);
        comChoice.setText(checkComputerChoice());

        result.setText(displayResult(playerChoice, comChoice, btnResult));
    }
    // checks what the computer picks
    protected String checkComputerChoice(){
        Random r= new Random();
        String choice = options[r.nextInt(2)];
        if(choice.equals(options[0])){
            return choice; // computer chooses rock
        }
        else if (choice.equals(options[1])){
            return choice; // computer chooses paper
        }
        else if (choice.equals(options[2])){
            return choice; // computer chooses scissors
        } else {
            return "";
        }
    }
    // checks which one wins
    protected String displayResult(TextView player, TextView com, Button result) {
        if (player.getText().toString().equals(options[0]) && com.getText().toString().equals(options[2]) ||
                player.getText().toString().equals(options[1]) && com.getText().toString().equals(options[0]) ||
                player.getText().toString().equals(options[2]) && com.getText().toString().equals(options[1])) {
            result.setText(getString(R.string.play_again));
            return getString(R.string.player_win);  // player wins
        } else if (player.getText().toString().equals(options[0]) && com.getText().toString().equals(options[1]) ||
                player.getText().toString().equals(options[1]) && com.getText().toString().equals(options[2]) ||
                player.getText().toString().equals(options[2]) && com.getText().toString().equals(options[0])){
            result.setText(getString(R.string.play_again));
            return getString(R.string.com_win);  // computer wins
        } else {
            result.setText(getString(R.string.pick_another));
            return getString(R.string.tie_win);  // tie
        }
    }
    // checks to see what value the result button has
    public void btnResultPress(View view) {
        Button btnResult = findViewById(R.id.btnResult);
        if (btnResult.getText().toString().equals(getString(R.string.play_again))) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DisplayButtonChoice.class);
            startActivity(intent);
        }
    }
}
