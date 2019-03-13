
package com.example.trackingapplicationproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Saver {

    // Initializing class variables and instances
    private Date today = Calendar.getInstance().getTime();
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
    private String date = formatter.format(today);
    private SimpleDateFormat formatter_1 = new SimpleDateFormat("MM");
    private String month = formatter_1.format(today);

    private SharedPreferences prefs;


    // Creating constructor for Saver
    public Saver(SharedPreferences prefs) {
        this.prefs = prefs;
    }


    // The function of the monthlySave method is to save the values to prefs monthly
    public void monthlySave(float save) {
        float saved = save+prefs.getFloat(month,0);
        prefs.edit().putFloat(month, saved);
        prefs.edit().apply();
    }

    // The function of the dailySave method is to save the values to prefs daily
    public void dailySave(float save) {
        float saved = save+prefs.getFloat(date,0);
        prefs.edit().putFloat(date, saved);
        prefs.edit().apply();
    }

    // The function of the dailyGet method is to return daily prefs from the system
    public float dailyGet(){
        return prefs.getFloat(date,0);
    }

    // The function of the monthlyGet method is to return the monthly prefs from the system
    public float monthlyGet(){
        return prefs.getFloat(month,0);
    }

    // The Exists method is used to check whether the object contains the float "date"
    public Boolean Exists() {
        return prefs.contains(date);
    }
}

