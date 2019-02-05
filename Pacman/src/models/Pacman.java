package models;

import enumations.DirectionEnum;
import enumations.TileEnum;
import interfaces.Collidable;
import interfaces.Movable;

/**   
* @Title: Pacman.java 
* @Package model 
* @Description:  
* @author Pengbin Li   
* @date 2019骞�1鏈�30鏃� 涓嬪崍2:20:39 
* @version V1.0   
*/

public class Pacman implements Movable, Collidable {

	private double x;
	private double y;
	private double speed;
	private DirectionEnum currentDirection;
	private DirectionEnum nextDirection;
	private int lives;

	@Override
	public TileEnum collide(TileEnum[][] maze) {
		return null;
	}

	@Override
	public void move() throws Exception {
		switch (currentDirection){
			case Bottom: y -= speed;
			break;
			case Up: y += speed;
			break;
			case Left: x -= speed;
			break;
			case Right: x += speed;
			break;
			default:	//throw new Exception("direction is invalid");
		}
	}
	
	public TileEnum eat(TileEnum[][] maze) {
		return null;
	}

	private Pacman() {}
	
	private static Pacman pacman;
	
	public static Pacman getInstance() {
		if (null == pacman) {
			pacman = new Pacman();
		}
		return pacman;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public DirectionEnum getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(DirectionEnum currentDirection) {
		this.currentDirection = currentDirection;
	}

	public DirectionEnum getNextDirection() {
		return nextDirection;
	}

	public void setNextDirection(DirectionEnum nextDirection) {
		this.nextDirection = nextDirection;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
