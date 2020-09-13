package com.example.epicalfootball;

public class Player {
    private Vector acceleration;
    private Vector speed;

    public Player() {
        this.speed = new Vector();
        this.acceleration = new Vector();
    }

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public Vector getAcceleration() {
        return acceleration;
    }

    public Vector getSpeed() {
        return speed;
    }
}
