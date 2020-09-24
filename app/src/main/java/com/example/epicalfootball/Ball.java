package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;

public class Ball extends FieldObject {

    public Ball() {
        //this.position = new Position(-7.32f / 2 - 0.1f, 35);
        this.position = new Position(0.2f, 20);
        this.speed = new Vector((float)-Math.PI / 2,2f);
        //this.speed = new Vector();
        this.radius = BALL_RADIUS;
    }

    public void updateSpeed(float timeFactor) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration = 0.2f;
            //float deceleration = 0.0f;

            deceleratedSpeedMagnitude = oldSpeedMagnitude - deceleration * timeFactor;

            if (deceleratedSpeedMagnitude < 0) {
                deceleratedSpeedMagnitude = 0;
            }
        }

        this.speed.setMagnitude(deceleratedSpeedMagnitude);
    }

    public void shiftTowardsPlayerDirectionOnBounce(float collisionDirection, float centersDistance, float playerDirection, float playerControlAngle) {
        float angleIncrement = playerControlAngle / 10;
        float factor = CONTROL_BOUNCE_SHIFT_MULTIPLIER;
        float shiftedCollisionDirection;

        if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + factor * angleIncrement);

            if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerDirection);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - factor * angleIncrement);

            if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerDirection);
            }
        }

        if (EpicalMath.directionDifference(playerDirection, collisionDirection) > 0) {
            shiftedCollisionDirection = collisionDirection + factor * angleIncrement;

            if (EpicalMath.directionDifference(playerDirection, shiftedCollisionDirection) < 0) {
                shiftedCollisionDirection = playerDirection;
            }
        } else {
            shiftedCollisionDirection = collisionDirection - factor * angleIncrement;

            if (EpicalMath.directionDifference(playerDirection, shiftedCollisionDirection) > 0) {
                shiftedCollisionDirection = playerDirection;
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
        float angleIncrement = player.getControlAngle() / 5;
        float newBallSpeed;

        if (EpicalMath.directionDifference(playerSpeedDirection, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + angleIncrement);

            if (EpicalMath.directionDifference(playerSpeedDirection, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerSpeedDirection);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - angleIncrement);

            if (EpicalMath.directionDifference(playerSpeedDirection, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerSpeedDirection);
            }
        }

        if (EpicalMath.directionDifference(playerOrientation, playerToBallDirection) > 0) {
            shiftedPlayerToBallDirection = playerToBallDirection + angleIncrement;

            if (EpicalMath.directionDifference(playerOrientation, shiftedPlayerToBallDirection) < 0) {
                shiftedPlayerToBallDirection = playerOrientation;
            }
        } else {
            shiftedPlayerToBallDirection = playerToBallDirection - angleIncrement;

            if (EpicalMath.directionDifference(playerOrientation, shiftedPlayerToBallDirection) > 0) {
                shiftedPlayerToBallDirection = playerOrientation;
            }
        }

        this.getPosition().copyPosition(player.getPosition());
        this.getPosition().addVector(shiftedPlayerToBallDirection, centersDistance);

        if (player.getSpeed().getMagnitude() < this.getSpeed().getMagnitude()) {
            newBallSpeed = this.getSpeed().getMagnitude() - CONTROL_CONE_SPEED_MULTIPLIER * player.getControlAngle();

            if (newBallSpeed < player.getSpeed().getMagnitude()) {
                newBallSpeed = player.getSpeed().getMagnitude();
            }

            this.getSpeed().setMagnitude(newBallSpeed);
        }/* else {
            newBallSpeed = this.getSpeed().getMagnitude() + CONTROL_CONE_SPEED_MULTIPLIER * player.getControlAngle();

            if (newBallSpeed > player.getSpeed().getMagnitude()) {
                newBallSpeed = player.getSpeed().getMagnitude();
            }
        }*/

        //this.getSpeed().setMagnitude(newBallSpeed);
    }
}
