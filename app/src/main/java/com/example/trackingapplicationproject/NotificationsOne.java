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
        CharSequence s = "Inside Notification one ";
        int id = 0;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            s = "error";
        } else {
            id = extras.getInt("notificationId");
        }
        TextView t = (TextView) findViewById(R.id.textView);
        s = s + "with id = " + id;
        t.setText(s);
        NotificationManager myNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // removes the notification with the specific id
        myNotificationManager.cancel(id);
    }
}