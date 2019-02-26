package controllers;

import enumations.DirectionEnum;
import enumations.TileEnum;
import models.Game;
import models.Ghost;

/**   
* @Title: GhostCtrl.java 
* @Package controllers 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午5:22:53 
* @version V1.0   
*/

public class GhostCtrl {

	private GhostCtrl() {}
	
	private static GhostCtrl ghostCtrl;
	
	public static GhostCtrl getInstance() {
		if (null == ghostCtrl) {
			ghostCtrl = new GhostCtrl();
		}
		return ghostCtrl;
	}
		
	private Ghost[] ghosts = Ghost.getInstance();
	
	public Ghost[] init() {
		for (Ghost g : ghosts) {
			g.setDead(false);
			g.setCurrentDirection(DirectionEnum.Up);
			g.setSpeed(1);
			//more
		}
		return ghosts;
	}
	
	public void moveGhosts(Game game) {
		for (Ghost g : ghosts) {
			DirectionEnum nextDirection = g.getNextDirection();
			DirectionEnum currentDirection = g.getCurrentDirection();
			if (nextDirection != null && checkMove(g, game, nextDirection)) {
				System.out.println("changed direction to " + nextDirection);
				g.setCurrentDirection(nextDirection);
				g.setNextDirection(null);	//does this risk race condition since listener also sets nextDirection?
				g.setPosition();
			} else if (currentDirection != null && checkMove(g, game, currentDirection)) {
				g.setPosition();
			} else {
				System.out.println("hit a wall, can't go any further "+currentDirection);
			}
		}
	}

	public DirectionEnum decideMove(Ghost g){
		return DirectionEnum.Bottom;
	}
	
	public void killGhost(int i) {
		ghosts[i].setDead(true);
	}
	
	private boolean checkMove(Ghost g, Game game, DirectionEnum direction) {
		g.preMove(direction);
		int[] p = g.translateToTile(g.getNextX(), g.getNextY());
		boolean res = game.getMaze()[p[0]][p[1]] != TileEnum.Wall;
		if (!res) {
			g.restoreExpect();
		}
		return res;
	}
	
}
