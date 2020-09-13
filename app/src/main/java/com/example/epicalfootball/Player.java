package com.example.epicalfootball;

public class Player {
    private float speed;
    private float speedDirection;
    private float acceleration;
    private float accelerationDirection;

    public Player() {
        this.speed = 0;
        this.speedDirection = 0;
        this.acceleration = 0;
        this.accelerationDirection = 0;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setSpeedDirection(float speedDirection) {
        this.speedDirection = speedDirection;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setAccelerationDirection(float accelerationDirection) {
        this.accelerationDirection = accelerationDirection;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSpeedDirection() {
        return speedDirection;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getAccelerationDirection() {
        return accelerationDirection;
    }
}
