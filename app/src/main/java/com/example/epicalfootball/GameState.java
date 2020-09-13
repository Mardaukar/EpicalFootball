package com.example.epicalfootball;

public class GameState {

    private int ballsLeft;
    private int goalsScored;

    public GameState() {
        this.ballsLeft = 10;
        this.goalsScored = 0;
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
