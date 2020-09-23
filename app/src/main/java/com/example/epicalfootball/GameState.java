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
    private long newBallTimer = NEW_BALL_WAIT_TIME;

    private RectF leftBoundary = new RectF(-FIELD_WIDTH / 2 - 1, -FIELD_WIDTH*0.8f*0.1f, -FIELD_WIDTH / 2, FIELD_WIDTH*0.8f*0.9f);
    private RectF rightBoundary = new RectF(FIELD_WIDTH / 2, -FIELD_WIDTH*0.8f*0.1f, FIELD_WIDTH / 2 + 1, FIELD_WIDTH*0.8f*0.9f);
    private RectF topBoundary = new RectF(-FIELD_WIDTH, -FIELD_WIDTH*0.8f*0.1f - 1, FIELD_WIDTH, -FIELD_WIDTH*0.8f*0.1f);
    private RectF bottomBoundary = new RectF(-FIELD_WIDTH, FIELD_WIDTH*0.8f*0.9f, FIELD_WIDTH, FIELD_WIDTH*0.8f*0.9f + 1);

    private RectF goalArea = new RectF(-GOAL_WIDTH / 2, -GOAL_DEPTH, GOAL_WIDTH / 2, -2 * BALL_RADIUS);
    private RectF rearNet = new RectF(-GOAL_WIDTH / 2, -GOAL_DEPTH - 2 * POST_RADIUS, GOAL_WIDTH / 2, -GOAL_DEPTH);
    private RectF leftNet = new RectF(-GOAL_WIDTH / 2 - 2 * POST_RADIUS, -GOAL_DEPTH, -GOAL_WIDTH / 2, -POST_RADIUS);
    private RectF rightNet = new RectF(GOAL_WIDTH / 2, -GOAL_DEPTH, GOAL_WIDTH / 2 + 2 * POST_RADIUS, -POST_RADIUS);
    private Circle leftPost = new Circle(-GOAL_WIDTH / 2 - POST_RADIUS, 0, POST_RADIUS);
    private Circle rightPost = new Circle(GOAL_WIDTH / 2 + POST_RADIUS, 0, POST_RADIUS);
    private Circle leftSupport = new Circle(-GOAL_WIDTH / 2 - POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
    private Circle rightSupport = new Circle(GOAL_WIDTH / 2 + POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);

    public GameState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.player = new Player();
        this.ball = new Ball();
        this.ballsLeft = 10;
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
                newBallTimer = NEW_BALL_WAIT_TIME;
            }
        }

        if (controlOn) {
            player.getTargetSpeed().setTargetSpeed(controlX, controlY, controlWidth);
        } else {
            player.getTargetSpeed().nullTargetSpeed();
        }

        handlePlayerBallCollision(player, ball);
        handleGoalCollision(player);
        handleGoalCollision(ball);
        handleBoundaryCollision(player);

        player.updatePosition(timeFactor);
        ball.updatePosition(timeFactor);

        player.updateSpeed(timeFactor, decelerateOn);
        ball.updateSpeed(timeFactor);

        if (ballInGoal() && canScore) {
            addGoal();
            canScore = false;
            newBallTimer = NEW_BALL_WAIT_TIME;
        } else if (ballOutOfBounds() && canScore) {
            canScore = false;
            newBallTimer = NEW_BALL_WAIT_TIME;
        }
    }

    public boolean ballOutOfBounds() {
        return ball.getPosition().getX() < -FIELD_WIDTH / 2
                || ball.getPosition().getX() > FIELD_WIDTH / 2
                || ball.getPosition().getY() < -0.8f
                || ball.getPosition().getY() > FIELD_WIDTH * 0.8f * 0.9f;
    }

    public boolean ballInGoal() {
        return EpicalMath.checkIntersect(goalArea, ball.position.getX(), ball.position.getY(), ball.getRadius());
    }

    public void handleGoalCollision(FieldObject fieldObject) {
        handleLineSegmentCollision(rearNet, fieldObject);
        handleLineSegmentCollision(leftNet, fieldObject);
        handleLineSegmentCollision(rightNet, fieldObject);
        handleCircleCollision(leftPost, fieldObject);
        handleCircleCollision(rightPost, fieldObject);
        handleCircleCollision(leftSupport, fieldObject);
        handleCircleCollision(rightSupport, fieldObject);
    }

    public void handleCircleCollision(Circle circle, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(circle.getX(), circle.getY(), circle.getRadius(), fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            Log.d("circle", "circle");
            float centersDistance = circle.getRadius() + fieldObject.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(fieldObject.getPosition().getX() - circle.getX(), fieldObject.getPosition().getY() - circle.getY());
            float fieldObjectCollisionDifference = EpicalMath.absoluteDifference(collisionDirection, fieldObject.getSpeed().getDirection());

            fieldObject.getPosition().copyPosition(circle.getPosition());
            fieldObject.getPosition().addVector(collisionDirection, centersDistance);

            if (fieldObjectCollisionDifference > Math.PI / 2) {
                fieldObject.getSpeed().bounceDirection(collisionDirection);
            }

            float newDifference = EpicalMath.absoluteDifference(collisionDirection, fieldObject.getSpeed().getDirection());
            fieldObject.getSpeed().setMagnitude((1 - 0.5f * (float)Math.cos(newDifference)) * fieldObject.getSpeed().getMagnitude() * 0.8f);
        }
    }

    public void handlePlayerBallCollision(Player player, Ball ball) {
        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            float centersDistance = player.getRadius() + ball.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(ball.getPosition().getX() - player.getPosition().getX(), ball.getPosition().getY() - player.getPosition().getY());
            float playerSpeedCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getSpeed().getDirection());
            float playerDirectionCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getTargetSpeed().getDirection());
            float ballCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());
            Vector impulse;
            float impactMagnitude;
            boolean ballControl = playerDirectionCollisionAngle <= player.getControlAngle();
            boolean bounce = ballCollisionAngle > Math.PI / 2 && ball.getSpeed().getMagnitude() > 0;

            if (bounce) {
                ball.getSpeed().bounceDirection(collisionDirection);
                float newBallCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());
                if (ballControl) {
                    ball.getSpeed().setMagnitude((1 - 0.5f * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * 0.2f);
                } else {
                    ball.getSpeed().setMagnitude((1 - 0.5f * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * 0.5f);
                }
                //float playerCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getSpeed().getDirection());
                impactMagnitude = (float)(Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude());
            } else {
                //float playerCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getSpeed().getDirection());
                //float ballCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());
                //float impactMagnitude = (float)(Math.cos(playerCollisionAngle) * player.getSpeed().getMagnitude() - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                impactMagnitude = (float)(Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude() - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                if (impactMagnitude < 0) {
                    impactMagnitude = 0;
                }
            }

            ball.getPosition().copyPosition(player.getPosition());
            impulse = new Vector(collisionDirection, impactMagnitude);
            ball.getSpeed().addVector(impulse);

            if (ballControl) {
                if (bounce) {
                    ball.shiftTowardsPlayerDirectionOnBounce(collisionDirection, centersDistance, player.getTargetSpeed().getDirection(), player.getControlAngle());
                } else {
                    ball.getPosition().addVector(collisionDirection, centersDistance);
                }

                if (player.getSpeed().getMagnitude() > BALLCONTROL_PLAYER_SPEED_MULTIPLIER) {
                    player.getSpeed().setMagnitude(BALLCONTROL_PLAYER_SPEED_MULTIPLIER);
                }
            } else {
                ball.getPosition().addVector(collisionDirection, centersDistance);
            }
        }
    }

    public void handleLineSegmentCollision(RectF line, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(line, fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            Log.d("line", "line");
            fieldObject.getSpeed().setMagnitude(fieldObject.getSpeed().getMagnitude() * 0.4f);

            if (line.height() < line.width()) {
                if (fieldObject.position.getY() < line.centerY()) {
                    if (player.getSpeed().getDirection() > 0) {
                        fieldObject.getSpeed().bounceDirection((float)-Math.PI / 2);
                    }
                    fieldObject.getPosition().setY(line.top - fieldObject.getRadius());
                } else {
                    if (player.getSpeed().getDirection() < 0) {
                        fieldObject.getSpeed().bounceDirection((float)Math.PI / 2);
                    }
                    fieldObject.getPosition().setY(line.bottom + fieldObject.getRadius());
                }
            } else {
                if (fieldObject.position.getX() < line.centerX()) {
                    if (Math.abs(player.getSpeed().getDirection()) < Math.PI / 2) {
                        fieldObject.getSpeed().bounceDirection((float) Math.PI);
                    }
                    fieldObject.getPosition().setX(line.left - fieldObject.getRadius());
                } else {
                    if (Math.abs(player.getSpeed().getDirection()) > Math.PI / 2) {
                        fieldObject.getSpeed().bounceDirection(0);
                    }
                    fieldObject.getPosition().setX(line.right + fieldObject.getRadius());
                }
            }
        }
    }

    public void handleBoundaryCollision(FieldObject fieldObject) {
        handleLineSegmentCollision(leftBoundary, fieldObject);
        handleLineSegmentCollision(rightBoundary, fieldObject);
        handleLineSegmentCollision(topBoundary, fieldObject);
        handleLineSegmentCollision(bottomBoundary, fieldObject);
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
