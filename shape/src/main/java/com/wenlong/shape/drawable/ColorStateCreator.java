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
            if (attr == R.styleable.text_selector_ss_checkable_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_checkable);
            } else if (attr == R.styleable.text_selector_ss_unCheckable_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_checkable);
            } else if (attr == R.styleable.text_selector_ss_checked_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_checked);
            } else if (attr == R.styleable.text_selector_ss_unChecked_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_checked);
            } else if (attr == R.styleable.text_selector_ss_enabled_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_enabled);
            } else if (attr == R.styleable.text_selector_ss_unEnabled_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_enabled);
            } else if (attr == R.styleable.text_selector_ss_selected_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_selected);
            } else if (attr == R.styleable.text_selector_ss_unSelected_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_selected);
            } else if (attr == R.styleable.text_selector_ss_pressed_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_pressed);
            } else if (attr == R.styleable.text_selector_ss_unPressed_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_pressed);
            } else if (attr == R.styleable.text_selector_ss_focused_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_focused);
            } else if (attr == R.styleable.text_selector_ss_unFocused_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_focused);
            } else if (attr == R.styleable.text_selector_ss_activated_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_activated);
            } else if (attr == R.styleable.text_selector_ss_unActivated_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_active);
            } else if (attr == R.styleable.text_selector_ss_active_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_active);
            } else if (attr == R.styleable.text_selector_ss_unActive_textColor) {
                setStateColor(textTa, attr, -android.R.attr.state_activated);
            } else if (attr == R.styleable.text_selector_ss_expanded_textColor) {
                setStateColor(textTa, attr, android.R.attr.state_expanded);
            } else if (attr == R.styleable.text_selector_ss_unExpanded_textColor) {
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
