/**
 * FaceMaker Main Activity Class
 *
 * @version sep 10 2020
 * @author Alex Junkins
 */

package com.example.junkinsfacemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private String[] hairstyles =
            {"Ultimate Hat-hair", "Afro", "Geometric Pompadour"};


    /**
     * Initializes the app's values when the class is created
     * @param savedInstanceState inherited parameter from overridden onCreate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * External Citation
         * Date: Sep 3, 2020
         * problem: Needed to populate a spinner
         * Resource: CS301 class notes from Sep 3, 2020
         * Solution: Used example code from this post
         */
        //populate the hairstyle spinner
        ArrayAdapter<String> hairstyleAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                hairstyles);
        Spinner hairstyleSpinner = (Spinner) findViewById(R.id.hairstyleSpinner);
        hairstyleSpinner.setAdapter(hairstyleAdapter);

        Face mainFace = findViewById(R.id.faceView);

        //get references to complex UI elements and use to create faceController
        RadioButton hairButton = findViewById(R.id.hairButton);
        RadioButton eyesButton = findViewById(R.id.eyesButton);
        RadioButton skinButton = findViewById(R.id.skinButton);
        SeekBar redSeekBar = findViewById(R.id.redSeekBar);
        SeekBar greenSeekBar = findViewById(R.id.greenSeekBar);
        SeekBar blueSeekBar = findViewById(R.id.blueSeekBar);
        Spinner hairSpinner = findViewById(R.id.hairstyleSpinner);
        FaceController faceController = new FaceController(
                mainFace, hairSpinner,
                hairButton, skinButton, eyesButton,
                redSeekBar, greenSeekBar, blueSeekBar );

        //assign complex UI listeners
        hairButton.setOnClickListener(faceController);
        skinButton.setOnClickListener(faceController);
        eyesButton.setOnClickListener(faceController);
        redSeekBar.setOnSeekBarChangeListener(faceController);
        greenSeekBar.setOnSeekBarChangeListener(faceController);
        blueSeekBar.setOnSeekBarChangeListener(faceController);
        hairSpinner.setOnItemSelectedListener(faceController);

        //random button is not required for controller init.
        Button randomButton = findViewById(R.id.randomFaceButton);
        randomButton.setOnClickListener(faceController);

        //set the hair spinner to the correct value
        hairSpinner.setSelection(mainFace.getHairStyle());
    }
}