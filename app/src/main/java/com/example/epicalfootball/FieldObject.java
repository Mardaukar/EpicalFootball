package com.example.epicalfootball;

abstract public class FieldObject {
    protected Vector speed;
    protected Position position;
    protected float radius;

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        this.position = new Position(x, y);
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
