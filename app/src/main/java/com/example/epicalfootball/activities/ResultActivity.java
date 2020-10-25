package com.example.epicalfootball.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.epicalfootball.R;

public class ResultActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toFullScreen();
        setContentView(R.layout.result);

        int goalsScored = getIntent().getIntExtra("goals_scored", 0);
        TextView resultView = findViewById(R.id.result_textView);
        resultView.setText("You scored " + goalsScored + " goals!");
    }

    protected void onResume() {
        super.onResume();
        toFullScreen();
    }

    public void toFullScreen() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void goToMenu(View view) {
        Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
        intent.putExtra("fromResult", true);
        startActivity(intent);
    }
}
