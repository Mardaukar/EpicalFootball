package com.example.epicalfootball;

import android.util.Log;

import static com.example.epicalfootball.Constants.*;

public class Player extends FieldObject {
    private TargetSpeedVector targetSpeed;
    private float recoveryTimer = 0;

    private float controlAngle;
    private float controlRadius;
    private float controlBallSpeed;
    private float controlFirstTouch;
    private float dribbling;
    private float acceleration;
    private float shotpower;
    private float accuracyDistance;
    private float finishingTargetGoalSpeed;
    private float longshotsTargetGoalSpeed;
    private float finishingMidShotPower;
    private float longshotsMidShotPower;
    private float longshotsAccuracy;
    private float dribblingTarget;

    public Player() {
        this.radius = MIN_REACH_VALUE + REACH_VALUE_INCREMENT * PLAYER_REACH_ATTRIBUTE;
        this.speed = new Vector();
        this.position = PLAYER_STARTING_POSITION;
        this.targetSpeed = new TargetSpeedVector();
        this.controlAngle = MIN_BALLCONTROL_ANGLE + BALLCONTROL_ANGLE_INCREMENT * PLAYER_BALLCONTROL_ATTRIBUTE;
        this.controlRadius = this.radius + MIN_BALLCONTROL_RADIUS + BALLCONTROL_RADIUS_INCREMENT * PLAYER_BALLCONTROL_ATTRIBUTE;
        this.controlBallSpeed = MIN_BALLCONTROL_BALL_SPEED + BALLCONTROL_BALL_SPEED_INCREMENT * PLAYER_BALLCONTROL_ATTRIBUTE;
        this.controlFirstTouch = MIN_BALLCONTROL_FIRST_TOUCH + BALLCONTROL_FIRST_TOUCH_INCREMENT * PLAYER_BALLCONTROL_ATTRIBUTE;
        this.dribbling = MIN_DRIBBLING_VALUE + DRIBBLING_VALUE_INCREMENT * PLAYER_DRIBBLING_ATTRIBUTE;
        this.dribblingTarget = MIN_DRIBBLING_TARGET + DRIBBLING_TARGET_INCREMENT * PLAYER_DRIBBLING_ATTRIBUTE;
        this.magnitudeSpeed = MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * PLAYER_SPEED_ATTRIBUTE;
        this.acceleration = (MIN_ACCELERATION_VALUE + ACCELERATION_VALUE_INCREMENT * PLAYER_ACCELERATION_ATTRIBUTE) / (MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * PLAYER_SPEED_ATTRIBUTE);
        this.accuracyDistance = MIN_ACCURACY_DISTANCE + ACCURACY_DISTANCE_INCREMENT * PLAYER_ACCURACY_ATTRIBUTE;
        this.shotpower = MIN_SHOTPOWER_VALUE + SHOTPOWER_VALUE_INCREMENT * PLAYER_SHOTPOWER_ATTRIBUTE;
        this.finishingTargetGoalSpeed = MIN_TARGET_GOAL_SPEED + TARGET_GOAL_SPEED_INCREMENT * PLAYER_FINISHING_ATTRIBUTE;
        this.longshotsTargetGoalSpeed = MIN_TARGET_GOAL_SPEED + TARGET_GOAL_SPEED_INCREMENT * PLAYER_LONGSHOTS_ATTRIBUTE;
        this.finishingMidShotPower = MIN_MID_SHOT_POWER + MID_SHOT_POWER_INCREMENT * PLAYER_FINISHING_ATTRIBUTE;
        this.longshotsMidShotPower = MIN_MID_SHOT_POWER + MID_SHOT_POWER_INCREMENT * PLAYER_LONGSHOTS_ATTRIBUTE;
        this.longshotsAccuracy = MIN_LONGSHOTS_ACCURACY + LONGSHOTS_ACCURACY_INCREMENT * PLAYER_LONGSHOTS_ATTRIBUTE;
    }

    public void updateSpeed(float timeFactor, boolean decelerateOn, Ball ball) {
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
            float acceleration;

            if (EpicalMath.checkIntersect(this.getPosition().getX(), this.getPosition().getY(), this.getControlRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
                acceleration = PLAYER_BASE_DECELERATION + this.acceleration * this.dribbling;
            } else {
                acceleration = PLAYER_BASE_DECELERATION + this.acceleration;
            }

            if (newSpeedMagnitudeX >= 0) {
                if (targetSpeedMagnitudeX > newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX -= Math.abs(Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeX < newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX += Math.abs(Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            }

            if (newSpeedMagnitudeY >= 0) {
                if (targetSpeedMagnitudeY > newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY -= Math.abs(Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeY < newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY += Math.abs(Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor);
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

    public float getMagnitudeToOrientation() {
        float angle = EpicalMath.absoluteAngleBetweenDirections(this.getSpeed().getDirection(), this.getTargetSpeed().getDirection());

        if (angle > Math.PI / 2) {
            return 0;
        } else {
            return (float)Math.cos(angle) * this.getSpeed().getMagnitude();
        }
    }

    public TargetSpeedVector getTargetSpeed() {
        return targetSpeed;
    }

    public float getControlAngle() {
        return controlAngle;
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

    public float getControlBallSpeed() {
        return controlBallSpeed;
    }

    public void setControlBallSpeed(float controlBallSpeed) {
        this.controlBallSpeed = controlBallSpeed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getControlFirstTouch() {
        return controlFirstTouch;
    }

    public void setControlFirstTouch(float controlFirstTouch) {
        this.controlFirstTouch = controlFirstTouch;
    }

    public float getAccuracyDistance() {
        return accuracyDistance;
    }

    public float getLongshotsAccuracy() {
        return longshotsAccuracy;
    }

    public float getFinishingTargetGoalSpeed() {
        return finishingTargetGoalSpeed;
    }

    public float getLongshotsTargetGoalSpeed() {
        return longshotsTargetGoalSpeed;
    }

    public float getFinishingMidShotPower() {
        return finishingMidShotPower;
    }

    public float getLongshotsMidShotPower() {
        return longshotsMidShotPower;
    }

    public float getDribblingTarget() {
        return dribblingTarget;
    }

    public float getShotpower() {
        return shotpower;
    }

    public void updateRecoveryTimer(float elapsed) {
        if (this.recoveryTimer > 0) {
            this.recoveryTimer -= elapsed;

            if (this.recoveryTimer < 0 ) {
                this.recoveryTimer = 0;
            }
        }
    }

    public float getRecoveryTimer() {
        return recoveryTimer;
    }

    public void setRecoveryTimer(float recoveryTimer) {
        this.recoveryTimer = recoveryTimer;
    }
}
