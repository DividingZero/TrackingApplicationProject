package com.example.trackingapplicationproject;

import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static int ageInput, heightInput, weightInput;
    public static long startTime;
    public static float achievement = 100;
    private int distanceCast, speedCast, timeCast, calorieCast, stepsCast;
    SensorManager sensorManager;
    TextView stepcount, kilometers, calories, timerunning, averagespeed;
    boolean running = false;
    private float stepValue, timeValue;
    private static final String PREF = "TestPref";
    private String congratulationsMessage;
    private Button notifications_button, settings_button;
    private SharedPreferences prefGet;
    private Saver saver;
    private SensorEvent sensorEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        achievement = 100;
        timeValue = 1;
        TextView date = (TextView) findViewById(R.id.currentDate);
        startTime = SystemClock.elapsedRealtime();
        stepcount = (TextView) findViewById(R.id.stepCount);
        kilometers = (TextView) findViewById(R.id.Kilometers);
        calories = (TextView) findViewById(R.id.cal);
        timerunning = (TextView) findViewById(R.id.timerunning);
        averagespeed = (TextView) findViewById(R.id.averagespeed);
        Log.d("Debug", "onCreate method");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setDate(date);
        prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        saver = new Saver(prefGet);
        stepValue = saver.dailyGet();
        notifications_button = (Button) findViewById(R.id.button8);

        notifications_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotifications();
            }
        });
        settings_button = (Button) findViewById(R.id.button7);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
    }

    public void setDate(TextView view) {

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formatting the date
        String date = formatter.format(today);
        view.setText(date);

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Log.d("Debug", "onResume method");
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {

            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

        } else {

            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorEvent = event;
        Log.d("Debug", "onSensorChanged method");
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormat dftwo = new DecimalFormat("#");


        if (running) {


            timeValue = (SystemClock.elapsedRealtime() - startTime);
            stepValue = event.values[0];
            stepsCast = (int) event.values[0];


            if (event.values[0] > 1000) {

                stepcount.setTextSize(50);

            } else if (event.values[0] < 1000) {

                stepcount.setTextSize(75);
            }
            if (timeValue <= 0.99) {

                event.values[0] = 0;

            } else {

                if (timeValue <= (60 / 1000)) {

                    timerunning.setText(String.valueOf((SystemClock.elapsedRealtime() - startTime) / 1000 + " s"));

                } else if (timeValue >= (3599 / 1000)) {

                    timerunning.setText(String.valueOf(((SystemClock.elapsedRealtime() - startTime) / 1000) / 60 + " min"));

                } else {

                    timerunning.setText(String.valueOf((((SystemClock.elapsedRealtime() - startTime) / 1000) / 60) / 60 + " h"));

                }

                if (stepValue > achievement) {

                    achievement *= 10;

                }

                stepcount.setText(String.valueOf(stepsCast));

                distanceCast = (int) (event.values[0] / 1.312);

                if (event.values[0] < 1312) {

                    kilometers.setText(String.valueOf(distanceCast) + " m");

                } else {

                    kilometers.setText(String.valueOf(distanceCast / 1000) + " km");

                }
                calorieCast = (int) (distanceCast/1000);
                calories.setText(String.valueOf(calorieCast * 0.91 * weightInput) + " cal");
                speedCast = (int) ((event.values[0] / 1312) / ((SystemClock.elapsedRealtime() - startTime) / 1000) * 3600);
                averagespeed.setText(String.valueOf(speedCast) + " km/h");
            }
        }


    }

    public void openNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }

    public void openSettings() {

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formatting the date
        String date = formatter.format(today);

        String first = "";
        first += date.charAt(0) + date.charAt(1);
        saver.monthlySave(stepValue);
        saver.dailySave(stepValue);
        reset(sensorEvent);


    }

    public void reset(SensorEvent event) {
        event.values[0] = 0;
    }

}