package interfaces;

import enumations.TileEnum;

/**   
* @Title: Collidable.java 
* @Package interfaces 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午4:27:04 
* @version V1.0   
*/

public interface Collidable {
	
	public TileEnum collide(TileEnum[][] maze);
	
}
