package controllers;

import enumations.DirectionEnum;
import enumations.TileEnum;
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
		return pacman;
	}
	
	public void movePacman(TileEnum[][] maze) {
		pacman.setNextDirection(getDirectionInput());
		if (checkChangeDirection()) {
			//changeDirection
		}
		//pacman.move();
		if (!checkMove()) {
			//reset pacman to legal position
		}
		pacman.eat(maze);
	}
	
	public void pacmanIsCaptured() {
		pacman.setLives(pacman.getLives() - 1);
	}
	
	public void updatePacmanDirection(DirectionEnum nextDirection) {
		pacman.setNextDirection(nextDirection);
	}
	
	private boolean checkChangeDirection() {
		return false;
	}
	
	private boolean checkMove() {
		return false;
	}

	private DirectionEnum getDirectionInput(){
		return DirectionEnum.Bottom;
	}
	
}
