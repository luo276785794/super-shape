package com.wenlong.shape.drawable;

import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.wenlong.shape.R;


public class PressDrawableCreator implements Creator<StateListDrawable> {

    private GradientDrawable drawable;
    private TypedArray pressTa;
    private TypedArray typedArray;

    PressDrawableCreator(GradientDrawable drawable, TypedArray typedArray, TypedArray pressTa) {
        this.drawable = drawable;
        this.pressTa = pressTa;
        this.typedArray = typedArray;
    }

    @Override
    public StateListDrawable build() throws Exception{
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int i = 0; i < pressTa.getIndexCount(); i++) {
            int attr = pressTa.getIndex(i);

            if (attr == R.styleable.background_press_yx_pressed_color) {
                int color = pressTa.getColor(attr, 0);
                GradientDrawable pressDrawable = DrawableFactory.getDrawable(typedArray);
                pressDrawable.setColor(color);
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
            } else if (attr == R.styleable.background_press_yx_unpressed_color) {
                int color = pressTa.getColor(attr, 0);
                drawable.setColor(color);
                stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawable);
            }
        }
        return stateListDrawable;
    }
}
