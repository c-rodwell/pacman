package models;

import enumations.DirectionEnum;
import enumations.TileEnum;
import interfaces.Collidable;
import interfaces.Movable;


public abstract class Agent implements Movable, Collidable {

    private double x;
    private double y;
    private double speed;
    private DirectionEnum currentDirection;
    private DirectionEnum nextDirection;


    @Override
    public TileEnum collide(TileEnum[][] maze) {
        return null;
    }

    @Override
    public void move() {
        switch (currentDirection){
            case Bottom: y -= speed;
                break;
            case Up: y += speed;
                break;
            case Left: x -= speed;
                break;
            case Right: x += speed;
                break;
            default:	System.out.println("direction is invalid");
                System.exit(1);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
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
