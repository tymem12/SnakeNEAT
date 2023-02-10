package genomePac;

import NNCalculations.Calculator;
import data_structures.RandomHashSet;
import neat_pac.Neat;

public class Genome {

    /*
     * class containing information about all genes present in given individual.
     */


    private RandomHashSet<ConnectionGene> connections = new RandomHashSet<>();
    private RandomHashSet<NodeGene> nodes = new RandomHashSet<>();

    private Neat neat;


    public Genome(Neat neat) {
        this.neat = neat;
    }


    /*
     * Calculated the distance between this genome g1 and a second genome g2.
     * The higher the value, the less similar the genomes are.
     * The formula is from the article on which the NEAT implementation is based.
     */
    public double distance(Genome g2){

        Genome g1 = this;
        int highest_innovation_gene1 = 0;
        if(g1.getConnections().size() != 0){
            highest_innovation_gene1 = g1.getConnections().get(g1.getConnections().size()-1).getInnovation_number();
        }

        int highest_innovation_gene2 = 0;
        if(g2.getConnections().size() != 0){
            highest_innovation_gene2 = g2.getConnections().get(g2.getConnections().size()-1).getInnovation_number();
        }

        if(highest_innovation_gene1 < highest_innovation_gene2){
            Genome g = g1;
            g1 = g2;
            g2 = g;
        }

        int index_g1 = 0;
        int index_g2 = 0;

        int disjointGenes = 0;
        double weight_diff = 0;
        int matchingGenes = 0;
        int excessGenes;


        while(index_g1 < g1.getConnections().size() && index_g2 < g2.getConnections().size()){

            ConnectionGene gene1 = g1.getConnections().get(index_g1);
            ConnectionGene gene2 = g2.getConnections().get(index_g2);

            int in1 = gene1.getInnovation_number();
            int in2 = gene2.getInnovation_number();

            if(in1 == in2){
                matchingGenes ++;
                weight_diff += Math.abs(gene1.getWeight() - gene2.getWeight());
                index_g1++;
                index_g2++;
            }else if(in1 > in2){
                disjointGenes ++;
                index_g2++;
            }else{
                disjointGenes ++;
                index_g1 ++;
            }
        }

        weight_diff /= Math.max(1,matchingGenes);
        excessGenes = g1.getConnections().size() - index_g1;

        double N = Math.max(g1.getConnections().size(), g2.getConnections().size());
        if(N < 20) N = 1;


        return neat.getC1()  * disjointGenes / N + neat.getC2() * excessGenes / N + neat.getC3() * weight_diff;

    }

    /*
     * Creating a new genome through crossover:.
     *  - g1 should have the higher score
     *  - take all the genes of g1
     *  - if there is a gen in g1 that is also in g2, choose randomly (50 percent chance)
     *  - do not take disjoint genes of g2
     *  - take excess and disjoint genes of g1 if they exist
     *
     *
     * In all method which creates new genome we do not want to add genes directly but add
     * returned instances from neat.getConnection(Gene gen) because we want to keep track if the inv number for this
     * connection already exist or should be created.
     */
    public static Genome crossOver(Genome g1, Genome g2){
        Neat neat = g1.getNeat();

        Genome genome = neat.empty_genome();

        int index_g1 = 0;
        int index_g2 = 0;

        while(index_g1 < g1.getConnections().size() && index_g2 < g2.getConnections().size()){

            ConnectionGene gene1 = g1.getConnections().get(index_g1);
            ConnectionGene gene2 = g2.getConnections().get(index_g2);

            int in1 = gene1.getInnovation_number();
            int in2 = gene2.getInnovation_number();

            if(in1 == in2){
                if(Math.random() > 0.5){
                    genome.getConnections().add(neat.getConnection(gene1));
                }else{
                    genome.getConnections().add(neat.getConnection(gene2));
                }
                index_g1++;
                index_g2++;
            }else if(in1 > in2){
                //genome.getConnections().add(neat.getConnection(gene2));
                index_g2++;
            }else{
                genome.getConnections().add(neat.getConnection(gene1));
                index_g1 ++;
            }
        }

        while(index_g1 < g1.getConnections().size()){
            ConnectionGene gene1 = g1.getConnections().get(index_g1);
            genome.getConnections().add(neat.getConnection(gene1));
            index_g1++;
        }

        for(ConnectionGene c:genome.getConnections().getData()){
            genome.getNodes().add(c.getFrom());
            genome.getNodes().add(c.getTo());
        }

        /*
        Calculator calculator = new Calculator(genome);
        if(calculator.calculate(1,1,1,1,1,1,1,1,1,1)[0] == 0.5 && genome.getConnections().size() != 0){
            System.out.println(g1.getConnections().getData());
            System.out.println(g2.getConnections().getData());
            System.out.println(genome.getConnections().getData());

            System.exit(-1);
        }

         */

        return genome;
    }




