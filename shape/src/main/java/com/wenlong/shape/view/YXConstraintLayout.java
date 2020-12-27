package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.wenlong.shape.GraphicsFactory;

public class YXConstraintLayout extends ConstraintLayout {

    public YXConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
