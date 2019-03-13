package com.example.trackingapplicationproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    // Initializing variables
    private ListView list_view;
    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int notificationIdTwo = 112;
    private int numMessagesOne = 0;
    private int numMessagesTwo = 0;
    private String dummyTitle = "TitleNotFound!";
    private String dummyMessage = "Congratulations for your achievement! " + (MainActivity.achievement/10) + " Steps!";

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initializing layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initializing listview which points to notifications_ListViewID
        list_view = (ListView) findViewById(R.id.notifications_ListViewID);

        // Initializing ArrayList for storing strings for achievements
        ArrayList<String> arrayList=new ArrayList<>();

        // Greeting users with messaage if achievements value is 100
        if(MainActivity.achievement==100){
            arrayList.add("Welcome to the notifications tab!");
        }
        else {

            // Since achievements is not it's default value, we know that it has been activated and display the notification on arrayAdapter
            arrayList.add("Congratulations for reaching " + MainActivity.achievement / 10 + " steps!");
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            list_view.setAdapter(arrayAdapter);

            // Setting onItemClickListener for adapter
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // When item is clicked it will go into notification
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    displayNotificationOne(dummyTitle, dummyMessage);
                    Intent intent = new Intent(Notifications.this,NotificationsOne.class);
                    startActivity(intent);
                }
            });
        }
    }


    public void displayNotificationOne(String title, String message) {
        //default notification service
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "noti1");

        mBuilder.setContentTitle(title);
        mBuilder.setContentTitle(message);
        mBuilder.setTicker("Explicit: New Message Received!");
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background); //place holder icon

        //increases noti number every time a new one arrives
        mBuilder.setNumber(++numMessagesOne);

        //creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, NotificationsOne.class);
        resultIntent.putExtra("notificationId", notificationIdOne);

        //Navigating backward from Activity leads out of the app to Home page || Min API problem
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        //adds the back stack for the Intent
        stackBuilder.addParentStack(NotificationsOne.class);

        //adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_ONE_SHOT); //can only be used once
        // start the activity when the user clicks the notification text
        mBuilder.setContentIntent(resultPendingIntent);
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //pass the notification object to the system
        myNotificationManager.notify(notificationIdOne, mBuilder.build());

    }
}
/*
Source:
https://examples.javacodegeeks.com/android/core/ui/notifications/android-notifications-example/
 */