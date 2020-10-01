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

import static com.example.epicalfootball.Constants.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint;
    private Paint clearPaint;
    private float surfaceWidth;
    private float drawPositionX;
    private float drawPositionY;

    private GameState gameState;
    private GameRunner gameRunner;

    public GameView (Context context, GameState gameState) {
        super(context);

        paint = new Paint();
        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        this.gameState = gameState;

        //What is this?
        //setFocusable(true);

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

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
        canvas.drawRect(0, 0, this.getWidth(), this.getWidth() * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, clearPaint);
        float pixelPerMeter = this.getWidth() / FIELD_WIDTH;
        surfaceWidth = this.getWidth();
        Player player = gameState.getPlayer();

        //DRAW BALL SHADOW
        paint.setColor(Color.BLACK);
        paint.setAlpha(SHADOW_ALPHA);
        Position ballPosition = gameState.getBall().getPosition();
        drawPositionX = ballPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
        drawPositionY = ballPosition.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter;
        canvas.drawCircle(drawPositionX  + SHADOW_OFFSET, drawPositionY + SHADOW_OFFSET, pixelPerMeter * BALL_RADIUS, paint);

        //DRAW PLAYER SHADOW
        Position playerPosition = player.getPosition();
        drawPositionX = playerPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
        drawPositionY = playerPosition.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter;
        canvas.drawCircle(drawPositionX + SHADOW_OFFSET, drawPositionY + SHADOW_OFFSET, pixelPerMeter * player.getRadius(), paint);

        //DRAW BALL
        paint.setColor(Color.WHITE);
        drawPositionX = ballPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
        drawPositionY = ballPosition.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter;
        canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * BALL_RADIUS, paint);

        //DRAW PLAYER
        drawPositionX = playerPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
        drawPositionY = playerPosition.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter;
        paint.setColor(getResources().getColor(R.color.playerColor));
        canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * player.getRadius(), paint);

        //DRAW CONTROL SECTOR
        paint.setColor(getResources().getColor(R.color.skinColor));
        float directionRadians = player.getTargetSpeed().getDirection();
        float directionDegrees = directionRadians / (float)Math.PI * 180;
        float controlConeRadians = player.getControlAngle();
        float controlConeDegrees = controlConeRadians / (float)Math.PI * 180;
        canvas.drawArc(drawPositionX - pixelPerMeter * player.getRadius(), drawPositionY - pixelPerMeter * player.getRadius(), drawPositionX + pixelPerMeter * player.getRadius(), drawPositionY + pixelPerMeter * player.getRadius(), directionDegrees - controlConeDegrees, 2 * controlConeDegrees, true, paint);

        canvas.drawRect(0, this.getWidth() * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, this.getWidth(), this.getHeight(), clearPaint);

        paint.setColor(Color.BLUE);

        if (gameState.isDecelerateOn()) {
            paint.setAlpha(DECELERATE_ON_ALPHA);
        } else {
            paint.setAlpha(DECELERATE_OFF_ALPHA);
        }

        canvas.drawCircle(this.getWidth() * HALF, this.getHeight() * CONTROL_AREA_CENTER_FROM_TOP, DECELERATE_DOT_RADIUS, paint);

        paint.setColor(Color.MAGENTA);
        paint.setAlpha(CONTROL_DOT_ALPHA);

        if (gameState.isControlOn()) {
            float controlX = gameState.getControlX();
            float controlY = gameState.getControlY();
            float controlWidth = gameState.getControlWidth();
            canvas.drawCircle((HALF + controlX / controlWidth * CONTROL_AREA_FROM_WIDTH) * this.getWidth(), (1 + HALF + controlY / controlWidth) * CONTROL_AREA_FROM_WIDTH * this.getWidth(), CONTROL_DOT_RADIUS, paint);
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
