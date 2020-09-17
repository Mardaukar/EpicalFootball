package com.example.epicalfootball;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private GameState gameState;
    private GameView gameView;
    private AccelerationVector playerAccelerationVector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        */
        gameState = new GameState(this);
        gameView = new GameView(this, gameState);

        ConstraintLayout c = findViewById(R.id.game_layout);
        c.addView(gameView);

        TextView balls_left_textView = findViewById(R.id.balls_number_textView);
        balls_left_textView.setText(String.format("%d", gameState.getBallsLeft()));

        TextView goals_scored_textView = findViewById(R.id.goals_number_textView);
        goals_scored_textView.setText(String.format("%d", gameState.getGoalsScored()));

        View controlView = findViewById(R.id.control_background);
        controlView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int eventAction = event.getAction();
                float centerSideDistance = view.getWidth() / 2f;
                float x = event.getX() - centerSideDistance;
                float y = event.getY() - centerSideDistance;

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        gameState.getPlayer().getAcceleration().setAcceleration(x, y, centerSideDistance);
                        break;
                    default:
                        gameState.getPlayer().getAcceleration().nullAcceleration();
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
}