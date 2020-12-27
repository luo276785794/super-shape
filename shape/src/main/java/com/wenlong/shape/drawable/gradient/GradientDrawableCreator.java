package com.wenlong.shape.drawable.gradient;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import com.wenlong.shape.R;
import com.wenlong.shape.drawable.Creator;

import org.xmlpull.v1.XmlPullParserException;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;

/**
 * Created by Wenlong on 2020/6/29.
 */
public class GradientDrawableCreator implements Creator<GradientDrawable> {

    private final TypedArray mTypedArray;

    public GradientDrawableCreator(TypedArray typedArray) {
        mTypedArray = typedArray;
    }

    @Override
    public GradientDrawable build() throws XmlPullParserException {
        GradientDrawable drawable = new GradientDrawable();
        Gradient gradient = initGradient(drawable);
        if (hasSetRadius(gradient.cornerRadius)) {
            drawable.setCornerRadii(gradient.cornerRadius);
        }

        if (mTypedArray.hasValue(R.styleable.background_yx_size_width) &&
                mTypedArray.hasValue(R.styleable.background_yx_size_height)) {
            drawable.setSize((int) gradient.sizeWidth, (int) gradient.sizeHeight);
        }
        //设置填充颜色
        solid(gradient, drawable);

        //设置边框颜色
        stroke(gradient, drawable);

        gradient(gradient, drawable);

        padding(gradient, drawable);

        return drawable;
    }

