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
        float angleBit = playerControlAngle / 10;
        float factor = CONTROL_BOUNCE_SHIFT_MULTIPLIER;
        float shiftedCollisionDirection;

        if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) > 0) {
            this.getSpeed().setDirection(this.getSpeed().getDirection() + factor * angleBit);

            if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) < 0) {
                this.getSpeed().setDirection(playerDirection);
            }
        } else {
            this.getSpeed().setDirection(this.getSpeed().getDirection() - factor * angleBit);

            if (EpicalMath.directionDifference(playerDirection, this.getSpeed().getDirection()) > 0) {
                this.getSpeed().setDirection(playerDirection);
            }
        }

        if (EpicalMath.directionDifference(playerDirection, collisionDirection) > 0) {
            shiftedCollisionDirection = collisionDirection + factor * angleBit;

            if (EpicalMath.directionDifference(playerDirection, shiftedCollisionDirection) < 0) {
                shiftedCollisionDirection = playerDirection;
            }
        } else {
            shiftedCollisionDirection = collisionDirection - factor * angleBit;

            if (EpicalMath.directionDifference(playerDirection, shiftedCollisionDirection) > 0) {
                shiftedCollisionDirection = playerDirection;
            }
        }

        this.getPosition().addVector(shiftedCollisionDirection, centersDistance);
    }
}
