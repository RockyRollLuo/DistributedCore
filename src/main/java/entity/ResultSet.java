package entity;

import java.util.HashMap;

public class ResultSet {

    private int roundNo;
    private long roundTime;
    private int changedNum;  //the vertex number of whose core number have changed during this round
    private int noChangedNum;
    private HashMap<Integer, Integer> estCoreMap;

    /**
     * constructor with all parameter
     *
     * @param roundNo
     * @param roundTime
     * @param changedNum
     * @param noChangedNum
     * @param estCoreMap
     */
    public ResultSet(int roundNo, long roundTime, int changedNum, int noChangedNum, HashMap<Integer, Integer> estCoreMap) {
        this.roundNo = roundNo;
        this.roundTime = roundTime;
        this.changedNum = changedNum;
        this.noChangedNum = noChangedNum;
        this.estCoreMap = estCoreMap;
    }

    /**
     * constructor with no parameter
     */
    public ResultSet() {
    }

    /**
     * getter and setter
     */
    public int getRoundNo() {
        return roundNo;
    }

    public void setRoundNo(int roundNo) {
        this.roundNo = roundNo;
    }

    public long getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(long roundTime) {
        this.roundTime = roundTime;
    }

    public int getChangedNum() {
        return changedNum;
    }

    public void setChangedNum(int changedNum) {
        this.changedNum = changedNum;
    }

    public int getNoChangedNum() {
        return noChangedNum;
    }

    public void setNoChangedNum(int noChangedNum) {
        this.noChangedNum = noChangedNum;
    }

    public HashMap<Integer, Integer> getEstCoreMap() {
        return estCoreMap;
    }

    public void setEstCoreMap(HashMap<Integer, Integer> estCoreMap) {
        this.estCoreMap = estCoreMap;
    }
}

