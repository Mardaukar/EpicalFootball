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

    public AIState(MatchState matchState) {
        this.matchState = matchState;
        this.goalkeeper = matchState.getGoalkeeper();
        this.goalkeeperAIAction = new AIAction();
    }

    public void update(float elapsed) {
        Log.d("AIState", "" + goalkeeperAIAction.getTargetPosition().getX() + " " + goalkeeperAIAction.getTargetPosition().getY());
        //Log.d("AIState", "...");

        Ball ball = matchState.getBall();

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

        goalkeeperAIAction.setAction("move");
    }
}
