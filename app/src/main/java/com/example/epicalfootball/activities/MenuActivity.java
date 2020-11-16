package com.example.epicalfootball.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.HashMap;

import com.example.epicalfootball.R;
import static com.example.epicalfootball.Constants.*;

public class MenuActivity extends Activity {

    private int playerReachAttribute = DEFAULT_ATTRIBUTE;
    private int playerAccelerationAttribute = DEFAULT_ATTRIBUTE;
    private int playerSpeedAttribute = DEFAULT_ATTRIBUTE;
    private int playerBallControlAttribute = DEFAULT_ATTRIBUTE;
    private int playerDribblingAttribute = DEFAULT_ATTRIBUTE;
    private int playerShotPowerAttribute = DEFAULT_ATTRIBUTE;
    private int playerAccuracyAttribute = DEFAULT_ATTRIBUTE;
    private int playerFinishingAttribute = DEFAULT_ATTRIBUTE;
    private int playerLongShotsAttribute = DEFAULT_ATTRIBUTE;
    public static HashMap<String, Integer> playerAttributes = new HashMap<>();

    private int goalkeeperReachAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperAgilityAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperSpeedAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperReflexesAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperReactionSavesAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperBallHandlingAttribute = DEFAULT_ATTRIBUTE;
    private int goalkeeperGoalkeepingIntelligenceAttribute = DEFAULT_ATTRIBUTE;
    public static HashMap<String, Integer> goalkeeperAttributes = new HashMap<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toFullScreen();

        if (getIntent().getBooleanExtra(FROM_RESULT_INTENT_TEXT, false)) {
            playerReachAttribute = playerAttributes.get(PLAYER_REACH_KEY);
            playerAccelerationAttribute = playerAttributes.get(PLAYER_ACCELERATION_KEY);
            playerSpeedAttribute = playerAttributes.get(PLAYER_SPEED_KEY);
            playerBallControlAttribute = playerAttributes.get(PLAYER_BALL_CONTROL_KEY);
            playerDribblingAttribute = playerAttributes.get(PLAYER_DRIBBLING_KEY);
            playerShotPowerAttribute = playerAttributes.get(PLAYER_SHOT_POWER_KEY);
            playerAccuracyAttribute = playerAttributes.get(PLAYER_ACCURACY_KEY);
            playerFinishingAttribute = playerAttributes.get(PLAYER_FINISHING_KEY);
            playerLongShotsAttribute = playerAttributes.get(PLAYER_LONG_SHOTS_KEY);

            goalkeeperReachAttribute = goalkeeperAttributes.get(GOALKEEPER_REACH_KEY);
            goalkeeperAgilityAttribute = goalkeeperAttributes.get(GOALKEEPER_AGILITY_KEY);
            goalkeeperSpeedAttribute = goalkeeperAttributes.get(GOALKEEPER_SPEED_KEY);
            goalkeeperReflexesAttribute = goalkeeperAttributes.get(GOALKEEPER_REFLEXES_KEY);
            goalkeeperReactionSavesAttribute = goalkeeperAttributes.get(GOALKEEPER_REACTION_SAVES_KEY);
            goalkeeperBallHandlingAttribute = goalkeeperAttributes.get(GOALKEEPER_BALL_HANDLING_KEY);
            goalkeeperGoalkeepingIntelligenceAttribute = goalkeeperAttributes.get(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY);
        }

        setContentView(R.layout.menu_player_attributes);
        setPlayerAttributes();
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

    public void switchToPlayerAttributes(View view) {
        setContentView(R.layout.menu_player_attributes);
        setPlayerAttributes();
    }

    public void switchToGoalkeeperAttributes(View view) {
        setContentView(R.layout.menu_goalkeeper_attributes);
        setGoalkeeperAttributes();
    }

