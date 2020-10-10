package com.example.epicalfootball;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    private int reachAttribute = 5;
    private int accelerationAttribute = 5;
    private int speedAttribute = 5;
    private int ballControlAttribute = 5;
    private int dribblingAttribute = 5;
    private int shotPowerAttribute = 5;
    private int accuracyAttribute = 5;
    private int finishingAttribute = 5;
    private int longShotsAttribute = 5;
    public static HashMap<String, Integer> playerAttributes= new HashMap<String, Integer>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (getIntent().getBooleanExtra("fromResult", false)) {
            reachAttribute = playerAttributes.get("reach");
            accelerationAttribute = playerAttributes.get("acceleration");
            speedAttribute = playerAttributes.get("speed");
            ballControlAttribute = playerAttributes.get("ballControl");
            dribblingAttribute = playerAttributes.get("dribbling");
            shotPowerAttribute = playerAttributes.get("shotPower");
            accuracyAttribute = playerAttributes.get("accuracy");
            finishingAttribute = playerAttributes.get("finishing");
            longShotsAttribute = playerAttributes.get("longShots");
        }

        TextView reachView = findViewById(R.id.reach_attribute_textView);
        reachView.setText(Integer.toString(this.reachAttribute));

        TextView accelerationView = findViewById(R.id.acceleration_attribute_textView);
        accelerationView.setText(Integer.toString(this.accelerationAttribute));

        TextView speedView = findViewById(R.id.speed_attribute_textView);
        speedView.setText(Integer.toString(this.speedAttribute));

        TextView ballControlView = findViewById(R.id.ball_control_attribute_textView);
        ballControlView.setText(Integer.toString(this.ballControlAttribute));

        TextView dribblingView = findViewById(R.id.dribbling_attribute_textView);
        dribblingView.setText(Integer.toString(this.dribblingAttribute));

        TextView shotPowerView = findViewById(R.id.shot_power_attribute_textView);
        shotPowerView.setText(Integer.toString(this.shotPowerAttribute));

        TextView accuracyView = findViewById(R.id.accuracy_attribute_textView);
        accuracyView.setText(Integer.toString(this.accuracyAttribute));

        TextView finishingView = findViewById(R.id.finishing_attribute_textView);
        finishingView.setText(Integer.toString(this.finishingAttribute));

        TextView longShotsView = findViewById(R.id.long_shots_attribute_textView);
        longShotsView.setText(Integer.toString(this.longShotsAttribute));
    }

    public void startGame(View view) {
        Intent startGameIntent = new Intent(this, GameActivity.class);
        playerAttributes.put("reach", reachAttribute);
        playerAttributes.put("acceleration", accelerationAttribute);
        playerAttributes.put("speed", speedAttribute);
        playerAttributes.put("ballControl", ballControlAttribute);
        playerAttributes.put("dribbling", dribblingAttribute);
        playerAttributes.put("shotPower", shotPowerAttribute);
        playerAttributes.put("accuracy", accuracyAttribute);
        playerAttributes.put("finishing", finishingAttribute);
        playerAttributes.put("longShots", longShotsAttribute);
        startActivity(startGameIntent);
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

    public void decreaseReach(View view) {
        if (this.reachAttribute > 0) {
            this.reachAttribute -= 1;
            TextView reachView = findViewById(R.id.reach_attribute_textView);
            reachView.setText(Integer.toString(this.reachAttribute));
        }
    }

    public void increaseReach(View view) {
        if (this.reachAttribute < 10) {
            this.reachAttribute += 1;
            TextView reachView = findViewById(R.id.reach_attribute_textView);
            reachView.setText(Integer.toString(this.reachAttribute));
        }
    }

    public void decreaseAcceleration(View view) {
        if (this.accelerationAttribute > 0) {
            this.accelerationAttribute -= 1;
            TextView accelerationView = findViewById(R.id.acceleration_attribute_textView);
            accelerationView.setText(Integer.toString(this.accelerationAttribute));
        }
    }

    public void increaseAcceleration(View view) {
        if (this.accelerationAttribute < 10) {
            this.accelerationAttribute += 1;
            TextView accelerationView = findViewById(R.id.acceleration_attribute_textView);
            accelerationView.setText(Integer.toString(this.accelerationAttribute));
        }
    }

    public void decreaseSpeed(View view) {
        if (this.speedAttribute > 0) {
            this.speedAttribute -= 1;
            TextView speedView = findViewById(R.id.speed_attribute_textView);
            speedView.setText(Integer.toString(this.speedAttribute));
        }
    }

    public void increaseSpeed(View view) {
        if (this.speedAttribute < 10) {
            this.speedAttribute += 1;
            TextView speedView = findViewById(R.id.speed_attribute_textView);
            speedView.setText(Integer.toString(this.speedAttribute));
        }
    }

    public void decreaseBallControl(View view) {
        if (this.ballControlAttribute > 0) {
            this.ballControlAttribute -= 1;
            TextView ballControlView = findViewById(R.id.ball_control_attribute_textView);
            ballControlView.setText(Integer.toString(this.ballControlAttribute));
        }
    }

    public void increaseBallControl(View view) {
        if (this.ballControlAttribute < 10) {
            this.ballControlAttribute += 1;
            TextView ballControlView = findViewById(R.id.ball_control_attribute_textView);
            ballControlView.setText(Integer.toString(this.ballControlAttribute));
        }
    }

    public void decreaseDribbling(View view) {
        if (this.dribblingAttribute > 0) {
            this.dribblingAttribute -= 1;
            TextView dribblingView = findViewById(R.id.dribbling_attribute_textView);
            dribblingView.setText(Integer.toString(this.dribblingAttribute));
        }
    }

    public void increaseDribbling(View view) {
        if (this.dribblingAttribute < 10) {
            this.dribblingAttribute += 1;
            TextView dribblingView = findViewById(R.id.dribbling_attribute_textView);
            dribblingView.setText(Integer.toString(this.dribblingAttribute));
        }
    }

    public void decreaseShotPower(View view) {
        if (this.shotPowerAttribute > 0) {
            this.shotPowerAttribute -= 1;
            TextView shotPowerView = findViewById(R.id.shot_power_attribute_textView);
            shotPowerView.setText(Integer.toString(this.shotPowerAttribute));
        }
    }

    public void increaseShotPower(View view) {
        if (this.shotPowerAttribute < 10) {
            this.shotPowerAttribute += 1;
            TextView shotPowerView = findViewById(R.id.shot_power_attribute_textView);
            shotPowerView.setText(Integer.toString(this.shotPowerAttribute));
        }
    }

    public void decreaseAccuracy(View view) {
        if (this.accuracyAttribute > 0) {
            this.accuracyAttribute -= 1;
            TextView accuracyView = findViewById(R.id.accuracy_attribute_textView);
            accuracyView.setText(Integer.toString(this.accuracyAttribute));
        }
    }

    public void increaseAccuracy(View view) {
        if (this.accuracyAttribute < 10) {
            this.accuracyAttribute += 1;
            TextView accuracyView = findViewById(R.id.accuracy_attribute_textView);
            accuracyView.setText(Integer.toString(this.accuracyAttribute));
        }
    }

    public void decreaseFinishing(View view) {
        if (this.finishingAttribute > 0) {
            this.finishingAttribute -= 1;
            TextView finishingView = findViewById(R.id.finishing_attribute_textView);
            finishingView.setText(Integer.toString(this.finishingAttribute));
        }
    }

    public void increaseFinishing(View view) {
        if (this.finishingAttribute < 10) {
            this.finishingAttribute += 1;
            TextView finishingView = findViewById(R.id.finishing_attribute_textView);
            finishingView.setText(Integer.toString(this.finishingAttribute));
        }
    }

    public void decreaseLongShots(View view) {
        if (this.longShotsAttribute > 0) {
            this.longShotsAttribute -= 1;
            TextView longShotsView = findViewById(R.id.long_shots_attribute_textView);
            longShotsView.setText(Integer.toString(this.longShotsAttribute));
        }
    }

    public void increaseLongShots(View view) {
        if (this.longShotsAttribute < 10) {
            this.longShotsAttribute += 1;
            TextView longShotsView = findViewById(R.id.long_shots_attribute_textView);
            longShotsView.setText(Integer.toString(this.longShotsAttribute));
        }
    }
}