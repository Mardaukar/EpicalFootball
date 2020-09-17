package com.example.epicalfootball;

public class Player {
    private AccelerationVector acceleration;
    private Vector speed;
    private Position position;

    public Player() {
        this.speed = new Vector(0.79f,1f);
        this.acceleration = new AccelerationVector();
        this.position = new Position();
    }

    public void updateSpeed(float timeFactor) {
        if (acceleration.getMagnitude() >  0) {
            float newSpeedX = (float)(Math.cos(speed.getDirection()) * speed.getMagnitude() + Math.cos(acceleration.getDirection()) * acceleration.getMagnitude() * 0.4 * timeFactor);
            float newSpeedY = (float)(Math.sin(speed.getDirection()) * speed.getMagnitude() + Math.sin(acceleration.getDirection()) * acceleration.getMagnitude() * 0.4 * timeFactor);

            float newDirection = EpicalMath.convertToDirection(newSpeedX, newSpeedY);
            float newMagnitude = EpicalMath.calculateMagnitude(newSpeedX, newSpeedY);

            Vector newSpeed = new Vector(newDirection, newMagnitude);
            this.setSpeed(newSpeed);

        }
    }

    public void baseDecelerate(float timeFactor) {
        if (speed.getMagnitude() > 0) {
            float newSpeedMagnitude = this.speed.getMagnitude() - 0.2f * timeFactor;

            if (newSpeedMagnitude < 0) {
                newSpeedMagnitude = 0;
            }

            this.speed.setMagnitude(newSpeedMagnitude);
        }
    }

    public void updatePosition(float timeFactor) {
        float x = this.position.getX();
        float y = this.position.getY();
        x = (float)(x + (Math.cos(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        y = (float)(y + (Math.sin(this.speed.getDirection())) * this.speed.getMagnitude() * 8 * timeFactor);
        Position newPosition = new Position(x, y);
        this.setPosition(newPosition);
    }

    public void setAcceleration(AccelerationVector acceleration) {
        this.acceleration = acceleration;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public AccelerationVector getAcceleration() {
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
