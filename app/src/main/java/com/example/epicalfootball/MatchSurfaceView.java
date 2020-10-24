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

import com.example.epicalfootball.items.Circle;
import com.example.epicalfootball.items.GoalFrame;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.items.OutfieldPlayer;
import com.example.epicalfootball.math.Position;

import static com.example.epicalfootball.Constants.*;

public class MatchSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private Paint paint;
    private Paint clearPaint;
    private float drawPositionX;
    private float drawPositionY;

    private MatchState matchState;
    private MatchRunner matchRunner;

    public MatchSurfaceView(Context context, MatchState matchState) {
        super(context);

        paint = new Paint();
        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.matchState = matchState;

        if(surfaceHolder == null) {
            surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
        }

        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        matchRunner = new MatchRunner(this, matchState);
        matchRunner.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (matchRunner != null) {
            matchRunner.shutdown();

            while (matchRunner != null) {
                try {
                    matchRunner.join();
                    matchRunner = null;
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void drawOnSurface() {
        float surfaceWidth = this.getWidth();
        float surfaceHeight = this.getHeight();
        OutfieldPlayer outfieldPlayer = matchState.getOutfieldPlayer();
        Goalkeeper goalkeeper = matchState.getGoalkeeper();
        GoalFrame goalFrame = matchState.getGoalFrame();
        float pixelPerMeter = surfaceWidth / FIELD_WIDTH;
        float touchlineFromTop = TOUCHLINE_FROM_TOP * pixelPerMeter;

        Canvas canvas = surfaceHolder.lockCanvas();

        if (canvas != null) {
            //CLEAR FIELD SURFACE
            canvas.drawRect(0, 0, surfaceWidth, surfaceWidth * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, clearPaint);

            //DRAW SHADOWS
            paint.setColor(Color.BLACK);
            paint.setAlpha(SHADOW_ALPHA);
            float shadowOffset = surfaceWidth * SHADOW_OFFSET;

            //DRAW BALL SHADOW
            Position ballPosition = matchState.getBall().getPosition();
            drawPositionX = ballPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = ballPosition.getY() * pixelPerMeter + touchlineFromTop;
            canvas.drawCircle(drawPositionX + shadowOffset, drawPositionY + shadowOffset, pixelPerMeter * BALL_RADIUS, paint);

            //DRAW GOALKEEPER SHADOW
            Position goalkeeperPosition = goalkeeper.getPosition();
            drawPositionX = goalkeeperPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = goalkeeperPosition.getY() * pixelPerMeter + touchlineFromTop;
            canvas.drawCircle(drawPositionX + shadowOffset, drawPositionY + shadowOffset, pixelPerMeter * goalkeeper.getRadius(), paint);

            //DRAW OUTFIELD PLAYER SHADOW
            Position outfieldPlayerPosition = outfieldPlayer.getPosition();
            drawPositionX = outfieldPlayerPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = outfieldPlayerPosition.getY() * pixelPerMeter + touchlineFromTop;
            canvas.drawCircle(drawPositionX + shadowOffset, drawPositionY + shadowOffset, pixelPerMeter * outfieldPlayer.getRadius(), paint);

            //DRAW GOAL SHADOW
            Circle leftPost = goalFrame.getLeftPost();
            Circle rightPost = goalFrame.getRightPost();
            Circle leftSupport = goalFrame.getLeftSupport();
            Circle rightSupport = goalFrame.getRightSupport();
            RectF leftNet = goalFrame.getLeftNet();
            RectF rightNet = goalFrame.getRightNet();
            RectF rearNet = goalFrame.getRearNet();
            canvas.drawCircle(leftPost.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF + shadowOffset, leftPost.getPosition().getY() * pixelPerMeter + touchlineFromTop + shadowOffset, leftPost.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(rightPost.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rightPost.getPosition().getY() * pixelPerMeter + touchlineFromTop + shadowOffset, rightPost.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(leftSupport.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF + shadowOffset, leftSupport.getPosition().getY() * pixelPerMeter + touchlineFromTop + shadowOffset, leftSupport.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(rightSupport.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rightSupport.getPosition().getY() * pixelPerMeter + touchlineFromTop + shadowOffset, rightSupport.getRadius() * pixelPerMeter, paint);
            canvas.drawRect(leftNet.left * pixelPerMeter + surfaceWidth * HALF + shadowOffset, leftNet.top * pixelPerMeter + touchlineFromTop + shadowOffset, leftNet.right * pixelPerMeter + surfaceWidth * HALF + shadowOffset, leftNet.bottom * pixelPerMeter + touchlineFromTop + shadowOffset, paint);
            canvas.drawRect(rightNet.left * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rightNet.top * pixelPerMeter + touchlineFromTop + shadowOffset, rightNet.right * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rightNet.bottom * pixelPerMeter + touchlineFromTop + shadowOffset, paint);
            canvas.drawRect(rearNet.left * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rearNet.top * pixelPerMeter + touchlineFromTop + shadowOffset, rearNet.right * pixelPerMeter + surfaceWidth * HALF + shadowOffset, rearNet.bottom * pixelPerMeter + touchlineFromTop + shadowOffset, paint);

            //DRAW BALL
            paint.setColor(Color.WHITE);
            drawPositionX = ballPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = ballPosition.getY() * pixelPerMeter + touchlineFromTop;
            canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * BALL_RADIUS, paint);

            //DRAW GOALKEEPER
            drawPositionX = goalkeeperPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = goalkeeperPosition.getY() * pixelPerMeter + touchlineFromTop;
            paint.setColor(getResources().getColor(R.color.goalkeeperColor));
            canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * goalkeeper.getRadius(), paint);

            //DRAW GOALKEEPER HANDLING SECTOR
            paint.setColor(getResources().getColor(R.color.skinColor));
            float goalkeeperOrientationRadians = goalkeeper.getOrientation();
            float goalkeeperOrientationDegrees = goalkeeperOrientationRadians / HALF_CIRCLE * 180;
            float handlingSectorRadians = goalkeeper.getBallHandlingAngle();
            float handlingSectorDegrees = handlingSectorRadians / HALF_CIRCLE * 180;
            canvas.drawArc(drawPositionX - pixelPerMeter * goalkeeper.getRadius(), drawPositionY - pixelPerMeter * goalkeeper.getRadius(), drawPositionX + pixelPerMeter * goalkeeper.getRadius(), drawPositionY + pixelPerMeter * goalkeeper.getRadius(), goalkeeperOrientationDegrees - handlingSectorDegrees, 2 * handlingSectorDegrees, true, paint);

            //DRAW OUTFIELD PLAYER
            drawPositionX = outfieldPlayerPosition.getX() * pixelPerMeter + surfaceWidth * HALF;
            drawPositionY = outfieldPlayerPosition.getY() * pixelPerMeter + touchlineFromTop;
            paint.setColor(getResources().getColor(R.color.outfieldPlayerColor));
            canvas.drawCircle(drawPositionX, drawPositionY, pixelPerMeter * outfieldPlayer.getRadius(), paint);

            //DRAW OUTFIELD PLAYER CONTROL SECTOR
            paint.setColor(getResources().getColor(R.color.skinColor));
            float playerOrientationRadians = outfieldPlayer.getOrientation();
            float playerOrientationDegrees = playerOrientationRadians / HALF_CIRCLE * 180;
            float controlSectorRadians = outfieldPlayer.getControlAngle();
            float controlSectorDegrees = controlSectorRadians / HALF_CIRCLE * 180;
            canvas.drawArc(drawPositionX - pixelPerMeter * outfieldPlayer.getRadius(), drawPositionY - pixelPerMeter * outfieldPlayer.getRadius(), drawPositionX + pixelPerMeter * outfieldPlayer.getRadius(), drawPositionY + pixelPerMeter * outfieldPlayer.getRadius(), playerOrientationDegrees - controlSectorDegrees, 2 * controlSectorDegrees, true, paint);

            //DRAW GOAL
            paint.setColor(Color.WHITE);
            canvas.drawCircle(leftPost.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF, leftPost.getPosition().getY() * pixelPerMeter + touchlineFromTop, leftPost.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(rightPost.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF, rightPost.getPosition().getY() * pixelPerMeter + touchlineFromTop, rightPost.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(leftSupport.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF, leftSupport.getPosition().getY() * pixelPerMeter + touchlineFromTop, leftSupport.getRadius() * pixelPerMeter, paint);
            canvas.drawCircle(rightSupport.getPosition().getX() * pixelPerMeter + surfaceWidth * HALF, rightSupport.getPosition().getY() * pixelPerMeter + touchlineFromTop, rightSupport.getRadius() * pixelPerMeter, paint);
            canvas.drawRect(leftNet.left * pixelPerMeter + surfaceWidth * HALF, leftNet.top * pixelPerMeter + touchlineFromTop, leftNet.right * pixelPerMeter + surfaceWidth * HALF, leftNet.bottom * pixelPerMeter + touchlineFromTop, paint);
            canvas.drawRect(rightNet.left * pixelPerMeter + surfaceWidth * HALF, rightNet.top * pixelPerMeter + touchlineFromTop, rightNet.right * pixelPerMeter + surfaceWidth * HALF, rightNet.bottom * pixelPerMeter + touchlineFromTop, paint);
            canvas.drawRect(rearNet.left * pixelPerMeter + surfaceWidth * HALF, rearNet.top * pixelPerMeter + touchlineFromTop, rearNet.right * pixelPerMeter + surfaceWidth * HALF, rearNet.bottom * pixelPerMeter + touchlineFromTop, paint);

            //CLEAR CONTROL SURFACE
            canvas.drawRect(0, surfaceWidth * FIELD_IMAGE_HEIGHT_WIDTH_RATIO, surfaceWidth, surfaceHeight, clearPaint);

            if (matchState.isShootButtonDown()) {
                Drawable targetGoalImage = getResources().getDrawable(R.drawable.football_goal, null);

                if (matchState.getTargetGoal() != null) {
                    float targetGoalLeft = CONTROL_AREA_LEFT_FROM_LEFT + matchState.getTargetGoal().getPositionX() * CONTROL_AREA_FROM_WIDTH;
                    float targetGoalTop = CONTROL_AREA_TOP_FROM_TOP + matchState.getTargetGoal().getPositionY() * CONTROL_AREA_FROM_HEIGHT;
                    float targetGoalRight = targetGoalLeft + matchState.getTargetGoal().getSize() * CONTROL_AREA_FROM_WIDTH;
                    float targetGoalBottom = targetGoalTop + matchState.getTargetGoal().getSize() / 3 * CONTROL_AREA_FROM_HEIGHT;

                    targetGoalImage.setBounds((int) (targetGoalLeft * surfaceWidth), (int) (targetGoalTop * surfaceHeight), (int) (targetGoalRight * surfaceWidth), (int) (targetGoalBottom * surfaceHeight));
                    targetGoalImage.draw(canvas);
                }

                if (matchState.isControlOn()) {
                    paint.setColor(Color.WHITE);
                    paint.setAlpha(TARGET_DOT_ALPHA);
                    float controlX = matchState.getControlX();
                    float controlY = matchState.getControlY();
                    canvas.drawCircle(((controlX - HALF) * AIMING_TARGET_MULTIPLIER * CONTROL_AREA_FROM_WIDTH + HALF) * surfaceWidth, ((controlY - FULL) * AIMING_TARGET_MULTIPLIER * CONTROL_AREA_FROM_HEIGHT + 1) * surfaceHeight, outfieldPlayer.getAccuracyTargetDot() * CONTROL_AREA_FROM_WIDTH * surfaceWidth, paint);
                }
            } else {
                paint.setColor(Color.BLUE);

                if (outfieldPlayer.isDecelerateOn()) {
                    paint.setAlpha(DECELERATE_ON_ALPHA);
                } else {
                    paint.setAlpha(DECELERATE_OFF_ALPHA);
                }

                canvas.drawCircle(surfaceWidth * HALF, surfaceHeight * CONTROL_AREA_CENTER_FROM_TOP, DECELERATE_DOT_RADIUS_OF_CONTROL_SURFACE * CONTROL_AREA_FROM_WIDTH * surfaceWidth, paint);

                if (matchState.isControlOn()) {
                    paint.setColor(Color.MAGENTA);

                    if (outfieldPlayer.getTargetSpeed().getMagnitude() < 1) {
                        paint.setAlpha(CONTROL_DOT_ALPHA);
                    } else {
                        paint.setAlpha(CONTROL_DOT_ALPHA + 100);
                    }

                    float controlX = matchState.getControlX();
                    float controlY = matchState.getControlY();
                    canvas.drawCircle((HALF + (controlX - HALF) * CONTROL_AREA_FROM_WIDTH) * surfaceWidth, (1 + HALF + (controlY - HALF)) * CONTROL_AREA_FROM_WIDTH * surfaceWidth, CONTROL_DOT_RADIUS_OF_CONTROL_SURFACE * CONTROL_AREA_FROM_WIDTH * surfaceWidth, paint);
                }
            }

            if (matchState.getShotPowerMeter() > SHOT_POWER_METER_OPTIMAL) {
                paint.setColor(Color.RED);

                if (matchState.getShotPowerMeter() >= SHOT_POWER_METER_HIGHER_LIMIT) {
                    paint.setAlpha(FULL_ALPHA);
                    Log.d("Over limit ","" + matchState.getShotPowerMeter());
                } else {
                    Log.d("Under limit ","" + matchState.getShotPowerMeter());
                    paint.setAlpha((int)(FAILED_SHOT_BASE_ALPHA + FAILED_SHOT_INCREMENTAL_ALPHA * (matchState.getShotPowerMeter() - SHOT_POWER_METER_OPTIMAL) / (SHOT_POWER_METER_HIGHER_LIMIT - SHOT_POWER_METER_OPTIMAL)));
                }

                canvas.drawRect(0, CONTROL_AREA_TOP_FROM_TOP * surfaceHeight, surfaceWidth * CONTROL_AREA_LEFT_FROM_LEFT, surfaceHeight, paint);
                canvas.drawRect(CONTROL_AREA_RIGHT_FROM_LEFT * surfaceWidth, CONTROL_AREA_TOP_FROM_TOP * surfaceHeight, surfaceWidth, surfaceHeight, paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
