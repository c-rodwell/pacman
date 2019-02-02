package controllers;

import enumations.DirectionEnum;
import models.Ghost;

/**   
* @Title: GhostCtrl.java 
* @Package controllers 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午5:22:53 
* @version V1.0   
*/

public class GhostCtrl {

	private GhostCtrl() {}
	
	private static GhostCtrl ghostCtrl;
	
	public static GhostCtrl getInstance() {
		if (null == ghostCtrl) {
			ghostCtrl = new GhostCtrl();
		}
		return ghostCtrl;
	}
	
	private Ghost[] ghosts = new Ghost[4];
	
	public Ghost[] init() {
		for (Ghost g : ghosts) {
			g = new Ghost();
			g.setDead(true);
			//more
		}
		return ghosts;
	}
	
	public void moveGhosts() {
		for (Ghost g : ghosts) {
			g.setNextDirection(decideMove(g));
			g.move();
		}

	}

	public DirectionEnum decideMove(Ghost g){
		return DirectionEnum.Bottom;
	}
	
}
