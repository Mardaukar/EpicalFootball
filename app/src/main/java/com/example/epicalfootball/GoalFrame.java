package com.example.epicalfootball;

import android.graphics.RectF;

import static com.example.epicalfootball.Constants.*;

public class GoalFrame {
    private RectF goalArea;
    private RectF rearNet;
    private RectF leftNet;
    private RectF rightNet;
    private Circle leftPost;
    private Circle rightPost;
    private Circle leftSupport;
    private Circle rightSupport;

    public GoalFrame() {
        this.goalArea = new RectF(-GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF, -DOUBLE * BALL_RADIUS);
        this.rearNet = new RectF(-GOAL_WIDTH * HALF, -GOAL_DEPTH - DOUBLE * POST_RADIUS, GOAL_WIDTH * HALF, -GOAL_DEPTH);
        this.leftNet = new RectF(-GOAL_WIDTH * HALF - DOUBLE * POST_RADIUS, -GOAL_DEPTH, -GOAL_WIDTH * HALF, -POST_RADIUS);
        this.rightNet = new RectF(GOAL_WIDTH * HALF, -GOAL_DEPTH, GOAL_WIDTH * HALF + DOUBLE * POST_RADIUS, -POST_RADIUS);
        this.leftPost = new Circle(-GOAL_WIDTH * HALF - POST_RADIUS, 0, POST_RADIUS);
        this.rightPost = new Circle(GOAL_WIDTH * HALF + POST_RADIUS, 0, POST_RADIUS);
        this.leftSupport = new Circle(-GOAL_WIDTH * HALF - POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
        this.rightSupport = new Circle(GOAL_WIDTH * HALF + POST_RADIUS, -GOAL_DEPTH - POST_RADIUS, POST_RADIUS);
    }

    public void handleGoalCollision(FieldObject fieldObject) {
        Collisions.handleLineSegmentCollision(rearNet, fieldObject);
        Collisions.handleLineSegmentCollision(leftNet, fieldObject);
        Collisions.handleLineSegmentCollision(rightNet, fieldObject);
        Collisions.handleCircleCollision(leftPost, fieldObject);
        Collisions.handleCircleCollision(rightPost, fieldObject);
        Collisions.handleCircleCollision(leftSupport, fieldObject);
        Collisions.handleCircleCollision(rightSupport, fieldObject);
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
