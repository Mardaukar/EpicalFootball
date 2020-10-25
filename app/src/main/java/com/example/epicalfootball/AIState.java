package com.example.epicalfootball;

import android.util.Log;

import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.items.OutfieldPlayer;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;

import static com.example.epicalfootball.Constants.*;
import static com.example.epicalfootball.MatchState.random;

public class AIState {
    private final MatchState matchState;
    private final Goalkeeper goalkeeper;
    private final OutfieldPlayer outfieldPlayer;
    private final AIAction goalkeeperAIAction;
    private float aiDecisionCounter;
    private boolean shotPerceived;
    private float previousAnticipatedBallDirection;
    private float previousJudgedBallDirection;
    private float previousJudgedPositionRadius;

    public AIState(MatchState matchState) {
        this.matchState = matchState;
        this.goalkeeper = matchState.getGoalkeeper();
        this.outfieldPlayer = matchState.getOutfieldPlayer();
        this.goalkeeperAIAction = new AIAction();
        this.shotPerceived = false;
        this.previousAnticipatedBallDirection = DOWN;
        this.previousJudgedBallDirection = DOWN;
        this.previousJudgedPositionRadius = 0;
    }

    public void update(float elapsed) {
        if (matchState.isGoalkeeperHoldingBall()) {
            goalkeeperAIAction.setAction(HOLD_ACTION);
            aiDecisionCounter = 0;
        } else if (!matchState.isCanScore()) {
            goalkeeperAIAction.setAction(MOVE_ACTION);
            goalkeeperAIAction.getTargetPosition().copyFromPosition(GOALKEEPER_STARTING_POSITION);
        } else {
            Ball ball = matchState.getBall();
            Position maxLeftBasePosition = new Position(-(GOAL_WIDTH * HALF + POST_RADIUS), goalkeeper.getRadius() + POST_RADIUS);
            Position maxRightBasePosition = new Position(GOAL_WIDTH * HALF + POST_RADIUS, goalkeeper.getRadius() + POST_RADIUS);

             if (ball.getSpeed().getMagnitude() >= GK_AI_BALL_SPEED_PERCEIVED_SHOT && ball.getSpeed().getDirection() < 0 && !this.shotPerceived) {
                this.shotPerceived = true;
                this.aiDecisionCounter = goalkeeper.getReflexes();
            } else if (ball.getSpeed().getMagnitude() < GK_AI_BALL_SPEED_PERCEIVED_SHOT || ball.getSpeed().getDirection() >= 0) {
                if (this.shotPerceived) {
                    this.shotPerceived = false;
                    goalkeeperAIAction.setAction(HOLD_ACTION);
                }
            }

             if (!shotPerceived) {
                 if (goalkeeper.getAfterKickTimer() > 0) {
                     goalkeeperAIAction.setAction(MOVE_ACTION);
                     goalkeeperAIAction.getTargetPosition().copyFromPosition(GOALKEEPER_STARTING_POSITION);
                 } else {
                     boolean ballInsideBox = !(ball.getPosition().getX() < -BOX_WIDTH * HALF) && !(ball.getPosition().getX() > BOX_WIDTH * HALF) && !(ball.getPosition().getY() > BOX_HEIGHT) && !(ball.getPosition().getY() < TOUCHLINE);
                     float interceptionDistanceFactor = FULL;

                     if (!ballInsideBox) {
                         interceptionDistanceFactor = GK_AI_OUTSIDE_BOX_INTERCEPT_FACTOR;
                     }

                     if (EpicalMath.calculateDistance(goalkeeper.getPosition(), ball.getPosition()) < GK_AI_INTERCEPT_DISTANCE_TO_BALL_FACTOR * interceptionDistanceFactor * EpicalMath.calculateDistance(outfieldPlayer.getPosition(), ball.getPosition())) {
                         goalkeeperAIAction.setAction(RUN_TO_BALL_ACTION);
                         aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
                     } else if (EpicalMath.calculateDistanceFromOrigo(ball.getPosition()) <= goalkeeper.getGoalkeepingIntelligenceInterceptingRadius()) {
                         goalkeeperAIAction.setAction(INTERCEPT_ACTION);
                         aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
                     }
                 }
             }

            if (aiDecisionCounter <= 0) {
                if (shotPerceived) {
                    float ballDirection = ball.getSpeed().getDirection();
                    EpicalMath.logDegrees("ball direction", ballDirection);
                    float ballToGoalkeeperDirection = EpicalMath.convertToDirection(ball.getPosition(), goalkeeper.getPosition());
                    EpicalMath.logDegrees("ballToGoalkeeper", ballToGoalkeeperDirection);
                    float savingTargetDirection;

                    if (ballDirection > ballToGoalkeeperDirection) {
                        savingTargetDirection = EpicalMath.sanitizeDirection(ballDirection + QUARTER_CIRCLE);
                        EpicalMath.logDegrees("saving td", savingTargetDirection);
                        float goalkeeperToRightBasePositionDirection = EpicalMath.convertToDirection(goalkeeper.getPosition(), maxRightBasePosition);

                        if (savingTargetDirection > QUARTER_CIRCLE && savingTargetDirection < goalkeeperToRightBasePositionDirection) {
                            savingTargetDirection = goalkeeperToRightBasePositionDirection;
                        }
                    } else {
                        savingTargetDirection = EpicalMath.sanitizeDirection(ballDirection - QUARTER_CIRCLE);
                        EpicalMath.logDegrees("saving td", savingTargetDirection);
                        float goalkeeperToLeftBasePositionDirection = EpicalMath.convertToDirection(goalkeeper.getPosition(), maxLeftBasePosition);

                        if (savingTargetDirection < QUARTER_CIRCLE && savingTargetDirection > goalkeeperToLeftBasePositionDirection) {
                            savingTargetDirection = goalkeeperToLeftBasePositionDirection;
                        }
                    }

                    EpicalMath.logDegrees("corrected std", savingTargetDirection);

                    float savingTargetDistance = EpicalMath.calculateDistance(ball.getPosition(), goalkeeper.getPosition()) * (float)Math.sin(EpicalMath.absoluteAngleBetweenDirections(ballDirection, ballToGoalkeeperDirection));
                    goalkeeperAIAction.setTargetPosition(goalkeeper.getPosition().clonePosition().addPositionVector(savingTargetDirection, savingTargetDistance));
                    goalkeeperAIAction.setAction(SAVE_ACTION);
                    aiDecisionCounter = goalkeeper.getReflexes() + 2000;
                } else {
                    float maxLeftBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxLeftBasePosition);
                    float maxRightBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxRightBasePosition);

                    Position anticipatedBallPosition = new Position();
                    anticipatedBallPosition.copyFromPosition(ball.getPosition());
                    anticipatedBallPosition.moveBySpeed(ball, goalkeeper.getGoalkeepingIntelligencePositioningAnticipation());

                    float anticipatedBallDirection = EpicalMath.convertToDirectionFromOrigo(anticipatedBallPosition);
                    float basePositionRadius = EpicalMath.calculateDistanceFromOrigo(maxRightBasePosition);
                    float judgedBallDirection = anticipatedBallDirection + (random.nextFloat() - HALF) * goalkeeper.getGoalkeepingIntelligencePositioningDirectionJudgementError();
                    float judgedPositionRadius;

                    if (anticipatedBallDirection + GK_AI_BALL_DIRECTION_PERCEIVED_SAME < this.previousAnticipatedBallDirection) {
                        if (judgedBallDirection > this.previousJudgedBallDirection) {
                            judgedBallDirection = this.previousJudgedBallDirection;
                        }

                        judgedPositionRadius = basePositionRadius + (random.nextFloat() - HALF) * goalkeeper.getGoalkeepingIntelligencePositioningRadiusJudgementError();
                    } else if (anticipatedBallDirection - GK_AI_BALL_DIRECTION_PERCEIVED_SAME > this.previousAnticipatedBallDirection) {
                        if (judgedBallDirection < this.previousJudgedBallDirection) {
                            judgedBallDirection = this.previousJudgedBallDirection;
                        }

                        judgedPositionRadius = basePositionRadius + (random.nextFloat() - HALF) * goalkeeper.getGoalkeepingIntelligencePositioningRadiusJudgementError();
                    } else {
                        if (EpicalMath.absoluteAngleBetweenDirections(judgedBallDirection, anticipatedBallDirection) > EpicalMath.absoluteAngleBetweenDirections(this.previousJudgedBallDirection, anticipatedBallDirection)) {
                            judgedBallDirection = this.previousJudgedBallDirection;
                        }

                        judgedPositionRadius = basePositionRadius + (previousJudgedPositionRadius - basePositionRadius) * HALF;
                    }

                    if (judgedBallDirection > maxRightBasePositionDirection && judgedBallDirection < maxLeftBasePositionDirection) {
                        goalkeeperAIAction.setTargetPosition(EpicalMath.convertToPositionFromOrigo(judgedBallDirection, judgedPositionRadius));
                    } else if (Math.abs(judgedBallDirection) > QUARTER_CIRCLE) {
                        goalkeeperAIAction.getTargetPosition().copyFromPosition(maxLeftBasePosition);
                    } else {
                        goalkeeperAIAction.getTargetPosition().copyFromPosition(maxRightBasePosition);
                    }

                    goalkeeperAIAction.setAction(MOVE_ACTION);
                    aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
                    previousAnticipatedBallDirection = anticipatedBallDirection;
                    previousJudgedBallDirection = judgedBallDirection;
                    previousJudgedPositionRadius = judgedPositionRadius;
                }
            } else {
                aiDecisionCounter -= elapsed;
            }
        }
    }

    public Position getInterceptingTargetPosition() {
        Ball ball = matchState.getBall();
        float ballDistanceFromOrigo = EpicalMath.calculateDistanceFromOrigo(ball.getPosition());
        float goalkeeperDistanceFromOrigo = EpicalMath.calculateDistanceFromOrigo(goalkeeper.getPosition());

        if (ballDistanceFromOrigo > goalkeeperDistanceFromOrigo) {
            float ballDirection = EpicalMath.convertToDirectionFromOrigo(ball.getPosition());
            float targetPositionDistanceFromOrigo = goalkeeperDistanceFromOrigo + (ballDistanceFromOrigo - goalkeeperDistanceFromOrigo) / goalkeeper.getGoalkeepingIntelligenceInterceptingPrecaution();
            Position targetPosition = new Position();
            targetPosition.addPositionVector(ballDirection, targetPositionDistanceFromOrigo);
            return targetPosition;
        } else {
            return ball.getPosition();
        }
    }

    public AIAction getGoalkeeperAIAction() {
        return goalkeeperAIAction;
    }
}


