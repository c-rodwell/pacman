package views;

import javax.imageio.ImageIO;
import javax.swing.*;

import controllers.GameCtrl;
import models.Game;
import enumations.DirectionEnum;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MazeBuilder extends JFrame {
	private JPanel topPanel;
	private DrawingPanel gamePanel;
	private JPanel bottomPanel;
	private int[][] map;
	
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
	
	
	DirectionEnum temp = temp = DirectionEnum.Up;;
	
	public MazeBuilder(int[][] a) {
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
	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel implements KeyListener{
		
		public DrawingPanel() {
			addKeyListener(this);
		}
		int[][] drawingMap = new int[28][30];
		int c;

		@Override
		public void keyPressed(KeyEvent e) { 
			c = e.getKeyCode();
			switch(c) {
			case 38:
				gameControl.updatePacmanDirection(DirectionEnum.Up);
				temp = DirectionEnum.Up;
				break;
			case 39:
				gameControl.updatePacmanDirection(DirectionEnum.Right);
				temp = DirectionEnum.Right; 
				break;
			case 40:
				gameControl.updatePacmanDirection(DirectionEnum.Bottom);
				temp = DirectionEnum.Bottom;
				break;
			case 37:
				gameControl.updatePacmanDirection(DirectionEnum.Left);
				temp = DirectionEnum.Left;
				break;
			}
		}
		@Override
	    public void keyReleased(KeyEvent e) { }
		@Override
	    public void keyTyped(KeyEvent e) { }
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			drawMaze(g2);
			drawPacMan(g2,temp);
			drawGhost(g2);
			
		}
		
		private void drawMaze(Graphics2D g2) {
			for(int i = 0; i < drawingMap.length; i++) {
				for(int j = 0; j < drawingMap[0].length; j++) {
					switch(drawingMap[i][j]) {
						case 0:
							g2.drawImage(wall,i*16,j*16,this);
							break;
						case 1:
							g2.drawImage(blank,i*16,j*16,this);
							break;
						case 2:
							g2.drawImage(food,i*16,j*16,this);
							break;
					}
				}
			}
		}
		private void drawPacMan(Graphics2D g2, DirectionEnum dir) {
			switch(dir) {
			case Up: 
				g2.drawImage(pacman2Up, 100, 100, this); 
				repaint();
				break;
			
			case Right: 
				g2.drawImage(pacman2Right, 100, 100, this);
				repaint();
				break;
			case Bottom: 
				g2.drawImage(pacman2Down, 100, 100, this);
				repaint();
				break;
			case Left: 
				g2.drawImage(pacman2Left, 100, 100, this);
				repaint();
				break;
			}
			
				
			
		}
		private void drawGhost(Graphics2D g2) {
			
		}
		
		public void updateMap(int[][] a) {
			drawingMap = a.clone();
		}
		
		
	}
	
	
	
	
	public static void main(String[] args) {

	
		int[][] a = {
				{0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,2,2,2,2,2,2,2,2,0,1,1,1,0,1,0,1,1,1,0,2,2,2,2,0,0,2,2,2,2,0},
				{0,2,0,0,0,2,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,2,2,2,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,0,0,0,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0},
				{0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0},
				{0,2,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0},
				{0,2,0,0,0,2,2,2,2,0,0,1,1,1,1,1,1,1,1,1,2,0,0,2,2,2,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,0,1,0,0,0,0,0,1,0,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,0,1,0,0,0,0,0,1,0,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,2,2,2,2,0,0,2,1,1,1,0,0,0,0,0,1,0,0,2,2,2,2,0,0,2,2,2,2,0},
				{0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,2,0},
				{0,0,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,2,0},
				{0,2,2,2,2,2,0,0,2,1,1,1,0,0,0,0,0,1,0,0,2,2,2,2,0,0,2,2,2,2,0},
				{0,2,0,0,0,2,0,0,2,0,0,1,0,0,0,0,0,1,0,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,2,0,0,1,0,0,0,0,0,1,0,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,0,0,0,2,2,2,2,0,0,1,1,1,1,1,1,1,1,1,2,0,0,2,2,2,2,0,0,2,0},
				{0,2,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0},
				{0,2,0,0,0,2,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2,0,0,2,0,0,0,0,0,2,0},
				{0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,2,0},
				{0,2,0,0,2,0,0,0,2,0,0,0,0,0,1,0,0,0,0,0,2,0,0,0,0,0,2,0,0,2,0},
				{0,2,0,0,2,0,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,0,0,0,2,0,0,2,0},
				{0,2,0,0,2,0,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,2,2,2,2,0,0,2,0},
				{0,2,0,0,2,0,0,0,2,0,1,1,1,0,1,0,1,1,1,0,2,0,0,2,0,0,2,0,0,2,0},
				{0,2,2,2,2,2,2,2,2,0,1,1,1,0,1,0,1,1,1,0,2,2,2,2,0,0,2,2,2,2,0},
				{0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},		};
		
	
		MazeBuilder bob = new MazeBuilder(a);
	}
}


