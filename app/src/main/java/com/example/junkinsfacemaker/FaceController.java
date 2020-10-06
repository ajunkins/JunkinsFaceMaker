/**
 * FaceMaker FaceController Class
 * contains listeners and event methods for interactions between the UI
 * and the Face class
 *
 * @version Oct 6 2020
 * @author Alex Junkins
 */

package com.example.junkinsfacemaker;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;

//for keeping track of which feature has been selected
enum Feature { HAIR, EYES, SKIN; }

public class FaceController implements OnClickListener,
        AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener{
    private Face faceReference;
    private RadioButton hairButtonRef;
    private RadioButton skinButtonRef;
    private RadioButton eyeButtonRef;
    private SeekBar rBar;
    private SeekBar bBar;
    private SeekBar gBar;
    private Spinner hairSpinner;
    private Feature selectedFeature;

    /**
     * Main constructor
     * Creates a faceController attached to a Face
     *
     * @param ref   the reference Face
     */
    public FaceController (Face ref, Spinner hs,
                           RadioButton hbr, RadioButton sbr, RadioButton ebr,
                           SeekBar rb, SeekBar gb, SeekBar bb){
        selectedFeature = Feature.HAIR; //hair is selected by default.
        //assign references and update
        this.faceReference = ref;
        this.hairSpinner = hs;
        this.hairButtonRef = hbr;
        this.skinButtonRef = sbr;
        this.eyeButtonRef = ebr;
        this.rBar = rb;
        this.gBar = gb;
        this.bBar = bb;
        hairButtonRef.setChecked(true);
        updateSeekBars();
    }

    /**
     * Button onClick
     * determines behavior when 'random face' button is clicked:
     * randomizes the referenced face's features
     * determines behavior when radio buttons are clicked:
     * deactivates other radio buttons and shows color vals
     *
     * @param view the clicked view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.randomFaceButton:
                faceReference.randomize();
                faceReference.invalidate(); //redraw
                //correct spinner and seek bars
                hairSpinner.setSelection(faceReference.getHairStyle());
                updateSeekBars();
                break;
            case R.id.hairButton:
                selectedFeature = Feature.HAIR;
                updateSeekBars();
                skinButtonRef.setChecked(false);
                eyeButtonRef.setChecked(false);
                break;
            case R.id.skinButton:
                selectedFeature = Feature.SKIN;
                updateSeekBars();
                hairButtonRef.setChecked(false);
                eyeButtonRef.setChecked(false);
                break;
            case R.id.eyesButton:
                selectedFeature = Feature.EYES;
                updateSeekBars();
                hairButtonRef.setChecked(false);
                skinButtonRef.setChecked(false);
                break;
            default: //this should never happen. If it does, do nothing
                break;

        }
    }

    /**
     * A method to set the current values  of the RGB seek bars to match the
     * color values of the current selected component
     */
    public void updateSeekBars(){
        int currentColor = 0x00000000;
        switch(selectedFeature){
            case HAIR:
                currentColor = faceReference.getHairColor();
                break;
            case SKIN:
                currentColor = faceReference.getSkinColor();
                break;
            case EYES:
                currentColor = faceReference.getEyeColor();
                break;
            default://this can't happen
                break;
        }

        /**
         * External Citation
         * Date: Oct 6, 2020
         * problem: Needed to covert a hex color to three rgb integers
         * Resource: stackoverflow
         * https://stackoverflow.com/questions/2534116/how-to-convert-getrgbx-
         * y-integer-pixel-to-colorr-g-b-a-in-java
         * Solution: Used bit-shifting and masking to isolate color components
         */
        setRedBar((currentColor>>16)&0xFF);
        setGreenBar((currentColor>>8)&0xFF);
        setBlueBar((currentColor)&0xFF);
    }

    /**
     * a method to set the red seekbar to a given value
     * must be between 0-255
     * @param num   number the seekbar should be set to
     */
    private void setRedBar(int num){
        if (num < 0 || num > 256) {
            return;
        }
        rBar.setProgress(num);
    }

    /**
     * a method to set the red seekbar to a given value
     * must be between 0-255
     * @param num   number the seekbar should be set to
     */
    private void setGreenBar(int num){
        if (num < 0 || num > 256) {
            return;
        }
        gBar.setProgress(num);
    }

    /**
     * a method to set the red seekbar to a given value
     * must be between 0-255
     * @param num   number the seekbar should be set to
     */
    private void setBlueBar(int num){
        if (num < 0 || num > 256) {
            return;
        }
        bBar.setProgress(num);
    }

    /**
     * Spinner listener
     * A method to set a new hairstyle picked from the spinner
     *
     * @param parent    the spinner
     * @param view      default view
     * @param pos       position
     * @param id        id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selectedStyle = (String)parent.getItemAtPosition(pos);
        Log.v("style", selectedStyle);
        switch (selectedStyle){
            case "Ultimate Hat-hair":
                faceReference.setHairStyle(0);
                break;
            case "Afro":
                faceReference.setHairStyle(1);
                break;
            case "Geometric Pompadour":
                faceReference.setHairStyle(2);
                break;
            default:
                //this should never happen. If it does, do nothing.
                break;
        }
        faceReference.invalidate();
    }

    /**
     * Spinner listener: no selection
     * A method of what to do if no new hairstyle is selected in the spinner:
     * no change
     *
     * @param adapterView
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { return; }

    /**
     * A method to respond when an RGB seek bar is changed
     *
     * @param seekBar   a reference to the seek bar
     * @param progress  the value the seek bar was changed to
     * @param fromUser  whether it was changed by the user
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        //do nothing if the user did not manually change this seek bar
        if (!fromUser) { return; }

        //get the current color of the selected feature
        int featureColor = getCurrentFeatureColor();

        //determine which seek bar changed and bit shift the hex
        //value accordingly, then splice it into the color
        int newColorComp = progress;
        int newColor = featureColor;
        switch(seekBar.getId()){
            case R.id.redSeekBar:
                newColorComp = newColorComp<<16;
                newColor = newColor&0x00FFFF;
                break;
            case R.id.greenSeekBar:
                newColorComp = newColorComp<<8;
                newColor = newColor&0xFF00FF;
                break;
            case R.id.blueSeekBar:
                newColor = newColor&0xFFFF00;
                break;
            default:
                //if any other id is provided, exit method.
                return;
        }

        //update the feature's color
        newColor = newColor + newColorComp;
        updateCurrentFeatureColor(newColor + 0xff000000);
        faceReference.updatePaints();
        faceReference.invalidate();

    }

    /**
     * a helper method to get the color of the currently selected feature
     * @return  the current color value of the selected feature
     */
    private int getCurrentFeatureColor(){
        switch(selectedFeature){
            case HAIR:
                return faceReference.getHairColor();
            case SKIN:
                return faceReference.getSkinColor();
            case EYES:
                return faceReference.getEyeColor();
            default: //this cannot happen
                return -1;
        }
    }

    /**
     * a helper method to get the color of the currently selected feature
     * @return  the current color value of the selected feature
     */
    private void updateCurrentFeatureColor(int newColor){
        switch(selectedFeature){
            case HAIR:
                faceReference.setHairColor(newColor);
                break;
            case SKIN:
                faceReference.setSkinColor(newColor);
                break;
            case EYES:
                faceReference.setEyeColor(newColor);
                break;
            default: //this cannot happen
                return;
        }
    }

    /**
     * An unused but required method to respond to a user starting to touch
     * the seekbar
     * @param seekBar   a reference to the seek bar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    /**
     * An unused but required method to respond to a user ceasing to touch
     * the seekbar
     * @param seekBar   a reference to the seek bar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}
