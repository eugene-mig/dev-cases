package com.trustpoint.cases.values;

public enum Step {
    READY("READY"),
    IN_PROCESS("IN_PROCESS"),
    READY_FOR_REPORTING("READY_FOR_REPORTING");

    private final String text;

    Step(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}