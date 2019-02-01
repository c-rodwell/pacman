package controllers;

import java.util.Calendar;

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

public class GameCtrl implements Runnable {
	
	private GameCtrl() {}
	
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
	
	@Override
	public void run() {
		update();
		System.out.println(Calendar.getInstance().getTime().toString());
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		GameCtrl gameCtrl = new GameCtrl();
		while (true) {
			gameCtrl.init();
			Thread thread = new Thread(gameCtrl);
			while (true) {
				thread.run();
				try {
					thread.sleep(33);
				} catch (InterruptedException e) {}
			}
		}
	}
	
}
