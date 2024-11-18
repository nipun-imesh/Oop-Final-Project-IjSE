package com.assignment.finalproject.util;

import java.util.ArrayList;
import java.util.List;

public enum ClassLevel {
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    ELEVEN("11");

    private final String label;

    ClassLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (ClassLevel level : ClassLevel.values()) {
            labels.add(level.getLabel());
        }
        return labels;
    }
}
