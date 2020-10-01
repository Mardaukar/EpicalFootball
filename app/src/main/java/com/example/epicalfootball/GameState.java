package com.example.epicalfootball;

import android.graphics.RectF;
import android.util.Log;

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

    private boolean canScore = true;
    private long newBallTimer = 0;

    private RectF leftBoundary = new RectF(-FIELD_WIDTH * HALF - BOUNDARY_WIDTH, -TOUCHLINE_FROM_TOP, -FIELD_WIDTH * HALF, FIELD_HEIGHT);
    private RectF rightBoundary = new RectF(FIELD_WIDTH * HALF, -TOUCHLINE_FROM_TOP, FIELD_WIDTH * HALF + BOUNDARY_WIDTH, FIELD_HEIGHT);
    private RectF topBoundary = new RectF(-FIELD_WIDTH, -TOUCHLINE_FROM_TOP - BOUNDARY_WIDTH, FIELD_WIDTH, -TOUCHLINE_FROM_TOP);
    private RectF bottomBoundary = new RectF(-FIELD_WIDTH, FIELD_HEIGHT, FIELD_WIDTH, FIELD_HEIGHT + BOUNDARY_WIDTH);

    private RectF goalArea = new RectF(-GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF, -DOUBLE * BALL_RADIUS);
    private RectF rearNet = new RectF(-GOAL_WIDTH * HALF, -GOAL_DEPTH - DOUBLE * POST_RADIUS, GOAL_WIDTH * HALF, -GOAL_DEPTH);
    private RectF leftNet = new RectF(-GOAL_WIDTH * HALF - DOUBLE * POST_RADIUS, -GOAL_DEPTH, -GOAL_WIDTH * HALF, -POST_RADIUS);
    private RectF rightNet = new RectF(GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF + DOUBLE * POST_RADIUS, -POST_RADIUS);
    private Circle leftPost = new Circle(-GOAL_WIDTH * HALF - POST_RADIUS, 0, POST_RADIUS);
    private Circle rightPost = new Circle(GOAL_WIDTH * HALF + POST_RADIUS, 0, POST_RADIUS);
    private Circle leftSupport = new Circle(-GOAL_WIDTH * HALF - POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
    private Circle rightSupport = new Circle(GOAL_WIDTH * HALF + POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);

    public GameState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.player = new Player();
        this.ball = new Ball();
        this.ballsLeft = BALLS_AT_START;
        this.goalsScored = 0;
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

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void updateGameState(long elapsed) {
        float timeFactor = elapsed/1000f;

        if (newBallTimer > 0) {
            newBallTimer -= elapsed;

            if (newBallTimer <= 0) {
                substractBall();
                this.ball = new Ball();
                canScore = true;
            }
        }

        if (controlOn) {
            player.getTargetSpeed().setTargetSpeed(controlX, controlY, controlWidth);
        } else {
            player.getTargetSpeed().nullTargetSpeed();
        }

        handleBoundaryCollision(player);
        handleGoalCollision(player);
        Collisions.handlePlayerBallCollision(player, ball);
        handleGoalCollision(ball);

        player.updatePosition(timeFactor);
        ball.updatePosition(timeFactor);

        player.updateSpeed(timeFactor, decelerateOn);
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

    public boolean ballOutOfBounds() {
        return ball.getPosition().getX() < -FIELD_WIDTH * HALF
                || ball.getPosition().getX() > FIELD_WIDTH * HALF
                || ball.getPosition().getY() < -DOUBLE * BALL_RADIUS
                || ball.getPosition().getY() > FIELD_HEIGHT;
    }

    public boolean ballInGoal() {
        return EpicalMath.checkIntersect(goalArea, ball.position.getX(), ball.position.getY(), ball.getRadius());
    }

    public void handleBoundaryCollision(FieldObject fieldObject) {
        Collisions.handleLineSegmentCollision(leftBoundary, fieldObject);
        Collisions.handleLineSegmentCollision(rightBoundary, fieldObject);
        Collisions.handleLineSegmentCollision(topBoundary, fieldObject);
        Collisions.handleLineSegmentCollision(bottomBoundary, fieldObject);
    }

    public void handleGoalCollision(FieldObject fieldObject) {
        Collisions.handleLineSegmentCollision(rearNet, fieldObject);
        Collisions.handleLineSegmentCollision(leftNet, fieldObject);
        Collisions.handleLineSegmentCollision(rightNet, fieldObject);
        Collisions.handleCircleCollision(leftPost, fieldObject);
        Collisions.handleCircleCollision(rightPost, fieldObject);
        Collisions.handleCircleCollision(leftSupport, fieldObject);
        Collisions.handleCircleCollision(rightSupport, fieldObject);
    }

    public boolean isDecelerateOn() {
        return decelerateOn;
    }

    public void setControlOn(float x, float y, float controlViewWidth) {
        controlOn = true;
        controlX = x;
        controlY = y;
        this.controlWidth = controlViewWidth;
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

    public void setControlWidth(float controlWidth) {
        this.controlWidth = controlWidth;
    }
}
