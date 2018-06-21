package hu.ait.android.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by mayavarghese on 3/6/18.
 */

public class MinesweeperView extends View {

    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintMine;
    private Paint paintFont;
    private Paint paintFlag;
    public boolean flagMode = false;
    private boolean gameOver = false;
    private int numFlags = 3;


    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        paintMine = new Paint();
        paintMine.setColor(Color.BLACK);
        paintMine.setStyle(Paint.Style.FILL);

        paintFlag = new Paint();
        paintFlag.setColor(Color.RED);
        paintFlag.setStyle(Paint.Style.STROKE);
        paintFlag.setStrokeWidth(5);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);

        paintFont = new Paint();
        paintFont.setColor(Color.BLUE);
        paintFont.setTextSize(100);

        MinesweeperModel.getInstance().setUpGame();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;    //if width and height diff use smaller
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBackground);
        drawGameArea(canvas);


        drawFieldElements(canvas);


    }

    private void drawGameArea(Canvas canvas) {
        // four horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // four vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    private void drawFieldElements(Canvas canvas) {
        Field[][] model = MinesweeperModel.getInstance().getModel();

        for(int row = 0; row < 5; row++) {
            for(int col = 0; col < 5; col++) {
                if(model[row][col].isUnCovered()) {
                    if(model[row][col].getType() == MinesweeperModel.MINE) {
                        float centerX = row * getWidth() / 5 + getWidth() / 10;
                        float centerY = col * getHeight() / 5 + getHeight() / 10;
                        int radius = getHeight() / 10 - 2;
                        canvas.drawCircle(centerX, centerY, radius, paintMine);
                    } else {
                        float centerX = row * getWidth() / 5 + getWidth() / 10 ;
                        float centerY = (col * getHeight() + 1)/ 5 + getWidth() / 10;
                        canvas.drawText(Integer.toString(model[row][col].getMinesAround()),
                                centerX, centerY, paintFont);
                    }
                } else if (model[row][col].isFlagged()) {
                    canvas.drawLine(row * getWidth() / 5, col * getHeight() / 5,
                            (row + 1) * getWidth() / 5,
                            (col + 1) * getHeight() / 5, paintFlag);

                    canvas.drawLine((row + 1) * getWidth() / 5, col * getHeight() / 5,
                            row * getWidth() / 5,
                            (col + 1) * getHeight() / 5, paintFlag);
                }

            }
        }
    }

    private boolean won() {
        Field[][] board = MinesweeperModel.getInstance().getModel();
        for(int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if(board[row][col].isFlagged()) {
                    if(board[row][col].getType() != MinesweeperModel.MINE) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        MinesweeperModel board = MinesweeperModel.getInstance();

        if(gameOver) {
            board.setUpGame();
            gameOver = false;
            numFlags = 3;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

                int touchX = ((int) event.getX() / (getWidth()/5));
                int touchY = ((int) event.getY() / (getHeight()/5));

            updateGameBoard(board, touchX, touchY);
        }



        invalidate();

        return true;


    }

    private void updateGameBoard(MinesweeperModel board, int touchX, int touchY) {
        if(flagMode) {
            if(!board.getModel()[touchX][touchY].isFlagged() &&
                    !board.getModel()[touchX][touchY].isUnCovered()) {
                board.getModel()[touchX][touchY].setFlagged(true);
                numFlags--;
                if(numFlags == 0) {
                    gameOver = true;
                    if(won()) {
                        //I know I have not extracted the strings here. I keep getting errors when I try
                        ((GameActivity) getContext()).showMessage("Congratulations! You won!");
                    } else {
                        ((GameActivity) getContext()).showMessage("Game over! You're out of flags");
                    }

                }
            } else {
                board.getModel()[touchX][touchY].setFlagged(false);
                numFlags++;
            }
        } else {
            board.getModel()[touchX][touchY].setUnCovered(true);
            if(board.getModel()[touchX][touchY].getType() == MinesweeperModel.MINE) {
                gameOver = true;
                ((GameActivity) getContext()).showMessage("Game over You stepped on a mine!");
            }
        }
    }


}