    public void setPlayerAttributes() {
        TextView playerReachView = findViewById(R.id.player_reach_attribute_textView);
        playerReachView.setText(Integer.toString(this.playerReachAttribute));

        TextView playerAccelerationView = findViewById(R.id.player_acceleration_attribute_textView);
        playerAccelerationView.setText(Integer.toString(this.playerAccelerationAttribute));

        TextView playerSpeedView = findViewById(R.id.player_speed_attribute_textView);
        playerSpeedView.setText(Integer.toString(this.playerSpeedAttribute));

        TextView playerBallControlView = findViewById(R.id.player_ball_control_attribute_textView);
        playerBallControlView.setText(Integer.toString(this.playerBallControlAttribute));

        TextView playerDribblingView = findViewById(R.id.player_dribbling_attribute_textView);
        playerDribblingView.setText(Integer.toString(this.playerDribblingAttribute));

        TextView playerShotPowerView = findViewById(R.id.player_shot_power_attribute_textView);
        playerShotPowerView.setText(Integer.toString(this.playerShotPowerAttribute));

        TextView playerAccuracyView = findViewById(R.id.player_accuracy_attribute_textView);
        playerAccuracyView.setText(Integer.toString(this.playerAccuracyAttribute));

        TextView playerFinishingView = findViewById(R.id.player_finishing_attribute_textView);
        playerFinishingView.setText(Integer.toString(this.playerFinishingAttribute));

        TextView playerLongShotsView = findViewById(R.id.player_long_shots_attribute_textView);
        playerLongShotsView.setText(Integer.toString(this.playerLongShotsAttribute));
    }

    public void setGoalkeeperAttributes() {
        TextView goalkeeperReachView = findViewById(R.id.goalkeeper_reach_attribute_textView);
        goalkeeperReachView.setText(Integer.toString(this.goalkeeperReachAttribute));

        TextView goalkeeperAgilityView = findViewById(R.id.goalkeeper_agility_attribute_textView);
        goalkeeperAgilityView.setText(Integer.toString(this.goalkeeperAgilityAttribute));

        TextView goalkeeperSpeedView = findViewById(R.id.goalkeeper_speed_attribute_textView);
        goalkeeperSpeedView.setText(Integer.toString(this.goalkeeperSpeedAttribute));

        TextView goalkeeperReflexesView = findViewById(R.id.goalkeeper_reflexes_attribute_textView);
        goalkeeperReflexesView.setText(Integer.toString(this.goalkeeperReflexesAttribute));

        //TextView goalkeeperReactionSavesView = findViewById(R.id.goalkeeper_reaction_saves_attribute_textView);
        //goalkeeperReactionSavesView.setText(Integer.toString(this.goalkeeperReactionSavesAttribute));

        TextView goalkeeperBallHandlingView = findViewById(R.id.goalkeeper_ball_handling_attribute_textView);
        goalkeeperBallHandlingView.setText(Integer.toString(this.goalkeeperBallHandlingAttribute));

        TextView goalkeeperGoalkeepingIntelligenceView = findViewById(R.id.goalkeeper_goalkeeping_intelligence_attribute_textView);
        goalkeeperGoalkeepingIntelligenceView.setText(Integer.toString(this.goalkeeperGoalkeepingIntelligenceAttribute));
    }

    public void startGame(View view) {
        playerAttributes.put(PLAYER_REACH_KEY, playerReachAttribute);
        playerAttributes.put(PLAYER_ACCELERATION_KEY, playerAccelerationAttribute);
        playerAttributes.put(PLAYER_SPEED_KEY, playerSpeedAttribute);
        playerAttributes.put(PLAYER_BALL_CONTROL_KEY, playerBallControlAttribute);
        playerAttributes.put(PLAYER_DRIBBLING_KEY, playerDribblingAttribute);
        playerAttributes.put(PLAYER_SHOT_POWER_KEY, playerShotPowerAttribute);
        playerAttributes.put(PLAYER_ACCURACY_KEY, playerAccuracyAttribute);
        playerAttributes.put(PLAYER_FINISHING_KEY, playerFinishingAttribute);
        playerAttributes.put(PLAYER_LONG_SHOTS_KEY, playerLongShotsAttribute);

        goalkeeperAttributes.put(GOALKEEPER_REACH_KEY, goalkeeperReachAttribute);
        goalkeeperAttributes.put(GOALKEEPER_AGILITY_KEY, goalkeeperAgilityAttribute);
        goalkeeperAttributes.put(GOALKEEPER_SPEED_KEY, goalkeeperSpeedAttribute);
        goalkeeperAttributes.put(GOALKEEPER_REFLEXES_KEY, goalkeeperReflexesAttribute);
        goalkeeperAttributes.put(GOALKEEPER_BALL_HANDLING_KEY, goalkeeperBallHandlingAttribute);
        goalkeeperAttributes.put(GOALKEEPER_REACTION_SAVES_KEY, goalkeeperReactionSavesAttribute);
        goalkeeperAttributes.put(GOALKEEPER_GOALKEEPING_INTELLIGENCE_KEY, goalkeeperGoalkeepingIntelligenceAttribute);

        Intent startGameIntent = new Intent(this, MatchActivity.class);
        startActivity(startGameIntent);
    }

