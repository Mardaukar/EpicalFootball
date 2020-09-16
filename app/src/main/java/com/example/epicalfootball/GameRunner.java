package com.example.epicalfootball;

import android.util.Log;

public class GameRunner extends Thread {

    private volatile boolean running = true;
    private GameView gameView;
    private GameState gameState;

    public GameRunner(GameView gameView, GameState gameState) {
        this.gameView = gameView;
        this.gameState = gameState;
    }

    @Override
    public void run() {
        while(running) {
            Log.d("RUNNER", "Thread running");
            gameState.updateGameState();
            gameView.updateSurface();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void shutdown() {
        running = false;
    }

}
