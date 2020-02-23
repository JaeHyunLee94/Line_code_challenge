package com.lee.line.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

        Glide.with(holder.itemView.getContext()).load(Uri.parse(img_list.get(position))).into(holder.image);

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
