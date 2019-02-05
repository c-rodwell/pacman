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

	private boolean vulnerable;
	private boolean dead;
	
	@Override
	public TileEnum collide(TileEnum[][] maze) {
		return null;
	}

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
