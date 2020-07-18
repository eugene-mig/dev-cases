package com.trustpoint.cases.values;

public enum Priority {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private String text;
    Priority (final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}