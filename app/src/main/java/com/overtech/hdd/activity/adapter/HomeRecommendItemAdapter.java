package com.overtech.hdd.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.overtech.djtechlibrary.utils.Utilities;
import com.overtech.djtechlibrary.widget.bitmap.ImageLoader;
import com.overtech.hdd.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Overtech on 16/7/18.
 */
public class HomeRecommendItemAdapter extends RecyclerView.Adapter<HomeRecommendItemAdapter.RecommonedViewHolder> {
    private Context context;
    private List<Map<String, Object>> recommendGoods;

    public HomeRecommendItemAdapter(Context context, List<Map<String, Object>> recommendGoods) {
        this.context = context;
        this.recommendGoods = recommendGoods;
    }

    public void setData(List<Map<String, Object>> recommendGoods) {
        this.recommendGoods = recommendGoods;
    }

    @Override
    public RecommonedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecommonedViewHolder viewHolder = new RecommonedViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_recommoned_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecommonedViewHolder holder, int position) {
        Map<String, Object> data = recommendGoods.get(position);
        ImageLoader.getInstance().displayImage(data.get("thumb_url").toString(), holder.ivThumbUrl);
        holder.tvGoodsName.setText(data.get("goods_name").toString());
        holder.tvGoodsPrice.setText(Utilities.fen2yuan(Double.parseDouble(data.get("price").toString())));
    }

    @Override
    public int getItemCount() {
        return recommendGoods == null ? 0 : recommendGoods.size();
    }

    class RecommonedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_recommoned_item_url)
        ImageView ivThumbUrl;
        @BindView(R.id.tv_recommend_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_recommend_goods_price)
        TextView tvGoodsPrice;

        public RecommonedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
