package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.wenlong.shape.GraphicsFactory;


public class YXTextView extends AppCompatTextView {
    public YXTextView(Context context) {
        this(context, null);
    }

    public YXTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
