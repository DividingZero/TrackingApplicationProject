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
import android.widget.Button;
import android.widget.ListView;

public class Notifications extends AppCompatActivity {
    //private ListView list_view;
    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int notificationIdTwo = 112;
    private int numMessagesOne = 0;
    private int numMessagesTwo = 0;
    private String dummyTitle = "TitleNotFound!";
    private String dummyMessage = "Congratulations for your achievement! " + (MainActivity.achievement/10) + " Steps!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        //list_view = (ListView) findViewById(R.id.notifications_ListViewID);

        Button notOneBtn = (Button) findViewById(R.id.notificationOne);
        notOneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                displayNotificationOne(dummyTitle, dummyMessage);
            }
        });

        Button notTwoBtn = (Button) findViewById(R.id.notificationTwo);
        notTwoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                displayNotificationTwo();
            }
        });
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

    protected  void displayNotificationTwo() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "noti2");

        mBuilder.setContentTitle("New Message with implicit intent");
        mBuilder.setContentTitle("new message from psykoosi received");
        mBuilder.setTicker("Implicit: New message Received");
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background); //place holder icon

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String[] events = new String[3];
        events[0] = new String("1) Message for implicit intent");
        events[1] = new String("2) big view Notification");
        events[2] = new String("3) from psykoosi!");

        //sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("More Details:");
        //moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);

        //increase the notification number every time a new noti arrives
        mBuilder.setNumber(++numMessagesTwo);

        //when the user presses the notification, it is auto-removed
        mBuilder.setAutoCancel(true);

        //creates an implicit intent
        Intent resultIntent = new Intent("com.example.trackingapplicationproject.TEL_INTENT",
                Uri.parse("tel : 123456789"));
        resultIntent.putExtra("from", "psykoosi");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationsTwo.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
        mBuilder.setContentIntent(resultPendingIntent);
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.notify(notificationIdTwo, mBuilder.build());
    }
}


