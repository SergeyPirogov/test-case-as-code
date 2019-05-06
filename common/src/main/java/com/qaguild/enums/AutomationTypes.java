package com.qaguild.enums;

public enum AutomationTypes {
    UI(1),
    API(2),
    None(3);

    private int value;

    AutomationTypes(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
