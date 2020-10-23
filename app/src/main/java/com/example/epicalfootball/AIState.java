package com.example.epicalfootball;

import android.util.Log;

import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;

import static com.example.epicalfootball.Constants.*;
import static com.example.epicalfootball.MatchState.random;

public class AIState {
    private final MatchState matchState;
    private final Goalkeeper goalkeeper;
    private final AIAction goalkeeperAIAction;
    private float aiDecisionCounter;
    private boolean shotPerceived;
    private float previousAnticipatedBallDirection;
    private float previousJudgedBallDirection;
    private float previousJudgedPositionRadius;

    public AIState(MatchState matchState) {
        this.matchState = matchState;
        this.goalkeeper = matchState.getGoalkeeper();
        this.goalkeeperAIAction = new AIAction();
        this.shotPerceived = false;
        this.previousAnticipatedBallDirection = DOWN;
        this.previousJudgedBallDirection = DOWN;
        this.previousJudgedPositionRadius = 0;
    }

    public void update(float elapsed) {
        if (matchState.isGoalkeeperHoldingBall()) {
            goalkeeperAIAction.setAction(HOLD_ACTION);
        } else {
            Ball ball = matchState.getBall();

            if (ball.getSpeed().getMagnitude() >= GK_AI_BALL_SPEED_PERCEIVED_SHOT && !this.shotPerceived) {
                this.shotPerceived = true;
                this.aiDecisionCounter = goalkeeper.getReflexes();
            } else if (ball.getSpeed().getMagnitude() < GK_AI_BALL_SPEED_PERCEIVED_SHOT) {
                this.shotPerceived = false;
            }

            if (aiDecisionCounter <= 0) {
                if (shotPerceived) {
                    //goalkeeperAIAction.setAction(SAVE_ACTION);
                    //aiDecisionCounter = goalkeeper.getReflexes();
                } else if (EpicalMath.calculateDistanceFromOrigo(ball.getPosition()) <= goalkeeper.getGoalkeepingIntelligenceInterceptingRadius()) {
                    //goalkeeperAIAction.setTargetPosition(matchState.getGoalkeeper().getPosition());
                    //goalkeeperAIAction.setAction(INTERCEPT_ACTION);
                    //aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
                } else {
                    Position maxLeftBasePosition = new Position(-(GOAL_WIDTH * HALF + POST_RADIUS), goalkeeper.getRadius() + POST_RADIUS);
                    Position maxRightBasePosition = new Position(GOAL_WIDTH * HALF + POST_RADIUS, goalkeeper.getRadius() + POST_RADIUS);
                    float maxLeftBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxLeftBasePosition);
                    float maxRightBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxRightBasePosition);

                    Position anticipatedBallPosition = new Position();
                    anticipatedBallPosition.clonePosition(ball.getPosition());
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
                        goalkeeperAIAction.setTargetPosition(maxLeftBasePosition);
                    } else {
                        goalkeeperAIAction.setTargetPosition(maxRightBasePosition);
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

    public AIAction getGoalkeeperAIAction() {
        return goalkeeperAIAction;
    }
}


