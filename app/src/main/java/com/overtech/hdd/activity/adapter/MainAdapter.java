package com.overtech.hdd.activity.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.overtech.djtechlibrary.utils.Utilities;
import com.overtech.djtechlibrary.widget.bitmap.ImageLoader;
import com.overtech.hdd.R;
import com.overtech.hdd.activity.MainActivity;
import com.overtech.hdd.debug.Logger;
import com.overtech.hdd.widget.cyclerviewpager.CycleViewPager;
import com.overtech.hdd.widget.cyclerviewpager.ViewFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Overtech on 16/7/14.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HOME_CAROUSEL = 100;
    public static final int TYPE_HOME_LOCAL_CLASSIFY = 101;
    public static final int TYPE_HOME_APP_SPECIAL = 102;
    public static final int TYPE_HOME_NORMAL_GOODS = 103;
    public static final int TYPE_HOME_SUPER_BRAND = 104;
    public static final int TYPE_HOME_RECOMMEND = 105;
    public static final int TYPE_LOADING_MORE = 110;

    public static final int PULL_LOAD_MORE = 120;
    public static final int LOADING_MORE = 121;
    private int currentLoadState;
    private List<Map<String, Object>> carouses;
    private List<Map<String, Object>> localConfig;
    private List<Map<String, Object>> goodsList;
    private List<Map<String, Object>> recomendSubjects;
    private Map<String, Object> superBrand;
    private List<Map<String, Object>> mobileAppGroups;

    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setCarouses(List<Map<String, Object>> carouses) {
        this.carouses = carouses;
    }

    public void setLocalConfig(List<Map<String, Object>> localConfig) {
        this.localConfig = localConfig;
    }

    public void setGoodsList(List<Map<String, Object>> goodsList) {
        this.goodsList = goodsList;
    }

    public void setRecomendSubjects(List<Map<String, Object>> recomendSubjects) {
        this.recomendSubjects = recomendSubjects;
    }

    public void setSuperBrand(Map<String, Object> superBrand) {
        this.superBrand = superBrand;
    }

    public void setMobileAppGroups(List<Map<String, Object>> mobileAppGroups) {
        this.mobileAppGroups = mobileAppGroups;
    }

    public void changeLoadMoreStatus(int status) {
        this.currentLoadState = status;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == TYPE_HOME_CAROUSEL) {
            vh = new HomeCarouselViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_carousel, parent, false));
        } else if (viewType == TYPE_HOME_LOCAL_CLASSIFY) {
            vh = new HomeLocalClassifyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_localclassify, parent, false));
        } else if (viewType == TYPE_HOME_APP_SPECIAL) {
            vh = new HomeAppSpecialViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_app_special, parent, false));
        } else if (viewType == TYPE_HOME_SUPER_BRAND) {
            vh = new HomeSuperViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_superbrand, parent, false));
        } else if (viewType == TYPE_HOME_RECOMMEND) {
            vh = new HomeRecommendViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_recommoned_subjects, parent, false));
        } else if (viewType == TYPE_LOADING_MORE) {
            vh = new LoadingMoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_loading_more, parent, false));
        } else {
            vh = new HomeGoodsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_home_goods, parent, false));
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Logger.e("onBindViewHolder执行的频率。。。");
        if (holder instanceof HomeCarouselViewHolder) {
            HomeCarouselViewHolder carouselViewHolder = (HomeCarouselViewHolder) holder;
            CycleViewPager cycleViewPager = carouselViewHolder.cycleViewPager;
            cycleViewPager.setTime(5000);
            cycleViewPager.setIndicatorCenter();
            if (cycleViewPager.getData() != null) {
                cycleViewPager.refreshData();
                return;
            }
            if (carouses != null) {
                List<ImageView> views = new ArrayList<ImageView>();
                for (Map<String, Object> map : carouses) {
                    views.add(ViewFactory.getImageView(context, map.get("home_banner").toString()));
                }
                cycleViewPager.setData(views, carouses, new CycleViewPager.ImageCycleViewListener() {
                    @Override
                    public void onImageClick(String info, int position, View imageView) {
                        Utilities.showToast(info + "==" + position, context);
                    }
                });
            }

        } else if (holder instanceof HomeLocalClassifyViewHolder) {

            HomeLocalClassifyViewHolder localClassifyViewHolder = (HomeLocalClassifyViewHolder) holder;
            if (localClassifyViewHolder.recyclerViewLocalClassify.getAdapter() == null) {
                ((LinearLayoutManager) localClassifyViewHolder.recyclerViewLocalClassify.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
                LocalClassifyAdapter localClassifyAdapter = new LocalClassifyAdapter(context, localConfig);
                localClassifyViewHolder.recyclerViewLocalClassify.setAdapter(localClassifyAdapter);
            }
        } else if (holder instanceof HomeAppSpecialViewHolder) {
            HomeAppSpecialViewHolder homeAppSpecialViewHolder = (HomeAppSpecialViewHolder) holder;
            Map<String, Object> data = mobileAppGroups.get(0);
            ImageLoader.getInstance().displayImage(data.get("thumb_url").toString(), homeAppSpecialViewHolder.ivThumbUrl);
            homeAppSpecialViewHolder.tvDesc.setText(data.get("desc").toString());
//            homeAppSpecialViewHolder.etCode //app专享码
            homeAppSpecialViewHolder.btConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.showToast("点我干嘛", context);
                }
            });
        } else if (holder instanceof HomeSuperViewHolder) {
            HomeSuperViewHolder homeSuperViewHolder = (HomeSuperViewHolder) holder;
            List<Map<String, Object>> superBrandGoodsList = (List<Map<String, Object>>) superBrand.get("goods_list");
            Map<String, Object> goods1 = superBrandGoodsList.get(0);
            Map<String, Object> goods2 = superBrandGoodsList.get(1);
            Map<String, Object> goods3 = superBrandGoodsList.get(2);
            ImageLoader.getInstance().displayImage(goods1.get("thumb_url").toString(), homeSuperViewHolder.ivSuperBrandGoods1);
            ImageLoader.getInstance().displayImage(goods2.get("thumb_url").toString(), homeSuperViewHolder.ivSuperBrandGoods2);
            ImageLoader.getInstance().displayImage(goods3.get("thumb_url").toString(), homeSuperViewHolder.ivSuperBrandGoods3);

            homeSuperViewHolder.tvSuperBrandGoodsPrice1.setText(Utilities.fen2yuan(Double.parseDouble(goods1.get("price").toString())));
            homeSuperViewHolder.tvSuperBrandGoodsPrice2.setText(Utilities.fen2yuan(Double.parseDouble(goods2.get("price").toString())));
            homeSuperViewHolder.tvSuperBrandGoodsPrice3.setText(Utilities.fen2yuan(Double.parseDouble(goods3.get("price").toString())));
        } else if (holder instanceof HomeRecommendViewHolder) {
            HomeRecommendViewHolder homeRecommendViewHolder = (HomeRecommendViewHolder) holder;
            Map<String, Object> recommonedSubject = recomendSubjects.get((position - 12) / 5);
            homeRecommendViewHolder.tvDesc.setText(recommonedSubject.get("desc").toString());
            homeRecommendViewHolder.tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.showToast("你要查看什么。。", context);
                }
            });
            ((LinearLayoutManager) homeRecommendViewHolder.rvRecommondGoods.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
            if (homeRecommendViewHolder.rvRecommondGoods.getAdapter() != null) {
                HomeRecommendItemAdapter adapter = (HomeRecommendItemAdapter) homeRecommendViewHolder.rvRecommondGoods.getAdapter();
                List<Map<String, Object>> goodsList = (List<Map<String, Object>>) recommonedSubject.get("goods_list");
                adapter.setData(goodsList);
                adapter.notifyDataSetChanged();
            } else {
                List<Map<String, Object>> goodsList = (List<Map<String, Object>>) recommonedSubject.get("goods_list");
                HomeRecommendItemAdapter adapter = new HomeRecommendItemAdapter(context, goodsList);
                homeRecommendViewHolder.rvRecommondGoods.setAdapter(adapter);
            }

        } else if (holder instanceof HomeGoodsViewHolder) {
            HomeGoodsViewHolder vh = (HomeGoodsViewHolder) holder;
            Map<String, Object> data = null;//position 的位置和集合中的位置之间的关系
            if ((position - 3) * 4 % 5 == 0) {
                data = goodsList.get((position - 3) * 4 / 5);
            } else {
                data = goodsList.get((position - 3) * 4 / 5 + 1);
            }

            ImageLoader.getInstance().displayImage(data.get("image_url").toString(), vh.ivGoodsImage);
            vh.tvGoodsName.setText(data.get("goods_name").toString());
            Map<String, Object> groupMap = (Map<String, Object>) data.get("group");
            vh.tvGoodsGroup.setText(groupMap.get("customer_num").toString() + "人团");
            vh.tvGroupPrice.setText("￥ " + Utilities.fen2yuan(Double.parseDouble(groupMap.get("price").toString())));
            vh.tvNormalPrice.setText("单买价￥" + Utilities.fen2yuan(Double.parseDouble(data.get("normal_price").toString())));
        } else if (holder instanceof LoadingMoreViewHolder) {
            LoadingMoreViewHolder vh = (LoadingMoreViewHolder) holder;
            if (currentLoadState == PULL_LOAD_MORE) {
                vh.tvLoadStatus.setText("上拉加载更多");
            } else if (currentLoadState == LOADING_MORE) {
                vh.tvLoadStatus.setText("正在加载中...");
            } else {
                vh.tvLoadStatus.setText("查看更多");
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HOME_CAROUSEL;
        } else if (position == 1) {
            return TYPE_HOME_LOCAL_CLASSIFY;
        } else if (position == 2) {
            return TYPE_HOME_APP_SPECIAL;
        } else if (position % 5 == 2 && position == 7) {
            return TYPE_HOME_SUPER_BRAND;
        } else if (position % 5 == 2) {
            return TYPE_HOME_RECOMMEND;
        } else if (position == getItemCount() - 1) {
            return TYPE_LOADING_MORE;
        } else {
            return TYPE_HOME_NORMAL_GOODS;
        }
    }

    @Override
    public int getItemCount() {
        //目前假设可以拿到所有的数据
        if (recomendSubjects != null && goodsList != null) {
            return recomendSubjects.size() + goodsList.size() + 5;
        } else {
            if (recomendSubjects != null) {
                return recomendSubjects.size() + 5;
            }
            if (goodsList != null) {
                return goodsList.size();
            }
            return 5;
        }
    }

    class HomeCarouselViewHolder extends RecyclerView.ViewHolder {
        CycleViewPager cycleViewPager;

        public HomeCarouselViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cycleViewPager = (CycleViewPager) ((MainActivity) context)
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_cycle_viewpager);
        }
    }

    class HomeLocalClassifyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerViewLocalClassify)
        RecyclerView recyclerViewLocalClassify;

        public HomeLocalClassifyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeAppSpecialViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_mobile_app_group_thumb_url)
        ImageView ivThumbUrl;
        @BindView(R.id.tv_mobile_app_group_desc)
        TextView tvDesc;
        @BindView(R.id.et_mobile_app_group_code)
        EditText etCode;
        @BindView(R.id.bt_mobile_app_group_confirm_join)
        Button btConfirm;

        public HomeAppSpecialViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeSuperViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_super_brand_end_time)
        TextView tvSuperBrandEndTime;

        @BindView(R.id.iv_super_brand_goods1)
        ImageView ivSuperBrandGoods1;
        @BindView(R.id.iv_super_brand_goods2)
        ImageView ivSuperBrandGoods2;
        @BindView(R.id.iv_super_brand_goods3)
        ImageView ivSuperBrandGoods3;

        @BindView(R.id.tv_super_brand_goods_price1)
        TextView tvSuperBrandGoodsPrice1;
        @BindView(R.id.tv_super_brand_goods_price2)
        TextView tvSuperBrandGoodsPrice2;
        @BindView(R.id.tv_super_brand_goods_price3)
        TextView tvSuperBrandGoodsPrice3;

        public HomeSuperViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeRecommendViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recommend_sub_desc)
        TextView tvDesc;
        @BindView(R.id.tv_recommend_sub_more)
        TextView tvMore;
        @BindView(R.id.recyclerView_recommend_sub_goods)
        RecyclerView rvRecommondGoods;

        public HomeRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HomeGoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_image_url)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_group)
        TextView tvGoodsGroup;
        @BindView(R.id.tv_group_price)
        TextView tvGroupPrice;
        @BindView(R.id.tv_goods_normal_price)
        TextView tvNormalPrice;
        @BindView(R.id.bt_kaituan)
        Button btKaituan;

        public HomeGoodsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LoadingMoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_load_status)
        TextView tvLoadStatus;

        public LoadingMoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
