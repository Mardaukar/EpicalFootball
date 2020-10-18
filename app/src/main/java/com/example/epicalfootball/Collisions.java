package com.example.epicalfootball;

import android.graphics.RectF;
import android.util.Log;

import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.Circle;
import com.example.epicalfootball.items.GoalPost;
import com.example.epicalfootball.items.OutfieldPlayer;
import com.example.epicalfootball.items.Player;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.Constants.*;

public class Collisions {

    public static void handlePlayerLineSegmentCollision(RectF line, Player player) {
        if (EpicalMath.checkIntersect(line, player)) {
            if (line.height() < line.width()) {
                if (player.getPosition().getY() < line.centerY()) {
                    player.getPosition().setY(line.top - player.getRadius());
                    player.removeSpeedComponent(DOWN);
                } else {
                    player.getPosition().setY(line.bottom + player.getRadius());
                    player.removeSpeedComponent(UP);
                }
            } else {
                if (player.getPosition().getX() < line.centerX()) {
                    player.getPosition().setX(line.left - player.getRadius());
                    player.removeSpeedComponent(RIGHT);
                } else {
                    player.getPosition().setX(line.right + player.getRadius());
                    player.removeSpeedComponent(LEFT);
                }
            }
        }
    }

    public static void handleGoalPostPlayerCollision(GoalPost goalPost, Player player) {
        if (EpicalMath.checkIntersect(goalPost, player)) {
            float radiusSum = goalPost.getRadius() + player.getRadius();
            float goalPostToCollisionDirection = EpicalMath.convertToDirection(goalPost.getPosition(), player.getPosition());
            float playerToCollisionDirection = EpicalMath.convertToDirection(player.getPosition(), goalPost.getPosition());

            player.getPosition().setPosition(goalPost.getPosition());
            player.getPosition().addVector(goalPostToCollisionDirection, radiusSum);
            player.removeSpeedComponent(playerToCollisionDirection);
        }
    }

    public static void handlePlayerPlayerCollision(Player player1, Player player2) {
        if (EpicalMath.checkIntersect(player1, player2)) {
            float player1ToCollisionDirection = EpicalMath.convertToDirection(player1.getPosition(), player2.getPosition());
            player1.removeSpeedComponent(player1ToCollisionDirection);
            float player2ToCollisionDirection = EpicalMath.convertToDirection(player2.getPosition(), player1.getPosition());
            player2.removeSpeedComponent(player2ToCollisionDirection);

            float radiusSum = player1.getRadius() + player2.getRadius();
            player1.setPosition(player2.getPosition());
            player1.getPosition().addVector(player2ToCollisionDirection, radiusSum);
        }
    }










    public static void handleBallLineSegmentCollision(RectF line, Ball ball) {
        if (EpicalMath.checkIntersect(line, ball)) {
            ball.getSpeed().setMagnitude(ball.getSpeed().getMagnitude() * LINE_SEGMENT_COLLISION_SPEED_MULTIPLIER);

            if (line.height() < line.width()) {
                if (ball.getPosition().getY() < line.centerY()) {
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
                if (ball.getPosition().getX() < line.centerX()) {
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

    public static void handleBallCircleCollision(Circle circle, Ball ball) {
        if (EpicalMath.checkIntersect(circle, ball)) {
            float centersDistance = circle.getRadius() + ball.getRadius();
            float collisionDirection = EpicalMath.convertToDirection(ball.getPosition(), circle.getPosition());
            float fieldObjectCollisionDifference = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, ball.getSpeed().getDirection());

            ball.getPosition().setPosition(circle.getPosition());
            ball.getPosition().addVector(collisionDirection, centersDistance);

            if (fieldObjectCollisionDifference > Math.PI * HALF) {
                ball.getSpeed().bounceDirection(collisionDirection);
            }

            float newDifference = EpicalMath.absoluteAngleBetweenDirections(collisionDirection, ball.getSpeed().getDirection());
            ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float)Math.cos(newDifference)) * ball.getSpeed().getMagnitude() * POST_COLLISION_SPEED_MULTIPLIER);
        }
    }

    public static boolean handlePlayerBallCollision(OutfieldPlayer outfieldPlayer, Ball ball, boolean readyToShoot, Position aimTarget) {
        float playerCollisionDirection = EpicalMath.convertToDirectionFromOrigo(ball.getPosition().getX() - outfieldPlayer.getPosition().getX(), ball.getPosition().getY() - outfieldPlayer.getPosition().getY());
        float playerOrientationCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, outfieldPlayer.getOrientation());
        boolean ballControl = playerOrientationCollisionAngle <= outfieldPlayer.getControlAngle() && outfieldPlayer.getKickRecoveryTimer() == 0;

        if (EpicalMath.checkIntersect(outfieldPlayer, ball)) {
            if (ballControl && readyToShoot) {
                float aimDirection = EpicalMath.convertToDirectionFromOrigo(aimTarget.getX() - ball.getPosition().getX(), aimTarget.getY() - ball.getPosition().getY());
                float collisionToAimTargetAngle = EpicalMath.absoluteAngleBetweenDirections(aimDirection, playerCollisionDirection);

                if (collisionToAimTargetAngle < Math.PI / 2) {
                    return true;
                }
            }

                float centersDistance = outfieldPlayer.getRadius() + ball.getRadius();
                float playerSpeedCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, outfieldPlayer.getSpeed().getDirection());
                float ballCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, ball.getSpeed().getDirection());
                Vector impulse;
                float impactMagnitude;
                boolean bounce = ballCollisionAngle > Math.PI * HALF && ball.getSpeed().getMagnitude() > 0;

