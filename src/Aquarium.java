import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Aquarium extends Frame implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6690475893594141255L;
	
	Image aquariumImage, memoryImage;
	Image icon;
	Graphics memoryGraphics;
	Image[] fishImages = new Image[4];
	MediaTracker tracker;
	Thread thread;
	int numberFish = 12;
	int sleepTime = 110;
	Vector<Fish> fishes = new Vector<>();
	boolean runOK = true;
	
	Aquarium(){
		setTitle("The Aquarium");
		tracker = new MediaTracker(this);
		icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("icon\\goldfish.png"));
		
		addDefaultComponents();
		
		setIconImage(icon);
		setSize(aquariumImage.getWidth(this), aquariumImage.getHeight(this));
		setResizable(false);
		setVisible(true);
		
		memoryImage = createImage(getSize().width, getSize().height);
		memoryGraphics = memoryImage.getGraphics();
		
		thread = new Thread(this);
		thread.start();
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(
					WindowEvent windowEvent) {
				runOK = false;
				System.exit(0);
				}
			}
		);
	}
	public void addDefaultComponents() {
		fishImages[0] = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("fishes\\fish5.gif"));
		tracker.addImage(fishImages[0], 0);
		
		fishImages[1] = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("fishes\\fish6.gif"));
		tracker.addImage(fishImages[1], 0);
		
		aquariumImage = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource("backgrounds\\bg6.jpg"));
		tracker.addImage(aquariumImage, 0);
		
		try {
			tracker.waitForID(0);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		
	}
	
	public void run() {
		for(int loopIndex = 0; loopIndex < numberFish; loopIndex++) {
			
			Rectangle edges = new Rectangle(0 + getInsets().left, 0 + getInsets().top, getSize().width -
				(getInsets().left + getInsets().right), getSize().height - 
				(getInsets().top + getInsets().bottom));
					
			fishes.add(new Fish(fishImages[0], fishImages[1], edges, this));
			try {
				Thread.sleep(50);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		Fish fish;
		
		while(runOK) {
			for(int index = 0; index < numberFish; index++) {
				fish = (Fish)fishes.elementAt(index);
				fish.swim();
			}
			try {
				Thread.sleep(sleepTime);
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
			repaint();
		}
	}

	public void update(Graphics g) {
		memoryGraphics.drawImage(aquariumImage, 0, 0, this);
		
		for(int index = 0; index < numberFish; index++) {
			((Fish)fishes.elementAt(index)).drawFishImage(memoryGraphics);
		}
		g.drawImage(memoryImage, 0, 0, this);
	}

	public static void main(String[] args) {
		 new Aquarium();
	}

}

