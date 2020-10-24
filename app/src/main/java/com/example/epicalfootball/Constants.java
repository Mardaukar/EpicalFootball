package com.example.epicalfootball;

import com.example.epicalfootball.math.Position;

public class Constants {

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
    public static float GOAL_NET_COLLISION_SPEED_MULTIPLIER = 0.15f;
    public static float GOAL_POST_COLLISION_SPEED_MULTIPLIER = 0.25f;
    public static float BALL_PLAYER_COLLISION_SPEED_BASE_MULTIPLIER = 0.8f;
    public static float BALL_PLAYER_COLLISION_SPEED_MULTIPLIER = 0.625f;
    public static float BALL_COLLISION_REFERENCE_MAX_SPEED = 5;
    public static float BALL_COLLISION_SPEED_REDUCTION_BY_SPEED_FACTOR = 0.5f;
    public static long BALL_FEED_TIMER = 500;
    public static float GOAL_POST_PLAYER_SHIFT_ANGLE = 0.1f;

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
    public static float FAILED_SHOT_ACCURACY_GAUSSIAN_FACTOR = 0.1f;
    public static float FAILED_SHOT_POWER_FACTOR = 0.5f;
    public static int SHOT_POWER_METER_LOWER_LIMIT = 15;
    public static float AIMING_TIME = 1.2f;
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

    //PLAYER
    public static float PLAYER_ACCELERATION_SPEED_CURVE_FACTOR = 6/8f; //Creates a speed curve on acceleration, 0 is linear, 1 long curve
    public static float PLAYER_BASE_DECELERATION = 0.3f;

    //GOALKEEPER
    public static Position GOALKEEPER_STARTING_POSITION = new Position(0, 1);
    public static float GOALKEEPER_STARTING_ORIENTATION = DOWN;
    public static String HOLD_ACTION = "hold";
    public static String MOVE_ACTION = "move";
    public static String SAVE_ACTION = "save";
    public static String INTERCEPT_ACTION = "intercept";
    public static float GK_AI_BALL_SPEED_PERCEIVED_SHOT = 1; //ball speed magnitude perceived as an incoming shot
    public static float GK_AI_BALL_DIRECTION_PERCEIVED_SAME = (float)Math.PI / 50;
    public static float GK_ACCEPTED_POSITION_OFFSET = 0.3f;
    public static float GK_SLOWDOWN_DIRECTION_ANGLE = (float)Math.PI / 8;
    public static float SAVING_BALL_SPEED_REDUCTION_RANDOM_MULTIPLIER = 3;
    public static float SAVING_BALL_SPEED_SHOVE_LIMIT = 2;
    public static float SAVING_SHOVE_MAX_ANGLE_SHIFT = QUARTER_CIRCLE / 2;
    public static Position SAVING_SHOVE_RIGHT_TARGET_POSITION = new Position(5, 0);
    public static Position SAVING_SHOVE_LEFT_TARGET_POSITION = new Position(-5, 0);

    //GOALKEEPER ATTRIBUTES
    public static int GK_REACH = 0;
    public static int GK_AGILITY = 5;
    public static int GK_SPEED = 5;
    public static int GK_REFLEXES = 0;
    public static int GK_BALL_HANDLING = 5;
    public static int GK_GOALKEEPING_INTELLIGENCE = 5;

    //OUTFIELD PLAYER
    public static Position OUTFIELD_PLAYER_STARTING_POSITION = new Position(0, 30);
    public static float OUTFIELD_PLAYER_STARTING_ORIENTATION = UP;
    public static float CONTROL_CONE_SHIFT_MULTIPLIER = 3; //How much control cone shifts ball, of player's control angle
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 0.5f; //How much bounce shifts ball direction in control, of player's control angle
    public static float DRIBBLING_KICK_FORWARD = 0.2f;
    public static float DRIBBLING_KICK_ANGLE = (float)Math.PI / 8;

    //PLAYER VALUE LIMITS
    public static float MIN_ACCELERATION_VALUE = 3;
    public static float MAX_ACCELERATION_VALUE = 6;
    public static float MIN_ACCELERATION_TURN = 3;
    public static float MAX_ACCELERATION_TURN = 6;
    public static float MIN_SPEED_VALUE = 6;
    public static float MAX_SPEED_VALUE = 12;

    //GOALKEEPER VALUE LIMITS
    public static float MIN_GOALKEEPING_REACH_VALUE = 0.85f;
    public static float MAX_GOALKEEPING_REACH_VALUE = 1.05f;
    public static float MIN_REFLEXES_VALUE = 200;
    public static float MAX_REFLEXES_VALUE = 100;
    public static float MIN_BALL_HANDLING_ANGLE = HALF_CIRCLE / 4;
    public static float MAX_BALL_HANDLING_ANGLE = HALF_CIRCLE / 2.2f;
    public static float MIN_BALL_HANDLING_VALUE = 1;
    public static float MAX_BALL_HANDLING_VALUE = 3;
    public static float MIN_GOALKEEPING_INTELLIGENCE_DECISION_TIME = 1500;
    public static float MAX_GOALKEEPING_INTELLIGENCE_DECISION_TIME = 750;
    public static float MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION = 0;
    public static float MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION = 0.7f;
    public static float MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR = (float)Math.PI / 4;
    public static float MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR = 0;
    public static float MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR = 2;
    public static float MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR = 0;
    public static float MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS = 10;
    public static float MAX_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS = 15;
    public static float MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION = 1;
    public static float MAX_GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION = 10;

