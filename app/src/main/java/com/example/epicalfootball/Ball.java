package com.example.epicalfootball;

public class Ball extends FieldObject {

    public Ball() {
        //this.position = new Position(-7.32f / 2 - 0.1f, 35);
        this.position = new Position(0.2f, 20);
        this.speed = new Vector((float)-Math.PI / 2,1f);
        this.radius = 0.4f;
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
