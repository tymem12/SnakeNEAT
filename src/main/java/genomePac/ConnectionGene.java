package genomePac;

import dataPackage.StartingData;
import javafx.scene.canvas.Canvas;
import neat_pac.Neat;

public class ConnectionGene extends Gene {

    /*
    class that represent connection between 2 nodes from 2 layers in structure of Neural Network.
    Innovation number of its instances is created from innovation numbers of Nodes.
    No two other nodes will be able to generate the same inv number.

    inv number does not to be explicitly declared, because equal function do it for us. (?)
     */

    private NodeGene from;
    private NodeGene to;

    private double weight;
    private boolean enabled = true;

    public ConnectionGene(NodeGene from, NodeGene to) {
        this.from = from;
        this.to = to;
    }

    public NodeGene getFrom() {
        return from;
    }

    public void setFrom(NodeGene from) {
        this.from = from;
    }

    public NodeGene getTo() {
        return to;
    }

    public void setTo(NodeGene to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    //2 connectionNodes are equal if their creating nodes have the same inv number
    @Override
    public boolean equals(Object o){
        if(!(o instanceof ConnectionGene)) return false;
        ConnectionGene c = (ConnectionGene) o;
        return (from.equals(c.from) && to.equals(c.to));
    }

    @Override
    public String toString() {
        return "ConnectionGene{" +
                "from=" + from.getInnovation_number() +
                ", to=" + to.getInnovation_number() +
                ", weight=" + weight +
                ", enabled=" + enabled +
                ", innovation_number=" + innovation_number +
                '}';
    }

    public int hashCode() {
        return from.getInnovation_number() * Neat.MAX_NODES + to.getInnovation_number();
    }
}