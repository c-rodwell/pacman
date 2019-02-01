package controllers;

import enumations.TileEnum;
import models.Game;
import models.Ghost;
import models.Pacman;
import views.MazeBuilder;

/**   
* @Title: GameCtrl.java 
* @Package controllers 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午5:22:37 
* @version V1.0   
*/

public class GameCtrl {
	
	private GameCtrl() {}
	
	private static GameCtrl gameCtrl;
	
	public static GameCtrl getInstance() {
		if (null == gameCtrl) {
			gameCtrl = new GameCtrl();
		}
		return gameCtrl;
	}
	
	private PacmanCtrl pacmanCtrl = PacmanCtrl.getInstance();
	private GhostCtrl ghostCtrl = GhostCtrl.getInstance();
	private MazeBuilder mazeBuilder = MazeBuilder.getInstance();
	
	private Game game = Game.getInstance();
	
	public void init() {
		game.setPacman(pacmanCtrl.init());
		game.setGhosts(ghostCtrl.init());
		//more
	}
	
	public void update() {
		pacmanCtrl.movePacman(game.getMaze());
		ghostCtrl.moveGhosts();
		if (isPacmanCaptured(game.getPacman(), game.getGhosts())) {
			pacmanCtrl.pacmanIsCaptured();
			if (game.getPacman().getLives() == 0) {
				//game over
			} else {
				reset();
			}
		} else if (noMoreFood(game.getMaze())) {
			nextLevel();
		}
		mazeBuilder.update(game);
	}
	
	private boolean isPacmanCaptured(Pacman pacman, Ghost[] ghosts) {
		return false;
	}
	
	private boolean noMoreFood(TileEnum[][] maze) {
		return game.getAllFood() == game.getFoodEat();
	}
	
	private void reset() {
		
	}
	
	private void nextLevel() {
		
	}
	
}
