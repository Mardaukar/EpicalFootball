package com.example.epicalfootball.items;

import com.example.epicalfootball.math.Position;

public class Circle {
    Position position;
    float radius;

    public Circle() {

    }

    public Circle(Position position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    public Circle (float x, float y, float radius) {
        this.position = new Position(x, y);
        this.radius = radius;
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