    private void padding(Gradient gradient, GradientDrawable drawable) {
        if (mTypedArray.hasValue(R.styleable.background_yx_padding_left) &&
                mTypedArray.hasValue(R.styleable.background_yx_padding_top) &&
                mTypedArray.hasValue(R.styleable.background_yx_padding_right) &&
                mTypedArray.hasValue(R.styleable.background_yx_padding_bottom)) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                drawable.setPadding(gradient.padding.left, gradient.padding.top, gradient.padding.right, gradient.padding.bottom);
            } else {
                try {
                    Field paddingField = drawable.getClass().getDeclaredField("mPadding");
                    paddingField.setAccessible(true);
                    paddingField.set(drawable, gradient.padding);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void gradient(Gradient gradient, GradientDrawable drawable) throws XmlPullParserException {
        if (mTypedArray.hasValue(R.styleable.background_yx_gradient_centerX) &&
                mTypedArray.hasValue(R.styleable.background_yx_gradient_centerY)) {
            drawable.setGradientCenter(gradient.centerX, gradient.centerY);
        }

        if (mTypedArray.hasValue(R.styleable.background_yx_gradient_startColor) &&
                mTypedArray.hasValue(R.styleable.background_yx_gradient_endColor)) {
            int[] colors;
            if (mTypedArray.hasValue(R.styleable.background_yx_gradient_centerColor)) {
                colors = new int[3];
                colors[0] = gradient.startColor;
                colors[1] = gradient.centerColor;
                colors[2] = gradient.endColor;
            } else {
                colors = new int[2];
                colors[0] = gradient.startColor;
                colors[1] = gradient.endColor;
            }
            drawable.setColors(colors);
        }
        if (gradient.gradientType == LINEAR_GRADIENT &&
                mTypedArray.hasValue(R.styleable.background_yx_gradient_angle)) {
            gradient.gradientAngle %= 360;
            if (gradient.gradientAngle % 45 != 0) {
                throw new XmlPullParserException(mTypedArray.getPositionDescription()
                        + "<gradient> tag requires 'angle' attribute to "
                        + "be a multiple of 45");
            }
            GradientDrawable.Orientation mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
            switch (gradient.gradientAngle) {
                case 0:
                    mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                    break;
                case 45:
                    mOrientation = GradientDrawable.Orientation.BL_TR;
                    break;
                case 90:
                    mOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                    break;
                case 135:
                    mOrientation = GradientDrawable.Orientation.BR_TL;
                    break;
                case 180:
                    mOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                    break;
                case 225:
                    mOrientation = GradientDrawable.Orientation.TR_BL;
                    break;
                case 270:
                    mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                    break;
                case 315:
                    mOrientation = GradientDrawable.Orientation.TL_BR;
                    break;
            }
            drawable.setOrientation(mOrientation);

        }
    }

    private void stroke(Gradient gradient, GradientDrawable drawable) {
        if (mTypedArray.hasValue(R.styleable.background_yx_stroke_width)) {
            int start = 0;
            ArrayList<Integer> stateList = new ArrayList<>();
            ArrayList<Integer> colorList = new ArrayList<>();
            if (mTypedArray.hasValue(R.styleable.background_yx_pressed_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unPressed_stroke_color)) {
                stateList.add(android.R.attr.state_pressed);
                stateList.add(-android.R.attr.state_pressed);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_pressed_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unPressed_stroke_color, 0));
            }
            if (mTypedArray.hasValue(R.styleable.background_yx_checkable_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unCheckable_stroke_color)) {
                stateList.add(android.R.attr.state_checkable);
                stateList.add(-android.R.attr.state_checkable);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_checkable_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unCheckable_stroke_color, 0));
            }
            if (mTypedArray.hasValue(R.styleable.background_yx_checked_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unChecked_stroke_color)) {
                stateList.add(android.R.attr.state_checked);
                stateList.add(-android.R.attr.state_checked);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_checked_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unChecked_stroke_color, 0));
            }
            if (mTypedArray.hasValue(R.styleable.background_yx_enabled_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unEnabled_stroke_color)) {
                stateList.add(android.R.attr.state_enabled);
                stateList.add(-android.R.attr.state_enabled);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_enabled_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unEnabled_stroke_color, 0));
            }
            if (mTypedArray.hasValue(R.styleable.background_yx_selected_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unSelected_stroke_color)) {
                stateList.add(android.R.attr.state_selected);
                stateList.add(-android.R.attr.state_selected);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_selected_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unSelected_stroke_color, 0));
            }
            if (mTypedArray.hasValue(R.styleable.background_yx_focused_stroke_color) &&
                    mTypedArray.hasValue(R.styleable.background_yx_unFocused_stroke_color)) {
                stateList.add(android.R.attr.state_focused);
                stateList.add(-android.R.attr.state_focused);
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_focused_stroke_color, 0));
                colorList.add(mTypedArray.getColor(R.styleable.background_yx_unFocused_stroke_color, 0));
            }
            if (stateList.size() > 0) {
                int[][] state = new int[stateList.size()][];
                int[] color = new int[stateList.size()];
                for (int iState : stateList) {
                    state[start] = new int[]{iState};
                    color[start] = colorList.get(start);
                    start++;
                }
                ColorStateList colorStateList = new ColorStateList(state, color);
                drawable.setStroke((int) gradient.strokeWidth, colorStateList, gradient.strokeDashWidth, gradient.strokeGap);
            } else if (mTypedArray.hasValue(R.styleable.background_yx_stroke_color)) {
                drawable.setStroke((int) gradient.strokeWidth, gradient.strokeColor, gradient.strokeDashWidth, gradient.strokeGap);
            }

            stateList = null;
            colorList = null;
        }
    }

    private Gradient initGradient(GradientDrawable drawable) {
        Gradient gradient = new Gradient();
        for (int i = 0; i < mTypedArray.getIndexCount(); i++) {
            final int attr = mTypedArray.getIndex(i);
            switch (attr) {
                case R.styleable.background_yx_shape:
                    drawable.setShape(mTypedArray.getInt(attr, 0));
                    break;
                case R.styleable.background_yx_solid_color:
                    gradient.solidColor = mTypedArray.getColor(attr, 0);
                    break;
                case R.styleable.background_yx_corners_radius:
                    drawable.setCornerRadius(mTypedArray.getDimension(attr, 0));
                    break;
                case R.styleable.background_yx_corners_bottomLeftRadius:
                    gradient.cornerRadius[6] = mTypedArray.getDimension(attr, 0);
                    gradient.cornerRadius[7] = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_corners_bottomRightRadius:
                    gradient.cornerRadius[4] = mTypedArray.getDimension(attr, 0);
                    gradient.cornerRadius[5] = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_corners_topLeftRadius:
                    gradient.cornerRadius[0] = mTypedArray.getDimension(attr, 0);
                    gradient.cornerRadius[1] = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_corners_topRightRadius:
                    gradient.cornerRadius[2] = mTypedArray.getDimension(attr, 0);
                    gradient.cornerRadius[3] = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_gradient_angle:
                    gradient.gradientAngle = mTypedArray.getInteger(attr, 0);
                    break;
                case R.styleable.background_yx_gradient_centerX:
                    gradient.centerX = mTypedArray.getFloat(attr, -1);
                    break;
                case R.styleable.background_yx_gradient_centerY:
                    gradient.centerY = mTypedArray.getFloat(attr, -1);
                    break;
                case R.styleable.background_yx_gradient_centerColor:
                    gradient. centerColor = mTypedArray.getColor(attr, 0);
                    break;
                case  R.styleable.background_yx_gradient_endColor:
                    gradient.endColor = mTypedArray.getColor(attr, 0);
                    break;
                case R.styleable.background_yx_gradient_startColor:
                    gradient.startColor = mTypedArray.getColor(attr, 0);
                    break;
                case R.styleable.background_yx_gradient_gradientRadius:
                    drawable.setGradientRadius(mTypedArray.getDimension(attr, 0));
                    break;
                case R.styleable.background_yx_gradient_type:
                    gradient.gradientType = mTypedArray.getInt(attr, 0);
                    drawable.setGradientType(gradient.gradientType);
                    break;
                case R.styleable.background_yx_gradient_useLevel:
                    drawable.setUseLevel(mTypedArray.getBoolean(attr, false));
                    break;
                case R.styleable.background_yx_padding_left:
                    gradient.padding.left = (int) mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_padding_top:
                    gradient.padding.top = (int) mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_padding_right:
                    gradient.padding.right = (int) mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_padding_bottom:
                    gradient.padding.bottom = (int) mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_size_width:
                    gradient. sizeWidth = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_size_height:
                    gradient.sizeHeight = mTypedArray.getDimension(attr, 0);
                    break;

                case R.styleable.background_yx_stroke_width:
                    gradient.strokeWidth = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_stroke_color:
                    gradient. strokeColor = mTypedArray.getColor(attr, 0);
                    break;
                case R.styleable.background_yx_stroke_dashWidth:
                    gradient. strokeDashWidth = mTypedArray.getDimension(attr, 0);
                    break;
                case R.styleable.background_yx_stroke_dashGap:
                    gradient. strokeGap = mTypedArray.getDimension(attr, 0);
                    break;
            }
        }
        return gradient;
    }

    private void solid(Gradient gradient, GradientDrawable drawable) {
        int start = 0;
        ArrayList<Integer> stateList = new ArrayList<>();
        ArrayList<Integer> colorList = new ArrayList<>();
        if (mTypedArray.hasValue(R.styleable.background_yx_pressed_solid_color)) {
            stateList.add(android.R.attr.state_pressed);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_pressed_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unPressed_solid_color)) {
            stateList.add(-android.R.attr.state_pressed);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unPressed_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_checkable_solid_color)) {
            stateList.add(android.R.attr.state_checkable);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_checkable_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unCheckable_solid_color)) {
            stateList.add(-android.R.attr.state_checkable);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unCheckable_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_checked_solid_color)) {
            stateList.add(android.R.attr.state_checked);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_checked_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unChecked_solid_color)) {
            stateList.add(-android.R.attr.state_checked);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unChecked_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_enabled_solid_color)) {
            stateList.add(android.R.attr.state_enabled);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_enabled_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unEnabled_solid_color)) {
            stateList.add(-android.R.attr.state_enabled);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unEnabled_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_selected_solid_color)) {
            stateList.add(android.R.attr.state_selected);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_selected_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unSelected_solid_color)) {
            stateList.add(-android.R.attr.state_selected);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unSelected_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_focused_solid_color)) {
            stateList.add(android.R.attr.state_focused);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_focused_solid_color, 0));
        }
        if (mTypedArray.hasValue(R.styleable.background_yx_unFocused_solid_color)) {
            stateList.add(-android.R.attr.state_focused);
            colorList.add(mTypedArray.getColor(R.styleable.background_yx_unFocused_solid_color, 0));
        }

        if (stateList.size() > 0) {
            int size = stateList.size();
            if (mTypedArray.hasValue(R.styleable.background_yx_solid_color)) {
                size ++;
            }
            int[][] state = new int[size][];
            int[] color = new int[size];
            for (int iState : stateList) {
                state[start] = new int[]{iState};
                color[start] = colorList.get(start);
                start++;
            }

            if (mTypedArray.hasValue(R.styleable.background_yx_solid_color)) {
                state[start] = new int[]{};
                color[start] = gradient.solidColor;
            }

            ColorStateList colorStateList = new ColorStateList(state, color);
            drawable.setColor(colorStateList);
        } else if (mTypedArray.hasValue(R.styleable.background_yx_solid_color)) {
            drawable.setColor(gradient.solidColor);
        }
        stateList = null;
        colorList = null;
    }

    private boolean hasSetRadius(float[] radius) {
        boolean hasSet = false;
        for (float f : radius) {
            if (f != 0.0f) {
                hasSet = true;
                break;
            }
        }
        return hasSet;
    }
}
