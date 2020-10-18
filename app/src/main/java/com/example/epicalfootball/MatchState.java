package com.example.epicalfootball;

import android.graphics.RectF;

import com.example.epicalfootball.activities.MatchActivity;
import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.GoalFrame;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.items.OutfieldPlayer;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;

import java.util.Random;

import static com.example.epicalfootball.Constants.*;

public class MatchState {

    private MatchActivity matchActivity;
    private int ballsLeft;
    private int goalsScored;
    private final GoalFrame goalFrame;
    private Goalkeeper goalkeeper;
    private OutfieldPlayer outfieldPlayer;
    private Ball ball;
    private boolean canScore;
    private long newBallTimer;
    private long ballFeedTimer;
    private final Random random = new Random();
    private final RectF leftBoundary = new RectF(-FIELD_WIDTH * HALF - BOUNDARY_WIDTH, -TOUCHLINE_FROM_TOP, -FIELD_WIDTH * HALF, FIELD_HEIGHT);
    private final RectF rightBoundary = new RectF(FIELD_WIDTH * HALF, -TOUCHLINE_FROM_TOP, FIELD_WIDTH * HALF + BOUNDARY_WIDTH, FIELD_HEIGHT);
    private final RectF topBoundary = new RectF(-FIELD_WIDTH, -TOUCHLINE_FROM_TOP - BOUNDARY_WIDTH, FIELD_WIDTH, -TOUCHLINE_FROM_TOP);
    private final RectF bottomBoundary = new RectF(-FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT + BOUNDARY_WIDTH);

    private boolean controlOn = false;
    private float controlX;
    private float controlY;
    private boolean decelerateOn = false;
    private boolean shootButtonDown = false;
    private boolean readyToShoot = false;
    private float shotPowerMeter = 0;
    private float shootingTimer = 0;
    private TargetGoal targetGoal;
    private Position aimTarget;
    private boolean longShot;

    public MatchState(MatchActivity matchActivity) {
        this.matchActivity = matchActivity;
        this.goalkeeper = new Goalkeeper();
        this.outfieldPlayer = new OutfieldPlayer();
        this.ballsLeft = BALLS_AT_START;
        this.goalsScored = 0;
        this.goalFrame = new GoalFrame();
        this.canScore = false;
        this.ballFeedTimer = BALL_FEED_TIMER;
        this.newBallTimer = 0;
        this.ball = feedNewBall();
    }

    public void updateGameState(long elapsed) {
        float timeFactor = elapsed/1000f;

        //Goalkeeper AI
        handlePlayerControls(elapsed, timeFactor);

        goalkeeper.updateSpeed(timeFactor);
        outfieldPlayer.updateSpeed(timeFactor, decelerateOn, ball);
        goalkeeper.updatePosition(timeFactor);
        goalkeeper.updateOrientation(timeFactor);
        outfieldPlayer.updatePosition(timeFactor);
        outfieldPlayer.updateOrientation(timeFactor);

        //handleGoalkeeperPlayerCollision
        handleBoundaryCollision(outfieldPlayer);
        this.goalFrame.handleGoalCollision(goalkeeper);
        this.goalFrame.handleGoalCollision(outfieldPlayer);

        for (int x = 0; x < BALL_UPDATES_PER_CYCLE; x++) {
            ball.updateSpeed(timeFactor / BALL_UPDATES_PER_CYCLE);
            ball.updatePosition(timeFactor / BALL_UPDATES_PER_CYCLE);

            //GoalkeeperBallCollision

            if (Collisions.handlePlayerBallCollision(outfieldPlayer, ball, readyToShoot, aimTarget)) {
                handleShootBall();
            }

            this.goalFrame.handleGoalCollision(ball);
        }

        updateBallFeedTimer(elapsed);
        updateNewBallTimer(elapsed);
        outfieldPlayer.updateKickRecoveryTimer(elapsed);
        this.matchActivity.updatePowerBars((int)this.shotPowerMeter);

        checkBallOver();
    }

