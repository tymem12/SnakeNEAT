package snakePackage;


import com.example.snake_evo1.HelloController;
import javafx.animation.AnimationTimer;

public class Game {
    boolean isRunning = false;
    Snake snake;
    AnimationTimer timer;
    HelloController controller;

    public Game(HelloController controller){
        this.controller = controller;
        timer = new myAnimationTimer();
        snake = new Snake();
    }

    public void runGame(){
        isRunning = true;
        timer.start();

    }
    public void moveUP(){
        if(snake.getActualDirection() != Snake.Directions.DOWN) snake.setActualDirection(Snake.Directions.UP);
    }
    public void moveDOWN(){
        if(snake.getActualDirection() != Snake.Directions.UP) snake.setActualDirection(Snake.Directions.DOWN);

    }
    public void moveRIGHT(){
        if(snake.getActualDirection() != Snake.Directions.LEFT) snake.setActualDirection(Snake.Directions.RIGHT);

    }
    public void moveLEFT(){
        if(snake.getActualDirection() != Snake.Directions.RIGHT) snake.setActualDirection(Snake.Directions.LEFT);

    }

    private class myAnimationTimer extends AnimationTimer {

        private long lastUpdate; // Last time in which `handle()` was called

        @Override
        public void start() {
            lastUpdate = System.nanoTime();
            super.start();
        }

        @Override
        public void handle(long now) {
            long elapsedNanoSeconds = now - lastUpdate;

            if(elapsedNanoSeconds > 75000000) {
                if (snake.getSnakeAlive()) {
                    lastUpdate = now;
                    controller.updateCanvas(snake);
                    snake.move();
                    snake.checkColisions();
                    snake.checkApples();


                }else{
                    timer.stop();
                    controller.setGameIsRunning(false);
                }

            }

        }

    }
}
