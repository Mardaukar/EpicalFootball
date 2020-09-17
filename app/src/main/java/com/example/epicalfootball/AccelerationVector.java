package com.example.epicalfootball;

public class AccelerationVector extends Vector {
    final float touchAreaLimitingFactor = 0.8f;

    public void setAcceleration(float x, float y, float centerSideDistance) {
        float newMagnitude = EpicalMath.calculateMagnitude(x, y, centerSideDistance * touchAreaLimitingFactor);
        this.setMagnitude(newMagnitude / 2f + 0.5f);

        float newDirection = EpicalMath.convertToDirection(x, y);
        this.setDirection(newDirection);
    }

    public void nullAcceleration() {
        this.setDirection(0);
        this.setMagnitude(0);
    }
}
