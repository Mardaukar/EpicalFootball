package com.example.epicalfootball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = new Paint();
    private GameState gameState;
    private GameRunner gameRunner;
    private float circleX = 0;
    private float circleY = 0;

    public GameView (Context context, GameState gameState) {
        super(context);

        this.gameState = gameState;

        //What is this?
        setFocusable(true);

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

        if(paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
        }

        // Set current surfaceview at top of the view tree. What is this?
        this.setZOrderOnTop(true);

        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameRunner = new GameRunner(this, gameState);
        gameRunner.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (gameRunner != null) {
            gameRunner.shutdown();

            while (gameRunner != null) {
                try {
                    gameRunner.join();
                    gameRunner = null;
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void updateSurface()
    {
        Log.d("GameView", "Start draw");
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), clearPaint);

        paint.setColor(Color.YELLOW);

        Position position = gameState.getPlayer().getPosition();
        float drawPositionX = position.getX() * this.getWidth() / 25f + this.getWidth()/2;
        float drawPositionY = position.getY() * this.getWidth() * 0.8f / 20f + this.getHeight() / 20;

        canvas.drawRect(drawPositionX - 30, drawPositionY - 30, drawPositionX + 30, drawPositionY + 30, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);

        Log.d("GameView", "End draw");
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
