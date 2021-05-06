// package example;

// import ie.tudublin.Visual;
// import ie.tudublin.VisualException;
// //import processing.core.PApplet;


// public class VisualTest2 extends Visual {

//     public void settings()
//     {
//         size(800, 800, P3D);
//         println("CWD: " + System.getProperty("user.dir"));
//         //fullScreen(P3D, SPAN);
//     }

//     public void keyPressed()
//     {
//         if (key == ' ')
//         {
//             getAudioPlayer().cue(0);
//             getAudioPlayer().play();
            
//         }
        
//     }

//     public void setup()
//     {
//         colorMode(HSB);
//         noCursor();
        
//         setFrameSize(256);

//         startMinim();
//         loadAudio("heroplanet.mp3");
//         //getAp().play();
//         //startListening(); 
        
//     }

//     float smoothedBoxSize = 0;

//     public void draw()
//     {
//         surface.setResizable(true);
//         calculateAverageAmplitude();
//         background(0);
//         noFill();
//         lights();
//         stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
//         camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
//         translate(0, 0, -250);
               
//         float boxSize = 50 + (getAmplitude() * 300);//map(average, 0, 1, 100, 400); 
//         smoothedBoxSize = lerp(smoothedBoxSize, boxSize, 0.2f);        
//         rotateY(angle);
//         rotateX(angle);
//         strokeWeight(1);
//         //sphere(smoothedBoxSize/ 2);            
//         //strokeWeight(5);
//         fill(0, 1, 100, 400);
            
//         box(smoothedBoxSize);
//         angle += 0.01f;
//     }
//     float angle = 0;

   
// } 