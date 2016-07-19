package com.overtech.hdd.widget.cyclerviewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.overtech.hdd.R;
import com.overtech.hdd.debug.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现可循环，可轮播的viewpager
 */
@SuppressLint("NewApi")
public class CycleViewPager extends Fragment implements OnPageChangeListener {

    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private ImageView[] indicators;
    private FrameLayout viewPagerFragmentLayout;
    private LinearLayout indicatorLayout; // 指示器
    private BaseViewPager viewPager;
    private ViewPagerAdapter adapter;
    private int time = 5000; // 默认轮播时间
    /**
     * 轮播图当前位置
     */
    private final int SCROLLING = 0x0001;
    private int currentPosition = 0;
    private ImageCycleViewListener mImageCycleViewListener;
    private List<Map<String, Object>> infos;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCROLLING:
                    currentPosition++;
                    Logger.e("cycylerViewPager==" + currentPosition);
                    if (currentPosition >= imageViews.size()) {
                        viewPager.setCurrentItem(0, true);// 此处如果改为false，下面的onPageScrollStateChanged
                        // 将不会执行，handle将不会重新执行任务，
                    } else {
                        viewPager.setCurrentItem(currentPosition, true);
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_cycle_viewpager_contet, container,false);
        viewPager = (BaseViewPager) view.findViewById(R.id.viewPager);
        indicatorLayout = (LinearLayout) view
                .findViewById(R.id.layout_viewpager_indicator);

        viewPagerFragmentLayout = (FrameLayout) view
                .findViewById(R.id.layout_viewager_content);

        return view;
    }

    public List<Map<String, Object>> getData() {
        return infos;
    }

    public void setData(List<ImageView> views, List<Map<String, Object>> list,
                        ImageCycleViewListener listener) {
        setData(views, list, listener, 0);
    }

    /**
     * 初始化viewpager
     *
     * @param views        要显示的views
     * @param showPosition 默认显示位置
     */
    public void setData(List<ImageView> views, List<Map<String, Object>> list,
                        ImageCycleViewListener listener, int showPosition) {
        mImageCycleViewListener = listener;
        infos = list;
        this.imageViews.clear();

        if (views.size() == 0) {
            viewPagerFragmentLayout.setVisibility(View.GONE);
            return;
        }

        for (ImageView item : views) {
            this.imageViews.add(item);
        }

        int ivSize = views.size();

        // 设置指示器
        indicators = new ImageView[ivSize];

        indicatorLayout.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.view_cycle_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.image_indicator);
            indicatorLayout.addView(view);
        }

        adapter = new ViewPagerAdapter();

        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);

        viewPager.setOffscreenPageLimit(ivSize);
        viewPager.setOnPageChangeListener(this);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(showPosition);

        handler.postDelayed(runnable, time);

    }

    /**
     * 设置指示器居中，默认指示器在右方
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        indicatorLayout.setLayoutParams(params);
    }

    /**
     * 提供外部控制
     *
     * @return
     */
    public Runnable getRunnable() {
        return runnable;
    }

    /**
     * @return
     */
    public Handler getHandler() {
        return handler;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (getActivity() != null && !getActivity().isFinishing()) {
                handler.sendEmptyMessage(SCROLLING);
            }
        }
    };

    /**
     * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
     */
    public void releaseHeight() {
        getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
        refreshData();
    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认5000ms
     *
     * @param time 毫秒为单位
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 隐藏CycleViewPager
     */
    public void hide() {
        viewPagerFragmentLayout.setVisibility(View.GONE);
    }

    /**
     * 返回内置的viewpager
     *
     * @return viewPager
     */
    public BaseViewPager getViewPager() {
        return viewPager;
    }

    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews == null ? 0 : imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            // Log.e("==cycleViewPager  destroyItem==", position + "");
            ImageView image = (ImageView) object;
            container.removeView(image);
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView v = imageViews.get(position);
            // Log.e("==instantiate==", position + "");
            if (mImageCycleViewListener != null) {
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mImageCycleViewListener.onImageClick(
                                infos.get(currentPosition).get("home_banner").toString(), currentPosition, v);
                    }
                });
            }
            container.addView(v);

            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        // Log.e("==onPageScrollState==", "==onPageScrollState==");
        if (state == ViewPager.SCROLL_STATE_DRAGGING) { // viewPager在滚动
            handler.removeCallbacks(runnable);// 当拖动时取消之前轮播的handle任务
            return;
        } else if (state == ViewPager.SCROLL_STATE_IDLE) { // viewPager滚动结束
            handler.postDelayed(runnable, time);// 滚动结束后再次发送handle请求下一次轮播
        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {

        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        // Log.e("==onPageSelected==", "==onPageSelected==");
        currentPosition = position;
        setIndicator(currentPosition);
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(R.mipmap.icon_adv_point);
        }
        indicators[selectedPosition]
                .setBackgroundResource(R.mipmap.icon_adv_point_pre);
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public static interface ImageCycleViewListener {

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        public void onImageClick(String info, int position, View imageView);
    }
}