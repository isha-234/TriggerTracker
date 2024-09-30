package com.tandt.tracktrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHelper {

    String stask;
    List dateList;
    List timeList;

    public UserHelper(String stask, int[] dateList, int[] timeList) {
        this.stask = stask;
        this.dateList = new ArrayList(Arrays.asList(dateList));
        this.timeList = new ArrayList(Arrays.asList(timeList));
    }


}
