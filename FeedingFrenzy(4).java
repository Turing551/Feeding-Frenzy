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
import javax.sound.sampled.*;

public class FeedingFrenzy extends JLabel implements KeyListener, ActionListener
{
	//Labeling
	private static FeedingFrenzy panel; //	main panel
	private static JFrame frame; //	 main frame
	private static JFrame scores; //  Scoreboard frame
	private static JFrame start; //  startmenu frame
	private static JFrame endframe; //  endmenu frame
	private static ScoreBoard sc; //  score board
	private static endMenu end; //  end menu
	private static startMenu s; //  start menu
	
	//timers
	private static long time; //  System time
	private static long timer; //  timer for calculating System time differences
	private static javax.swing.Timer t; //  timer for repeats
	private static int killtime; //  Left orca movement interval
	private static int killtime2; //  Right orca movement interval
	private static long wave; //  Left orca wave
	private static long wave2; //  Right orca wave
	
	//major objects
	private static playerFish panelfish; //  player
	private static ArrayList<predatorFish> orca1; //  Left orcas
	private static ArrayList<predatorFish2> orca2; //  Right orcas
	private static background b; //  background
	
	//minor objects
	private static ArrayList<backgroundFish> bf; //  Left small fishes
	private static ArrayList<backgroundFish2> bf2; //  Right small fishes
	private static int fishCount; //  Left small fishes count
	private static int fishCount2; //  Right small fishes count
	private static backgroundMusic bm; //  background music
	private static ArrayList<alert> al; //  alert for left orcas
	private static ArrayList<alert> al2; //  alert for right orcas
	private static ArrayList<curve> seaweeds; //  seaweeds
	private static crab c; //  crab
	private static boolean crabmove; //  boolean for crab moving right
	private static JPanel buttonPanel; //  Restart button panel
	private static JButton restarter; //  Restart button

	//constructor
	public FeedingFrenzy()
	{
		//Main frame instantiation
		frame = new JFrame("Feeding Frenzy"); //  Name
		frame.setSize(800,600); //  size set to 800 by 600
		frame.setLocationRelativeTo(null); //  set in center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //  close upon clicking X
		frame.setResizable(false); //  deny resizing
		
		//Start frame instantiation
		start = new JFrame("Start Menu"); //  Name
		start.setSize(800,600);
		start.setLocationRelativeTo(null);
		start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.setResizable(false);
				
		//Score frame instantiation
		scores = new JFrame("Score"); //  Name
		scores.setSize(200,200); //  size set to 200 by 200
		scores.setLocation(new Point(1125,125)); //  set next to main frame
		scores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scores.setResizable(false);
		
		//End frame instantiation
		endframe = new JFrame("Game Over"); //  Name
		endframe.setSize(800,600);
		endframe.setLocationRelativeTo(null);
		endframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endframe.setResizable(false);
		endframe.setVisible(false); //  does not show in beginning
	}
	
	//implements for keyListener
	public void keyPressed(KeyEvent e){
		
		//Upon clicking spacebar...
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			
			//instantiation
			time = System.currentTimeMillis();
			timer = System.currentTimeMillis();
			wave = System.currentTimeMillis();
			wave2 = System.currentTimeMillis();
			fishCount = 0;
			fishCount2 = 0;
			killtime = (int)(Math.random()*4+6)*1000; //  First left orcas spawn time will be between 6 to 10 seconds
			killtime2 = (int)(Math.random()*4+6+killtime)*1000; //  First right orcas spawn time will be between 6 to 10 seconds after first left orcas spawn
			
			//music
			frame.add(bm); //  Frame will add background music
			bm.loop(); //  background music loop() method will loop the music
			
			//import minor objects
			b.drawRock(); //  background drawRock() method will draw Rocks and Corals
			
			for(int i = 0; i < 13; i++){
				Point seaweedpos = new Point((int)(Math.random()*700+20), (int)(Math.random()*70+300)); //  location anywhere from 20-719 for x and 300-369 for y
				seaweeds.add(new curve());																			//  instantiation and adding seaweeds
				b.add(seaweeds.get(i));
				seaweeds.get(i).setLocation(seaweedpos); //  randoming seaweed position from 'seaweedpos'
			}
			b.add(c); //  add Crab
			c.setLocation((int)(Math.random()*400+250),400); //  randoming Crab Location, anywhere from 250-649 for x, and 400 for y
			crabmove = true; //  Crab will always move right first
			frame.add(b); //  mainframe will add background
			
			//changing Jframes
			frame.setVisible(true); //  main frame will now show
			scores.setVisible(true); //  scoreboard will now show
			start.setVisible(false); //  start menu will now close
			
			//repeat timer
			t = new javax.swing.Timer(10,this); //  Instantiate repeat timer, each 10 millisecond delay will trigger actionPerformed() of ActionListener
			t.setRepeats(true); //  repeat timer
       		t.setCoalesce(true); //  allow timer repeat multiple times
        	t.start(); //  begins timer
        }
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	//main method
	public static void main(String...args) throws Exception
	{
		//objects instantiation
		//panel instantiation
		panel = new FeedingFrenzy();
		s = new startMenu();
		sc = new ScoreBoard();
		b = new background();
		
		//major objects
		panelfish = new playerFish();
		orca1 = new ArrayList<predatorFish>();
		orca2 = new ArrayList<predatorFish2>();
		
		//minor objects
		bf = new ArrayList<backgroundFish>();
		bf2 = new ArrayList<backgroundFish2>();
		bm = new backgroundMusic();
		al = new ArrayList<alert>();
		al2 = new ArrayList<alert>();
		seaweeds = new ArrayList<curve>();
		c = new crab();
		
		//Input box for player name
		panelfish.setName(); //  setName() method of playerFish which starts input box
		String n = panelfish.getName();
		sc.setName(n); //  gives name to scoreboard
		
		//adding components to JFrame
		//Main frame
		frame.add(panel); //  adds main
		frame.add(panelfish); //  adds player
		
		//Start menu frame
        start.add(s); //  adds logo
        start.add(b); //  adds background
        
        //Scoreboard frame
		scores.add(sc); //  adds scoreboard
		
		//adding listeners to components
		//Start menu
		start.addKeyListener(panel); //  KeyListener for main panel
		
		//Main frame
		frame.addMouseListener(panelfish);
		frame.addMouseMotionListener(panelfish); //  MouseListener for player
		frame.addMouseListener(bm);
		frame.addMouseMotionListener(bm); //  MouseListener for background music volume bar
		
		
		//Main method
		start.setVisible(true); //  start menu starts first
	}
	
