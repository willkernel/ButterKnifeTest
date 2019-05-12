package com.willkernel.butterknifetest;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.willkernel.annotation.BindView;
import com.willkernel.annotation.OnClick;
import com.willkernel.annotation.OnLongClick;
import com.willkernel.butterknifelibrary.ButterKnife;


//ButterKnife 加载临时创建的类 MainActivity_ViewBinding
// 工具类Utils
// public static View findRequiredView(View source, @IdRes int id, String who) {
//    View view = source.findViewById(id);
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        com.netease.butterknife.library.ButterKnife.bind(this);
    }


    @OnClick({R.id.tv1, R.id.tv2})
    public void click(View view) {
        Toast.makeText(this, "butter", Toast.LENGTH_LONG).show();
    }

    @OnLongClick({R.id.tv1, R.id.tv2})
    public void longClick(View view) {
        Toast.makeText(this, "longClick", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unBind(this);
    }
}
