package C19305471;

public class Menu extends SophieVisual2{

    SophieVisual2 sv2;
    float x;



    public Menu (SophieVisual2 sv2)
    {
        this.sv2 = sv2;
    }

        public void render()
    {
        x = sv2.width/2 - 420;
        
        //main headings
        sv2.textSize(40);
        sv2.fill(45, 255, 255);
        sv2.text("Music Visuals Assignment by Sophie Nugent", x, 300);

        sv2.fill(100, 255, 255);
        sv2.text("C19305471", x + 300, 350);

        //music controls
        sv2.fill(150, 255, 255);
        sv2.text("Controls:", x + 330, 400);

        sv2.textSize(30);
        sv2.fill(200, 255, 255);
        sv2.text("Space Bar - Play", x + 300, 450);

        sv2.fill(20, 255, 255);
        sv2.text("P - Pause", x + 340, 500);

        sv2.fill(175, 255, 255);
        sv2.text("M - Toggle Menu", x + 290, 550);
    }
    
}
