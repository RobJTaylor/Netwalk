package com.example.robta.netwalk;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by robta on 12/02/2017.
 * Used to capture user taps.
 */

public class gListener extends GestureDetector.SimpleOnGestureListener {

    static String currentGestureDetected;

    /**
     * Gets the motion event and converts it to a String.
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        currentGestureDetected = e.toString();
        return true;
    }
}
