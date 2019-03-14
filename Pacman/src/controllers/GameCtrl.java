package controllers;

import java.io.IOException;

import enumations.DirectionEnum;
import enumations.GameStateEnum;
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
	
	public void init(boolean b) {
		MazeImportHandler m = new MazeImportHandler();
		if (b) {
			game.setAllLevel(m.getFileNames());
			game.setCurrentLevel(0);
		}
		try {
			m.readFile("./src/maze/" + game.getAllLevel().get(game.getCurrentLevel()));
			game.setPacman(pacmanCtrl.init(m.getPositionPacman(), b ? 3 : game.getPacman().getLives()));
			game.setPositionPacman(m.getPositionPacman());
			game.setGhosts(ghostCtrl.init(m.getPositionGhosts()));
			game.setPositionGhosts(m.getPositionGhosts());
			game.setMaze(m.getMaze());
			game.setAllFood(m.getAllfood());
			game.setFoodEat(0);
			game.setScore(b ? 0 : game.getScore());
		} catch (UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
		setGameState(GameStateEnum.Pause);
		mazeBuilder = MazeBuilder.getInstance(game);
	}

	public void update() {
		pacmanCtrl.movePacman(game);
		ghostCtrl.moveGhosts(game);
		if (updateForGhostCollision()) {
			return;
		}
		if (noMoreFood()){
			nextLevel();
			return;
		}
		mazeBuilder.update(game);
	}
	
	public void setGameState(GameStateEnum e) {
		game.setGameState(e);
	}

	private boolean updateForGhostCollision() {
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
				}
				return true;
			}
		}
		return false;
	}

	private void eatGhost(int ghostNum){
		setGameState(GameStateEnum.ResetGhost);
		mazeBuilder.updateGhostEndpoint(ghostNum);
		mazeBuilder.update(game);
		Ghost g = game.getGhosts()[ghostNum];
		ghostCtrl.beAten(g, game, ghostNum);
	}

	private void pacmanCaptured(){
		System.out.println("\n******************\npacman got captured.\n******************\n");
		setGameState(GameStateEnum.ResetPacman);
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
		mazeBuilder.updatePacmanEndpoint();
		for (int i = 0; i < 4; i++) {
			mazeBuilder.updateGhostEndpoint(i);
		}
		mazeBuilder.update(game);
		pacmanCtrl.init(game.getPositionPacman(), game.getPacman().getLives());
		ghostCtrl.init(game.getPositionGhosts());
		System.out.println("reset current level");
	}

	//create next level - new food, new ghosts, possibly new maze layout
	private void nextLevel() {
		System.out.println("next level");
		game.setCurrentLevel(game.getCurrentLevel() + 1);
		if (game.getAllLevel().size() == game.getCurrentLevel()) {
			youWin();
		} else {
			gameCtrl.init(false);
		}
	}

	//game over - show a game over message and score. can play again from here
	private void gameOver(){
		System.out.println("Game over");
		game.setGameState(GameStateEnum.End);
		System.out.println(mazeBuilder);
		mazeBuilder.update(game);
	}
	
	private void youWin() {
		System.out.println("You Win!");
		game.setGameState(GameStateEnum.Win);
		mazeBuilder.update(game);
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
			gameCtrl.init(true);
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
