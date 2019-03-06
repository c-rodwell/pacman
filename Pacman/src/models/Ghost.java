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
	private boolean dead;
	private static int vulnerableTime = 50;
	
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
	            setVulnerable(false);
            }
        }
    }

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	@Override
	public String getDebugString(){
		return "dead: "+dead+", "+super.getDebugString();
	}
	
}
