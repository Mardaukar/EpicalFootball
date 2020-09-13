package com.example.epicalfootball;

public class AccelerationVector extends Vector {
    final float touchAreaLimitingFactor = 0.8f;

    public void setAcceleration(float x, float y, float centerSideDistance) {
        float magnitude = (float)Math.sqrt(x * x + y * y) / (centerSideDistance * touchAreaLimitingFactor);
        if (magnitude > 1) {
            magnitude = 1;
        }
        this.setMagnitude(magnitude);

        float direction = 0;

        if (x > 0) {
            direction = (float)Math.atan(y / x);
        } else if(x < 0) {
            if (y >= 0) {
                direction = (float)(Math.PI + Math.atan(y / x));
            } else {
                direction = (float)(Math.atan(y / x) - Math.PI);
            }
        } else {
            if (y > 0) {
                direction = (float)Math.PI * 0.5f;
            } else if (y < 0) {
                direction = (float)Math.PI * -0.5f;
            } else {
                direction = 0;
            }
        }

        this.setDirection(direction);
    }
}
