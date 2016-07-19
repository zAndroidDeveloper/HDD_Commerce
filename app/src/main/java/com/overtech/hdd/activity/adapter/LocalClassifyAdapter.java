package com.overtech.hdd.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.overtech.djtechlibrary.widget.bitmap.ImageLoader;
import com.overtech.hdd.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Overtech on 16/7/15.
 */
public class LocalClassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Map<String, Object>> datas;

    public LocalClassifyAdapter(Context context, List<Map<String, Object>> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LocalClassifyViewHolder viewHolder = new LocalClassifyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_local_classify_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LocalClassifyViewHolder classifyViewHolder= (LocalClassifyViewHolder) holder;
        Map<String,Object> data=datas.get(position);
        ImageLoader.getInstance().displayAssetImage(data.get("icon").toString(),classifyViewHolder.ivIcon);
        classifyViewHolder.tvName.setText(data.get("name").toString());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0:datas.size();
    }

    class LocalClassifyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tip)
        ImageView ivTip;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.iv_name)
        TextView tvName;
        public LocalClassifyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
