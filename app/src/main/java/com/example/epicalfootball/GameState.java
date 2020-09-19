package com.example.epicalfootball;

import android.util.Log;

public class GameState {

    private GameActivity gameActivity;
    private Player player;
    private int ballsLeft;
    private int goalsScored;

    private boolean controlOn = false;
    private float controlX;
    private float controlY;
    private float centerSideDistance;
    private boolean decelerateOn = false;

    public GameState(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.player = new Player();
        this.ballsLeft = 10;
        this.goalsScored = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public int getBallsLeft() {
        return this.ballsLeft;
    }

    public int getGoalsScored() {
        return this.goalsScored;
    }

    public void addGoal() {
        this.goalsScored++;
        gameActivity.updateGoals(Integer.toString(goalsScored));
    }

    public void substractBall() {
        this.ballsLeft--;
        gameActivity.updateBallsLeft(Integer.toString(ballsLeft));
    }

    public void updateGameState(long elapsed) {
        float timeFactor = elapsed/1000f;

        if (controlOn) {
            player.getTargetSpeed().setTargetSpeed(controlX - centerSideDistance, controlY - centerSideDistance, centerSideDistance);
        } else {
            player.getTargetSpeed().nullTargetSpeed();
        }

        player.updateSpeed(timeFactor, decelerateOn);
        player.updatePosition(timeFactor);
    }

    public boolean isDecelerateOn() {
        return decelerateOn;
    }

    public void setControlOn(float x, float y, float centerSideDistance) {
        controlOn = true;
        controlX = x;
        controlY = y;
        this.centerSideDistance = centerSideDistance;
        decelerateOn = false;
    }

    public boolean isControlOn() {
        return controlOn;
    }

    public float getControlX() {
        return controlX;
    }

    public float getControlY() {
        return controlY;
    }

    public float getCenterSideDistance() {
        return centerSideDistance;
    }

    public void setControlOffWithDecelerate(boolean decelerate) {
        controlOn = false;
        decelerateOn = decelerate;
    }
}
