package com.example.snake_evo1;

import dataPackage.StartingData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import neat_pac.Neat;
import snakePackage.GameAnimation;
import snakePackage.Simulation;
import snakePackage.Snake;
import snakePackage.SnakeGameplay;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
   private boolean gameIsRunning = false;
    SnakeGameplay gameplay;

    @FXML
    private Button runButton, nextGenButton;
    @FXML
    private MenuItem startGameButton;
    @FXML
    private Canvas canvas;
    @FXML
    private GraphicsContext graphicsContext;

    public void startGameAction(ActionEvent event) {
        System.out.println("działaGierka");
        gameplay = new GameAnimation(this);
        gameIsRunning = true;
        gameplay.run();
        //po rozpoczęciu gry TUTAJ MOŻEMY ZACZĄĆ MYŚLEĆ

    }
    public void prepareSimulation(ActionEvent event){
        int CLIENTS = 100;
        Neat neat = new Neat(26, 4,CLIENTS);
        System.out.println("działaSymulacja");
        gameplay = new Simulation( neat, this);
        gameIsRunning = false;


    }
    public void runSimulation(ActionEvent event){
        gameplay.run();
    }
    public void generateSimulation(ActionEvent event){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        graphicsContext = canvas.getGraphicsContext2D();
        runCanvas();
    }

    public void runCanvas() {

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, StartingData.SCREEN_WIDTH, StartingData.SCREEN_HEIGHT);
       // graphicsContext.setFill(Color.GREEN);
        //graphicsContext.fillRect(8 , 0, StartingData.UNIT_SIZE , StartingData.UNIT_SIZE);


    }
    public void updateCanvas(Snake snake){
        //DO POPRAWIENIA - ZA DUŻO ARGUMENTÓW


        runCanvas();
        graphicsContext.setFill(Color.GREEN);
        for(int i =0; i < snake.getLength(); i++){
            graphicsContext.fillRect(snake.getxLocation()[i] , snake.getyLocation()[i], StartingData.UNIT_SIZE, StartingData.UNIT_SIZE);
        }
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(snake.getxAppleLocation(), snake.getyAppleLocation(),StartingData.UNIT_SIZE, StartingData.UNIT_SIZE );

    }
    public void changeDirections(KeyCode code){
        if(gameIsRunning){
            switch (code){
                case UP :
                    gameplay.getSnake().moveUP();
                    break;
                case DOWN :
                    gameplay.getSnake().moveDOWN();
                    break;
                case RIGHT:
                    gameplay.getSnake().moveRIGHT();
                    break;
                case LEFT:
                    gameplay.getSnake().moveLEFT();
                    break;
            }
        }
    }


    public boolean isGameIsRunning() {
        return gameIsRunning;
    }

    public void setGameIsRunning(boolean gameIsRunning) {
        this.gameIsRunning = gameIsRunning;
    }
}