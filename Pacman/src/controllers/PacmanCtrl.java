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
		return pacman;
	}

	public void movePacman(TileEnum[][] maze) {

		Game game = Game.getInstance();
		DirectionEnum nextDirection = pacman.getNextDirection();
		DirectionEnum currentDirection = pacman.getCurrentDirection();

		//first, try to go in nextDirection if possible (then update currentDirection, set nextDirection back to null)
		if ((nextDirection!= null)&&(game.checkMove(pacman, nextDirection))){
			System.out.println("changed direction to "+nextDirection);
			pacman.setCurrentDirection(nextDirection);
			pacman.setNextDirection(null);	//does this risk race condition since listener also sets nextDirection?
			pacman.move();
		//next try going in currentDirection
		} else if ((currentDirection!= null)&&(game.checkMove(pacman, currentDirection))){
			pacman.move();
		} else {
			System.out.println("hit a wall, can't go any further "+currentDirection);
			//pacman can't move in currentDirection or nextDirection - stay still?
		}
		pacman.eat(maze);
	}
	
	public void pacmanIsCaptured() {
		pacman.setLives(pacman.getLives() - 1);
	}
	
	public void updatePacmanDirection(DirectionEnum nextDirection) {
		pacman.setNextDirection(nextDirection);
		System.out.println(pacman.getNextDirection());
	}
	
	private boolean checkChangeDirection() {
		return false;
	}

//	private boolean checkMove(DirectionEnum direction) {
//		double[] nextPosition = pacman.getNextPosition(direction);
//		if(game.isPassable(x,y))
//
//	}

//	private DirectionEnum getDirectionInput(){
//		return DirectionEnum.Bottom;
//	}
	
}
