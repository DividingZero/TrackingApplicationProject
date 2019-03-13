package com.example.trackingapplicationproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    TextView stepcount, kilometers, calories, timerunning, averagespeed;
    public static long startTime;
    SensorManager sensorManager;
    private float stepValue, timeValue;
    private String congratulationsMessage;
    public static float achievement = 100;

    boolean running = false;
    private Button notifications_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        achievement = 100;
        TextView date = (TextView)findViewById(R.id.currentDate);
        startTime = SystemClock.elapsedRealtime();
        stepcount = (TextView) findViewById(R.id.stepCount);
        kilometers = (TextView) findViewById(R.id.Kilometers);
        calories = (TextView) findViewById(R.id.cal);
        timerunning = (TextView) findViewById(R.id.timerunning);
        averagespeed = (TextView) findViewById(R.id.averagespeed);
        Log.d("Debug", "onCreate method");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setDate(date);

        notifications_button = (Button) findViewById(R.id.button8);
        notifications_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotifications();
            }
        });
    }
    public void setDate (TextView view){

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
        if(countSensor != null){

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
        Log.d("Debug", "onSensorChanged method");
        if(running){
            timeValue = (SystemClock.elapsedRealtime() - startTime);
            if(timeValue<=(60/1000)){

                timerunning.setText(String.valueOf((SystemClock.elapsedRealtime() - startTime) / 1000 + " s"));

            }
            else if(timeValue>=(3599/1000)){

                timerunning.setText(String.valueOf(((SystemClock.elapsedRealtime() - startTime) / 1000) / 60 + " min"));

            }
            else{

                timerunning.setText(String.valueOf((((SystemClock.elapsedRealtime() - startTime) / 1000) / 60) / 60 + " h"));

            }
            stepValue = event.values[0];
            if(stepValue>achievement){

             //   congratulationsMessage = "Congratulations for reaching " + achievement + " steps!";

             //   Notifications not = new Notifications();

             //   not.displayNotificationOne("Congratulations!", congratulationsMessage);
                achievement *= 10;

            }
            stepcount.setText(String.valueOf(event.values[0]));
            kilometers.setText(String.valueOf(event.values[0]/1312) + " km");
            calories.setText(String.valueOf(event.values[0]*0.05) + " cal");
            averagespeed.setText(String.valueOf(((event.values[0]/1312)/((SystemClock.elapsedRealtime() - startTime) / 1000) * 3600 )) + " km/h");



        }
    }

    public void openNotifications() {
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}