    public void decreasePlayerReach(View view) {
        if (this.playerReachAttribute > MIN_ATTRIBUTE) {
            this.playerReachAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerReachView = findViewById(R.id.player_reach_attribute_textView);
            playerReachView.setText(Integer.toString(this.playerReachAttribute));
        }
    }

    public void increasePlayerReach(View view) {
        if (this.playerReachAttribute < MAX_ATTRIBUTE) {
            this.playerReachAttribute += ATTRIBUTE_INCREMENT;
            TextView playerReachView = findViewById(R.id.player_reach_attribute_textView);
            playerReachView.setText(Integer.toString(this.playerReachAttribute));
        }
    }

    public void decreasePlayerAcceleration(View view) {
        if (this.playerAccelerationAttribute > MIN_ATTRIBUTE) {
            this.playerAccelerationAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerAccelerationView = findViewById(R.id.player_acceleration_attribute_textView);
            playerAccelerationView.setText(Integer.toString(this.playerAccelerationAttribute));
        }
    }

    public void increasePlayerAcceleration(View view) {
        if (this.playerAccelerationAttribute < MAX_ATTRIBUTE) {
            this.playerAccelerationAttribute += ATTRIBUTE_INCREMENT;
            TextView playerAccelerationView = findViewById(R.id.player_acceleration_attribute_textView);
            playerAccelerationView.setText(Integer.toString(this.playerAccelerationAttribute));
        }
    }

    public void decreasePlayerSpeed(View view) {
        if (this.playerSpeedAttribute > MIN_ATTRIBUTE) {
            this.playerSpeedAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerSpeedView = findViewById(R.id.player_speed_attribute_textView);
            playerSpeedView.setText(Integer.toString(this.playerSpeedAttribute));
        }
    }

    public void increasePlayerSpeed(View view) {
        if (this.playerSpeedAttribute < MAX_ATTRIBUTE) {
            this.playerSpeedAttribute += ATTRIBUTE_INCREMENT;
            TextView playerSpeedView = findViewById(R.id.player_speed_attribute_textView);
            playerSpeedView.setText(Integer.toString(this.playerSpeedAttribute));
        }
    }

    public void decreasePlayerBallControl(View view) {
        if (this.playerBallControlAttribute > MIN_ATTRIBUTE) {
            this.playerBallControlAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerBallControlView = findViewById(R.id.player_ball_control_attribute_textView);
            playerBallControlView.setText(Integer.toString(this.playerBallControlAttribute));
        }
    }

    public void increasePlayerBallControl(View view) {
        if (this.playerBallControlAttribute < MAX_ATTRIBUTE) {
            this.playerBallControlAttribute += ATTRIBUTE_INCREMENT;
            TextView playerBallControlView = findViewById(R.id.player_ball_control_attribute_textView);
            playerBallControlView.setText(Integer.toString(this.playerBallControlAttribute));
        }
    }

    public void decreasePlayerDribbling(View view) {
        if (this.playerDribblingAttribute > MIN_ATTRIBUTE) {
            this.playerDribblingAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerDribblingView = findViewById(R.id.player_dribbling_attribute_textView);
            playerDribblingView.setText(Integer.toString(this.playerDribblingAttribute));
        }
    }

    public void increasePlayerDribbling(View view) {
        if (this.playerDribblingAttribute < MAX_ATTRIBUTE) {
            this.playerDribblingAttribute += ATTRIBUTE_INCREMENT;
            TextView playerDribblingView = findViewById(R.id.player_dribbling_attribute_textView);
            playerDribblingView.setText(Integer.toString(this.playerDribblingAttribute));
        }
    }

