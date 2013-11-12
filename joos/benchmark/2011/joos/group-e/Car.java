import joos.lib.*;
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.applet.Applet;
import joos.lib.*;

public class Car extends Applet {

protected JoosIO f;
protected int r;
   public Car() { super(); }

	public void paint( Graphics g )
	{
		int x;
		int i;
		int a;
		int y;
		int z;
		int rand;
		Color c1;
		Color c2;
		Color c3;
		Color c4;
		JoosThread t;
		JoosConstants c;
		Font font1;
		String messages;
		c = new JoosConstants();
      		// create a font object: 12-point bold times roman
      		font1 = new Font( "TimesRoman", c.BOLD(), 12 );
     		t = new JoosThread(null);
		i=0;
		x=10;
		y=10;
		z=1;
		c1=new Color(255,255,255);
		c2=new Color(0,0,0);
		c3=new Color(0,0,255);
		c4=new Color(255,0,0);

		this.drawFinishLine(g,c1);

		while(i<10)
		{
			this.drawCar(c3,c2,x,25,g);
			this.drawCar(c4,c2,y,110,g);
			i = i+1;
			t.sleep(200);
			g.clearRect(x,15,60,40);
			g.clearRect(y,100,60,40);
			x=x+50;
			y=y+20;
		
		}

                r = new Random().nextInt(10); 
                if (r > 0)
                        rand = r % 10;
                else
                        rand = (-r) % 10;

                if(rand==0) messages=new String("Why am I stuck here???");
                else if(rand==1) messages=new String("Fuel in Reserve..");
                else if(rand==2) messages=new String("Ooops...Police caught..");
                else if(rand==3) messages=new String("Engine jam,.. I hate winters..");
                else if(rand==4) messages=new String("Tyre Punctured!!");
                else if(rand==5) messages=new String("Over speeding. Engine Choked..");
                else if(rand==6) messages=new String("Bad luck, My clutch wire is cut..");
                else if(rand==7) messages=new String("Can you believe,a dent on road!!!!");
                else if(rand==8) messages=new String("Had I not fueled the car!!?");
                else if(rand==9) messages=new String("Is it not my lucky date!!");
                else messages=new String("Fuel Gone");

		this.drawCar(c3,c2,x,25,g);
		g.setFont(font1);
		g.drawString(messages,x,60);
		i=0;
		while(i<15)
		{
			this.drawCar(c4,c2,y,110,g);
			i = i+1;
			t.sleep(200);
			g.clearRect(y,100,60,40);
			y=y+50;
		}
		this.drawCar(c4,c2,y,110,g);
		g.drawString("WE WIN... Hurray!! :D",y,140);

		g.setColor(c3);
		font1 = new Font( "TimesRoman", c.BOLD(), 20 );
		g.setFont(font1);
		g.drawString("THIS DID NOT SEEM TO BE YOUR LUCKY NUMBER OF THE DAY...",100,300);
		g.drawString("TRY AGAIN BY CLOSING THE BROWSER AND RE-OPENING THE .HTML FILE... ",80,375);

	}

public void drawFinishLine(Graphics g,Color c1)
{
                int i;
		Color c;
		c=new Color(0,255,0);
		i=0;

		g.fillRect(1,1,1000,4);
		g.fillRect(1,150,1000,4);
		g.fillRect(1,75,1000,5);

		g.fillRect(950,5,10,10);
                g.fillRect(940,15,10,10);
                g.fillRect(950,25,10,10);
                g.fillRect(940,35,10,10);
                g.fillRect(950,45,10,10);
                g.fillRect(940,55,10,10);
                g.fillRect(950,65,10,10);
                g.fillRect(940,75,10,10);
                g.fillRect(950,85,10,10);
                g.fillRect(940,95,10,10);
                g.fillRect(950,105,10,10);
                g.fillRect(940,115,10,10);
                g.fillRect(950,125,10,10);
                g.fillRect(940,135,10,10);


                g.setColor(c1);
                g.fillRect(940,5,10,10);
                g.fillRect(950,15,10,10);
                g.fillRect(940,25,10,10);
                g.fillRect(950,35,10,10);
                g.fillRect(940,45,10,10);
                g.fillRect(950,55,10,10);
                g.fillRect(940,65,10,10);
                g.fillRect(950,75,10,10);
                g.fillRect(940,85,10,10);
                g.fillRect(950,95,10,10);
                g.fillRect(940,105,10,10);
                g.fillRect(950,115,10,10);
                g.fillRect(940,125,10,10);
                g.fillRect(950,135,10,10);
		
		while(i<1000)
                {
                        g.setColor(c);
                        g.fillOval(i,70,10,10);
                        g.fillOval(i+15,80,10,10);
                        i=i+30;
                }
}

public void drawCar(Color a,Color b, int x, int y,Graphics g)
{
	g.setColor(a);
	g.fillRect(x,y,60,10);
	g.setColor(b);
	g.fillRect(x+10,y-10,40,10);
	g.fillOval(x+10,y+10,10,10);
	g.fillOval(x+40,y+10,10,10);
}


}
