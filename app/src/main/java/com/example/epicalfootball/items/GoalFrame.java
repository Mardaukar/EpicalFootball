package com.example.epicalfootball.items;

import android.graphics.RectF;

import com.example.epicalfootball.Collisions;

import static com.example.epicalfootball.Constants.*;

public class GoalFrame {
    private RectF goalArea;
    private GoalNet rearNet;
    private GoalNet leftNet;
    private GoalNet rightNet;
    private GoalPost leftPost;
    private GoalPost rightPost;
    private GoalPost leftSupport;
    private GoalPost rightSupport;

    public GoalFrame() {
        this.goalArea = new RectF(-GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF, -BALL_RADIUS);
        this.rearNet = new GoalNet(-GOAL_WIDTH * HALF, -GOAL_DEPTH - DOUBLE * POST_RADIUS, GOAL_WIDTH * HALF, -GOAL_DEPTH);
        this.leftNet = new GoalNet(-GOAL_WIDTH * HALF - DOUBLE * POST_RADIUS, -GOAL_DEPTH, -GOAL_WIDTH * HALF, -POST_RADIUS);
        this.rightNet = new GoalNet(GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF + DOUBLE * POST_RADIUS, -POST_RADIUS);
        this.leftPost = new GoalPost(-GOAL_WIDTH * HALF - POST_RADIUS, 0, POST_RADIUS);
        this.rightPost = new GoalPost(GOAL_WIDTH * HALF + POST_RADIUS, 0, POST_RADIUS);
        this.leftSupport = new GoalPost(-GOAL_WIDTH * HALF - POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
        this.rightSupport = new GoalPost(GOAL_WIDTH * HALF + POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
    }

    public void handleGoalCollision(Ball ball) {
        Collisions.handleBallGoalNetCollision(rearNet, ball);
        Collisions.handleBallGoalNetCollision(leftNet, ball);
        Collisions.handleBallGoalNetCollision(rightNet, ball);
        Collisions.handleBallGoalPostCollision(leftPost, ball);
        Collisions.handleBallGoalPostCollision(rightPost, ball);
        Collisions.handleBallGoalPostCollision(leftSupport, ball);
        Collisions.handleBallGoalPostCollision(rightSupport,ball);
    }

    public void handleGoalCollision(Player player) {
        Collisions.handlePlayerLineSegmentCollision(rearNet, player);
        Collisions.handlePlayerLineSegmentCollision(leftNet, player);
        Collisions.handlePlayerLineSegmentCollision(rightNet, player);
        Collisions.handleGoalPostPlayerCollision(leftPost, player);
        Collisions.handleGoalPostPlayerCollision(rightPost, player);
        Collisions.handleGoalPostPlayerCollision(leftSupport, player);
        Collisions.handleGoalPostPlayerCollision(rightSupport, player);
    }

    public RectF getGoalArea() {
        return goalArea;
    }

    public RectF getRearNet() {
        return rearNet;
    }

    public RectF getLeftNet() {
        return leftNet;
    }

    public RectF getRightNet() {
        return rightNet;
    }

    public Circle getLeftPost() {
        return leftPost;
    }

    public Circle getRightPost() {
        return rightPost;
    }

    public Circle getLeftSupport() {
        return leftSupport;
    }

    public Circle getRightSupport() {
        return rightSupport;
    }
}
