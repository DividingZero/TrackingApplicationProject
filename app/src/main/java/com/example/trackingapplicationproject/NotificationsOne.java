package com.example.trackingapplicationproject;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class NotificationsOne extends Activity {

    // Creating the layout and assigning it to activity_noti1
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti1);

        // Setting the charactersequence for reaching the achievement
        CharSequence s = "Congratulations for reaching " + (MainActivity.achievement/10) + " steps!";
        int id = 0;

        // Checking if extras is null to detect whether the message should be displayed or not.
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            s = "Congratulations for reaching " + (MainActivity.achievement/10) + " steps!";
        } else {
            id = extras.getInt("notificationId");
        }

        // Editing the text view to display the next goal the user should reach for.
        TextView t = (TextView) findViewById(R.id.text1);
        t.setText(s + " Next goal is " + MainActivity.achievement + "!");
        NotificationManager myNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // removes the notification with the specific id
        myNotificationManager.cancel(id);
    }
}
/*
Source:
https://examples.javacodegeeks.com/android/core/ui/notifications/android-notifications-example/
 */