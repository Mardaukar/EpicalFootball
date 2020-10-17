package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;
import static com.example.epicalfootball.activities.MenuActivity.playerAttributes;

public class OutfieldPlayer extends Player {
    private float controlAngle;
    private float controlRadius;
    private float controlBallSpeed;
    private float controlFirstTouch;
    private float dribbling;
    private float dribblingTarget;
    private float shotPower;
    private float accuracyDistance;
    public float accuracyTargetDot;
    private float accuracyGaussianFactor;
    private float finishingMidShotPower;
    private float finishingTargetGoalSpeed;
    private float longShotsAccuracy;
    private float longShotsMidShotPower;
    private float longShotsTargetGoalSpeed;

    private float aimRecoveryTimer = 0;
    private float kickRecoveryTimer = 0;

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        this.position = new Position(x, y);
    }

    public OutfieldPlayer() {
        this.position = OUTFIELD_PLAYER_STARTING_POSITION;
        this.orientation = OUTFIELD_PLAYER_STARTING_ORIENTATION;
        this.targetSpeed = new TargetSpeedVector();
        this.speed = new Vector();

        this.radius = MIN_REACH_VALUE + REACH_VALUE_INCREMENT * playerAttributes.get("reach");
        this.fullMagnitudeSpeed = MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * playerAttributes.get("speed");
        this.acceleration = (MIN_ACCELERATION_VALUE + ACCELERATION_VALUE_INCREMENT * playerAttributes.get("acceleration")) / (MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * playerAttributes.get("speed"));
        this.accelerationTurn = MIN_ACCELERATION_TURN + ACCELERATION_TURN_INCREMENT * playerAttributes.get("acceleration");
        this.controlAngle = MIN_BALL_CONTROL_ANGLE + BALL_CONTROL_ANGLE_INCREMENT * playerAttributes.get("ballControl");
        this.controlRadius = this.radius + MIN_BALL_CONTROL_RADIUS + BALL_CONTROL_RADIUS_INCREMENT * playerAttributes.get("ballControl");
        this.controlBallSpeed = MIN_BALL_CONTROL_BALL_SPEED + BALL_CONTROL_BALL_SPEED_INCREMENT * playerAttributes.get("ballControl");
        this.controlFirstTouch = MIN_BALL_CONTROL_FIRST_TOUCH + BALL_CONTROL_FIRST_TOUCH_INCREMENT * playerAttributes.get("ballControl");
        this.dribbling = MIN_DRIBBLING_LIMIT + DRIBBLING_LIMIT_INCREMENT * playerAttributes.get("dribbling");
        this.dribblingTarget = MIN_DRIBBLING_TARGET + DRIBBLING_TARGET_INCREMENT * playerAttributes.get("dribbling");
        this.shotPower = MIN_SHOT_POWER_VALUE + SHOT_POWER_VALUE_INCREMENT * playerAttributes.get("shotPower");
        this.accuracyDistance = MIN_ACCURACY_DISTANCE + ACCURACY_DISTANCE_INCREMENT * playerAttributes.get("accuracy");
        this.accuracyTargetDot = MIN_ACCURACY_TARGET_DOT + ACCURACY_TARGET_DOT_INCREMENT * playerAttributes.get("accuracy");
        this.accuracyGaussianFactor = MIN_ACCURACY_GAUSSIAN_FACTOR + ACCURACY_GAUSSIAN_FACTOR_INCREMENT * playerAttributes.get("accuracy");
        this.finishingTargetGoalSpeed = MIN_TARGET_GOAL_SPEED + TARGET_GOAL_SPEED_INCREMENT * playerAttributes.get("finishing");
        this.finishingMidShotPower = MIN_MID_SHOT_POWER + MID_SHOT_POWER_INCREMENT * playerAttributes.get("finishing");
        this.longShotsTargetGoalSpeed = MIN_TARGET_GOAL_SPEED + TARGET_GOAL_SPEED_INCREMENT * playerAttributes.get("longShots");
        this.longShotsMidShotPower = MIN_MID_SHOT_POWER + MID_SHOT_POWER_INCREMENT * playerAttributes.get("longShots");
        this.longShotsAccuracy = MIN_LONG_SHOTS_ACCURACY + LONG_SHOTS_ACCURACY_INCREMENT * playerAttributes.get("longShots");
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

    public void updateOrientation(float timeFactor) {
        if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) > 0) {
            this.orientation += timeFactor * this.accelerationTurn;

            if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) < 0) {
                this.orientation = this.targetSpeed.getDirection();
            }
        } else {
            this.orientation -= timeFactor * this.accelerationTurn;

            if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) > 0) {
                this.orientation = this.targetSpeed.getDirection();
            }
        }
    }

    public void updateKickRecoveryTimer(float elapsed) {
        if (this.kickRecoveryTimer > 0) {
            this.kickRecoveryTimer -= elapsed;

            if (this.kickRecoveryTimer < 0 ) {
                this.kickRecoveryTimer = 0;
            }
        }
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

    public float getControlBallSpeed() {
        return controlBallSpeed;
    }

    public void setControlBallSpeed(float controlBallSpeed) {
        this.controlBallSpeed = controlBallSpeed;
    }

    public float getControlFirstTouch() {
        return controlFirstTouch;
    }

    public void setControlFirstTouch(float controlFirstTouch) {
        this.controlFirstTouch = controlFirstTouch;
    }

    public float getDribbling() {
        return dribbling;
    }

    public void setDribbling(float dribbling) {
        this.dribbling = dribbling;
    }

    public float getDribblingTarget() {
        return dribblingTarget;
    }

    public void setDribblingTarget(float dribblingTarget) {
        this.dribblingTarget = dribblingTarget;
    }

    public float getShotPower() {
        return shotPower;
    }

    public void setShotPower(float shotPower) {
        this.shotPower = shotPower;
    }

    public float getAccuracyDistance() {
        return accuracyDistance;
    }

    public void setAccuracyDistance(float accuracyDistance) {
        this.accuracyDistance = accuracyDistance;
    }

    public float getAccuracyTargetDot() {
        return accuracyTargetDot;
    }

    public void setAccuracyTargetDot(float accuracyTargetDot) {
        this.accuracyTargetDot = accuracyTargetDot;
    }

    public float getAccuracyGaussianFactor() {
        return accuracyGaussianFactor;
    }

    public void setAccuracyGaussianFactor(float accuracyGaussianFactor) {
        this.accuracyGaussianFactor = accuracyGaussianFactor;
    }

    public float getFinishingMidShotPower() {
        return finishingMidShotPower;
    }

    public void setFinishingMidShotPower(float finishingMidShotPower) {
        this.finishingMidShotPower = finishingMidShotPower;
    }

    public float getFinishingTargetGoalSpeed() {
        return finishingTargetGoalSpeed;
    }

    public void setFinishingTargetGoalSpeed(float finishingTargetGoalSpeed) {
        this.finishingTargetGoalSpeed = finishingTargetGoalSpeed;
    }

    public float getLongShotsAccuracy() {
        return longShotsAccuracy;
    }

    public void setLongShotsAccuracy(float longShotsAccuracy) {
        this.longShotsAccuracy = longShotsAccuracy;
    }

    public float getLongShotsMidShotPower() {
        return longShotsMidShotPower;
    }

    public void setLongShotsMidShotPower(float longShotsMidShotPower) {
        this.longShotsMidShotPower = longShotsMidShotPower;
    }

    public float getLongShotsTargetGoalSpeed() {
        return longShotsTargetGoalSpeed;
    }

    public void setLongShotsTargetGoalSpeed(float longShotsTargetGoalSpeed) {
        this.longShotsTargetGoalSpeed = longShotsTargetGoalSpeed;
    }

    public float getAimRecoveryTimer() {
        return aimRecoveryTimer;
    }

    public void setAimRecoveryTimer(float aimRecoveryTimer) {
        this.aimRecoveryTimer = aimRecoveryTimer;
    }

    public float getKickRecoveryTimer() {
        return kickRecoveryTimer;
    }

    public void setKickRecoveryTimer(float kickRecoveryTimer) {
        this.kickRecoveryTimer = kickRecoveryTimer;
    }
}
