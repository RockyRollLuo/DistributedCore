package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * each edge will just one in edgeList.
 */
public class ProbGraph implements Graph {

    private int vertexSize;
    private int edgeSize;
    private ArrayList<Integer> vertexList;
    private ArrayList<ProbEdge> edgeList;

    public ProbGraph() {
    }

    public ProbGraph(ArrayList<ProbEdge> edgeList) {
        this.edgeList = edgeList;
        this.edgeSize = edgeList.size();

        HashSet<Integer> set = new HashSet<Integer>();
        for (ProbEdge e : edgeList) {
            set.add(e.getHeadV());
            set.add(e.getTailV());
        }
        this.vertexList = new ArrayList<Integer>(set);
        this.vertexSize = set.size();
    }

    /***
     * get one vertex degree
     * @param vertex
     * @return
     */
    public int getVertexDegree(int vertex) {
        int deg = 0;
        ArrayList<Integer> list = getVertexAdjacentVertexList(vertex);
        if (list != null) {
            return list.size();
        } else {
            return deg;
        }
    }

    /***
     * get one vertex neigbors vertex
     * @param vertex
     * @return
     */
    public ArrayList<Integer> getVertexAdjacentVertexList(int vertex) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (ProbEdge e : edgeList) {
            if (e.getHeadV() == vertex) {
                list.add(e.getTailV());
            }
            if (e.getTailV() == vertex) {
                list.add(e.getHeadV());
            }
        }
        return list;
    }


    /**
     * get one vertex's adjacent edge's list
     *
     * @param vertex
     * @return
     */
    public ArrayList<ProbEdge> getVertexAdjacentEdgesList(int vertex) {
        ArrayList<ProbEdge> list = new ArrayList<ProbEdge>();
        for (ProbEdge e : edgeList) {
            if (e.getHeadV() == vertex || e.getTailV() == vertex) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * get one vertex's adjacent edge's probability list
     *
     * @param vertex
     * @return
     */
    public ArrayList<Float> getVertexAdjacentEdgesProbList(int vertex) {
        ArrayList<Float> list = new ArrayList<Float>();
        for (ProbEdge e : edgeList) {
            if (e.getHeadV() == vertex || e.getTailV() == vertex) {
                list.add(e.getProb());
            }
        }
        return list;
    }


    /**
     * get one vertex eta degree
     * Pr[deg(v)>=k]>=eta
     *
     * @param vertex
     * @param eta:   a  given threshold
     * @return
     */
    public int getVertexEtaDegree(int vertex, float eta) {
        int etaDeg = 0;
        int k = 0;

        // IMPORTANT!! float compare use String type
        BigDecimal bigDecimal1 = new BigDecimal(Float.toString(proVertexDegMoreThanK(vertex, k)));
        BigDecimal bigDecimal2 = new BigDecimal(Float.toString(eta));

        while ((bigDecimal1.compareTo(bigDecimal2)) == 1) {
            k++;  //the case k<dv have consider in the proVertexDegMoreThanK
            bigDecimal1 = new BigDecimal(proVertexDegMoreThanK(vertex, k));
        }
        etaDeg = k - 1;
        return etaDeg;
    }

    /**
     * the probability of a vertex have degree i.
     * Pr[deg(v)=i] i=0,1,2,,,dv  (dv is max-degree of v)
     *
     * @param vertex
     * @param i
     * @return
     */
    public Float proVertexDegEquali(int vertex, int i) {
        float p = 0;
        int degree = getVertexDegree(vertex);
        if (i > degree || i < 0) {
            return p;
        }
        ArrayList<Float> adjacentEdgesProbList = getVertexAdjacentEdgesProbList(vertex);
        p = func(degree, i, adjacentEdgesProbList);
        return p;
    }

    /**
     * X(h,j)=ph*X(h-1,j-1)+(1-ph)X(h-1,j)
     * <p>
     * X(0,0)=1,
     * X(h,-1)=0, h in [0,dv]
     * X(h,j)=0, h in [0,dv], j in [h+1, i]
     *
     * @param h
     * @param j
     * @param adjacentEdgesProbList
     * @return
     */
    private float func(int h, int j, ArrayList<Float> adjacentEdgesProbList) {
        int dv = adjacentEdgesProbList.size();
        if (h < 0 || h > dv) {
            return 0f;
        } else if (h == 0 && j == 0) {
            return 1f;
        } else if (j == -1 || j > h) {
            return 0f;
        } else {
            //if h is the index  of edge ,then the ph=adjacentEdgesList.get(h-1);
            return adjacentEdgesProbList.get(h - 1) * func(h - 1, j - 1, adjacentEdgesProbList) + (1 - adjacentEdgesProbList.get(h - 1)) * func(h - 1, j, adjacentEdgesProbList);
        }
    }


    /**
     * the probability of a vertex have degree more than k
     * Pr[deg[v]>=k]
     * = (the sum fo all Pr[deg(v)=i]) i=k,k+1,,,dv
     * = 1-(the sum of all Pr[deg(v)=i]) i=0,1,2,,,k-1
     *
     * @param vertex
     * @param k
     * @return
     */
    public Float proVertexDegMoreThanK(int vertex, int k) {
        float p = 0;
        int degree = getVertexDegree(vertex);
        if (k > degree || k < 0) {
            return p;
        }
//        for (int i = 0; i < k; i++) {
//            p += proVertexDegEquali(vertex, i);
//        }
//        return 1 - p;

        for (int i = k; i <= degree; i++) {
            p += proVertexDegEquali(vertex, i);
        }
        return p;
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
        return vertexList;
    }

    public void setVertexList(ArrayList<Integer> vertexList) {
        this.vertexList = vertexList;
    }

    public ArrayList<ProbEdge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<ProbEdge> edgeList) {
        this.edgeList = edgeList;
    }
}
