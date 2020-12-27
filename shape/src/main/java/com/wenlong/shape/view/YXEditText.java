package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.wenlong.shape.GraphicsFactory;


public class YXEditText extends AppCompatEditText {
    public YXEditText(Context context) {
        this(context, null);
    }

    public YXEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
