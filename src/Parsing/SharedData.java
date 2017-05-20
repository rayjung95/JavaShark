package Parsing;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by diego on 2017-04-11.
 */
public class SharedData {

    private static SharedData instance = new SharedData();
    private static ArrayList<Map> info;
    private SharedData(){}

    public static SharedData getInstance() {

        return instance;
    }

    public static ArrayList<Map> getInfo() {

        return info;
    }

    public static void setInfo (ArrayList<Map> info) {
        SharedData.info = info;
    }
}
