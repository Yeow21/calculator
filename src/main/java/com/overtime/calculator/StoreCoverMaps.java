package com.overtime.calculator;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreCoverMaps {
    private static ArrayList<HashMap<String, ArrayList<String>>> allCoverMaps = new ArrayList<>();

    public static void addCoverMap(HashMap<String, ArrayList<String>> coverMap)
    {
        allCoverMaps.add(coverMap);
    }

    public static ArrayList<HashMap<String, ArrayList<String>>> getAllCoverMaps() {
        return allCoverMaps;
    }
}
