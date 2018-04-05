package top.omooo.blackfish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/4/5.
 */

public class ClassifyTitleAdapter extends RecyclerView.Adapter<ClassifyTitleAdapter.TitleViewHolder> {

    private Context mContext;
    private ArrayList<String> mListTitle;

    public ClassifyTitleAdapter(Context context, ArrayList<String> listTitle) {
        mListTitle = new ArrayList<>();
        mContext = context;
        mListTitle = listTitle;
    }

    @Override
    public TitleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_classify_goods_title_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TitleViewHolder holder, int position) {
        holder.mTextTitle.setText(mListTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return mListTitle.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.tv_classify_goods_title_item);
        }
    }

}
