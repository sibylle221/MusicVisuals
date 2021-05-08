package C19305471;

import processing.core.PApplet;
import processing.core.PVector;

public class Cubes extends SophieVisual2
{
    //linking sv2 and initialising everything
    SophieVisual2 sv2;
    PVector location; //PVector for location in place of usual xyz
    float speed;
    float pz, px, py; //keeps consistent x, y, z
    float c = 0;//for the colours in fill further down
    //Z position of "spawn" and maximum Z position
    int startingZ = -1500;
    int maxZ = 1;
    
    //function for class
    public Cubes(SophieVisual2 sv2)
    {
        //sv2 in front of everything from other classes
        this.sv2 = sv2;      
       
        //location for random x,y,z values withing range to see them
        location = new PVector (sv2.random(-sv2.width/2, sv2.width/2),
        sv2.random(-sv2.height, sv2.height), sv2.random(startingZ, maxZ));
              
        pz = location.z;
        
    }

    //update cube
    void update()
    {
        speed = sv2.getSmoothedAmplitude()*50;//speed for cubes moving towards camera
        location.z += speed;//changing z (depth)
        //respawning cubes when they go out of range
        if (location.z >= maxZ) {
            location.z = startingZ;
            location.x = sv2.random(-sv2.width/2, sv2.width/2);
            location.y = sv2.random(-sv2.height, sv2.height);
            
            pz = location.z;
          }
        
    }
    //render call to "draw" stuff
    public void render()
    {
        sv2.getAudioBuffer();

        sv2.lights();
        //stroke for cubes, lines
        sv2.stroke(PApplet.map(sv2.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        sv2.strokeWeight(2);

        //keeping positions for x and y consistent 
        float px = PApplet.map(location.x/pz, 0, 1, 0, sv2.width/2);
        float py = PApplet.map(location.y/pz, 0, 1, 0, sv2.height);
        
        //push matrix :)
        sv2.pushMatrix();

        //translate 
        sv2.translate(px, py, pz);
        
        sv2.calculateFrequencyBands();
        float[] bands = sv2.getSmoothedBands();
        //colouring cubes rainbow with the amplitude affecting the size
        //and frequency bands affecting colouring
        for(int i = 0 ; i < bands.length; i ++)
        {
            c = (c + 0.1f) % 255;
            sv2.fill(c + bands[i], 255, 220);
            sv2.box(50+sv2.getSmoothedAmplitude()*500);
        }
        
        //PVectors are changed so this keeps consistency
        pz = location.z;
        px = location.x;
        py = location.y;
        //popmatrix :)
        sv2.popMatrix();

    }
    
}