    public void decreasePlayerShotPower(View view) {
        if (this.playerShotPowerAttribute > MIN_ATTRIBUTE) {
            this.playerShotPowerAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerShotPowerView = findViewById(R.id.player_shot_power_attribute_textView);
            playerShotPowerView.setText(Integer.toString(this.playerShotPowerAttribute));
        }
    }

    public void increasePlayerShotPower(View view) {
        if (this.playerShotPowerAttribute < MAX_ATTRIBUTE) {
            this.playerShotPowerAttribute += ATTRIBUTE_INCREMENT;
            TextView playerShotPowerView = findViewById(R.id.player_shot_power_attribute_textView);
            playerShotPowerView.setText(Integer.toString(this.playerShotPowerAttribute));
        }
    }

    public void decreasePlayerAccuracy(View view) {
        if (this.playerAccuracyAttribute > MIN_ATTRIBUTE) {
            this.playerAccuracyAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerAccuracyView = findViewById(R.id.player_accuracy_attribute_textView);
            playerAccuracyView.setText(Integer.toString(this.playerAccuracyAttribute));
        }
    }

    public void increasePlayerAccuracy(View view) {
        if (this.playerAccuracyAttribute < MAX_ATTRIBUTE) {
            this.playerAccuracyAttribute += ATTRIBUTE_INCREMENT;
            TextView playerAccuracyView = findViewById(R.id.player_accuracy_attribute_textView);
            playerAccuracyView.setText(Integer.toString(this.playerAccuracyAttribute));
        }
    }

    public void decreasePlayerFinishing(View view) {
        if (this.playerFinishingAttribute > MIN_ATTRIBUTE) {
            this.playerFinishingAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerFinishingView = findViewById(R.id.player_finishing_attribute_textView);
            playerFinishingView.setText(Integer.toString(this.playerFinishingAttribute));
        }
    }

    public void increasePlayerFinishing(View view) {
        if (this.playerFinishingAttribute < MAX_ATTRIBUTE) {
            this.playerFinishingAttribute += ATTRIBUTE_INCREMENT;
            TextView playerFinishingView = findViewById(R.id.player_finishing_attribute_textView);
            playerFinishingView.setText(Integer.toString(this.playerFinishingAttribute));
        }
    }

    public void decreasePlayerLongShots(View view) {
        if (this.playerLongShotsAttribute > MIN_ATTRIBUTE) {
            this.playerLongShotsAttribute -= ATTRIBUTE_INCREMENT;
            TextView playerLongShotsView = findViewById(R.id.player_long_shots_attribute_textView);
            playerLongShotsView.setText(Integer.toString(this.playerLongShotsAttribute));
        }
    }

    public void increasePlayerLongShots(View view) {
        if (this.playerLongShotsAttribute < MAX_ATTRIBUTE) {
            this.playerLongShotsAttribute += ATTRIBUTE_INCREMENT;
            TextView playerLongShotsView = findViewById(R.id.player_long_shots_attribute_textView);
            playerLongShotsView.setText(Integer.toString(this.playerLongShotsAttribute));
        }
    }

    public void decreaseGoalkeeperReach(View view) {
        if (this.goalkeeperReachAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperReachAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperReachView = findViewById(R.id.goalkeeper_reach_attribute_textView);
            goalkeeperReachView.setText(Integer.toString(this.goalkeeperReachAttribute));
        }
    }

    public void increaseGoalkeeperReach(View view) {
        if (this.goalkeeperReachAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperReachAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperReachView = findViewById(R.id.goalkeeper_reach_attribute_textView);
            goalkeeperReachView.setText(Integer.toString(this.goalkeeperReachAttribute));
        }
    }

    public void decreaseGoalkeeperAgility(View view) {
        if (this.goalkeeperAgilityAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperAgilityAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperAgilityView = findViewById(R.id.goalkeeper_agility_attribute_textView);
            goalkeeperAgilityView.setText(Integer.toString(this.goalkeeperAgilityAttribute));
        }
    }

