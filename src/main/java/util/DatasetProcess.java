package util;

import entity.Graph;
import entity.ResultSet;
import entity.UnweightedGraph;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.omg.CORBA.INTERNAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatasetProcess {
    private static Logger LOGGER = Logger.getLogger(DatasetProcess.class);

    public static void saveJSONDatasetInfo(UnweightedGraph unweightedGraph, ArrayList<ResultSet> resultSetArrayList, String datasetname) {
        ResultSet resultSet = ResultProcess.getFinalResult(resultSetArrayList);
        HashMap<Integer, Integer> coreMap = resultSet.getEstCoreMap();
        int maxCore = ResultProcess.getMaxCore(resultSet);
        int minCore = ResultProcess.getMinCore(resultSet);
        HashMap<Integer, ArrayList<Integer>> adjacencyMap = unweightedGraph.getAdjacencyMap();

        LOGGER.warn("start save datasetinfo");


        ArrayList<HashMap<String, String>> categories = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String, Integer>> nodes = new ArrayList<HashMap<String, Integer>>();
        ArrayList<HashMap<String, Integer>> links = new ArrayList<HashMap<String, Integer>>();


        for (int i = minCore; i < maxCore + 1; i++) {
            HashMap<String, String> categoryMap = new HashMap<String, String>();
            String tag = i + "-coreness";

            categoryMap.put("name", tag);
            categoryMap.put("keyword", i+"");
            categoryMap.put("base", i+"");

            categories.add(categoryMap);
        }
        LOGGER.warn("datasetinfo categories done");

        for (Map.Entry<Integer, Integer> entry : coreMap.entrySet()) {
            HashMap<String, Integer> nodeMap = new HashMap<String, Integer>();
            int key = entry.getKey();
            int value = entry.getValue();

            nodeMap.put("name", key);
            nodeMap.put("value", value);
            nodeMap.put("category", value);

            nodes.add(nodeMap);
        }
        LOGGER.warn("datasetinfo nodes done");

        for (Map.Entry<Integer, ArrayList<Integer>> entry : adjacencyMap.entrySet()) {
            int a = entry.getKey();
            ArrayList<Integer> blist = entry.getValue();
            for (Integer b : blist) {
                HashMap<String, Integer> linkMap = new HashMap<String, Integer>();
                linkMap.put("source", a);
                linkMap.put("target", b);

                links.add(linkMap);
            }
        }
        LOGGER.warn("datasetinfo Links done");

        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("links", links);
        jsonMap.put("type", "force");
        jsonMap.put("nodes", nodes);
        jsonMap.put("categories", categories);

        JSONObject json = JSONObject.fromObject(jsonMap);
        String fileName = ConstantVal.dataseinfoRoot + datasetname + ".json";
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            json.write(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.warn("datasetinfo json object done");

    }


}
