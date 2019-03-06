package com.example.trackingapplicationproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class notifications extends AppCompatActivity {
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        list_view = (ListView) findViewById(R.id.notifications_ListViewID);

    }

    void updateUi() {

    }
}