    public void increaseGoalkeeperAgility(View view) {
        if (this.goalkeeperAgilityAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperAgilityAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperAgilityView = findViewById(R.id.goalkeeper_agility_attribute_textView);
            goalkeeperAgilityView.setText(Integer.toString(this.goalkeeperAgilityAttribute));
        }
    }

    public void decreaseGoalkeeperSpeed(View view) {
        if (this.goalkeeperSpeedAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperSpeedAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperSpeedView = findViewById(R.id.goalkeeper_speed_attribute_textView);
            goalkeeperSpeedView.setText(Integer.toString(this.goalkeeperSpeedAttribute));
        }
    }

    public void increaseGoalkeeperSpeed(View view) {
        if (this.goalkeeperSpeedAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperSpeedAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperSpeedView = findViewById(R.id.goalkeeper_speed_attribute_textView);
            goalkeeperSpeedView.setText(Integer.toString(this.goalkeeperSpeedAttribute));
        }
    }

    public void decreaseGoalkeeperReflexes(View view) {
        if (this.goalkeeperReflexesAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperReflexesAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperReflexesView = findViewById(R.id.goalkeeper_reflexes_attribute_textView);
            goalkeeperReflexesView.setText(Integer.toString(this.goalkeeperReflexesAttribute));
        }
    }

    public void increaseGoalkeeperReflexes(View view) {
        if (this.goalkeeperReflexesAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperReflexesAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperReflexesView = findViewById(R.id.goalkeeper_reflexes_attribute_textView);
            goalkeeperReflexesView.setText(Integer.toString(this.goalkeeperReflexesAttribute));
        }
    }

    public void decreaseGoalkeeperBallHandling(View view) {
        if (this.goalkeeperBallHandlingAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperBallHandlingAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperBallHandlingView = findViewById(R.id.goalkeeper_ball_handling_attribute_textView);
            goalkeeperBallHandlingView.setText(Integer.toString(this.goalkeeperBallHandlingAttribute));
        }
    }

    public void increaseGoalkeeperBallHandling(View view) {
        if (this.goalkeeperBallHandlingAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperBallHandlingAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperBallHandlingView = findViewById(R.id.goalkeeper_ball_handling_attribute_textView);
            goalkeeperBallHandlingView.setText(Integer.toString(this.goalkeeperBallHandlingAttribute));
        }
    }

    /*public void decreaseGoalkeeperReactionSaves(View view) {
        if (this.goalkeeperReactionSavesAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperReactionSavesAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperReactionSavesView = findViewById(R.id.goalkeeper_reaction_saves_attribute_textView);
            goalkeeperReactionSavesView.setText(Integer.toString(this.goalkeeperReactionSavesAttribute));
        }
    }

    public void increaseGoalkeeperReactionSaves(View view) {
        if (this.goalkeeperReactionSavesAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperReactionSavesAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperReactionSavesView = findViewById(R.id.goalkeeper_reaction_saves_attribute_textView);
            goalkeeperReactionSavesView.setText(Integer.toString(this.goalkeeperReactionSavesAttribute));
        }
    }*/

    public void decreaseGoalkeeperGoalkeepingIntelligence(View view) {
        if (this.goalkeeperGoalkeepingIntelligenceAttribute > MIN_ATTRIBUTE) {
            this.goalkeeperGoalkeepingIntelligenceAttribute -= ATTRIBUTE_INCREMENT;
            TextView goalkeeperGoalkeepingIntelligenceView = findViewById(R.id.goalkeeper_goalkeeping_intelligence_attribute_textView);
            goalkeeperGoalkeepingIntelligenceView.setText(Integer.toString(this.goalkeeperGoalkeepingIntelligenceAttribute));
        }
    }

    public void increaseGoalkeeperGoalkeepingIntelligence(View view) {
        if (this.goalkeeperGoalkeepingIntelligenceAttribute < MAX_ATTRIBUTE) {
            this.goalkeeperGoalkeepingIntelligenceAttribute += ATTRIBUTE_INCREMENT;
            TextView goalkeeperGoalkeepingIntelligenceView = findViewById(R.id.goalkeeper_goalkeeping_intelligence_attribute_textView);
            goalkeeperGoalkeepingIntelligenceView.setText(Integer.toString(this.goalkeeperGoalkeepingIntelligenceAttribute));
        }
    }
}