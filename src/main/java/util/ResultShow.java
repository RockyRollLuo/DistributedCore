package util;


import entity.ResultSet;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.DataPersistence.createJSONObject;

public class ResultShow {
    private static Logger LOGGER = Logger.getLogger(ResultShow.class);

    /**
     * main
     *
     * @param algorithmtype
     * @param datasetName
     * @param resultSetArrayList
     */
    public static void createJSONofDatasetExperimentResult(int algorithmtype, String datasetName, ArrayList<ResultSet> resultSetArrayList) {
        StringBuffer filenamePrefix = new StringBuffer();
        switch (algorithmtype) {
            case ConstantVal.ALGORITHMTYPE_DistributedCoreDecomposition:
                filenamePrefix.append(ConstantVal.DistributedCoreDecomposition);
                break;
            case ConstantVal.ALGORITHMTYPE_DistributedEtaCoreDecomposition:
                filenamePrefix.append(ConstantVal.DistributedEtaCoreDecomposition);
                break;
            case ConstantVal.ALGORITHMTYPE_CoreDecomposition:
                filenamePrefix.append(ConstantVal.CoreDecomposition);
                break;
            case ConstantVal.ALGORITHMTYPE_EtaCoreDecomposition:
                filenamePrefix.append(ConstantVal.EtaCoreDecomposition);
                break;
            default:
                break;
        }
        filenamePrefix.append(datasetName);

        //1 and 2
        firstandFinalRoundCorenessDistribution(filenamePrefix, resultSetArrayList);


        //7
        eachRoundCorenessDistribution(filenamePrefix, resultSetArrayList);
    }


    /**
     * 1,2 firstandFinalRoundCorenessDistribution
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void firstandFinalRoundCorenessDistribution(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ResultSet fistResultSet = ResultProcess.getFirstResult(resultSetArrayList);
        ResultSet finalResultSet = ResultProcess.getFinalResult(resultSetArrayList);
        ondRoundCorenessDistribution(filenamePrefix.toString() + ConstantVal.CHART_firstRoundCorenessDistribution, fistResultSet);
        ondRoundCorenessDistribution(filenamePrefix.toString() + ConstantVal.CHART_finalRoundCorenessDistribution, finalResultSet);

        LOGGER.info("chart 1 2 have created");
    }


    public static void ondRoundCorenessDistribution(String filename, ResultSet resultSet) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        HashMap<Integer, Integer> coreMap = ResultProcess.getCoreNumMap(resultSet);

        ArrayList<Integer> xdata = new ArrayList<Integer>(coreMap.keySet()); //coreness
        ArrayList<Integer> ydata = new ArrayList<Integer>(coreMap.values()); //number

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        createJSONObject(map, filename);
    }







    /**
     * 7 each round the coreness distribution
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void eachRoundCorenessDistribution(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<String> xdata = new ArrayList<String>(); //round
        ArrayList<String> ydata = new ArrayList<String>(); //coreness
        ArrayList<ArrayList<Integer>> zdata = new ArrayList<ArrayList<Integer>>(); //[round, coreness, number]

        //xdata
        int size = resultSetArrayList.size();
        for (int i = 0; i < size; i++) {
            xdata.add("round" + i);
        }

        int maxCore = 1;
        //zdata
        for (int i = 0; i < size; i++) {

            ResultSet rs = resultSetArrayList.get(i);

            maxCore = Math.max(maxCore, ResultProcess.getMaxCore(rs));

            HashMap<Integer, Integer> coreNumMap = ResultProcess.getCoreNumMap(rs);

            for (Map.Entry<Integer, Integer> entry : coreNumMap.entrySet()) {
                ArrayList<Integer> unit = new ArrayList<Integer>();
                unit.add(i);//rounds
                unit.add(entry.getKey());//coreness
                unit.add(entry.getValue());//num

                zdata.add(unit);
            }
        }

        //ydata
        for (int i = 1; i < maxCore+1; i++) {
            ydata.add(i+"");
        }

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        map.put("zdata", zdata);

        createJSONObject(map, filenamePrefix.toString() + ConstantVal.CHART_eachRoundCorenessDistribution);

        LOGGER.info("chart 7 have created");
    }

}
