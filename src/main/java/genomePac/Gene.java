package genomePac;

public class Gene {

    /*
    Base class for NodeGene and ConnectionGen. Gives to inheriting classes innovation_number which is something like
    id for our objects creating neural network. It is necessary, because we have to keep track of which genes should
    be "next to" each other during crossover.
     */
    protected int innovation_number;

    public Gene(int innovation_number){
        this.innovation_number = innovation_number;
    }
    public Gene(){
    }

    public int getInnovation_number() {
        return innovation_number;
    }

    public void setInnovation_number(int innovation_number) {
        this.innovation_number = innovation_number;
    }
}
