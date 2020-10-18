package com.example.epicalfootball;

import com.example.epicalfootball.math.Position;

public class Constants {

    //PLAYER
    public static Position OUTFIELD_PLAYER_STARTING_POSITION = new Position(5, 30);
    public static float OUTFIELD_PLAYER_STARTING_ORIENTATION = (float)-Math.PI / 2;
    public static Position GOALKEEPER_STARTING_POSITION = new Position(0, 2);
    public static float GOALKEEPER_STARTING_ORIENTATION = (float)Math.PI / 2;
    public static float PLAYER_ACCELERATION_SPEED_CURVE_FACTOR = 6/8f; //Creates a speed curve on acceleration, 0 is linear, 1 long curve
    public static float CONTROL_CONE_SHIFT_MULTIPLIER = 0.1f; //How much control cone shifts ball, of player's control angle
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 0.2f; //How much bounce shifts ball in control, of player's control angle
    public static float PLAYER_BASE_DECELERATION = 0.3f;
    public static float DRIBBLING_KICK_FORWARD = 0.2f;

    //BALL
    public static float BALL_RADIUS = 0.35f;
    public static float BALL_REFERENCE_SPEED = 10; //Ball speed at magnitude 1
    public static float BALL_BASE_DECELERATION = 0.2f;
    public static int BALL_UPDATES_PER_CYCLE = 5;

    //FIELD IMAGE (superminifield)
    /*
    public static float FIELD_IMAGE_WIDTH_PIXELS = 375;
    public static float FIELD_IMAGE_HEIGHT_PIXELS = 300;
    public static float FIELD_IMAGE_TOUCHLINE_FROM_TOP_PIXELS = 30;
    public static float FIELD_IMAGE_GOAL_DEPTH_PIXELS = 27;
    public static float FIELD_IMAGE_BOX_HEIGHT_PIXELS = 170;
    public static float FIELD_IMAGE_HEIGHT_WIDTH_RATIO =  FIELD_IMAGE_HEIGHT_PIXELS / FIELD_IMAGE_WIDTH_PIXELS;
    */

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
    public static float POST_RADIUS = 0.15f;
    public static float GOAL_WIDTH = 7.32f;

    //MATCH PLAY
    public static int BALLS_AT_START = 10;
    public static long NEW_BALL_WAIT_TIME_IN_MILLISECONDS = 1000;
    public static int GAME_UPDATE_TIME = 30;
    public static float GOAL_NET_COLLISION_SPEED_MULTIPLIER = 0.3f;
    public static float GOAL_POST_COLLISION_SPEED_MULTIPLIER = 0.6f;
    public static float COLLISION_ANGLE_SPEED_MULTIPLIER = 0.5f; //needed?
    public static float BALL_PLAYER_COLLISION_SPEED_MULTIPLIER = 0.5f;
    public static long BALL_FEED_TIMER = 500;

    //SHOOTING
    public static float LONG_SHOTS_LIMIT = 20;
    public static float TARGET_GOAL_MOVE_SPEED = 0.4f;
    public static float TARGET_GOAL_WIDTH_TO_HEIGHT = 3;
    public static float MAX_TARGET_GOAL_SIZE = 0.9f;
    public static float MAX_TARGET_GOAL_MOVE_SIZE = 0.8f;
    public static float MIN_TARGET_GOAL_SIZE = 0.1f;
    public static float AIMING_TARGET_MULTIPLIER = 1.5f;
    public static float PLAYER_SLOW_ON_SHOT_FACTOR = 0.5f;
    public static int SHOT_POWER_METER_OPTIMAL = 100;
    public static int SHOT_POWER_METER_HIGHER_LIMIT = 130;
    public static float FAILED_SHOT_ACCURACY_GAUSSIAN_FACTOR = 0.2f;
    public static float FAILED_SHOT_POWER_FACTOR = 0.5f;
    public static int SHOT_POWER_METER_LOWER_LIMIT = 20;
    public static float AIMING_TIME = 1.5f;
    public static float SHOOT_READY_TIME_IN_MILLISECONDS = 1500;
    public static float PLAYER_KICK_RECOVERY_TIME = 300;
    public static float AIM_RECOVERY_TIME = 300;
    public static float AUTOPILOT_SPEED_MAGNITUDE = 0.5f;