    public void handlePlayerControls(float elapsed, float timeFactor) {
        if (shootButtonDown) {
            outfieldPlayer.setAimRecoveryTimer(0);
            float playerBallDirection = EpicalMath.convertToDirectionFromOrigo(ball.getPosition().getX() - outfieldPlayer.getPosition().getX(), ball.getPosition().getY() - outfieldPlayer.getPosition().getY());
            outfieldPlayer.getTargetSpeed().setDirection(playerBallDirection);
            outfieldPlayer.getTargetSpeed().setMagnitude(AUTOPILOT_SPEED_MAGNITUDE);

            if (this.targetGoal == null) {
                float distance = EpicalMath.calculateDistanceFromOrigo(this.ball.getPosition().getX(), this.ball.getPosition().getY());

                if (distance < LONG_SHOTS_LIMIT) {
                    longShot = false;
                } else {
                    longShot = true;
                }

                this.targetGoal = new TargetGoal(distance, longShot, this.getOutfieldPlayer());
            }

            targetGoal.updatePosition(timeFactor);

            if (readyToShoot) {
                readyToShoot = false;
                this.shotPowerMeter = 0;
            }

            if (!longShot) {
                if (this.shotPowerMeter < outfieldPlayer.getFinishingMidShotPower()) {
                    this.shotPowerMeter += outfieldPlayer.getFinishingMidShotPower() / (SHOT_POWER_METER_OPTIMAL - outfieldPlayer.getFinishingMidShotPower()) * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                } else {
                    this.shotPowerMeter += (SHOT_POWER_METER_OPTIMAL - outfieldPlayer.getFinishingMidShotPower()) / outfieldPlayer.getFinishingMidShotPower() * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                }
            } else {
                if (this.shotPowerMeter < outfieldPlayer.getLongShotsMidShotPower()) {
                    this.shotPowerMeter += outfieldPlayer.getLongShotsMidShotPower() / (SHOT_POWER_METER_OPTIMAL - outfieldPlayer.getLongShotsMidShotPower()) * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                } else {
                    this.shotPowerMeter += (SHOT_POWER_METER_OPTIMAL - outfieldPlayer.getLongShotsMidShotPower()) / outfieldPlayer.getLongShotsMidShotPower() * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                }
            }
        } else {
            if (this.shotPowerMeter < SHOT_POWER_METER_LOWER_LIMIT) {
                this.readyToShoot = false;
                this.shotPowerMeter = 0;
                this.shootingTimer = 0;
            } else if (this.shotPowerMeter > 0) {
                if (readyToShoot) {
                    this.shootingTimer -= elapsed;

                    if (this.shootingTimer <= 0) {
                        readyToShoot = false;
                        this.shotPowerMeter = 0;
                        this.shootingTimer = 0;
                    }
                } else {
                    this.readyToShoot = true;
                    this.shootingTimer = SHOOT_READY_TIME_IN_MILLISECONDS;
                    outfieldPlayer.setAimRecoveryTimer(AIM_RECOVERY_TIME);

                    if (controlOn) {
                        this.aimTarget = targetGoal.getAimTarget(controlX - HALF, controlY - FULL);
                    } else {
                        this.aimTarget = new Position(0,0);
                    }
                }
            }

            if (outfieldPlayer.getAimRecoveryTimer() > 0) {
                outfieldPlayer.setAimRecoveryTimer(outfieldPlayer.getAimRecoveryTimer() - elapsed);

                if (outfieldPlayer.getAimRecoveryTimer() <= 0) {
                    outfieldPlayer.setAimRecoveryTimer(0);
                }
            }

            if (outfieldPlayer.getAimRecoveryTimer() > 0) {
                float playerBallDirection = EpicalMath.convertToDirectionFromOrigo(ball.getPosition().getX() - outfieldPlayer.getPosition().getX(), ball.getPosition().getY() - outfieldPlayer.getPosition().getY());
                outfieldPlayer.getTargetSpeed().setDirection(playerBallDirection);
                outfieldPlayer.getTargetSpeed().setMagnitude(AUTOPILOT_SPEED_MAGNITUDE);
            } else if (controlOn) {
                outfieldPlayer.getTargetSpeed().setTargetSpeed(controlX - HALF, controlY - HALF);
            } else {
                outfieldPlayer.getTargetSpeed().nullTargetSpeed();
            }

            this.targetGoal = null;
        }
    }

