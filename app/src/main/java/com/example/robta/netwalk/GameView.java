package com.example.robta.netwalk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by robta on 04/02/2017.
 * Draws the grid, manages parts of the game such as calculating tapped cells.
 */
public class GameView extends View {
    /**
     * Call init to begin initialisation.
     *
     * @param context
     */
    public GameView(Context context) {
        super(context);
        init();
    }

    private int[] mOriginalGrid = new int[Game.getGrid().getColumns() * Game.getGrid().getRows()];

    //Pre-initialize canvas height and width
    private int canvasWidth;
    private int canvasHeight;
    private int cellWidth;
    private int cellHeight;

    /**
     * Called to initialise the game
     */
    public void init() {
        //Get number of columns
        int yCells = Game.getGrid().getColumns();

        int cellYNumber = 0;
        int cellXNumber = 0;

        //Loop through grid
        for (int i = 0; i < mOriginalGrid.length; i++) {
            //Determine if column needs to be adjusted
            if (cellXNumber == yCells) {
                cellYNumber++;
                cellXNumber = 0;
            }

            //Store current element in original grid array
            mOriginalGrid[i] = Game.getGrid().getGridElem(cellXNumber, cellYNumber);

            //Scramble the current element
            scrambleElement(cellXNumber, cellYNumber);

            //Add 1 to cellXNumber
            cellXNumber++;
        }
    }

    /**
     * Called to scramble grid element and store server position.
     *
     * @param cellXNumber X cell to scramble
     * @param cellYNumber Y cell to scramble
     */
    public void scrambleElement(int cellXNumber, int cellYNumber) {
        //Generate new random number up to 6
        Random rand = new Random();
        int random = rand.nextInt(6);

        //Up to random number
        for (int i = 0; i < random; i++) {
            //Call rotateRight() to rotate
            Game.getGrid().rotateRight(cellXNumber, cellYNumber);
        }
    }

