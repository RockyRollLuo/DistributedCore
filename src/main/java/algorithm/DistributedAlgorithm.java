package algorithm;

import entity.ResultSet;

import java.util.ArrayList;

public interface DistributedAlgorithm {
    public ArrayList<ResultSet> run(String datasetName);

    public ArrayList<ResultSet> run(String datasetName, float eta);
}


