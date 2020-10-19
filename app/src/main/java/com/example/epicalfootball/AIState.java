package com.example.epicalfootball;

import android.util.Log;

import com.example.epicalfootball.items.Ball;
import com.example.epicalfootball.items.Goalkeeper;
import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;

import static com.example.epicalfootball.Constants.*;

public class AIState {
    private MatchState matchState;
    private Goalkeeper goalkeeper;
    private AIAction goalkeeperAIAction;
    private int aiDecisionCounter;
    private boolean shotPerceived;

    public AIState(MatchState matchState) {
        this.matchState = matchState;
        this.goalkeeper = matchState.getGoalkeeper();
        this.goalkeeperAIAction = new AIAction();
        this.shotPerceived = false;
    }

    public void update(float elapsed) {
        Log.d("AIState", "" + goalkeeperAIAction.getTargetPosition().getX() + " " + goalkeeperAIAction.getTargetPosition().getY());

        Ball ball = matchState.getBall();

        if (ball.getSpeed().getMagnitude() >= GK_AI_BALL_SPEED_PERCEIVED_SHOT && this.shotPerceived == false) {
            this.shotPerceived = true;
            this.aiDecisionCounter = GK_REFLEX_TIME;
        } else if(ball.getSpeed().getMagnitude() < GK_AI_BALL_SPEED_PERCEIVED_SHOT) {
            this.shotPerceived = false;
        }

        if (aiDecisionCounter <= 0) {
            if (shotPerceived) {
                goalkeeperAIAction.setAction(SAVE_ACTION);
                aiDecisionCounter = GK_REFLEX_TIME;
            } else if(EpicalMath.calculateDistanceFromOrigo(ball.getPosition()) <= GK_AI_INTERCEPT_RADIUS) {
                goalkeeperAIAction.setTargetPosition(matchState.getGoalkeeper().getPosition());
                goalkeeperAIAction.setAction(INTERCEPT_ACTION);
                aiDecisionCounter = GK_AI_DECISION_TIMER;
            } else {
                Position maxLeftBasePosition = new Position(-(GOAL_WIDTH * HALF + POST_RADIUS), goalkeeper.getRadius() + POST_RADIUS);
                Position maxRightBasePosition = new Position(GOAL_WIDTH * HALF + POST_RADIUS, goalkeeper.getRadius() + POST_RADIUS);
                float maxLeftBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxLeftBasePosition);
                float maxRightBasePositionDirection = EpicalMath.convertToDirectionFromOrigo(maxRightBasePosition);
                float basePositionRadius = EpicalMath.calculateDistanceFromOrigo(maxRightBasePosition);
                float ballDirection = EpicalMath.convertToDirectionFromOrigo(ball.getPosition());

                if (ballDirection > maxRightBasePositionDirection && ballDirection < maxLeftBasePositionDirection) {
                    goalkeeperAIAction.setTargetPosition(EpicalMath.convertToPositionFromOrigo(ballDirection, basePositionRadius));
                } else if (Math.abs(ballDirection) > QUARTER_CIRCLE) {
                    goalkeeperAIAction.setTargetPosition(maxLeftBasePosition);
                } else {
                    goalkeeperAIAction.setTargetPosition(maxRightBasePosition);
                }

                goalkeeperAIAction.setAction(MOVE_ACTION);
                aiDecisionCounter = GK_AI_DECISION_TIMER;
            }
        } else {
            aiDecisionCounter -= elapsed;
        }
    }

    public AIAction getGoalkeeperAIAction() {
        return goalkeeperAIAction;
    }
}


