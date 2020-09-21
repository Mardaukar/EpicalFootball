package com.example.epicalfootball;

public class Position {
    private float x;
    private float y;

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Position addVector(float direction, float distance) {
        this.x += Math.cos(direction) * distance;
        this.y += Math.sin(direction) * distance;
        return this;
    }

    public Position addVector(Vector vector) {
        addVector(vector.getDirection(), vector.getMagnitude());
        return this;
    }
}
