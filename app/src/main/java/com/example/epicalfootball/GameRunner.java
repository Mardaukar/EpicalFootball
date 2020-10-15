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
        long elapsed;
        long begin;
        long delta;

        while(running) {
            begin = System.currentTimeMillis();
            elapsed = begin - lastTime;
            //Log.d("Elapsed: ", "" + elapsed);
            lastTime = begin;

            gameState.updateGameState(elapsed);
            gameView.drawOnSurface();

            delta = System.currentTimeMillis() - begin;

            if (delta < GAME_UPDATE_TIME) {
                try {
                    Thread.sleep(GAME_UPDATE_TIME - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //Log.d("Elapsed: ", "SPLURGE");
            }
        }

    }

    public void shutdown() {
        running = false;
    }

}
