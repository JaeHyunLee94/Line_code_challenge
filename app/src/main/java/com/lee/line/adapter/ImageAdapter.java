package com.lee.line.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lee.line.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    ArrayList<String> img_list;
    Context context;

    OnitemLongClickInterface long_listner;
    OnitemClickInterface listner;


    public interface OnitemClickInterface {
        void onItemClick(View v, int pos);
    }

    public interface OnitemLongClickInterface {
        void onItemLongClick(View v, int pos);
    }


    public ImageAdapter(Context context, ArrayList<String> img_list, OnitemClickInterface listner, OnitemLongClickInterface long_listner) {
        this.context = context;
        this.img_list = img_list;
        this.long_listner = long_listner;
        this.listner = listner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(img_list.get(position)))
                .placeholder(R.drawable.ic_watch_later_indigo_100_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error_outline_red_600_24dp)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Toast.makeText(context,"이미지 로딩에 실패하였습니다. 인터넷 연결을 확인하거나 올바른 URL주소를 입력하세요",Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return img_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.grid_img);
            image.setClipToOutline(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        listner.onItemClick(v, pos);

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        long_listner.onItemLongClick(v,pos);



                    }
                    return true;
                }
            });


        }
    }
}
