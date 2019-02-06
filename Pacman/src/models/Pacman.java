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
* @date 2019-1-30 2:20:39 
* @version V1.0   
*/

public class Pacman extends Agent{

	private int lives;

	@Override
	public TileEnum collide(TileEnum[][] maze) {
		return null;
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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
