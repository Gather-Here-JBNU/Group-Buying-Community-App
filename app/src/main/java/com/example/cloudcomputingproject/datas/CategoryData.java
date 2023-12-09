package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class CategoryData {

    @SerializedName("label")
    private String label;

    public CategoryData(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}