package algorithm;

import entity.ResultSet;
import entity.UnweightedGraph;
import entity.Vertex;
import org.apache.log4j.Logger;
import util.DatasetProcess;
import util.ReadData;

import java.util.ArrayList;
import java.util.HashMap;

public class DistributedCoreDecomposition implements DistributedAlgorithm{
    private static Logger LOGGER = Logger.getLogger(DistributedCoreDecomposition.class);

    public ArrayList<ResultSet> run(String datasetName) {
        LOGGER.info("===Start Run: DistributedCoreDecomposition===");

        UnweightedGraph unweightedGraph = ReadData.readUnweightedGraph(datasetName);

        /**===initial vertex===**/
        HashMap<Integer, Vertex> vertexMap = new HashMap<Integer, Vertex>(); //aLL vertex's information

        for (int i : unweightedGraph.getVertexList() ) {
            Vertex vertex = new Vertex(i);
            vertex.setEstCore(unweightedGraph.getVertexDegree(i));  //initial estCore=degree
            vertex.setNeighborsList(unweightedGraph.getVertexNeigborsList(i));
            vertexMap.put(i, vertex);
        }

        /**===vertice send messages loop===**/
        ArrayList<ResultSet> resultSetsList = DistributedLoop.startLoop(vertexMap);

        //TODO:delete when run create datasetinfo
//        DatasetProcess.saveJSONDatasetInfo(unweightedGraph,resultSetsList,datasetName);

        return resultSetsList;
    }

    public ArrayList<ResultSet> run(String datasetName, float eta) {
        return null;
    }
}
