package com.example.epicalfootball;

public class Constants {

    //PLAYER ATTRIBUTES
    public static float PLAYER_REACH_ATTRIBUTE = 5;
    public static float PLAYER_ACCELERATION_ATTRIBUTE = 5;
    public static float PLAYER_SPEED_ATTRIBUTE = 5;
    public static float PLAYER_BALLCONTROL_ATTRIBUTE = 5;
    public static float PLAYER_DRIBBLING_ATTRIBUTE = 5;

    //PLAYER VALUE LIMITS
    public static float MIN_REACH_VALUE = 0.5f;
    public static float MAX_REACH_VALUE = 1;
    public static float MIN_ACCELERATION_VALUE = 3;
    public static float MAX_ACCELERATION_VALUE = 6;
    public static float MIN_SPEED_VALUE = 6;
    public static float MAX_SPEED_VALUE = 12;
    public static float MIN_BALLCONTROL_ANGLE = (float)Math.PI / 4;
    public static float MAX_BALLCONTROL_ANGLE = (float)Math.PI / 2;
    public static float MIN_BALLCONTROL_RADIUS = 0.2f;
    public static float MAX_BALLCONTROL_RADIUS = 0.6f;
    public static float MIN_BALLCONTROL_BALL_SPEED = 0.01f;
    public static float MAX_BALLCONTROL_BALL_SPEED = 0.03f;
    public static float MIN_DRIBBLING_VALUE = 0.5f;
    public static float MAX_DRIBBLING_VALUE = 1;

    //PLAYER VALUE INCREMENTS
    public static float REACH_VALUE_INCREMENT = (MAX_REACH_VALUE - MIN_REACH_VALUE) / 10;
    public static float ACCELERATION_VALUE_INCREMENT = (MAX_ACCELERATION_VALUE - MIN_ACCELERATION_VALUE) / 10;
    public static float SPEED_VALUE_INCREMENT = (MAX_SPEED_VALUE - MIN_SPEED_VALUE) / 10;
    public static float BALLCONTROL_ANGLE_INCREMENT = (MAX_BALLCONTROL_ANGLE - MIN_BALLCONTROL_ANGLE) / 10;
    public static float BALLCONTROL_RADIUS_INCREMENT = (MAX_BALLCONTROL_RADIUS - MIN_BALLCONTROL_RADIUS) / 10;
    public static float BALLCONTROL_BALL_SPEED_INCREMENT = (MAX_BALLCONTROL_BALL_SPEED - MIN_BALLCONTROL_BALL_SPEED) / 10;
    public static float DRIBBLING_VALUE_INCREMENT = (MAX_DRIBBLING_VALUE - MIN_DRIBBLING_VALUE) / 10;

    //PLAYER
    public static Position PLAYER_STARTING_POSITION = new Position(0, 0);
    public static float PLAYER_ACCELERATION_SPEED_CURVE_FACTOR = 6/8f; //Creates a speed curve on acceleration, 0 is linear, 1 long curve

    //BALL
    public static float BALL_RADIUS = 0.4f;
    public static float BALL_REFERENCE_SPEED = 10; //Ball speed at magnitude 1
    public static float BALL_BASE_DECELERATION = 0.2f;
    public static Position BALL_STARTING_POSITION = new Position(0, 25);
    public static Vector BALL_STARTING_SPEED = new Vector((float)-Math.PI / 2,0.77f);

    //FIELD IMAGE
    public static float FIELD_WIDTH_PIXELS = 375;
    public static float FIELD_HEIGHT_PIXELS = 300;
    public static float TOUCHLINE_FROM_TOP_PIXELS = 30;
    public static float GOAL_DEPTH_PIXELS = 27;
    public static float BOX_HEIGHT_PIXELS = 170;

    //FIELD METRICS
    public static float METERS_PER_PIXELS = 16 / BOX_HEIGHT_PIXELS;
    public static float FIELD_HEIGHT_WIDTH_RATIO =  FIELD_HEIGHT_PIXELS / FIELD_WIDTH_PIXELS;
    public static float FIELD_WIDTH = FIELD_WIDTH_PIXELS * METERS_PER_PIXELS;
    public static float FIELD_HEIGHT = FIELD_HEIGHT_PIXELS * METERS_PER_PIXELS;
    public static float GOAL_DEPTH = GOAL_DEPTH_PIXELS * METERS_PER_PIXELS;
    public static float TOUCHLINE_FROM_TOP = TOUCHLINE_FROM_TOP_PIXELS * METERS_PER_PIXELS;

    //GOAL
    public static float POST_RADIUS = 0.15f;
    public static float GOAL_WIDTH = 7.32f;

    //MATCH
    public static long NEW_BALL_WAIT_TIME_IN_MILLISECONDS = 1000;

    //GAME PLAY
    public static float CONTROL_CONE_SHIFT_MULTIPLIER = 0.1f; //How much control cone shifts ball, of player's control angle
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 0.2f; //How much bounce shifts ball in control, of player's control angle
    public static float PLAYER_BASE_DECELERATION = 0.3f;

    //LAYOUT
    public static float CONTROL_AREA_FROM_WIDTH = 0.8f;
    public static float CONTROL_AREA_CENTER_FROM_TOP = 3/4f;

    //VISUAL
    public static int SHADOW_ALPHA = 150;
    public static float SHADOW_OFFSET = 5;
    public static int DECELERATE_ON_ALPHA = 100;
    public static int DECELERATE_OFF_ALPHA = 50;
    public static int DECELERATE_DOT_RADIUS = 90;
    public static int CONTROL_DOT_ALPHA = 50;
    public static int CONTROL_DOT_RADIUS = 80;

    //RUNNER
    public static long ELAPSED_LIMIT_IN_MILLISECONDS = 150;
    public static long SLEEP_TIME_IN_MILLISECONDS = 10;

    //MATHEMATICAL
    public static float FULL_MAGNITUDE = 1f;
    public static float HALF = 0.5f;
}
