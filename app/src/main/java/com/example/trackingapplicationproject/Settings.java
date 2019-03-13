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

    private Button ageButton, heightButton, weightButton;
    private EditText ageInput, heightInput, weightInput;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        ageButton = (Button) findViewById(R.id.ageButton);
        heightButton = (Button) findViewById(R.id.heightButton);
        weightButton = (Button) findViewById(R.id.weightButton);

        ageInput = (EditText)findViewById(R.id.ageInput);
        heightInput = (EditText)findViewById(R.id.heightInput);
        weightInput = (EditText)findViewById(R.id.weightInput);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

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
