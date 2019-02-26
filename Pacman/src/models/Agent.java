package models;

import enumations.DirectionEnum;
import enumations.TileEnum;
import interfaces.Collidable;
import interfaces.Movable;


public abstract class Agent implements Movable, Collidable {

    protected int x;
    protected int y;
    protected int speed;
    protected DirectionEnum currentDirection;
    protected DirectionEnum nextDirection;

    @Override
    public TileEnum collide(TileEnum[][] maze) {
        return null;
    }

    @Override
    public void move() {
        int[] newPosition = getNextPosition(currentDirection);
        x = newPosition[0];
        y = newPosition[1];
    }

    //position that would result if it went in that direction
    public int[] getNextPosition(DirectionEnum direction){
        int nextX = x;
        int nextY = y;
        switch (direction){
            case Bottom: nextY -= speed;
                break;
            case Up: nextY += speed;
                break;
            case Left: nextX -= speed;
                break;
            case Right: nextX += speed;
                break;
            default:	System.out.println("direction is invalid");
                System.exit(1);
        }
        int[] newPosition = {nextX, nextY};
        return newPosition;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getSpeed() {
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


}
