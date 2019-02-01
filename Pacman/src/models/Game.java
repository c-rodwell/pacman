package models;

import enumations.DirectionEnum;
import enumations.GameStateEnum;
import enumations.TileEnum;

/**   
* @Title: Game.java 
* @Package models 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午6:19:47 
* @version V1.0   
*/

public class Game {

	private DirectionEnum inputDirection;
	private int foodEat;
	private int allFood;
	private TileEnum[][] maze;
	private Pacman pacman;
	private Ghost[] ghosts;
	private GameStateEnum gameState;
	
	private Game() {}
	
	private static Game game;
	
	public static Game getInstance() {
		if (null == game) {
			game = new Game();
		}
		return game;
	}
	
	public DirectionEnum getInputDirection() {
		return inputDirection;
	}

	public void setInputDirection(DirectionEnum inputDirection) {
		this.inputDirection = inputDirection;
	}

	public int getFoodEat() {
		return foodEat;
	}

	public void setFoodEat(int foodEat) {
		this.foodEat = foodEat;
	}

	public int getAllFood() {
		return allFood;
	}

	public void setAllFood(int allFood) {
		this.allFood = allFood;
	}

	public TileEnum[][] getMaze() {
		return maze;
	}

	public void setMaze(TileEnum[][] maze) {
		this.maze = maze;
	}

	public Pacman getPacman() {
		return pacman;
	}

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	public Ghost[] getGhosts() {
		return ghosts;
	}

	public void setGhosts(Ghost[] ghosts) {
		this.ghosts = ghosts;
	}

	public GameStateEnum getGameState() {
		return gameState;
	}

	public void setGameState(GameStateEnum gameState) {
		this.gameState = gameState;
	}
	
}
