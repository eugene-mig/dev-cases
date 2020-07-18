package com.trustpoint.cases.values;

public enum CaseState {
    OPEN("OPEN"),
    CREATED("CREATED"),
    CLOSED("CLOSED");

    private final String text;

    CaseState(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
