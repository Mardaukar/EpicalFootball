package com.example.epicalfootball;

public class Constants {

    //PLAYER
    public static Position PLAYER_STARTING_POSITION = new Position(5, 30);
    public static float PLAYER_ACCELERATION_SPEED_CURVE_FACTOR = 6/8f; //Creates a speed curve on acceleration, 0 is linear, 1 long curve

    //BALL
    public static float BALL_RADIUS = 0.4f;
    public static float BALL_REFERENCE_SPEED = 10; //Ball speed at magnitude 1
    public static float BALL_BASE_DECELERATION = 0.2f;
    public static Position BALL_STARTING_POSITION = new Position(0, 35);
    public static Vector BALL_STARTING_SPEED = new Vector((float)-Math.PI / 2,0.6f);

    //FIELD IMAGE (superminifield)
    /*
    public static float FIELD_IMAGE_WIDTH_PIXELS = 375;
    public static float FIELD_IMAGE_HEIGHT_PIXELS = 300;
    public static float FIELD_IMAGE_TOUCHLINE_FROM_TOP_PIXELS = 30;
    public static float FIELD_IMAGE_GOAL_DEPTH_PIXELS = 27;
    public static float FIELD_IMAGE_BOX_HEIGHT_PIXELS = 170;
    public static float FIELD_IMAGE_HEIGHT_WIDTH_RATIO =  FIELD_IMAGE_HEIGHT_PIXELS / FIELD_IMAGE_WIDTH_PIXELS;*/

    //FIELD IMAGE (smallfield)
    public static float FIELD_IMAGE_WIDTH_PIXELS = 500;
    public static float FIELD_IMAGE_HEIGHT_PIXELS = 400;
    public static float FIELD_IMAGE_TOUCHLINE_FROM_TOP_PIXELS = 32;
    public static float FIELD_IMAGE_GOAL_DEPTH_PIXELS = 22;
    public static float FIELD_IMAGE_BOX_HEIGHT_PIXELS = 172;
    public static float FIELD_IMAGE_HEIGHT_WIDTH_RATIO =  FIELD_IMAGE_HEIGHT_PIXELS / FIELD_IMAGE_WIDTH_PIXELS;

    //FIELD METRICS
    public static float FIELD_HEIGHT_PIXELS = FIELD_IMAGE_HEIGHT_PIXELS - FIELD_IMAGE_TOUCHLINE_FROM_TOP_PIXELS;
    public static float METERS_PER_PIXELS = 16.5f / FIELD_IMAGE_BOX_HEIGHT_PIXELS;
    public static float FIELD_WIDTH = FIELD_IMAGE_WIDTH_PIXELS * METERS_PER_PIXELS;
    public static float FIELD_HEIGHT = FIELD_HEIGHT_PIXELS * METERS_PER_PIXELS;
    public static float GOAL_DEPTH = FIELD_IMAGE_GOAL_DEPTH_PIXELS * METERS_PER_PIXELS;
    public static float TOUCHLINE_FROM_TOP = FIELD_IMAGE_TOUCHLINE_FROM_TOP_PIXELS * METERS_PER_PIXELS;
    public static float BOUNDARY_WIDTH = 1;

    //GOAL
    public static float POST_RADIUS = 0.15f;
    public static float GOAL_WIDTH = 7.32f;

    //MATCH
    public static long NEW_BALL_WAIT_TIME_IN_MILLISECONDS = 1000;

