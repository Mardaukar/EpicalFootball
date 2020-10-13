package com.example.epicalfootball;

import android.graphics.RectF;

import java.util.Random;

import static com.example.epicalfootball.Constants.*;

public class GameState {

    private GameActivity gameActivity;
    private Player player;
    private Ball ball;
    private int ballsLeft;
    private int goalsScored;

    private boolean controlOn = false;
    private float controlX;
    private float controlY;
    private float controlWidth;
    private boolean decelerateOn = false;
    private boolean shootButtonDown = false;
    private boolean readyToShoot = false;
    private float shotPowerMeter = 0;
    private float shootingTimer = 0;
    private GoalFrame goalFrame;
    private TargetGoal targetGoal;
    private Position aimTarget;
    private boolean longshot;

    private boolean canScore;
    private long newBallTimer;
    private long ballFeedTimer;

    private Random random = new Random();

    private RectF leftBoundary = new RectF(-FIELD_WIDTH * HALF - BOUNDARY_WIDTH, -TOUCHLINE_FROM_TOP, -FIELD_WIDTH * HALF, FIELD_HEIGHT);
    private RectF rightBoundary = new RectF(FIELD_WIDTH * HALF, -TOUCHLINE_FROM_TOP, FIELD_WIDTH * HALF + BOUNDARY_WIDTH, FIELD_HEIGHT);
    private RectF topBoundary = new RectF(-FIELD_WIDTH, -TOUCHLINE_FROM_TOP - BOUNDARY_WIDTH, FIELD_WIDTH, -TOUCHLINE_FROM_TOP);
    private RectF bottomBoundary = new RectF(-FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT + BOUNDARY_WIDTH);

    public GameState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.player = new Player();
        this.ballsLeft = BALLS_AT_START;
        this.goalsScored = 0;
        this.goalFrame = new GoalFrame();
        this.canScore = false;
        this.ballFeedTimer = BALL_FEED_TIMER;
        this.newBallTimer = 0;
        this.ball = feedNewBall();
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

    public Player getPlayer() {
        return player;
    }

    public int getBallsLeft() {
        return this.ballsLeft;
    }

    public int getGoalsScored() {
        return this.goalsScored;
    }

    public void addGoal() {
        this.goalsScored++;
        gameActivity.updateGoals(Integer.toString(goalsScored));
    }

    public void substractBall() {
        this.ballsLeft--;
        gameActivity.updateBallsLeft(Integer.toString(ballsLeft));
    }

    public Ball getBall() {
        return ball;
    }

    public void updateGameState(long elapsed) {
        float timeFactor = elapsed/1000f;

        if (ballFeedTimer > 0) {
            ballFeedTimer -= elapsed;

            if (ballFeedTimer <= 0) {
                ballFeedTimer = 0;
                canScore = true;
            }
        }

        if (newBallTimer > 0) {
            newBallTimer -= elapsed;

            if (newBallTimer <= 0) {
                newBallTimer = 0;

                if (this.ballsLeft > 1) {
                    ballFeedTimer = BALL_FEED_TIMER;
                    substractBall();
                    this.ball = feedNewBall();
                } else {
                    gameActivity.goToResult(this.goalsScored);
                }
            }
        }

        player.updateRecoveryTimer(elapsed);

        if (shootButtonDown) {
            if (this.targetGoal == null) {
                float distance = EpicalMath.calculateDistance(this.ball.getPosition().getX(), this.ball.getPosition().getY());

                if (distance < LONG_SHOTS_LIMIT) {
                    longshot = false;
                } else {
                    longshot = true;
                }

                this.targetGoal = new TargetGoal(distance, longshot, this.getPlayer());
            }

            targetGoal.updatePosition(timeFactor);

            if (readyToShoot) {
                readyToShoot = false;
                this.shotPowerMeter = 0;
            }

            if (!longshot) {
                if (this.shotPowerMeter < player.getFinishingMidShotPower()) {
                    this.shotPowerMeter += player.getFinishingMidShotPower() / (SHOT_POWER_METER_OPTIMAL - player.getFinishingMidShotPower()) * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                } else {
                    this.shotPowerMeter += (SHOT_POWER_METER_OPTIMAL - player.getFinishingMidShotPower()) / player.getFinishingMidShotPower() * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                }
            } else {
                if (this.shotPowerMeter < player.getLongshotsMidShotPower()) {
                    this.shotPowerMeter += player.getLongshotsMidShotPower() / (SHOT_POWER_METER_OPTIMAL - player.getLongshotsMidShotPower()) * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                } else {
                    this.shotPowerMeter += (SHOT_POWER_METER_OPTIMAL - player.getLongshotsMidShotPower()) / player.getLongshotsMidShotPower() * timeFactor * SHOT_POWER_METER_OPTIMAL / AIMING_TIME;
                }
            }

            if (controlOn) {

            } else {

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
                    readyToShoot = true;
                    this.shootingTimer = SHOOT_READY_TIME_IN_MILLISECONDS;

                    if (controlOn) {
                        this.aimTarget = targetGoal.getAimTarget(controlX - HALF, controlY - FULL);
                    } else {
                        this.aimTarget = new Position(0,0);
                    }
                }
            }

            if (controlOn) {
                player.getTargetSpeed().setTargetSpeed(controlX - HALF, controlY - HALF);
            } else {
                player.getTargetSpeed().nullTargetSpeed();
            }

            this.targetGoal = null;
        }

