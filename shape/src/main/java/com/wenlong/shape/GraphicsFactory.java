package com.wenlong.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.wenlong.shape.R;
import com.wenlong.shape.drawable.DrawableFactory;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * Created by Wenlong on 2020/6/28.
 */
public class GraphicsFactory implements LayoutInflater.Factory2 {

    private static final String SHAPE_VIEW_PACKAGE = "com.wenlong.shape.view";
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final int LEFT = 1 << 1;
    private static final int TOP = 1 << 2;
    private static final int RIGHT = 1 << 3;
    private static final int BOTTOM = 1 << 4;

    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final Object[] mConstructorArgs = new Object[2];
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();

    private LayoutInflater.Factory mDelegateFactory;
    private LayoutInflater.Factory2 mDelegateFactory2;

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return onCreateView(name, context, attrs);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (name.startsWith(SHAPE_VIEW_PACKAGE)) {
            return null;
        }
        View view = onDelegateCreateView(name, context, attrs);
        return setViewBackground(name, context, attrs, view);
    }

    private View onDelegateCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        //防止与其他调用factory库冲突，例如字体、皮肤替换库，用已经设置的factory来创建view
        if (mDelegateFactory2 != null) {
            view = mDelegateFactory2.onCreateView(name, context, attrs);
            if (view == null) {
                view = mDelegateFactory2.onCreateView(null, name, context, attrs);
            }
        } else if (mDelegateFactory != null) {
            view = mDelegateFactory.onCreateView(name, context, attrs);
        }

        return view;
    }

    @Nullable
    public static View setViewBackground(Context context, AttributeSet attrs, View view){
        return setViewBackground(null, context, attrs, view);
    }

    private static View setViewBackground(String name, Context context, AttributeSet attrs, View view) {
        TypedArray backgroundTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background);
        TypedArray pressTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background_press);
        TypedArray selectorTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background_selector);
        TypedArray textTypedArray = context.obtainStyledAttributes(attrs, R.styleable.text_selector);
        TypedArray otherTypedArray = context.obtainStyledAttributes(attrs, R.styleable.yx_other);
        TypedArray buttonTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background_button_drawable);
        TypedArray multiSelectorTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background_multi_selector);
        TypedArray multiTextTypedArray = context.obtainStyledAttributes(attrs, R.styleable.background_multi_selector_text);
        try {
            if (!hasTypedArray(backgroundTypedArray, pressTypedArray, selectorTypedArray, textTypedArray,
                    buttonTypedArray, multiSelectorTypedArray, multiTextTypedArray)) {
                return view;
            }
            if (view == null) {
                view = createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            //R.styleable.background_selector 和 R.styleable.background_multi_selector的属性不能同时使用
            if (selectorTypedArray.getIndexCount() > 0 && multiSelectorTypedArray.getIndexCount() > 0) {
                throw new IllegalArgumentException("background_selector and background_multi_selector cannot be used simultaneously");
            }
            if (textTypedArray.getIndexCount() > 0 && multiTextTypedArray.getIndexCount() > 0) {
                throw new IllegalArgumentException("text_selector and background_multi_selector_text cannot be used simultaneously");
            }

            GradientDrawable drawable;
            StateListDrawable stateListDrawable;
            if (isClickable(pressTypedArray, selectorTypedArray, buttonTypedArray)) {
                view.setClickable(true);
            }
            if (buttonTypedArray.getIndexCount() > 0 && view instanceof CompoundButton) {
                ((CompoundButton) view).setButtonDrawable(DrawableFactory.getButtonDrawable(backgroundTypedArray, buttonTypedArray));
            } else if (selectorTypedArray.getIndexCount() > 0) {
                stateListDrawable = DrawableFactory.getSelectorDrawable(backgroundTypedArray, selectorTypedArray);
                setDrawable(stateListDrawable, view, otherTypedArray, backgroundTypedArray);

            } else if (pressTypedArray.getIndexCount() > 0) {

                drawable = DrawableFactory.getDrawable(backgroundTypedArray);
                stateListDrawable = DrawableFactory.getPressDrawable(drawable, backgroundTypedArray, pressTypedArray);
                setDrawable(stateListDrawable, view, otherTypedArray, backgroundTypedArray);

            } else if (multiSelectorTypedArray.getIndexCount() > 0) {

                stateListDrawable = DrawableFactory.getMultiSelectorDrawable(context, multiSelectorTypedArray, backgroundTypedArray);
                setBackground(stateListDrawable, view, backgroundTypedArray);

            } else if (backgroundTypedArray.getIndexCount() > 0) {
                drawable = DrawableFactory.getDrawable(backgroundTypedArray);
                setDrawable(drawable, view, otherTypedArray, backgroundTypedArray);

            }

            return view;

        } catch (Exception e) {
            e.printStackTrace();
            return view;
        } finally {
            backgroundTypedArray.recycle();
            pressTypedArray.recycle();
            selectorTypedArray.recycle();
            textTypedArray.recycle();
            buttonTypedArray.recycle();
            multiSelectorTypedArray.recycle();
            multiTextTypedArray.recycle();
        }
    }

    private static void setDrawable(Drawable drawable, View view, TypedArray otherTa, TypedArray typedArray) {

        if (view instanceof TextView) {
            if (otherTa.hasValue(R.styleable.yx_other_yx_position)) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                final int position = otherTa.getInt(R.styleable.yx_other_yx_position, 0);
                final TextView textView = (TextView) view;
                switch (position) {
                    case LEFT:
                        textView.setCompoundDrawables(drawable, null, null, null);
                        break;
                    case TOP:
                        textView.setCompoundDrawables(null, drawable, null, null);
                        break;
                    case RIGHT:
                        textView.setCompoundDrawables(null, null, drawable, null);
                        break;
                    case BOTTOM:
                        textView.setCompoundDrawables(null, null, null, drawable);
                        break;
                }
            } else {
                setBackground(drawable, view, typedArray);
            }
        } else {
            setBackground(drawable, view, typedArray);
        }

    }

    private static void setBackground(Drawable drawable, View view, TypedArray typedArray) {
        if (typedArray.hasValue(R.styleable.background_yx_stroke_width) && typedArray.hasValue(R.styleable.background_yx_stroke_position)) {

            float width = typedArray.getDimension(R.styleable.background_yx_stroke_width, 0f);
            int position = typedArray.getInt(R.styleable.background_yx_stroke_position, 0);
            float leftValue = hasStatus(position, LEFT) ? width : -width;
            float topValue = hasStatus(position, TOP) ? width : -width;
            float rightValue = hasStatus(position, RIGHT) ? width : -width;
            float bottomValue = hasStatus(position, BOTTOM) ? width : -width;
            drawable = new LayerDrawable(new Drawable[]{drawable});
            ((LayerDrawable) drawable).setLayerInset(0, (int) leftValue, (int) topValue, (int) rightValue, (int) bottomValue);
        }

        view.setBackground(drawable);
    }

    private static boolean hasStatus(int flag, int status) {
        return (flag & status) == status;
    }


    private static boolean isClickable(TypedArray... typedArray) {
        for (TypedArray array : typedArray) {
            if (array.getIndexCount() > 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasTypedArray(TypedArray... typedArray) {
        for (TypedArray array : typedArray) {
            if (array.getIndexCount() > 0) {
                return true;
            }
        }
        return false;
    }

    public void setDelegateFactory(LayoutInflater.Factory factory) {
        this.mDelegateFactory = factory;
    }

    public void setDelegateFactory2(LayoutInflater.Factory2 factory) {
        this.mDelegateFactory2 = factory;
    }

    private static View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (String s : sClassPrefixList) {
                    final View view = createView(context, name, s);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private static View createView(Context context, String name, String prefix) throws InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }


}
