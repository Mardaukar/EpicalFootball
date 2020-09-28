package com.example.epicalfootball;

import android.graphics.RectF;
import android.util.Log;

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

    public static boolean checkIntersect(RectF rect, float x, float y, float radius) {
        return (x + radius >= rect.left && x - radius <= rect.right && y + radius >= rect.top && y - radius <= rect.bottom);
    }

    public static boolean checkIntersect(float x1, float y1, float radius1, float x2, float y2, float radius2) {
        float distance = calculateDistance(x1, y1, x2, y2);
        return distance <= radius1 + radius2;
    }

    public static float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public static float calculateDistance(float x, float y) {
        return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float sanitizeDirection(float direction) {
        if (direction < -Math.PI) {
            direction += 2*Math.PI;
        } else if (direction > Math.PI) {
            direction -= 2*Math.PI;
        }

        return direction;
    }

    public static float absoluteAngleBetweenDirections(float direction1, float direction2) {
        float difference = sanitizeDirection(direction1 - direction2);
        return Math.abs(difference);
    }

    public static float angleBetweenDirections(float direction1, float direction2) {
        float difference = sanitizeDirection(direction1 - direction2);
        return difference;
    }
}
