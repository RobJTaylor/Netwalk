package com.example.robta.netwalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Used to show information to user when game finishes
 */
public class GameOver extends AppCompatActivity {

    /**
     * Creates the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        init();
    }

    /**
     * Initialises the view with total taps user made in Game
     */
    public void init() {
        //Get total taps from Game
        Intent intent = getIntent();
        int taps = intent.getIntExtra("taps", 0);

        //Create String, include total taps and update TextView
        String info = "You finished the game in " + taps + " taps. Why not try a harder difficulty, or maybe a custom game?";
        TextView infoTV = (TextView) findViewById(R.id.infoTV);
        infoTV.setText(info);
    }

    /**
     * Called from button press on GameOver
     * Ends the view and returns to main menu
     * @param view
     */
    public void mainMenu(View view) {
        //End view
        finish();
    }
}
