package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.wenlong.shape.GraphicsFactory;


public class YXRelativeLayout extends RelativeLayout {
    public YXRelativeLayout(Context context) {
        this(context, null);
    }

    public YXRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
