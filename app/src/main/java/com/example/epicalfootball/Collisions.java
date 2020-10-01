package com.example.epicalfootball;

import android.graphics.RectF;
import android.util.Log;

import static com.example.epicalfootball.Constants.*;

public class Collisions {

    public static void handleCircleCollision(Circle circle, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(circle.getX(), circle.getY(), circle.getRadius(), fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            float centersDistance = circle.getRadius() + fieldObject.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(fieldObject.getPosition().getX() - circle.getX(), fieldObject.getPosition().getY() - circle.getY());
            float fieldObjectCollisionDifference = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, fieldObject.getSpeed().getDirection());

            fieldObject.getPosition().setPosition(circle.getPosition());
            fieldObject.getPosition().addVector(collisionDirection, centersDistance);

            if (fieldObjectCollisionDifference > Math.PI * HALF) {
                fieldObject.getSpeed().bounceDirection(collisionDirection);
            }

            float newDifference = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, fieldObject.getSpeed().getDirection());
            fieldObject.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float)Math.cos(newDifference)) * fieldObject.getSpeed().getMagnitude() * POST_COLLISION_SPEED_MULTIPLIER);
        }
    }

    public static void handlePlayerBallCollision(Player player, Ball ball) {
        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            float centersDistance = player.getRadius() + ball.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(ball.getPosition().getX() - player.getPosition().getX(), ball.getPosition().getY() - player.getPosition().getY());
            float playerSpeedCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, player.getSpeed().getDirection());
            float playerDirectionCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, player.getTargetSpeed().getDirection());
            float ballCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, ball.getSpeed().getDirection());
            Vector impulse;
            float impactMagnitude;
            boolean ballControl = playerDirectionCollisionAngle <= player.getControlAngle();
            boolean bounce = ballCollisionAngle > Math.PI * HALF && ball.getSpeed().getMagnitude() > 0;

            if (bounce) {
                ball.getSpeed().bounceDirection(collisionDirection);
                float newBallCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, ball.getSpeed().getDirection());
                if (ballControl) {
                    ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * player.getControlFirstTouch());
                } else {
                    ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * BALL_PLAYER_COLLISION_SPEED_MULTIPLIER);
                }

                impactMagnitude = (float)(Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED);
            } else {
                impactMagnitude = (float)(Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                if (impactMagnitude < 0) {
                    impactMagnitude = 0;
                }
            }

            ball.getPosition().setPosition(player.getPosition());
            impulse = new Vector(collisionDirection, impactMagnitude);
            ball.getSpeed().addVector(impulse);

            if (ballControl) {
                if (bounce) {
                    ball.shiftTowardsPlayerDirectionOnBounce(collisionDirection, centersDistance, player.getTargetSpeed().getDirection(), player.getControlAngle());
                } else {
                    ball.getPosition().addVector(collisionDirection, centersDistance);

                    if (player.getTargetSpeed().getMagnitude() == 1) {
                        if (player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() > ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
                            float targetMagnitude = player.getSpeed().getMagnitude() + DRIBBLING_KICK_FORWARD;

                            if (targetMagnitude > 1) {
                                targetMagnitude = 1;
                            }

                            ball.getSpeed().setMagnitude(targetMagnitude * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                        }
                    }
                }

                if (player.getSpeed().getMagnitude() > player.getDribbling()) {
                    player.getSpeed().setMagnitude(player.getDribbling());
                }
            } else {
                ball.getPosition().addVector(collisionDirection, centersDistance);
            }
        }

        //Control cone
        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getControlRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            float ballDirectionFromPlayer = EpicalMath.convertToDirection(ball.getPosition().getX() - player.getPosition().getX(), ball.getPosition().getY() - player.getPosition().getY());
            float playerOrientationFromBall = EpicalMath.absoluteAngleBetweenDirections(ballDirectionFromPlayer, player.getTargetSpeed().getDirection());

            if (playerOrientationFromBall <= player.getControlAngle()) {
                ball.shiftWithControlCone(player);
            }
        }
    }

    public static void handleLineSegmentCollision(RectF line, FieldObject fieldObject) {
        if (EpicalMath.checkIntersect(line, fieldObject.getPosition().getX(), fieldObject.getPosition().getY(), fieldObject.getRadius())) {
            fieldObject.getSpeed().setMagnitude(fieldObject.getSpeed().getMagnitude() * LINESEGMENT_COLLISION_SPEED_MULTIPLIER);

            if (line.height() < line.width()) {
                if (fieldObject.position.getY() < line.centerY()) {
                    if (fieldObject.getSpeed().getDirection() > 0) {
                        fieldObject.getSpeed().bounceDirection((float)-Math.PI * HALF);
                    }
                    fieldObject.getPosition().setY(line.top - fieldObject.getRadius());
                } else {
                    if (fieldObject.getSpeed().getDirection() < 0) {
                        fieldObject.getSpeed().bounceDirection((float)Math.PI * HALF);
                    }
                    fieldObject.getPosition().setY(line.bottom + fieldObject.getRadius());
                }
            } else {
                if (fieldObject.position.getX() < line.centerX()) {
                    if (Math.abs(fieldObject.getSpeed().getDirection()) < Math.PI * HALF) {
                        fieldObject.getSpeed().bounceDirection((float) Math.PI);
                    }
                    fieldObject.getPosition().setX(line.left - fieldObject.getRadius());
                } else {
                    if (Math.abs(fieldObject.getSpeed().getDirection()) > Math.PI * HALF) {
                        fieldObject.getSpeed().bounceDirection(0);
                    }
                    fieldObject.getPosition().setX(line.right + fieldObject.getRadius());
                }
            }
        }
    }
}
