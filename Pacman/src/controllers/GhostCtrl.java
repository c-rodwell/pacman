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
		for (Ghost g : ghosts) {
			g.setDead(false);
			g.setCurrentDirection(DirectionEnum.Left);
			g.setSpeed(4);
			g.setX(416);
			g.setY(16);
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
				int r = (int) (Math.random() * 4);
				g.setNextDirection(DirectionEnum.class.getEnumConstants()[r]);
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
		if (TileEnum.Wall == checkFace(g, game, direction)) {
			g.restoreExpect();
			return false;
		} else {
			return true;
		}
	}
	
	private TileEnum checkFace(Ghost g, Game game, DirectionEnum direction) {
		g.preMove(direction);
		int[] p1 = new int[2];
		int[] p2 = new int[2];
		if (direction.equals(DirectionEnum.Bottom)) {
			p1 = g.translateToTile(g.getNextX(), g.getNextY() + 15);
			p2 = g.translateToTile(g.getNextX() + 15, g.getNextY() + 15);
		} else if (direction.equals(DirectionEnum.Right)) {
			p1 = g.translateToTile(g.getNextX() + 15, g.getNextY());
			p2 = g.translateToTile(g.getNextX() + 15, g.getNextY() + 15);
		} else if (direction.equals(DirectionEnum.Up)) {
			p1 = g.translateToTile(g.getNextX(), g.getNextY());
			p2 = g.translateToTile(g.getNextX() + 15, g.getNextY());
		} else if (direction.equals(DirectionEnum.Left)) {
			p1 = g.translateToTile(g.getNextX(), g.getNextY());
			p2 = g.translateToTile(g.getNextX(), g.getNextY() + 15);
		}
		TileEnum t1 = game.getMaze()[p1[0]][p1[1]];
		TileEnum t2 = game.getMaze()[p2[0]][p2[1]];
		if (t1 == t2) {
			return t1;
		} else {
			return TileEnum.Wall;
		}	
	}
	
}
