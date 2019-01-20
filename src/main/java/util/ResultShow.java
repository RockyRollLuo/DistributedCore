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

        //3
        oneRoundCorenessPercent(filenamePrefix, ResultProcess.getFinalResult(resultSetArrayList));

        //4,5,6,7
        eachRoundChangedNumber(filenamePrefix, resultSetArrayList);
        eachRoundNoChangedNumber(filenamePrefix, resultSetArrayList);

        //9
        eachRoundCorenessDistribution(filenamePrefix, resultSetArrayList);
    }


    /**
     * 1,2 firstandFinalRoundCorenessDistribution
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void firstandFinalRoundCorenessDistribution(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        int chartype1 = ConstantVal.CHART_firstRoundCorenessDistribution;
        int chartype2 = ConstantVal.CHART_finalRoundCorenessDistribution;
        HashMap<String, Object> map = new HashMap<String, Object>();
        ResultSet fistResultSet = ResultProcess.getFirstResult(resultSetArrayList);
        ResultSet finalResultSet = ResultProcess.getFinalResult(resultSetArrayList);
        ondRoundCorenessDistribution(filenamePrefix.toString() + chartype1, fistResultSet);
        ondRoundCorenessDistribution(filenamePrefix.toString() + chartype2, finalResultSet);

        LOGGER.info("chart " + chartype1 + " have created");
        LOGGER.info("chart " + chartype2 + " have created");
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
     * 3
     *
     * @param filenamePrefix
     * @param resultSet
     */
    public static void oneRoundCorenessPercent(StringBuffer filenamePrefix, ResultSet resultSet) {
        int chartype = ConstantVal.CHART_finalRoundCorenessPercent;
        HashMap<String, Object> map = new HashMap<String, Object>();
        ArrayList<String> myLegendData = new ArrayList<String>();
        HashMap<String, Boolean> mySelectedData = new HashMap<String, Boolean>();
        ArrayList<HashMap<String, Object>> mySeriesData = new ArrayList<HashMap<String, Object>>();

        HashMap<Integer, Integer> coreMap = ResultProcess.getCoreNumMap(resultSet);
        int minCore = ResultProcess.getMinCore(resultSet);
        int maxCore = ResultProcess.getMaxCore(resultSet);

        for (int i = minCore; i < maxCore + 1; i++) {
            String tag = i + "-coreness";
            myLegendData.add(tag);
            mySelectedData.put(tag, true);
        }

        for (Map.Entry<Integer, Integer> entry : coreMap.entrySet()) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("name", entry.getKey() + "-coreness");
            dataMap.put("value", entry.getValue());

            mySeriesData.add(dataMap);
        }

        map.put("myLegendData", myLegendData);
        map.put("mySelectedData", mySelectedData);
        map.put("mySeriesData", mySeriesData);
        createJSONObject(map, filenamePrefix.toString() + chartype);

        LOGGER.info("chart " + chartype + " have created");
    }

    /**
     * 4
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void eachRoundChangedNumber(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        int chartype = ConstantVal.CHART_XRoundYChangedNum;
        HashMap<String, Object> map = new HashMap<String, Object>();

        ArrayList<String> xdata = new ArrayList<String>();
        ArrayList<Integer> ydata = new ArrayList<Integer>();
        int maxValue = 100;

        int rounds = resultSetArrayList.size();

        for (int i = 0; i < rounds; i++) {
            xdata.add("round" + i);
        }

        for (ResultSet rs : resultSetArrayList) {
            int changedNum = rs.getChangedNum();
            maxValue = Math.max(maxValue, changedNum);
            ydata.add(changedNum);
        }

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        map.put("maxNum", maxValue);
        createJSONObject(map, filenamePrefix.toString() + chartype);

        LOGGER.info("chart " + chartype + " have created");
    }

    /**
     * 6
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void eachRoundNoChangedNumber(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        int chartype = ConstantVal.CHART_XRoundYNoChangedNum;
        HashMap<String, Object> map = new HashMap<String, Object>();

        ArrayList<String> xdata = new ArrayList<String>();
        ArrayList<Integer> ydata = new ArrayList<Integer>();
        int maxValue = 100;

        int rounds = resultSetArrayList.size();

        for (int i = 0; i < rounds; i++) {
            xdata.add("round" + i);
        }

        for (ResultSet rs : resultSetArrayList) {
            int noChangedNum = rs.getNoChangedNum();
            maxValue = Math.max(maxValue, noChangedNum);
            ydata.add(noChangedNum);
        }

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        map.put("maxNum", maxValue);
        createJSONObject(map, filenamePrefix.toString() + chartype);

        LOGGER.info("chart " + chartype + " have created");
    }


    /**
     * 9 each round the coreness distribution
     *
     * @param filenamePrefix
     * @param resultSetArrayList
     */
    public static void eachRoundCorenessDistribution(StringBuffer filenamePrefix, ArrayList<ResultSet> resultSetArrayList) {
        int chartype = ConstantVal.CHART_eachRoundCorenessDistribution;
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
        for (int i = 1; i < maxCore + 1; i++) {
            ydata.add(i + "");
        }

        map.put("xdata", xdata);
        map.put("ydata", ydata);
        map.put("zdata", zdata);

        createJSONObject(map, filenamePrefix.toString() + chartype);

        LOGGER.info("chart " + chartype + " have created");
    }

}
