package com.psych.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChartItem {
    private String _type;
    private String type;
    private Item item;

    public ChartItem() {
    }

    public ChartItem(String _type, String type, Item item) {
        this._type = _type;
        this.type = type;
        this.item = item;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    // Getters and setters for the fields

    // Constructors
}

