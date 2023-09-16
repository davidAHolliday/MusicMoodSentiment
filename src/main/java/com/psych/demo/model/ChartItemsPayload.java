package com.psych.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartItemsPayload {
    @JsonProperty("chart_items")
    private List<ChartItem> chartItems;
    private int next_page;

    public ChartItemsPayload(){}
    public ChartItemsPayload(List<ChartItem> chartItems, int next_page) {
        this.chartItems = chartItems;
        this.next_page = next_page;
    }



    public List<ChartItem> getChartItems() {
        return chartItems;
    }

    public void setChartItems(List<ChartItem> chartItems) {
        this.chartItems = chartItems;
    }

    public int getNext_page() {
        return next_page;
    }

    public void setNext_page(int next_page) {
        this.next_page = next_page;
    }

    // Getters and setters for the fields

    // Constructors
}
