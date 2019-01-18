package util;

import java.util.ArrayList;

public class ConstantVal {

    /**
     * algorithm type
     */
    public static final ArrayList<String> LIST_ALGORITHM_TYPE = new ArrayList<String>();

    static {
        LIST_ALGORITHM_TYPE.add("Distributed Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Distributed Eta Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Eta Core Decomposition");
    }

    public static final int ALGORITHMTYPE_DistributedCoreDecomposition = 0;
    public static final int ALGORITHMTYPE_DistributedEtaCoreDecomposition = 1;
    public static final int ALGORITHMTYPE_CoreDecomposition = 2;
    public static final int ALGORITHMTYPE_EtaCoreDecomposition = 3;


    /**
     * Algorithm short name
     */
    public static final String DistributedCoreDecomposition = "DCD";
    public static final String DistributedEtaCoreDecomposition = "DECD";
    public static final String CoreDecomposition = "CD";
    public static final String EtaCoreDecomposition = "ECD";


    /**
     * file dir
     */
    private static  String projectRoot = System.getProperty("user.dir");
    private static  String osType = System.getProperty("os.name");
    private static  String resultDir = "/resultdata/";
    private static  String jsonDataDir = "/echarts/data/";
    private static  String echartDir = "/echarts/";
    private static  String datasetDir = "/dataset/";


    static {
        if (osType.indexOf("Windows") > -1) {
            resultDir="\\resultdata\\";
            jsonDataDir= "\\echarts\\data\\";
            echartDir = "\\echarts\\";
            datasetDir = "\\dataset\\";
        }
    }

    public static final String resultRoot = projectRoot + resultDir; //resultdata
    public static final String jsonRoot = projectRoot + jsonDataDir; //echarts/data
    public static final String echartRoot = projectRoot + echartDir; //echarts
    public static final String dataseRoot = projectRoot + datasetDir; //dataset


}
