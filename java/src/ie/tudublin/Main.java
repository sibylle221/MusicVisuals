package ie.tudublin;


//import C19305471.*;
import example.SophieVisual;
import example.CubeVisual;
//import example.MyVisual;
import example.RotatingAudioBands;
import example.CubeVisual1;
import example.VisualTestWork;
import example.*;


public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new VisualTestWork2());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();	
	}
}
