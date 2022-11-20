package data_structures;

import java.util.ArrayList;

//class witch will be useful when we reproduce ours individuals. Base concept is behind random method
public class RandomSelector<T> {

    private ArrayList<T> objects = new ArrayList<>();
    private ArrayList<Double> scores = new ArrayList<>();

    private double total_score = 0;

    public void add(T element, double score){
        objects.add(element);
        scores.add(score);
        total_score += score;
    }

    //method which will return random specie, but with bigger probability if the score value of the whole specie is bigger
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