	//ActionListener trigger
	public void actionPerformed(ActionEvent e){
		//Crab movement
		/* Crab will cycle movement from 250 to 650 pixel and vice versa
		 * Crab eyes will move by 5 pixels once it reaches 250 pixel end or 650 pixel end */
		if(c.getX() < 650 && crabmove){
			c.setLocation(c.getX()+1, c.getY()); //  incrementation of crab movement to right
		}else if(c.getX() == 650){
			c.moveEyes(); //  Calls on moveEyes() to move eyes of crab
			crabmove = false; //  allows crab to move left
		}
		if(c.getX() > 250 && !crabmove){
			c.setLocation(c.getX()-1, c.getY()); //  decrementation of crab movement to left
		}else if(c.getX() == 250){
			c.moveEyes();
			crabmove = true;
		}
		
		//Left Orca movement
		/* Left Orca wave will always begin first
		 * Once current time reaches killtime, wave will begin
		 * Wave always starts with an alert of Orca position */
		if(System.currentTimeMillis()-wave >= killtime){
			int orcasize = (int)(Math.random()*2+1); //  1 to 2 Orcas will spawn each left wave
			
			//Spawn orca
			for(int z = 0; z < orcasize; z++){
				int orcax = -527; //  Left orcas will always begin at -527 x position
				int orcay = 0;
				/* By a 50% chance, Orca may either spawn in a random y position, or spawn in the current position of where player is */
				if(Math.random()*1 < 0.50){
					orcay = (int)(Math.random()*448)-224;
				}else{
					orcay = panelfish.getY();
				}
				orca1.add(z, new predatorFish(orcax, orcay));
				b.add(orca1.get(z)); //  Adding orcas into the background component
				(orca1.get(z)).setLocation(orcax, orcay);
				/* Throws IOException from calling Alert (alert uses a BufferedImage) */
				try{
					
					//Adds alerts
					al.add(z, new alert());
					b.add(al.get(z)); //  Adding alert into the background component
					(al.get(z)).setLocation(-375, orcay);} //  Changes alert position to where Orca is
				catch (IOException f){}
			}
			
			//Resetting next spawn time
			killtime = (int)(Math.random()*4+6)*1000; //  Next spawn time will be between 6 to 10 seconds
			wave = System.currentTimeMillis(); //  Marks current time position, allows next wave of Left orcas after spawn time has passed
		}
		/* After 1 second from the alert, Orcas will move */
		if(System.currentTimeMillis()-(wave+1000) >= 1000){
			
			//Remove alert
			for(int alsize = 0; alsize < al.size(); alsize++){
				b.remove(al.get(alsize));
				al.remove(al.get(alsize));
			}
			
			//Moves Orcas
			for(int p = 0; p < orca1.size(); p++){
				orca1.get(p).move(); //  Moves left Orca by move() method
				
				/* Upon touching a 50 pixel radius or less of Orca's mouth, game will end */
				if(Math.sqrt(Math.pow(orca1.get(p).getX()+97-panelfish.getX(),2) + Math.pow(orca1.get(p).getY()-panelfish.getY(),2)) <= 50){
					try{
						this.theend();
					}catch (IOException f){}
				}
				
				/* When Orca reaches other side of the gamescreen, Orca will be removed */
				if(orca1.get(p).getX() >= 1372){
					b.remove(orca1.get(p));
					orca1.remove(orca1.get(p));
					p--;
				}
			}
		}
		
		//Right Orca movement
		/* Same as above
		 * Orca will spawn after Left Orca spawns */
		if(System.currentTimeMillis()-wave2 >= killtime2){
			int orcasize2 = (int)(Math.random()*2+1);
			
			for(int q = 0; q < orcasize2; q++){
				int orca2x = 527;
				int orca2y = 0;
				if(Math.random()*1 < 0.50){
					orca2y = (int)(Math.random()*448)-224;
				}else{
					orca2y = panelfish.getY();
				}
				orca2.add(q, new predatorFish2(orca2x, orca2y));
				b.add(orca2.get(q));
				(orca2.get(q)).setLocation(orca2x, orca2y);
				
				try{
					al2.add(q, new alert());
					b.add(al2.get(q));
					(al2.get(q)).setLocation(370, orca2y);
				}catch (IOException f){}
			}
			killtime2 = (int)(Math.random()*4+6)*1000; //  This also notes that both wave can spawn at the same time once spawn times random to the same spawn time
			wave2 = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis()-(wave2+1000) >= 1000){
			
			for(int alsize2 = 0; alsize2 < al2.size(); alsize2++){
				b.remove(al2.get(alsize2));
				al2.remove(al2.get(alsize2));
			}
			
			for(int a = 0; a < orca2.size(); a++){
				orca2.get(a).move();
				
				if(Math.sqrt(Math.pow(orca2.get(a).getX()-97-panelfish.getX(),2) + Math.pow(orca2.get(a).getY()-panelfish.getY(),2)) <= 50){
					try{
						this.theend();
					}catch (IOException f){}
				}
				
				if(orca2.get(a).getX() <= -527){
					b.remove(orca2.get(a));
					orca2.remove(orca2.get(a));
					a--;
				}
			}
		}
		
		//Spawn fishes
		/* 1 Fish will spawn every .25 seconds */
		if(System.currentTimeMillis()-time >= 250){
			//50% change to spawn fish left or right
			if((int)(Math.random()*2) == 0){
				bf.add(fishCount, new backgroundFish());
				b.add(bf.get(fishCount));
				(bf.get(fishCount)).setLocation((int)-422.5, (int)(Math.random()*450-225)); //  Left fish beginning location, always -422 for x and 225-674 for y location
				fishCount++; // variable will keep track of how many fishes have spawned in the gamescreen
				time = System.currentTimeMillis();
			}else{
				bf2.add(fishCount2, new backgroundFish2());
				b.add(bf2.get(fishCount2));
				(bf2.get(fishCount2)).setLocation((int)377.5, (int)(Math.random()*450-225)); //  Right fish beginning location, always 377 for x and 225-674 for y location
				fishCount2++;
				time = System.currentTimeMillis();
			}
		}
		
		//Small fishes movement
		for(int x = 0; x < bf.size(); x++){
			bf.get(x).move(panelfish); //  Calls left fish movement move() method
			
			/* Once fish contacts within a radius of 70 or less with player, it is removed */
			try{
				if(Math.sqrt(Math.pow(panelfish.getX()-bf.get(x).getX(),2)+Math.pow(panelfish.getY()-bf.get(x).getY(),2)) < 70){
					b.remove(bf.get(x));
					bf.remove(bf.get(x));
					x--;
					fishCount--;
					sc.increment(); //  Removes fish and increments score by 1
				}
			}catch(ArrayIndexOutOfBoundsException f){x=0;} //  Just to be safe, this changes x from going -1 to 0
			/* Once fish goes off screen, it is removed */
			try{
				if(bf.get(x).getX() <= -430 || bf.get(x).getX() >= 380){
					b.remove(bf.get(x));
					bf.remove(bf.get(x));
					x--;
					fishCount--; //  Removes fish
				}
			}catch(ArrayIndexOutOfBoundsException f){x=0;}
		}
		
		//Right Small fishes movement, same as above
		for(int y = 0; y < bf2.size(); y++){
			bf2.get(y).move(panelfish);
			try{
				if(Math.sqrt(Math.pow(panelfish.getX()-bf2.get(y).getX(),2)+Math.pow(panelfish.getY()-bf2.get(y).getY(),2)) < 70){
					b.remove(bf2.get(y));
					bf2.remove(bf2.get(y));
					y--;
					fishCount2--;
					sc.increment();
				}
			}catch(ArrayIndexOutOfBoundsException f){y=0;}
			try{
				if(bf2.get(y).getX() <= -430 || bf2.get(y).getX() >= 380){
					b.remove(bf2.get(y));
					bf2.remove(bf2.get(y));
					y--;
					fishCount2--;
				}
			}catch(ArrayIndexOutOfBoundsException f){y=0;}
		}
	}
	
	//Once gets killed by Orcas, this method is called
	public void theend() throws IOException/* for Restart button picture */{
		//Close game frames
		frame.setVisible(false);
		scores.setVisible(false);
		
		//Restart button
		ImageIcon restartIcon = new ImageIcon("Restart.png"); //  Creates JButton Image
		restarter = new JButton(restartIcon); //  Instantiates JButton icon image
		restarter.addActionListener(new reaction(panel)); //  Adds ActionListener to JButton, clicking on button will restart the game
		buttonPanel = new JPanel(); //  Instantiates Button Panel (This is for changing the JButton location and size)
		
		//Button Panel for JButton
		buttonPanel.add(restarter); //  Adds JButton component to panel
		buttonPanel.setVisible(true);
		buttonPanel.setSize(buttonPanel.getPreferredSize()); //  Changes Button Panel to its preferred size, but not preferred location
		buttonPanel.setLocation(160,400); //  Changes Button panel location to 160 for x, 400 for y (Centered and 400 pixels down)
		
		//Instantiates End Menu with current score
		end = new endMenu(sc.getScore());

		//Frames
		endframe.getContentPane().setLayout(null); //  Changes End menu frame layout to null for editing, rather than a preferred layout
		endframe.add(end); //  Adds End menu component to frame
		endframe.add(buttonPanel); //  Adds Button Panel component to frame
		endframe.setVisible(true); //  Calls for End menu frame to show
		
		//Stops
		bm.stopper(); //  Stops background music
		t.stop(); //  Stops timer
	}
	
	//Restart method
	public void restart() throws Exception{
		endframe.setVisible(false); //  Closes End frame
		this.main(); //  Restart
	}
}

//The Restart button action trigger
class reaction implements ActionListener{
	private FeedingFrenzy p;
	
	//Constructor
	reaction(FeedingFrenzy p){
		this.p = p; //  Allows calling of methods inside Main class
	}
	
	public void actionPerformed(ActionEvent e){
		try{p.restart();}catch (Exception f){} //  Throws exception and restarts the game
	}
}

//FINALLY, WELCOME TO THE MAJOR OBJECTS

/* MAJOR OBJECT 1: player
 *
 * Player is a small fish that follows the mouse
 * Fish will rotate along with mouse movement
 * Fish will change sides upon moving left or right
 * This also contains the method at the beginning of the game which calls for the input of Fish name */
class playerFish extends JLabel implements MouseListener, MouseMotionListener
{
	private String name; //  stores name of playerFish
	private int side; //  side, left or right
	
	//For mouse and roatation
	private double mousex;
	private double mousey;
	private double savex;
	private double savey;
	
	//Constructor
    public playerFish() {
    	mousex = 1.0; //  Fish will always face east first, 0 degrees
    	mousey = 0.0;
    	savex = this.getX(); //  Saves current fish location for rotation
    	savey = this.getY();
    	side = 1; //  Right side first
		setSize(800, 600); //  Size for frame is 800 by 600
		setVisible(true); //  Allow Fish to be visible
    }
    
