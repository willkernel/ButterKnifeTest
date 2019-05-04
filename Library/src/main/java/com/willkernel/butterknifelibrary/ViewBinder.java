package com.willkernel.butterknifelibrary;

/**
 * Created by willkernel
 * on 2019/5/3.
 */
public interface ViewBinder<T> {
    void bind(T target);

    void unBind(T target);
}
