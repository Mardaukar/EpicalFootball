package com.example.epicalfootball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint = new Paint();
    private GameState gameState;
    private GameRunner gameRunner;

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

    public void updateSurface() {
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRect(0, 0, this.getWidth(), this.getWidth() * 0.8f, clearPaint);

        float pixelPerMeter = this.getWidth() / 35.294f;

        paint.setColor(Color.WHITE);
        Position ballPosition = gameState.getBall().getPosition();
        float drawPositionX = ballPosition.getX() * pixelPerMeter + this.getWidth() / 2f;
        float drawPositionY = ballPosition.getY() * pixelPerMeter + this.getWidth() / 375f * 30;
        //canvas.drawRect(drawPositionX - pixelPerMeter / 4, drawPositionY - pixelPerMeter / 4, drawPositionX + pixelPerMeter / 4, drawPositionY + pixelPerMeter / 4, paint);
        canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * 0.4f, paint);

        Position playerPosition = gameState.getPlayer().getPosition();
        drawPositionX = playerPosition.getX() * pixelPerMeter + this.getWidth() / 2f;
        drawPositionY = playerPosition.getY() * pixelPerMeter + this.getWidth() / 375f * 30;
        paint.setColor(0Xff004d98);
        //canvas.drawRect(drawPositionX - pixelPerMeter / 2, drawPositionY - pixelPerMeter / 2, drawPositionX + pixelPerMeter / 2, drawPositionY + pixelPerMeter / 2, paint);
        canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * 0.8f, paint);

        paint.setColor(0xffffad60);
        canvas.drawArc(drawPositionX - pixelPerMeter * 0.8f, drawPositionY - pixelPerMeter * 0.8f, drawPositionX + pixelPerMeter * 0.8f, drawPositionY + pixelPerMeter * 0.8f, -90, 120, true, paint);


        canvas.drawRect(0, this.getWidth() * 0.8f, this.getWidth(), this.getHeight(), clearPaint);

        if (gameState.isDecelerateOn()) {
            paint.setColor(Color.BLUE);
            paint.setAlpha(100);
        } else {
            paint.setColor(Color.BLUE);
            paint.setAlpha(50);
        }
        canvas.drawCircle(this.getWidth() / 2f, this.getHeight() * 3 / 4, 90, paint);

        paint.setColor(Color.MAGENTA);
        paint.setAlpha(50);

        if (gameState.isControlOn()) {
            float controlX = gameState.getControlX();
            float controlY = gameState.getControlY();
            float centerSideDistance = gameState.getCenterSideDistance();
            canvas.drawCircle((0.125f + controlX / centerSideDistance / 2) * this.getWidth() * 0.8f, (1 + controlY / centerSideDistance / 2) * this.getWidth() * 0.8f, 80, paint);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
