package controllers;

import enumations.DirectionEnum;
import enumations.TileEnum;
import models.Game;
import models.Pacman;

/**   
* @Title: PacmanCtrl.java 
* @Package controllers 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午5:22:45 
* @version V1.0   
*/

public class PacmanCtrl {

	private PacmanCtrl() {}
	
	private static PacmanCtrl pacmanCtrl;
	
	public static PacmanCtrl getInstance() {
		if (null == pacmanCtrl) {
			pacmanCtrl = new PacmanCtrl();
		}
		return pacmanCtrl;
	}
	
	private Pacman pacman = Pacman.getInstance();
	
	public Pacman init() {
		//set pacman
		pacman.setSpeed(1);
		pacman.setCurrentDirection(DirectionEnum.Right);
		pacman.setX(16);
		pacman.setY(16);
		return pacman;
	}
	
	public void movePacman(Game game) {
		DirectionEnum nextDirection = pacman.getNextDirection();
		DirectionEnum currentDirection = pacman.getCurrentDirection();
		if (nextDirection != null && checkMove(game, nextDirection)) {
			System.out.println("changed direction to " + nextDirection);
			pacman.setCurrentDirection(nextDirection);
			pacman.setNextDirection(null);	//does this risk race condition since listener also sets nextDirection?
			pacman.setPosition();
		} else if (currentDirection != null && checkMove(game, currentDirection)) {
			System.out.println("current direction is " + currentDirection);
			pacman.setPosition();
		} else {
			System.out.println("hit a wall, can't go any further "+currentDirection);
		}
		pacman.eat(game.getMaze());
	}
	
	public void pacmanIsCaptured() {
		pacman.setLives(pacman.getLives() - 1);
	}
	
	public void updatePacmanDirection(DirectionEnum nextDirection) {
		pacman.setNextDirection(nextDirection);
	}
	
	private boolean checkMove(Game game, DirectionEnum direction) {
		pacman.preMove(direction);
		int[] p1 = new int[2];
		int[] p2 = new int[2];
		if (direction.equals(DirectionEnum.Bottom)) {
			p1 = pacman.translateToTile(pacman.getNextX(), pacman.getNextY() + 15);
			p2 = pacman.translateToTile(pacman.getNextX() + 15, pacman.getNextY() + 15);
		} else if (direction.equals(DirectionEnum.Right)) {
			p1 = pacman.translateToTile(pacman.getNextX() + 15, pacman.getNextY());
			p2 = pacman.translateToTile(pacman.getNextX() + 15, pacman.getNextY() + 15);
		} else if (direction.equals(DirectionEnum.Up)) {
			p1 = pacman.translateToTile(pacman.getNextX(), pacman.getNextY());
			p2 = pacman.translateToTile(pacman.getNextX() + 15, pacman.getNextY());
		} else if (direction.equals(DirectionEnum.Left)) {
			p1 = pacman.translateToTile(pacman.getNextX(), pacman.getNextY());
			p2 = pacman.translateToTile(pacman.getNextX(), pacman.getNextY() + 15);
		}
		boolean res = game.getMaze()[p1[0]][p1[1]] != TileEnum.Wall
				&& game.getMaze()[p2[0]][p2[1]] != TileEnum.Wall;
		if (!res) {
			pacman.restoreExpect();
		}
		return res;
	}
	
}
