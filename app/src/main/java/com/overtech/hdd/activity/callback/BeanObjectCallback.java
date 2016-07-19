package com.overtech.hdd.activity.callback;

import com.google.gson.Gson;
import com.overtech.hdd.debug.Logger;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Response;

/**
 * Created by Overtech on 16/7/15.
 */
public abstract class BeanObjectCallback extends Callback<Map<String,Object>> {
    @Override
    public Map<String,Object> parseNetworkResponse(Response response, int id) throws Exception {
        String json = response.body().string();
        Logger.e(json);
        Map<String,Object> map = new Gson().fromJson(json, Map.class);
        return map;
    }
}
