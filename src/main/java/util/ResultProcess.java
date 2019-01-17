package util;

import entity.ResultSet;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResultProcess {
    private static Logger LOGGER = Logger.getLogger(ResultProcess.class);


    /**
     * final result
     * @param resultSetList
     * @return
     */
    public static ResultSet getFinalResult(ArrayList<ResultSet> resultSetList) {
        int size = resultSetList.size();
        return resultSetList.get(size - 1);
    }

    /**
     * in one resultset, get the core list
     * @param resultSet
     * @return
     */
    public static ArrayList<Integer> getCoreList(ResultSet resultSet) {


        return new ArrayList<Integer>(resultSet.getEstCoreMap().values());
    }

    /**
     * in one resultset, each core number and its Num
     * @param resultSet
     * @return map
     */
    public static HashMap<Integer, Integer> getCoreNumMap(ResultSet resultSet) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> coreList = getCoreList(resultSet);
        for (int core : coreList) {
            if (map.containsKey(core)) {
                map.put(core, map.get(core) + 1);
            } else {
                map.put(core, 1);
            }
        }
        return map;
    }

    /**
     * in one resultset, each core number and its percentage
     * @param resultSet
     * @return
     */
    public static HashMap<Integer, Float> getCorePercentMap(ResultSet resultSet) {
        HashMap<Integer, Float> map = new HashMap<Integer, Float>();
        HashMap<Integer, Integer> coreNoMap = getCoreNumMap(resultSet);
        int size = coreNoMap.size();
        for (Map.Entry<Integer, Integer> m : coreNoMap.entrySet()) {
            float percent = Float.parseFloat(m.getKey().toString()) / size;
            map.put(m.getKey(), percent);
        }
        return map;
    }

    /**
     * in one resultset, the max core
     * @param resultSet
     * @return
     */
    public static int getMaxCore(ResultSet resultSet) {
        ArrayList<Integer> coreList = getCoreList(resultSet);
        return Collections.max(coreList);
    }

    /**
     * in one resultset, the min core
     * @param resultSet
     * @return
     */
    public static int getMinCore(ResultSet resultSet) {
        ArrayList<Integer> coreList = getCoreList(resultSet);
        return Collections.min(coreList);
    }


    /**
     * int each round, the max core
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Integer> getEachRoundMaxCore(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (ResultSet rs : resultSetList) {
            map.put(rs.getRoundNo(), getMaxCore(rs));
        }
        return map;
    }


    /**
     * int each round, the runing time
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Long> getEachRoundRuningTime(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        for (ResultSet rs : resultSetList) {
            map.put(rs.getRoundNo(), rs.getRoundTime());
        }
        return map;
    }


    /**
     * in each round, the correct core Num
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Integer> getEachRoundCorrectNum(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ArrayList<Integer> correctCoreList = getCoreList(getFinalResult(resultSetList));
        for (ResultSet rs : resultSetList) {
            ArrayList<Integer> coreList = getCoreList(rs);
            int num = 0;
            for (int i = 0; i < coreList.size(); i++) {
                if (correctCoreList.get(i).equals(coreList.get(i))) {
                    num++;
                }
            }
            map.put(rs.getRoundNo(), num);
        }
        return map;
    }


    /**
     * in each round, the correct core percent
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Float> getEachRoundCorrectPercent(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Float> map = new HashMap<Integer, Float>();
        HashMap<Integer, Integer> correctNumMap = getEachRoundCorrectNum(resultSetList);
        int size = correctNumMap.size();
        for (Map.Entry<Integer, Integer> m : correctNumMap.entrySet()) {
            float percent = Float.parseFloat(m.getKey().toString()) / size;
            map.put(m.getKey(), percent);
        }
        return map;
    }

    /**
     * in each round, the changed num
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Integer> getEachRoundChangedNum(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (ResultSet rs : resultSetList) {
            map.put(rs.getRoundNo(), rs.getChangedNum());
        }
        return map;
    }

    /**
     * in each round, the no changed num
     * @param resultSetList
     * @return
     */
    public static HashMap<Integer, Integer> getEachRoundNoChangedNum(ArrayList<ResultSet> resultSetList) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (ResultSet rs : resultSetList) {
            map.put(rs.getRoundNo(), rs.getNoChangedNum());
        }
        return map;
    }

}

