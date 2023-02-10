package com.example.snake_evo1;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import neat_pac.Individual;

import neat_pac.Neat;

import java.io.IOException;

public class HelloApplication extends Application {



    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                controller.changeDirections(keyEvent.getCode());


            }
        });
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
      //  launch();


        Neat neat = new Neat(10, 1, 100);
        double[] in = new double[10];
        for (int i = 0; i < 10; i++) in[i] = Math.random();


        for (int i = 0; i < 5; i++) {

            for (Individual individual : neat.getClients().getData()) {
                double score = individual.calculate(in)[0];
                individual.setScore(score);
            }
            neat.evolve();
            neat.printSpecies();
        }
        System.out.println("koiec");


    }


}