package com.example.epicalfootball;

import android.util.Log;

public class Player extends FieldObject {
    private TargetSpeedVector targetSpeed;

    public Player() {
        this.speed = new Vector((float)Math.PI/3f,1f);
        this.targetSpeed = new TargetSpeedVector();
        this.position = new Position();
        this.radius = 0.8f;
    }

    public void updateSpeed(float timeFactor, boolean decelerateOn) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration;

            if (decelerateOn) {
                deceleration = 0.7f;
            } else {
                deceleration = 0.3f;
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
                    newSpeedX += Math.cos(targetSpeed.getDirection()) * 0.7 * timeFactor;
                } else {
                    newSpeedX -= Math.abs(Math.cos(targetSpeed.getDirection()) * 0.7 * timeFactor);
                }
            } else {
                if (targetSpeedX < newSpeedX) {
                    newSpeedX += Math.cos(targetSpeed.getDirection()) * 0.7 * timeFactor;
                } else {
                    newSpeedX += Math.abs(Math.cos(targetSpeed.getDirection()) * 0.7 * timeFactor);
                }
            }

            if (newSpeedY >= 0) {
                if (targetSpeedY > newSpeedY) {
                    newSpeedY += Math.sin(targetSpeed.getDirection()) * 0.7 * timeFactor;
                } else {
                    newSpeedY -= Math.abs(Math.sin(targetSpeed.getDirection()) * 0.7 * timeFactor);
                }
            } else {
                if (targetSpeedY < newSpeedY) {
                    newSpeedY += Math.sin(targetSpeed.getDirection()) * 0.7 * timeFactor;
                } else {
                    newSpeedY += Math.abs(Math.sin(targetSpeed.getDirection()) * 0.7 * timeFactor);
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
}
