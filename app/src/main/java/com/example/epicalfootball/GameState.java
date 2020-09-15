package com.example.epicalfootball;

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
    }

    public void substractBall() {
        this.ballsLeft--;
    }
}
