package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.wenlong.shape.GraphicsFactory;


public class YXLinearLayout extends LinearLayout {
    public YXLinearLayout(Context context) {
        this(context, null);
    }

    public YXLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
