package snakePackage;

import com.example.snake_evo1.HelloController;

import java.util.ArrayList;

public abstract class SnakeGameplay {
    ArrayList<Snake> snakes = new ArrayList<>();
    HelloController controller;

    public Snake getSnake() {
        return snakes.get(0);
    }
    public abstract void run();
}
