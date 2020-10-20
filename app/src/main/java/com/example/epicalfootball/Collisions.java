package com.example.epicalfootball;

import android.graphics.RectF;

import com.example.epicalfootball.items.*;
import com.example.epicalfootball.math.*;
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
            float goalPostToPlayerDirection = EpicalMath.convertToDirection(goalPost.getPosition(), player.getPosition());
            float playerToGoalPostDirection = EpicalMath.convertToDirection(player.getPosition(), goalPost.getPosition());

            player.getPosition().setPosition(goalPost.getPosition());
            player.getPosition().addVector(goalPostToPlayerDirection, radiusSum);
            player.removeSpeedComponent(playerToGoalPostDirection);
        }
    }

    public static void handlePlayerPlayerCollision(Player player1, Player player2) {
        if (EpicalMath.checkIntersect(player1, player2)) {
            float player1ToPlayer2Direction = EpicalMath.convertToDirection(player1.getPosition(), player2.getPosition());
            player1.removeSpeedComponent(player1ToPlayer2Direction);
            float player2ToPlayer1Direction = EpicalMath.convertToDirection(player2.getPosition(), player1.getPosition());
            player2.removeSpeedComponent(player2ToPlayer1Direction);

            float radiusSum = player1.getRadius() + player2.getRadius();
            player1.setPosition(player2.getPosition());
            player1.getPosition().addVector(player2ToPlayer1Direction, radiusSum);
        }
    }

    public static void handleBallGoalNetCollision(GoalNet goalNet, Ball ball) {
        if (EpicalMath.checkIntersect(goalNet, ball)) {
            ball.getSpeed().setMagnitude(ball.getSpeed().getMagnitude() * GOAL_NET_COLLISION_SPEED_MULTIPLIER);

            if (goalNet.height() < goalNet.width()) {
                if (ball.getPosition().getY() < goalNet.centerY()) {
                    if (ball.getSpeed().getDirection() > RIGHT) {
                        ball.getSpeed().bounceDirection(UP);
                    }
                    ball.getPosition().setY(goalNet.top - ball.getRadius());
                } else {
                    if (ball.getSpeed().getDirection() < RIGHT) {
                        ball.getSpeed().bounceDirection(DOWN);
                    }
                    ball.getPosition().setY(goalNet.bottom + ball.getRadius());
                }
            } else {
                if (ball.getPosition().getX() < goalNet.centerX()) {
                    if (Math.abs(ball.getSpeed().getDirection()) < DOWN) {
                        ball.getSpeed().bounceDirection(LEFT);
                    }
                    ball.getPosition().setX(goalNet.left - ball.getRadius());
                } else {
                    if (Math.abs(ball.getSpeed().getDirection()) > DOWN) {
                        ball.getSpeed().bounceDirection(RIGHT);
                    }
                    ball.getPosition().setX(goalNet.right + ball.getRadius());
                }
            }
        }
    }

    public static void handleBallGoalPostCollision(GoalPost goalPost, Ball ball) {
        if (EpicalMath.checkIntersect(goalPost, ball)) {
            float radiusSum = goalPost.getRadius() + ball.getRadius();
            float goalPostToBallDirection = EpicalMath.convertToDirection(goalPost.getPosition(), ball.getPosition());
            float goalPostToBallBallSpeedAngle = EpicalMath.absoluteAngleBetweenDirections(goalPostToBallDirection, ball.getSpeed().getDirection());

            if (goalPostToBallBallSpeedAngle > QUARTER_CIRCLE) {
                ball.getSpeed().bounceDirection(goalPostToBallDirection);
                float multiplierAngle = goalPostToBallBallSpeedAngle - QUARTER_CIRCLE;
                float speedMultiplier = FULL - (float)Math.sin(multiplierAngle) * (FULL - GOAL_POST_COLLISION_SPEED_MULTIPLIER);
                ball.getSpeed().setMagnitude(speedMultiplier * ball.getSpeed().getMagnitude());
            }

            ball.getPosition().setPosition(goalPost.getPosition());
            ball.getPosition().addVector(goalPostToBallDirection, radiusSum);
        }
    }













    public static boolean handlePlayerBallCollision(OutfieldPlayer player, Ball ball, boolean readyToShoot, Position aimTarget) {
        if (EpicalMath.checkIntersect(player, ball)) {
            float playerToBallDirection = EpicalMath.convertToDirection(player.getPosition(), ball.getPosition());
            float playerToBallPlayerOrientationAngle = EpicalMath.absoluteAngleBetweenDirections(playerToBallDirection, player.getOrientation());
            boolean ballControl = playerToBallPlayerOrientationAngle <= player.getControlAngle() && player.getKickRecoveryTimer() == 0;

            if (ballControl && readyToShoot) {
                float aimDirection = EpicalMath.convertToDirection(ball.getPosition(), aimTarget);
                float playerToBallAimTargetAngle = EpicalMath.absoluteAngleBetweenDirections(aimDirection, playerToBallDirection);

                if (playerToBallAimTargetAngle < QUARTER_CIRCLE) {
                    return true;
                }
            }

            float playerToBallPlayerSpeedAngle = EpicalMath.absoluteAngleBetweenDirections(playerToBallDirection, player.getSpeed().getDirection());
            float playerToBallBallSpeedAngle = EpicalMath.absoluteAngleBetweenDirections(playerToBallDirection, ball.getSpeed().getDirection());
            float impactMagnitude;
            boolean bounce = playerToBallBallSpeedAngle > QUARTER_CIRCLE && ball.getSpeed().getMagnitude() > 0;
            float multiplierAngle = playerToBallBallSpeedAngle - QUARTER_CIRCLE;

            if (bounce) {
                ball.getSpeed().bounceDirection(playerToBallDirection);
                float speedMultiplier;

                if (ballControl) {
                    speedMultiplier = player.getControlFirstTouch();
                } else {
                    speedMultiplier = (FULL - (float)Math.sin(multiplierAngle) * (FULL - BALL_PLAYER_COLLISION_SPEED_MULTIPLIER)) * BALL_PLAYER_COLLISION_SPEED_BASE_MULTIPLIER;
                }

                ball.getSpeed().setMagnitude(speedMultiplier * ball.getSpeed().getMagnitude());
                impactMagnitude = (float) (Math.cos(playerToBallPlayerSpeedAngle) * player.getSpeed().getMagnitude() * player.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED);
            } else {
                impactMagnitude = (float) (Math.cos(playerToBallPlayerSpeedAngle) * player.getSpeed().getMagnitude() * player.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED - Math.cos(playerToBallBallSpeedAngle) * ball.getSpeed().getMagnitude());

                if (impactMagnitude < 0) {
                    impactMagnitude = 0;
                }
            }

            Vector impulse = new Vector(playerToBallDirection, impactMagnitude);
            ball.getSpeed().addVector(impulse);
            float radiusSum = player.getRadius() + ball.getRadius();

            if (ballControl) {
                player.setAimRecoveryTimer(0);

                if (bounce) {
                    ball.shiftTowardsPlayerDirectionOnBounce(player);
                } else {
                    ball.getPosition().setPosition(player.getPosition());
                    ball.getPosition().addVector(playerToBallDirection, radiusSum);

                    if (player.getTargetSpeed().getMagnitude() == 1) {
                        if (player.getSpeed().getMagnitude() * player.getFullMagnitudeSpeed() >= ball.getSpeed().getMagnitude() * BALL_REFERENCE_SPEED) {
                            float newPlayerToBallDirection = EpicalMath.convertToDirection(player.getPosition(), ball.getPosition());

                            if (EpicalMath.absoluteAngleBetweenDirections(newPlayerToBallDirection, player.getTargetSpeed().getDirection()) < DRIBBLING_KICK_ANGLE) {
                                if (EpicalMath.absoluteAngleBetweenDirections(newPlayerToBallDirection, player.getOrientation()) < DRIBBLING_KICK_ANGLE) {
                                    float targetMagnitude = player.getSpeed().getMagnitude() + DRIBBLING_KICK_FORWARD;

                                    if (targetMagnitude > player.getDribblingTarget()) {
                                        targetMagnitude = player.getDribblingTarget();
                                    }

                                    ball.getSpeed().setDirection(player.getTargetSpeed().getDirection());
                                    ball.getSpeed().setMagnitude(targetMagnitude * player.getFullMagnitudeSpeed() / BALL_REFERENCE_SPEED);
                                    player.setKickRecoveryTimer(PLAYER_KICK_RECOVERY_TIME);
                                }
                            }
                        }
                    }
                }

                if (player.getSpeed().getMagnitude() > player.getDribbling()) {
                    player.getSpeed().setMagnitude(player.getDribbling());
                }
            } else {
                ball.getPosition().setPosition(player.getPosition());
                ball.getPosition().addVector(playerToBallDirection, radiusSum);
            }

        }

        return false;
    }

    public static void handlePlayerControlConeBallCollision(float ballTimeFactor, OutfieldPlayer player, Ball ball) {
        Circle controlCircle = new Circle(player.getPosition(), player.getControlRadius());
        if (EpicalMath.checkIntersect(controlCircle, ball)) {
            float playerToBallDirection = EpicalMath.convertToDirection(player.getPosition(), ball.getPosition());
            float playerToBallPlayerOrientationAngle = EpicalMath.absoluteAngleBetweenDirections(playerToBallDirection, player.getOrientation());
            boolean ballControl = playerToBallPlayerOrientationAngle <= player.getControlAngle() && player.getKickRecoveryTimer() == 0;

            if (ballControl) {
                ball.shiftWithControlCone(ballTimeFactor, player);
            }
        }
    }
}
