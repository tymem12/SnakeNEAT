package snakePackage;

import com.example.snake_evo1.HelloController;
import neat_pac.Individual;
import neat_pac.Neat;

import java.util.HashMap;
import java.util.HashSet;

public class Simulation extends SnakeGameplay {
    GameAnimation[] allGames;
    HelloController controller;
    Neat neat;
    HashMap<Individual, Snake> map_ind_snake;

    public Simulation( Neat neat, HelloController controller){
        this.neat = neat;
        this.controller = controller;
        allGames = new GameAnimation[neat.getClients().size()];
        map_ind_snake = new HashMap<>();
        for(int i =0 ;i< neat.getClients().size(); i++){
            //allGames[i] = new GameAnimation();
            map_ind_snake.put(neat.getClient(i), new Snake());
        }

    }
    @Override
    public void run() {

    }

    public void run_once(){
        int numberOfLiving = neat.getClients().size();
            for(Individual ind : neat.getClients().getData()){
                Snake current = map_ind_snake.get(ind);
                while(current.getSnakeAlive()) {
                    double[] input = current.generateInputTable();
                    double[] output = ind.calculate(input);
                    current.makeDecision(output);
                    //

                }
            }


    }
}
