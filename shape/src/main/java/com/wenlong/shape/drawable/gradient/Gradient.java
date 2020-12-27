package com.wenlong.shape.drawable.gradient;

import android.graphics.Rect;

import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;

/**
 * Created by Wenlong on 2020/6/29.
 */
public class Gradient {

    public float[] cornerRadius = new float[8];
    public float sizeWidth = 0;
    public float sizeHeight = 0;
    public float strokeWidth = -1;
    public float strokeDashWidth = 0;
    public int strokeColor = 0;
    public int solidColor = 0;
    public float strokeGap = 0;
    public float centerX = 0;
    public float centerY = 0;
    public int centerColor = 0;
    public int startColor = 0;
    public int endColor = 0;
    public int gradientType = LINEAR_GRADIENT;
    public int gradientAngle = 0;
    public Rect padding = new Rect();

}
