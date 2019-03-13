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

    // Initializing class variables and instances.
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

    // Creating layout and setting everything up
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting default values for static variables
        achievement = 100;
        timeValue = 1;

        // Setting "date" to the currentdate textview
        TextView date = (TextView) findViewById(R.id.currentDate);

        // Setting the system startTime reference
        startTime = SystemClock.elapsedRealtime();

        // Setting all textviews to their corresponding instance variables
        stepcount = (TextView) findViewById(R.id.stepCount);
        kilometers = (TextView) findViewById(R.id.Kilometers);
        calories = (TextView) findViewById(R.id.cal);
        timerunning = (TextView) findViewById(R.id.timerunning);
        averagespeed = (TextView) findViewById(R.id.averagespeed);

        // In onCreate method
        Log.d("Debug", "onCreate method");

        // Setting sensormanager to the contex "SENSOR_SERVICE"
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Calling the setDate method with the date TextView as the parameter
        setDate(date);

        // Setting saver up
        prefGet = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        saver = new Saver(prefGet);
        stepValue = saver.dailyGet();

        // Setting notifications_button to it's corresponding widget
        notifications_button = (Button) findViewById(R.id.button8);


        // Setting onclick listener for both bottom buttons, the buttons will take the user to their corresponding activities
        notifications_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Go to the notificatons tab
                openNotifications();
            }
        });
        settings_button = (Button) findViewById(R.id.button7);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Go to the settings tab
                openSettings();
            }
        });
    }


    //The setDate function is used to set the date of the TextView that is inputted.
    public void setDate(TextView view) {

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formatting the date
        String date = formatter.format(today);
        view.setText(date);

    }


    // The onResume method is utilized to reinstantiate the accelerometer
    @Override
    protected void onResume() {

        super.onResume();
        running = true;
        Log.d("Debug", "onResume method");

        // Initializing the sensorManager as TYPE_STEP_COUNTER
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // If countSensor exists, register the listener, if it doesn't tell the user that the sensor doesn't exist
        if (countSensor != null) {

            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

        } else {

            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();

        }

    }


    // The onPause method is used to set the running variable to false so the application wont gather data.
    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }


    // The onSensorChanged method is used to calculate everything from steps to calories using the SensorEvent object
    @Override
    public void onSensorChanged(SensorEvent event) {

        // Setting sensorEvent as event
        sensorEvent = event;
        Log.d("Debug", "onSensorChanged method");



        // Check if the application is running, if it is not runnning, then don't do anything.
        if (running) {

            // Set the clock value and step values
            timeValue = (SystemClock.elapsedRealtime() - startTime);
            stepValue = event.values[0];
            stepsCast = (int) event.values[0];


            // Check if the event values are too big for the textview to display properly
            if (event.values[0] > 1000) {

                stepcount.setTextSize(50);

            } else if (event.values[0] < 1000) {

                stepcount.setTextSize(75);
            }

            // Check if time is less than 0.99, if it's not then continue updating
            if (timeValue <= 0.99) {

                event.values[0] = 0;

            } else {


                // Set values for time in multiple formats
                if (timeValue <= (60 / 1000)) {

                    timerunning.setText(String.valueOf((SystemClock.elapsedRealtime() - startTime) / 1000 + " s"));

                } else if (timeValue >= (3599 / 1000)) {

                    timerunning.setText(String.valueOf(((SystemClock.elapsedRealtime() - startTime) / 1000) / 60 + " min"));

                } else {

                    timerunning.setText(String.valueOf((((SystemClock.elapsedRealtime() - startTime) / 1000) / 60) / 60 + " h"));

                }

                // Check if the user has preceded the current achievement.
                if (stepValue > achievement) {

                    achievement *= 10;

                }

                // Set the value of stepcount to stepsCast
                stepcount.setText(String.valueOf(stepsCast));


                // Calculate kilometers and meters and check which one is appropriate to display
                distanceCast = (int) (event.values[0] / 1.312);

                if (event.values[0] < 1312) {

                    kilometers.setText(String.valueOf(distanceCast) + " m");

                } else {

                    kilometers.setText(String.valueOf(distanceCast / 1000) + " km");

                }

                // Set the relative value of calorieCast to kilometers
                calorieCast = distanceCast/1000;

                // If the user hasn't inputted their weight, the weightInput variable will default to 60kg
                if(weightInput==0){

                    weightInput = 60;

                }

                // Calculate calories and display them on the activity
                calories.setText(String.valueOf(calorieCast * 0.91 * weightInput) + " cal");

                // Calculate speed and display it on the activity
                speedCast = (int) ((event.values[0] / 1312) / ((SystemClock.elapsedRealtime() - startTime) / 1000) * 3600);

                // Calculate average speed and display it on the activity
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