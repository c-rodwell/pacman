package controllers;

import java.io.IOException;

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
		MazeImportHandler m = new MazeImportHandler();
		if (null == game.getAllLevel() || game.getAllLevel().length == 0) {
			game.setAllLevel(m.getFileNames());
			game.setCurrentLevel(0);
		}
		if (game.getAllLevel().length == game.getCurrentLevel()) {
			youWin();
			return ;
		}
		try {
			m.readFile("./src/maze/" + game.getAllLevel()[game.getCurrentLevel()]);
			game.setPacman(pacmanCtrl.init(m.getPositionPacman(), 3));
			game.setPositionPacman(m.getPositionPacman());
			game.setGhosts(ghostCtrl.init(m.getPositionGhosts()));
			game.setPositionGhosts(m.getPositionGhosts());
			game.setMaze(m.getMaze());
			game.setAllFood(m.getAllfood());
			game.setFoodEat(0);
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}

		mazeBuilder = MazeBuilder.getInstance(game);
		game.setGameState(GameStateEnum.Pause);
	}

	public void update() {
		pacmanCtrl.movePacman(game);
		ghostCtrl.moveGhosts(game);
		updateForGhostCollision();
		if (noMoreFood()){
			nextLevel();
		}
		mazeBuilder.update(game);
	}
	
	public void setGameState(GameStateEnum e) {
		if (GameStateEnum.End != game.getGameState()) {
			game.setGameState(e);
		}
	}

	private void updateForGhostCollision() {
		Pacman pacman = game.getPacman();
		int xl = pacman.getX();
		int xr = xl + 15;
		int yt = pacman.getY();
		int yb = yt + 15;
		Ghost[] ghosts = game.getGhosts();
		for (int i=0; i< ghosts.length; i++) {
			Ghost g = ghosts[i];
			int x = g.getX();
			int y = g.getY();
			if (x >= xl && x <= xr && y >= yt && y <= yb) {
				if (g.isVulnerable()) {
					eatGhost(i);
				} else {
					pacmanCaptured();
					return;
				}
			}
		}
	}

	public void eatGhost(int ghostNum){
		Ghost g = game.getGhosts()[ghostNum];
		g.setVulnerable(false);
		int[][] position = game.getPositionGhosts();
		g.setX(position[ghostNum][0]);
		g.setY(position[ghostNum][1]);
		g.restoreExpect();
	}

	private void pacmanCaptured(){
		System.out.println("\n******************\npacman got captured.\n******************\n");
		setGameState(GameStateEnum.Pause);
		pacmanCtrl.pacmanIsCaptured();
		int life = game.getPacman().getLives();
		if (life == 0) {
			gameOver();
		} else {
			reset();
		}
	}
	
	private boolean noMoreFood() {
		return game.getAllFood() == game.getFoodEat();
	}

	//pacman lost a life but has lives left:
	//	put pacman and ghosts back to original positions
	//	food remains eaten
	private void reset() {
		pacmanCtrl.init(game.getPositionPacman(), game.getPacman().getLives());
		ghostCtrl.init(game.getPositionGhosts());
		game.setGameState(GameStateEnum.Pause);
		System.out.println("reset current level");
	}

	//create next level - new food, new ghosts, possibly new maze layout
	private void nextLevel() {
		System.out.println("next level");
		game.setCurrentLevel(game.getCurrentLevel() + 1);
		gameCtrl.init();
		game.setGameState(GameStateEnum.Pause);
	}

	//game over - show a game over message and score. can play again from here
	private void gameOver(){
		System.out.println("Game over");
		game.setGameState(GameStateEnum.End);
	}
	
	private void youWin() {
		System.out.println("You Win!");
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