    //draws fish 
	public void paintComponent(Graphics ge)
	{
		ge.setColor(Color.BLACK);
		ge.setFont(new Font("Helvetica",Font.PLAIN,12));
		ge.drawString(name,430,280); //  Name will be displayed at 430 for x and 280 for y
		Graphics2D g2 = (Graphics2D) ge; //  typecasting graphics into 2D graphics type
		
		//Rotation
		/* Fish is drawn in the center, translating to middle of the frame and rotating it allows it to be rotated in the center */
		g2.translate(400,300);
		g2.rotate(Math.atan(mousey/mousex)); //  MAJOR MOVEMENT 2: ROTATION, arctan(Y distance of mouse from last point, X distance of mouse from last point)
		g2.translate(-400,-300);
		
		//Draw
		drawPlayer(g2);		
	}		
	private void drawPlayer(Graphics2D g){
		
		//Right side
		if(side == 1){
			//fins
			g.setColor(new Color(255,255,128));
			g.fillOval(400,265,10,30);
			g.fillOval(400,305,10,30);
			g.fillOval(415,302,10,30);
			g.fillOval(360,275,10,55);
		
			//body
			g.setColor(new Color(255,198,198)); //body mass
			g.fillOval(365,280,75,50);
			g.setColor(Color.BLACK);		
			g.draw(new Arc2D.Double(415,280,25,50,150,70,Arc2D.OPEN)); //gill		
			g.drawLine(395,295,410,315);  //scales
			g.drawLine(385,300,400,320);
			g.drawLine(382,312,402,292);
			g.drawLine(390,320,410,300);		
			g.draw(new Arc2D.Double(430,310,20,10,150,80,Arc2D.OPEN));  //mouth		
			g.fillOval(425,295,8,8);  //eyeball
			g.setColor(Color.WHITE);
			g.fillOval(426,296,4,4);
		}
		
		//Left side
		else{
			//fins
			g.setColor(new Color(255,255,128));
			g.fillOval(390,265,10,30);
			g.fillOval(390,305,10,30);
			g.fillOval(375,302,10,30);
			g.fillOval(430,275,10,55);
		
			//body
			g.setColor(new Color(255,198,198)); //body mass
			g.fillOval(360,280,75,50);
			g.setColor(Color.BLACK);		
			g.draw(new Arc2D.Double(360,280,25,50,320,70,Arc2D.OPEN)); //gill		
			g.drawLine(405,295,390,315);  //scales
			g.drawLine(415,300,400,320);
			g.drawLine(418,312,398,292);
			g.drawLine(410,320,390,300);		
			g.draw(new Arc2D.Double(350,310,20,10,310,80,Arc2D.OPEN));  //mouth		
			g.fillOval(367,295,8,8);  //eyeball
			g.setColor(Color.WHITE);
			g.fillOval(370,296,4,4);
		}
	}
	//set name of fish
	public void setName() throws IOException{
		JFrame input = new JFrame("Fish name?"); //  Input box
		final ImageIcon fishIcon = new ImageIcon("FishIcon.png"); //  Input box image
    	name = (String)JOptionPane.showInputDialog(input, "Fish name?", "Name your fishie!", JOptionPane.QUESTION_MESSAGE, fishIcon, null, null); //  Input box will be a question type with fishIcon image
    	//This is only if 'cancel' is clicked to get rid of NullPointerException; if there is no name, the game will still play with an empty String for name
    	if(name == null){
    		System.exit(0);
    	}
    } 
    //gets name of fish
    public String getName(){
    	return name;
    }
    //implements for mouseListener, MouseMotionListener		   	   	
	public void mouseWheelMoved(MouseWheelEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){
		/* MAJOR MOVEMENT 1:
		 *
		 * Fish will follow mouse
		 * Since mouse location starts at 8 by 31, fish location will be subtracted with 400+8 and 300 + 31 */
		Point mousePoint = new Point();
		mousePoint.setLocation((double)(e.getPoint().getX())-408.0, (double)(e.getPoint().getY())-331.0);
		this.setLocation(mousePoint);
		
		/* MAJOR MOVEMENT 2:
		 * Fish will rotate with mouse movement */
    	mousex = this.getX()-savex; //  Distance of mouse from last point                      //Math.atan will use these
    	mousey = this.getY()-savey;                                                            //values to determine the angle
    	savex = this.getX(); //  Sets last point to current point                              //for rotation.
    	savey = this.getY();
    	if(mousex > 0){ //  If mouse goes right, fish faces right
    		side = 1;
    	}
    	if(mousex < 0){ //  vice versa
    		side = 2;
    	}
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
}

/* MAJOR OBJECT 2: Background
 *
 * Background holds other minor objects */
class background extends JLabel{
	
	private BufferedImage b; //  holds background picture
	private rocks r; //  imports minor object rocks
	private boolean rock; //  boolean for draw rocks or not
	private ArrayList<coral> corals; //  importats minor object corals
	private int size; //  coral arraylist size
	
	//Constructor
	public background() throws IOException{
		BufferedImage img = ImageIO.read(new File("underwater-natural-picture.jpg")); //IMPORTS BACKGROUND IMAGE
		b = getScaledImage(img, 800, 600); // Getting a scaled image of picture to fit frame
		setSize(800,600); //  size will be 800 by 600
		setVisible(true); //  will be visible
		r = new rocks(); //  instantiate rocks
		rock = false; //  don't draw yet in start screen
		corals = new ArrayList<coral>();
		size = (int)(Math.random()*3+2); //  2-4 corals upon starting the game
		for(int i = 0; i < size; i++){
			corals.add(new coral()); //  instantiate corals
		}
	}
	
	//scales image
	public static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    	BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); //  cast into RGB type
    	Graphics2D g2 = resizedImg.createGraphics(); //  Making a new buffered image into graphics
    	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //  set rendering
    	g2.drawImage(srcImg, 0, 0, w, h, null); //  Changes the image into a resized one through Graphics
    	g2.dispose(); //  disposes graphics context and releases possible system resources that its using
    	return resizedImg; //  return the buffered image
	}
	public void paintComponent(Graphics gc)
	{
		drawb(gc); //  Draws the background image, resized
		if(rock){ //  Will be called from the ActionListener of main to draw rocks in the game
			r.paintComponent(gc); //  calls paintComponent in rocks
			for(int i = 0; i < size; i++){ //  and corals
				corals.get(i).paintComponent(gc); //  calls paintComponent in coral
			}
		}
	}
	//draws background	
	private void drawb(Graphics gc){
		gc.drawImage(b,0,0, null);
	}
	//For main to trigger whether or not to draw rocks
	public void drawRock(){
		rock = true;
	}
}

/* MAJOR OBJECT 3: Orcas
 *
 * Orcas will move from one side to another side
 * Orcas can be spawned with random speed and a random path 
 * player will die upon touching Orca's mouth */
// LEFT ORCA
class predatorFish extends JLabel{
	
	//movement variables
	private long time;
	private long acceltime; //  acceltime - time = time to accelerate
	private double t;
	private double trate; //  rate of movement
	private double accel; //  acceleration, defaulted at 
	private double speed;
	private double radius;
	
	//rotation variables
	private int chance;
	private int spawnx;
	private int spawny;
	private double nextx;
	private double nexty;
	
