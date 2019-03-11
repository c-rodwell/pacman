package models;

import enumations.DirectionEnum;
import interfaces.Collidable;
import interfaces.Movable;


public abstract class Agent implements Movable, Collidable {

    private int x;
    private int y;
    private int nextX;
    private int nextY;
    private int speed;
    private DirectionEnum currentDirection;
    private DirectionEnum nextDirection;

    /*@Override
    public TileEnum collide(TileEnum[][] maze) {
        return null;
    }*/
    
    @Override
    public int[] translateToTile(int x, int y) {
    	return new int[]{x / 16, y / 16};
    }
    
    @Override
    public int[] preMove(DirectionEnum d) {
        switch (d){
            case Bottom:
            	nextY = y + speed;
            	if (nextY > 480) {
            		nextY = 0;
            	}
                break;
            case Up:
            	nextY = y - speed;
            	if (nextY < 0) {
            		nextY = 480;
            	}
                break;
            case Left:
            	nextX = x - speed;
            	if (nextX < 0) {
            		nextX = 432;
            	}
                break;
            case Right:
            	nextX = x + speed;
            	if (nextX > 432) {
            		nextX = 0;
            	}
                break;
            default:	System.out.println("direction is invalid");
                System.exit(1);
        }
        return new int[]{nextX, nextY};
    }
    
    @Override
    public void setPosition() {
    	x = nextX;
    	y = nextY;
    }
    
    @Override
    public void restoreExpect() {
    	nextX = x;
    	nextY = y;
    }
    
    public String getDebugString(){
		return "x: "+x
				+", y: "+y
				+", speed: "+speed
				+", currentDirection: "+currentDirection
				+", nextDirection: "+nextDirection;
	}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DirectionEnum getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(DirectionEnum currentDirection) {
        this.currentDirection = currentDirection;
    }

    public DirectionEnum getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(DirectionEnum nextDirection) {
        this.nextDirection = nextDirection;
    }

	public int getNextX() {
		return nextX;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

}
