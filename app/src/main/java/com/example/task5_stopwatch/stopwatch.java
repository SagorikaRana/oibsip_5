package com.example.task5_stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class stopwatch extends AppCompatActivity {

    ImageButton startBtn, holdBtn;
    Chronometer chronometer;
    ListView listHold;
    private boolean isStarted;

    Handler handler;
    long tMiliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, miliSec;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        startBtn = findViewById(R.id.startStopBtn);
        holdBtn = findViewById(R.id.holdResetBtn);
        chronometer = findViewById(R.id.chronometer);
        listHold = findViewById(R.id.listHold);


        ArrayList<String> holds = new ArrayList<>();
        ArrayAdapter<String> list = (new ArrayAdapter(stopwatch.this, R.layout.list_item, R.id.listItems, holds));
        listHold.setAdapter(list);


        handler = new Handler();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStarted) {
                    isStarted = true;
                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.stop));
                    holdBtn.setImageDrawable(getResources().getDrawable(R.drawable.hold));

                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                } else {
                    isStarted = false;
                    startBtn.setImageDrawable(getResources().getDrawable(R.drawable.start));
                    holdBtn.setImageDrawable(getResources().getDrawable(R.drawable.reset));

                    tBuff = tBuff + tMiliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                }
            }
        });

        holdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStarted) {
                    tMiliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    miliSec = 0;
                    chronometer.setText("00:00:00");
                    holds.clear();

                } else {
                    holds.add(chronometer.getText().toString());

                }
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMiliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMiliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            miliSec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", miliSec));
            handler.postDelayed(this, 60);
        }
    };
}