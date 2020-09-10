package com.example.junkinsfacemaker;

import java.util.Random;


//the main Face class
//contains values for the face's traits
//currently only generated with random values
public class Face {
    //instantiate Random
    Random rnd = new Random();

    //trait values
    private int skinColor;
    private int eyeColor;
    private int hairColor;
    private int hairStyle;

    //main constructor
    //creates a face with randomized traits
    public Face(){
        this.randomize();
    }

    //A method to randomize the face's values
    public void randomize(){
        this.skinColor = rnd.nextInt(256);
        this.eyeColor = rnd.nextInt(256);
        this.hairColor = rnd.nextInt(256);
        //random hairstyle is bounded by the current num of hairstyles,
        //currently hardcoded
        this.hairStyle = rnd.nextInt(3);
    }
}
