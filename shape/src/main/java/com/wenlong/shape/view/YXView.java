package com.wenlong.shape.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.wenlong.shape.GraphicsFactory;

/**
 * Created by Wenlong on 2020/6/29.
 */
public class YXView extends View {

    public YXView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YXView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        GraphicsFactory.setViewBackground(context, attrs, this);
    }
}
