package com.example.epicalfootball;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private GameState gameState;
    private Player player;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        gameState = new GameState();
        player = new Player();

        TextView balls_left_textView = (TextView) findViewById(R.id.balls_number_textView);
        balls_left_textView.setText(String.format("%d", gameState.getBallsLeft()));

        TextView goals_scored_textView = (TextView) findViewById(R.id.goals_number_textView);
        goals_scored_textView.setText(String.format("%d", gameState.getGoalsScored()));

        SurfaceView controlView = (SurfaceView) findViewById(R.id.control_view);
        controlView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int eventAction = event.getAction();
                float side = view.getWidth();
                float x = event.getX() - view.getLeft() - side/2;
                float y = event.getY() - view.getTop() - side/2;

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("TOUCH", "Touched View");
                        AccelerationVector v = new AccelerationVector();
                        v.setAcceleration(x, y, side);
                        player.setAcceleration(v.getMagnitude());
                        player.setAccelerationDirection(v.getDirection());
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    default:
                        Log.d("TOUCH", "Released");
                        break;
                }

                return true;
            }
        });
    }

}