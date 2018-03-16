package top.omooo.blackfish.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/3/16.
 */

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.BannerViewHolder> {

    private Context mContext;
    private String[] imageUrl = new String[4];

    public HomeBannerAdapter(Context context, String[] imageUrl) {
        mContext = context;
        this.imageUrl = imageUrl;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_pager_banner_item, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BannerViewHolder holder, int position) {
        holder.mBannerView.setImageURI(imageUrl[position]);
        for (int i = 0; i < 4; i++) {
            if (position == 0) {
                holder.mPointView1.setBackgroundColor(Color.YELLOW);
                holder.mPointView2.setBackgroundColor(Color.GRAY);
                holder.mPointView3.setBackgroundColor(Color.GRAY);
                holder.mPointView4.setBackgroundColor(Color.GRAY);
            } else if (position == 1) {
                holder.mPointView1.setBackgroundColor(Color.GRAY);
                holder.mPointView2.setBackgroundColor(Color.YELLOW);
                holder.mPointView3.setBackgroundColor(Color.GRAY);
                holder.mPointView4.setBackgroundColor(Color.GRAY);
            } else if (position == 2) {
                holder.mPointView1.setBackgroundColor(Color.GRAY);
                holder.mPointView2.setBackgroundColor(Color.GRAY);
                holder.mPointView3.setBackgroundColor(Color.YELLOW);
                holder.mPointView4.setBackgroundColor(Color.GRAY);
            } else if (position == 3) {
                holder.mPointView1.setBackgroundColor(Color.GRAY);
                holder.mPointView2.setBackgroundColor(Color.GRAY);
                holder.mPointView3.setBackgroundColor(Color.GRAY);
                holder.mPointView4.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageUrl.length;
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView mBannerView;
        public ImageView mPointView1;
        public ImageView mPointView2;
        public ImageView mPointView3;
        public ImageView mPointView4;
        public BannerViewHolder(View itemView) {
            super(itemView);
            mBannerView = itemView.findViewById(R.id.iv_home_banner);
            mPointView1 = itemView.findViewById(R.id.iv_home_banner_point1);
            mPointView2 = itemView.findViewById(R.id.iv_home_banner_point2);
            mPointView3 = itemView.findViewById(R.id.iv_home_banner_point3);
            mPointView4 = itemView.findViewById(R.id.iv_home_banner_point4);

        }
    }
}