	//Constructor
	predatorFish(int x, int y){
		time = System.currentTimeMillis(); //  time for movements
		acceltime = System.currentTimeMillis(); //  time for acceleration
		setSize(800,600); //  size will be 800 by 600
		setVisible(true); //  will be visible
		speed = Math.random()*3+2; //  speed randomed to be 2 - 4.999999
		accel = Math.random()*3+2; //  acceleration randomed to be 2 - 4.999999
		chance = (int)(Math.random()*2); //  chance for a straight path or a sine wave path
		t = 0.0; //  variable, like time, if moving in a sine wave
		trate = Math.random()*0.5+1.5; //  t speed
		spawnx = x; //  from the Main ActionListener
		spawny = y;
		radius = Math.random()*Math.sqrt(200)+Math.sqrt(900); //  sine wave radius, from 10 by 10 to 40 by 40
		nextx = 1.0; //  for rotation (always start by facing east)
		nexty = 0.0;
	}
	//Graphics
	public void paintComponent(Graphics gf){
		Graphics2D gf2 = (Graphics2D) gf; //typecasting graphics into 2D graphics type
		gf2.translate(400,300);
		gf2.rotate(Math.atan(nexty/nextx)); //  like player, this is the same mechanism for rotation, with same translation to center
		gf2.translate(-400,-300);
		drawFish(gf2);
	}
	private void drawFish(Graphics2D gf){		
		//body 
		gf.setColor(Color.WHITE);		
		int[] whiteXval ={524, 527, 527, 522, 512, 499, 433, 394, 384, 372, 333, 375};
		int[] whiteYval ={306, 309, 321, 330, 340, 344, 344, 337, 336, 333, 333, 285};
		Polygon white = new Polygon(whiteXval,whiteYval,whiteXval.length);
		gf.fillPolygon(white);		
		
		gf.setColor(Color.BLACK);		
		int[] bodyXval ={453, 480, 493, 503, 513, 524, 527, 519, 514, 470, 462, 457, 452, 448, 441, 436, 437, 426, 417, 408, 383, 383, 388, 394, 400, 405, 405, 393, 376, 372, 352, 345, 345, 342, 342, 339, 339, 331, 325, 322, 304, 304, 308, 297, 287, 283, 280, 277, 273, 291, 299, 306, 316, 342, 372, 388, 423, 437};
		int[] bodyYval ={257, 260, 266, 268, 277, 292, 307, 312, 314, 314, 316, 320, 322, 331, 338, 343, 350, 357, 359, 362, 362, 353, 346, 342, 339, 340, 330, 324, 322, 322, 322, 329, 334, 341, 355, 359, 363, 372, 374, 376, 376, 348, 332, 332, 323, 315, 313, 301, 296, 296, 298, 302, 313, 284, 262, 257, 255, 254};
		Polygon body = new Polygon(bodyXval,bodyYval,bodyXval.length);
		gf.fillPolygon(body);
		
		gf.setColor(Color.WHITE);
		int[] eyeXval={487, 508, 508, 505, 504, 487, 485, 485, 483, 484, 485, 487};
		int[] eyeYval={283, 294, 296, 296, 298, 298, 297, 294, 294, 286, 285, 283};
		Polygon eye = new Polygon(eyeXval,eyeYval,eyeXval.length);
		gf.fillPolygon(eye);
		
		//fin
		gf.setColor(Color.BLACK);
		int[] finxval = {422, 431, 422, 421, 421, 423, 431, 444, 451, 459, 464};
		int[] finyval = {261, 244, 231, 230, 225, 224, 224, 230, 240, 253, 267};
		Polygon fin = new Polygon(finxval,finyval,finxval.length);
		gf.fillPolygon(fin);	
	}
	
	/* MAJOR MOVEMENT 3:
	 * Orcas will either move straight forward or they'll move in a sine wave pattern, with 50% chance */
	public void move(){
		//Didn't use a javax.swing.Timer because it would take too much code
		if(System.currentTimeMillis()-time >= 10){
			if(chance == 0){
				this.setLocation((int)(this.getX()+speed), (int)(this.getY())); //  Moves straight forward
			}else{
				nextx = radius*trate*t+spawnx-this.getX(); //  rotation, x distance = radius * next time frame for sin + spawn location - current location
				nexty = spawny/2+radius*(Math.sin(t))-this.getY(); //  rotation, y distance = spawn place + radius * next sin(time frame) - current location
				this.setLocation((int)(radius*trate*t+spawnx), (int)(spawny/2+radius*(Math.sin(t)))); //  moves to next sine time frame
				t+=0.2; //  changes sine time frame
			}
			time = System.currentTimeMillis(); //  resets
		}
		//every .1 seconds, speed will accelerate
		if(System.currentTimeMillis()-acceltime >= 100){
			speed += accel;
			acceltime = System.currentTimeMillis();
		}
	}
}

// RIGHT ORCA, same as above
class predatorFish2 extends JLabel{
	
	private long time;
	private long acceltime;
	private double t;
	private double trate;
	private double accel;
	private double speed;
	private double radius;
	private int chance;
	private int spawnx;
	private int spawny;
	private double nextx;
	private double nexty;
	
	//Constructor
	predatorFish2(int x, int y){
		time = System.currentTimeMillis();
		acceltime = System.currentTimeMillis();
		setSize(800,600);
		setVisible(true);
		speed = Math.random()*3+2;
		accel = Math.random()*3+2;
		chance = (int)(Math.random()*2);
		t = 0.0;
		trate = Math.random()*0.5+1.5;
		spawnx = x;
		spawny = y;
		radius = Math.random()*14.1421+42.4264;
		nextx = 1.0;
		nexty = 0.0;
	}
	//Graphics
	public void paintComponent(Graphics gf){
		Graphics2D gf2 = (Graphics2D) gf; //typecasting graphics into 2D graphics type
		gf2.translate(400,300);
		gf2.rotate(Math.atan(nexty/nextx));
		gf2.translate(-400,-300);
		drawFish(gf2);
	}
	//difference is that this has a flipped side
	private void drawFish(Graphics2D gf){		
		//body 
		gf.setColor(Color.WHITE);		
		int[] whiteXval ={276, 273, 273, 278, 288, 301, 367, 406, 416, 428, 467, 425};
		int[] whiteYval ={306, 309, 321, 330, 340, 344, 344, 337, 336, 333, 333, 285};
		Polygon white = new Polygon(whiteXval,whiteYval,whiteXval.length);
		gf.fillPolygon(white);		
		
		gf.setColor(Color.BLACK);		
		int[] bodyXval ={347, 320, 307, 297, 287, 276, 273, 281, 286, 330, 338, 343, 348, 352, 359, 364, 363, 374, 383, 392, 417, 417, 412, 406, 400, 395, 395, 407, 424, 428, 448, 455, 455, 458, 458, 461, 461, 469, 475, 478, 496, 496, 492, 503, 513, 517, 520, 523, 527, 509, 501, 494, 484, 458, 428, 412, 377, 363};
		int[] bodyYval ={257, 260, 266, 268, 277, 292, 307, 312, 314, 314, 316, 320, 322, 331, 338, 343, 350, 357, 359, 362, 362, 353, 346, 342, 339, 340, 330, 324, 322, 322, 322, 329, 334, 341, 355, 359, 363, 372, 374, 376, 376, 348, 332, 332, 323, 315, 313, 301, 296, 296, 298, 302, 313, 284, 262, 257, 255, 254};
		Polygon body = new Polygon(bodyXval,bodyYval,bodyXval.length);
		gf.fillPolygon(body);
		
		gf.setColor(Color.WHITE);
		int[] eyeXval={313, 292, 292, 295, 296, 313, 315, 315, 317, 316, 315, 313};
		int[] eyeYval={283, 294, 296, 296, 298, 298, 297, 294, 294, 286, 285, 283};
		Polygon eye = new Polygon(eyeXval,eyeYval,eyeXval.length);
		gf.fillPolygon(eye);
		
		//fin
		gf.setColor(Color.BLACK);
		int[] finxval = {378, 369, 378, 379, 379, 377, 369, 356, 349, 341, 336};
		int[] finyval = {261, 244, 231, 230, 225, 224, 224, 230, 240, 253, 267};
		Polygon fin = new Polygon(finxval,finyval,finxval.length);
		gf.fillPolygon(fin);	
	}
	public void move(){
		if(System.currentTimeMillis()-time >= 10){
			if(chance == 0){
				this.setLocation((int)(this.getX()-speed), (int)(this.getY())); //  -speed rather than +speed
			}else{
				nextx = spawnx-radius*trate*t-this.getX();
				nexty = spawny/2+radius*(Math.sin(t))-this.getY();
				this.setLocation((int)(spawnx-radius*trate*t), (int)(spawny/2+radius*(Math.sin(t)))); //  -radius rather than + radius for x
				t+=0.2;
			}
			time = System.currentTimeMillis();
		}
		if(System.currentTimeMillis()-acceltime >= 100){
			speed += accel;
			acceltime = System.currentTimeMillis();
		}
	}
}

//WELCOME TO MINOR OBJECTS
/* MINOR OBJECT 1: SCOREBOARD
 *
 * Tracks score
 * Each fish eaten = 1 increment */
class ScoreBoard extends JLabel
{
 	private String name; //stores name of fish
 	private int score; //stores player score
 	private int size;
 	
 	//Constructor
	ScoreBoard(){
		setSize(200,200); //  size 200 by 200
		score = 0; //  instantiation of score
		setVisible(true); //  isVisible
		size = 100; //  size of score text
	}
	//draws board
	public void paintComponent(Graphics ga){drawBoard(ga);}
	private void drawBoard(Graphics ga){
		ga.setFont(new Font("Helvetica",Font.PLAIN,12));
		ga.drawString("Fishy "+name,10,20); //  fish name
		ga.setFont(new Font("Helvetica",Font.BOLD,20));
		ga.drawString("Score: ",10,60); //  "Score:"
		ga.setFont(new Font("Helvetica",Font.BOLD,size));
		ga.drawString(Integer.toString(score),10,150); //  Score
	}
	//sets name of fish
	public void setName(String s){name = s;}
	public int getScore(){return score;}
	//increments score
	public void increment(){
		score+=1;
		this.repaint();
		//decreases score text size once reached threshold
		if(score >= 1000){
			size = 80;
		}
		if(score >= 10000){
			size = 65;
		}
		if(score >= 100000){
			size = 53;
		}
	}
}

