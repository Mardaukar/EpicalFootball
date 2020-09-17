package com.example.epicalfootball;

import android.util.Log;

public class GameState {

    private GameActivity gameActivity;
    private Player player;
    private int ballsLeft;
    private int goalsScored;

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

        player.baseDecelerate(timeFactor);
        player.updateSpeed(timeFactor);
        player.updatePosition(timeFactor);

        Log.d("GameState", "Player x: " + this.player.getPosition().getX());
        Log.d("GameState", "Player y: " + this.player.getPosition().getY());
    }
}
