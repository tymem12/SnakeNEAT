package data_structures;

import java.util.ArrayList;

//structure used to store out species and corresponding scores values.
public class RandomSelector<T> {

    private ArrayList<T> objects = new ArrayList<>();
    private ArrayList<Double> scores = new ArrayList<>();

    private double total_score = 0;

    public void add(T element, double score){
        objects.add(element);
        scores.add(score);
        total_score += score;
    }

    //method return a "random" specie, based on the roulette method (bigger score means bigger probability of being chosen)
    public T random(){
        double v = Math.random() * total_score;
        double c = 0;
        for(int i = 0 ;i< objects.size(); i++){
            c += scores.get(i);
            if(c > v){
                return objects.get(i);
            }
        }
        return null;
    }
}