    //GAME PLAY
    public static float CONTROL_CONE_SHIFT_MULTIPLIER = 0.1f; //How much control cone shifts ball, of player's control angle
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 0.2f; //How much bounce shifts ball in control, of player's control angle
    public static float PLAYER_BASE_DECELERATION = 0.3f;
    public static int BALLS_AT_START = 10;
    public static float LINESEGMENT_COLLISION_SPEED_MULTIPLIER = 0.5f;
    public static float POST_COLLISION_SPEED_MULTIPLIER = 0.8f;
    public static float COLLISION_ANGLE_SPEED_MULTIPLIER = 0.5f;
    public static float BALL_PLAYER_COLLISION_SPEED_MULTIPLIER = 0.5f;
    public static float DRIBBLING_KICK_FORWARD = 0.4f;
    public static int SHOT_POWER_METER_OPTIMAL = 100;
    public static int SHOT_POWER_METER_HIGHER_LIMIT = 120;
    public static int SHOT_POWER_METER_LOWER_LIMIT = 10;
    public static float AIMING_TIME = 1.5f;
    public static float SHOOT_READY_TIME_IN_MILLISECONDS = 1500;

    //SHOOTING
    public static float LONG_SHOTS_LIMIT = 20;
    public static float TARGET_GOAL_MOVE_SPEED = 0.4f;
    public static float TARGET_GOAL_WIDTH_TO_HEIGHT = 3;
    public static float MAX_TARGET_GOAL_SIZE = 0.9f;
    public static float MAX_TARGET_GOAL_MOVE_SIZE = 0.8f;
    public static float MIN_TARGET_GOAL_SIZE = 0.1f;
    public static float AIMING_TARGET_MULTIPLIER = 1.5f;

    //LAYOUT
    public static float CONTROL_AREA_FROM_WIDTH = 0.8f;
    public static float CONTROL_AREA_FROM_HEIGHT = 0.5f;
    public static float CONTROL_AREA_CENTER_FROM_TOP = 0.75f;
    public static float CONTROL_AREA_TOP_FROM_TOP = 0.5f;
    public static float CONTROL_AREA_LEFT_FROM_LEFT = 0.1f;
    public static float CONTROL_VIEW_MAXIMUM_LIMIT = 0.8f;
    public static float DECELERATE_DOT_RADIUS_OF_CONTROL_SURFACE = 0.12f;
    public static float CONTROL_DOT_RADIUS_OF_CONTROL_SURFACE = 0.1f;
    public static float TARGET_DOT_RADIUS_OF_CONTROL_SURFACE = 0.05f;

    //VISUAL
    public static int SHADOW_ALPHA = 150;
    public static float SHADOW_OFFSET = 0.0052f;
    public static int DECELERATE_ON_ALPHA = 100;
    public static int DECELERATE_OFF_ALPHA = 50;
    public static int CONTROL_DOT_ALPHA = 50;
    public static int TARGET_DOT_ALPHA = 150;
    public static int SHOOT_BUTTON_DOWN_COLOR = 0xFF673AB7;
    public static int SHOOT_BUTTON_UP_COLOR = 0xDF471A97;

    //RUNNER
    public static long ELAPSED_LIMIT_IN_MILLISECONDS = 150;
    public static long SLEEP_TIME_IN_MILLISECONDS = 10;

    //MATHEMATICAL
    public static float FULL_MAGNITUDE = 1f;
    public static float FULL = 1f;
    public static float HALF = 0.5f;
    public static float DOUBLE = 2;

