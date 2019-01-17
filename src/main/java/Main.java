import algorithm.DistributedAlgorithm;
import algorithm.DistributedCoreDecomposition;
import algorithm.DistributedEtaCoreDecomposition;
import entity.ResultSet;
import org.apache.log4j.Logger;
import util.ConstantVal;
import util.ReadData;
import util.ResultProcess;

import java.util.*;

public class Main {
    private static Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        DistributedAlgorithm algorithm;
        String datasetName;
        float eta = 1.0f;
        Scanner scanner = new Scanner(System.in);

        /**
         * choose algorithm
         */
        System.out.println("=====================================");
        System.out.println("======== Choose an algorithm ========");
        System.out.println("=====================================");
        int AlgorithmNo = ConstantVal.LIST_ALGORITHM_TYPE.size();
        for (int i = 0; i < AlgorithmNo; i++) {
            System.out.print("[" + i + "]: " + ConstantVal.LIST_ALGORITHM_TYPE.get(i));
            System.out.println();
        }

        System.out.print("Input your algorithm type:");
        int algorithmType = scanner.nextInt();
        if (algorithmType < 0 || algorithmType > AlgorithmNo - 1) {
            System.out.println("!NO SUCH ALGORITHM");
            return;
        }
        LOGGER.info("algorithm type:" + ConstantVal.LIST_ALGORITHM_TYPE.get(algorithmType));

        /**
         * choose dataset
         */
        System.out.println("=====================================");
        System.out.println("======== Choose an dataset ==========");
        System.out.println("=====================================");

        HashMap<String, StringBuffer> datasetInfo = ReadData.getAlldatasetInfo();
        assert datasetInfo != null;
        List<Map.Entry<String, StringBuffer>> datasetInfoEntryList = new ArrayList<Map.Entry<String, StringBuffer>>(datasetInfo.entrySet());

        int datasetsNo = datasetInfo.size();
        int datasetIndex = 0;
        for (StringBuffer value : datasetInfo.values()) {
            System.out.println("[" + datasetIndex + "]: " + value);
            datasetIndex++;
        }

        System.out.print("Input your dataset:");
        int datasetType = scanner.nextInt();
        if (datasetType < 0 || datasetType > datasetsNo - 1) {
            System.out.println("!NO SUCH ALGORITHM");
            return;
        }

        datasetName = datasetInfoEntryList.get(datasetType).getKey();
        LOGGER.info("dataset Name: " + datasetName);

        /**
         * ============run algorithm=================
         */
        switch (algorithmType) {
            case 0:
                algorithm = new DistributedCoreDecomposition();
                break;
            case 1:
                algorithm = new DistributedEtaCoreDecomposition();
                System.out.println("==input eta==");
                System.out.print("Input your eta:");
                eta = scanner.nextFloat();
                break;
            default:
                LOGGER.error("We do not have this algorithm!");
                return;
        }
        LOGGER.info("==eta: " + eta + "==");

        ArrayList<ResultSet> resultSetArrayList = new ArrayList<ResultSet>();
        if (algorithmType == 0) {
            resultSetArrayList = algorithm.run(datasetName);
        } else if (algorithmType == 1) {

            resultSetArrayList = algorithm.run(datasetName, eta);
        }


        for (ResultSet rs : resultSetArrayList) {
            LOGGER.info(rs.getRoundNo()+":"+ResultProcess.getCoreList(rs).toString());
        }

    }
}
