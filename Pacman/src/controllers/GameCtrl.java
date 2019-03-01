package controllers;

import enumations.DirectionEnum;
import enumations.GameStateEnum;
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

	private static GameCtrl gameCtrl;
	
	public static GameCtrl getInstance() {
		if (null == gameCtrl) {
			gameCtrl = new GameCtrl();
		}
		return gameCtrl;
	}
	
	private PacmanCtrl pacmanCtrl = PacmanCtrl.getInstance();
	private GhostCtrl ghostCtrl = GhostCtrl.getInstance();
	private MazeBuilder mazeBuilder;
	
	private Game game = Game.getInstance();
	
	public void init() {
		game.setPacman(pacmanCtrl.init());
		game.setGhosts(ghostCtrl.init());
		game.setMaze(new TileEnum[][]{
				{TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall,TileEnum.Wall,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Food,TileEnum.Wall},
				{TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Wall,TileEnum.Path,TileEnum.Path,TileEnum.Path,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall,TileEnum.Wall}});
		//more
		mazeBuilder = MazeBuilder.getInstance(game);
		game.setAllFood(244);
		game.setGameState(GameStateEnum.Pause);
	}
	
	public void update() {
		pacmanCtrl.movePacman(game);
		ghostCtrl.moveGhosts(game);
		if (isPacmanCaptured(game.getPacman(), game.getGhosts())) {
			setGameState(GameStateEnum.Pause);
			pacmanCtrl.pacmanIsCaptured();
			if (game.getPacman().getLives() == 0) {
				gameOver();
			} else {
				reset();
			}
		} else if (noMoreFood()) {
			nextLevel();
		}
		mazeBuilder.update(game);
	}
	
	public void setGameState(GameStateEnum e) {
		if (GameStateEnum.End != game.getGameState()) {
			game.setGameState(e);
		}
	}
	
	private boolean isPacmanCaptured(Pacman pacman, Ghost[] ghosts) {
		int xl = pacman.getX();
		int xr = xl + 15;
		int yt = pacman.getY();
		int yb = yt + 15;
		for (Ghost g : ghosts) {
			int x = g.getX();
			int y = g.getY();
			if (x >= xl && x <= xr && y >= yt && y <= yb) {
				System.out.println("\n******************\npacman got captured.\n******************\n");
				return true;
			}
		}
		return false;
	}
	
	private boolean noMoreFood() {
		return game.getAllFood() == game.getFoodEat();
	}

	//pacman lost a life but has lives left:
	//	put pacman and ghosts back to original positions
	//	food remains eaten
	private void reset() {
		System.out.println("reset current level");
	}

	//create next level - new food, new ghosts, possibly new maze layout
	private void nextLevel() {
		System.out.println("next level");
		game.setGameState(GameStateEnum.End);
	}

	//game over - show a game over message and score. can play again from here
	private void gameOver(){
		System.out.println("Game over");
		game.setGameState(GameStateEnum.End);
	}
	
	public void updatePacmanDirection(DirectionEnum direction) {
		pacmanCtrl.updatePacmanDirection(direction);
	}
	
	@Override
	public void run() {
		if (GameStateEnum.Running == game.getGameState()) {
			update();
			printStatus();
		}
		//System.out.println(game.getGhosts()[0].isDead());
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

	//debugging helper - print out details of game state
	private void printStatus(){
		System.out.println("\n______ game state: ______");
		System.out.println(game.getDebugString());
	}
	
}
