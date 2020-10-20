package com.example.epicalfootball.math;

import com.example.epicalfootball.items.Ball;

import static com.example.epicalfootball.Constants.BALL_REFERENCE_SPEED;

public class Position {
    private float x;
    private float y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position addPositionVector(float direction, float distance) {
        this.x += Math.cos(direction) * distance;
        this.y += Math.sin(direction) * distance;
        return this;
    }

    public Position addPositionVector(Vector vector) {
        addPositionVector(vector.getDirection(), vector.getMagnitude());
        return this;
    }

    public void moveBySpeed(Ball ball, float seconds) {
        this.x += Math.cos(ball.getSpeed().getDirection()) * ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED * seconds;
        this.y += Math.sin(ball.getSpeed().getDirection()) * ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED * seconds;
    }

    public void clonePosition(Position otherPosition) {
        this.x = otherPosition.getX();
        this.y = otherPosition.getY();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
