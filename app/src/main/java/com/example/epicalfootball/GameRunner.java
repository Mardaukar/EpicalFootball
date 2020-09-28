package com.example.epicalfootball;

import android.util.Log;

import static com.example.epicalfootball.Constants.*;

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

        long lastTime = System.currentTimeMillis();
        long now;
        long elapsed;

        while(running) {
            now = System.currentTimeMillis();
            elapsed = now - lastTime;

            if (elapsed < ELAPSED_LIMIT_IN_MILLISECONDS) {
                gameState.updateGameState(elapsed);
                gameView.updateSurface();
            }

            lastTime = now;

            try {
                Thread.sleep(SLEEP_TIME_IN_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void shutdown() {
        running = false;
    }

}
