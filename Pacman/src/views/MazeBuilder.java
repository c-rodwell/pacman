package views;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.GameCtrl;
import models.Game;
import enumations.DirectionEnum;
import enumations.TileEnum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MazeBuilder extends JFrame {
	
	private static final long serialVersionUID = -7062563352860128122L;
	
	private JPanel topPanel;
	private DrawingPanel gamePanel;
	private JPanel bottomPanel;
	private TileEnum[][] map;
	
	BufferedImage food;
	BufferedImage wall;
	BufferedImage blank;
	BufferedImage redGhost;
	BufferedImage cyanGhost;
	BufferedImage orangeGhost;
	BufferedImage pinkGhost;
	BufferedImage spookedGhost;
	
	GameCtrl gameControl = GameCtrl.getInstance();
	
	private MazeBuilder(TileEnum[][] a) {
		
		importImage();
		
		map = a.clone();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Pac Man");
		setPreferredSize(new Dimension(28*16,37*16+6));
		setLayout(new BorderLayout());

		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(28*16,3*16));
		topPanel.setBackground(new Color(0,0,0));
		topPanel.setVisible(true);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		gamePanel = new DrawingPanel();
		gamePanel.setMinimumSize(new Dimension(28*16,31*16));
		gamePanel.setPreferredSize(new Dimension(28*16,31*16));
		gamePanel.setBackground(new Color(50,50,50));
		gamePanel.setVisible(true);
		gamePanel.updateMap(map);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		getContentPane().add(gamePanel, BorderLayout.CENTER);

		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(28*16,2*16));
		bottomPanel.setBackground(new Color(0,0,0));
		bottomPanel.setVisible(true);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		setResizable(false); 
		setVisible(true);
		pack();	
	}
	private void importImage() {
		try{ 
            food = ImageIO.read(getClass().getResource("/pictures/Food.png"));
            wall = ImageIO.read(getClass().getResource("/pictures/Wall.png"));
            blank = ImageIO.read(getClass().getResource("/pictures/Blank.png"));
            
        }catch(IOException e){
        	System.out.println(e);
        }
	}
	private class DrawingPanel extends JPanel implements KeyListener{
		
		private static final long serialVersionUID = 3752806378765782599L;

		public DrawingPanel() {
			addKeyListener(this);
		}
		TileEnum[][] drawingMap = new TileEnum[28][30];
		int c;
		
		public void keyPressed(KeyEvent e) { 
			c = e.getKeyCode();
			switch(c) {
			case 38:
				gameControl.updatePacmanDirection(DirectionEnum.Up);
				break;
			case 39:
				gameControl.updatePacmanDirection(DirectionEnum.Right);
				break;
			case 40:
				gameControl.updatePacmanDirection(DirectionEnum.Bottom);
				break;
			case 37:
				gameControl.updatePacmanDirection(DirectionEnum.Left);
				break;
			}
			System.out.println(c);
		}
	    public void keyReleased(KeyEvent e) { }
	    public void keyTyped(KeyEvent e) { }
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			for(int i = 0; i < drawingMap.length; i++) {
				for(int j = 0; j < drawingMap[0].length; j++) {
					switch(drawingMap[i][j]) {
						case Wall:
							g2.drawImage(wall,i*16,j*16,this);
							break;
						case Path:
							g2.drawImage(blank,i*16,j*16,this);
							break;
						case Food:
							g2.drawImage(food,i*16,j*16,this);
							break;
						default: break;
					}
				}
			}
		}
		public void updateMap(TileEnum[][] a) {
			drawingMap = a.clone();
		}
		
	}
	
	private static MazeBuilder mazeBuilder;
		
	public static MazeBuilder getInstance(Game game) {
		if (null == mazeBuilder) {
			mazeBuilder = new MazeBuilder(game.getMaze());
		}
		return mazeBuilder;
	}
	
	public void update(Game game) {
		System.out.println("mazeBuilder.update");
	}
	
}
