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

	public String getDebugString(){
		String gameString = "Game: "
				+"all food: "+getAllFood()
				+", eaten food: "+getFoodEat();

		String pacManString = "Pacman: "+pacman.getDebugString();
		String ghostString = "";
		for (int i=0; i<ghosts.length; i++){
			ghostString+="Ghost "+i+": "+ghosts[i].getDebugString()+"\n";
		}
		//return gameString + "\n"+ pacManString + "\n"+ ghostString;
		return pacManString;
	}

	public boolean checkMove(Agent agent, DirectionEnum direction) {
		double[] nextPosition = agent.getNextPosition(direction);
		return (isPassable(nextPosition[0], nextPosition[1]));
	}

	//check if pac man can go to that place
	//is it as simple as checking if position is a wall, or do we need to enforce min distance from a wall?
	//in tunnel of 1 square width, pacman shouldn't be able to move toward either wall
	public boolean isPassable(double x, double y){
		int[] intPosition = doubleToIntPosition(x, y);
		return maze[intPosition[0]][intPosition[1]] != TileEnum.Wall;
	}

	//transate the double position to closest integer position in the maze
	//is this the right way up? not sure which way x and y go in the array
	public int[] doubleToIntPosition(double x, double y){
		int[] intPoint = {(int) x/16, (int) y/16};
		return intPoint;
	}
	
}