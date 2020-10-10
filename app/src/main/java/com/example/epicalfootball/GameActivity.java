package com.example.epicalfootball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import static com.example.epicalfootball.Constants.*;

public class GameActivity extends AppCompatActivity {

    private GameState gameState;
    private GameView gameView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameState = new GameState(this);
        gameView = new GameView(this, gameState);

        ConstraintLayout gameLayout = findViewById(R.id.game_layout);
        gameLayout.addView(gameView);

        TextView balls_left_textView = findViewById(R.id.balls_number_textView);
        balls_left_textView.setText(String.format("%d", gameState.getBallsLeft()));

        TextView goals_scored_textView = findViewById(R.id.goals_number_textView);
        goals_scored_textView.setText(String.format("%d", gameState.getGoalsScored()));

        View controlSurface = findViewById(R.id.control_surface);
        controlSurface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int eventAction = event.getAction();
                float touchX = event.getX();
                float touchY = event.getY();
                float sideLength = view.getWidth();

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        gameState.setControl(touchX / sideLength, touchY / sideLength, sideLength);
                        //Log.d("activity control", "" + touchX + " " + touchY + " " + sideLength);
                        break;
                    default:
                        gameState.setControlOffWithDecelerate(false);
                        break;
                }

                return true;
            }
        });

        final ImageView controlBackground = findViewById(R.id.control_background);
        final Button shootButton = findViewById(R.id.action_button);
        shootButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int eventAction = event.getAction();

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                        gameState.setShootButtonDown(true);
                        shootButton.setBackgroundColor(SHOOT_BUTTON_DOWN_COLOR);
                        controlBackground.setImageResource(R.drawable.campnou_grass);
                        break;
                    case MotionEvent.ACTION_UP:
                        gameState.setShootButtonDown(false);
                        shootButton.setBackgroundColor(SHOOT_BUTTON_UP_COLOR);
                        controlBackground.setImageResource(R.drawable.fire_ring_medium);
                        break;
                }

                return true;
            }
        });
    }

    public void updateBallsLeft (final String ballsLeft) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView balls_left_textView = findViewById(R.id.balls_number_textView);
                balls_left_textView.setText(ballsLeft);
            }
        });
    }

    public void updateGoals(final String goals) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView goals_scored_textView = findViewById(R.id.goals_number_textView);
                goals_scored_textView.setText(goals);
            }
        });
    }

    public void updatePowerBars(final int shotPower) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressBar power_bar_left = findViewById(R.id.power_bar);
                ProgressBar power_bar_right = findViewById(R.id.power_bar2);
                power_bar_left.setProgress(shotPower);
                power_bar_right.setProgress(shotPower);
            }
        });
    }

    public void goToResult (final int goalsScored) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("goals_scored", goalsScored);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}