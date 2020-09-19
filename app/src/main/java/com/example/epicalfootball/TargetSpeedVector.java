package com.example.epicalfootball;

public class TargetSpeedVector extends Vector {
    final float touchAreaLimitingFactor = 0.8f;

    public void setTargetSpeed(float x, float y, float centerSideDistance) {
        float newDirection = EpicalMath.convertToDirection(x, y);
        this.setDirection(newDirection);
        float newMagnitude = EpicalMath.calculateMagnitude(x, y, centerSideDistance * touchAreaLimitingFactor);
        this.setMagnitude(newMagnitude);
    }

    public void nullTargetSpeed() {
        this.setDirection(0);
        this.setMagnitude(0);
    }
}
