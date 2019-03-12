package controllers;

import enumations.DirectionEnum;
import enumations.TileEnum;
import models.Game;
import models.Ghost;
import models.Pacman;

/**
 * @Title: GhostCtrl.java
 * @Package controllers
 * @Description:
 * @author Pengbin Li
 * @date 2019年1月30日 下午5:22:53
 * @version V1.0
 */

public class GhostCtrl {

	private GhostCtrl() {
	}

	private static GhostCtrl ghostCtrl;

	public static GhostCtrl getInstance() {
		if (null == ghostCtrl) {
			ghostCtrl = new GhostCtrl();
		}
		return ghostCtrl;
	}

	private Ghost[] ghosts = Ghost.getInstance();

	public Ghost[] init(int[][] position) {
		for (int i = 0; i < ghosts.length; i++) {
			Ghost g = ghosts[i];
			g.setCurrentDirection(DirectionEnum.Left);
			g.setSpeed(4);
			g.setX(position[i][0]);
			g.setY(position[i][1]);
			g.restoreExpect();
		}
		return ghosts;
	}

	public void moveGhosts(Game game) {
		for (int i = 0; i < ghosts.length; i++) {
			Ghost g = ghosts[i];
			DirectionEnum nextDirection = i == 0 ? moveGhostsDirectional(game, g) : g.getNextDirection();
			DirectionEnum currentDirection = g.getCurrentDirection();
			if (nextDirection != null && checkMove(g, game, nextDirection)) {
				System.out.println("changed direction to " + nextDirection);
				g.setCurrentDirection(nextDirection);
				g.setNextDirection(null); // does this risk race condition since
											// listener also sets nextDirection?
				g.setPosition();
			} else if (currentDirection != null && checkMove(g, game, currentDirection)) {
				g.setPosition();
			} else {
				int r = (int) (Math.random() * 4);
				g.setNextDirection(DirectionEnum.class.getEnumConstants()[r]);
				System.out.println("hit a wall, can't go any further " + currentDirection);
			}
			g.tickDown();
		}
	}

	public void beAten(Ghost g, Game game, int ghostNum) {
		g.setVulnerable(false);
		g.setSpeed(4);
		int[][] position = game.getPositionGhosts();
		g.setX(position[ghostNum][0]);
		g.setY(position[ghostNum][1]);
		g.restoreExpect();
	}

	public DirectionEnum decideMove(Ghost g) {
		return DirectionEnum.Bottom;
	}

	private boolean checkMove(Ghost g, Game game, DirectionEnum direction) {
		if (TileEnum.Wall == checkFace(g, game, direction)) {
			g.restoreExpect();
			return false;
		} else {
			return true;
		}
	}

	private TileEnum checkFace(Ghost g, Game game, DirectionEnum direction) {
		TileEnum[] t = g.checkFace(game, direction);
		if (t[0] == t[1]) {
			return t[0];
		} else {
			return TileEnum.Wall;
		}
	}

	// return the manhattanDistance between pacman to a ghost
	private int manhattanDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private DirectionEnum moveGhostsDirectional(Game game, Ghost g) {
		// Get pacman position
		Pacman pacman = Pacman.getInstance();
		int pacManX = pacman.getX();
		int pacManY = pacman.getY();
		boolean vul = g.isVulnerable();

		// Calculate distances to pacman for different next directions
		int[] distances = new int[4];
		int optimalDistance;

		if (vul) {
			optimalDistance = Integer.MIN_VALUE;
		} else {
			optimalDistance = Integer.MAX_VALUE;
		}

		for (int i = 0; i < 4; i++) {
			if (checkMove(g, game, DirectionEnum.class.getEnumConstants()[i])) {
				distances[i] = manhattanDistance(pacManX, pacManY, g.getNextX(), g.getNextY());
				if (vul) {
					optimalDistance = Math.max(optimalDistance, distances[i]);
				} else {
					optimalDistance = Math.min(optimalDistance, distances[i]);
				}
				g.restoreExpect();
			} else {
				if (vul) {
					distances[i] = Integer.MIN_VALUE;
				} else {
					distances[i] = Integer.MAX_VALUE;
				}

			}
		}

		// Check if currentDirection is optimal
		DirectionEnum currentDirection = g.getCurrentDirection();
		boolean ifcontinue = false;
		for (int i = 0; i < 4; i++) {
			if (distances[i] == optimalDistance && DirectionEnum.class.getEnumConstants()[i] == currentDirection) {
				ifcontinue = true;
			}
		}

		// If current already optimal, continue with current direction,
		// otherwise randomize direction
		DirectionEnum nextDirection;

		if (ifcontinue) {
			nextDirection = currentDirection;
		} else {
			int numMin = 0;
			int numOther = 0;
			for (int i = 0; i < 4; i++) {
				if (distances[i] == optimalDistance) {
					numMin++;
				} else if (distances[i] < Integer.MAX_VALUE && distances[i] > Integer.MIN_VALUE) {
					numOther++;
				}
			}

			// Calculate next direction distribution
			double[] distribution = new double[4];
			double distributionSum = 0;
			for (int i = 0; i < 4; i++) {
				if (distances[i] == optimalDistance) {
					distribution[i] = 0.7 / (double) numMin;
					distributionSum += 0.7 / (double) numMin;
				} else if (distances[i] < Integer.MAX_VALUE && distances[i] > Integer.MIN_VALUE) {
					distribution[i] = 0.3 / (double) numOther;
					distributionSum += 0.3 / (double) numOther;
				} else {
					distribution[i] = 0;
				}
			}

			// Normalize distribution
			for (int i = 0; i < 4; i++) {
				distribution[i] /= distributionSum;
			}

			// Randomize over the distribution
			double r = Math.random();
			double base = 0.0;
			int direction = 0;
			for (int i = 0; i < 4; i++) {
				base += distribution[i];
				if (r <= base) {
					direction = i;
					break;
				}
			}

			nextDirection = DirectionEnum.class.getEnumConstants()[direction];
		}

		return nextDirection;
	}

}
