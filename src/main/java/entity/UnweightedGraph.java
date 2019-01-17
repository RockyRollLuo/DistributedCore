package entity;

import java.util.ArrayList;
import java.util.HashMap;

public class UnweightedGraph implements Graph {
    private int vertexSize;
    private int edgeSize;
    private ArrayList<Integer> vertexList;
    private HashMap<Integer, ArrayList<Integer>> adjacencyMap;

    public UnweightedGraph(HashMap<Integer, ArrayList<Integer>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
        this.vertexSize = adjacencyMap.size();
        int edgesize = 0;
        for (ArrayList<Integer> list : adjacencyMap.values()) {
            edgesize += list.size();
        }
        this.edgeSize = edgesize;
    }

    public UnweightedGraph(int vertexSize, int edgeSize, HashMap<Integer, ArrayList<Integer>> adjacencyMap) {
        this.vertexSize = vertexSize;
        this.edgeSize = edgeSize;
        this.adjacencyMap = adjacencyMap;
    }

    /***
     * get one vertex degree
     * @param vertex
     * @return
     */
    public int getVertexDegree(int vertex) {
        int deg = 0;
        if (adjacencyMap.containsKey(vertex)) {
            ArrayList<Integer> listNeigbors = adjacencyMap.get(vertex);
            deg = listNeigbors.size();
        }
        return deg;
    }

    /***
     * get one vertex neigbors index
     * @param vertex
     * @return
     */
    public ArrayList<Integer> getVertexNeigborsList(int vertex) {
        return adjacencyMap.get(vertex);
    }

    /***
     * get the maxdegree
     * @return
     */
    public int getMaxdegree() {
        int maxdeg = 0;
        int deg = 0;
        for (int i = 0; i < vertexSize; i++) {
            deg = getVertexDegree(i);
            if (deg > maxdeg) {
                maxdeg = deg;
            }
        }
        return maxdeg;
    }

    /**
     * add a undirect edge(headV, tailV)
     * @param headV
     * @param tailV
     */
    public void addUndirectEdge(int headV, int tailV) {
        addDirectEdge(headV, tailV);
        addDirectEdge(tailV, headV);
    }

    /**
     *add a direct edge(headV, tailV)
     * @param headV
     * @param tailV
     */
    public void addDirectEdge(int headV, int tailV) {
        if (adjacencyMap.containsKey(headV)) {
            ArrayList<Integer> list = adjacencyMap.get(headV);
            list.add(tailV);
        } else {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(tailV);
            adjacencyMap.put(headV, list);
        }
    }


    /**
     * the function remove a vertex and its adjacent edges
     *
     * @param vertex
     */
    public void removeOneVertex(int vertex) {
        //remove edge
        adjacencyMap.remove(vertex);
        for (HashMap.Entry<Integer, ArrayList<Integer>> entry : adjacencyMap.entrySet()) {
            entry.getValue().remove(vertex);
        }
        //re-compute edgeSize
        int newEdgeSize = 0;
        for (HashMap.Entry<Integer, ArrayList<Integer>> entry : adjacencyMap.entrySet()) {
            newEdgeSize += entry.getValue().size();
        }
        this.edgeSize = newEdgeSize / 2;
        this.vertexSize = vertexSize - 1;
    }

    /**
     * getter and setter
     */
    public int getVertexSize() {
        return vertexSize;
    }

    public void setVertexSize(int vertexSize) {
        this.vertexSize = vertexSize;
    }

    public int getEdgeSize() {
        return edgeSize;
    }

    public void setEdgeSize(int edgeSize) {
        this.edgeSize = edgeSize;
    }

    public ArrayList<Integer> getVertexList() {
        return new ArrayList<Integer>(adjacencyMap.keySet());
    }

    public void setVertexList(ArrayList<Integer> vertexList) {
        this.vertexList = vertexList;
    }

    public HashMap<Integer, ArrayList<Integer>> getAdjacencyMap() {
        return adjacencyMap;
    }

    public void setAdjacencyMap(HashMap<Integer, ArrayList<Integer>> adjacencyMap) {
        this.adjacencyMap = adjacencyMap;
    }
}
