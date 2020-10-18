package com.example.epicalfootball.math;

import android.graphics.RectF;

import com.example.epicalfootball.items.Circle;

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

    public static float convertToDirectionFromOrigo(float x, float y) {
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

    public static boolean checkIntersect(RectF rect, Circle circle) {
        return (circle.getPosition().getX() + circle.getRadius() >= rect.left && circle.getPosition().getX() - circle.getRadius() <= rect.right && circle.getPosition().getY() + circle.getRadius() >= rect.top && circle.getPosition().getY() - circle.getRadius() <= rect.bottom);
    }

    public static boolean checkIntersect(Circle circle1, Circle circle2) {
        float distance = calculateDistance(circle1.getPosition().getX(), circle1.getPosition().getY(), circle2.getPosition().getX(), circle2.getPosition().getY());
        return distance <= circle1.getRadius() + circle2.getRadius();
    }

    public static float calculateDistance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

    public static float calculateDistance(Position position1, Position position2) {
        return calculateDistance(position1.getX(), position1.getY(), position2.getX(), position2.getY());
    }

    public static float calculateDistanceFromOrigo(float x, float y) {
        return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public static float calculateDistanceFromOrigo(Position position) {
        return calculateDistanceFromOrigo(position.getX(), position.getY());
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
