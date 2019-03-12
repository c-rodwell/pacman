package models;

/**   
* @Title: Ghost.java 
* @Package models 
* @Description:  
* @author Pengbin Li   
* @date 2019年1月30日 下午4:37:01 
* @version V1.0   
*/

public class Ghost extends Agent {

	private boolean vulnerable;
	private int vulnerableCounter;
	private final int vulnerableTime = 120;
	
	private Ghost() {}
	
	private static Ghost[] ghosts;
	
	public static Ghost[] getInstance() {
		if (null == ghosts) {
			ghosts = new Ghost[4];
			for (int i = 0; i < ghosts.length; i++) {
				ghosts[i] = new Ghost();
			}
		}
		return ghosts;
	}

	public boolean isVulnerable() {
		return vulnerable;
	}

	public void setVulnerable(boolean vulnerable) {
	    this.vulnerable = vulnerable;
	    if (vulnerable){
	        vulnerableCounter = vulnerableTime;
        }
	}

	public void tickDown(){
	    if (vulnerable){
	        vulnerableCounter -= 1;
	        if (vulnerableCounter <= 0){
	        	setSpeed(4);
	            setVulnerable(false);
            }
        }
    }

	@Override
	public String getDebugString(){
		return super.getDebugString();
	}
	
}
