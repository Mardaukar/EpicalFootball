package com.example.epicalfootball.items;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;
import com.example.epicalfootball.math.Vector;

import java.util.Random;

import static com.example.epicalfootball.Constants.*;

public class Ball extends Circle {
    private final float fullMagnitudeSpeed;
    private Vector speed;
    private Random random = new Random();

    public Ball() {
        this.radius = BALL_RADIUS;
        this.fullMagnitudeSpeed = BALL_REFERENCE_SPEED;
        this.position = new Position();
        this.speed = new Vector();
    }

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * this.fullMagnitudeSpeed * timeFactor);
        this.position = new Position(x, y);
    }

    public void updateSpeed(float timeFactor) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration = BALL_BASE_DECELERATION;

            deceleratedSpeedMagnitude = oldSpeedMagnitude - deceleration * timeFactor;

            if (deceleratedSpeedMagnitude < 0) {
                deceleratedSpeedMagnitude = 0;
            }
        }

        this.speed.setMagnitude(deceleratedSpeedMagnitude);
    }

    public void shiftTowardsPlayerDirectionOnBounce(float collisionDirection, float centersDistance, float playerOrientation, float playerControlAngle) {
        float angleIncrement = playerControlAngle * CONTROL_BOUNCE_SHIFT_MULTIPLIER;
        float shiftedCollisionDirection;

        if (EpicalMath.angleBetweenDirections(playerOrientation, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerOrientation, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerOrientation);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerOrientation, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerOrientation);
            }
        }

        if (EpicalMath.angleBetweenDirections(playerOrientation, collisionDirection) > 0) {
            shiftedCollisionDirection = collisionDirection + angleIncrement;

            if (EpicalMath.angleBetweenDirections(playerOrientation, shiftedCollisionDirection) < 0) {
                shiftedCollisionDirection = playerOrientation;
            }
        } else {
            shiftedCollisionDirection = collisionDirection - angleIncrement;

            if (EpicalMath.angleBetweenDirections(playerOrientation, shiftedCollisionDirection) > 0) {
                shiftedCollisionDirection = playerOrientation;
            }
        }

        this.getPosition().addVector(shiftedCollisionDirection, centersDistance);
    }

    public void shiftWithControlCone(OutfieldPlayer outfieldPlayer) {
        float centersDistance = EpicalMath.calculateDistance(outfieldPlayer.getPosition().getX(), outfieldPlayer.getPosition().getY(), this.getPosition().getX(), this.getPosition().getY());
        float playerToBallDirection = EpicalMath.convertToDirectionFromOrigo(this.getPosition().getX() - outfieldPlayer.getPosition().getX(), this.getPosition().getY() - outfieldPlayer.getPosition().getY());
        float shiftedPlayerToBallDirection;
        float playerTargetSpeedDirection = outfieldPlayer.getTargetSpeed().getDirection();
        float playerOrientation = outfieldPlayer.getOrientation();
        float angleIncrement = outfieldPlayer.getControlAngle() * CONTROL_CONE_SHIFT_MULTIPLIER;
        float playerSpeed = outfieldPlayer.getSpeed().getMagnitude() * outfieldPlayer.getFullMagnitudeSpeed();
        float newBallSpeedMagnitude;

        if (EpicalMath.angleBetweenDirections(playerTargetSpeedDirection, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerTargetSpeedDirection, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerTargetSpeedDirection);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerTargetSpeedDirection, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerTargetSpeedDirection);
            }
        }

        if (EpicalMath.angleBetweenDirections(playerOrientation, playerToBallDirection) > 0) {
            shiftedPlayerToBallDirection = playerToBallDirection + angleIncrement;

            if (EpicalMath.angleBetweenDirections(playerOrientation, shiftedPlayerToBallDirection) < 0) {
                shiftedPlayerToBallDirection = playerOrientation;
            }
        } else {
            shiftedPlayerToBallDirection = playerToBallDirection - angleIncrement;

            if (EpicalMath.angleBetweenDirections(playerOrientation, shiftedPlayerToBallDirection) > 0) {
                shiftedPlayerToBallDirection = playerOrientation;
            }
        }

        this.getPosition().setPosition(outfieldPlayer.getPosition());
        this.getPosition().addVector(shiftedPlayerToBallDirection, centersDistance);

        if (playerSpeed < this.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
            newBallSpeedMagnitude = this.getSpeed().getMagnitude() - outfieldPlayer.getControlBallSpeed();

            if (newBallSpeedMagnitude * BALL_REFERENCE_SPEED < playerSpeed) {
                newBallSpeedMagnitude = playerSpeed / BALL_REFERENCE_SPEED;
            }

            this.getSpeed().setMagnitude(newBallSpeedMagnitude);
        }
    }

    public void shoot(OutfieldPlayer outfieldPlayer, float shotPowerMeter, Position aimTarget) {
        float failedGaussianFactor;
        float shotPowerFactor;
        if (shotPowerMeter <= SHOT_POWER_METER_OPTIMAL) {
            failedGaussianFactor = 0;
            shotPowerFactor = shotPowerMeter / SHOT_POWER_METER_OPTIMAL;
        } else if (shotPowerMeter >= SHOT_POWER_METER_HIGHER_LIMIT) {
            failedGaussianFactor = FAILED_SHOT_ACCURACY_GAUSSIAN_FACTOR;
            shotPowerFactor = FAILED_SHOT_POWER_FACTOR;
        } else {
            failedGaussianFactor = FAILED_SHOT_ACCURACY_GAUSSIAN_FACTOR * HALF * (FULL + (shotPowerMeter - SHOT_POWER_METER_OPTIMAL) / (SHOT_POWER_METER_HIGHER_LIMIT - SHOT_POWER_METER_OPTIMAL));
            shotPowerFactor = FULL;
        }

        float intendedDirection = EpicalMath.convertToDirectionFromOrigo(aimTarget.getX() - this.getPosition().getX(), aimTarget.getY() - this.getPosition().getY());
        float realDirection = intendedDirection + (float)random.nextGaussian() * outfieldPlayer.getAccuracyGaussianFactor() + failedGaussianFactor;
        EpicalMath.sanitizeDirection(realDirection);

        this.getSpeed().setDirection(realDirection);
        this.getSpeed().setMagnitude(shotPowerFactor * outfieldPlayer.getShotPower());
    }

    public float getRadius() {
        return radius;
    }

    public float getFullMagnitudeSpeed() {
        return fullMagnitudeSpeed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }
}
