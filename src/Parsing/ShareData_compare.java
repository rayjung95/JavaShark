package Parsing;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by diego on 2017-04-11.
 */
public class ShareData_compare {

    private static ShareData_compare instance = new ShareData_compare();
    private static ArrayList<Map> info;
    private ShareData_compare(){}

    public static ShareData_compare getInstance() {

        return instance;
    }

    public static ArrayList<Map> getInfo() {

        return info;
    }

    public static void setInfo (ArrayList<Map> info) {
        ShareData_compare.info = info;
    }
}
