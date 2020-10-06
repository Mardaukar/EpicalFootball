package com.example.epicalfootball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.example.epicalfootball.Constants.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint;
    private Paint clearPaint;
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

    public void drawOnSurface() {
        float surfaceWidth = this.getWidth();
        float surfaceHeight = this.getHeight();
        Player player = gameState.getPlayer();
        GoalFrame goalFrame = gameState.getGoalFrame();
        float pixelPerMeter = surfaceWidth / FIELD_WIDTH;

        Canvas canvas = surfaceHolder.lockCanvas();

        //CLEAR FIELD SURFACE
        canvas.drawRect(0, 0, surfaceWidth, surfaceWidth * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, clearPaint);

        //DRAW BALL SHADOW
        paint.setColor(Color.BLACK);
        paint.setAlpha(SHADOW_ALPHA);
        Position ballPosition = gameState.getBall().getPosition();
        drawPositionX = ballPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
        drawPositionY = ballPosition.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter;
        canvas.drawCircle(drawPositionX  + surfaceWidth * SHADOW_OFFSET, drawPositionY + surfaceWidth * SHADOW_OFFSET, pixelPerMeter * BALL_RADIUS, paint);

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

        //DRAW GOAL
        paint.setColor(Color.WHITE);
        Circle leftPost = goalFrame.getLeftPost();
        Circle rightPost = goalFrame.getRightPost();
        Circle leftSupport = goalFrame.getLeftSupport();
        Circle rightSupport = goalFrame.getRightSupport();
        RectF leftNet = goalFrame.getLeftNet();
        RectF rightNet = goalFrame.getRightNet();
        RectF rearNet = goalFrame.getRearNet();
        canvas.drawCircle(leftPost.getX() * pixelPerMeter + surfaceWidth * HALF, leftPost.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, leftPost.getRadius() * pixelPerMeter, paint);
        canvas.drawCircle(rightPost.getX() * pixelPerMeter + surfaceWidth * HALF, rightPost.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, rightPost.getRadius() * pixelPerMeter, paint);
        canvas.drawCircle(leftSupport.getX() * pixelPerMeter + surfaceWidth * HALF, leftSupport.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, leftSupport.getRadius() * pixelPerMeter, paint);
        canvas.drawCircle(rightSupport.getX() * pixelPerMeter + surfaceWidth * HALF, rightSupport.getY() * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, rightSupport.getRadius() * pixelPerMeter, paint);
        canvas.drawRect(leftNet.left * pixelPerMeter + surfaceWidth * HALF, leftNet.top * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, leftNet.right * pixelPerMeter + surfaceWidth * HALF, leftNet.bottom * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, paint);
        canvas.drawRect(rightNet.left * pixelPerMeter + surfaceWidth * HALF, rightNet.top * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, rightNet.right * pixelPerMeter + surfaceWidth * HALF, rightNet.bottom * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, paint);
        canvas.drawRect(rearNet.left * pixelPerMeter + surfaceWidth * HALF, rearNet.top * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, rearNet.right * pixelPerMeter + surfaceWidth * HALF, rearNet.bottom * pixelPerMeter + TOUCHLINE_FROM_TOP * pixelPerMeter, paint);

        //CONTROL AREA
        canvas.drawRect(0, surfaceWidth * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, surfaceWidth, surfaceHeight, clearPaint);

        if (gameState.isShootButtonDown()) {
            //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.)
            //canvas.drawBitmap(bm, 0, 0, null);
            Drawable targetGoalImage = getResources().getDrawable(R.drawable.football_goal, null);

            if (gameState.getTargetGoal() != null) {
                float targetGoalLeft = CONTROL_AREA_LEFT_FROM_LEFT + gameState.getTargetGoal().getPositionX() * CONTROL_AREA_FROM_WIDTH;
                float targetGoalTop = CONTROL_AREA_TOP_FROM_TOP + gameState.getTargetGoal().getPositionY() * CONTROL_AREA_FROM_HEIGHT;
                float targetGoalRight = targetGoalLeft + gameState.getTargetGoal().getSize() * CONTROL_AREA_FROM_WIDTH;
                float targetGoalBottom = targetGoalTop + gameState.getTargetGoal().getSize() / 3 * CONTROL_AREA_FROM_HEIGHT;

                Log.d("DRAW", "" + targetGoalLeft + " " + targetGoalTop + " " + targetGoalRight + " " + targetGoalBottom);

                targetGoalImage.setBounds((int) (targetGoalLeft * surfaceWidth), (int)(targetGoalTop * surfaceHeight), (int)(targetGoalRight * surfaceWidth), (int)(targetGoalBottom * surfaceHeight));
                //targetGoalImage.setBounds(0, 0, 50, 50);
                targetGoalImage.draw(canvas);
                //canvas.drawCircle(surfaceWidth * HALF, surfaceHeight * CONTROL_AREA_CENTER_FROM_TOP, DECELERATE_DOT_RADIUS, paint);
            }
        } else {
            paint.setColor(Color.BLUE);

            if (gameState.isDecelerateOn()) {
                paint.setAlpha(DECELERATE_ON_ALPHA);
            } else {
                paint.setAlpha(DECELERATE_OFF_ALPHA);
            }

            canvas.drawCircle(surfaceWidth * HALF, surfaceHeight * CONTROL_AREA_CENTER_FROM_TOP, DECELERATE_DOT_RADIUS_OF_CONTROL_SURFACE * CONTROL_AREA_FROM_WIDTH * surfaceWidth, paint);

            paint.setColor(Color.MAGENTA);
            paint.setAlpha(CONTROL_DOT_ALPHA);

            if (gameState.isControlOn()) {
                float controlX = gameState.getControlX();
                float controlY = gameState.getControlY();
                float controlWidth = gameState.getControlWidth();
                canvas.drawCircle((HALF + controlX / controlWidth * CONTROL_AREA_FROM_WIDTH) * surfaceWidth, (1 + HALF + controlY / controlWidth) * CONTROL_AREA_FROM_WIDTH * surfaceWidth, CONTROL_DOT_RADIUS_OF_CONTROL_SURFACE * CONTROL_AREA_FROM_WIDTH * surfaceWidth, paint);
            }
        }

        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
