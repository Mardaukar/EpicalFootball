package com.example.epicalfootball.items;

import android.util.Log;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.control.TargetSpeedVector;
import com.example.epicalfootball.math.Position;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.Constants.*;

public class Player extends Circle {
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

    public Position getSeizingPosition(Ball ball) {
        if (ball.getSpeed().getMagnitude() == 0) {
            return ball.getPosition();
        } else {
            float ballDirection = ball.getSpeed().getDirection();
            float ballToPlayerDirection = EpicalMath.convertToDirection(ball.getPosition(), this.getPosition());
            float perpendicularTargetDirection;

            if (EpicalMath.directionLeftFromDirection(ballToPlayerDirection, ballDirection)) {
                perpendicularTargetDirection = EpicalMath.sanitizeDirection(ballDirection + QUARTER_CIRCLE);
            } else {
                perpendicularTargetDirection = EpicalMath.sanitizeDirection(ballDirection - QUARTER_CIRCLE);
            }

            float perpendicularTargetDistance = EpicalMath.calculateDistance(ball.getPosition(), this.getPosition()) * (float) Math.sin(EpicalMath.absoluteAngleBetweenDirections(ballDirection, ballToPlayerDirection));
            Position perpendicularTargetPosition = this.getPosition().clonePosition().addPositionVector(perpendicularTargetDirection, perpendicularTargetDistance);

            float timeToPerpendicularTargetPosition;
            if (this.getSpeed().getMagnitude() > 0 && EpicalMath.absoluteAngleBetweenDirections(perpendicularTargetDirection, this.getSpeed().getDirection()) < QUARTER_CIRCLE) {
                timeToPerpendicularTargetPosition = perpendicularTargetDistance / (this.getSpeed().getMagnitude() * this.getFullMagnitudeSpeed());

                if (timeToPerpendicularTargetPosition > MAX_SEIZE_ANTICIPATION_TIME) {
                    timeToPerpendicularTargetPosition = MAX_SEIZE_ANTICIPATION_TIME;
                }
            } else {
                timeToPerpendicularTargetPosition = MAX_SEIZE_ANTICIPATION_TIME;
            }

            Position anticipatedBallPosition = ball.getPosition().clonePosition();
            anticipatedBallPosition.moveBySpeed(ball, timeToPerpendicularTargetPosition);

            if (EpicalMath.absoluteAngleBetweenDirections(EpicalMath.convertToDirection(ball.getPosition(), perpendicularTargetPosition), ball.getSpeed().getDirection()) < QUARTER_CIRCLE) {
                return EpicalMath.getPositionBetweenPositions(perpendicularTargetPosition, anticipatedBallPosition);
            } else {
                return anticipatedBallPosition;
            }
        }
    }


    public void setTargetSpeedDirectionByTargetPosition(Position targetPosition) {
        float playerToTargetPositionDirection = EpicalMath.convertToDirection(this.getPosition(), targetPosition);

        if (this.getSpeed().getMagnitude() > 0) {
            float currentSpeedDirection = this.getSpeed().getDirection();
            float newTargetSpeedDirection;

            if (EpicalMath.absoluteAngleBetweenDirections(playerToTargetPositionDirection, currentSpeedDirection) > QUARTER_CIRCLE) {
                newTargetSpeedDirection = EpicalMath.reversedDirection(currentSpeedDirection);
                newTargetSpeedDirection = EpicalMath.directionBetweenDirections(playerToTargetPositionDirection, newTargetSpeedDirection);
            } else if (EpicalMath.calculateDistance(this.position, targetPosition) < SEIZE_MIRRORING_DISTANCE_TOLERANCE) {
                newTargetSpeedDirection = playerToTargetPositionDirection;
            } else {
                newTargetSpeedDirection = EpicalMath.mirroredDirection(playerToTargetPositionDirection, currentSpeedDirection);
                newTargetSpeedDirection = EpicalMath.directionBetweenDirections(playerToTargetPositionDirection, newTargetSpeedDirection);
            }

            this.targetSpeed.setDirection(newTargetSpeedDirection);
        } else {
            this.targetSpeed.setDirection(playerToTargetPositionDirection);
        }
    }

    public float calculateStoppingDistance() {
        float stoppingAcceleration = (PLAYER_BASE_DECELERATION + this.acceleration) * fullMagnitudeSpeed;
        float currentSpeed = this.speed.getMagnitude() * fullMagnitudeSpeed;
        return currentSpeed * currentSpeed / stoppingAcceleration / 2;
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