    //LAYOUT
    public static float CONTROL_AREA_FROM_WIDTH = 0.8f;
    public static float CONTROL_AREA_FROM_HEIGHT = 0.5f;
    public static float CONTROL_AREA_CENTER_FROM_TOP = 0.75f;
    public static float CONTROL_AREA_TOP_FROM_TOP = 0.5f;
    public static float CONTROL_AREA_LEFT_FROM_LEFT = 0.1f;
    public static float CONTROL_AREA_RIGHT_FROM_LEFT = 0.9f;
    public static float CONTROL_VIEW_MAXIMUM_LIMIT = 0.8f;
    public static float DECELERATE_DOT_RADIUS_OF_CONTROL_SURFACE = 0.12f;
    public static float CONTROL_DOT_RADIUS_OF_CONTROL_SURFACE = 0.1f;

    //VISUAL
    public static int FULL_ALPHA = 255;
    public static int SHADOW_ALPHA = 150;
    public static float SHADOW_OFFSET = 0.0052f;
    public static int DECELERATE_ON_ALPHA = 100;
    public static int DECELERATE_OFF_ALPHA = 50;
    public static int CONTROL_DOT_ALPHA = 50;
    public static int TARGET_DOT_ALPHA = 150;
    public static int SHOOT_BUTTON_DOWN_COLOR = 0xFF673AB7;
    public static int SHOOT_BUTTON_UP_COLOR = 0xDF471A97;
    public static float POST_WIDTH_IN_TARGET_PNG = 0.115f;
    public static int FAILED_SHOT_BASE_ALPHA = 50;
    public static int FAILED_SHOT_INCREMENTAL_ALPHA = 100;

    //MATHEMATICAL
    public static float FULL_MAGNITUDE = 1f;
    public static float FULL = 1f;
    public static float HALF = 0.5f;
    public static float DOUBLE = 2;
    public static int TWO = 2;
    public static float DOWN = (float)Math.PI / 2;
    public static float UP = (float)-Math.PI / 2;
    public static float LEFT = (float)Math.PI;
    public static float RIGHT = 0;
    public static float FULL_CIRCLE = 2 * (float)Math.PI;
    public static float HALF_CIRCLE = (float)Math.PI;
    public static float QUARTER_CIRCLE = (float)Math.PI / 2;

    //PLAYER VALUE LIMITS
    public static float MIN_REACH_VALUE = 0.7f;
    public static float MAX_REACH_VALUE = 0.9f;
    public static float MIN_ACCELERATION_VALUE = 3;
    public static float MAX_ACCELERATION_VALUE = 6;
    public static float MIN_ACCELERATION_TURN = 3;
    public static float MAX_ACCELERATION_TURN = 6;
    public static float MIN_SPEED_VALUE = 6;
    public static float MAX_SPEED_VALUE = 12;
    public static float MIN_BALL_CONTROL_ANGLE = (float)Math.PI / 4;
    public static float MAX_BALL_CONTROL_ANGLE = (float)Math.PI / 2.2f;
    public static float MIN_BALL_CONTROL_RADIUS = 0f;
    public static float MAX_BALL_CONTROL_RADIUS = 0.6f;
    public static float MIN_BALL_CONTROL_BALL_SPEED = 0.01f;
    public static float MAX_BALL_CONTROL_BALL_SPEED = 0.03f;
    public static float MIN_BALL_CONTROL_FIRST_TOUCH = BALL_PLAYER_COLLISION_SPEED_MULTIPLIER;
    public static float MAX_BALL_CONTROL_FIRST_TOUCH = BALL_PLAYER_COLLISION_SPEED_MULTIPLIER / 5;
    public static float MIN_DRIBBLING_LIMIT = 0.5f;
    public static float MAX_DRIBBLING_LIMIT = 1;
    public static float MIN_DRIBBLING_TARGET = 0.9f;
    public static float MAX_DRIBBLING_TARGET = 1.1f;
    public static float MAX_SHOT_POWER_VALUE = 6f;
    public static float MIN_SHOT_POWER_VALUE = 2f;
    public static float MIN_MID_SHOT_POWER = 25;
    public static float MAX_MID_SHOT_POWER = 75;
    public static float MIN_ACCURACY_DISTANCE = 6;
    public static float MAX_ACCURACY_DISTANCE = 12;
    public static float MIN_ACCURACY_TARGET_DOT = 0.1f;
    public static float MAX_ACCURACY_TARGET_DOT = 0.02f;
    public static float MIN_ACCURACY_GAUSSIAN_FACTOR = 0.05f;
    public static float MAX_ACCURACY_GAUSSIAN_FACTOR = 0;
    public static float MIN_TARGET_GOAL_SPEED = 1;
    public static float MAX_TARGET_GOAL_SPEED = 0.5f;
    public static float MIN_LONG_SHOTS_ACCURACY = 1;
    public static float MAX_LONG_SHOTS_ACCURACY = 0.5f;