    //PLAYER ATTRIBUTES
    public static float PLAYER_REACH_ATTRIBUTE = 5;
    public static float PLAYER_ACCELERATION_ATTRIBUTE = 5;
    public static float PLAYER_SPEED_ATTRIBUTE = 5;
    public static float PLAYER_BALLCONTROL_ATTRIBUTE = 5;
    public static float PLAYER_DRIBBLING_ATTRIBUTE = 5;
    public static float PLAYER_SHOTPOWER_ATTRIBUTE = 5;
    public static float PLAYER_ACCURACY_ATTRIBUTE = 0;
    public static float PLAYER_FINISHING_ATTRIBUTE = 5;
    public static float PLAYER_LONGSHOTS_ATTRIBUTE = 0;

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
    public static float MIN_BALLCONTROL_FIRST_TOUCH = BALL_PLAYER_COLLISION_SPEED_MULTIPLIER;
    public static float MAX_BALLCONTROL_FIRST_TOUCH = BALL_PLAYER_COLLISION_SPEED_MULTIPLIER / 5;
    public static float MIN_DRIBBLING_VALUE = 0.5f;
    public static float MAX_DRIBBLING_VALUE = 1;
    public static float MIN_DRIBBLING_TARGET = 0.9f;
    public static float MAX_DRIBBLING_TARGET = 1.2f;
    public static float MAX_SHOTPOWER_VALUE = 6f;
    public static float MIN_SHOTPOWER_VALUE = 2f;
    public static float MIN_ACCURACY_DISTANCE = 6;
    public static float MAX_ACCURACY_DISTANCE = 12;
    public static float MIN_ACCURACY_TARGET_DOT = 0.1f;
    public static float MAX_ACCURACY_TARGET_DOT = 0.02f;
    public static float MIN_TARGET_GOAL_SPEED = 1;
    public static float MAX_TARGET_GOAL_SPEED = 0.5f;
    public static float MIN_MID_SHOT_POWER = 25;
    public static float MAX_MID_SHOT_POWER = 75;
    public static float MIN_LONGSHOTS_ACCURACY = 1;
    public static float MAX_LONGSHOTS_ACCURACY = 0.5f;

    //PLAYER VALUE INCREMENTS
    public static float REACH_VALUE_INCREMENT = (MAX_REACH_VALUE - MIN_REACH_VALUE) / 10;
    public static float ACCELERATION_VALUE_INCREMENT = (MAX_ACCELERATION_VALUE - MIN_ACCELERATION_VALUE) / 10;
    public static float SPEED_VALUE_INCREMENT = (MAX_SPEED_VALUE - MIN_SPEED_VALUE) / 10;
    public static float BALLCONTROL_ANGLE_INCREMENT = (MAX_BALLCONTROL_ANGLE - MIN_BALLCONTROL_ANGLE) / 10;
    public static float BALLCONTROL_RADIUS_INCREMENT = (MAX_BALLCONTROL_RADIUS - MIN_BALLCONTROL_RADIUS) / 10;
    public static float BALLCONTROL_BALL_SPEED_INCREMENT = (MAX_BALLCONTROL_BALL_SPEED - MIN_BALLCONTROL_BALL_SPEED) / 10;
    public static float BALLCONTROL_FIRST_TOUCH_INCREMENT = (MAX_BALLCONTROL_FIRST_TOUCH - MIN_BALLCONTROL_FIRST_TOUCH) / 10;
    public static float DRIBBLING_VALUE_INCREMENT = (MAX_DRIBBLING_VALUE - MIN_DRIBBLING_VALUE) / 10;
    public static float DRIBBLING_TARGET_INCREMENT = (MAX_DRIBBLING_TARGET - MIN_DRIBBLING_TARGET) / 10;
    public static float SHOTPOWER_VALUE_INCREMENT = (MAX_SHOTPOWER_VALUE - MIN_SHOTPOWER_VALUE) / 10;
    public static float ACCURACY_DISTANCE_INCREMENT = (MAX_ACCURACY_DISTANCE - MIN_ACCURACY_DISTANCE) / 10;
    public static float ACCURACY_TARGET_DOT_INCREMENT = (MAX_ACCURACY_TARGET_DOT - MIN_ACCURACY_TARGET_DOT) / 10;
    public static float TARGET_GOAL_SPEED_INCREMENT = (MAX_TARGET_GOAL_SPEED - MIN_TARGET_GOAL_SPEED) / 10;
    public static float MID_SHOT_POWER_INCREMENT = (MAX_MID_SHOT_POWER - MIN_MID_SHOT_POWER) / 10;
    public static float LONGSHOTS_ACCURACY_INCREMENT = (MAX_LONGSHOTS_ACCURACY - MIN_LONGSHOTS_ACCURACY) / 10;
}
