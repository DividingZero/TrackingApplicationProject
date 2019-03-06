package com.example.trackingapplicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button notifications_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView date = (TextView)findViewById(R.id.currentDate);
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

    public void openNotifications() {
        Intent intent = new Intent(this, notifications.class);
        startActivity(intent);
    }
}