        this.gameActivity.updatePowerBars((int)this.shotPowerMeter);

        handleBoundaryCollision(player);
        this.goalFrame.handleGoalCollision(player);

        if (Collisions.handlePlayerBallCollision(player, ball, readyToShoot, aimTarget)) {
            this.ball.shoot(player, shotPowerMeter, aimTarget);
            player.setKickRecoveryTimer(PLAYER_KICK_RECOVERY_TIME);
            player.getSpeed().setMagnitude(player.getSpeed().getMagnitude() * PLAYER_SLOW_ON_SHOT_FACTOR);
            this.readyToShoot = false;
            this.shotPowerMeter = 0;
        }

        this.goalFrame.handleGoalCollision(ball);

        player.updatePosition(timeFactor);
        ball.updatePosition(timeFactor);

        player.updateSpeed(timeFactor, decelerateOn, ball);
        ball.updateSpeed(timeFactor);

        if (ballInGoal() && canScore) {
            addGoal();
            canScore = false;
            newBallTimer = NEW_BALL_WAIT_TIME_IN_MILLISECONDS;
        } else if (ballOutOfBounds() && canScore) {
            canScore = false;
            newBallTimer = NEW_BALL_WAIT_TIME_IN_MILLISECONDS;
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

    public boolean ballOutOfBounds() {
        return ball.getPosition().getX() < -FIELD_WIDTH * HALF
                || ball.getPosition().getX() > FIELD_WIDTH * HALF
                || ball.getPosition().getY() < -BALL_RADIUS
                || ball.getPosition().getY() > FIELD_HEIGHT;
    }

    public boolean ballInGoal() {
        return EpicalMath.checkIntersect(this.goalFrame.getGoalArea(), ball.position.getX(), ball.position.getY(), ball.getRadius());
    }

    public void handleBoundaryCollision(Player player) {
        Collisions.handleLineSegmentCollision(leftBoundary, player);
        Collisions.handleLineSegmentCollision(rightBoundary, player);
        Collisions.handleLineSegmentCollision(topBoundary, player);
        Collisions.handleLineSegmentCollision(bottomBoundary, player);
    }

    public boolean isDecelerateOn() {
        return decelerateOn;
    }

    public void setControlOn(float x, float y) {
        controlOn = true;
        controlX = x;
        controlY = y;
        decelerateOn = false;
    }

    public boolean isControlOn() {
        return controlOn;
    }

    public float getControlX() {
        return controlX;
    }

    public float getControlY() {
        return controlY;
    }

    public void setControlOffWithDecelerate(boolean decelerate) {
        controlOn = false;
        decelerateOn = decelerate;
    }

    public float getControlWidth() {
        return controlWidth;
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

    public TargetGoal getTargetGoal() {
        return targetGoal;
    }

    public GoalFrame getGoalFrame() {
        return goalFrame;
    }
}
