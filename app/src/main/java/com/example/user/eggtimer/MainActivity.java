package com.example.user.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button controlButton;
    boolean controllerActive = false;
    SeekBar timerSeekBar;
    TextView timerTextView;
    MediaPlayer mp;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        controlButton.setText("Go!");
        controllerActive=false;
        countDownTimer.cancel();
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
    }

    public void updateTimer(int progress){

            int minutes = (int) progress / 60;
            int seconds = progress - minutes * 60;
            String secString = Integer.toString(seconds);
            String minString = Integer.toString(minutes);
            if (seconds / 10 == 0) {
                secString = "0" + Integer.toString(seconds);
            }
            if (minutes / 10 == 0) {
                minString = "0" + Integer.toString(minutes);
            }
            timerTextView.setText(minString+":"+secString);

    }

    public void controlTimer(View view) {
        if (controllerActive == false) {
            controllerActive = true;
            timerSeekBar.setEnabled(false);
            controlButton.setText("STOP");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mp.start();
                }
            }.start();
        }else{
            resetTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlButton = (Button)findViewById(R.id.controlButton);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
