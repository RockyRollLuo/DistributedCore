package util;


import entity.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.DataPersistence.createJSONObject;

public class ResultShow {


    /**
     * @param algorithmName
     * @param datasetName
     * @param resultSetArrayList
     */
    public static void createJSONofDatasetExperimentResult(String algorithmName, String datasetName, ArrayList<ResultSet> resultSetArrayList) {



    }


    /**
     * each round the coreness distribution
     *
     * @param algorithmName
     * @param datasetName
     * @param resultSetArrayList
     */
    public static void eachRoundCorenessDistribution(String algorithmName, String datasetName, ArrayList<ResultSet> resultSetArrayList) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<String> xdata = new ArrayList<String>(); //round
        ArrayList<String> ydata = new ArrayList<String>(); //coreness
        ArrayList<ArrayList<Integer>> zdata = new ArrayList<ArrayList<Integer>>(); //[round, coreness, number]

        int size = resultSetArrayList.size();
        for (int i = 0; i < size; i++) {
            xdata.add("round" + i);
        }

        for (int i = 0; i < size; i++) {

            ResultSet rs = resultSetArrayList.get(i);
            HashMap<Integer, Integer> coreNumMap = ResultProcess.getCoreNumMap(rs);

            for (Map.Entry<Integer, Integer> entry : coreNumMap.entrySet()) {
                ArrayList<Integer> unit = new ArrayList<Integer>();
                unit.add(i);//rounds
                unit.add(entry.getKey());//coreness
                unit.add(entry.getValue());//num

                zdata.add(unit);
            }
        }

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        map.put("zdata", zdata);
        createJSONObject(map, "Each round the coreness distribution");
    }

}
