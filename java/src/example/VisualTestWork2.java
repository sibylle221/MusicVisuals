package example;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
//import processing.core.PApplet;


public class VisualTestWork2 extends Visual {

    
    // variables for zones of spectrum
    float specLow = 0.03f; // 3%
    float specMid = 0.125f; // 12.5%
    float specHi = 0.20f; // 20%

    // This leaves 64% of the possible spectrum that will not be used. These values ​​are generally too high for the human ear anyway.

    // Score values ​​for each zone
    float scoreLow = 0f;
    float scoreMid = 0f;
    float scoreHi = 0f;

    // Previous values, to soften the reduction
    float oldScoreLow = scoreLow;
    float oldScoreMid = scoreMid;
    float oldScoreHi = scoreHi;

    // Softening value
    float scoreDecreaseRate = 25f;

    // Lines that appear on the sides
    int nbWalls = 3000; //500
    Wall[] walls;

    int nbCubes;
    Cube[] cubes;

    float smooth_factor = 0.5f;

    public void settings() {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        // fullScreen(P3D, SPAN);
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            
        }

    }

    public void setup()
    {
        surface.setResizable(true);
        colorMode(HSB);
        //getFFT();
        setFrameSize(256);
        
        
        startMinim();
        
        loadAudio("heroplanet.mp3");
        
        keyPressed();
        //getAudioPlayer().play();
        // Create the FFT object to analyze the song
        //fft = new FFT(song.bufferSize(), song.sampleRate());

        
        //startListening(); 
        //as many walls as we want
        walls = new Wall[nbWalls];
        for (int i = 0; i < nbWalls; i+=4) {
            walls[i] = new Wall(0, height/2, 10, height);
          }

        //right walls
        for (int i = 1; i < nbWalls; i+=4) {
            walls[i] = new Wall(width, height/2, 10, height);
        }

        //bottom walls
        for (int i = 2; i < nbWalls; i+=4) {
            walls[i] = new Wall(width/2, height, width, 10);
         }

        //top walls
        for (int i = 3; i < nbWalls; i+=4) {
            walls[i] = new Wall(width/2, 0, width, 10);
        }

        nbCubes = (int)(fft.specSize()*specHi);
        cubes = new Cube[nbCubes];
        for (int i = 0; i < nbCubes; i++) {
            cubes[i] = new Cube(); 
           }
        

    }

    public void draw() {

        
        surface.setResizable(true);
        background(255);
        calculateAverageAmplitude();
        try
        {
            calculateFFT();
        }
        catch(VisualException e)
        {
            e.printStackTrace();
        }
        calculateFrequencyBands();
        noFill();
        lights();
        stroke(255);
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        translate(0, 0, -250);
        //  Advance the song. We draw () for each "frame" of the song
        //fft.forward(ab);

        // Calculation of the "scores" (power) for three categories of sound
        // First, save the old values
        oldScoreLow = scoreLow;
        oldScoreMid = scoreMid;
        oldScoreHi = scoreHi;

        // reintialise values
        scoreLow = 0;
        scoreMid = 0;
        scoreHi = 0;

        // Calculate new "scores"
        for (int i = 0; i < fft.specSize() * specLow; i++) {
            scoreLow += fft.getBand(i);
        }

        for (int i = (int) (fft.specSize() * specLow); i < fft.specSize() * specMid; i++) {
            scoreMid += fft.getBand(i);
        }

        for (int i = (int) (fft.specSize() * specMid); i < fft.specSize() * specHi; i++) {
            scoreHi += fft.getBand(i);
        }

        // Slow down the descent.
        if (oldScoreLow > scoreLow) {
            scoreLow = oldScoreLow - scoreDecreaseRate;
        }

        if (oldScoreMid > scoreMid) {
            scoreMid = oldScoreMid - scoreDecreaseRate;
        }

        if (oldScoreHi > scoreHi) {
            scoreHi = oldScoreHi - scoreDecreaseRate;
        }

        // Volume for all frequencies at this time, with higher sounds more prominent.
        // This allows the animation to go faster for higher pitched sounds, which are more noticeable
        float scoreGlobal = 0.66f * scoreLow + 0.8f * scoreMid + 1 * scoreHi;

        // subtle canvas background color
        //background(scoreLow / 100, scoreMid / 100, scoreHi / 100);
        

        for(int i = 0; i < nbCubes; i++)
         {
             //Frequency band value
             float bandValue = fft.getBand(i);
    
             //The color is represented as follows: red for bass, green for mid sounds, and blue for highs.
             //The opacity is determined by the volume of the tape and the overall volume.
             cubes[i].display(scoreLow, scoreMid, scoreHi, bandValue, scoreGlobal);
         }

        // Line walls, here you have to keep the value of the previous strip and the next one to connect them together
        float previousBandValue = fft.getBand(0);

        // Distance between each line point, negative because on dimension z
        float dist = -25;

        float heightMult = 2;

        // For each band
        for (int i = 1; i < fft.specSize(); i++) {
            // The value of the frequency band, the farther bands are multiplied so that
            // they are more visible.
            float bandValue = fft.getBand(i) * (1 + (i / 10));

            // Selection of the color according to the strengths of the different types of sounds
            stroke(100 + scoreLow, 100 + scoreMid, 100 + scoreHi, 155 - i); //255
            
            strokeWeight(1 + (scoreGlobal / 100));

            // diagonal line, left, lower

            //// upper
            line(0, height - (previousBandValue * heightMult), dist * (i - 1), 0, height - (bandValue * heightMult), dist * i);
            // lower
            line((previousBandValue * heightMult), height, dist * (i - 1), (bandValue * heightMult), height, dist * i);
            // central
            line(0, height - (previousBandValue * heightMult), dist * (i - 1), (bandValue * heightMult), height, dist * i);

            // diagonal line, left, higher
            line(0, (previousBandValue * heightMult), dist * (i - 1), 0, (bandValue * heightMult), dist * i);
            line((previousBandValue * heightMult), 0, dist * (i - 1), (bandValue * heightMult), 0, dist * i);
            line(0, (previousBandValue * heightMult), dist * (i - 1), (bandValue * heightMult), 0, dist * i);

            // diagonal line, right, lower
            line(width, height - (previousBandValue * heightMult), dist * (i - 1), width, height - (bandValue * heightMult), dist * i);
            line(width - (previousBandValue * heightMult), height, dist * (i - 1), width - (bandValue * heightMult), height, dist * i);
            line(width, height - (previousBandValue * heightMult), dist * (i - 1), width - (bandValue * heightMult), height, dist * i);

            // diagonal line, left, higher
            line(width, (previousBandValue * heightMult), dist * (i - 1), width, (bandValue * heightMult), dist * i);
            line(width - (previousBandValue * heightMult), 0, dist * (i - 1), width - (bandValue * heightMult), 0, dist * i);
            line(width, (previousBandValue * heightMult), dist * (i - 1), width - (bandValue * heightMult), 0, dist * i);

            previousBandValue = bandValue;
        }

        // Walls rectangles
        for (int i = 0; i < nbWalls; i++) {
            // Each wall is assigned a band, and its amplitude is sent to it.
            float intensity = fft.getBand(i % ((int) (fft.specSize() * specHi)));
            walls[i].display(scoreLow, scoreMid, scoreHi, intensity, scoreGlobal);
        }
    }

    // Class to display the lines on the sides
    class Wall {

        
        // Minimum and maximum position Z
        float startingZ = -25000; //-10000
        float maxZ = 5000; //50

        // postion values
        float x, y, z;
        float sizeX, sizeY;

        // Constructor
        Wall(float x, float y, float sizeX, float sizeY) {
            // Make the line appear at the specified location
            this.x = x;
            this.y = y;
            // random depth
            this.z = random(startingZ, maxZ);

            // We determine the size because the walls to the floors have a different size than those on the sides
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        // ======= WALL COLORS ==========
        void display(float scoreLow, float scoreMid, float scoreHi, float intensity, float scoreGlobal) {
            // Color determined by low, medium and high sounds
            // Opacity determined by the overall volume
            float displayColor = color(scoreLow * 0.67f, scoreMid * 0.67f, scoreHi * 0.67f, scoreGlobal);

            // Make the lines disappear in the distance to give an illusion of fog
            fill(displayColor, ((scoreGlobal - 5) / 1000) * (255 + (z / 25)));
            //fill(displayColor); //more aggressive lines on walls
            
            noStroke();

            pushMatrix();

            //displacement
            translate(x, y, z);

            //Enlargement
            if (intensity > 100)
            {
                intensity = 100;
            }
            scale(sizeX * (intensity / 100), sizeY * (intensity / 100), 20);

            // box
            box(1);
            popMatrix();

            // Second strip, the one that is always the same size
            displayColor = color(scoreLow * 0.5f, scoreMid * 0.5f, scoreHi * 0.5f, scoreGlobal);
            fill(displayColor, (scoreGlobal / 5000) * (255 + (z / 25)));
            //Transformation matrix
            pushMatrix();

            // displacement
            translate(x, y, z);

            // Enlargement
            scale(sizeX, sizeY, 10);

            // creation of the box
            box(1);
            popMatrix();

            //Z displacement
            z += (pow((scoreGlobal / 150), 2));
            if (z >= maxZ) {
                z = startingZ;
            }

            
        }
    }

    //Class for cubes that float in space
class Cube {

    float c = 0;

    
    //Z position of "spawn" and maximum Z position
    float startingZ = -10000;
    float maxZ = 1000;
    
    //Position values
    float x, y, z;
    float rotX, rotY, rotZ;
    float sumRotX, sumRotY, sumRotZ;
    
    //Constructor
    Cube() {
      //random location
      x = random(0, width);
      y = random(0, height);
      z = random(startingZ, maxZ);
      
      //random rotation
      rotX = random(0, 1);
      rotY = random(0, 1);
      rotZ = random(0, 1);
    }
    
    void display(float scoreLow, float scoreMid, float scoreHi, float intensity, float scoreGlobal) {

        //colorMode(HSB);
      //Color lines, they disappear with the individual intensity of the cube
      float displayColor = color(scoreLow*0.67f, scoreMid*0.67f, scoreHi*0.67f, intensity*5f);
      //fill(displayColor, 255);
      c = (c + 1f) % 255;
      fill(c + intensity*5, 255, 220);
      
      
      //Color lines, they disappear with the individual intensity of the cube
      float strokeColor = color(255f, 150-(20*intensity));
      //cubes.stroke(PApplet.map(i, 0, bands.length, 0, 255), 255, 255);
      stroke(strokeColor);
      strokeWeight(1 + (scoreGlobal/300));
      
      //strokeColor.stroke(PApplet.map(i, 0, bands.length, 0, 255), 255, 255);
    //   int color;

    //   color = cube.color(cube.random(1, 255), cube.random(1, 255), cube.random(1, 255));
  

      //Creation of a transformation matrix to perform rotations, enlargements
      pushMatrix();
      
      //displacement
      translate(x, y, z);
      
      //Calculation of rotation as a function of intensity for the cube
      sumRotX += intensity*(rotX/1000);
      sumRotY += intensity*(rotY/1000);
      sumRotZ += intensity*(rotZ/1000);
      
      //Apply rotation
      rotateX(sumRotX);
      rotateY(sumRotY);
      rotateZ(sumRotZ);
      
      //Creation of the box, variable size according to the intensity for the cube
      box(100+(intensity/2));
      
      //Apply Matrix
      popMatrix();
      
      //z displacement
      z+= (1+(intensity/5)+(pow((scoreGlobal/150), 2)));
      
      //Replace the box at the back when it is no longer visible
      if (z >= maxZ) {
        x = random(0, width);
        y = random(0, height);
        z = startingZ;
      }
    }
    
  }

}
