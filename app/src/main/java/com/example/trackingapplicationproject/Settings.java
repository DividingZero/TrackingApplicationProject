package com.example.trackingapplicationproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {
    // Defining class variables and instances
    private Button ageButton, heightButton, weightButton;
    private EditText ageInput, heightInput, weightInput;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;


    // Creating the layout and assigning it to activity_settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        // Setting the buttons to instance variables
        ageButton = (Button) findViewById(R.id.ageButton);
        heightButton = (Button) findViewById(R.id.heightButton);
        weightButton = (Button) findViewById(R.id.weightButton);

        // Setting EditText widgets to instance variables
        ageInput = (EditText)findViewById(R.id.ageInput);
        heightInput = (EditText)findViewById(R.id.heightInput);
        weightInput = (EditText)findViewById(R.id.weightInput);

        // Initializing sharedpreferences with context MODE_PRIVATE
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        // Setting OnClickListener for ageButton that's point is to validate and commit the user input
        ageButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("user_age", Integer.valueOf(ageInput.getText().toString()));
                        editor.commit();
                        MainActivity.ageInput = Integer.valueOf(ageInput.getText().toString());

                    }
                });

        // Setting OnClickListener for heightButton that's point is to validate and commit the user input
        heightButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("user_height", Integer.valueOf(heightInput.getText().toString()));
                        editor.commit();
                        MainActivity.heightInput = Integer.valueOf(heightInput.getText().toString());
                    }
                });

        // Setting OnClickListener for weightButton that's point is to validate and commit the user input
        weightButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("user_weight", Integer.valueOf(weightInput.getText().toString()));
                        editor.commit();
                        MainActivity.weightInput = Integer.valueOf(weightInput.getText().toString());
                    }
                });


    }
}