/* MINOR OBJECT 2: SMALL FISHES
 *
 * Small fishes will move from one side of screen to another
 * Will either turn back or rotate around player
 * Eaten = score.increment();
 * Randomized bright colors */
//Left small fishes
class backgroundFish extends JLabel{
	
	private int speed = (int)(Math.random()*3+3); //  speed randomized to 3-5 pixels every .01 millisecond
	private int chance; //  for going back or rotate around player
	private int side; //  side, 1 for right, 2 for left
	private boolean goback; //  if goes back, multiplies speed by 1.5
	private double angle; //  fish rotation
	
	//Constructor
	backgroundFish(){
		angle = 0.0; //  angle at 0, face right
		goback = false;
		chance = (int)(Math.random()*2); //  go back or not
		setSize(800,600); //  frame is 800 by 600, but size is smaller
		side = 1; //  left fish
		setVisible(true); //  isVisible
	}
	//draws background fish
	public void paintComponent(Graphics gb)
	{	
		Graphics2D g2 = (Graphics2D) gb; //  change to Graphics2D
		g2.translate(400,300);
		g2.rotate(angle);  // same mechanism
		g2.translate(-400,-300);
	  	drawFish(g2);
	}
	//Graphics2D for rotation
	private void drawFish(Graphics2D gb){
		if(side == 1){
	 		gb.setColor(new Color(173,173,173));
			gb.fillOval((int)382.5,290,40,20);
			gb.fillOval((int)377.5,285,10,30);
			gb.fillOval((int)407.5,295,5,20);
			gb.fillOval((int)402.5,295,5,20);
			gb.fillOval((int)402.5,285,5,20);
			gb.setColor(Color.BLACK);
			gb.fillOval((int)412.5,295,5,5);
		}
		else{
			gb.setColor(new Color(173,173,173));
			gb.fillOval((int)377.5,290,40,20);
			gb.fillOval((int)412.5,285,10,30);
			gb.fillOval((int)387.5,295,5,20);
			gb.fillOval((int)392.5,295,5,20);
			gb.fillOval((int)392.5,285,5,20);
			gb.setColor(Color.BLACK);
			gb.fillOval((int)382.5,295,5,5);
		}
	}
	
	/* MINOR MOVEMENT 1: Move across the screen
	 *
	 * Fish will move from one place to another */
	public void move(playerFish pf){
		/* MINOR MOVEMENT 2: Rotation
		 * Fish will rotate within 100 radius of player, so it won't get caught */
		if(Math.sqrt(Math.pow((pf.getX()-this.getX()),2) + Math.pow((pf.getY()-this.getY()),2)) <= 100){
			if(chance == 0){ //  50% chance to turn back
				try{
				this.setLocation((int)(this.getX()+((this.getX() - pf.getX())/Math.abs(this.getX() - pf.getX()))*speed*1.2), (int)(this.getY()+((this.getY() - pf.getY())/Math.abs(this.getY() - pf.getY()))*speed*1.2));}catch (ArithmeticException e){this.setLocation((int)(this.getX()+1*speed*1.2), (int)(this.getY()+1*speed*1.2));} //location = 1(up or down depending on placement) around fish
			}else{
				goback = true; //  goes back
				this.setLocation((int)(this.getX()-speed*1.5), (int)this.getY()); //  goes back with 1.5 multiplier speed
				side = 2; //  side changes to go back
			}
		}else if(!goback){
			angle = 0.0;
			this.setLocation((int)this.getX()+speed, (int)this.getY()); //  not go back? move forward
		}else{
			angle = 0.0;
			this.setLocation((int)(this.getX()-speed*1.5), (int)this.getY()); //  all else, keep going back
		}
		if(Math.sqrt(Math.pow((pf.getX()-this.getX()),2) + Math.pow((pf.getY()-this.getY()),2)) <= 110){
			try{
				if(Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) > 0){
					angle = Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) - Math.PI/2; //  Math.atan mechanism for angles going downward
				}else if(Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) < 0){
					angle = Math.PI/2 + Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())); //  Math.atan mechanism for angles going upward
				}
				/* Mechanism explained:
				 *
				 * Since fish is always going right
				 * Fishes going down will have the Math.atan(circle radius around fish) to always face southwest, so southwest + 90 degrees = south east
				 * Fishes going up will have the Math.atan(circle radius around fish) to always face northwest, so 90 degrees - northwest = north east
				 * Fishes will either face south east or north east
				 */
			}catch (ArithmeticException e){}; //  if / by 0, then don't change angle
		}
	}
}

//Right small fishes, same as above
class backgroundFish2 extends JLabel{
	
	private long t = System.currentTimeMillis();
	private int speed = (int)(Math.random()*3+3);
	private int chance;
	private int side;
	private boolean goback;
	private double angle;
	
	//Constructor
	backgroundFish2(){
		angle = 0.0;
		goback = false;
		chance = (int)(Math.random()*2);
		setSize(800,600);
		side = 2; //  side will be 2 first
		setVisible(true);
	}
	//draws background fish (from right)
	public void paintComponent(Graphics gb)
	{	
	 	Graphics2D g2 = (Graphics2D) gb;
		g2.translate(400,300);
		g2.rotate(angle);
		g2.translate(-400,-300);
	  	drawFish(g2);
	}
	//Graphics2D
	private void drawFish(Graphics2D gb){
		if(side == 1){
	 		gb.setColor(new Color(173,173,173));
			gb.fillOval((int)382.5,290,40,20);
			gb.fillOval((int)377.5,285,10,30);
			gb.fillOval((int)407.5,295,5,20);
			gb.fillOval((int)402.5,295,5,20);
			gb.fillOval((int)402.5,285,5,20);
			gb.setColor(Color.BLACK);
			gb.fillOval((int)412.5,295,5,5);
		}
		else{
			gb.setColor(new Color(173,173,173));
			gb.fillOval((int)377.5,290,40,20);
			gb.fillOval((int)412.5,285,10,30);
			gb.fillOval((int)387.5,295,5,20);
			gb.fillOval((int)392.5,295,5,20);
			gb.fillOval((int)392.5,285,5,20);
			gb.setColor(Color.BLACK);
			gb.fillOval((int)382.5,295,5,5);
		}
	}
	//Movement mechanism
	public void move(playerFish pf){
		if(Math.sqrt(Math.pow((pf.getX()-this.getX()),2) + Math.pow((pf.getY()-this.getY()),2)) <= 100){
			if(chance == 0){
				try{
					this.setLocation((int)(this.getX()+((this.getX() - pf.getX())/Math.abs(this.getX() - pf.getX()))*speed*1.2), (int)(this.getY()+((this.getY() - pf.getY())/Math.abs(this.getY() - pf.getY()))*speed*1.2));}catch (ArithmeticException e){this.setLocation((int)(this.getX()+1*speed*1.2), (int)(this.getY()+1*speed*1.2));}
			}else{
				goback = true;
				this.setLocation((int)(this.getX()+speed*1.5), (int)this.getY());
				side = 1;
			}
		}else if(!goback){
			angle = 0.0;
			setLocation((int)this.getX()-speed, (int)this.getY());
		}else{
			angle = 0.0;
			this.setLocation((int)(this.getX()+speed*1.5), (int)this.getY());
		}
		if(Math.sqrt(Math.pow((pf.getX()-this.getX()),2) + Math.pow((pf.getY()-this.getY()),2)) <= 110){
			try{
				if(Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) > 0){
					angle = Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) - Math.PI/2;
				}else if(Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX())) < 0){
					angle = Math.PI/2 + Math.atan((this.getY() - pf.getY())/(this.getX()-pf.getX()));
				}
				/* Mechanism explained:
				 *
				 * Since fish is always going left
				 * Fishes going down will have the Math.atan(circle radius around fish) to always face southeast, so southeast + 90 degrees = south west
				 * Fishes going up will have the Math.atan(circle radius around fish) to always face northeast, so northeast - 90 degrees = north west
				 * Fishes will either face south east or north east
				 */
			}catch (ArithmeticException e){};
		}
	}
}

/* MINOR OBJECT 3: Alert Symbol
 * 
 * Alerts incoming Orca location */
class alert extends JLabel{
	private float scale; //  Scale for blinking
	private BufferedImage alertimg; //  Import image
	private float direction; //  Direction for blinking
	
