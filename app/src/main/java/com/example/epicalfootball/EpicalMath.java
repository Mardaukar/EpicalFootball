package com.example.epicalfootball;

public class EpicalMath {

    public static float calculateMagnitude(float x, float y, float relationDivisor) {
        float newMagnitude = (float)Math.sqrt(x * x + y * y) / relationDivisor;

        if (newMagnitude > 1) {
            newMagnitude = 1;
        }

        return newMagnitude;
    }

    public static float calculateMagnitude(float x, float y) {
        float newMagnitude = (float)Math.sqrt(x * x + y * y);

        if (newMagnitude > 1) {
            newMagnitude = 1;
        }

        return newMagnitude;
    }

    public static float convertToDirection(float x, float y) {
        float direction;

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

        return direction;
    }
}
