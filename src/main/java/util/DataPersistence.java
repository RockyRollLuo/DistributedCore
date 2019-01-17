package util;

import entity.ResultSet;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataPersistence {
    private static Logger LOGGER = Logger.getLogger(DataPersistence.class);

    private static String osType = System.getProperty("os.name");
    private static String resultDir = "/resultdata/";
    private static String jsonDataDir = "/echarts/data/";

    static {
        if (osType.indexOf("Windows") > -1) {
            resultDir = "\\resultdata\\";
            jsonDataDir = "\\echarts\\data\\";
        } else if (osType.indexOf("Linux") > -1) {
            resultDir = "/resultdata/";
            jsonDataDir = "/echarts/data/";
        }
    }

    private static String resultRoot = System.getProperty("user.dir") + resultDir;
    private static String jsonRoot = System.getProperty("user.dir") + jsonDataDir;


    /**
     * save a object to file
     *
     * @param algorithm
     * @param dataset
     * @param list
     */
    public static String saveResult(String algorithm, String dataset, ArrayList<ResultSet> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = sdf.format(new Date());
        String fileName = algorithm + "_" + dataset + "_" + currentTime;

        File file = new File(resultRoot + fileName);

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(list);
            objOut.flush();
            objOut.close();
            LOGGER.info("write object success!");
        } catch (IOException e) {
            LOGGER.error("write object failed");
            e.printStackTrace();
        }
        return fileName;
    }


    /**
     * read a file to object
     *
     * @param fileName
     * @return
     */
    public static ArrayList<ResultSet> readResult(String fileName) {
        Object temp = null;
        File file = new File(resultRoot + fileName);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            objIn.close();
            LOGGER.info("read object success!");
        } catch (IOException e) {
            LOGGER.error("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (ArrayList<ResultSet>) temp;
    }


    /**
     * save as json file
     *
     * @param map
     * @param title
     */
    public static void createJSONObject(HashMap map, String title) {
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();

        jsonMap.put("title", title);
        jsonMap.put("xdata", map.keySet());
        jsonMap.put("ydata", map.values());

        JSONObject json =JSONObject.fromObject(jsonMap);


        try {
            File file = new File(jsonRoot + title + ".json");
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
    }


}
