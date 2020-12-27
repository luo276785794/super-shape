package com.wenlong.shape.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.wenlong.shape.drawable.gradient.GradientDrawableCreator;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by Wenlong on 2020/6/29.
 */
public class DrawableFactory {

    private DrawableFactory() {
    }

    //获取shape属性的drawable
    public static GradientDrawable getDrawable(TypedArray typedArray) throws XmlPullParserException {
        return (GradientDrawable) new GradientDrawableCreator(typedArray).build();
    }

    //获取selector属性的drawable
    public static StateListDrawable getSelectorDrawable(TypedArray typedArray, TypedArray selectorTa) throws Exception {
        return new SelectorDrawableCreator(typedArray, selectorTa).build();
    }


    //获取button 属性的drawable
    public static StateListDrawable getButtonDrawable(TypedArray typedArray, TypedArray buttonTa) throws Exception {
        return new ButtonDrawableCreator(typedArray, buttonTa).build();
    }

    //获取text selector属性关于text的color
    public static ColorStateList getTextSelectorColor(TypedArray textTa) {
        return new ColorStateCreator(textTa).build();
    }


    public static StateListDrawable getPressDrawable(GradientDrawable drawable, TypedArray typedArray, TypedArray pressTa)
            throws Exception {
        return new PressDrawableCreator(drawable, typedArray, pressTa).build();
    }


    public static StateListDrawable getMultiSelectorDrawable(Context context, TypedArray selectorTa, TypedArray typedArray) {
        return  new MultiSelectorDrawableCreator(context, selectorTa, typedArray).build();
    }

    public static ColorStateList getMultiTextColorSelectorColorCreator(Context context, TypedArray selectorTa) {
        return new MultiTextColorSelectorColorCreator(context, selectorTa).build();
    }
}
