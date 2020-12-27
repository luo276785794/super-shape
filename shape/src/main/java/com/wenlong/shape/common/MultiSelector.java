package com.wenlong.shape.common;

public enum MultiSelector {

    STATE_CHECKABLE("state_checkable", android.R.attr.state_checkable),
    STATE_CHECKED("state_checked", android.R.attr.state_checked),
    STATE_ENABLED("state_enabled", android.R.attr.state_enabled),
    STATE_SELECTED("state_selected", android.R.attr.state_selected),
    STATE_PRESSED("state_pressed", android.R.attr.state_pressed),
    STATE_FOCUSED("state_focused", android.R.attr.state_focused),
    STATE_HOVERED("state_hovered", android.R.attr.state_hovered),
    STATE_ACTIVATED("state_activated", android.R.attr.state_activated);

    public String value;
    public int id;

    MultiSelector(String value, int id){
        this.value = value;
        this.id = id;
    }

    public static MultiSelector getMultiAttr(String value){
        switch (value){
            case "state_checkable":
                return STATE_CHECKABLE;
            case "state_checked":
                return STATE_CHECKED;
            case "state_enabled":
                return STATE_ENABLED;
            case "state_selected":
                return STATE_SELECTED;
            case "state_pressed":
                return STATE_PRESSED;
            case "state_focused":
                return STATE_FOCUSED;
            case "state_hovered":
                return STATE_HOVERED;
            case "state_activated":
                return STATE_ACTIVATED;
            default:
                return null;
        }
    }
}
