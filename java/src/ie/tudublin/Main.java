package ie.tudublin;

import C19305471.SophieVisual2;
import example.VisualTest;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new SophieVisual2());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();	
	}
}