	//Constructor
	alert() throws IOException{
		BufferedImage img = ImageIO.read(new File("alert.png"));
		alertimg = getScaledImage(img, 50, 50);
		setSize(800,600); //  frame is 800 by 600 but size is still 50 by 50
		setVisible(true); //  isVisible
		this.setScale(1f); //  start scale for blink
		direction = -0.1f; //  fade for blink first
		this.fade(); //  fade upon being called
	}
	//scales image, same mechanism
	private static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    	BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = resizedImg.createGraphics();
    	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2.drawImage(srcImg, 0, 0, w, h, null);
    	g2.dispose();
    	return resizedImg;
	}
	//Scale changer
   	public void setScale(float value) {
        scale = value;
   	}
   	//Get scale
	public float getScale(){
		return scale;
	}
	//Get direction
	public float getDirection(){
		return direction;
	}
	//Direction changer
	public void setDirection(float d){
		direction = d;
	}
	//Graphics
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g; //  typecast to 2D
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getScale())); //  Allows Rendering
		drawalert(g2);
	}
	//draws alert	
	private void drawalert(Graphics2D g){
		g.drawImage(alertimg, 375, 275, null);
	}
	/* BLINKING */
	private void fade(){
	    javax.swing.Timer timer = new javax.swing.Timer(25, new alertAction(this)); //  25 millisecond delay for each cycle, look to alertAction for more information
        timer.setRepeats(true); //  set to repeat
        timer.setCoalesce(true); //  allow multiple stacks of timer
        timer.start(); //  start
	}
}

/* MINOR OBJECT 4: Seaweeds
 * 
 * Seaweed focuses on Benzier curves
 * Does not use a sine curve and curves with timer system on repeats */
class curve extends JLabel{
	private double t1; //  Benzier curve to the right
	private double t2;
	private double t3; //  Benzier curve to the left
	private double t4;
	private double t5; //  Right Benzier curve ending point
	private double t6; //  Left Benzier curve ending point
	//Direction for Benzier curve changes
	private double direction1;
	private double direction2;
	private double direction3;
	private double direction4;
	private double direction5;
	private double direction6;
	
	//Constructor
	curve(){
		setSize(800,600);
		setVisible(true);
		//curve will curve in a x range of 25 pixels
		t1 = 412.5;
		t2 = 400;
		t3 = t2;
		t4 = 387.5;
		t5 = t3;
		t6 = t5;
		//first direction change
		direction1 = 1.0;
		direction2 = 1.0;
		direction3 = -1.0;
		direction4 = -1.0;
		direction5 = .5;
		direction6 = -.5;
		this.change(); //  curve effect
	}
	//Graphics
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(-377.5,-225); //  translate and move curve to 0, 0
		drawcurve(g2); //  draws seaweed
		g2.dispose(); //  dispose curve of using any other graphics
	}
	private void drawcurve(Graphics2D g){
		Path2D.Double path = new Path2D.Double();
		/* Mechanism explained:
		 * 
		 * Path2D.Double allows drawing of a path as a shape
		 * moveTo is the mechanism to pinpoint the starting location of a path
		 * 8 curves, 4 for curving up, 4 for curving down the same way with increasing pixels to 10 in between
		 * Benzier curves pinpoints first point x,y to be a point where the first point curves to, second point x,y to where the curve point will curve to, and the last point as where the path will end
		 * Notice that the third x, y, or where the curved path ends, is also changed
		 * This allows an almost flowing motion of a sine wave */
		//Height: 200, Width: 35
		path.moveTo(400-10,425);
		path.curveTo(t1-8,400,t2-8,400,t5-8,375);
		path.curveTo(t3-6,350,t4-6,350,t6-6,325);
		path.curveTo(t1-4,300,t2-4,300,t5-4,275);
		path.curveTo(t3-2,250,t4-2,250,t6-2,225);
		path.curveTo(t4+2,250,t3+2,250,t5+2,275);
		path.curveTo(t2+4,300,t1+4,300,t6+4,325);
		path.curveTo(t4+6,350,t3+6,350,t5+6,375);
		path.curveTo(t2+8,400,t1+8,400,400+10,425);
		//Final lineTo to connect the last point to the first point
		path.lineTo(400-10,425);
		//Color seagreen
		g.setColor(new Color(46,139,87));
		//Fills path shape
		g.fill(path);
		//Color black
		g.setColor(Color.BLACK);
		Path2D.Double linepath = new Path2D.Double();
		//Line in the center of the seaweed
		linepath.moveTo(400,420);
		linepath.curveTo(t1,400,t2,400,t5,375);
		linepath.curveTo(t3,350,t4,350,t6,325);
		linepath.curveTo(t1,300,t2,300,t5,275);
		g.draw(linepath);
	}
	//Modifiers for direction
	public double getScale1(){
		return t1;
	}
	public double getDirection1(){
		return direction1;
	}
	public void setDirection1(double d){
		direction1 = d;
	}
	public double getScale2(){
		return t2;
	}
	public double getDirection2(){
		return direction2;
	}
	public void setDirection2(double d){
		direction2 = d;
	}
	public double getScale3(){
		return t3;
	}
	public double getDirection3(){
		return direction3;
	}
	public void setDirection3(double d){
		direction3 = d;
	}
	public double getScale4(){
		return t4;
	}
	public double getDirection4(){
		return direction4;
	}
	public void setDirection4(double d){
		direction4 = d;
	}
	public double getScale5(){
		return t5;
	}
	public double getDirection5(){
		return direction5;
	}
	public void setDirection5(double d){
		direction5 = d;
	}
	public double getScale6(){
		return t6;
	}
	public double getDirection6(){
		return direction6;
	}
	public void setDirection6(double d){
		direction6 = d;
	}
	//Benzier curve modifiers
	public void setScale1(double t1){
		this.t1 = t1;
		this.repaint();
	}
	public void setScale2(double t2){
		this.t2 = t2;
		this.repaint();
	}
	public void setScale3(double t3){
		this.t3 = t3;
		this.repaint();
	}
	public void setScale4(double t4){
		this.t4 = t4;
		this.repaint();
	}
	public void setScale5(double t5){
		this.t5 = t5;
		this.repaint();
	}
	public void setScale6(double t6){
		this.t6 = t6;
		this.repaint();
	}
	//Same as fade() in alert
	/* MINOR MOVEMENT 3: Curve 
	 *
	 * look at curveAction for more information */
	private void change(){
	    javax.swing.Timer timer = new javax.swing.Timer((int)(Math.random()*10)+25, new curveAction(this)); //  25-35 millisecond delay, this allows different timing for curve movement of seaweeds, look at curveAction for more information
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
	}
}

/* MINOR OBJECT 5: Crab
 * 
 * Crab will move at a range of 250 to 650 pixel for x, and 450 for y
 * Crab eyes will rotate at arrival of an end */
class crab extends JLabel{
	private int eye1x; //  first eye's x
	private int eye2x; //  second eye's x
	private javax.swing.Timer timer;
	
	//Constructor
	crab(){
		setSize(100,50); //  Frame 100 by 50
		setVisible(true); //  isVisible
		eye1x = 46; //  first eye location
		eye2x = 60; //  second eye location
	}
	//Graphics
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		drawCrab(g2);
	}
	private void drawCrab(Graphics g){
				
		Graphics2D g2 = (Graphics2D) g;
		
		g.setColor(Color.ORANGE);
		g.fillOval(20,13,60,30);
		g.drawLine(23,31,14,34);
		g.drawLine(14,34,17,43);
		g.drawLine(27,35,19,37);
		g.drawLine(19,37,21,45);
		g.drawLine(74,33,81,37);
		g.drawLine(81,37,82,44);
		g.drawLine(76,29,86,31);
		g.drawLine(86,31,87,41);
		g.drawLine(75,21,86,15);
		g.drawLine(25,21,19,16);
		g.fillOval(5,4,18,17);
		g.fillOval(82,4,18,17);
		g.setColor(Color.WHITE);
		g.drawLine(10,4,10,9);
		g.drawLine(10,9,17,9);
		g.drawLine(17,9,14,12);
		g.drawLine(89,13,95,12);
		g.drawLine(95,12,91,9);
		g.drawLine(91,9,97,7);
		g.fillOval(40,7,15,13);
		g.fillOval(55,7,15,13);
		g.setColor(Color.BLACK);
		g.fillOval(eye1x,8,7,7);
		g.fillOval(eye2x,9,7,7);
		g2.draw(new Arc2D.Double(50,20,6,6,180,180,Arc2D.OPEN));				
	}
	//Eye modifiers for eye movement
	public int geteye1x(){
		return eye1x;
	}
	public int geteye2x(){
		return eye2x;
	}
	public void increment(){
		eye1x++;
		eye2x++;
	}
	public void decrement(){
		eye1x--;
		eye2x--;
	}
	//Eye movements
	/* MINOR MOVEMENT 4: Eye Movement
	 *
	 * Look at crabAction for more information */
	public void moveEyes(){
		//if eyes move completely to left, eyes will move right upon arrival at end
		if(eye1x == 41 && eye2x == 55){
			timer = new javax.swing.Timer(100, new crabAction(this, true)); //  100 millisecond delay before action
			timer.start();
		}
		//vice versa
		else if(eye1x == 46 && eye2x == 60){
			timer = new javax.swing.Timer(100, new crabAction(this, false));
			timer.start();
		}
	}
	//To stop the timer once eyes has moved once
	public void timerStop(){
		timer.stop();
	}
}

