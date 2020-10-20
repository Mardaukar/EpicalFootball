package com.example.epicalfootball.items;

import android.util.Log;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.TargetSpeedVector;
import com.example.epicalfootball.math.Position;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.Constants.*;

public abstract class Player extends Circle {
    float orientation;
    TargetSpeedVector targetSpeed;
    Vector speed;
    boolean decelerateOn = false;

    float acceleration;
    float accelerationTurn;
    float fullMagnitudeSpeed;

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        this.position.setX(x);
        this.position.setY(y);
    }

    public float getSpeedMagnitudeToOrientation() {
        float angle = EpicalMath.absoluteAngleBetweenDirections(this.getSpeed().getDirection(), this.orientation);

        if (angle > QUARTER_CIRCLE) {
            return 0;
        } else {
            return (float)Math.cos(angle) * this.getSpeed().getMagnitude();
        }
    }

    public float getSpeedMagnitudeToTargetSpeedDirection() {
        float angle = EpicalMath.absoluteAngleBetweenDirections(this.getSpeed().getDirection(), this.getTargetSpeed().getDirection());

        if (angle > QUARTER_CIRCLE) {
            return 0;
        } else {
            return (float)Math.cos(angle) * this.getSpeed().getMagnitude();
        }
    }

    public void removeSpeedComponent(float direction) {
        float speedDirectionObstructionDifference = EpicalMath.angleBetweenDirections(direction, this.getSpeed().getDirection());

        if (Math.abs(speedDirectionObstructionDifference) < QUARTER_CIRCLE) {
            if (speedDirectionObstructionDifference > 0) {
                this.getSpeed().setDirection(EpicalMath.sanitizeDirection(direction - QUARTER_CIRCLE));
            } else {
                this.getSpeed().setDirection(EpicalMath.sanitizeDirection(direction + QUARTER_CIRCLE));
            }

            this.getSpeed().setMagnitude(this.getSpeed().getMagnitude() * (float) Math.abs(Math.sin(speedDirectionObstructionDifference)));
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

    public boolean isDecelerateOn() {
        return decelerateOn;
    }

    public void setDecelerateOn(boolean decelerateOn) {
        this.decelerateOn = decelerateOn;
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