                if (bounce) {
                    ball.getSpeed().bounceDirection(playerCollisionDirection);
                    float newBallCollisionAngle = EpicalMath.absoluteAngleBetweenDirections(playerCollisionDirection, ball.getSpeed().getDirection());
                    if (ballControl) {
                        ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * outfieldPlayer.getControlFirstTouch());
                    } else {
                        ball.getSpeed().setMagnitude((1 - COLLISION_ANGLE_SPEED_MULTIPLIER * (float) Math.cos(newBallCollisionAngle)) * ball.getSpeed().getMagnitude() * BALL_PLAYER_COLLISION_SPEED_MULTIPLIER);
                    }

                    impactMagnitude = (float) (Math.cos(playerSpeedCollisionAngle) * outfieldPlayer.getSpeed().getMagnitude() * outfieldPlayer.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                } else {
                    impactMagnitude = (float) (Math.cos(playerSpeedCollisionAngle) * outfieldPlayer.getSpeed().getMagnitude() * outfieldPlayer.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED - Math.cos(ballCollisionAngle) * ball.getSpeed().getMagnitude());

                    if (impactMagnitude < 0) {
                        impactMagnitude = 0;
                    }
                }

                ball.getPosition().setPosition(outfieldPlayer.getPosition());
                impulse = new Vector(playerCollisionDirection, impactMagnitude);
                ball.getSpeed().addVector(impulse);

                if (ballControl) {
                    outfieldPlayer.setAimRecoveryTimer(0);

                    if (bounce) {
                        ball.shiftTowardsPlayerDirectionOnBounce(playerCollisionDirection, centersDistance, outfieldPlayer.getOrientation(), outfieldPlayer.getControlAngle());
                    } else {
                        ball.getPosition().addVector(playerCollisionDirection, centersDistance);

                        if (outfieldPlayer.getTargetSpeed().getMagnitude() == 1) {
                            if (outfieldPlayer.getSpeed().getMagnitude() * outfieldPlayer.getFullMagnitudeSpeed() >= ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
                                float targetMagnitude = outfieldPlayer.getMagnitudeToOrientation() + DRIBBLING_KICK_FORWARD;
                                Log.d("targetMagnitude", "" + targetMagnitude);

                                if (targetMagnitude > outfieldPlayer.getDribblingTarget()) {
                                    targetMagnitude = outfieldPlayer.getDribblingTarget();
                                }

                                outfieldPlayer.setKickRecoveryTimer(PLAYER_KICK_RECOVERY_TIME);

                                ball.getSpeed().setMagnitude(targetMagnitude * outfieldPlayer.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                            }
                        }
                    }

                    if (outfieldPlayer.getSpeed().getMagnitude() > outfieldPlayer.getDribbling()) {
                        outfieldPlayer.getSpeed().setMagnitude(outfieldPlayer.getDribbling());
                    }
                } else {
                    ball.getPosition().addVector(playerCollisionDirection, centersDistance);
                }

        }

        //CONTROL CONE
        Circle controlCircle = new Circle(outfieldPlayer.getPosition(), outfieldPlayer.getControlRadius());
        if (EpicalMath.checkIntersect(controlCircle, ball)) {
            if (ballControl) {
                ball.shiftWithControlCone(outfieldPlayer);
            }
        }

        return false;
    }
}
