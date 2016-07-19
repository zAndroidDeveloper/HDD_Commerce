package com.overtech.hdd.activity.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.overtech.hdd.debug.Logger;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by Overtech on 16/7/15.
 */
public abstract class BeanArrayCallback extends Callback<List<Map<String,Object>>> {
    @Override
    public List<Map<String,Object>> parseNetworkResponse(Response response, int id) throws Exception {
        String json = response.body().string();
        Logger.e(json);
        Type jsonType=new TypeToken<List<Map<String,Object>>>(){}.getType();
        List<Map<String,Object>> object = new Gson().fromJson(json,jsonType );
        return object;
    }
}
