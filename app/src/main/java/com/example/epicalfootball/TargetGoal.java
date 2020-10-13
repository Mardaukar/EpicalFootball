package com.example.epicalfootball;

import android.util.Log;

import java.util.Random;

import static com.example.epicalfootball.Constants.*;

public class TargetGoal {
    private float positionX;
    private float positionY;
    private float size;
    private float speed;
    private float verticalSpeed;
    private float horizontalSpeed;
    Random random = new Random();

    public TargetGoal(float distance, boolean longshot, Player player) {
        if (!longshot) {
            this.size = player.getAccuracyDistance() / distance;
        } else {
            this.size = player.getAccuracyDistance() / (LONG_SHOTS_LIMIT + (distance - LONG_SHOTS_LIMIT) * player.getLongshotsAccuracy());
        }

        if (this.size > MAX_TARGET_GOAL_MOVE_SIZE) {
            if (this.size > MAX_TARGET_GOAL_SIZE) {
                this.size = MAX_TARGET_GOAL_SIZE;
            }

            this.positionX = (FULL - this.size) * HALF;
            this.positionY = (FULL - this.size) / TARGET_GOAL_WIDTH_TO_HEIGHT;
        } else {
            if (this.size < MIN_TARGET_GOAL_SIZE) {
                this.size = MIN_TARGET_GOAL_SIZE;
            }

            this.positionX = random.nextFloat() * (FULL - this.size);
            this.positionY = random.nextFloat() * (FULL - this.size) / TARGET_GOAL_WIDTH_TO_HEIGHT;

            if (!longshot) {
                speed = (FULL - this.size) * TARGET_GOAL_MOVE_SPEED * player.getFinishingTargetGoalSpeed();
            } else {
                speed = (FULL - this.size) * TARGET_GOAL_MOVE_SPEED * player.getLongshotsTargetGoalSpeed();
            }

            horizontalSpeed = speed;
            verticalSpeed = speed;

            if (random.nextFloat() - HALF < 0) {
                horizontalSpeed *= -1;
            }

            if (random.nextFloat() - HALF < 0) {
                verticalSpeed *= -1;
            }
        }
    }

    public void updatePosition(float timeFactor) {
        if (this.size < MAX_TARGET_GOAL_MOVE_SIZE) {
            if (this.positionX < 0) {
                this.horizontalSpeed *= -1;
                this.positionX = 0;
            } else if (this.positionX > FULL - this.size) {
                this.horizontalSpeed *= -1;
                this.positionX = FULL - this.size;
            }

            if (this.positionY < 0) {
                this.verticalSpeed *= -1;
                this.positionY = 0;
            } else if(this.positionY > (FULL - this.size) / TARGET_GOAL_WIDTH_TO_HEIGHT) {
                this.verticalSpeed *= -1;
                this.positionY = (FULL - this.size) / TARGET_GOAL_WIDTH_TO_HEIGHT;
            }

            this.positionX += timeFactor * this.horizontalSpeed;
            this.positionY += timeFactor * this.verticalSpeed;
        }
    }

    public Position getAimTarget(float controlX, float controlY) {
        float targetGoalSize = this.size;
        float targetGoalMiddle = this.positionX - HALF + targetGoalSize * HALF;
        float targetGoalBottom = this.positionY - FULL + targetGoalSize / TARGET_GOAL_WIDTH_TO_HEIGHT;

        float targetX = controlX * AIMING_TARGET_MULTIPLIER;
        float targetY = controlY * AIMING_TARGET_MULTIPLIER;

        if (targetY >= targetGoalBottom) {
            targetX *= targetGoalBottom / targetY;
        }

        float aimPositionX = (targetX - targetGoalMiddle) / targetGoalSize * (GOAL_WIDTH + DOUBLE * POST_RADIUS); ///Should calculate post size from pic

        if (aimPositionX < -FIELD_WIDTH) {
            aimPositionX = -FIELD_WIDTH;
        } else if (aimPositionX > FIELD_WIDTH) {
            aimPositionX = FIELD_WIDTH;
        }

        return new Position(aimPositionX, 0);
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getSize() {
        return size;
    }
}
