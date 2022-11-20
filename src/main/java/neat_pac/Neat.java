package neat_pac;

import data_structures.RandomHashSet;
import data_structures.RandomSelector;
import genomePac.ConnectionGene;
import genomePac.Genome;
import genomePac.NodeGene;

import java.util.HashMap;

// It is the most important class that keeps track of everything what is happening with our simulation.
// It also knows about every connection and every Node which was created.
public class Neat {

    public static final int MAX_NODES = (int) Math.pow(2, 20);


    private double C1 = 1, C2 = 1, C3 = 1;
    private double CP = 4;


    private double WEIGHT_SHIFT_STRENGTH = 0.3;
    private double WEIGHT_RANDOM_STRENGTH = 1;

    private double SURVIVORS = 0.8;

    private double PROBABILITY_MUTATE_LINK = 0.01;
    private double PROBABILITY_MUTATE_NODE = 0.1;
    private double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.02;
    private double PROBABILITY_MUTATE_WEIGHT_RANDOM = 0.02;
    private double PROBABILITY_MUTATE_TOGGLE_LINK = 0;

    private HashMap<ConnectionGene, ConnectionGene> all_connections = new HashMap<>();
    private RandomHashSet<NodeGene> all_nodes = new RandomHashSet<>();

    private RandomHashSet<Individual> clients = new RandomHashSet<>();
    private RandomHashSet<Species> species = new RandomHashSet<>();

    private int max_clients;
    private int output_size;
    private int input_size;

    public Neat(int input_size, int output_size, int clients) {
        this.reset(input_size, output_size, clients);
    }

    public Genome empty_genome() {
        Genome g = new Genome(this);
        for (int i = 0; i < input_size + output_size; i++) {
            g.getNodes().add(getNode(i + 1));
        }
        return g;
    }

    public void reset(int input_size, int output_size, int clients) {
        this.input_size = input_size;
        this.output_size = output_size;
        this.max_clients = clients;

        all_connections.clear();
        all_nodes.clear();
        this.clients.clear();

        for (int i = 0; i < input_size; i++) {
            NodeGene n = getNode();
            n.setX(0.1);
            n.setY((i + 1) / (double) (input_size + 1));
        }

        for (int i = 0; i < output_size; i++) {
            NodeGene n = getNode();
            n.setX(0.9);
            n.setY((i + 1) / (double) (output_size + 1));
        }

        for (int i = 0; i < max_clients; i++) {
            Individual c = new Individual();
            c.setGenome(empty_genome());
            c.generate_calculator();
            this.clients.add(c);
        }
    }

    public Individual getClient(int index) {
        return clients.get(index);
    }


    public static ConnectionGene getConnection(ConnectionGene con) {
        ConnectionGene c = new ConnectionGene(con.getFrom(), con.getTo());
        c.setInnovation_number(con.getInnovation_number());
        c.setWeight(con.getWeight());
        c.setEnabled(con.isEnabled());
        return c;
    }

    public ConnectionGene getConnection(NodeGene node1, NodeGene node2) {
        ConnectionGene connectionGene = new ConnectionGene(node1, node2);

        if (all_connections.containsKey(connectionGene)) {
            connectionGene.setInnovation_number(all_connections.get(connectionGene).getInnovation_number());
        } else {
            connectionGene.setInnovation_number(all_connections.size() + 1);
            all_connections.put(connectionGene, connectionGene);
        }

        return connectionGene;
    }

    public NodeGene getNode() {
        NodeGene n = new NodeGene(all_nodes.size() + 1);
        all_nodes.add(n);
        return n;
    }

    public NodeGene getNode(int id) {
        if (id <= all_nodes.size()) {
            return all_nodes.get(id - 1);
        }
        return getNode();
    }

    public void evolve() {

        gen_species();
        kill();
        remove_extinct_species();
        reproduce();
        mutate();
        for (Individual c : clients.getData()) {
            c.generate_calculator();
        }
    }

    public void printSpecies() {
        System.out.println("##########################################");
        for (Species s : this.species.getData()) {
            System.out.println(s + "  " + s.getScore() + "  " + s.size());
        }
    }

    private void reproduce() {
        RandomSelector<Species> selector = new RandomSelector<>();
        for (Species s : species.getData()) {
            selector.add(s, s.getScore());
        }

        for (Individual c : clients.getData()) {
            if (c.getSpecies() == null) {
                Species s = selector.random();
                c.setGenome(s.breed());
                s.force_put(c);
            }
        }
    }

    public void mutate() {
        for (Individual c : clients.getData()) {
            c.mutate();
        }
    }

    private void remove_extinct_species() {
        for (int i = species.size() - 1; i >= 0; i--) {
            if (species.get(i).size() <= 1) {
                species.get(i).goExtinct();
                species.remove(i);
            }
        }
    }

    private void gen_species() {
        for (Species s : species.getData()) {
            s.reset();
        }

        for (Individual c : clients.getData()) {
            if (c.getSpecies() != null) continue;


            boolean found = false;
            for (Species s : species.getData()) {
                if (s.put(c)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                species.add(new Species(c));
            }
        }

        for (Species s : species.getData()) {
            s.evaluate_score();
        }
    }

    private void kill() {
        for (Species s : species.getData()) {
            s.kill(1 - SURVIVORS);
        }
    }


    public double getCP() {
        return CP;
    }

    public void setCP(double CP) {
        this.CP = CP;
    }

    public double getC1() {
        return C1;
    }

    public double getC2() {
        return C2;
    }

    public double getC3() {
        return C3;
    }


    public double getWEIGHT_SHIFT_STRENGTH() {
        return WEIGHT_SHIFT_STRENGTH;
    }

    public double getWEIGHT_RANDOM_STRENGTH() {
        return WEIGHT_RANDOM_STRENGTH;
    }

    public double getPROBABILITY_MUTATE_LINK() {
        return PROBABILITY_MUTATE_LINK;
    }

    public double getPROBABILITY_MUTATE_NODE() {
        return PROBABILITY_MUTATE_NODE;
    }

    public double getPROBABILITY_MUTATE_WEIGHT_SHIFT() {
        return PROBABILITY_MUTATE_WEIGHT_SHIFT;
    }

    public double getPROBABILITY_MUTATE_WEIGHT_RANDOM() {
        return PROBABILITY_MUTATE_WEIGHT_RANDOM;
    }

    public double getPROBABILITY_MUTATE_TOGGLE_LINK() {
        return PROBABILITY_MUTATE_TOGGLE_LINK;
    }

    public int getOutput_size() {
        return output_size;
    }

    public int getInput_size() {
        return input_size;
    }

    public RandomHashSet<Individual> getClients() {
        return clients;
    }

    public RandomHashSet<Species> getSpecies() {
        return species;
    }
}