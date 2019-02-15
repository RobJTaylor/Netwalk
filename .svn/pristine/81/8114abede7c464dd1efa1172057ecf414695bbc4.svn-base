package com.example.robta.netwalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Allows the user to start a new game with varying rows and columns.
 */
public class NewGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    /**
     * Called when user selects easy game
     * Changes activity to game, passes rows and columns variables.
     * @param view
     */
    public void easyGame (View view) {
        Intent intent = new Intent(this, Game.class);
        //Make sure rows and columns are accessible in game
        intent.putExtra("rows", 4);
        intent.putExtra("columns", 4);
        startActivity(intent);
        //End view
        finish();
    }

    /**
     * Called when user selects medium game
     * Changes activity to game, passes rows and columns variables.
     * @param view
     */
    public void mediumGame (View view) {
        Intent intent = new Intent(this, Game.class);
        //Make sure rows and columns are accessible in game
        intent.putExtra("rows", 6);
        intent.putExtra("columns", 6);
        startActivity(intent);
        //End view
        finish();
    }

    /**
     * Called when user selects hard game
     * Changes activity to game, passes rows and columns variables.
     * @param view
     */
    public void hardGame (View view) {
        Intent intent = new Intent(this, Game.class);
        //Make sure rows and columns are accessible in game
        intent.putExtra("rows", 9);
        intent.putExtra("columns", 9);
        startActivity(intent);
        //End view
        finish();
    }

    /**
     * Called when user selects custom game
     * Changes activity to game, passes rows and columns variables.
     * @param view
     */
    public void customGame (View view) {
        Intent intent = new Intent(this, Game.class);

        //Make text boxes accessible
        EditText rowsTB = (EditText)findViewById(R.id.rowsTB);
        EditText columnsTB = (EditText)findViewById(R.id.columnsTB);
        //Get Strings from text boxes
        String stringRows = rowsTB.getText().toString();
        String stringColumns = columnsTB.getText().toString();
        //Convert Strings to ints
        int rows = Integer.valueOf(stringRows);
        int columns = Integer.valueOf(stringColumns);

        //Check that rows and columns are greater than 1
        if (rows <= 1 || columns <= 1) {
            //Notify user of their incompetence as toast notification
            String wrongValue = "Please select a number greater than 1 to start a custom game";
            Toast.makeText(this, wrongValue, Toast.LENGTH_LONG).show();
        }
        else {
            //Make rows and columns variables accessible to game
            intent.putExtra("rows", rows);
            intent.putExtra("columns", columns);
            startActivity(intent);
            //End view
            finish();
        }
    }
}
