package ie.tudublin;


//import C19305471.SophieVisual;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new MyVisual());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();	
		//CubeVisual example = new example();
		//example.CubeVisual();	
	}
}