package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;

public class TargetSpeedVector extends Vector {

    public TargetSpeedVector() {
        this.direction = 0;
        this.magnitude = 0;
    }

    public TargetSpeedVector (float direction, float magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void setTargetSpeed(float x, float y) {
        float newDirection = EpicalMath.convertToDirection(x, y);
        this.setDirection(newDirection);
        float newMagnitude = EpicalMath.calculateMagnitude(x, y, HALF * CONTROL_VIEW_MAXIMUM_LIMIT );
        this.setMagnitude(newMagnitude);
    }

    public void nullTargetSpeed() {
        this.setMagnitude(0);
    }
}
