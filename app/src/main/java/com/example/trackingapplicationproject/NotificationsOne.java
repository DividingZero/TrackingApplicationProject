package com.example.trackingapplicationproject;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class NotificationsOne extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti1);
        CharSequence s = "Congratulations for reaching " + (MainActivity.achievement/10) + " steps!";
        int id = 0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            s = "Congratulations for reaching " + (MainActivity.achievement/10) + " steps!";
        } else {
            id = extras.getInt("notificationId");
        }
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