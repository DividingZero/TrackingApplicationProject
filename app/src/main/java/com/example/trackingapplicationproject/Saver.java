
package com.example.trackingapplicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Saver {
    private Date today = Calendar.getInstance().getTime();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
    private String date = formatter.format(today);
    private SimpleDateFormat formatter_1 = new SimpleDateFormat("MM");
    private String month = formatter_1.format(today);

    private SharedPreferences prefs;

    public Saver(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void monthlySave(float save) {
        float saved = save+prefs.getFloat(month,0);
        prefs.edit().putFloat(month, saved);
        prefs.edit().apply();
    }
    public void dailySave(float save) {
        float saved = save+prefs.getFloat(date,0);
        prefs.edit().putFloat(date, saved);
        prefs.edit().apply();
    }
    public float dailyGet(){
        return prefs.getFloat(date,0);
    }
    public float monthlyGet(){
        return prefs.getFloat(month,0);
    }

    public Boolean Exists() {
        return prefs.contains(date);
    }
}

