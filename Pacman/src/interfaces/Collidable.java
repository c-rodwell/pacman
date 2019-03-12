package interfaces;

import enumations.DirectionEnum;
import enumations.TileEnum;
import models.Game;

/**   
* @Title: Collidable.java 
* @Package interfaces 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午4:27:04 
* @version V1.0   
*/

public interface Collidable {
	
	public TileEnum[] checkFace(Game game, DirectionEnum direction);
	
	public int[] translateToTile(int x, int y);
	
}