/* MINOR OBJECT 6: Corals
 * 
 * Nice corals, 3D effect */
class coral extends JLabel{
	private int x;
	private int y;
	
	//Constructor
	coral(){
		setSize(100,400); //  Frame 100 by 400
		setVisible(true); //  isVisible
		x = (int)(Math.random()*400+250); //  Coral will be drawn in some random range of 250-649 for x, 425-474 for y
		y = (int)(Math.random()*50+425);	
	}
	//Graphics
	public void paintComponent(Graphics gg){
		Graphics2D gg2 = (Graphics2D) gg;		 //typecasting graphics into 2D graphics type
		drawCoral(gg2);
	}
	private void drawCoral(Graphics2D gg){		
		gg.setColor(new Color(255,158,62));
		gg.fillRect(x+30,y+30,40,60);
		gg.fillOval(x+15,y+30,40,60);
		gg.fillOval(x+45,y+30,40,60);
		
		gg.setColor(new Color(255,181,106));
		gg.fillOval(x+15,y+20,70,30);
		int[] xVal ={x+23,x+11,x+1,x+2,x+5,x+12,x+17,x+23};
		int[] yVal ={y+25,y+20,y+20,y+26,y+30,y+28,y+30,y+25};
		Polygon tent1 = new Polygon(xVal,yVal,xVal.length);
		gg.fillPolygon(tent1);
		int[] xVal2 ={x+27,x+25,x+20,x+23,x+33,x+39,x+40,x+37};
		int[] yVal2 ={y+40,y+47,y+55,y+64,y+67,y+60,y+46,y+42};
		Polygon tent2 = new Polygon(xVal2,yVal2,xVal2.length);
		gg.fillPolygon(tent2);
		int[] xVal3 ={x+61,x+64,x+72,x+91,x+91,x+72};
		int[] yVal3 ={y+43,y+54,y+61,y+61,y+54,y+41};
		Polygon tent3 = new Polygon(xVal3,yVal3,xVal3.length);
		gg.fillPolygon(tent3);
		int[] xVal4 ={x+82,x+95,x+103,x+102,x+96,x+86,x+81};
		int[] yVal4 ={y+36,y+36,y+30,y+26,y+24,y+26,y+30};
		Polygon tent4 = new Polygon(xVal4,yVal4,xVal4.length);
		gg.fillPolygon(tent4);
		int[] xVal5 ={x+64,x+66,x+62,x+52,x+44,x+45};
		int[] yVal5 ={y+21,y+16,y+13,y+14,y+17,y+21};
		Polygon tent5 = new Polygon(xVal5,yVal5,xVal5.length);
		gg.fillPolygon(tent5);
		
		gg.setColor(new Color(255,255,113));
		gg.fillOval(x+20,y+21,60,20);	
	}
}

/* MINOR OBJECT 7: Rocks
 * 
 * YAY ROCKS ALWAYS AT THE BOTTOM LEFT OF THE SCREEN :D */
class rocks extends JLabel{
	
	//Constructor
	rocks(){
		setSize(600,100); //  Frame 600 by 100
		setVisible(true); //  isVisible
	}
	//Graphics
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		drawRocks(g2);
	}
	private void drawRocks(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		//Color changes for each rock
		Color lightGrey = new Color(224,224,224);
		Color grey = new Color(192,192,192);
		Color darkGrey = new Color(141,141,141);
		Color slate = new Color(96,96,96);
		
		g.setColor(lightGrey);
		int[] bigX ={0,10,16,24,54,67,105,124,127,133,144,192,196,205,213,223,234,240,0};
		int[] bigY ={415,404,402,395,385,382,380,386,388,390,406,453,461,470,487,498,538,800,800};
		Polygon big = new Polygon(bigX,bigY,bigX.length);
		g.fillPolygon(big);	
		
		g.setColor(darkGrey);
		int[] medX ={100,103,109,118,133,146,158,169,193,196,208,213,220,233,239,242,248,259,284};
		int[] medY ={600,544,529,518,508,502,492,489,489,492,497,505,509,524,526,532,536,549,600};
		Polygon med = new Polygon(medX,medY,medX.length);
		g.fillPolygon(med);	
			
		g.setColor(grey);
		int[] miniX ={0,19,23,32,40,54,88,99,122,135,139,143,0};
		int[] miniY ={555,544,544,537,538,537,536,541,562,583,592,600,600};
		Polygon mini = new Polygon(miniX,miniY,miniX.length);
		g.fillPolygon(mini);	
			
		g.setColor(slate);
		int[] smallX ={186,191,199,201,215,225,242,248,273,281,301,307,312,319,321};
		int[] smallY ={600,594,587,583,576,570,554,551,548,553,563,570,579,586,600};
		Polygon small = new Polygon(smallX,smallY,smallX.length);
		g.fillPolygon(small);
	}
}

//YES SOUNDS AT LAST! (YES, JIEHAO IS SO PROUD OF THIS)
/* SOUND
 *
 * Uses .wav music
 * Uses AudioSystem controls
 * Allows adjustion of volume
 * May be muted
 * Loops */
class backgroundMusic extends JLabel implements MouseListener, MouseMotionListener{
	//Labeling
	private AudioInputStream music; //  Music stream
	private Clip clip; //  player for music stream
	private boolean open; //  if volume bar is open
	private boolean mute; //  if volume is muted
	private double barlocation; //  volume slider location
	private FloatControl volume; //  volume control
	private BooleanControl muter; //  boolean control for AudioStream to mute
	
	//Constructor
	public backgroundMusic() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
		//Instantiation
		music = AudioSystem.getAudioInputStream(new File("Odyssey.wav")); //  Gets stream
		clip = AudioSystem.getClip(); //  player for stream
		clip.open(music); //  player opens stream
		barlocation = 120.0; //  volume slider is at max by default
		volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //  clip volume control
		muter = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE); //  clip mute control
		open = false; //  Volume bar not opened by default
		mute = false; //  Volume not muted by default
		setSize(130,27); //  Volume bar size
		setVisible(true); //  isVisible
	}
	//Once called by Main, music will loop endlessly
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	//Graphics for volume bar
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		drawBar(g2);
	}
	public void drawBar(Graphics2D g){
		//If bar is open
		if(open){
			//Sound symbol
			g.setColor(Color.BLACK);
			g.fillRoundRect(0, 0, 25, 25, 10, 10);
			g.setColor(Color.WHITE);
			g.fillRoundRect(2, 2, 21, 21, 11, 11);
			
			g.setColor(Color.BLACK);
			g.drawRect(5,8,5,9);
			int[] tzx = {10,19,19,10};
			int[] tzy = {8,5,20,17};
			g.drawPolygon(tzx, tzy, tzx.length);
			
			//Slider container
			g.setColor(Color.BLACK);
			g.drawRoundRect(26,0,101,25,8,8);
			g.setColor(Color.WHITE);
			g.fillRoundRect(27,1,100,24,10,10);
			
			//Slider line
			g.setColor(Color.BLACK);
			g.setStroke(new BasicStroke(4));
			g.drawLine(32,13,122,13);
			g.setStroke(new BasicStroke(1));
			//Slider
			this.drawBlock(g);
		}else{
			//If bar is closed
			//Just sound symbol
			g.setStroke(new BasicStroke(1));
			g.setColor(Color.BLACK);
			g.drawRect(5,8,5,9);
			int[] tzx = {10,19,19,10};
			int[] tzy = {8,5,20,17};
			g.drawPolygon(tzx, tzy, tzx.length);
			
			g.setColor(Color.WHITE);
			g.fillRect(6,9,4,8);
			int[] tzfx = {11,19,19,11};
			int[] tzfy = {8,5,20,17};
			g.fillPolygon(tzfx, tzfy, tzfx.length);
		}
		if(mute){
			//If sound is muted
			//Draws a red cross at sound symbol
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(3));
			g.drawLine(5,5,20,20);
			g.drawLine(5,20,20,5);
			g.setStroke(new BasicStroke(1));
		}
	}
	//Graphics for slider
	private void drawBlock(Graphics2D g){
		g.drawRoundRect((int)barlocation, 5, 5, 15, 2, 2);
		g.setColor(Color.WHITE);
		g.fillRoundRect((int)(barlocation+1), 6, 4, 14, 2, 2); //  Follows mouse with barlocation
	}
	//Stops sound if called by Main
	public void stopper(){
		clip.stop();
	}
	public void mouseWheelMoved(MouseWheelEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	//Once mouse is clicked and dragged inside the Volume bar...
	public void mouseDragged(MouseEvent e){
		if(open){
			if(e.getPoint().getX() >= 33 && e.getPoint().getX() <= 122 && e.getPoint().getY() >= 31 && e.getPoint().getY() <= 46){
				//Changes slider position
				barlocation = e.getPoint().getX()-3;
				//If slider reaches end
				//Mutes
				if(barlocation == 30.0){
					muter.setValue(true);
					mute = true;
				}else{
				//Slider allows volume control if not
					muter.setValue(false);
					volume.setValue((float)((barlocation-107.0)/2));
					mute = false;
				}
			}
		}
	}
	//If clicked on sound symbol...
	public void mouseClicked(MouseEvent e){
		//Either opens or closes volume bar
		if(e.getPoint().getX() <= 25 && e.getPoint().getY() <= 50){
			if(open){
				open = false;
			}else{
				open = true;
			}
		}
	}
}

