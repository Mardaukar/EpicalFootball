package com.example.epicalfootball;

import android.graphics.RectF;
import android.util.Log;

public class GameState {

    private GameActivity gameActivity;
    private Player player;
    private Ball ball;
    private int ballsLeft;
    private int goalsScored;

    private boolean controlOn = false;
    private float controlX;
    private float controlY;
    private float centerSideDistance;
    private boolean decelerateOn = false;

    private boolean canScore = true;
    private long newBallTimer = 2000;

    private RectF goalArea = new RectF(-7.32f / 2, -35.294f*0.8f*0.08f, 7.32f / 2, -0.8f);
    private RectF rearNet = new RectF(-7.32f / 2, -35.294f*0.8f*0.08f, 7.32f / 2, -35.294f*0.8f*0.08f);
    private RectF leftNet = new RectF(-7.32f / 2, -35.294f*0.8f*0.08f, -7.32f / 2, 0);
    private RectF rightNet = new RectF(7.32f / 2, (float)(-35.294*0.8*0.08), 7.32f / 2, 0);
    private RectF leftBoundary = new RectF(-35.294f / 2, -35.294f*0.8f*0.1f, -35.294f / 2, 35.294f*0.8f*0.9f);
    private RectF rightBoundary = new RectF(35.294f / 2, -35.294f*0.8f*0.1f, 35.294f / 2, 35.294f*0.8f*0.9f);
    private RectF topBoundary = new RectF(-35.294f / 2, -35.294f*0.8f*0.1f, 35.294f / 2, -35.294f*0.8f*0.1f);
    private RectF bottomBoundary = new RectF(-35.294f / 2, 35.294f*0.8f*0.9f, 35.294f / 2, 35.294f*0.8f*0.9f);
    private Circle leftPost = new Circle(-7.32f / 2 - 0.1f, 0, 0.2f);
    private Circle rightPost = new Circle(7.32f / 2 + 0.1f, 0, 0.2f);

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
                newBallTimer = 3000;
            }
        }

        if (controlOn) {
            player.getTargetSpeed().setTargetSpeed(controlX - centerSideDistance, controlY - centerSideDistance, centerSideDistance);
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
            newBallTimer = 2000;
        } else if (ballOutOfBounds() && canScore) {
            canScore = false;
            newBallTimer = 2000;
        }
    }

    public boolean ballOutOfBounds() {
        if (ball.getPosition().getX() < -35.294f / 2
                || ball.getPosition().getX() > 35.294f / 2
                || ball.getPosition().getY() < -0.8f
                || ball.getPosition().getY() > 35.294f*0.8f*0.9f) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ballInGoal() {
        if (EpicalMath.checkIntersect(goalArea, ball.position.getX(), ball.position.getY(), ball.getRadius())) {
            return true;
        } else {
            return false;
        }
    }

    public void handleGoalCollision(FieldObject fieldObject) {
        handleCircleCollision(leftPost, fieldObject);
        handleCircleCollision(rightPost, fieldObject);
        handleLineSegmentCollision(rearNet, fieldObject);
        handleLineSegmentCollision(leftNet, fieldObject);
        handleLineSegmentCollision(rightNet, fieldObject);
    }

    public void handleCircleCollision(Circle circle, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(circle.getX(), circle.getY(), circle.getRadius(), fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            float distance = circle.getRadius() + fieldObject.getRadius();
            float direction = EpicalMath.convertToDirection(fieldObject.getPosition().getX() - circle.getX(), fieldObject.getPosition().getY() - circle.getY());
            fieldObject.getPosition().setX(circle.getPosition().getX());
            fieldObject.getPosition().setY(circle.getPosition().getY());
            fieldObject.getPosition().addVector(direction, distance);
            fieldObject.getSpeed().bounceDirection(direction);
            float hitAngle = Math.abs(EpicalMath.sanitizeDirection(fieldObject.getSpeed().getDirection() - direction));
            fieldObject.getSpeed().setMagnitude((1 - 0.5f * (float)Math.cos(hitAngle)) * fieldObject.getSpeed().getMagnitude() * 0.8f);
        }
    }

    public void handlePlayerBallCollision(Player player, Ball ball) {
        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            float centersDistance = player.getRadius() + ball.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(ball.getPosition().getX() - player.getPosition().getX(), ball.getPosition().getY() - player.getPosition().getY());
            float ballCollisionDifference = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());

            ball.getPosition().copyPosition(player.getPosition());
            ball.getPosition().addVector(collisionDirection, centersDistance);

            if (ballCollisionDifference > Math.PI / 2 && ball.getSpeed().getMagnitude() > 0) {
                Log.d("GameState", "BOUNCE");
                ball.getSpeed().bounceDirection(collisionDirection);
                float newDifference = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());
                ball.getSpeed().setMagnitude((1 - 0.5f * (float)Math.cos(newDifference)) * ball.getSpeed().getMagnitude() * 0.5f);

                float playerCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getSpeed().getDirection());
                float impactMagnitude = (float)(Math.cos(playerCollisionAngle) * player.getSpeed().getMagnitude());

                Vector impulse = new Vector(collisionDirection, impactMagnitude);
                ball.getSpeed().addVector(impulse);
            } else {
                Log.d("GameState", "touch");
                float playerCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, player.getSpeed().getDirection());
                float ballCollisionAngle = EpicalMath.absoluteDifference(collisionDirection, ball.getSpeed().getDirection());
                float impactMagnitude = (float)(Math.cos(playerCollisionAngle) * player.getSpeed().getMagnitude() - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                if (impactMagnitude < 0) {
                    impactMagnitude = 0;
                }

                Vector impulse = new Vector(collisionDirection, impactMagnitude);
                ball.getSpeed().addVector(impulse);
            }
        }
    }

    public void handleLineSegmentCollision(RectF rectF, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(rectF, fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            fieldObject.getSpeed().setMagnitude(fieldObject.getSpeed().getMagnitude() * 0.2f);

            if (rectF.top == rectF.bottom) {
                if (fieldObject.position.getY() < rectF.top) {
                    fieldObject.getSpeed().bounceDirection((float)-Math.PI / 2);
                    fieldObject.getPosition().setY(rectF.top - fieldObject.getRadius());
                } else {
                    fieldObject.getSpeed().bounceDirection((float)Math.PI / 2);
                    fieldObject.getPosition().setY(rectF.bottom + fieldObject.getRadius());
                }
            } else {
                if (fieldObject.position.getX() < rectF.left) {
                    fieldObject.getSpeed().bounceDirection((float)Math.PI);
                    fieldObject.getPosition().setX(rectF.left - fieldObject.getRadius());
                } else {
                    fieldObject.getSpeed().bounceDirection(0);
                    fieldObject.getPosition().setX(rectF.right + fieldObject.getRadius());
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

    public void setControlOn(float x, float y, float centerSideDistance) {
        controlOn = true;
        controlX = x;
        controlY = y;
        this.centerSideDistance = centerSideDistance;
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

    public float getCenterSideDistance() {
        return centerSideDistance;
    }

    public void setControlOffWithDecelerate(boolean decelerate) {
        controlOn = false;
        decelerateOn = decelerate;
    }
}
