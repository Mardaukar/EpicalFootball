package com.example.epicalfootball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = new Paint();
    private GameState gameState;
    private float circleX = 0;
    private float circleY = 0;
/*
    public GameView (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setFocusable(true);

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

        if(paint == null)
        {
            paint = new Paint();

            paint.setColor(Color.RED);
        }

        // Set current surfaceview at top of the view tree.
        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }*/

    public GameView (Context context, GameState gameState) {
        super(context);

        this.gameState = gameState;

        setFocusable(true);

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

        if(paint == null)
        {
            paint = new Paint();

            paint.setColor(Color.RED);
        }

        // Set current surfaceview at top of the view tree.
        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void drawPlayer(Position position)
    {
        Canvas canvas = surfaceHolder.lockCanvas();

        //Paint surfaceBackground = new Paint();
        // Set the surfaceview background color.
        //surfaceBackground.setColor(Color.BLUE);
        // Draw the surfaceview background color.
        //canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), surfaceBackground);
        paint.setColor(Color.YELLOW);

        canvas.drawRect(position.getX(), position.getY(), position.getX() + 200, position.getY() + 200, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public float getCircleX() {
        return circleX;
    }

    public void setCircleX(float circleX) {
        this.circleX = circleX;
    }

    public float getCircleY() {
        return circleY;
    }

    public void setCircleY(float circleY) {
        this.circleY = circleY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
