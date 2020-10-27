package com.example.epicalfootball.items;

import android.util.Log;

import com.example.epicalfootball.math.EpicalMath;
import com.example.epicalfootball.control.TargetSpeedVector;
import com.example.epicalfootball.math.Vector;

import static com.example.epicalfootball.activities.MenuActivity.goalkeeperAttributes;
import static com.example.epicalfootball.Constants.*;

public class Goalkeeper extends Player {
    private int afterKickTimer;
    private int recoveryTimer;
    private float ballHoldingDirection;

    private int agilityRecoveryTime;
    private float reflexes;
    private float ballHandlingAngle;
    private float ballHandling;
    private float goalkeepingIntelligenceDecisionTime;
    private float goalkeepingIntelligenceInterceptingRadius;
    private float goalkeepingIntelligenceInterceptingPrecaution;
    private float goalkeepingIntelligencePositioningAnticipation;
    private float goalkeepingIntelligencePositioningDirectionJudgementError;
    private float goalkeepingIntelligencePositioningRadiusJudgementError;

    public Goalkeeper() {
        afterKickTimer = 0;
        recoveryTimer = 0;

        this.position = GOALKEEPER_STARTING_POSITION.clonePosition();
        this.orientation = GOALKEEPER_STARTING_ORIENTATION;
        this.targetSpeed = new TargetSpeedVector();
        this.speed = new Vector();

        this.radius = MIN_GOALKEEPING_REACH_VALUE + GOALKEEPING_REACH_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_REACH_KEY);
        this.acceleration = (MIN_ACCELERATION_VALUE + ACCELERATION_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_AGILITY_KEY)) / (MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_SPEED_KEY));
        this.accelerationTurn = MIN_ACCELERATION_TURN + ACCELERATION_TURN_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_AGILITY_KEY);
        this.agilityRecoveryTime = MIN_AGILITY_RECOVERY_TIME + AGILITY_RECOVERY_TIME_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_AGILITY_KEY);
        this.fullMagnitudeSpeed = MIN_SPEED_VALUE + SPEED_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_SPEED_KEY);
        this.reflexes = MIN_REFLEXES_VALUE + REFLEXES_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_REFLEXES_KEY);
        this.ballHandlingAngle = MIN_BALL_HANDLING_ANGLE + BALL_HANDLING_ANGLE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_BALL_HANDLING_KEY);
        this.ballHandling = MIN_BALL_HANDLING_VALUE + BALL_HANDLING_VALUE_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_BALL_HANDLING_KEY);
        this.goalkeepingIntelligenceDecisionTime = MIN_GOALKEEPING_INTELLIGENCE_DECISION_TIME + GOALKEEPING_INTELLIGENCE_DECISION_TIME_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        this.goalkeepingIntelligenceInterceptingRadius = MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS + GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        this.goalkeepingIntelligenceInterceptingPrecaution = MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION + GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        this.goalkeepingIntelligencePositioningAnticipation = MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION + GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        this.goalkeepingIntelligencePositioningDirectionJudgementError = MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR + GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        this.goalkeepingIntelligencePositioningRadiusJudgementError = MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR + GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR_INCREMENT * goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
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

    public void updateOrientation(float timeFactor, Ball ball, boolean goalkeeperHoldingBall) {
        if (this.recoveryTimer <= 0 || this.speed.getMagnitude() == 0) {
            float goalkeeperToBallDirection = EpicalMath.convertToDirection(this.getPosition(), ball.getPosition());
            float angleShift = timeFactor * this.accelerationTurn;
            this.setOrientation(EpicalMath.shiftTowardsDirection(this.getOrientation(), goalkeeperToBallDirection, angleShift));
        }
    }

    public void updateRecoveryTimer(float elapsed) {
        if (this.recoveryTimer > 0) {
            this.recoveryTimer -= elapsed;

            if (this.recoveryTimer < 0 ) {
                this.recoveryTimer = 0;
            }
        }
    }

    public int getAfterKickTimer() {
        return afterKickTimer;
    }

    public void setAfterKickTimer(int afterKickTimer) {
        this.afterKickTimer = afterKickTimer;
    }

    public int getRecoveryTimer() {
        return recoveryTimer;
    }

    public void setRecoveryTimer(int recoveryTimer) {
        this.recoveryTimer = recoveryTimer;
    }

    public float getBallHoldingDirection() {
        return ballHoldingDirection;
    }

    public void setBallHoldingDirection(float ballHoldingDirection) {
        this.ballHoldingDirection = ballHoldingDirection;
    }

    public int getAgilityRecoveryTime() {
        return agilityRecoveryTime;
    }

    public void setAgilityRecoveryTime(int agilityRecoveryTime) {
        this.agilityRecoveryTime = agilityRecoveryTime;
    }

    public float getReflexes() {
        return reflexes;
    }

    public void setReflexes(float reflexes) {
        this.reflexes = reflexes;
    }

    public float getBallHandlingAngle() {
        return ballHandlingAngle;
    }

    public void setBallHandlingAngle(float ballHandlingAngle) {
        this.ballHandlingAngle = ballHandlingAngle;
    }

    public float getBallHandling() {
        return ballHandling;
    }

    public void setBallHandling(float ballHandling) {
        this.ballHandling = ballHandling;
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

    public float getGoalkeepingIntelligenceInterceptingPrecaution() {
        return goalkeepingIntelligenceInterceptingPrecaution;
    }

    public void setGoalkeepingIntelligenceInterceptingPrecaution(float goalkeepingIntelligenceInterceptingPrecaution) {
        this.goalkeepingIntelligenceInterceptingPrecaution = goalkeepingIntelligenceInterceptingPrecaution;
    }

    public float getGoalkeepingIntelligencePositioningAnticipation() {
        return goalkeepingIntelligencePositioningAnticipation;
    }

    public void setGoalkeepingIntelligencePositioningAnticipation(float goalkeepingIntelligencePositioningAnticipation) {
        this.goalkeepingIntelligencePositioningAnticipation = goalkeepingIntelligencePositioningAnticipation;
    }

    public float getGoalkeepingIntelligencePositioningDirectionJudgementError() {
        return goalkeepingIntelligencePositioningDirectionJudgementError;
    }

    public void setGoalkeepingIntelligencePositioningDirectionJudgementError(float goalkeepingIntelligencePositioningDirectionJudgementError) {
        this.goalkeepingIntelligencePositioningDirectionJudgementError = goalkeepingIntelligencePositioningDirectionJudgementError;
    }

    public float getGoalkeepingIntelligencePositioningRadiusJudgementError() {
        return goalkeepingIntelligencePositioningRadiusJudgementError;
    }

    public void setGoalkeepingIntelligencePositioningRadiusJudgementError(float goalkeepingIntelligencePositioningRadiusJudgementError) {
        this.goalkeepingIntelligencePositioningRadiusJudgementError = goalkeepingIntelligencePositioningRadiusJudgementError;
    }
}
