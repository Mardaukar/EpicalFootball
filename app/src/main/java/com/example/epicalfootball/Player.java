package com.example.epicalfootball;

import android.util.Log;

import static com.example.epicalfootball.Constants.*;

public class Player extends FieldObject {
    private TargetSpeedVector targetSpeed;
    private float controlAngle;
    private float controlRadius;
    private float dribbling;
    private float acceleration;

    public Player() {
        this.radius = MIN_REACH_VALUE + REACH_VALUE_INCREMENT * PLAYER_REACH;
        this.speed = new Vector();
        this.position = PLAYER_STARTING_POSITION;
        this.targetSpeed = new TargetSpeedVector();
        this.controlAngle = MIN_BALLCONTROL_ANGLE + BALLCONTROL_ANGLE_INCREMENT * PLAYER_BALLCONTROL;
        this.controlRadius = this.radius + MIN_BALLCONTROL_RADIUS + BALLCONTROL_RADIUS_INCREMENT * PLAYER_BALLCONTROL;
        this.dribbling = MIN_DRIBBLING_VALUE + DRIBBLING_VALUE_INCREMENT * PLAYER_DRIBBLING;
        this.magnitudeSpeed = MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * PLAYER_SPEED;
        this.acceleration = (MIN_ACCELERATION_VALUE + ACCELERATION_VALUE_INCREMENT * PLAYER_ACCELERATION) / (MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * PLAYER_SPEED);
    }

    public void updateSpeed(float timeFactor, boolean decelerateOn) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration;

            if (decelerateOn) {
                deceleration = PLAYER_BASE_DECELERATION + this.acceleration;
            } else {
                deceleration = PLAYER_BASE_DECELERATION;
            }

            deceleratedSpeedMagnitude = oldSpeedMagnitude - deceleration * timeFactor;

            if (deceleratedSpeedMagnitude < 0) {
                deceleratedSpeedMagnitude = 0;
            }
        }

        if (targetSpeed.getMagnitude() >  0) {
            float newSpeedMagnitudeX = (float)(Math.cos(speed.getDirection()) * deceleratedSpeedMagnitude);
            float newSpeedMagnitudeY = (float)(Math.sin(speed.getDirection()) * deceleratedSpeedMagnitude);
            float targetSpeedMagnitudeX = (float)(Math.cos(targetSpeed.getDirection()) * targetSpeed.getMagnitude());
            float targetSpeedMagnitudeY = (float)(Math.sin(targetSpeed.getDirection()) * targetSpeed.getMagnitude());

            if (newSpeedMagnitudeX >= 0) {
                if (targetSpeedMagnitudeX > newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX -= Math.abs(Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeX < newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX += Math.abs(Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            }

            if (newSpeedMagnitudeY >= 0) {
                if (targetSpeedMagnitudeY > newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY -= Math.abs(Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeY < newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY += Math.abs(Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            }

            float newDirection = EpicalMath.convertToDirection(newSpeedMagnitudeX, newSpeedMagnitudeY);
            float newSpeedMagnitude = EpicalMath.calculateMagnitude(newSpeedMagnitudeX, newSpeedMagnitudeY);

            if (newSpeedMagnitude > oldSpeedMagnitude) {
                newSpeedMagnitude = (newSpeedMagnitude - oldSpeedMagnitude) * (FULL_MAGNITUDE - oldSpeedMagnitude * PLAYER_ACCELERATION_SPEED_CURVE_FACTOR) + oldSpeedMagnitude;
            }

            Vector newSpeed = new Vector(newDirection, newSpeedMagnitude);
            this.setSpeed(newSpeed);

        } else {
            this.speed.setMagnitude(deceleratedSpeedMagnitude);
        }
    }

    public void setTargetSpeed(TargetSpeedVector targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public TargetSpeedVector getTargetSpeed() {
        return targetSpeed;
    }

    public float getControlAngle() {
        return controlAngle;
    }

    public void setControlAngle(float controlAngle) {
        this.controlAngle = controlAngle;
    }

    public float getControlRadius() {
        return controlRadius;
    }

    public void setControlRadius(float controlRadius) {
        this.controlRadius = controlRadius;
    }

    public float getDribbling() {
        return dribbling;
    }

    public void setDribbling(float dribbling) {
        this.dribbling = dribbling;
    }
}
