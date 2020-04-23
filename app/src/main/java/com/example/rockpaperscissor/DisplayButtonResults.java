package com.example.rockpaperscissor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        // all reference variables that need to be created
        TextView result = findViewById(R.id.lblResults);
        TextView playerChoice = findViewById(R.id.lblPlayerChoice);
        TextView comChoice = findViewById(R.id.lblComChoice);
        Button btnResult = findViewById(R.id.btnResult);

        playerChoice.setText(checkPlayerChoice());
        comChoice.setText(checkComputerChoice());

        result.setText(displayResult(playerChoice, comChoice, btnResult));
    }
    // checks what the player picks
    protected String checkPlayerChoice() {
        String playerChoice = ;

        if (playerChoice == options[0]){
            return playerChoice;

        } else if (playerChoice == options[1]){
            return playerChoice;

        } else {
            return playerChoice;
        }
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
        else {
            return choice; // computer chooses scissors
        }
    }
    // checks which one wins
    protected String displayResult(TextView player, TextView com, Button result) {
        if (player.getText() == options[0] && com.getText() == options[2] ||
                player.getText() == options[1] && com.getText() == options[0] ||
                player.getText() == options[2] && com.getText() == options[1]) {
            result.setText(getString(R.string.play_again));
            return getString(R.string.player_win);  // player wins
        } else if (player.getText() == options[0] && com.getText() == options[1] ||
                player.getText() == options[1] && com.getText() == options[2] ||
                player.getText() == options[2] && com.getText() == options[0]) {
            result.setText(getString(R.string.play_again));
            return getString(R.string.com_win);  // computer wins
        } else {
            result.setText(getString(R.string.pick_another));
            return getString(R.string.tie_win);  // tie
        }
    }
}
