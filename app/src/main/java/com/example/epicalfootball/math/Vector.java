package com.example.epicalfootball.math;

import static com.example.epicalfootball.Constants.*;

public class Vector {
    protected float direction;
    protected float magnitude;

    public Vector() {
        this.direction = 0;
        this.magnitude = 0;
    }

    public Vector(float direction, float magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void addVector(float direction, float magnitude) {
        float directionX = (float)(Math.cos(this.direction) * this.magnitude + Math.cos(direction) * magnitude);
        float directionY = (float)(Math.sin(this.direction) * this.magnitude + Math.sin(direction) * magnitude);
        this.direction = EpicalMath.convertToDirectionFromOrigo(directionX, directionY);
        this.magnitude = EpicalMath.calculateDistanceFromOrigo(directionX, directionY);
    }

    public void addVector(Vector vector) {
        addVector(vector.getDirection(), vector.getMagnitude());
    }

    public void reverseDirection() {
        if (direction >= 0 ) {
            direction -= HALF_CIRCLE;
        } else {
            direction += HALF_CIRCLE;
        }
    }

    public void bounceDirection (float surfaceDirection) {
        this.reverseDirection();
        float bounceDirection = EpicalMath.sanitizeDirection(surfaceDirection + surfaceDirection - this.direction);
        this.direction = bounceDirection;
    }

    public Vector cloneVector() {
        return new Vector(this.direction, this.magnitude);
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }
}
