package views;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.GameCtrl;
import models.Game;
import models.Ghost;
import enumations.DirectionEnum;
import enumations.GameStateEnum;
import enumations.TileEnum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MazeBuilder extends JFrame {

	private static final long serialVersionUID = -7062563352860128122L;

	Game game;
	private ScorePanel topPanel;
	private DrawingPanel gamePanel;
	private JPanel bottomPanel;
	private TileEnum[][] map;
	String soundName = "./src/pictures/pacman_chomp.wav";
	AudioInputStream audioInputStream;
	Clip clip;

	BufferedImage number;
	BufferedImage score;
	BufferedImage gameover;
	BufferedImage youwin;

	BufferedImage food;
	BufferedImage power;
	BufferedImage wall;
	BufferedImage gate;
	BufferedImage blank;
	BufferedImage redGhost;
	BufferedImage cyanGhost;
	BufferedImage orangeGhost;
	BufferedImage pinkGhost;
	BufferedImage spookedGhost;
	BufferedImage pacman1;
	BufferedImage pacman2Down;
	BufferedImage pacman2Left;
	BufferedImage pacman2Up;
	BufferedImage pacman2Right;
	BufferedImage pacman3Down;
	BufferedImage pacman3Left;
	BufferedImage pacman3Up;
	BufferedImage pacman3Right;

	BufferedImage[] pacmanUp = new BufferedImage[4];
	BufferedImage[] pacmanRight = new BufferedImage[4];
	BufferedImage[] pacmanDown = new BufferedImage[4];
	BufferedImage[] pacmanLeft = new BufferedImage[4];

	int imageIndex;
	int[] scoreImageIndex = new int[] { 1, 18, 36, 53, 71, 88, 106, 123, 141, 158 };
	GameCtrl gameControl = GameCtrl.getInstance();

	DirectionEnum temp = DirectionEnum.Up;

	private int pacmanEndX;
	private int pacmanEndY;
	private int[] ghostsEndX = new int[4];
	private int[] ghostsEndY = new int[4];
	private boolean[] resetGhost = new boolean[4];
	private int animatePacmanStep = 0;
	private final int animatePacmanStepMin = 0;
	private final int animatePacmanStepMax = 50;
	int gameState = 1;

	public MazeBuilder(TileEnum[][] a) {
		importImage();

		imageIndex = 0;
		// map = a.clone();
		map = a;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Pac Man");
		setPreferredSize(new Dimension(28 * 16, 37 * 16 + 6));
		setLayout(new BorderLayout());
		topPanel = new ScorePanel();
		topPanel.setPreferredSize(new Dimension(28 * 16, 3 * 16));
		topPanel.setBackground(new Color(0, 0, 0));
		topPanel.setVisible(true);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		gamePanel = new DrawingPanel();
		gamePanel.setMinimumSize(new Dimension(28 * 16, 31 * 16));
		gamePanel.setPreferredSize(new Dimension(28 * 16, 31 * 16));
		gamePanel.setBackground(new Color(50, 50, 50));
		gamePanel.setVisible(true);
		gamePanel.updateMap(map);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		getContentPane().add(gamePanel, BorderLayout.CENTER);

		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(28 * 16, 2 * 16));
		bottomPanel.setBackground(new Color(0, 0, 0));
		bottomPanel.setVisible(true);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		setResizable(false);
		setVisible(true);
		pack();
	}

	private void importImage() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			clip = AudioSystem.getClip();

			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			System.out.println(e);
		}
		try {
			food = ImageIO.read(getClass().getResource("/pictures/Food.png"));
			power = ImageIO.read(getClass().getResource("/pictures/Power.png"));
			wall = ImageIO.read(getClass().getResource("/pictures/Wall.png"));
			blank = ImageIO.read(getClass().getResource("/pictures/Blank.png"));
			gate = ImageIO.read(getClass().getResource("/pictures/Gate.png"));
			redGhost = ImageIO.read(getClass().getResource("/pictures/Red.png"));
			cyanGhost = ImageIO.read(getClass().getResource("/pictures/Cyan.png"));
			pinkGhost = ImageIO.read(getClass().getResource("/pictures/Pink.png"));
			orangeGhost = ImageIO.read(getClass().getResource("/pictures/Orange.png"));
			spookedGhost = ImageIO.read(getClass().getResource("/pictures/Spooked.png"));
			pacman1 = ImageIO.read(getClass().getResource("/pictures/pacman1.png"));
			pacman2Down = ImageIO.read(getClass().getResource("/pictures/pacman2Down.png"));
			pacman2Left = ImageIO.read(getClass().getResource("/pictures/pacman2Left.png"));
			pacman2Up = ImageIO.read(getClass().getResource("/pictures/pacman2Up.png"));
			pacman2Right = ImageIO.read(getClass().getResource("/pictures/pacman2Right.png"));
			pacman3Down = ImageIO.read(getClass().getResource("/pictures/pacman3Down.png"));
			pacman3Left = ImageIO.read(getClass().getResource("/pictures/pacman3Left.png"));
			pacman3Up = ImageIO.read(getClass().getResource("/pictures/pacman3Up.png"));
			pacman3Right = ImageIO.read(getClass().getResource("/pictures/pacman3Right.png"));
			score = ImageIO.read(getClass().getResource("/pictures/score.png"));
			number = ImageIO.read(getClass().getResource("/pictures/number.png"));
			gameover = ImageIO.read(getClass().getResource("/pictures/gameover.png"));
			youwin = ImageIO.read(getClass().getResourceAsStream("/pictures/youwin.png"));

			pacmanUp = new BufferedImage[] { pacman1, pacman2Up, pacman3Up, pacman2Up };
			pacmanRight = new BufferedImage[] { pacman1, pacman2Right, pacman3Right, pacman2Right };
			pacmanDown = new BufferedImage[] { pacman1, pacman2Down, pacman3Down, pacman2Down };
			pacmanLeft = new BufferedImage[] { pacman1, pacman2Left, pacman3Left, pacman2Left };
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@SuppressWarnings("serial")
	private class DrawingPanel extends JPanel implements KeyListener {
		public DrawingPanel() {
			addKeyListener(this);
		}

		TileEnum[][] drawingMap;
		int c;

		@Override
		public void keyPressed(KeyEvent e) {
			c = e.getKeyCode();
			if (game.getGameState() == GameStateEnum.Pause) {
				gameControl.setGameState(GameStateEnum.Running);
			}
			switch (c) {
			case 38:
				gameControl.updatePacmanDirection(DirectionEnum.Up);
				temp = DirectionEnum.Up;
				break;
			case 39:
				gameControl.updatePacmanDirection(DirectionEnum.Right);
				temp = DirectionEnum.Right;
				break;
			case 40:
				gameControl.updatePacmanDirection(DirectionEnum.Bottom);
				temp = DirectionEnum.Bottom;
				break;
			case 37:
				gameControl.updatePacmanDirection(DirectionEnum.Left);
				temp = DirectionEnum.Left;
				break;
			default:
				if (game.getGameState() == GameStateEnum.Running) {
					gameControl.setGameState(GameStateEnum.Pause);
				} else if (game.getGameState() == GameStateEnum.End || game.getGameState() == GameStateEnum.Win) {
					gameControl.init(true);
				}
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			drawMaze(g2);
			// System.out.println("CURRENT => " + gameState);
			if (animatePacmanStep <= animatePacmanStepMax && game.getGameState() == GameStateEnum.ResetPacman) {
				System.out.println("animation running");
				agentReset(g2, mazeBuilder.pacmanEndX, mazeBuilder.pacmanEndY, game.getPositionPacman()[0],
						game.getPositionPacman()[1], animatePacmanStep, pacman1, 6, GameStateEnum.Pause);
				agentReset(g2, mazeBuilder.ghostsEndX[0], mazeBuilder.ghostsEndY[0], game.getPositionGhosts()[0][0],
						game.getPositionGhosts()[0][1], animatePacmanStep, pinkGhost, 6, GameStateEnum.Pause);
				agentReset(g2, mazeBuilder.ghostsEndX[1], mazeBuilder.ghostsEndY[1], game.getPositionGhosts()[1][0],
						game.getPositionGhosts()[1][1], animatePacmanStep, redGhost, 6, GameStateEnum.Pause);
				agentReset(g2, mazeBuilder.ghostsEndX[2], mazeBuilder.ghostsEndY[2], game.getPositionGhosts()[2][0],
						game.getPositionGhosts()[2][1], animatePacmanStep, orangeGhost, 6, GameStateEnum.Pause);
				agentReset(g2, mazeBuilder.ghostsEndX[3], mazeBuilder.ghostsEndY[3], game.getPositionGhosts()[3][0],
						game.getPositionGhosts()[3][1], animatePacmanStep, cyanGhost, 6, GameStateEnum.Pause);
				animatePacmanStep++;
			} else if (animatePacmanStep <= animatePacmanStepMax && game.getGameState() == GameStateEnum.ResetGhost) {
				for (int i = 0; i < mazeBuilder.resetGhost.length; i++) {
					if (mazeBuilder.resetGhost[i]) {
						switch (i) {
						case 0:
							agentReset(g2, mazeBuilder.ghostsEndX[0], mazeBuilder.ghostsEndY[0],
									game.getPositionGhosts()[0][0], game.getPositionGhosts()[0][1], animatePacmanStep,
									pinkGhost, 33, GameStateEnum.Running);
							break;
						case 1:
							agentReset(g2, mazeBuilder.ghostsEndX[1], mazeBuilder.ghostsEndY[1],
									game.getPositionGhosts()[1][0], game.getPositionGhosts()[1][1], animatePacmanStep,
									redGhost, 33, GameStateEnum.Running);
							break;
						case 2:
							agentReset(g2, mazeBuilder.ghostsEndX[2], mazeBuilder.ghostsEndY[2],
									game.getPositionGhosts()[2][0], game.getPositionGhosts()[2][1], animatePacmanStep,
									orangeGhost, 33, GameStateEnum.Running);
							break;
						case 3:
							agentReset(g2, mazeBuilder.ghostsEndX[3], mazeBuilder.ghostsEndY[3],
									game.getPositionGhosts()[3][0], game.getPositionGhosts()[3][1], animatePacmanStep,
									cyanGhost, 33, GameStateEnum.Running);
							break;
						}
					} else {
						switch (i) {
						case 0:
							drawPink(g2, game.getGhosts()[0].getCurrentDirection());
							break;
						case 1:
							drawRed(g2, game.getGhosts()[0].getCurrentDirection());
							break;
						case 2:
							drawOrange(g2, game.getGhosts()[0].getCurrentDirection());
							break;
						case 3:
							drawCyan(g2, game.getGhosts()[0].getCurrentDirection());
							break;
						}
					}
				}
				try {
					drawPacMan(g2, game.getPacman().getCurrentDirection());
				} catch (LineUnavailableException | IOException e) {
					e.printStackTrace();
				}
				animatePacmanStep++;
			} else {
				for (int i = 0; i < mazeBuilder.resetGhost.length; i++) {
					mazeBuilder.resetGhost[i] = false;
				}
				try {
					drawPacMan(g2, game.getPacman().getCurrentDirection());
				} catch (LineUnavailableException | IOException e) {
					e.printStackTrace();
				}
				drawPink(g2, game.getGhosts()[0].getCurrentDirection());
				drawRed(g2, game.getGhosts()[0].getCurrentDirection());
				drawOrange(g2, game.getGhosts()[0].getCurrentDirection());
				drawCyan(g2, game.getGhosts()[0].getCurrentDirection());

				if (game.getGameState() == GameStateEnum.End) {
					g2.drawImage(gameover, 111, 189, this);
				}
				if (game.getGameState() == GameStateEnum.Win) {
					g2.drawImage(youwin, 111, 189, this);
				}
			}
		}

		private void agentReset(Graphics2D g2, int startX, int startY, int endX, int endY, int step,
				BufferedImage image, int delay, GameStateEnum state) {
			if (step < animatePacmanStepMax) {
				int slopeX = startX - endX;
				int slopeY = startY - endY;
				double stepX = (double) slopeX / (double) animatePacmanStepMax;
				double stepY = (double) slopeY / (double) animatePacmanStepMax;
				System.out.println(startX + ", " + (startX - (int) (stepX * step)));
				g2.drawImage(image, startX - (int) (stepX * step), startY - (int) (stepY * step), this);
				System.out.println("animation step: " + animatePacmanStep);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			} else {
				repaint();
				resumeGame(state);
			}
		}

		private void drawMaze(Graphics2D g2) {
			for (int i = 0; i < drawingMap.length; i++) {
				for (int j = 0; j < drawingMap[0].length; j++) {
					switch (drawingMap[i][j]) {
					case Wall:
						g2.drawImage(wall, i * 16, j * 16, this);
						break;
					case Path:
						g2.drawImage(blank, i * 16, j * 16, this);
						break;
					case Food:
						g2.drawImage(food, i * 16, j * 16, this);
						break;
					case Gate:
						g2.drawImage(gate, i * 16, j * 16, this);
						break;
					case Power:
						g2.drawImage(power, i * 16, j * 16, this);
					default:
						break;
					}
				}
			}
		}

		private void drawPacMan(Graphics2D g2, DirectionEnum dir) throws LineUnavailableException, IOException {
			int x = (int) game.getPacman().getX();
			int y = (int) game.getPacman().getY();
			switch (dir) {
			case Up:
				if (imageIndex > 11) {
					imageIndex = 0;
				}
				g2.drawImage(pacmanUp[imageIndex / 3], x, y, this);
				imageIndex++;
				break;

			case Right:
				if (imageIndex > 11) {
					imageIndex = 0;
				}
				g2.drawImage(pacmanRight[imageIndex / 3], x, y, this);
				imageIndex++;
				break;
			case Bottom:
				if (imageIndex > 11) {
					imageIndex = 0;
				}
				g2.drawImage(pacmanDown[imageIndex / 3], x, y, this);
				imageIndex++;
				break;
			case Left:
				if (imageIndex > 11) {
					imageIndex = 0;
				}
				g2.drawImage(pacmanLeft[imageIndex / 3], x, y, this);
				imageIndex++;
				break;
			}
		}

		private void drawPink(Graphics2D g2, DirectionEnum dir) {
			Ghost ghost = game.getGhosts()[0];
			BufferedImage image = pinkGhost;
			if (ghost.isVulnerable()) {
				image = spookedGhost;
			}
			g2.drawImage(image, ghost.getX(), ghost.getY(), this);

		}

		private void drawRed(Graphics2D g2, DirectionEnum dir) {
			Ghost ghost = game.getGhosts()[1];

			BufferedImage image = redGhost;
			if (ghost.isVulnerable()) {
				image = spookedGhost;
			}
			g2.drawImage(image, ghost.getX(), ghost.getY(), this);

		}

		private void drawOrange(Graphics2D g2, DirectionEnum dir) {
			Ghost ghost = game.getGhosts()[2];

			BufferedImage image = orangeGhost;
			if (ghost.isVulnerable()) {
				image = spookedGhost;
			}
			g2.drawImage(image, ghost.getX(), ghost.getY(), this);

		}

		private void drawCyan(Graphics2D g2, DirectionEnum dir) {
			Ghost ghost = game.getGhosts()[3];

			BufferedImage image = cyanGhost;
			if (ghost.isVulnerable()) {
				image = spookedGhost;
			}
			g2.drawImage(image, ghost.getX(), ghost.getY(), this);
		}

		public void updateMap(TileEnum[][] a) {
			drawingMap = a;
		}

	}

	@SuppressWarnings("serial")
	private class ScorePanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			drawScore(g2);
			drawLives(g2);
		}

		private void drawScore(Graphics2D g2) {
			int i = game.getScore();
			if (i < 0) {
				i = 0;
			}
			g2.drawImage(score, 10, 16, this);

			int digits = (int) Math.log10(i) + 1;
			System.out.println(digits);

			switch (digits) {
			default:
				g2.drawImage(number, 118, 16, 134, 32, scoreImageIndex[0], 0, scoreImageIndex[0] + 16, 16, this);
			case 1:
				g2.drawImage(number, 118, 16, 134, 32, scoreImageIndex[i], 0, scoreImageIndex[i] + 16, 16, this);
				break;
			case 2:
				g2.drawImage(number, 118, 16, 134, 32, scoreImageIndex[i / 10], 0, scoreImageIndex[i / 10] + 16, 16,
						this);
				g2.drawImage(number, 136, 16, 152, 32, scoreImageIndex[i % 10], 0, scoreImageIndex[i % 10] + 16, 16,
						this);
				break;
			case 3:

				System.out.println(i / 100);

				g2.drawImage(number, 118, 16, 134, 32, scoreImageIndex[i / 100], 0, scoreImageIndex[i / 100] + 16, 16,
						this);
				g2.drawImage(number, 136, 16, 152, 32, scoreImageIndex[i / 10 % 10], 0,
						scoreImageIndex[i / 10 % 10] + 16, 16, this);
				g2.drawImage(number, 154, 16, 170, 32, scoreImageIndex[i % 10], 0, scoreImageIndex[i % 10] + 16, 16,
						this);
				break;
			}

		}

		private void drawLives(Graphics2D g2) {
			if (game.getPacman().getLives() > 0) {
				g2.drawImage(pacman2Left, 365, 14, 20, 20, this);
			}
			if (game.getPacman().getLives() > 1) {
				g2.drawImage(pacman2Left, 390, 14, 20, 20, this);
			}
			if (game.getPacman().getLives() > 2) {
				g2.drawImage(pacman2Left, 415, 14, 20, 20, this);
			}
		}
	}

	public void updatePacmanEndpoint() {
		mazeBuilder.pacmanEndX = game.getPacman().getX();
		mazeBuilder.pacmanEndY = game.getPacman().getY();
	}

	public void updateGhostEndpoint(int i) {
		mazeBuilder.resetGhost[i] = true;
		mazeBuilder.ghostsEndX[i] = game.getGhosts()[i].getX();
		mazeBuilder.ghostsEndY[i] = game.getGhosts()[i].getY();
	}

	public void updateScore(int temp) {
	}

	private void resumeGame(GameStateEnum state) {
		game.setGameState(state);
		System.out.println("game is resumed");
	}

	private static MazeBuilder mazeBuilder;

	public static MazeBuilder getInstance(Game game) {
		if (null != mazeBuilder) {
			mazeBuilder.dispose();
		}
		mazeBuilder = new MazeBuilder(game.getMaze());
		mazeBuilder.game = game;
		return mazeBuilder;
	}

	public void update(Game game) {
		System.out.println("mazeBuilder.update");
		if (game.getGameState() == GameStateEnum.ResetPacman || game.getGameState() == GameStateEnum.ResetGhost) {
			mazeBuilder.animatePacmanStep = animatePacmanStepMin;
		}
		System.out.println("update: " + gameState);
		mazeBuilder.repaint();
	}

}