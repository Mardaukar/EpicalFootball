package com.example.epicalfootball;

public class Ball {
    protected Vector speed;
    protected Position position;

    public Ball() {
        this.position = new Position(-15, 13);
        this.speed = new Vector(0,1f);
    }

    public Vector getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return position;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        Position newPosition = new Position(x, y);
        this.position = newPosition;
    }

    public void updateSpeed(float timeFactor) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration = 0.2f;

            deceleratedSpeedMagnitude = oldSpeedMagnitude - deceleration * timeFactor;

            if (deceleratedSpeedMagnitude < 0) {
                deceleratedSpeedMagnitude = 0;
            }
        }

        this.speed.setMagnitude(deceleratedSpeedMagnitude);
    }
}
