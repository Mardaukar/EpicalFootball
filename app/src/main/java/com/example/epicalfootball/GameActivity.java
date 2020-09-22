package com.example.epicalfootball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
                float x = event.getX();
                float y = event.getY();
                float sideLength = view.getWidth();

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (x >= sideLength * 0.40 && x <= sideLength * 0.60 && y >= sideLength * 0.40 && y <= sideLength * 0.60) {
                            gameState.setControlOffWithDecelerate(true);
                        } else if(x >= 0 && x <= sideLength && y >= 0 && y <= sideLength) {
                            gameState.setControlOn(x - sideLength / 2, y - sideLength / 2, sideLength);
                        } else {
                            gameState.setControlOffWithDecelerate(false);
                        }
                        break;
                    default:
                        gameState.setControlOffWithDecelerate(false);
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