package models;

import enumations.DirectionEnum;
import enumations.TileEnum;
import interfaces.Collidable;
import interfaces.Movable;

/**   
* @Title: Ghost.java 
* @Package models 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午4:37:01 
* @version V1.0   
*/

public class Ghost extends Agent {

	private double x;
	private double y;
	private double speed;
	private DirectionEnum currentDirection;
	private DirectionEnum nextDirection;
	private boolean vulnerable;
	private boolean dead;
	
	@Override
	public TileEnum collide(TileEnum[][] maze) {
		return null;
	}

	@Override
	public void move() {

	}
	
//	public double getX() {
//		return x;
//	}
//
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}
//
//	public double getSpeed() {
//		return speed;
//	}
//
//	public void setSpeed(double speed) {
//		this.speed = speed;
//	}
//
//	public DirectionEnum getCurrentDirection() {
//		return currentDirection;
//	}
//
//	public void setCurrentDirection(DirectionEnum currentDirection) {
//		this.currentDirection = currentDirection;
//	}
//
//	public DirectionEnum getNextDirection() {
//		return nextDirection;
//	}
//
//	public void setNextDirection(DirectionEnum nextDirection) {
//		this.nextDirection = nextDirection;
//	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
		this.vulnerable = vulnerable;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
}
