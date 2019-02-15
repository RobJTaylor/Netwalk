package com.example.robta.netwalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A grid representation for the netwalk game
 */
public class NetwalkGrid {
    public static final int CONNECTORMASK = 0b1111;
    public static final int PROPMASK = ~0b1111;

    /**
     * Bit value to indicate a connected node
     */
    public static final int CONNECTED = 0b1000000;
    /**
     * Bit value to indicate a node
     */
    public static final int NODE = 0b100000;
    /**
     * Bit value to indicate the server
     */
    public static final int SERVER = 0b10000;
    /**
     * Bit value to indicate a connector to the left
     */
    public static final int LEFT = 0b1000;
    /**
     * Bit value to indicate a connector upwards
     */
    public static final int UP = 0b100;
    /**
     * Bit value to indicate a connector to the right
     */
    public static final int RIGHT = 0b10;
    /**
     * Bit value to indicate a connector downwards
     */
    public static final int DOWN = 0b1;
    private static final int[] ROTATE_RIGHT_TABLE =
            {
                    0b0000,       //0b0000
                    0b1000,       //0b0001
                    0b0001,       //0b0010
                    0b1001,       //0b0011
                    0b0010,       //0b0100
                    0b1010,       //0b0101
                    0b0011,       //0b0110
                    0b1011,       //0b0111
                    0b0100,       //0b1000
                    0b1100,       //0b1001
                    0b0101,       //0b1010
                    0b1101,       //0b1011
                    0b0110,       //0b1100
                    0b1110,       //0b1101
                    0b0111,       //0b1110
                    0b1111,       //0b1111
            };

    /**
     * The actual grid as a flat array
     */
    private final int[] mGrid;

    /**
     * The width of the grid
     */
    private final int mColumns;
    /**
     * The height of the grid
     */
    private final int mRows;
    private final int mServerPos;


    public NetwalkGrid(int columns, int rows) {
        this.mColumns = columns;
        this.mRows = rows;
        mGrid = new int[columns * rows];

        mServerPos = generate();
    }

    private int generate() {
        Random random = new Random();

        // First determine a random position for the server
        int serverPos = random.nextInt(mGrid.length);
        mGrid[serverPos] = SERVER | CONNECTED; // The server is always connected

        // The leaves list contains positions that may have a connector added.
        List<Integer> leaves = new ArrayList<>();
        leaves.add(serverPos);

        // As long as there is a place where we can add a connector
        while (leaves.size() > 0) {
            // Determine which element of the list to connect to
            int leavePos = random.nextInt(leaves.size());
            // The actual position for this element
            int leave = leaves.get(leavePos);

            // Find a connecting position
            int nextPos = nextPosFrom(leave, random);
            // Do the actual connecting (set the bits in both origin and destination)
            connect(leave, nextPos);

            // Make sure that all leaves in the list are still valid (still have an empty neighbor)
            for (int i = leaves.size() - 1; i >= 0; --i) {
                int leaf = leaves.get(i);
                if (!canExtendFrom(leaf)) {
                    leaves.remove(i);
                }
            }

            // Add this new cell if it has empty neighbors
            if (canExtendFrom(nextPos)) {
                leaves.add(nextPos);
            }

        }

        // Those cells with only a single connector are nodes/terminals so mark them
        for (int i = mGrid.length - 1; i >= 0; --i) {
            switch (mGrid[i]) { // the server has an extra bit so will not match anyway
                case LEFT:
                case RIGHT:
                case UP:
                case DOWN:
                    notifyChange();
                    mGrid[i] |= NODE;
            }
        }

        return serverPos;
    }

    public boolean canExtendFrom(final int pos) {
        switch (mGrid[pos] & CONNECTORMASK) { // don't allow more than 3 connections, just hardcode the 4 options
            case 0b1011:
            case 0b111:
            case 0b1101:
            case 0b1110:
                return false;
        }
        int x = x(pos);
        int y = y(pos);
        // At least one of the neighbors must be empty
        return (x > 0 && mGrid[pos - 1] == 0) ||
                (x + 1 < mColumns && mGrid[pos + 1] == 0) ||
                (y > 0 && mGrid[pos - mColumns] == 0) ||
                (y + 1 < mRows && mGrid[pos + mColumns] == 0);
    }

