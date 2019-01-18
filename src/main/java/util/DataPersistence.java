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


    /**
     * save as json file
     * @param map
     * @param filename
     */
    public static void createJSONObject(HashMap map, String filename) {
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();

        jsonMap.put("myTime", getCurrentTimeFormat());
        jsonMap.put("myData", map);

        JSONObject json =JSONObject.fromObject(jsonMap);
        String fileName=ConstantVal.jsonRoot+filename+".json";
        try {
            File file = new File(fileName);
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


    /**
     * get current time str
     * @return
     */
    private static String getCurrentTimeStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    private static String getCurrentTimeFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * save json
     */
    public static void updateResultDataList() {
        //read echart/data dir
        ArrayList<String> rslist = new ArrayList<String>();

        ArrayList<File> fileList = new ArrayList<File>();
        File file = new File(ConstantVal.jsonRoot);
        File[] files = file.listFiles();
        if (files == null) {
            return ;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            }
        }
        for (File f : fileList) {
            rslist.add(f.getName());
        }

        //write json
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("rslist",rslist);

        JSONObject json =JSONObject.fromObject(jsonMap);
        try {
            File jsonfile = new File(ConstantVal.echartRoot+"datalist.json");
            if (!jsonfile.exists()) {
                jsonfile.createNewFile();
            }
            FileWriter fw = new FileWriter(jsonfile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            json.write(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * update the dataset list to json , for js read
     */
    public static void updateJsonDatasetList() {
        //read echart/data dir
        ArrayList<String> datasetList = new ArrayList<String>();
        ArrayList<File> fileList = new ArrayList<File>();

        File file = new File(ConstantVal.dataseRoot);
        File[] files = file.listFiles();
        if (files == null) {
            return ;
        }
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            }
        }
        for (File f : fileList) {
            datasetList.add(f.getName());
        }

        //write json
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("datasetlist",datasetList);

        JSONObject json =JSONObject.fromObject(jsonMap);
        try {
            File jsonfile = new File(ConstantVal.echartRoot+"datasetlist.json");
            if (!jsonfile.exists()) {
                jsonfile.createNewFile();
            }
            FileWriter fw = new FileWriter(jsonfile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            json.write(bw);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * save a object to file
     *
     * @param algorithm
     * @param dataset
     * @param list
     */
    public static String saveResult(String algorithm, String dataset, ArrayList<ResultSet> list) {

        String fileName = algorithm + "_" + dataset + "_" + getCurrentTimeStr();

        File file = new File(ConstantVal.resultRoot + fileName);

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
     * @param fileName
     * @return
     */
    public static ArrayList<ResultSet> readResult(String fileName) {
        Object temp = null;
        File file = new File(ConstantVal.resultRoot + fileName);
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


}
