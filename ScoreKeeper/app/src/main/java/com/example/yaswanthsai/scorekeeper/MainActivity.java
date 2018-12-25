package com.example.yaswanthsai.scorekeeper;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long start_time = 90000; // 1 min 30 sec
    private TextView timerText;
    private TextView statusText;
    private Button timerButton;
    private Button resetButton;
    private CountDownTimer countDownTimer;
    private long timeLeft = start_time;
    private boolean timerRunning;
    public static int team_A_Score = 0;
    public static int team_B_Score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.timer_text);
        timerButton = findViewById(R.id.timerButton);
        resetButton = findViewById(R.id.reset);
        resetButton.setVisibility(View.INVISIBLE);
        make_enable(false);

    }

    /*leading_text is for showing the which is in lead  during the Match*/
    private void leading_text() {
        statusText = findViewById(R.id.status);
        if ((team_A_Score == team_B_Score) && ((team_B_Score != 0))) {
            statusText.setText(R.string.equal_score);
        } else {
            if (team_A_Score > team_B_Score)
                statusText.setText(R.string.teamA_lead);
            else
                statusText.setText(R.string.teamB_lead);
        }
    }

    /*won_text is for showing which team has won the Match after the time completed*/
    private void won_text() {
        statusText = findViewById(R.id.status);
        if ((team_A_Score == team_B_Score)) {
            statusText.setText(R.string.tied);
        } else {
            if (team_A_Score > team_B_Score)
                statusText.setText(R.string.teamA_win);
            else
                statusText.setText(R.string.teamB_win);
        }
    }

    /*goal_for_A or B is invoked when a click on goal button happens*/
    /*foul_for_A or B is invoked when a click on Foul button happens*/
    public void goal_for_A(View view) {
        team_A_Score = team_A_Score + 3;
        display_score(team_A_Score, 'A');
    }

    public void foul_for_A(View view) {
        team_A_Score = team_A_Score - 1;
        display_score(team_A_Score, 'A');
    }

    public void goal_for_B(View view) {
        team_B_Score = team_B_Score + 3;
        display_score(team_B_Score, 'B');
    }

    public void foul_for_B(View view) {
        team_B_Score = team_B_Score - 1;
        display_score(team_B_Score, 'B');
    }

    /* These Methods are used for Game Timer */
    public void startStop(View view) {
        if (timerRunning) {
            make_enable(false);
            stopTimer();
        } else
            startTimer();
        updateTimer();

    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timerButton.setEnabled(false);
                timerButton.setText(R.string.start_btn_txt);
                timerText.setText(R.string.end_time);
                make_enable(false);
                won_text();
                Toast.makeText(getApplicationContext(), "TIME UP ", Toast.LENGTH_LONG).show();
            }
        }.start();
        timerButton.setText(R.string.buttonTextPas);
        resetButton.setVisibility(View.VISIBLE);
        make_enable(true);
        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerButton.setText(R.string.buttonTextRes);
        timerRunning = false;
    }

    public void updateTimer() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftText = String.format(Locale.getDefault(), "%02d : %02d ", minutes, seconds);
        timerText.setText(timeLeftText);
    }

    /* DISPLAYING THE SCORES */
    public void display_score(int score, char team) {

        if (team == 'A') {
            TextView A_scoreText = findViewById(R.id.team_A_score);
            A_scoreText.setText(String.valueOf(score));

        }
        if (team == 'B') {
            TextView B_scoreText = findViewById(R.id.team_B_score);
            B_scoreText.setText(String.valueOf(score));

        }
        leading_text();

    }

    /* RESET THE SCORECARD */
    public void reset_score(View view) {
        timeLeft = start_time;
        updateTimer();
        stopTimer();
        timerButton.setText(R.string.start_btn_txt);
        resetButton.setVisibility(View.INVISIBLE);
        team_A_Score = 0;
        team_B_Score = 0;
        display_score(team_A_Score, 'A');
        display_score(team_B_Score, 'B');
        statusText.setText(R.string.status);
        timerButton.setEnabled(true);
        make_enable(false);
    }

    /* FOR MAKING BUTTON"S ENABLE & DISABLE */
    public void make_enable(boolean x) {
        Button goalA, goalB, foulA, foulB;
        goalA = findViewById(R.id.team_A_Goal);
        goalB = findViewById(R.id.team_B_Goal);
        foulA = findViewById(R.id.team_A_foul);
        foulB = findViewById(R.id.team_B_foul);
        if (x) {
            goalA.setEnabled(true);
            goalB.setEnabled(true);
            foulA.setEnabled(true);
            foulB.setEnabled(true);
        } else {
            goalA.setEnabled(false);
            goalB.setEnabled(false);
            foulA.setEnabled(false);
            foulB.setEnabled(false);
        }
    }
}
