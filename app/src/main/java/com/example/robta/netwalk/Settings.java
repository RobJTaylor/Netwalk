package com.example.robta.netwalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

/**
 * Allows user to set preferences
 */
public class Settings extends AppCompatActivity {
    //Define necessary variables
    private int iconStyle = 0;
    private int scrambleChance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     * Deals with radio button presses, changes user preferences.
     *
     * @param view
     */
    public void radioButtonPressed(View view) {
        //Android manual referenced @ https://developer.android.com/guide/topics/ui/controls/radiobutton.html

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.classicRB:
                if (checked) {
                    MainActivity.settings.iconStyle = 0;
                    break;
                }
            case R.id.prototypeRB:
                if (checked) {
                    MainActivity.settings.iconStyle = 1;
                    break;
                }
            case R.id.scrambleEnableRB:
                if (checked) {
                    MainActivity.settings.scrambleChance = 0;
                    break;
                }
            case R.id.scrambleDisableRB:
                if (checked) {
                    MainActivity.settings.scrambleChance = 1;
                    break;
                }
        }

    }

    /**
     * Gets iconStyle to determine which icon set to use
     * @return
     */
    public int getIconStyle() {
        return iconStyle;
    }

    /**
     * Gets scrambleChance to determine if we should randomly scramble
     * @return
     */
    public int getScrambleChance() {
        return scrambleChance;
    }
}
