package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;

public class Ball extends FieldObject {

    public Ball() {
        this.position = BALL_STARTING_POSITION;
        this.speed = new Vector();
        this.speed.setMagnitude(BALL_STARTING_SPEED.getMagnitude());
        this.speed.setDirection(BALL_STARTING_SPEED.getDirection());
        this.radius = BALL_RADIUS;
        this.magnitudeSpeed = BALL_REFERENCE_SPEED;
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

    public void shiftWithControlCone(Player player) {
        float centersDistance = EpicalMath.calculateDistance(player.getPosition().getX(), player.getPosition().getY(), this.getPosition().getX(), this.getPosition().getY());
        float playerToBallDirection = EpicalMath.convertToDirection(this.getPosition().getX() - player.getPosition().getX(), this.getPosition().getY() - player.getPosition().getY());
        float shiftedPlayerToBallDirection;
        float playerSpeedDirection = player.getTargetSpeed().getDirection();
        float playerOrientation = player.getTargetSpeed().getDirection();
        float angleIncrement = player.getControlAngle() * CONTROL_CONE_SHIFT_MULTIPLIER;
        float playerSpeed = player.getSpeed().getMagnitude() * player.getMagnitudeSpeed();
        float newBallSpeedMagnitude;

        if (EpicalMath.angleBetweenDirections(playerSpeedDirection, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerSpeedDirection, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerSpeedDirection);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - angleIncrement);

            if (EpicalMath.angleBetweenDirections(playerSpeedDirection, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerSpeedDirection);
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

        this.getPosition().setPosition(player.getPosition());
        this.getPosition().addVector(shiftedPlayerToBallDirection, centersDistance);

        if (playerSpeed < this.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
            newBallSpeedMagnitude = this.getSpeed().getMagnitude() - player.getControlBallSpeed();

            if (newBallSpeedMagnitude * BALL_REFERENCE_SPEED < playerSpeed) {
                newBallSpeedMagnitude = playerSpeed / BALL_REFERENCE_SPEED;
            }

            this.getSpeed().setMagnitude(newBallSpeedMagnitude);
        }
    }
}
