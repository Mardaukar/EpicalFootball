package com.example.epicalfootball;

public abstract class Player {
    Position position;
    float orientation;
    TargetSpeedVector targetSpeed;
    Vector speed;

    public float getMagnitudeToOrientation() {
        float angle = EpicalMath.absoluteAngleBetweenDirections(this.getSpeed().getDirection(), this.orientation);

        if (angle > Math.PI / 2) {
            return 0;
        } else {
            return (float)Math.cos(angle) * this.getSpeed().getMagnitude();
        }
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public TargetSpeedVector getTargetSpeed() {
        return targetSpeed;
    }

    public void setTargetSpeed(TargetSpeedVector targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }
}
