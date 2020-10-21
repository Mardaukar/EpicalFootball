package com.example.epicalfootball.items;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.math.Position;
import com.example.epicalfootball.TargetSpeedVector;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.Constants.*;
import static com.example.epicalfootball.activities.MenuActivity.playerAttributes;

public class Goalkeeper extends Player {
    private float reflexes;
    private float goalkeepingIntelligenceDecisionTime;
    private float goalkeepingIntelligenceInterceptingRadius;
    private float goalkeepingIntelligencePositioningAnticipation;

    public Goalkeeper() {
        this.position = GOALKEEPER_STARTING_POSITION;
        this.orientation = GOALKEEPER_STARTING_ORIENTATION;
        this.targetSpeed = new TargetSpeedVector();
        this.speed = new Vector();

        this.radius = MIN_REACH_VALUE + REACH_VALUE_INCREMENT * GK_REACH;
        this.fullMagnitudeSpeed = MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * GK_SPEED;
        this.acceleration = (MIN_ACCELERATION_VALUE + ACCELERATION_VALUE_INCREMENT * GK_ACCELERATION) / (MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * GK_SPEED);
        this.accelerationTurn = MIN_ACCELERATION_TURN + ACCELERATION_TURN_INCREMENT * GK_ACCELERATION;
        this.reflexes = MIN_REFLEXES_VALUE + REFLEXES_VALUE_INCREMENT * GK_REFLEXES;
        this.goalkeepingIntelligenceDecisionTime = MIN_GOALKEEPING_INTELLIGENCE_DECISION_TIME + GOALKEEPING_INTELLIGENCE_DECISION_TIME_INCREMENT * GK_GOALKEEPING_INTELLIGENCE;
        this.goalkeepingIntelligenceInterceptingRadius = MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS + GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS_INCREMENT * GK_GOALKEEPING_INTELLIGENCE;
        this.goalkeepingIntelligencePositioningAnticipation = MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION + GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION_INCREMENT * GK_GOALKEEPING_INTELLIGENCE;
    }

    public void updateSpeed(float timeFactor) {
        float oldSpeedMagnitude = speed.getMagnitude();
        float deceleratedSpeedMagnitude = 0;

        if (oldSpeedMagnitude > 0) {
            float deceleration;

            if (this.decelerateOn) {
                deceleration = PLAYER_BASE_DECELERATION + this.acceleration;
            } else {
                deceleration = PLAYER_BASE_DECELERATION;
            }

            deceleratedSpeedMagnitude = oldSpeedMagnitude - deceleration * timeFactor;

            if (deceleratedSpeedMagnitude < 0) {
                deceleratedSpeedMagnitude = 0;
            }
        }

        if (targetSpeed.getMagnitude() >  0) {
            float newSpeedMagnitudeX = (float)(Math.cos(speed.getDirection()) * deceleratedSpeedMagnitude);
            float newSpeedMagnitudeY = (float)(Math.sin(speed.getDirection()) * deceleratedSpeedMagnitude);
            float targetSpeedMagnitudeX = (float)(Math.cos(targetSpeed.getDirection()) * targetSpeed.getMagnitude());
            float targetSpeedMagnitudeY = (float)(Math.sin(targetSpeed.getDirection()) * targetSpeed.getMagnitude());
            float acceleration = PLAYER_BASE_DECELERATION + this.acceleration;

            if (newSpeedMagnitudeX >= 0) {
                if (targetSpeedMagnitudeX > newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX -= Math.abs(Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeX < newSpeedMagnitudeX) {
                    newSpeedMagnitudeX += Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeX += Math.abs(Math.cos(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            }

            if (newSpeedMagnitudeY >= 0) {
                if (targetSpeedMagnitudeY > newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY -= Math.abs(Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            } else {
                if (targetSpeedMagnitudeY < newSpeedMagnitudeY) {
                    newSpeedMagnitudeY += Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor;
                } else {
                    newSpeedMagnitudeY += Math.abs(Math.sin(targetSpeed.getDirection()) * (acceleration) * timeFactor);
                }
            }

            float newDirection = EpicalMath.convertToDirectionFromOrigo(newSpeedMagnitudeX, newSpeedMagnitudeY);
            float newSpeedMagnitude = EpicalMath.calculateMagnitude(newSpeedMagnitudeX, newSpeedMagnitudeY);

            if (newSpeedMagnitude > oldSpeedMagnitude) {
                newSpeedMagnitude = (newSpeedMagnitude - oldSpeedMagnitude) * (FULL_MAGNITUDE - oldSpeedMagnitude * PLAYER_ACCELERATION_SPEED_CURVE_FACTOR) + oldSpeedMagnitude;
            }

            Vector newSpeed = new Vector(newDirection, newSpeedMagnitude);
            this.setSpeed(newSpeed);

        } else {
            this.speed.setMagnitude(deceleratedSpeedMagnitude);
        }
    }

    public void updateOrientation(float timeFactor) {
        if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) > 0) {
            this.orientation += timeFactor * this.accelerationTurn;

            if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) < 0) {
                this.orientation = this.targetSpeed.getDirection();
            }
        } else {
            this.orientation -= timeFactor * this.accelerationTurn;

            if (EpicalMath.angleBetweenDirections(this.targetSpeed.getDirection(), this.orientation) > 0) {
                this.orientation = this.targetSpeed.getDirection();
            }
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAccelerationTurn() {
        return accelerationTurn;
    }

    public void setAccelerationTurn(float accelerationTurn) {
        this.accelerationTurn = accelerationTurn;
    }

    public float getFullMagnitudeSpeed() {
        return fullMagnitudeSpeed;
    }

    public void setFullMagnitudeSpeed(float fullMagnitudeSpeed) {
        this.fullMagnitudeSpeed = fullMagnitudeSpeed;
    }

    public float getReflexes() {
        return reflexes;
    }

    public void setReflexes(float reflexes) {
        this.reflexes = reflexes;
    }

    public float getGoalkeepingIntelligenceDecisionTime() {
        return goalkeepingIntelligenceDecisionTime;
    }

    public void setGoalkeepingIntelligenceDecisionTime(float goalkeepingIntelligenceDecisionTime) {
        this.goalkeepingIntelligenceDecisionTime = goalkeepingIntelligenceDecisionTime;
    }

    public float getGoalkeepingIntelligenceInterceptingRadius() {
        return goalkeepingIntelligenceInterceptingRadius;
    }

    public void setGoalkeepingIntelligenceInterceptingRadius(float goalkeepingIntelligenceInterceptingRadius) {
        this.goalkeepingIntelligenceInterceptingRadius = goalkeepingIntelligenceInterceptingRadius;
    }

    public float getGoalkeepingIntelligencePositioningAnticipation() {
        return goalkeepingIntelligencePositioningAnticipation;
    }

    public void setGoalkeepingIntelligencePositioningAnticipation(float goalkeepingIntelligencePositioningAnticipation) {
        this.goalkeepingIntelligencePositioningAnticipation = goalkeepingIntelligencePositioningAnticipation;
    }
}
