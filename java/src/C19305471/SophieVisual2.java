package C19305471;

import ie.tudublin.*;

public class SophieVisual2 extends Visual{
    
    //classes
    Cubes[] no_Cubes = new Cubes[50];
    Menu menu;
    Boolean toggle = true;
    
    //settings
    public void settings()
    {
        size(1200, 800, P3D);
        
    }

    //space button to play and p key to pause
    public void keyPressed()
    {
        if (key == 'm')
        {
            toggle = !toggle;
        }
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            
            
        }
        if (key == 'p')
        {
            getAudioPlayer().pause();
        }

    }

    //setup
    public void setup()
    {
        colorMode(HSB);//Hue, Saturation, Brightness
        surface.setResizable(true);//so it displays on mac
        setFrameSize(256);
        startMinim();
            
        // Call loadAudio to load an audio file to process 
        loadAudio("I_LIKE_CARS.mp3");   //a banger
        
        menu = new Menu(this);
        //establishing an instance of the class
        for (int i = 0; i < no_Cubes.length; i++)
        {
            no_Cubes[i] = new Cubes(this);
        } 
                      
    }

    //draw function
    public void draw()
    {
        //background set to black
        background(0);
        
        try
        {
            // Call this if you want to use FFT data
            calculateFFT(); 
        }
        catch(VisualException e)
        {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands(); 
        // Call this is you want to get the average amplitude
        calculateAverageAmplitude();
        getAudioBuffer();
        getSmoothedBands();
        
        

        //toggles menu
        if(toggle)
        {
            menu.render();
        }
        //makes cubes appear on screen
        translate(width/2, height/2);
        
        //cubes
        for (int i = 0; i < no_Cubes.length; i++)
        {
            no_Cubes[i].update();
            no_Cubes[i].render();
        }  
          
        
    }

}
