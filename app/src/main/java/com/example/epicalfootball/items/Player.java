package com.example.epicalfootball.items;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.TargetSpeedVector;
import com.example.epicalfootball.math.Vector;

public abstract class Player extends Circle {
    float orientation;
    TargetSpeedVector targetSpeed;
    Vector speed;

    float acceleration;
    float accelerationTurn;
    float fullMagnitudeSpeed;

    public float getMagnitudeToOrientation() {
        float angle = EpicalMath.absoluteAngleBetweenDirections(this.getSpeed().getDirection(), this.orientation);

        if (angle > Math.PI / 2) {
            return 0;
        } else {
            return (float)Math.cos(angle) * this.getSpeed().getMagnitude();
        }
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public TargetSpeedVector getTargetSpeed() {
        return targetSpeed;
    }

    public void setTargetSpeed(TargetSpeedVector targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAccelerationTurn() {
        return accelerationTurn;
    }

    public void setAccelerationTurn(float accelerationTurn) {
        this.accelerationTurn = accelerationTurn;
    }

    public float getFullMagnitudeSpeed() {
        return fullMagnitudeSpeed;
    }

    public void setFullMagnitudeSpeed(float fullMagnitudeSpeed) {
        this.fullMagnitudeSpeed = fullMagnitudeSpeed;
    }
}
