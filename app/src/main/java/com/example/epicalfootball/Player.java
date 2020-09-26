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
        this.speed = new Vector((float)Math.PI/3f,0f);
        this.position = new Position(0, 0);
        this.targetSpeed = new TargetSpeedVector();
        this.controlAngle = MIN_BALLCONTROL_VALUE + BALLCONTROL_VALUE_INCREMENT * PLAYER_BALLCONTROL;
        this.controlRadius = 1.2f;
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
            float newSpeedX = (float)(Math.cos(speed.getDirection()) * deceleratedSpeedMagnitude);
            float newSpeedY = (float)(Math.sin(speed.getDirection()) * deceleratedSpeedMagnitude);
            float targetSpeedX = (float)(Math.cos(targetSpeed.getDirection()) * targetSpeed.getMagnitude());
            float targetSpeedY = (float)(Math.sin(targetSpeed.getDirection()) * targetSpeed.getMagnitude());

            if (newSpeedX >= 0) {
                if (targetSpeedX > newSpeedX) {
                    newSpeedX += Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedX -= Math.abs(Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedX < newSpeedX) {
                    newSpeedX += Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedX += Math.abs(Math.cos(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            }

            if (newSpeedY >= 0) {
                if (targetSpeedY > newSpeedY) {
                    newSpeedY += Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedY -= Math.abs(Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedY < newSpeedY) {
                    newSpeedY += Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor;
                } else {
                    newSpeedY += Math.abs(Math.sin(targetSpeed.getDirection()) * (PLAYER_BASE_DECELERATION + this.acceleration) * timeFactor);
                }
            }

            float newDirection = EpicalMath.convertToDirection(newSpeedX, newSpeedY);
            float newSpeedMagnitude = EpicalMath.calculateMagnitude(newSpeedX, newSpeedY);

            if (newSpeedMagnitude > oldSpeedMagnitude) {
                newSpeedMagnitude = (newSpeedMagnitude - oldSpeedMagnitude) * (1 - oldSpeedMagnitude * 7/8) + oldSpeedMagnitude;
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
