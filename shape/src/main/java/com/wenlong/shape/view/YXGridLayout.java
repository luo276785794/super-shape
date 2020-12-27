package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import com.wenlong.shape.GraphicsFactory;


public class YXGridLayout extends GridLayout {
    public YXGridLayout(Context context) {
        this(context, null);
    }

    public YXGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
