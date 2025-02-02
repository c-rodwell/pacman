package controllers;

import enumations.DirectionEnum;
import enumations.TileEnum;
import models.Game;
import models.Ghost;
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
	
	public Pacman init(int[] position, int life) {
		pacman.setLives(life);
		pacman.setSpeed(4);
		pacman.setCurrentDirection(DirectionEnum.Right);
		pacman.setX(position[0]);
		pacman.setY(position[1]);
		pacman.restoreExpect();
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
	}
	
	public void pacmanIsCaptured() {
		pacman.setLives(pacman.getLives() - 1);
	}
	
	public void updatePacmanDirection(DirectionEnum nextDirection) {
		pacman.setNextDirection(nextDirection);
	}
	
	private boolean checkMove(Game game, DirectionEnum direction) {
		if (TileEnum.Wall == checkFace(game, direction)) {
			pacman.restoreExpect();
			return false;
		} else {
			return true;
		}
	}
	
	private TileEnum checkFace(Game game, DirectionEnum direction) {
		TileEnum[] t = pacman.checkFace(game, direction);
		if (t[0] == t[1]) {
			if (t[0] != TileEnum.Wall) {
				int[] p = pacman.translateToTile(pacman.getNextX() + 7, pacman.getNextY() + 7);
				eatTile(game, p[0], p[1]);
			}
			return t[0];
		} else {
			return TileEnum.Wall;
		}	
	}

	private void eatTile(Game game, int x, int y){
	    TileEnum tileToEat = game.getMaze()[x][y];
        if (tileToEat == TileEnum.Food) {
            game.getMaze()[x][y] = TileEnum.Path;
            game.setFoodEat(game.getFoodEat() + 1);
            game.addScore(1);
        } else if (tileToEat == TileEnum.Power){
            game.getMaze()[x][y] = TileEnum.Path;
            for (Ghost ghost : game.getGhosts()){
                ghost.setVulnerable(true);
            }
        }
    }
	
}
