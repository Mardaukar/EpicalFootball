package com.example.epicalfootball;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.Constants.*;

public class TargetSpeedVector extends Vector {

    public TargetSpeedVector() {
        this.direction = UP;
        this.magnitude = 0;
    }

    public TargetSpeedVector (float direction, float magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public void setTargetSpeed(float x, float y) {
        float newDirection = EpicalMath.convertToDirectionFromOrigo(x, y);
        this.setDirection(newDirection);
        float newMagnitude = EpicalMath.calculateMagnitude(x, y, HALF * CONTROL_VIEW_MAXIMUM_LIMIT );
        this.setMagnitude(newMagnitude);
    }

    public void nullTargetSpeed() {
        this.setMagnitude(0);
    }
}
