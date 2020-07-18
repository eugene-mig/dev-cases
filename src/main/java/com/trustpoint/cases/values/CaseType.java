package com.trustpoint.cases.values;

public enum CaseType {
    General("General");

    private String text;
    CaseType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
