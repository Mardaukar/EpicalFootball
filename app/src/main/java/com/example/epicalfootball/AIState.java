package com.example.epicalfootball;

import android.util.Log;

import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;

import static com.example.epicalfootball.Constants.*;

public class AIState {
    private final MatchState matchState;
    private final Goalkeeper goalkeeper;
    private final AIAction goalkeeperAIAction;
    private float aiDecisionCounter;
    private boolean shotPerceived;

    public AIState(MatchState matchState) {
        this.matchState = matchState;
        this.goalkeeper = matchState.getGoalkeeper();
        this.goalkeeperAIAction = new AIAction();
        this.shotPerceived = false;
    }

    public void update(float elapsed) {
        //Log.d("AIState", "" + goalkeeperAIAction.getTargetPosition().getX() + " " + goalkeeperAIAction.getTargetPosition().getY());

        Ball ball = matchState.getBall();

        if (ball.getSpeed().getMagnitude() >= GK_AI_BALL_SPEED_PERCEIVED_SHOT && !this.shotPerceived) {
            this.shotPerceived = true;
            this.aiDecisionCounter = goalkeeper.getReflexes();
        } else if(ball.getSpeed().getMagnitude() < GK_AI_BALL_SPEED_PERCEIVED_SHOT) {
            this.shotPerceived = false;
        }

        if (aiDecisionCounter <= 0) {
            if (shotPerceived) {
                //goalkeeperAIAction.setAction(SAVE_ACTION);
                //aiDecisionCounter = goalkeeper.getReflexes();
            } else if(EpicalMath.calculateDistanceFromOrigo(ball.getPosition()) <= goalkeeper.getGoalkeepingIntelligenceInterceptingRadius()) {
                //goalkeeperAIAction.setTargetPosition(matchState.getGoalkeeper().getPosition());
                //goalkeeperAIAction.setAction(INTERCEPT_ACTION);
                //aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
            } else {
                Position maxLeftBasePosition = new Position(-(GOAL_WIDTH * HALF + POST_RADIUS), goalkeeper.getRadius() + POST_RADIUS);
                Position maxRightBasePosition = new Position(GOAL_WIDTH * HALF + POST_RADIUS, goalkeeper.getRadius() + POST_RADIUS);
                float maxLeftBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxLeftBasePosition);
                float maxRightBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxRightBasePosition);
                float basePositionRadius = EpicalMath.calculateDistanceFromOrigo(maxRightBasePosition);

                Position anticipatedBallPosition = new Position();
                anticipatedBallPosition.clonePosition(ball.getPosition());
                anticipatedBallPosition.moveBySpeed(ball, GK_POSITIONING_ANTICIPATION_TIME);

                float anticipatedBallDirection = EpicalMath.convertToDirectionFromOrigo(anticipatedBallPosition);

                if (anticipatedBallDirection > maxRightBasePositionDirection && anticipatedBallDirection < maxLeftBasePositionDirection) {
                    goalkeeperAIAction.setTargetPosition(EpicalMath.convertToPositionFromOrigo(anticipatedBallDirection, basePositionRadius));
                } else if (Math.abs(anticipatedBallDirection) > QUARTER_CIRCLE) {
                    goalkeeperAIAction.setTargetPosition(maxLeftBasePosition);
                } else {
                    goalkeeperAIAction.setTargetPosition(maxRightBasePosition);
                }

                goalkeeperAIAction.setAction(MOVE_ACTION);
                aiDecisionCounter = goalkeeper.getGoalkeepingIntelligenceDecisionTime();
            }
        } else {
            aiDecisionCounter -= elapsed;
        }
    }

    public AIAction getGoalkeeperAIAction() {
        return goalkeeperAIAction;
    }
}


