package com.psych.demo;

import com.psych.demo.model.ChartItem;

import java.util.ArrayList;
import java.util.List;
@org.springframework.stereotype.Service
public class Service {

    public List<String> getData(List<ChartItem> payload) {
        ArrayList<String> lstOfArtistNames = new ArrayList<>();
        for(ChartItem item : payload){
            lstOfArtistNames.add(item.getItem().getName());
        }
        return lstOfArtistNames;
    }
}
