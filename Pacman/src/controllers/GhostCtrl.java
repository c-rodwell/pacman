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
	
	public Ghost[] init(int[][] position) {
		for (int i = 0; i < ghosts.length; i++) {
			Ghost g = ghosts[i];
			g.setDead(false);
			g.setCurrentDirection(DirectionEnum.Left);
			g.setSpeed(4);
			g.setX(position[i][0]);
			g.setY(position[i][1]);
			g.restoreExpect();
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
				int r = (int) (Math.random() * 4);
				g.setNextDirection(DirectionEnum.class.getEnumConstants()[r]);
				System.out.println("hit a wall, can't go any further "+currentDirection);
			}
			g.tickDown();
		}
	}

	public void beAten(Ghost g, Game game, int ghostNum) {
		g.setVulnerable(false);
		int[][] position = game.getPositionGhosts();
		g.setX(position[ghostNum][0]);
		g.setY(position[ghostNum][1]);
		g.restoreExpect();
	}

	public DirectionEnum decideMove(Ghost g){
		return DirectionEnum.Bottom;
	}
	
	public void killGhost(int i) {
		ghosts[i].setDead(true);
	}
	
	private boolean checkMove(Ghost g, Game game, DirectionEnum direction) {
		if (TileEnum.Wall == checkFace(g, game, direction)) {
			g.restoreExpect();
			return false;
		} else {
			return true;
		}
	}
	
	private TileEnum checkFace(Ghost g, Game game, DirectionEnum direction) {
		TileEnum[] t = g.checkFace(game, direction);
		if (t[0] == t[1]) {
			return t[0];
		} else {
			return TileEnum.Wall;
		}	
	}
	
}