    //OUTFIELD PLAYER VALUE LIMITS
    public static float MIN_OUTFIELD_PLAYER_REACH_VALUE = 0.7f;
    public static float MAX_OUTFIELD_PLAYER_REACH_VALUE = 0.9f;
    public static float MIN_BALL_CONTROL_ANGLE = HALF_CIRCLE / 4;
    public static float MAX_BALL_CONTROL_ANGLE = HALF_CIRCLE / 2.2f;
    public static float MIN_BALL_CONTROL_RADIUS = 0.1f;
    public static float MAX_BALL_CONTROL_RADIUS = 0.3f;
    public static float MIN_BALL_CONTROL_BALL_SPEED = 1f;
    public static float MAX_BALL_CONTROL_BALL_SPEED = 2f;
    public static float MIN_BALL_CONTROL_FIRST_TOUCH = BALL_PLAYER_COLLISION_SPEED_MULTIPLIER * BALL_PLAYER_COLLISION_SPEED_BASE_MULTIPLIER;
    public static float MAX_BALL_CONTROL_FIRST_TOUCH = MIN_BALL_CONTROL_FIRST_TOUCH / 5;
    public static float MIN_DRIBBLING_LIMIT = 0.5f;
    public static float MAX_DRIBBLING_LIMIT = 1;
    public static float MIN_DRIBBLING_TARGET = 0.9f;
    public static float MAX_DRIBBLING_TARGET = 1.1f;
    public static float MAX_SHOT_POWER_VALUE = 5f;
    public static float MIN_SHOT_POWER_VALUE = 2.5f;
    public static float MIN_ACCURACY_DISTANCE = 6;
    public static float MAX_ACCURACY_DISTANCE = 12;
    public static float MIN_ACCURACY_TARGET_DOT = 0.1f;
    public static float MAX_ACCURACY_TARGET_DOT = 0.02f;
    public static float MIN_ACCURACY_GAUSSIAN_FACTOR = 0.05f;
    public static float MAX_ACCURACY_GAUSSIAN_FACTOR = 0;
    public static float MIN_MID_SHOT_POWER = 30;
    public static float MAX_MID_SHOT_POWER = 60;
    public static float MIN_TARGET_GOAL_SPEED = 1;
    public static float MAX_TARGET_GOAL_SPEED = 0.5f;
    public static float MIN_LONG_SHOTS_ACCURACY = 1;
    public static float MAX_LONG_SHOTS_ACCURACY = 0.5f;

    //PLAYER VALUE INCREMENTS
    public static float ACCELERATION_VALUE_INCREMENT = (MAX_ACCELERATION_VALUE - MIN_ACCELERATION_VALUE) / 10;
    public static float ACCELERATION_TURN_INCREMENT = (MAX_ACCELERATION_TURN - MIN_ACCELERATION_TURN) / 10;
    public static float SPEED_VALUE_INCREMENT = (MAX_SPEED_VALUE - MIN_SPEED_VALUE) / 10;

    //GOALKEEPER VALUE INCREMENTS
    public static float GOALKEEPING_REACH_VALUE_INCREMENT = (MAX_GOALKEEPING_REACH_VALUE - MIN_GOALKEEPING_REACH_VALUE) / 10;
    public static float REFLEXES_VALUE_INCREMENT = (MAX_REFLEXES_VALUE - MIN_REFLEXES_VALUE) / 10;
    public static float BALL_HANDLING_ANGLE_INCREMENT = (MAX_BALL_HANDLING_ANGLE - MIN_BALL_HANDLING_ANGLE) / 10;
    public static float BALL_HANDLING_VALUE_INCREMENT = (MAX_BALL_HANDLING_VALUE - MIN_BALL_HANDLING_VALUE) / 10;
    public static float GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS - MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_RADIUS) / 10;
    public static float GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION - MIN_GOALKEEPING_INTELLIGENCE_INTERCEPTING_PRECAUTION) / 10;
    public static float GOALKEEPING_INTELLIGENCE_DECISION_TIME_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_DECISION_TIME - MIN_GOALKEEPING_INTELLIGENCE_DECISION_TIME) / 10;
    public static float GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION - MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_ANTICIPATION) / 10;
    public static float GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR - MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_DIRECTION_JUDGEMENT_ERROR) / 10;
    public static float GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR_INCREMENT = (MAX_GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR - MIN_GOALKEEPING_INTELLIGENCE_POSITIONING_RADIUS_JUDGEMENT_ERROR) / 10;

    //OUTFIELD PLAYER VALUE INCREMENTS
    public static float OUTFIELD_PLAYER_REACH_VALUE_INCREMENT = (MAX_OUTFIELD_PLAYER_REACH_VALUE - MIN_OUTFIELD_PLAYER_REACH_VALUE) / 10;
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
