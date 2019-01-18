package util;

import entity.ProbEdge;
import entity.ProbGraph;
import entity.UnweightedGraph;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadData {
    private static Logger LOGGER = Logger.getLogger(ReadData.class);

    //Determine the operating system type
    private static String osType = System.getProperty("os.name");
    private static String dirStr = "/dataset/";

    static {
        if (osType.indexOf("Windows") > -1) {
            dirStr = "\\dataset\\";
        } else if (osType.indexOf("Linux") > -1) {
            dirStr = "/dataset/";
        }
    }

    private static String datasetRoot = System.getProperty("user.dir") + dirStr;


    /**
     * read a simple graph,  no direction no weight
     * @param datasetName
     * @return Unweighted Graph
     */
    public static UnweightedGraph readUnweightedGraph(String datasetName) {
        LOGGER.info("===starting=== readUnweightedGraph");

        String filePath = datasetRoot + datasetName;
        String line;
        HashMap<Integer, ArrayList<Integer>> adjacencyMap = new HashMap<Integer, ArrayList<Integer>>();
        String[] edge;
        int headV;
        int tailV;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            line = br.readLine();

            while (line.indexOf("#") > -1) {
                line = br.readLine();   //ignore the comment in dataset
            }

            while (line != null) {
                edge = line.split("\t");
                headV = Integer.parseInt(edge[0]);
                tailV = Integer.parseInt(edge[1]);

                //add edge
                if (adjacencyMap.containsKey(headV)) {
                    ArrayList<Integer> list = adjacencyMap.get(headV);
                    list.add(tailV);
                } else {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(tailV);
                    adjacencyMap.put(headV, list);
                }
                if (adjacencyMap.containsKey(tailV)) {
                    ArrayList<Integer> list = adjacencyMap.get(tailV);
                    list.add(headV);
                } else {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(headV);
                    adjacencyMap.put(tailV, list);
                }

                line = br.readLine();
            }

            br.close();
        } catch (IOException e1) {
            LOGGER.error("IO error");
            System.out.println("readSimpleGraph: " + e1);
        } catch (OutOfMemoryError e2) {
            LOGGER.error("Out of memory error");
            System.out.println("readSimpleGraph: " + e2);
        }

        UnweightedGraph unweightedGraph = new UnweightedGraph(adjacencyMap);
        return unweightedGraph;
    }


    public static ProbGraph readProbGraph(String datasetName) {
        LOGGER.info("===starting=== readProbGraph");

        String filePath = datasetRoot + datasetName;
        String line;
        String[] edge;
        ArrayList<ProbEdge> edgeList = new ArrayList<ProbEdge>();
        int headV;
        int tailV;
        float prob;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            line = br.readLine();

            while (line.indexOf("#") > -1) {
                line = br.readLine();   //ignore the comment in dataset
            }

            while (line != null) {
                edge = line.split("\t");
                headV = Integer.parseInt(edge[0]);
                tailV = Integer.parseInt(edge[1]);

                if (edge.length > 2) {
                    prob = Float.parseFloat(edge[2]);
                } else {
                    prob=Float.parseFloat(Double.toString(Math.random()));
                }

                ProbEdge probEdge = new ProbEdge(headV, tailV, prob);
                edgeList.add(probEdge);

                line = br.readLine();
            }

            br.close();
        } catch (IOException e1) {
            LOGGER.error("IO error");
            System.out.println("readSimpleGraph: " + e1);
        } catch (OutOfMemoryError e2) {
            LOGGER.error("Out of memory error");
            System.out.println("readSimpleGraph: " + e2);
        }

        ProbGraph probGraph = new ProbGraph(edgeList);
        return probGraph;
    }


    /**
     * auto show dataset infomation
     */
    public static HashMap<String, StringBuffer> getAlldatasetInfo() {
        HashMap<String, StringBuffer> datasetInfoMap = new HashMap<String, StringBuffer>();

        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(datasetRoot);
        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            }
        }
        //read statics info of each dataset
        for (File f : fileList) {
            String datasetName=f.getName();
            StringBuffer datasetInfo = new StringBuffer();
            String line;

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f.getAbsoluteFile())));
                line = br.readLine();
                while (line.contains("#")) {
                    datasetInfo.append(line).append("\n").append("\t ");
                    line = br.readLine();
                }
                br.close();
                datasetInfoMap.put(datasetName, datasetInfo);
            } catch (IOException e) {
                LOGGER.error("read datasetName error");
                System.out.println("read data error: " + e);
            }
        }
        return datasetInfoMap;
    }

}
