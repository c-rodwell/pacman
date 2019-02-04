package views;

import models.Game;

/**   
* @Title: MazeBuilder.java 
* @Package views 
* @Description:  
* @author Pengbin Li   
* @date 2019年2月1日 上午11:29:56 
* @version V1.0   
*/

public class MazeBuilder {
	
	private MazeBuilder() {}
	
	private static MazeBuilder mazeBuilder;
	
	public static MazeBuilder getInstance() {
		if (null == mazeBuilder) {
			mazeBuilder = new MazeBuilder();
		}
		return mazeBuilder;
	}
	
	public void test () {}
	
	public void update(Game game) {
		
	}
	
}
