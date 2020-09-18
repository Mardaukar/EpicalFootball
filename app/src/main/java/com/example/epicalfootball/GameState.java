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
        Log.d("GameState", "Update gameState");

        float timeFactor = elapsed/1000f;

        if (controlOn) {
            player.getAcceleration().setAcceleration(controlX - centerSideDistance, controlY - centerSideDistance, centerSideDistance);
        } else {
            player.getAcceleration().nullAcceleration();
        }
        player.baseDecelerate(timeFactor);
        player.updateSpeed(timeFactor);
        player.updatePosition(timeFactor);

        Log.d("GameState", "Player x: " + this.player.getPosition().getX());
        Log.d("GameState", "Player y: " + this.player.getPosition().getY());
    }

    public void setControlOn(float x, float y, float centerSideDistance) {
        controlOn = true;
        controlX = x;
        controlY = y;
        this.centerSideDistance = centerSideDistance;
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

    public void setControlOff() {
        controlOn = false;
    }
}
