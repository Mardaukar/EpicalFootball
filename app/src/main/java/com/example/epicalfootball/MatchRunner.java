package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;

public class MatchRunner extends Thread {

    private volatile boolean running = true;
    private MatchSurfaceView matchSurfaceView;
    private MatchState matchState;

    public MatchRunner(MatchSurfaceView matchSurfaceView, MatchState matchState) {
        this.matchSurfaceView = matchSurfaceView;
        this.matchState = matchState;
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
            lastTime = begin;

            matchState.updateGameState(elapsed);
            matchSurfaceView.drawOnSurface();

            delta = System.currentTimeMillis() - begin;

            if (delta < GAME_UPDATE_TIME) {
                try {
                    Thread.sleep(GAME_UPDATE_TIME - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