//MENUS
/* MENUS
 * 
 * Basically frames for start and the end */
//Start Menu
class startMenu extends JPanel{
	
	private BufferedImage logo; //holds logo
	
	//Constructor
	public startMenu() throws IOException{
		BufferedImage img = ImageIO.read(new File("logo.png")); //imports logo
		logo = getScaledImage(img, 652, 98);
		setSize(800,600);
		setVisible(true);
	}
	//scales image, same mechanism
	private static BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
    	BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = resizedImg.createGraphics();
    	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	g2.drawImage(srcImg, 0, 0, w, h, null);
    	g2.dispose();
    	return resizedImg;
	}
	//Graphics
	public void paintComponent(Graphics gd)
	{
		drawBackground(gd);
	}
	//draws start menu	
	private void drawBackground(Graphics gd){
		//Draws the logo with "Press the spacebar to begin!" text at the bottom of it
		gd.drawImage(logo,74,150, null);
		gd.setColor(Color.BLACK);
		gd.setFont(new Font("Helvetica", Font.PLAIN, 20));
		gd.drawString("Press the spacebar to begin!", 255, 300);
	}
}

//End Menu, same as above
class endMenu extends background{
	
	private BufferedImage gm; //holds gameover
	private int score; //  holds score at the end
	
	public endMenu(int score) throws IOException{
		super(); //  supers background to import background image
		BufferedImage img = ImageIO.read(new File("gm.png")); //imports gameover
		gm = super.getScaledImage(img, 603, 98);
		setSize(800,600);
		setVisible(true);
		this.score = score; //  Called by main to give score
	}
	//Graphics
	public void paintComponent(Graphics gd)
	{
		super.paintComponent(gd);
		drawBackground(gd);
	}
	//draws end menu	
	private void drawBackground(Graphics gd){
		//Draws game over sign and "Great Job! You've scored: " + score text beneath it
		gd.drawImage(gm,99,150, null);
		gd.setColor(Color.BLACK);
		gd.setFont(new Font("Helvetica", Font.PLAIN, 20));
		gd.drawString("Great Job! You've scored: "+ score, 255, 300);
	}
}

//ACTIONS, YES THE ACTIONS!! AT LAST
/* Alert Action
 *
 * Allows BLINKING */
class alertAction implements ActionListener{
	private alert al;
	//Constructor
	alertAction(alert al){
		this.al = al; //  Allows modification of alert
	}
	//Action trigger
 	public void actionPerformed(ActionEvent e) {
 		/* mechanism explained:
 		 *
 		 * Gets old scale for opacity
 		 * If opacity reached 1, direction will be set to decrease opacity, and scale will move in that direction until opacity reaches 0
 		 * Once 0, direction will be set to increase opacity, and scale will move in that direction until opacity reaches 1
 		 * This allows the blinking of the alert symbol */
                float oldscale = al.getScale();
                oldscale += al.getDirection();
                if(oldscale < 0){
                    oldscale = 0;
                    al.setDirection(0.1f);
                }else if(oldscale > 1){
                    oldscale = 1;
                    al.setDirection(-0.1f);
                }
                al.setScale(oldscale);
            }
}

/* Curve Action
 *
 * This allows seaweeds to curve */
class curveAction implements ActionListener{
	private curve c;
	//Constructor
	curveAction(curve c){
		this.c = c; //  Allows modification of seaweeds
	}
	//Action trigger
	public void actionPerformed(ActionEvent e) {
		/* mechanism explained:
		 *
		 * Uses the direction modifiers in the seaweed class to change Benzier curves
		 * Once Benzier curve point reaches max at right, direction will change to make it move left and vice versa
		 * Direction 5 and 6 allows Benzier curve end points to change to give seaweeds a flow
		 * Direction 1 and 2 is for right Benzier curves
		 * Direction 3 and 4 is for left Benzier curves */
		 		//Gets old position of curves
                double oldscale1 = c.getScale1();
                double oldscale2 = c.getScale2();
                double oldscale3 = c.getScale3();
                double oldscale4 = c.getScale4();
                double oldscale5 = c.getScale5();
                double oldscale6 = c.getScale6();
                //Modification of curves
                oldscale1 += c.getDirection1();
                oldscale2 += c.getDirection2();
                oldscale3 += c.getDirection3();
                oldscale4 += c.getDirection4();
                oldscale5 += c.getDirection5();
                oldscale6 += c.getDirection6();
                //Modification of direction of curves if reached max right or left
                if(oldscale1 < 387.5){
                    oldscale1 = 387.5;
                    c.setDirection1(1.0);
                }else if(oldscale1 > 412.5){
                    oldscale1 = 412.5;
                    c.setDirection1(-1.0);
                }
                if(oldscale2 < 387.5){
                    oldscale2 = 387.5;
                    c.setDirection2(1.0);
                }else if(oldscale2 > 412.5){
                    oldscale2 = 412.5;
                    c.setDirection2(-1.0);
                }
                if(oldscale3 < 387.5){
                    oldscale3 = 387.5;
                    c.setDirection3(1.0);
                }else if(oldscale3 > 412.5){
                    oldscale3 = 412.5;
                    c.setDirection3(-1.0);
                }
                if(oldscale4 < 387.5){
                    oldscale4 = 387.5;
                    c.setDirection4(1.0);
                }else if(oldscale4 > 412.5){
                    oldscale4 = 412.5;
                    c.setDirection4(-1.0);
                }
                if(oldscale5 < 393.75){
                    oldscale5 = 393.75;
                    c.setDirection5(.5);
                }else if(oldscale5 > 406.25){
                    oldscale5 = 406.25;
                    c.setDirection5(-.5);
                }
                if(oldscale6 < 393.75){
                    oldscale6 = 393.75;
                    c.setDirection6(.5);
                }else if(oldscale6 > 406.25){
                    oldscale6 = 406.25;
                    c.setDirection6(-.5);
                }
                //Modify the curve position in seaweeds
                c.setScale1(oldscale1);
                c.setScale2(oldscale2);
                c.setScale3(oldscale3);
                c.setScale4(oldscale4);
                c.setScale5(oldscale5);
                c.setScale6(oldscale6);
            }
}

/* Crab Action
 *
 * Minor movement 4: Eye movement
 * Crabs will move left and right in a cycle */
class crabAction implements ActionListener{
	private crab c;
	private boolean eyeright; //  eyes always start at right, and move left
	//Constructor
	crabAction(crab c, boolean er){
		this.c = c; //  Allows modification of crab
		eyeright = er;
	}
	//Action trigger
	public void actionPerformed(ActionEvent e){
		/* mechanism explained:
		 *
		 * If eyes are at the right, then eyes will move to the left upon crab reaching the right end of its path, and vice versa */
		if(eyeright){
			if(c.geteye1x() < 46 && c.geteye2x() < 60){
				c.increment(); //  increments eyes location
			}else{
				c.timerStop(); //  stops once eyes has reached location
			}
		}else{
			//if eyes are on the left
			if(c.geteye1x() > 41 && c.geteye2x() > 55){
				c.decrement();
			}else{
				c.timerStop();
			}
		}
	}
}

//YES THE END OF THE CODE