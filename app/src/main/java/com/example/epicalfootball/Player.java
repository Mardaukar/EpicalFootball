package com.example.epicalfootball;

public class Player {
    private Vector acceleration;
    private Vector speed;
    private Position position;

    public Player() {
        this.speed = new Vector(0.79f,0.1f);
        this.acceleration = new Vector();
        this.position = new Position();
    }

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        Position newPosition = new Position(x, y);
        this.setPosition(newPosition);
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
