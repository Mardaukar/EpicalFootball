package com.example.epicalfootball;

public class Constants {

    //PLAYER


    //BALL
    public static float BALL_RADIUS = 0.4f;

    //GOAL
    public static float POST_RADIUS = 0.15f;
    public static float GOAL_WIDTH = 7.32f;
    public static float GOAL_DEPTH = 2.2f; //this should be calculated from field image!

    //FIELD IMAGE
    public static float FIELD_WIDTH = 35.294f; //replace with formula!

    //MATCH
    public static long NEW_BALL_WAIT_TIME = 2000;

    //GAME PLAY
    public static float CONTROL_BOUNCE_SHIFT_MULTIPLIER = 2; //How much more bounce (compared to touch) shifts ball in control
    public static float BALLCONTROL_PLAYER_SPEED_MULTIPLIER = 1f; //Maximum speed magnitude when controlling ball

    //VISUAL
    public static float SHADOW_OFFSET = 5;
}
