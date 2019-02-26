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
	BufferedImage pacman1;
	BufferedImage pacman2Down;
	BufferedImage pacman2Left;
	BufferedImage pacman2Up;
	BufferedImage pacman2Right;
	BufferedImage pacman3Down;
	BufferedImage pacman3Left;
	BufferedImage pacman3Up;
	BufferedImage pacman3Right;
	
	GameCtrl gameControl = GameCtrl.getInstance();
	Game game;
	
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
            redGhost = ImageIO.read(getClass().getResource("/pictures/Red.png"));
            cyanGhost = ImageIO.read(getClass().getResource("/pictures/Cyan.png"));
            pinkGhost = ImageIO.read(getClass().getResource("/pictures/Pink.png"));
            orangeGhost = ImageIO.read(getClass().getResource("/pictures/Orange.png"));
            spookedGhost = ImageIO.read(getClass().getResource("/pictures/Spooked.png"));
            pacman1 = ImageIO.read(getClass().getResource("/pictures/pacman1.png"));
            pacman2Down = ImageIO.read(getClass().getResource("/pictures/pacman2Down.png"));
            pacman2Left = ImageIO.read(getClass().getResource("/pictures/pacman2Left.png"));
            pacman2Up = ImageIO.read(getClass().getResource("/pictures/pacman2Up.png"));
            pacman2Right = ImageIO.read(getClass().getResource("/pictures/pacman2Right.png"));
            pacman3Down = ImageIO.read(getClass().getResource("/pictures/pacman3Down.png"));
            pacman3Left = ImageIO.read(getClass().getResource("/pictures/pacman3Left.png"));
            pacman3Up = ImageIO.read(getClass().getResource("/pictures/pacman3Up.png"));
            pacman3Right = ImageIO.read(getClass().getResource("/pictures/pacman3Right.png"));
            
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
		}
		
	    public void keyReleased(KeyEvent e) {}
	    
	    public void keyTyped(KeyEvent e) {}
		
	    public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			drawMaze(g2);
			drawPacMan(g2, game.getPacman().getCurrentDirection());
			drawGhost(g2);
			
		}
		
		private void drawMaze(Graphics2D g2) {
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
		
		private void drawPacMan(Graphics2D g2, DirectionEnum dir) {
			switch(dir) {
			case Up: 
				g2.drawImage(pacman2Up, game.getPacman().getX(), game.getPacman().getY(), this); 
				repaint();
				break;
			case Right: 
				g2.drawImage(pacman2Right, game.getPacman().getX(), game.getPacman().getY(), this);
				repaint();
				break;
			case Bottom: 
				g2.drawImage(pacman2Down, game.getPacman().getX(), game.getPacman().getY(), this);
				repaint();
				break;
			case Left: 
				g2.drawImage(pacman2Left, game.getPacman().getX(), game.getPacman().getY(), this);
				repaint();
				break;
			}
		}
		
		private void drawGhost(Graphics2D g2) {
			
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
		this.game = game;
		this.repaint();
	}
	
}
