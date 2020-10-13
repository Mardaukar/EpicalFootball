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

    public static boolean handlePlayerBallCollision(Player player, Ball ball, boolean readyToShoot, Position aimTarget) {
        float playerCollisionDirection = EpicalMath.convertToDirection(ball.getPosition().getX() - player.getPosition().getX(), ball.getPosition().getY() - player.getPosition().getY());
        float playerOrientationCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, player.getTargetSpeed().getDirection());
        boolean ballControl = playerOrientationCollisionAngle <= player.getControlAngle() && player.getKickRecoveryTimer() == 0;

        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            if (ballControl && readyToShoot) { ///Add angle criteria
                float aimDirection = EpicalMath.convertToDirection(aimTarget.getX() - ball.getPosition().getX(), aimTarget.getY() - ball.getPosition().getY());
                float collisionToAimTargetAngle = EpicalMath.absoluteAngleBetweenDirections(aimDirection, playerCollisionDirection);

                if (collisionToAimTargetAngle < Math.PI / 2) {
                    return true;
                }
            }

                float centersDistance = player.getRadius() + ball.getRadius();
                float playerSpeedCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, player.getSpeed().getDirection());
                float ballCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, ball.getSpeed().getDirection());
                Vector impulse;
                float impactMagnitude;
                boolean bounce = ballCollisionAngle > Math.PI * HALF && ball.getSpeed().getMagnitude() > 0;

                if (bounce) {
                    ball.getSpeed().bounceDirection(playerCollisionDirection);
                    float newBallCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, ball.getSpeed().getDirection());
                    if (ballControl) {
                        ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * player.getControlFirstTouch());
                    } else {
                        ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * BALL_PLAYER_COLLISION_SPEED_MULTIPLIER);
                    }

                    impactMagnitude = (float) (Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                } else {
                    impactMagnitude = (float) (Math.cos(playerSpeedCollisionAngle) * player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                    if (impactMagnitude < 0) {
                        impactMagnitude = 0;
                    }
                }

                ball.getPosition().setPosition(player.getPosition());
                impulse = new Vector(playerCollisionDirection, impactMagnitude);
                ball.getSpeed().addVector(impulse);

                if (ballControl) {
                    if (bounce) {
                        ball.shiftTowardsPlayerDirectionOnBounce(playerCollisionDirection, centersDistance, player.getTargetSpeed().getDirection(), player.getControlAngle());
                    } else {
                        ball.getPosition().addVector(playerCollisionDirection, centersDistance);

                        if (player.getTargetSpeed().getMagnitude() == 1) {
                            if (player.getSpeed().getMagnitude() * player.getMagnitudeSpeed() >= ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
                                float targetMagnitude = player.getMagnitudeToOrientation() + DRIBBLING_KICK_FORWARD;
                                Log.d("targetMagnitude", "" + targetMagnitude);

                                if (targetMagnitude > player.getDribblingTarget()) {
                                    targetMagnitude = player.getDribblingTarget();
                                }

                                ball.getSpeed().setMagnitude(targetMagnitude * player.getMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                            }
                        }
                    }

                    if (player.getSpeed().getMagnitude() > player.getDribbling()) {
                        player.getSpeed().setMagnitude(player.getDribbling());
                    }
                } else {
                    ball.getPosition().addVector(playerCollisionDirection, centersDistance);
                }

        }

        //CONTROL CONE
        if (EpicalMath.checkIntersect(player.getPosition().getX(), player.getPosition().getY(), player.getControlRadius(), ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            if (ballControl) {
                ball.shiftWithControlCone(player);
            }
        }

        return false;
    }

    public static void handleLineSegmentCollision(RectF line, Ball ball) {
        if (EpicalMath.checkIntersect(line, ball.getPosition().getX(), ball.getPosition().getY(), ball.getRadius())) {
            ball.getSpeed().setMagnitude(ball.getSpeed().getMagnitude() * LINESEGMENT_COLLISION_SPEED_MULTIPLIER);

            if (line.height() < line.width()) {
                if (ball.position.getY() < line.centerY()) {
                    if (ball.getSpeed().getDirection() > 0) {
                        ball.getSpeed().bounceDirection((float)-Math.PI * HALF);
                    }
                    ball.getPosition().setY(line.top - ball.getRadius());
                } else {
                    if (ball.getSpeed().getDirection() < 0) {
                        ball.getSpeed().bounceDirection((float)Math.PI * HALF);
                    }
                    ball.getPosition().setY(line.bottom + ball.getRadius());
                }
            } else {
                if (ball.position.getX() < line.centerX()) {
                    if (Math.abs(ball.getSpeed().getDirection()) < Math.PI * HALF) {
                        ball.getSpeed().bounceDirection((float) Math.PI);
                    }
                    ball.getPosition().setX(line.left - ball.getRadius());
                } else {
                    if (Math.abs(ball.getSpeed().getDirection()) > Math.PI * HALF) {
                        ball.getSpeed().bounceDirection(0);
                    }
                    ball.getPosition().setX(line.right + ball.getRadius());
                }
            }
        }
    }

    public static void handleLineSegmentCollision(RectF line, Player player) {
        if (EpicalMath.checkIntersect(line, player.getPosition().getX(), player.getPosition().getY(), player.getRadius())) {
            if (line.height() < line.width()) {
                if (player.position.getY() < line.centerY()) {
                    /*if (player.getSpeed().getDirection() > 0) {
                        if (player.getSpeed().getDirection() > Math.PI / 2) {
                            player.getSpeed().setDirection((float)Math.PI);
                        } else {
                            player.getSpeed().setDirection(0);
                        }
                    }*/
                    player.getPosition().setY(line.top - player.getRadius());
                } else {
                    /*if (player.getSpeed().getDirection() < 0) {
                        if (player.getSpeed().getDirection() < -Math.PI / 2) {
                            player.getSpeed().setDirection((float)Math.PI);
                        } else {
                            player.getSpeed().setDirection(0);
                        }
                    }*/
                    player.getPosition().setY(line.bottom + player.getRadius());
                }
            } else {
                if (player.position.getX() < line.centerX()) {
                    /*if (Math.abs(player.getSpeed().getDirection()) < Math.PI / 2) {
                        if (player.getSpeed().getDirection() < 0) {
                            player.getSpeed().setDirection((float)-Math.PI / 2);
                        } else {
                            player.getSpeed().setDirection((float)Math.PI / 2);
                        }
                    }*/
                    player.getPosition().setX(line.left - player.getRadius());
                } else {
                    /*if (Math.abs(player.getSpeed().getDirection()) > Math.PI / 2) {
                        if (player.getSpeed().getDirection() < 0) {
                            player.getSpeed().setDirection((float)-Math.PI / 2);
                        } else {
                            player.getSpeed().setDirection((float)Math.PI / 2);
                        }
                    }*/
                    player.getPosition().setX(line.right + player.getRadius());
                }
            }
        }
    }
}
