package com.example.epicalfootball;

import static com.example.epicalfootball.Constants.*;

public class AIRunner extends Thread {

    private volatile boolean running = true;
    private AIState AIState;

    public AIRunner(AIState aiState) {
        this.AIState = aiState;
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

            AIState.update(elapsed);

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
