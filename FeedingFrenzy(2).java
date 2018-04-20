/*
 *	I may or may not have broken your code XD
 *  All I wanted to do was make bubbles T.T"
 *
 *  Yup I broke your code sorry :P
 *  I edited out the parts I put in lol just so it'd run and be pretty again
 */

import java.awt.*;
import java.awt.Graphics2D.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class FeedingFrenzy extends JPanel implements KeyListener
{
	private static JFrame frame;
	private static Point p;
	private static BufferedImage background;
	private static playerFish panelfish = new playerFish();
	private
	
	public FeedingFrenzy()
	{
		frame = new JFrame("Frenzy");
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Prevent Resizing
		frame.setResizable(true);
	}
	
	public void setP(Point p){
		p = new Point((int)p.getX()-70, (int)p.getY()-80);
		this.p = p;
	}
	
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE){
			frame.add(panelfish);
		}
	}
	
	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}

	public static void main(String...args) throws Exception
	{
		//constructors
		FeedingFrenzy panel = new FeedingFrenzy();
		
		background = ImageIO.read(new File("underwater-natural-picture.jpg"));
		JLabel jbackground = new JLabel(new ImageIcon(background));
		
		//add'em all
		frame.add(panel);
		frame.addMouseListener(panelfish);
		frame.addMouseMotionListener(panelfish);
        //frame.add() WHERE ARE THE BUBBLES?!
		frame.add(jbackground);
		
		frame.setVisible(true);
		p = new Point(0, 0);
		boolean show = true;
		while(show){
			while(ds){
				panelfish.setLocation(p);
			}
		}
	}
}

class playerFish extends FeedingFrenzy implements MouseListener, MouseMotionListener
{
	private String name;
	
    public playerFish() {
    	name = "Fishie!";
		setSize(500, 500);
		setVisible(true); //it's like calling the repaint method.
		setName();
    }  	
	public void paintComponent(Graphics g)
	{
		drawPlayer(g);		
	}
	private void drawPlayer(Graphics g){
		Graphics2D g2 = (Graphics2D) g; //typecasting graphics into 2D graphics type
		
		//fins
		g.setColor(new Color(255,255,128));
		g.fillOval(60,10,10,30);
		g.fillOval(60,50,10,30);
		g.fillOval(75,47,10,30);
		g.fillOval(20,20,10,55);
		
		//body
		g.setColor(new Color(255,198,198)); //body mass
		g.fillOval(25,25,75,50);
		g.setColor(Color.BLACK);	
		g2.draw(new Arc2D.Double(75,25,25,50,150,70,Arc2D.OPEN)); //gill		
		g.drawLine(55,40,70,60);  //scales
		g.drawLine(45,45,60,65);
		g.drawLine(42,57,62,37);
		g.drawLine(50,65,70,45);		
		g2.draw(new Arc2D.Double(90,55,20,10,150,80,Arc2D.OPEN));  //mouth	
		g.fillOval(85,40,8,8);  //eyeball
		g.setColor(Color.WHITE);
		g.fillOval(86,41,4,4);
	
		g.setColor(Color.BLACK);
		g.drawString(name,90,30);
		//stop adding random tags omg >.<" :'( but... I'm bored
	}
	private void setName(){
    	JFrame input = new JFrame("Fish name?");
    	name = JOptionPane.showInputDialog(input, "Name your fishie!");
    }
	
	public void mouseWheelMoved(MouseWheelEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){
    	Point mousePoint = e.getPoint();
		super.setP(mousePoint);
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
}

//SUCH KAWAII!

//NEXT UP: FLIPPING FISH!

/*lets try to make random bubbles that appear everywhere ^.^
 *okay this doesn't work when I put it in here but like look at my doodle class XD
 *that's basically where I'm gonna make graphics lol
 *I'm trying to get it to delay so it displays the bubbles in sequence...Ms. Truong said something about Stage6 in the APCSII Project folder but I 
 *don't know how to implement the code into this without screwing it up again
 *I'll try and get seaweed done by like some time XD 
 *sorry, I'm not very good at coding <3
*/