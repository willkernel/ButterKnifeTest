package com.willkernel.butterknifelibrary;

import android.view.View;

/**
 * Created by willkernel
 * on 2019/5/3.
 */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        doClick(v);
    }

    public abstract void doClick(View view);
}
