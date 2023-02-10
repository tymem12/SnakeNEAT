package neat_pac;

import data_structures.RandomHashSet;
import genomePac.Genome;

import java.util.Comparator;

public class Species {

    private RandomHashSet<Individual> clients = new RandomHashSet<>();
    private Individual representative;
    private double score;

    public Species(Individual representative){
        this.representative = representative;
        this.representative.setSpecies(this);
        clients.add(representative);
    }

    public boolean put(Individual individual){
        if(individual.distance(representative) < representative.getGenome().getNeat().getCP()){
            individual.setSpecies(this);
            clients.add(individual);
            return true;
        }
        return false;
    }

    public void force_put(Individual individual) {
        individual.setSpecies(this);
        clients.add(individual);
    }

    public void goExtinct() {
        for(Individual c:clients.getData()){
            c.setSpecies(null);
        }
    }

    public void evaluate_score() {
        double v = 0;
        for(Individual c:clients.getData()){
            v += c.getScore();
        }
        score = v / clients.size();
    }

    public void reset() {
        representative = clients.random_element();
        for(Individual c:clients.getData()){
            c.setSpecies(null);
        }
        clients.clear();

        clients.add(representative);
        representative.setSpecies(this);
        score = 0;
    }

    public void kill(double percentage) {
        clients.getData().sort(
                new Comparator<Individual>() {
                    @Override
                    public int compare(Individual o1, Individual o2) {
                        return Double.compare(o1.getScore(), o2.getScore());
                    }
                }
        );

        double amount = percentage * this.clients.size();
        for(int i = 0;i < amount; i++){
            clients.get(0).setSpecies(null);
            clients.remove(0);
        }
    }

    public Genome breed() {
        Individual c1 = clients.random_element();
        Individual c2 = clients.random_element();

        if(c1.getScore() > c2.getScore()) return Genome.crossOver(c1.getGenome(), c2.getGenome());
        return Genome.crossOver(c2.getGenome(), c1.getGenome());
    }

    public int size() {
        return clients.size();
    }

    public RandomHashSet<Individual> getClients() {
        return clients;
    }

    public Individual getRepresentative() {
        return representative;
    }

    public double getScore() {
        return score;
    }
}