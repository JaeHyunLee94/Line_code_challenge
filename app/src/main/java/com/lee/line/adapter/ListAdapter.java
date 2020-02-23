package com.lee.line.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.lee.line.data.Memo;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Memo> datas;
    OnitemClickInterface listner;
    OnitemLongClickInterface long_listner;
    Context context;

    public interface OnitemClickInterface {
        void onItemClick(View v, int pos);
    }

    public interface OnitemLongClickInterface {
        void onItemLongClick(View v, int pos);
    }


    public ListAdapter(Context context,List<Memo> datas, OnitemClickInterface listner,OnitemLongClickInterface long_listner) {
        this.datas = datas;
        this.listner = listner;
        this.long_listner=long_listner;
        this.context=context;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.memo_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        ImageView thumbnail;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.title = itemView.findViewById(R.id.list_title);
            this.content = itemView.findViewById(R.id.list_content);
            this.thumbnail=itemView.findViewById(R.id.list_thumbnail);
            this.thumbnail.setClipToOutline(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        listner.onItemClick(v,pos);

//                        Memo clicked_memo = datas.get(pos);
//                        Log.e("clicked: ", "" + pos);



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

        public void bind(Memo m) {


            this.title.setText(m.getTitle());
            this.content.setText(m.getContent());

            if(!m.isImgEmpty()){

                Glide.with(itemView.getContext())
                        .load(Uri.parse(m.getImgThumbnail()))
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
                        .into(this.thumbnail);


            }

        }
    }
}
