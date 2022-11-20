package snakePackage;

import dataPackage.StartingData;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Snake {

    private int UnitSize;
    private boolean snakeAlive;
    private int length;
    private int points;
    private int xAppleLocation;
    private int yAppleLocation;
    private Directions actualDirection;
    private int[] xLocation;
    private int[] yLocation;
    private Random random;
    private long timeOfLiving;

    public Snake() {
        xLocation = new int[(StartingData.SCREEN_HEIGHT / StartingData.UNIT_SIZE) *(StartingData.SCREEN_HEIGHT / StartingData.UNIT_SIZE)];
        yLocation = new int[(StartingData.SCREEN_HEIGHT / StartingData.UNIT_SIZE) *(StartingData.SCREEN_HEIGHT / StartingData.UNIT_SIZE)];
        random = new Random();
        actualDirection = Directions.RIGHT;
        length = 6;
        snakeAlive = true;
        points = 0;
        generateApple();
        timeOfLiving = 0;
        UnitSize = StartingData.UNIT_SIZE;

    }
    enum Directions{
        UP, DOWN, RIGHT, LEFT
    }


    public void generateApple(){
        xAppleLocation = random.nextInt((int)(StartingData.SCREEN_WIDTH /StartingData.UNIT_SIZE))*StartingData.UNIT_SIZE;
        yAppleLocation = random.nextInt((int)(StartingData.SCREEN_HEIGHT /StartingData.UNIT_SIZE))*StartingData.UNIT_SIZE;
    }

    public void move() {

        for(int i = length; i>0; i--) {
            xLocation[i] = xLocation[i-1];
            yLocation[i] = yLocation[i-1];

        }
        switch(actualDirection) {
            case UP:
                yLocation[0] = yLocation[0] - UnitSize;
                break;

            case DOWN:
                yLocation[0] = yLocation[0] + UnitSize;
                break;

            case LEFT:
                xLocation[0] = xLocation[0] - UnitSize;
                break;

            case RIGHT:
                xLocation[0] = xLocation[0] + UnitSize;
                break;
        }

    }

    public void checkApples() {
        if((xLocation[0] == xAppleLocation) && (yLocation[0] == yAppleLocation)) {
            length++;
            points++;
            generateApple();
        }

    }

    public void checkColisions() {
        for(int i = length; i>0; i--) {
            if((xLocation[0] == xLocation[i]) && (yLocation[0] == yLocation[i])) {	//sprawdz czy dla każdego elementu glowa koliduje z ciałem
                 snakeAlive = false;
            }
        }
        if(xLocation[0] < 0 ) {
            snakeAlive = false;
        }
        if(xLocation[0] >= StartingData.SCREEN_WIDTH) {
            snakeAlive = false;
        }
        if(yLocation[0] < 0 ) {
            snakeAlive = false;
        }

        if(yLocation[0] >= StartingData.SCREEN_HEIGHT) {
            snakeAlive = false;
        }


    }


    public boolean getSnakeAlive(){
        return snakeAlive;
    }

    public int getUnitSize() {
        return UnitSize;
    }

    public void setUnitSize(int unitSize) {
        UnitSize = unitSize;
    }

    public boolean isSnakeAlive() {
        return snakeAlive;
    }

    public void setSnakeAlive(boolean snakeAlive) {
        this.snakeAlive = snakeAlive;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getxAppleLocation() {
        return xAppleLocation;
    }

    public void setxAppleLocation(int xAppleLocation) {
        this.xAppleLocation = xAppleLocation;
    }

    public int getyAppleLocation() {
        return yAppleLocation;
    }

    public void setyAppleLocation(int yAppleLocation) {
        this.yAppleLocation = yAppleLocation;
    }

    public Directions getActualDirection() {
        return actualDirection;
    }

    public void setActualDirection(Directions actualDirection) {
        this.actualDirection = actualDirection;
    }

    public int[] getxLocation() {
        return xLocation;
    }

    public void setxLocation(int[] xLocation) {
        this.xLocation = xLocation;
    }

    public int[] getyLocation() {
        return yLocation;
    }

    public void setyLocation(int[] yLocation) {
        this.yLocation = yLocation;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public long getTimeOfLiving() {
        return timeOfLiving;
    }

    public void setTimeOfLiving(long timeOfLiving) {
        this.timeOfLiving = timeOfLiving;
    }
}
