package com.example.epicalfootball;

public class Constants {

    //PLAYER ATTRIBUTES
    public static float PLAYER_REACH = 5;
    public static float PLAYER_ACCELERATION = 5;
    public static float PLAYER_SPEED = 5;
    public static float PLAYER_BALLCONTROL = 5;
    public static float PLAYER_DRIBBLING = 5;

    //PLAYER VALUE LIMITS
    public static float MIN_REACH_VALUE = 0.5f;
    public static float MAX_REACH_VALUE = 1;
    public static float MIN_ACCELERATION_VALUE = 3;
    public static float MAX_ACCELERATION_VALUE = 6;
    public static float MIN_SPEED_VALUE = 6;
    public static float MAX_SPEED_VALUE = 12;
    public static float MIN_BALLCONTROL_VALUE = (float)Math.PI / 4;
    public static float MAX_BALLCONTROL_VALUE = (float)Math.PI / 2;
    public static float MIN_DRIBBLING_VALUE = 0.5f;
    public static float MAX_DRIBBLING_VALUE = 1;

    //PLAYER VALUE INCREMENTS
    public static float REACH_VALUE_INCREMENT = (MAX_REACH_VALUE - MIN_REACH_VALUE) / 10;
    public static float ACCELERATION_VALUE_INCREMENT = (MAX_ACCELERATION_VALUE - MIN_ACCELERATION_VALUE) / 10;
    public static float SPEED_VALUE_INCREMENT = (MAX_SPEED_VALUE - MIN_SPEED_VALUE) / 10;
    public static float BALLCONTROL_VALUE_INCREMENT = (MAX_BALLCONTROL_VALUE - MIN_BALLCONTROL_VALUE) / 10;
    public static float DRIBBLING_VALUE_INCREMENT = (MAX_DRIBBLING_VALUE - MIN_DRIBBLING_VALUE) / 10;

    //BALL
    public static float BALL_RADIUS = 0.4f;
    public static float BALL_REFERENCE_SPEED = 10; //Ball speed at magnitude 1

    //GOAL
    public static float POST_RADIUS = 0.15f;
    public static float GOAL_WIDTH = 7.32f;
    public static float GOAL_DEPTH = 2.2f; //this should be calculated from field image!

    //FIELD IMAGE
    public static float FIELD_WIDTH = 35.294f; //replace with formula!

    //MATCH
    public static long NEW_BALL_WAIT_TIME = 1000;

    //GAME PLAY
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 2; //How much more bounce (compared to touch) shifts ball in control
    public static float CONTROL_CONE_SPEED_MULTIPLIER = 0.015f; //How much control cone decelerates ball
    public static float PLAYER_BASE_DECELERATION = 0.3f;

    //VISUAL
    public static float SHADOW_OFFSET = 5;
}
