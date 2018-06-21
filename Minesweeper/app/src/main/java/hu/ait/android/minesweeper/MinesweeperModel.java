package hu.ait.android.minesweeper;

import android.util.Log;

import java.util.Random;

/**
 * Created by mayavarghese on 3/8/18.
 */

public class MinesweeperModel {

    private static MinesweeperModel instance = null;
    private static int FIELD_LENGTH = 5;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }

        return instance;
    }

    private MinesweeperModel() {
    }

    public static final int SAFE = 0;
    public static final int MINE = 1;

    private Field[][] model = new Field[5][5];

    private int[] mineIndices = new int[6];

    public Field[][] getModel() {
        return model;
    }

    public boolean inBounds(int row, int column) {
        Log.i("debug", "in inbounds");
        if(row >= 0 && row < FIELD_LENGTH && column >= 0 && column < FIELD_LENGTH) {
            Log.i("debug", "in if");
            return true;
        }

        return false;
    }

    public void setMinesAround(int row, int col) {
        int minesAround = 0;
        if(inBounds(row-1, col - 1) &&
                model[row - 1][col - 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row-1, col) &&
                model[row - 1][col].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row-1, col + 1) &&
                model[row - 1][col + 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row, col - 1) &&
                model[row][col - 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row, col + 1) &&
                model[row][col + 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row + 1, col - 1) &&
                model[row + 1][col - 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row + 1, col) &&
                model[row + 1][col].getType() == MinesweeperModel.MINE) {
            minesAround++;
        } if(inBounds(row + 1, col + 1) &&
                model[row + 1][col + 1].getType() == MinesweeperModel.MINE) {
            minesAround++;
        }

        model[row][col].setMinesAround(minesAround);


    }

    public void setUpGame() {
        Random rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j]=new Field();
            }
        }

        for(int i = 0; i < 3; i++) {
            int row = rand.nextInt(5);
            int column = rand.nextInt(5);
            model[row][column].setType(MINE);
            mineIndices[2*i] = row;
            mineIndices[2*i + 1] = column;
        }

        for(int row = 0; row < FIELD_LENGTH; row++) {
            for (int col = 0; col < FIELD_LENGTH; col++) {
                if(model[row][col].getType() != MINE) {
                    model[row][col].setType(SAFE);
                    setMinesAround(row, col);
                }
            }
        }
    }



}