    public void updateBallFeedTimer(float elapsed) {
        if (ballFeedTimer > 0) {
            ballFeedTimer -= elapsed;

            if (ballFeedTimer <= 0) {
                ballFeedTimer = 0;
                canScore = true;
            }
        }
    }

    public void updateNewBallTimer(float elapsed) {
        if (newBallTimer > 0) {
            newBallTimer -= elapsed;

            if (newBallTimer <= 0) {
                newBallTimer = 0;

                if (this.ballsLeft > 1) {
                    ballFeedTimer = BALL_FEED_TIMER;
                    substractBall();
                    this.ball = feedNewBall();
                } else {
                    matchActivity.goToResult(this.goalsScored);
                }
            }
        }
    }

    public Ball feedNewBall() {
        Ball ball = new Ball();
        ball.getSpeed().setMagnitude((random.nextInt(4) + 9) / 10f);
        ball.getPosition().setY(random.nextInt(7) + 24);

        if (random.nextInt(TWO) == 0) {
            ball.getSpeed().setDirection(0);
            ball.getPosition().setX(-FIELD_WIDTH * HALF - BOUNDARY_WIDTH);
        } else {
            ball.getSpeed().setDirection((float)Math.PI);
            ball.getPosition().setX(FIELD_WIDTH * HALF + BOUNDARY_WIDTH);
        }

        return ball;
    }

    public void checkBallOver() {
        if (canScore) {
            if (ballInGoal()) {
                addGoal();
                canScore = false;
                newBallTimer = NEW_BALL_WAIT_TIME_IN_MILLISECONDS;
            } else if (ballOutOfBounds()) {
                canScore = false;
                newBallTimer = NEW_BALL_WAIT_TIME_IN_MILLISECONDS;
            } else if (ball.getSpeed().getMagnitude() == 0 && EpicalMath.checkIntersect(goalkeeper, ball)) {
                canScore = false;
                newBallTimer = NEW_BALL_WAIT_TIME_IN_MILLISECONDS;
            }
        }
    }

    public void handleShootBall() {
        this.ball.shoot(outfieldPlayer, shotPowerMeter, aimTarget);
        outfieldPlayer.setKickRecoveryTimer(PLAYER_KICK_RECOVERY_TIME);
        outfieldPlayer.getSpeed().setMagnitude(outfieldPlayer.getSpeed().getMagnitude() * PLAYER_SLOW_ON_SHOT_FACTOR);
        this.readyToShoot = false;
        this.shotPowerMeter = 0;
        this.shootingTimer = 0;
        outfieldPlayer.setAimRecoveryTimer(0);
    }

    public boolean ballOutOfBounds() {
        return ball.getPosition().getX() < -FIELD_WIDTH * HALF
                || ball.getPosition().getX() > FIELD_WIDTH * HALF
                || ball.getPosition().getY() < -BALL_RADIUS
                || ball.getPosition().getY() > FIELD_HEIGHT;
    }

    public boolean ballInGoal() {
        return EpicalMath.checkIntersect(this.goalFrame.getGoalArea(), ball);
    }

    public void handleBoundaryCollision(OutfieldPlayer outfieldPlayer) {
        Collisions.handlePlayerLineSegmentCollision(leftBoundary, outfieldPlayer);
        Collisions.handlePlayerLineSegmentCollision(rightBoundary, outfieldPlayer);
        Collisions.handlePlayerLineSegmentCollision(topBoundary, outfieldPlayer);
        Collisions.handlePlayerLineSegmentCollision(bottomBoundary, outfieldPlayer);
    }

    public void setControlOn(float x, float y) {
        controlOn = true;
        controlX = x;
        controlY = y;
        decelerateOn = false;
    }

    public void setControlOffWithDecelerate(boolean decelerate) {
        controlOn = false;
        decelerateOn = decelerate;
    }

