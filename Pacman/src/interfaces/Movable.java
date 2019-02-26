package interfaces;

import enumations.DirectionEnum;

/**   
* @Title: Movable.java 
* @Package interfaces 
* @Description:  
* @author Pengbin Li   
* @date 2019-1-30 4:25:10 
* @version V1.0   
*/

public interface Movable {

	public int[] preMove(DirectionEnum d);
	
	public void setPosition();
	
	public void restoreExpect();
	
}
