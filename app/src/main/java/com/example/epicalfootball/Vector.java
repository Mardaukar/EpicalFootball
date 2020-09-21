package com.example.epicalfootball;

public class Vector {
    private float direction;
    private float magnitude;

    public Vector() {
        this.direction = 0;
        this.magnitude = 0;
    }

    public Vector(float direction, float magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public float getDirection() {
        return direction;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void reverseDirection() {
        if (direction >= 0 ) {
            direction -= Math.PI;
        } else {
            direction += Math.PI;
        }
    }

    public void bounceDirection (float surfaceDirection) {
        this.reverseDirection();
        float bounceDirection = EpicalMath.sanitizeDirection(surfaceDirection + surfaceDirection - this.direction);
        this.direction = bounceDirection;
    }
}