    //PLAYER VALUE INCREMENTS
    public static float REACH_VALUE_INCREMENT = (MAX_REACH_VALUE - MIN_REACH_VALUE) / 10;
    public static float ACCELERATION_VALUE_INCREMENT = (MAX_ACCELERATION_VALUE - MIN_ACCELERATION_VALUE) / 10;
    public static float ACCELERATION_TURN_INCREMENT = (MAX_ACCELERATION_TURN - MIN_ACCELERATION_TURN) / 10;
    public static float SPEED_VALUE_INCREMENT = (MAX_SPEED_VALUE - MIN_SPEED_VALUE) / 10;
    public static float BALL_CONTROL_ANGLE_INCREMENT = (MAX_BALL_CONTROL_ANGLE - MIN_BALL_CONTROL_ANGLE) / 10;
    public static float BALL_CONTROL_RADIUS_INCREMENT = (MAX_BALL_CONTROL_RADIUS - MIN_BALL_CONTROL_RADIUS) / 10;
    public static float BALL_CONTROL_BALL_SPEED_INCREMENT = (MAX_BALL_CONTROL_BALL_SPEED - MIN_BALL_CONTROL_BALL_SPEED) / 10;
    public static float BALL_CONTROL_FIRST_TOUCH_INCREMENT = (MAX_BALL_CONTROL_FIRST_TOUCH - MIN_BALL_CONTROL_FIRST_TOUCH) / 10;
    public static float DRIBBLING_LIMIT_INCREMENT = (MAX_DRIBBLING_LIMIT - MIN_DRIBBLING_LIMIT) / 10;
    public static float DRIBBLING_TARGET_INCREMENT = (MAX_DRIBBLING_TARGET - MIN_DRIBBLING_TARGET) / 10;
    public static float SHOT_POWER_VALUE_INCREMENT = (MAX_SHOT_POWER_VALUE - MIN_SHOT_POWER_VALUE) / 10;
    public static float ACCURACY_DISTANCE_INCREMENT = (MAX_ACCURACY_DISTANCE - MIN_ACCURACY_DISTANCE) / 10;
    public static float ACCURACY_TARGET_DOT_INCREMENT = (MAX_ACCURACY_TARGET_DOT - MIN_ACCURACY_TARGET_DOT) / 10;
    public static float ACCURACY_GAUSSIAN_FACTOR_INCREMENT = (MAX_ACCURACY_GAUSSIAN_FACTOR - MIN_ACCURACY_GAUSSIAN_FACTOR) / 10;
    public static float TARGET_GOAL_SPEED_INCREMENT = (MAX_TARGET_GOAL_SPEED - MIN_TARGET_GOAL_SPEED) / 10;
    public static float MID_SHOT_POWER_INCREMENT = (MAX_MID_SHOT_POWER - MIN_MID_SHOT_POWER) / 10;
    public static float LONG_SHOTS_ACCURACY_INCREMENT = (MAX_LONG_SHOTS_ACCURACY - MIN_LONG_SHOTS_ACCURACY) / 10;
}
