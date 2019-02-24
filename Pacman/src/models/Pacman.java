package models;

import enumations.TileEnum;

/**   
* @Title: Pacman.java 
* @Package model 
* @Description:  
* @author Pengbin Li   
* @date 2019-1-30 2:20:39 
* @version V1.0   
*/

public class Pacman extends Agent {

	private int lives;
	
	public TileEnum eat(TileEnum[][] maze) {
		return null;
	}

	private Pacman() {
		speed = 1;

		//hardcode position corresponding to 1,1 in the hardcoded maze, which is a food tile
		//TODO get pacman start position from the maze layout
		x = 16;
		y = 16;
	}
	
	private static Pacman pacman;
	
	public static Pacman getInstance() {
		if (null == pacman) {
			pacman = new Pacman();
		}
		return pacman;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public String getDebugString(){
		return "lives: "+lives
				+", x: "+x
				+", y: "+y
				+", speed: "+speed
				+", currentDirection: "+currentDirection
				+", nextDirection: "+nextDirection;
	}
	
}
