/**
 * FaceMaker Face Class
 * contains values for the face's traits
 * currently only generated with random values
 *
 * @version Oct 6 2020
 * @author Alex Junkins
 */
package com.example.junkinsfacemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

import java.util.Random;


//the main Face class
public class Face extends SurfaceView {
    //instantiate Random
    Random rnd = new Random();

    //trait values
    private int skinColor;
    private int eyeColor;
    private int hairColor;
    private int hairStyle;

    //paints used to make the face
    Paint skinPaint = new Paint();
    Paint hairPaint = new Paint();
    Paint eyePaint = new Paint();
    Paint detailsPaint = new Paint();

    //constants that define the dimensions of the face
    public static final float headTop = 100f;
    public static final float headLeft = 200f;
    public static final float headWidth = 700f;
    public static final float headHeight = 900f;
    public static final float eyeRadius = 60f;
    public static final float eyeSeparation = 130f;
    public static final float pupilRadius = 40f;
    public static final float mouthWidth = 200f;
    public static final float mouthHeight = 30f;
    public static final float noseWidth = 80f;
    public static final float noseHeight = 150f;

    public static final float hairWidth = 800f;
    public static final float hairHeight = 300f;



    /**
     * Main constructor
     * creates a face with randomized traits
     *
     * @param context   Inherited parameter from SurfaceView
     * @param attrs     Inherited parameter from SurfaceView
     */
    public Face(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.randomize();


        //make sure the face gets drawn
        setWillNotDraw(false);

        //palette setup
        skinPaint.setColor(skinColor);
        skinPaint.setStyle(Paint.Style.FILL);
        hairPaint.setColor(hairColor);
        hairPaint.setStyle(Paint.Style.FILL);
        eyePaint.setColor(eyeColor);
        eyePaint.setStyle(Paint.Style.FILL);
        detailsPaint.setColor(Color.BLACK);
        detailsPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * A method to reset the paint colors based on the integer color values
     */
    public void updatePaints(){
        skinPaint.setColor(skinColor);
        hairPaint.setColor(hairColor);
        eyePaint.setColor(eyeColor);
    }


    /**
     * Randomize the face's values
     */
    public void randomize(){
        //picks random OPAQUE colors
        skinColor = rnd.nextInt(0xffffff + 1) + 0xff000000;
        skinPaint.setColor(skinColor);
        eyeColor = rnd.nextInt(0xffffff + 1) + 0xff000000;
        eyePaint.setColor(eyeColor);
        hairColor = rnd.nextInt(0xffffff + 1) + 0xff000000;
        hairPaint.setColor(hairColor);
        //random hairstyle is bounded by the current num of hairstyles,
        //currently hardcoded
        hairStyle = rnd.nextInt(3);
    }

    /**
     * A helper method to draw the eyes and pupils of the face
     *
     * @param canvas    the canvas object the face is being drawn on
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawEyes(Canvas canvas){
        //draw the eye colors
        canvas.drawOval(
                headLeft + .5f*headWidth - .5f*eyeSeparation - 2*eyeRadius,
                headTop + .3f * headHeight,
                headLeft + .5f*headWidth - .5f*eyeSeparation,
                headTop + .3f * headHeight + 2*eyeRadius,
                eyePaint);
        canvas.drawOval(headLeft + .5f*headWidth + .5f*eyeSeparation,
                headTop + .3f * headHeight,
                headLeft + .5f*headWidth + .5f*eyeSeparation + 2*eyeRadius,
                headTop + .3f * headHeight + 2*eyeRadius,
                eyePaint);

        //draw the pupils
        canvas.drawOval(
                headLeft + .5f*headWidth - .5f*eyeSeparation  - eyeRadius
                        - pupilRadius - 5f,
                headTop + .3f * headHeight + eyeRadius - pupilRadius,
                headLeft + .5f*headWidth - .5f*eyeSeparation - eyeRadius
                        + pupilRadius - 5f,
                headTop + .3f * headHeight + 2*eyeRadius - eyeRadius
                        + pupilRadius,
                detailsPaint);
        canvas.drawOval(
                headLeft + .5f*headWidth + .5f*eyeSeparation  + eyeRadius
                        - pupilRadius + 5f,
                headTop + .3f * headHeight + eyeRadius - pupilRadius,
                headLeft + .5f*headWidth + .5f*eyeSeparation + eyeRadius
                        + pupilRadius + 5f,
                headTop + .3f * headHeight + 2*eyeRadius - eyeRadius
                        + pupilRadius,
                detailsPaint);
    }

    /**
     * Draws the face
     * contains code for the face's visual layout
     *
     * @param canvas    the canvas object the face will be drawn on
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDraw(Canvas canvas)
    {
        //if hairstyle 1, draw hair below  head
        if (hairStyle == 0){
            drawHatHair(canvas);
        }

        //first the head underneath everything
        canvas.drawOval(headLeft, headTop, headLeft + headWidth,
                headTop + headHeight, skinPaint);

        //draw the eyes
        drawEyes(canvas);

        //draw the nose
        canvas.drawRect(headLeft + .5f*headWidth - .5f*noseWidth,
                headTop + .5f*headHeight - .2f*noseHeight,
                headLeft + .5f*headWidth + .5f*noseWidth,
                headTop + .5f*headHeight + .8f*noseHeight, detailsPaint);

        //draw the mouth
        canvas.drawRect(headLeft + .5f*headWidth - .5f*mouthWidth,
                headTop + .75f*headHeight - .2f*mouthHeight,
                headLeft + .5f*headWidth + .5f*mouthWidth,
                headTop + .75f*headHeight + .8f*mouthHeight, detailsPaint);

        //draw hairstyle 2 and 3
        if (hairStyle == 1){
            drawAfro(canvas);
        }
        else if (hairStyle == 2){
            drawPomp(canvas);
        }

    }//onDraw

    /**
     * A method to draw hat-hair on the given canvas
     * @param canvas    the canvas
     */
    public void drawHatHair(Canvas canvas){
        canvas.drawRect(headLeft + .5f*headWidth - .5f*hairWidth,
                headTop - .2f*hairHeight,
                headLeft + .5f*headWidth + .5f*hairWidth,
                headTop + .8f*hairHeight, hairPaint);
    }

    /**
     * A method to draw an afro on the given canvas
     * @param canvas    the canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawAfro(Canvas canvas){
        canvas.drawOval(headLeft + .5f*headWidth - .5f*hairWidth,
                headTop - .2f*hairHeight,
                headLeft + .5f*headWidth + .5f*hairWidth,
                headTop + hairHeight + 20f, hairPaint);
    }

    /**
     * A method to draw geometric pompadour on the given canvas
     * @param canvas    the canvas
     */
    public void drawPomp(Canvas canvas){
        canvas.drawRect(headLeft + .5f*headWidth - .6f*hairWidth,
                headTop - .2f*hairHeight,
                headLeft + .5f*headWidth + .3f*hairWidth,
                headTop + .95f*hairHeight, hairPaint);
    }


    /**
     * A getter for eyeColor
     * @return eye color
     */
    public int getEyeColor() {
        return eyeColor;
    }

    /**
     * A setter for eyeColor
     * @param newColor  an ARGB hexadecimal color
     */
    public void setEyeColor(int newColor){
        eyeColor = newColor;
    }

    /**
     * A getter for hairColor
     * @return hair color
     */
    public int getHairColor() {
        return hairColor;
    }

    /**
     * A setter for hairColor
     * @param newColor  an ARGB hexadecimal color
     */
    public void setHairColor(int newColor){
        hairColor = newColor;
    }

    /**
     * A getter for skinColor
     * @return skin color
     */
    public int getSkinColor() {
        return skinColor;
    }

    /**
     * A setter for skinColor
     * @param newColor  an ARGB hexadecimal color
     */
    public void setSkinColor(int newColor){
        skinColor = newColor;
    }



    /**
     * A getter for hairStyle
     * @return hair style
     */
    public int getHairStyle() {
        return hairStyle;
    }

    /**
     * A setter for hairStyle
     * @param   integer 0-2
     *          0: Ultimate hat-hair, 1: Afro, 2: Geometric Pompadour
     */
    public void setHairStyle(int newStyle){
        if (newStyle < 0 || newStyle > 2){
            return;
        }
        hairStyle = newStyle;
    }
}//class Face
