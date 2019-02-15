package com.example.robta.netwalk;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * MainActivity used to control main menu.
 */
public class MainActivity extends AppCompatActivity {
    //Set default settings
    public static Settings settings = new Settings();

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when user taps Play - launches NewGame activity.
     * @param view
     */
    public void Play(View view){
        Intent intent = new Intent(this, NewGame.class);
        startActivity(intent);
    }

    /**
     * Called when user taps About - launches About activity.
     * @param view
     */
    public void About(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    /**
     * Called when user taps How To Play - launches How To Play activity.
     * @param view
     */
    public void HowToPlay(View view) {
        Intent intent = new Intent(this, HowToPlay.class);
        startActivity(intent);
    }

    /**
     * Called when user taps Settings - launches Settings activity.
     * @param view
     */
    public void Settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}
