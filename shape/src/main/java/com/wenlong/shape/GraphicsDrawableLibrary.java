package com.wenlong.shape;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;

/**
 * Created by Wenlong on 2020/6/28.
 */
public class GraphicsDrawableLibrary {

    public static LayoutInflater inject(Context context) {
        LayoutInflater inflater = getLayoutInflater(context);
        if (inflater == null) {
            return null;
        }
        if (inflater.getFactory2() == null) {
            GraphicsFactory factory = injectDelegateFactory(context);
            inflater.setFactory2(factory);
        } else if (!(inflater.getFactory2() instanceof GraphicsFactory)) {
            forceSetFactory2(inflater);
        }
        return inflater;
    }

    private static GraphicsFactory injectDelegateFactory(Context context) {
        GraphicsFactory factory = new GraphicsFactory();
        if (context instanceof AppCompatActivity) {
            final AppCompatDelegate delegate = ((AppCompatActivity) context).getDelegate();
            factory.setDelegateFactory(delegateFactory(delegate));
        }
        return factory;
    }

    private static LayoutInflater.Factory delegateFactory(final AppCompatDelegate delegate) {
        return (name, context, attrs) -> delegate.createView(null, name, context, attrs);
    }

    private static LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater inflater;
        if (context instanceof Activity) {
            inflater = ((Activity) context).getLayoutInflater();
        } else {
            inflater = LayoutInflater.from(context);
        }
        return inflater;
    }

    private static void forceSetFactory2(LayoutInflater inflater) {
        Class<LayoutInflaterCompat> compatClass = LayoutInflaterCompat.class;
        Class<LayoutInflater> inflaterClass = LayoutInflater.class;
        try {

            Field sCheckedField = compatClass.getDeclaredField("sCheckedField");
            sCheckedField.setAccessible(true);
            sCheckedField.setBoolean(inflater, false);

            Field mFactory = inflaterClass.getDeclaredField("mFactory");
            mFactory.setAccessible(true);
            Field mFactory2 = inflaterClass.getDeclaredField("mFactory2");
            mFactory2.setAccessible(true);

            GraphicsFactory factory = new GraphicsFactory();

            if (inflater.getFactory2() != null) {
                factory.setDelegateFactory2(inflater.getFactory2());
            } else if (inflater.getFactory() != null) {
                factory.setDelegateFactory(inflater.getFactory());
            }
            mFactory2.set(inflater, factory);
            mFactory.set(inflater, factory);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
