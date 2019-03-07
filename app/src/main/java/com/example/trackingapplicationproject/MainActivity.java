package com.example.trackingapplicationproject;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    TextView stepcount;

    SensorManager sensorManager;

    boolean running = false;
    private Button notifications_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView date = (TextView)findViewById(R.id.currentDate);
        stepcount = (TextView) findViewById(R.id.stepCount);
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
            stepcount.setText(String.valueOf(event.values[0]));
        }
    }

    public void openNotifications() {
        Intent intent = new Intent(this, notifications.class);
        startActivity(intent);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