    public void mutate(){
        if(neat.getPROBABILITY_MUTATE_LINK() > Math.random()){
            mutate_link();
        }if(neat.getPROBABILITY_MUTATE_NODE() > Math.random()){
            mutate_node();
        }if(neat.getPROBABILITY_MUTATE_WEIGHT_SHIFT() > Math.random()){
            mutate_weight_shift();
        }if(neat.getPROBABILITY_MUTATE_WEIGHT_RANDOM() > Math.random()){
            mutate_weight_random();
        }if(neat.getPROBABILITY_MUTATE_TOGGLE_LINK() > Math.random()){
            mutate_link_toggle();
        }
    }

    public void mutate_link() {

        for(int i = 0; i < 100; i++){

            NodeGene a = nodes.random_element();
            NodeGene b = nodes.random_element();

            if(a == null || b == null) continue;
            if(a.getX() == b.getX()){
                continue;
            }

            ConnectionGene con;
            if(a.getX() < b.getX()){
                con = new ConnectionGene(a,b);
            }else{
                con = new ConnectionGene(b,a);
            }

            if(connections.contains(con)){
                continue;
            }

            con = neat.getConnection(con.getFrom(), con.getTo());
            con.setWeight((Math.random() * 2 - 1) * neat.getWEIGHT_RANDOM_STRENGTH());

            connections.addSorted(con);
            return;
        }
    }

    public void mutate_node() {
        ConnectionGene con = connections.random_element();
        if(con == null) return;

        NodeGene from = con.getFrom();
        NodeGene to = con.getTo();

        NodeGene middle = neat.CreateNewNode();
        middle.setX((from.getX() + to.getX()) / 2);
        middle.setY((from.getY() + to.getY()) / 2 + Math.random() * 0.1 - 0.05);

        ConnectionGene con1 = neat.getConnection(from, middle);
        ConnectionGene con2 = neat.getConnection(middle, to);

        con1.setWeight(1);
        con2.setWeight(con.getWeight());
        con2.setEnabled(con.isEnabled());

        connections.remove(con);
        connections.add(con1);
        connections.add(con2);

        nodes.add(middle);
    }

    public void mutate_weight_shift() {
        ConnectionGene con = connections.random_element();
        if(con != null){
            con.setWeight(con.getWeight() + (Math.random() * 2 - 1) * neat.getWEIGHT_SHIFT_STRENGTH());
        }
    }

    public void mutate_weight_random() {
        ConnectionGene con = connections.random_element();
        if(con != null){
            con.setWeight((Math.random() * 2 - 1) * neat.getWEIGHT_RANDOM_STRENGTH());
        }
    }

    public void mutate_link_toggle() {
        ConnectionGene con = connections.random_element();
        if(con != null){
            con.setEnabled(!con.isEnabled());
        }
    }

    public RandomHashSet<ConnectionGene> getConnections() {
        return connections;
    }

    public RandomHashSet<NodeGene> getNodes() {
        return nodes;
    }

    public Neat getNeat() {
        return neat;
    }



}