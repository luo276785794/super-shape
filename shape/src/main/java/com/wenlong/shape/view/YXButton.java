package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.wenlong.shape.GraphicsFactory;

/**
 * Created by Wenlong on 2020/6/29.
 */
public class YXButton extends AppCompatButton {

    public YXButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
