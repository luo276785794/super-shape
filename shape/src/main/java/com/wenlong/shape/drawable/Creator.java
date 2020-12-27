package com.wenlong.shape.drawable;

/**
 * Created by Wenlong on 2020/6/29.
 */
public interface Creator<T> {
     T build() throws Exception;
}
