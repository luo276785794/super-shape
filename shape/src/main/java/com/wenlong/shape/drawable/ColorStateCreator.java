package com.wenlong.shape.drawable;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import androidx.annotation.AttrRes;

import com.wenlong.shape.R;


public class ColorStateCreator implements Creator<ColorStateList>{

    private TypedArray textTa;

    ColorStateCreator(TypedArray textTa) {
        this.textTa = textTa;
    }

    private int[][] states = new int[][]{};
    private int[] colors = new int[]{};
    private int index;

    @Override
    public ColorStateList build(){
        states = new int[textTa.getIndexCount()][];
        colors = new int[textTa.getIndexCount()];
        for (int i = 0; i < textTa.getIndexCount(); i++) {
            int attr = textTa.getIndex(i);
            if (attr == R.styleable.text_selector_yx_checkable_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_checkable);
            } else if (attr == R.styleable.text_selector_yx_unCheckable_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_checkable);
            } else if (attr == R.styleable.text_selector_yx_checked_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_checked);
            } else if (attr == R.styleable.text_selector_yx_unChecked_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_checked);
            } else if (attr == R.styleable.text_selector_yx_enabled_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_enabled);
            } else if (attr == R.styleable.text_selector_yx_unEnabled_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_enabled);
            } else if (attr == R.styleable.text_selector_yx_selected_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_selected);
            } else if (attr == R.styleable.text_selector_yx_unSelected_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_selected);
            } else if (attr == R.styleable.text_selector_yx_pressed_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_pressed);
            } else if (attr == R.styleable.text_selector_yx_unPressed_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_pressed);
            } else if (attr == R.styleable.text_selector_yx_focused_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_focused);
            } else if (attr == R.styleable.text_selector_yx_unFocused_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_focused);
            } else if (attr == R.styleable.text_selector_yx_activated_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_activated);
            } else if (attr == R.styleable.text_selector_yx_unActivated_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_active);
            } else if (attr == R.styleable.text_selector_yx_active_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_active);
            } else if (attr == R.styleable.text_selector_yx_unActive_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_activated);
            } else if (attr == R.styleable.text_selector_yx_expanded_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_expanded);
            } else if (attr == R.styleable.text_selector_yx_unExpanded_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_expanded);
            }

        }
        return new ColorStateList(states, colors);
    }

    private void setStateColor(TypedArray selectorTa, int attr, @AttrRes int functionId) {
        states[index] = new int[]{functionId};
        colors[index] = selectorTa.getColor(attr, 0);
        index++;
    }
}
