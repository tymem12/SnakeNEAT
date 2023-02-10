package data_structures;

import genomePac.Gene;

import java.util.ArrayList;
import java.util.HashSet;

public class RandomHashSet<T> {
/*
    hashSet - only because we can check if element is in our structure in O(1) complexity instead of O(n), but
    right now I am not sure if it is worth an effort
 */



  // private HashSet<T> set;
   private ArrayList<T> data;

    public RandomHashSet(){
        //set = new HashSet<>();
        data = new ArrayList<>();
    }

    public boolean contains(T object){
        return data.contains(object);
    }

    public T random_element(){
        if(data.size() > 0) return data.get((int) (Math.random() * size()));
        else return null;
    }

    public int size(){
        return data.size();
    }

    public void add(T object){
        if(!data.contains(object)){
            //set.add(object);
            data.add(object);
        }
    }

    //method to add Node with specific innovation number in to the specific place in list
    public void addSorted(Gene object){
        for(int i = 0; i<this.size(); i++){
            int innov = ((Gene)data.get(i)).getInnovation_number();
            if(object.getInnovation_number() < innov){
                data.add(i, (T) object);
              //  set.add((T) object);
                return;
            }
        }
        data.add((T) object);
        //set.add((T) object);
    }

    public void remove(T object){
        //set.remove(object);
        data.remove(object);
    }
    public void remove(int index){
        if(index < 0 || index > size()) return;
        //set.remove(data.get(index));
        data.remove(index);
    }

    public void clear(){
        data.clear();
        //set.clear();
    }

    public T get(int index){
        if(index < 0 || index >= size()) return null;
        return data.get(index);
    }

    public T get(T template){
        return data.get(data.indexOf(template));
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
