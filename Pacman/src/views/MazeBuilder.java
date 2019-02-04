package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
//hello
public class MazeBuilder extends JFrame {
	private JPanel topPanel;
	private DrawingPanel gamePanel;
	private JPanel bottomPanel;
	private int[][] map;
	
	BufferedImage food;
	BufferedImage wall;
	BufferedImage blank;
	
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
	private class DrawingPanel extends JPanel{
		
		int[][] drawingMap = new int[28][30];
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
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


