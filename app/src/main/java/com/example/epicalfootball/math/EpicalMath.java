package com.example.epicalfootball.math;

import android.graphics.RectF;
import android.util.Log;

import com.example.epicalfootball.items.Circle;

import static com.example.epicalfootball.Constants.*;

public class EpicalMath {

    public static void logDegrees(String name, float radians) {
        int degrees = (int)(radians / Math.PI * 180);
        Log.d(name, "" + degrees);
    }

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
                direction = (float)(HALF_CIRCLE + Math.atan(y / x));
            } else {
                direction = (float)(Math.atan(y / x) - HALF_CIRCLE);
            }
        } else {
            if (y > 0) {
                direction = DOWN;
            } else if (y < 0) {
                direction = UP;
            } else {
                direction = 0;
            }
        }

        return direction;
    }

    public static float convertToDirectionFromOrigo(Position position) {
        return convertToDirectionFromOrigo(position.getX(), position.getY());
    }

    public static float convertToDirection(Position position1, Position position2) {
        float x = position2.getX() - position1.getX();
        float y = position2.getY() - position1.getY();
        return convertToDirectionFromOrigo(x, y);
    }

    public static boolean directionLeftFromDirection(float direction, float referenceDirection) {
        float correctedDirection = sanitizeDirection(direction - referenceDirection);

        if (correctedDirection < 0) {
            return true;
        } else {
            return false;
        }
    }

    public static float shiftTowardsDirection(float originalDirection, float targetDirection, float angleShift) {
        float correctedDirection = sanitizeDirection(originalDirection - targetDirection);

        if (correctedDirection < 0) {
            correctedDirection += angleShift;

            if (correctedDirection > 0) {
                correctedDirection = 0;
            }
        } else {
            correctedDirection -= angleShift;

            if (correctedDirection < 0) {
                correctedDirection = 0;
            }
        }

        return sanitizeDirection(correctedDirection + targetDirection);
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

    public static int radiansToDegrees(float radians) {
        return (int)(radians / FULL_CIRCLE * 360);
    }

    public static float sanitizeDirection(float direction) {
        if (direction < -HALF_CIRCLE) {
            direction += FULL_CIRCLE;
        } else if (direction > HALF_CIRCLE) {
            direction -= FULL_CIRCLE;
        }

        return direction;
    }

    public static float reversedDirection(float direction) {
        float reversedDirection = direction + HALF_CIRCLE;
        return sanitizeDirection(reversedDirection);
    }

    public static float mirroredDirection(float referenceDirection, float direction) {
        float angle = angleBetweenDirections(referenceDirection, direction);
        Log.d("angle", "" + angle);
        return sanitizeDirection(referenceDirection + angle);
    }

    public static float directionBetweenDirections(float direction1, float direction2) {
        float angle = angleBetweenDirections(direction1, direction2);
        return direction2 + angle / 2;
    }

    public static float absoluteAngleBetweenDirections(float direction1, float direction2) {
        float difference = sanitizeDirection(direction1 - direction2);
        return Math.abs(difference);
    }

    public static float angleBetweenDirections(float direction1, float direction2) {
        float difference = sanitizeDirection(direction1 - direction2);
        return difference;
    }

    public static Position convertToPositionFromOrigo(float direction, float distance) {
        Position position = new Position();
        position.addPositionVector(direction, distance);
        return position;
    }

    public static Position getPositionBetweenPositions(Position position1, Position position2) {
        float x = (position1.getX() + position2.getX()) / 2;
        float y = (position1.getY() + position2.getY()) / 2;
        return new Position(x, y);
    }
}
