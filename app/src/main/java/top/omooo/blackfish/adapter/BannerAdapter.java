package top.omooo.blackfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import top.omooo.blackfish.R;
import top.omooo.blackfish.bean.BannerItemInfo;

/**
 * Created by SSC on 2018/3/2.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {

    private Context mContext;
    private List<BannerItemInfo> mItemInfos = new ArrayList<>();

    public BannerAdapter(Context context, List<BannerItemInfo> bannerItemInfos) {
        mContext = context;
        mItemInfos = bannerItemInfos;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_pager_banner_item, parent, false));

    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        int imageBannerId = mItemInfos.get(position).getImageBannerId();
        int imagepointId = mItemInfos.get(position).getImagePointId();
        int imageUnPointId = mItemInfos.get(position).getUnImagePointId();
        holder.mImageBanner.setImageResource(imageBannerId);
        holder.mImagePoint.setImageResource(imagepointId);
        holder.mImageUnPoint.setImageResource(imageUnPointId);
    }

    @Override
    public int getItemCount() {
        return mItemInfos.size();
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageBanner;
        public ImageView mImagePoint;
        public ImageView mImageUnPoint;
        public BannerViewHolder(View itemView) {
            super(itemView);
            mImageBanner = itemView.findViewById(R.id.iv_home_banner);
            mImagePoint = itemView.findViewById(R.id.iv_home_banner_point);
            mImageUnPoint = itemView.findViewById(R.id.iv_home_banner_point2);
        }
    }
}
