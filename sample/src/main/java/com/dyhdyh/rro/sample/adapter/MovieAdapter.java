package com.dyhdyh.rro.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.rro.sample.R;
import com.dyhdyh.rro.sample.model.HotMovieModel;

import java.util.List;

/**
 * author  dengyuhan
 * created 2017/1/13 1:54
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {
    private List<HotMovieModel.SubjectsEntity> mData;

    public MovieAdapter(List<HotMovieModel.SubjectsEntity> data) {
        this.mData = data;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        HotMovieModel.SubjectsEntity entity = this.mData.get(position);
        holder.tvTitle.setText(entity.getTitle());

        StringBuffer name = new StringBuffer();
        for (HotMovieModel.SubjectsEntity.CastsEntity cast : entity.getCasts()) {
            name.append(cast.getName());
            name.append("、");
        }
        name.deleteCharAt(name.length() - 1);
        holder.tvName.setText(String.format(holder.itemView.getResources().getString(R.string.text_actor_name), name));
    }

    @Override
    public int getItemCount() {
        return this.mData.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvName;

        public Holder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }

}
