package genomePac;

import javafx.scene.Node;

public class NodeGene extends Gene{

    /*
    class that represent Node in structure of Neural Network.
    Its innovation number is used to calculate innovation number of Connection Gene

    x,y - attributes used only with graphical representation where exactly is node (not yet implemented)
     */

    private double x,y;

    public NodeGene(int innovation_number) {
        super(innovation_number);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof NodeGene)) return false;
        return innovation_number == ((NodeGene) o).getInnovation_number();
    }

    @Override
    public String toString() {
        return "NodeGene{" +
                "innovation_number=" + innovation_number +
                '}';
    }

    public int hashCode(){
        return innovation_number;
    }
}