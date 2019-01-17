package algorithm;

import entity.ProbGraph;
import entity.ResultSet;
import entity.UnweightedGraph;
import entity.Vertex;
import org.apache.log4j.Logger;
import util.ReadData;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributedEtaCoreDecomposition implements DistributedAlgorithm {
    private static Logger LOGGER = Logger.getLogger(DistributedEtaCoreDecomposition.class);

    public ArrayList<ResultSet> run(String datasetName) {
        return null;
    }

    public ArrayList<ResultSet> run(String datasetName, float eta) {
        LOGGER.info("===Start Run: DistributedEtaCoreDecomposition===");

        ProbGraph probGraph = ReadData.readProbGraph(datasetName);

        /**===initial vertex===**/
        HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>(); //aLL vertex's information

        for (int i : probGraph.getVertexList() ) {
            Vertex vertex = new Vertex(i);
            vertex.setEstCore(probGraph.getVertexEtaDegree(i,eta));  //initial estCore=degree
            vertex.setNeighborsList(probGraph.getVertexAdjacentVertexList(i));
            vertexMap.put(i, vertex);
        }

        /**===vertice send messages loop===**/
        ArrayList<ResultSet> resultSetsList = DistributedLoop.startLoop(vertexMap);
        return resultSetsList;
    }
}
