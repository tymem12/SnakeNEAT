package neat_pac;

import NNCalculations.Calculator;
import genomePac.Genome;

public class Individual {

    private Calculator calculator;

    private Genome genome;
    private double score;
    private Species species;

    public void generate_calculator(){
        this.calculator = new Calculator(genome);
    }

    /*
    method we call when we want to calculate the result on NN for given input
     */
    public double[] calculate(double... input){
        if(this.calculator == null) generate_calculator();
        return this.calculator.calculate(input);
    }

    public double distance(Individual other) {
        return this.getGenome().distance(other.getGenome());
    }

    public void mutate() {
        getGenome().mutate();
    }

    public Calculator getCalculator() {
        return calculator;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}