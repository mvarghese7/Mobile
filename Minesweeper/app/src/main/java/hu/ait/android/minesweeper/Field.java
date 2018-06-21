package hu.ait.android.minesweeper;

/**
 * Created by mayavarghese on 3/6/18.
 */

public class Field {

    private int type;
    private int minesAround = 0;
    private boolean isFlagged;
    private boolean unCovered;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int x) {
        this.minesAround = x;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isUnCovered() {
        return unCovered;
    }

    public void setUnCovered(boolean unCovered) {
        this.unCovered = unCovered;
    }
}
