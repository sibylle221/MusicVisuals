package C19305471;

import ddf.minim.AudioBuffer;
import processing.core.PApplet;
import processing.core.PVector;

public class Cubes extends SophieVisual2
{

    SophieVisual2 sv2;
    PVector location;
    float rotX, rotY, rotZ;
    float sumRotX, sumRotY, sumRotZ;
    float speed;
    float pz, px, py;
    float c = 0;
    //Z position of "spawn" and maximum Z position
    int startingZ = -1500;
    int maxZ = 1;
    
    public Cubes(SophieVisual2 sv2)
    {
        this.sv2 = sv2;      
       
        //random location
        location = new PVector (sv2.random(-sv2.width/2, sv2.width/2),sv2.random(-sv2.height, sv2.height), sv2.random(startingZ, maxZ));
              
        pz = location.z;

        // rotX = sv2.random(0, 1);
        // rotY = sv2.random(0, 1);
        // rotZ = sv2.random(0, 1);
        
    }

    float angle = 0.5f;
    void update()
    {
        speed = sv2.getSmoothedAmplitude()*50;
        location.z += speed;
        if (location.z >= maxZ) {
            location.z = startingZ;
            location.x = sv2.random(-sv2.width/2, sv2.width/2);
            location.y = sv2.random(-sv2.height, sv2.height);
            
            pz = location.z;
          }
        
    }

    public void render()
    {
        sv2.getAudioBuffer();

        float average = 0;
        float sum = 0;
        
        // sv2.rotateY(angle);
        // sv2.rotateX(angle);
        // sv2.rotateZ(angle);
        sv2.lights();
        sv2.stroke(PApplet.map(sv2.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        sv2.strokeWeight(2);
        
        float cx = PApplet.map(location.x/location.z, 0, 1, 0, sv2.width/2);
        float cy = PApplet.map(location.y/location.z, 0, 1, 0, sv2.height);
        float cz = PApplet.map(location.z, 0, sv2.width/2, 300, 0);
        float px = PApplet.map(location.x/pz, 0, 1, 0, sv2.width/2);
        float py = PApplet.map(location.y/pz, 0, 1, 0, sv2.height);
        // sv2.noFill();
        sv2.pushMatrix();
        sv2.translate(px, py, pz);
        
        sv2.calculateFrequencyBands();
        float[] bands = sv2.getSmoothedBands();
        
        for(int i = 0 ; i < bands.length; i ++)
        {
            c = (c + 0.1f) % 255;
            sv2.fill(c + bands[i], 255, 220);
            sv2.box(50+sv2.getSmoothedAmplitude()*500);
        }
        
        pz = location.z;
        px = location.x;
        py = location.y;
        sv2.popMatrix();

    }

    
    
}
