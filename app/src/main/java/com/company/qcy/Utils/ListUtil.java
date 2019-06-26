package com.company.qcy.Utils;

import java.util.Arrays;
import java.util.List;

public class ListUtil {

    public static String listToString(List<String> list) {

        if (list == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String string : list) {

            if (first) {
                first = false;

            } else {
                result.append(",");

            }
            result.append(string);

        }

        return result.toString();
    }

    public static List<String> stringToList(String strs){

        String str[] = strs.split(",");

        return Arrays.asList(str);

    }


}