    /**
     * Connect the two positions
     *
     * @param pos1 The first position
     * @param pos2 The second position
     */
    private void connect(final int pos1, final int pos2) {
        if (pos1 > pos2) { // If the first position is to the bottom right of the second, just reverse the parameters
            connect(pos2, pos1);
            return;
        }
        // The grid works such that horizontal cells are adjacent, we know that pos2 is to the right
        if (pos2 - pos1 == 1) {
            mGrid[pos1] |= RIGHT;
            mGrid[pos2] |= LEFT;
        } else {
            mGrid[pos1] |= DOWN;
            mGrid[pos2] |= UP;
        }
        // Call the hook that can be used to redraw
        notifyChange();
    }

    /**
     * Hook function that may be used to handle changes.
     */
    protected void notifyChange() {

    }

    public void rotateRight(int col, int row) {
        final int gridPos = gridPos(col, row);
        final int extraBits = mGrid[gridPos] & PROPMASK;
        mGrid[gridPos] = extraBits | ROTATE_RIGHT_TABLE[mGrid[gridPos] & CONNECTORMASK];
        notifyChange();
    }

    /**
     * Helper function to get the x coordinate of a position in the array.
     *
     * @param gridPos Position in the array
     * @return The x coordinate
     */
    private int x(int gridPos) {
        return gridPos % mColumns;
    }

    /**
     * Helper function to get the y coordinate of a position in the array.
     *
     * @param gridPos Position in the array
     * @return The y coordinate
     */
    private int y(int gridPos) {
        return gridPos / mColumns;
    }

    /**
     * Helper function to get the array position of a coordinate pair.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return The position in the array
     */
    private int gridPos(int x, int y) {
        return y * mColumns + x;
    }

    /**
     * Function that finds a cell to connect to the base position
     *
     * @param basePos The position to connect from
     * @param random  The random generator to use
     * @return A new gridposition for a cell
     */
    private int nextPosFrom(final int basePos, final Random random) {
        int baseX = x(basePos); // Get the X and Y coordinates
        int baseY = y(basePos);

        while (true) { // until we have a valid position
            int direction = random.nextInt(4); // 4 directions
            int x = baseX;
            int y = baseY;
            switch (direction) {
                case 0:
                    x++;
                    break;
                case 1:
                    y++;
                    break;
                case 2:
                    x--;
                    break;
                case 3:
                    y--;
                    break;
            }
            // If the new position is free (and valid) return it as a grid position.
            if (isFreePos(x, y)) {
                return gridPos(x, y);
            }
        }
    }

    /**
     * Determine whether the coordinates represent a valid free position
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Position is valid and empty
     */
    private boolean isFreePos(int x, int y) {
        return !(x < 0 || y < 0 || x >= mColumns || y >= mRows) && mGrid[gridPos(x, y)] == 0;
    }

    /**
     * Getter for the amount of columns in the grid
     *
     * @return The amount of columns
     */
    public int getColumns() {
        return mColumns;
    }

    /**
     * Getter for the amount of rows in the grid
     *
     * @return The amount of rows
     */
    public int getRows() {
        return mRows;
    }

    /**
     * Get the value for the particular grid element. This is for users of the class (usage of a 1dim array is an implementation detail)
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return The value
     */
    public int getGridElem(int x, int y) {
        if (x < 0 || y < 0 || x >= mColumns || y >= mRows) {
            throw new IndexOutOfBoundsException("The coordinates (" + x + ", " + y + ") are not valid.");
        }
        return mGrid[gridPos(x, y)];
    }

    /**
     * Helper function to determine whether the position contains the server
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return If the coordinate contains the server
     */
    public boolean isServer(int x, int y) {
        int gridpos = gridPos(x, y);
        return (mGrid[gridpos] & SERVER) != 0;
    }

    /**
     * Helper function to determine whether the position contains a terminal node
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return If the coordinate is a terminal node
     */
    public boolean isNode(int x, int y) {
        int gridpos = gridPos(x, y);
        return (mGrid[gridpos] & NODE) != 0;
    }

}