package com.overtech.hdd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.overtech.djtechlibrary.utils.Utilities;
import com.overtech.djtechlibrary.widget.tabview.TabView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TabView.OnTabChangeListener {

    private int mCurrentTabIndex;
    private int mPreviousTabIndex;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tabView)
    TabView tabView;

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mCurrentTabIndex = 0;
        mPreviousTabIndex = -1;
        tabView.setOnTabChangeListener(this);
        tabView.setCurrentTab(mCurrentTabIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChange(String tag) {
        if (tag != null) {
            if (tag.equals(getResources().getString(R.string.tab_text1))) {
                Utilities.showToast(getResources().getString(R.string.tab_text1), this);
            } else if (tag.equals(getResources().getString(R.string.tab_text2))) {
                Utilities.showToast(getResources().getString(R.string.tab_text2), this);
            } else if (tag.equals(getResources().getString(R.string.tab_text3))) {
                Utilities.showToast(getResources().getString(R.string.tab_text3), this);
            } else if (tag.equals(getResources().getString(R.string.tab_text4))) {
                Utilities.showToast(getResources().getString(R.string.tab_text4), this);
            }
        }
    }
}