    public void setControl(float touchX, float touchY, float sideLength) {
        if (shootButtonDown) {
            setControlOn(touchX, touchY);
        } else {
            if (EpicalMath.calculateDistance(HALF, HALF, touchX, touchY) < DECELERATE_DOT_RADIUS_OF_CONTROL_SURFACE) {
                setControlOffWithDecelerate(true);
            } else if (touchX >= 0 && touchX <= sideLength && touchY >= 0 && touchY <= sideLength) {
                setControlOn(touchX, touchY);
            } else {
                setControlOffWithDecelerate(false);
            }
        }
    }

    public void addGoal() {
        this.goalsScored++;
        matchActivity.updateGoals(Integer.toString(goalsScored));
    }

    public void substractBall() {
        this.ballsLeft--;
        matchActivity.updateBallsLeft(Integer.toString(ballsLeft));
    }

    public int getBallsLeft() {
        return ballsLeft;
    }

    public void setBallsLeft(int ballsLeft) {
        this.ballsLeft = ballsLeft;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public GoalFrame getGoalFrame() {
        return goalFrame;
    }

    public Goalkeeper getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(Goalkeeper goalkeeper) {
        this.goalkeeper = goalkeeper;
    }

    public OutfieldPlayer getOutfieldPlayer() {
        return outfieldPlayer;
    }

    public void setOutfieldPlayer(OutfieldPlayer outfieldPlayer) {
        this.outfieldPlayer = outfieldPlayer;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public boolean isCanScore() {
        return canScore;
    }

    public void setCanScore(boolean canScore) {
        this.canScore = canScore;
    }

    public long getNewBallTimer() {
        return newBallTimer;
    }

    public void setNewBallTimer(long newBallTimer) {
        this.newBallTimer = newBallTimer;
    }

    public long getBallFeedTimer() {
        return ballFeedTimer;
    }

    public void setBallFeedTimer(long ballFeedTimer) {
        this.ballFeedTimer = ballFeedTimer;
    }

    public Random getRandom() {
        return random;
    }

    public RectF getLeftBoundary() {
        return leftBoundary;
    }

    public RectF getRightBoundary() {
        return rightBoundary;
    }

    public RectF getTopBoundary() {
        return topBoundary;
    }

    public RectF getBottomBoundary() {
        return bottomBoundary;
    }

    public boolean isControlOn() {
        return controlOn;
    }

    public void setControlOn(boolean controlOn) {
        this.controlOn = controlOn;
    }

    public float getControlX() {
        return controlX;
    }

    public void setControlX(float controlX) {
        this.controlX = controlX;
    }

    public float getControlY() {
        return controlY;
    }

    public void setControlY(float controlY) {
        this.controlY = controlY;
    }

    public boolean isDecelerateOn() {
        return decelerateOn;
    }

    public void setDecelerateOn(boolean decelerateOn) {
        this.decelerateOn = decelerateOn;
    }

    public boolean isShootButtonDown() {
        return shootButtonDown;
    }

    public void setShootButtonDown(boolean shootButtonDown) {
        this.shootButtonDown = shootButtonDown;
    }

    public boolean isReadyToShoot() {
        return readyToShoot;
    }

    public void setReadyToShoot(boolean readyToShoot) {
        this.readyToShoot = readyToShoot;
    }

    public float getShotPowerMeter() {
        return shotPowerMeter;
    }

    public void setShotPowerMeter(float shotPowerMeter) {
        this.shotPowerMeter = shotPowerMeter;
    }

    public float getShootingTimer() {
        return shootingTimer;
    }

    public void setShootingTimer(float shootingTimer) {
        this.shootingTimer = shootingTimer;
    }

    public TargetGoal getTargetGoal() {
        return targetGoal;
    }

    public void setTargetGoal(TargetGoal targetGoal) {
        this.targetGoal = targetGoal;
    }

    public Position getAimTarget() {
        return aimTarget;
    }

    public void setAimTarget(Position aimTarget) {
        this.aimTarget = aimTarget;
    }

    public boolean isLongShot() {
        return longShot;
    }

    public void setLongShot(boolean longShot) {
        this.longShot = longShot;
    }
}
