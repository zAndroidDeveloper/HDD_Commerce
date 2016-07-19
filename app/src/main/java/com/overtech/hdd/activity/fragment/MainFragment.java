package com.overtech.hdd.activity.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.overtech.hdd.R;
import com.overtech.hdd.activity.adapter.MainAdapter;
import com.overtech.hdd.activity.base.BaseFragment;
import com.overtech.hdd.activity.callback.BeanArrayCallback;
import com.overtech.hdd.activity.callback.BeanObjectCallback;
import com.overtech.hdd.config.BizUrl;
import com.overtech.hdd.debug.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by Overtech on 16/7/14.
 */
public class MainFragment extends BaseFragment {
    MainAdapter adapter;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutIds() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        adapter = new MainAdapter(getActivity());
        loadLocalConfig();
        loadSubject();
        loadGoods();
    }

    private void loadLocalConfig() {
        AssetManager am = getActivity().getAssets();
        try {
            InputStream is = am.open("config.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            String json = sb.toString();
            Type type = new TypeToken<List<Map<String, Object>>>() {
            }.getType();
            List<Map<String, Object>> lists = new Gson().fromJson(json, type);
            adapter.setLocalConfig(lists);

//            recyclerView.setAdapter(adapter);//zhe ge di fang keneng buyong zheme gao

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGoods() {
        startProgressDialog();
        OkHttpUtils.get().url(BizUrl.MAIN_GOODS).addParams("page", "1").addParams("size", "50").build().execute(new BeanObjectCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                stopProgressDialog();
                Logger.e(e.getMessage());
            }

            @Override
            public void onResponse(Map<String, Object> response, int id) {
                stopProgressDialog();
                List<Map<String, Object>> goodsList = (List<Map<String, Object>>) response.get("goods_list");
                List<Map<String, Object>> homeRecommendSubjects = (List<Map<String, Object>>) response.get("home_recommend_subjects");
                Map<String, Object> homeSuperBrand = (Map<String, Object>) response.get("home_super_brand");
                List<Map<String, Object>> mobileAppGroups = (List<Map<String, Object>>) response.get("mobile_app_groups");
                adapter.setGoodsList(goodsList);
                adapter.setRecomendSubjects(homeRecommendSubjects);
                adapter.setMobileAppGroups(mobileAppGroups);
                adapter.setSuperBrand(homeSuperBrand);


                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void loadSubject() {
        startProgressDialog();
        OkHttpUtils.get().url(BizUrl.SUBJECTS).build().execute(new BeanArrayCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                stopProgressDialog();
                Logger.e(e.getMessage());
            }

            @Override
            public void onResponse(List<Map<String, Object>> response, int id) {
                stopProgressDialog();
                adapter.setCarouses(response);
                Logger.e("response的大小==" + response.size());
            }
        });


    }

}
