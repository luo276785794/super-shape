package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.wenlong.shape.GraphicsFactory;


public class YXRadioGroup extends RadioGroup {
    public YXRadioGroup(Context context) {
        super(context);
    }

    public YXRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
