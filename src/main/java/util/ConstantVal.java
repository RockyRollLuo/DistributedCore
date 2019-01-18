package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConstantVal {

    /**
     * algorithm type
     */
    public static final ArrayList<String> LIST_ALGORITHM_TYPE = new ArrayList<String>();
    static {
        LIST_ALGORITHM_TYPE.add("Distributed Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Distributed Eta Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Peel Core Decomposition");
        LIST_ALGORITHM_TYPE.add("Eta Core Decomposition");
    }


}
