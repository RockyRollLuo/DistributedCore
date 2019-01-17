package entity;

public class ProbEdge implements Edge {

    private int headV;
    private int tailV;
    private float prob;

    public ProbEdge() {
    }

    public ProbEdge(int headV, int tailV, float prob) {
        this.headV = headV;
        this.tailV = tailV;
        this.prob = prob;
    }


    /**
     * getter and setter
     */
    public int getHeadV() {
        return headV;
    }

    public void setHeadV(int headV) {
        this.headV = headV;
    }

    public int getTailV() {
        return tailV;
    }

    public void setTailV(int tailV) {
        this.tailV = tailV;
    }

    public float getProb() {
        return prob;
    }

    public void setProb(float prob) {
        this.prob = prob;
    }
}
