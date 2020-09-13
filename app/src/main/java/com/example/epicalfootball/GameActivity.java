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
    private AccelerationVector playerAccelerationVector;

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
        playerAccelerationVector = new AccelerationVector();

        TextView balls_left_textView = (TextView) findViewById(R.id.balls_number_textView);
        balls_left_textView.setText(String.format("%d", gameState.getBallsLeft()));

        TextView goals_scored_textView = (TextView) findViewById(R.id.goals_number_textView);
        goals_scored_textView.setText(String.format("%d", gameState.getGoalsScored()));

        SurfaceView controlView = (SurfaceView) findViewById(R.id.control_view);
        controlView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int eventAction = event.getAction();
                float centerSideDistance = view.getWidth() / 2f;
                float x = event.getX() - centerSideDistance;
                float y = event.getY() - centerSideDistance;

                switch (eventAction) {
                    case MotionEvent.ACTION_DOWN:
                        //Log.d("TOUCH", "Touched View");
                        Log.d("TOUCH", "" + x + " " + y);
                        playerAccelerationVector.setAcceleration(x, y, centerSideDistance);
                        player.setAcceleration(playerAccelerationVector);
                        Log.d("TOUCH", "" + player.getAcceleration().getMagnitude() + " " + player.getAcceleration().getDirection());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //playerAccelerationVector.setAcceleration(x, y, centerSideDistance);
                        //player.setAcceleration(playerAccelerationVector);
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