package snakePackage;


import com.example.snake_evo1.HelloController;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class GameAnimation extends SnakeGameplay {
    boolean isRunning = false;

    AnimationTimer timer;

    public GameAnimation(HelloController controller){
        this.controller = controller;
        timer = new myAnimationTimer();
        snakes.add(new Snake());
    }
    public GameAnimation(){
        snakes.add(new Snake());
        timer = new myAnimationTimer();
    }

    @Override
    public void run(){
        isRunning = true;
        timer.start();

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

            if(elapsedNanoSeconds > 80000000) {
                if (snakes.get(0).getSnakeAlive()) {
                    lastUpdate = now;
                    controller.updateCanvas(snakes.get(0));
                    snakes.get(0).move();
                    snakes.get(0).checkColisions();
                    snakes.get(0).checkApples();


                }else{
                    timer.stop();
                    controller.setGameIsRunning(false);
                }

            }

        }

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }



    public void setSnake(ArrayList snake) {
        this.snakes = snake;
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }

    public HelloController getController() {
        return controller;
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }
}
