package com.example.epicalfootball;

public class TargetSpeedVector extends Vector {
    final float touchAreaLimitingFactor = 0.8f;

    public TargetSpeedVector() {
        this.direction = 0;
        this.magnitude = 0;
    }

    public TargetSpeedVector (float direction, float magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void setTargetSpeed(float x, float y, float controlWidth) {
        float newDirection = EpicalMath.convertToDirection(x, y);
        this.setDirection(newDirection);
        float newMagnitude = EpicalMath.calculateMagnitude(x, y, controlWidth / 2 * touchAreaLimitingFactor);
        this.setMagnitude(newMagnitude);
    }

    public void nullTargetSpeed() {
        //this.setDirection(0);
        this.setMagnitude(0);
    }
}
