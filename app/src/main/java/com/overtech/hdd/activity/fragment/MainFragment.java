package com.overtech.hdd.activity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.overtech.hdd.R;
import com.overtech.hdd.activity.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Overtech on 16/7/14.
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.textView)
    TextView textView;
    @Override
    protected int getLayoutIds() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }
    @OnClick(R.id.button)
    public void onClick(View view){
        textView.setText("wo ai ni");
    }
}
