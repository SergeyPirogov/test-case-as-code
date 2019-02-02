package com.qaguild.plugin.enums;

public enum AutomationType {
    GUI_SELENIUM(1),
    BACKEND(2),
    None(3);

    private int value;

    AutomationType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
