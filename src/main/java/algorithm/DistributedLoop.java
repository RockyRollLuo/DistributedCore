package algorithm;

import entity.ResultSet;
import entity.Vertex;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DistributedLoop {
    private static Logger LOGGER = Logger.getLogger(DistributedLoop.class);

    public static ArrayList<ResultSet> startLoop(HashMap<Integer,Vertex> vertexMap) {


        int vertexSize = vertexMap.size();

        int round = 0;
        int noChangedNum = 0;
        int changedNum = 0;
        long startTime = 0;
        long endtime = 0;
        long roundTime = 0;

        ArrayList<ResultSet> resultSetsList = new ArrayList<ResultSet>();
        while (true) {
            startTime = System.currentTimeMillis(); //round start time;
            LOGGER.info("==Start: ROUND: " + round);

            /**
             * ===check no changed (changed=false) number===
             */
            noChangedNum = 0;
            HashMap<Integer, Integer> vertexCoreMap = new HashMap<Integer, Integer>();
            for (Vertex e : vertexMap.values()) {
                vertexCoreMap.put(e.getVertexId(), e.getEstCore());
                if (e.isChanged() == false) {
                    noChangedNum++;
                }
            }
            LOGGER.info("==round" + round + ".  No changed number: " + noChangedNum);

            /**
             * ===get message from neighbors===
             * whatever who have changed,
             * because in distributed environment, after changed then send message to update the neighbor's information
             * ===re-computing estmate core===
             */
            LOGGER.info("==round:" + round + ". re-computing estmate core");

            ArrayList<Integer> neighborsList;

            for (Vertex vertex:vertexMap.values()) {

                ArrayList<Integer> neighborsEstCore = new ArrayList<Integer>();

                //get each neighbor est-core
                neighborsList = vertex.getNeighborsList(); //get neighbors vertexid
                for (int neighborid : neighborsList) {
                    Vertex neighbor = vertexMap.get(neighborid);
                    neighborsEstCore.add(neighbor.getEstCore());
                    neighbor.setChanged(false);
                }

                int k = vertex.getEstCore();
                int t = computedIndex(neighborsEstCore, k);
                if (t < k) {
                    vertex.setEstCore(t);
                    vertex.setChanged(true);
                }

            }
            LOGGER.info("==DONE round: " + round);
            endtime = System.currentTimeMillis();
            roundTime = endtime - startTime;

            /**
             * ===collect current resultset
             */
            changedNum = vertexSize - noChangedNum;
            ResultSet resultSet = new ResultSet(round, roundTime, changedNum, noChangedNum, vertexCoreMap);
            resultSetsList.add(resultSet);
            /**
             * ===exit the loop===
             * when all vertices's core no more change, we have get the final result
             */
            if (noChangedNum == vertexSize) {
                LOGGER.warn("======!!!ALL VERTICES HAVE CONVERGE!!!======");
                break;
            }
            if (round == 60) {
                LOGGER.warn("======!!!CANNOT CONVERGE!!!======");
                break;
            }

            //next loop
            round++;
        }

        return resultSetsList;
    }

    /***
     * @param neighborsEstCore u's neigborsCore
     * @param k u's core
     * @return
     */
    private static int computedIndex(ArrayList<Integer> neighborsEstCore, int k) {
        int[] count = new int[k + 1];
        for (int i = 0; i < k + 1; i++) {
            count[i] = 0;
        }

        for (int j : neighborsEstCore) {
            int index = Math.min(k, j);
            count[index] = count[index] + 1;

        }

        //the neighbors num greater than core vlaue
        for (int i = k; i > 1; i--) {
            count[i - 1] = count[i - 1] + count[i];
        }

        //the maximum core number(k) has at least k neighbors
        int ret = k;
        while (ret > 1 & count[ret] < ret) {
            ret = ret - 1;
        }
        return ret;
    }

}
