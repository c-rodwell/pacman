package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import enumations.TileEnum;

/**
 * 
 * @Title MazeImportHandler.java
 * @Package controllers
 * @author Pengbin Li
 * @date 2019年3月5日 下午2:21:32
 * @version V1.0
 */

public class MazeImportHandler {

	private TileEnum[][] maze = new TileEnum[28][31];
	private int[] positionPacman = new int[2];
	private int[][] positionGhosts = new int[4][2];
	private int allfood = 0;

	public void readFile(String file) throws IOException, UnsupportedOperationException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))));
		for (int i = 0; i < 28; i++) {
			String data = br.readLine();
			String[] ss = data.split(",");
			for (int j = 0; j < 31; j++) {
				this.maze[i][j] = TileEnum.valueOf(ss[j]);
				if (this.maze[i][j] == TileEnum.Food) {
					this.allfood++;
				}
			}
		}
		String data = br.readLine();
		String[] ss = data.split(",");
		this.positionPacman = new int[] { Integer.parseInt(ss[0]), Integer.parseInt(ss[1]) };
		data = br.readLine();
		ss = data.split(",");
		for (int i = 0; i < 4; i++) {
			this.positionGhosts[i] = new int[] { Integer.parseInt(ss[0 + 2 * i]), Integer.parseInt(ss[1 + 2 * i]) };
		}
		br.close();
	}

	public ArrayList<String> getFileNames() {
		File dir = new File("./src/maze/");
		if (dir.isDirectory()) {
			String[] ss = dir.list();
			Comparator<String> c = new FileSort();
			ArrayList<String> list = new ArrayList<String>();
			for (String s : ss) {
				if(!s.equals(".DS_Store")) {
					list.add(s);
				}
			}
			Collections.sort(list, c);
			return list;
		}
		return null;
	}

	public TileEnum[][] getMaze() {
		return maze;
	}

	public void setMaze(TileEnum[][] maze) {
		this.maze = maze;
	}

	public int[] getPositionPacman() {
		return positionPacman;
	}

	public void setPositionPacman(int[] positionPacman) {
		this.positionPacman = positionPacman;
	}

	public int[][] getPositionGhosts() {
		return positionGhosts;
	}

	public void setPositionGhosts(int[][] positionGhosts) {
		this.positionGhosts = positionGhosts;
	}

	public int getAllfood() {
		return allfood;
	}

	public void setAllfood(int allfood) {
		this.allfood = allfood;
	}

}

class FileSort implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		if (o1.equals(o2)) {
			return 0;
		} else if (Integer.parseInt(o1.substring(0, o1.lastIndexOf('.')))
				- Integer.parseInt(o2.substring(0, o2.lastIndexOf('.'))) > 0) {
			return 1;
		} else {
			return -1;
		}
	}

}