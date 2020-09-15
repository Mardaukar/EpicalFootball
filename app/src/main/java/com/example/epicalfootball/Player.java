package com.example.epicalfootball;

public class Player {
    private Vector acceleration;
    private Vector speed;
    private Position position;

    public Player() {
        this.speed = new Vector();
        this.acceleration = new Vector();
        this.position = new Position();
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
