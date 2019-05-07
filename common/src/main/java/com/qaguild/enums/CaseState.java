package com.qaguild.enums;

public enum CaseState {

    DRAFT(1),
    READY_FOR_TESTING(2),
    AUTOMATED(3),
    READY_FOR_AUTOMATION(4),
    MANUAL(5);

    int value;

    CaseState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