    /**
     * Draws the grid
     *
     * @param canvas Canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Get rows and columns
        int rowCount = Game.getGrid().getRows();
        int columnCount = Game.getGrid().getColumns();

        //Get canvas dimensions
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        //Get cell width and height
        cellWidth = canvasWidth / columnCount;
        cellHeight = canvasHeight / rowCount;

        //Set current X and Y cell number
        int cellXNumber = 0;
        int cellYNumber = 0;

        //Get total number of cells
        int totalCount = rowCount * columnCount;

        for (int i = 0; i < totalCount; i++) {
            //Get cell width and height
            int cellWidth = canvas.getWidth() / columnCount;
            int cellHeight = canvas.getHeight() / rowCount;

            //Adjust cell Y value when looping
            if (cellXNumber == columnCount) {
                cellYNumber++;
                cellXNumber = 0;
            }

            //Get total rows and columns
            int currentItem = Game.getGrid().getGridElem(cellXNumber, cellYNumber);

            //Get image to draw
            Bitmap resizedImage = getBitmap(currentItem);

            //Calculate X and Y coordinates
            int positionX = cellXNumber * cellWidth;
            int positionY = cellYNumber * cellHeight;

            //Draw bitmap at calculated coordinates
            canvas.drawBitmap(resizedImage, positionX, positionY, null);

            cellXNumber++;
        }

    }

    /**
     * Used to determine what bitmap to draw
     *
     * @param number
     */
    public Bitmap getBitmap(int number) {
        //Code for bitmap modified from https://developer.android.com/reference/android/graphics/Bitmap.html
        //Code for bitmap resizing based off, but modified from http://stackoverflow.com/questions/4837715/how-to-resize-a-bitmap-in-android
        //Define bitmap image and resized image
        Bitmap image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.appicon);
        Bitmap resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);

        //Determine which icon pack to use
        if (MainActivity.settings.getIconStyle() == 0) {
            switch (number) {
                //Cases for straight lines
                case 5:
                    //Change image
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_up_down_classic);
                    //Resize image
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 10:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for L lines
                case 9:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 3:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 6:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 12:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for T lines
                case 14:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 11:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_down_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 7:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 13:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for connected servers
                case 81:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 82:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 84:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 86:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 88:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 89:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 85:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 90:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 95:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_all_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 91:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 94:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 93:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 87:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 83:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_right_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 92:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for unconnected computers
                case 33:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_down_purple_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 34:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_right_purple_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 36:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_up_purple_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 40:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_left_purple_classic);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
            }
            if (MainActivity.settings.getIconStyle() == 0) {
                switch (number) {
                    //Cases for straight lines
                    case 5:
                        //Change image
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_up_down_classic);
                        //Resize image
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 10:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    //Cases for L lines
                    case 9:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 3:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 6:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 12:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    //Cases for T lines
                    case 14:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 11:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_down_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 7:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 13:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    //Cases for connected servers
                    case 81:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 82:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 84:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 86:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 88:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 89:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 85:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 90:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 95:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_all_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 91:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 94:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 93:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 87:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 83:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_right_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 92:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    //Cases for unconnected computers
                    case 33:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_down_purple_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 34:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_right_purple_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 36:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_up_purple_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                    case 40:
                        image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_left_purple_classic);
                        resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                        return resizedImage;
                }
            }
        }
        //If icon pack is prototype
        else if (MainActivity.settings.getIconStyle() == 1) {
            switch (number) {
                //Cases for straight lines
                case 5:
                    //Change image
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_up_down_prototype);
                    //Resize image
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 10:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.line_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for L lines
                case 9:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 3:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_down_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 6:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 12:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.l_up_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for T lines
                case 14:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 11:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_down_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 7:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 13:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.t_up_down_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for connected servers
                case 81:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 82:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 84:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 86:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 88:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 89:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 85:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 90:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 95:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_all_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 91:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 94:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 93:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 87:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_up_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 83:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_down_right_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 92:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.server_up_left_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                //Cases for unconnected computers
                case 33:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_down_purple_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 34:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_right_purple_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 36:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_up_purple_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
                case 40:
                    image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.comp_left_purple_prototype);
                    resizedImage = Bitmap.createScaledBitmap(image, cellWidth, cellHeight, false);
                    return resizedImage;
            }
        }
        return resizedImage;
    }

    /**
     * Called when canvas needs re-drawing
     */

    public void redrawCanvas() {
        invalidate();
    }

    /**
     * Called to calculate tapped cell.
     *
     * @param x X coordinate user tapped
     * @param y Y coordinate user tapped
     * @return Grid location tapped
     */
    public int[] calculateTappedCell(int x, int y) {
        //Get cell width and height
        int cellWidth = canvasWidth / Game.getGrid().getColumns();
        int cellHeight = canvasHeight / Game.getGrid().getRows();
        //Get columns and rows
        int columns = Game.getGrid().getColumns();
        int rows = Game.getGrid().getRows();
        //Setup tapped variables
        int tappedX = 0;
        int tappedY = 0;
        //Setup current column and last column variables
        int currentColumn = 0;
        int lastColumn;
        //Setup current row and last row variables
        int currentRow = 0;
        int lastRow;

        //Determine which column has been tapped
        for (int i = 0; i <= columns; i++) {
            //Add 1 to current column and set last column to current column - 1
            //This is used to calculate which row has been tapped
            currentColumn++;
            lastColumn = currentColumn - 1;

            //Set current max and current min based on current column and last column
            //This gives us boundaries for the tap
            int currentMax = currentColumn * cellWidth;
            int currentMin = lastColumn * cellWidth;

            //If tap falls between boundary then set tappedX to i
            if (x <= currentMax && x >= currentMin) {
                tappedX = i;
            }
        }

        //Determine which row has been tapped
        for (int i = 0; i <= rows; i++) {
            //Add 1 to current row and set last row to current row - 1
            //This is used to calculate which column has been tapped
            currentRow++;
            lastRow = currentRow - 1;

            //Set current max and current min based on current row and last row
            //This gives us boundaries for the tap
            int currentMax = currentRow * cellHeight;
            int currentMin = lastRow * cellHeight;

            //If tap falls between boundary then set tappedY to i
            if (y <= currentMax && y >= currentMin) {
                tappedY = i - 1;
            }
        }

        //Define coordinates array, set contents ro tappedX and tappedY
        int[] coordinates = new int[2];
        coordinates[0] = tappedX;
        coordinates[1] = tappedY;

        /*If statements fix a bug where tapping past cells can trigger
        an array out of bounds exception.*/
        if (coordinates[0] == -1) {
            coordinates[0] = 0;
        }
        if (coordinates[0] == columns + 1) {
            coordinates[0] = columns;
        }
        if (coordinates[1] == -1) {
            coordinates[1] = 0;
        }
        if (coordinates[1] == rows + 1) {
            coordinates[1] = rows;
        }

        //Call rotateRight() using the calculated cell
        Game.getGrid().rotateRight(coordinates[0], coordinates[1]);


        //Redraw rhe canvas
        redrawCanvas();
        //Return the coordinates, used for debugging
        return coordinates;
    }

    /**
     * Gives integer value of tapped item, used for debugging
     *
     * @param x X cell to identify
     * @param y Y cell to identify
     * @return Integer value of tapped item
     */
    public int getTappedItem(int x, int y) {
        //Calculaye cell width and height
        int cellWidth = canvasWidth / Game.getGrid().getColumns();
        int cellHeight = canvasHeight / Game.getGrid().getRows();
        //Get number of columns and rows
        int columns = Game.getGrid().getColumns();
        int rows = Game.getGrid().getRows();
        //Setup tappedX and tappedY
        int tappedX = 0;
        int tappedY = 0;
        //Define currentColumn and lastColumn
        int currentColumn = 0;
        int lastColumn;
        //Define currentRow and lastRow
        int currentRow = 0;
        int lastRow;


        //Determine which column has been tapped
        for (int i = 0; i <= columns; i++) {
            //Add 1 to current column and set last column to current column - 1
            //This is used to calculate which row has been tapped
            currentColumn++;
            lastColumn = currentColumn - 1;

            //Set current max and current min based on current column and last column
            //This gives us boundaries for the tap
            int currentMax = currentColumn * cellWidth;
            int currentMin = lastColumn * cellWidth;

            //If tap falls between boundary then set tappedX to i
            if (x <= currentMax && x >= currentMin) {
                tappedX = i;
            }
        }

        //Determine which row has been tapped
        for (int i = 0; i <= rows; i++) {
            //Add 1 to current row and set last row to current row - 1
            //This is used to calculate which column has been tapped
            currentRow++;
            lastRow = currentRow - 1;

            //Set current max and current min based on current row and last row
            //This gives us boundaries for the tap
            int currentMax = currentRow * cellHeight;
            int currentMin = lastRow * cellHeight;

            //If tap falls between boundary then set tappedY to i
            if (y <= currentMax && y >= currentMin) {
                tappedY = i - 1;
            }
        }

        //Define coordinates array, set contents ro tappedX and tappedY
        int[] coordinates = new int[2];
        coordinates[0] = tappedX;
        coordinates[1] = tappedY;

        /*If statements fix a bug where tapping past cells can trigger
        an array out of bounds exception.*/
        if (coordinates[0] == -1) {
            coordinates[0] = 0;
        }
        if (coordinates[0] == columns + 1) {
            coordinates[0] = columns;
        }
        if (coordinates[1] == -1) {
            coordinates[1] = 0;
        }
        if (coordinates[1] == rows + 1) {
            coordinates[1] = rows;
        }

        //Get elementID as int from grid
        int elementID = Game.getGrid().getGridElem(coordinates[0], coordinates[1]);
        //Return the elementID
        return elementID;
    }

    /**
     * Original method of checking for win, leaving in if needed.
     *
     * @return
     */
    public boolean checkWin() {
        //Get columns
        int yCells = Game.getGrid().getColumns();

        int y = 0;
        int x = 0;

        //Compare original grid to current grid array
        for (int i = 0; i < mOriginalGrid.length; i++) {
            //Determine if column needs to be adjusted
            if (x == yCells) {
                y++;
                x = 0;
            }

            //Used for debugging - print array contents
            System.out.println(Game.getGrid().getGridElem(x, y));

            //If grids don't match, return false
            if (Game.getGrid().getGridElem(x, y) != mOriginalGrid[i]) {
                return false;
            }

            //If grids do match and current position in array is 1 less than max position, return true
            if (Game.getGrid().getGridElem(x, y) == mOriginalGrid[i] && i == mOriginalGrid.length - 1) {
                return true;
            }

            //Add 1 to x
            x++;
        }

        //Default return statement in-case above criteria isn't met
        return false;
    }

    /**
     * Used to randomly scramble the grid during gameplay.
     */
    public void randomScramble() {
        //Get number of columns
        int yCells = Game.getGrid().getColumns();

        int cellYNumber = 0;
        int cellXNumber = 0;

        //Loop through grid
        for (int i = 0; i < mOriginalGrid.length; i++) {
            //Determine if column needs to be adjusted
            if (cellXNumber == yCells) {
                cellYNumber++;
                cellXNumber = 0;
            }

            //Scramble the current element
            scrambleElement(cellXNumber, cellYNumber);

            //Add 1 to cellXNumber
            cellXNumber++;
        }
    }

}

