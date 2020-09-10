package com.example.junkinsfacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private String[] hairstyles = {"Bowl Cut", "Bedhead", "Pompadour"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Populate the hairstyle spinner - from class Sep 3
        ArrayAdapter<String> hairstyleAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                hairstyles);
        Spinner hairstyleSpinner = (Spinner) findViewById(R.id.hairstyleSpinner);
        hairstyleSpinner.setAdapter(hairstyleAdapter);
    }
}