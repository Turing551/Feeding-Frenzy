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
	private static JFrame start;
	private static Point p;
	private static BufferedImage background;
	private static BufferedImage logo;
	private static BufferedImage l;
	private static JLabel jbackground;
	private static long time;
	
	public FeedingFrenzy()
	{
		frame = new JFrame("Frenzy");
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Allow Resizing
		frame.setResizable(true);
		
		start = new JFrame("Start Menu");
		start.setSize(800,600);
		start.setLocationRelativeTo(null);
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		start.setResizable(true);
	}
	
	public void setP(Point p){
		p = new Point((int)p.getX()-70, (int)p.getY()-80);
		this.p = p;
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			frame.setVisible(true);
			frame.add(jbackground);
			start.setVisible(false);
        }
	}
	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}
	
	public static void main(String...args) throws Exception
	{
		//objects
		playerFish panelfish = new playerFish();
		FeedingFrenzy panel = new FeedingFrenzy();
		startMenu s = new startMenu();
		//ScoreBoard s = new ScoreBoard();

		
		background = ImageIO.read(new File("underwater-natural-picture.jpg"));
		jbackground = new JLabel(new ImageIcon(background));
		
		//add'em all
		frame.add(panel);     
		frame.add(panelfish);  
        start.add(s);
        start.add(jbackground);
		//frame.add(s);
		
		start.addKeyListener(panelfish);
		frame.addMouseListener(panelfish);
		frame.addMouseMotionListener(panelfish);

		boolean show = true;
		start.setVisible(true);
		frame.setVisible(false);
		p = new Point(0, 0);
		while(show){
			panelfish.setLocation(p);
			time = System.currentTimeMillis();
		}
	}
}

class playerFish extends FeedingFrenzy implements MouseListener, MouseMotionListener
{
	private String name; //stores name of playerFish
	
    public playerFish() {
    	name = "Fishie!";
		setSize(100, 100);
		setVisible(true); //it's like calling the repaint method.
		setName();
    }
     //draws fish 	
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
	//set name of fish
	private void setName(){
		JFrame input = new JFrame("Fish name?");
    	name = JOptionPane.showInputDialog(input, "Name your fishie!");
    	if(name == null){
    		System.exit(0);
    	}
    	
    } 
    //get name of fish
    public String getName(){
    	return name;
    }	
    
    //implement for mouseListener    		   	   	
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

class startMenu extends FeedingFrenzy{
	private BufferedImage logo;
	
	public startMenu() throws IOException{
		BufferedImage img = ImageIO.read(new File("logo.png"));
		logo = getScaledImage(img, 529, 62);
		setSize(800,600);
		setVisible(true);
	}
	 private static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    	BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = resizedImg.createGraphics();
    	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2.drawImage(srcImg, 0, 0, w, h, null);
    	g2.dispose();
    	return resizedImg;
	}
	public void paintComponent(Graphics g)
	{
		drawBackground(g);
	}	
	private void drawBackground(Graphics g){
		g.drawImage(logo, 136, 150, null);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		g.drawString("Press the spacebar to begin!", 255, 300);
	}
}

/*class ScoreBoard extends JPanel
 *	{
		ScoreBoard(){
		setSize(800,150);
		setVisible(true);
	}
	public void paintComponent(Graphics g){
		drawBoard(g);
	}
	private void drawBoard(Graphics g){
		g.setColor(new Color(251,196,68));
		g.fillRect(0,450,800,150);
	}
}
*/


//NEXT UP: FLIPPING FISH!
