package entity;

import org.apache.log4j.Logger;

import java.util.ArrayList;

public class Vertex {
    private int vertexId;
    private int estCore;

    private boolean changed;
    private ArrayList<Integer> neighborsList;
    private ArrayList<Integer> neighborsEstCoreList;

    /**
     * Constructor with vertexId
     * @param vertexId
     */
    public Vertex(int vertexId) {
        this.vertexId = vertexId;
        this.estCore = Integer.MAX_VALUE;
        this.changed = true;
    }

    /**
     * Constructor with vertexId and estCore
     * @param vertexId
     * @param estCore
     */
    public Vertex(int vertexId, int estCore) {
        this.vertexId = vertexId;
        this.estCore = estCore;
        this.changed = true;
    }

    public int getVertexId() {
        return vertexId;
    }

    public void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    public int getEstCore() {
        return estCore;
    }

    public void setEstCore(int estCore) {
        this.estCore = estCore;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public ArrayList<Integer> getNeighborsList() {
        return neighborsList;
    }

    public void setNeighborsList(ArrayList<Integer> neighborsList) {
        this.neighborsList = neighborsList;
    }

    public ArrayList<Integer> getNeighborsEstCoreList() {
        return neighborsEstCoreList;
    }

    public void setNeighborsEstCoreList(ArrayList<Integer> neighborsEstCoreList) {
        this.neighborsEstCoreList = neighborsEstCoreList;
    }
}
