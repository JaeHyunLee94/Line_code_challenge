package com.lee.line.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.line.dialog.DetailDialog;
import com.lee.line.R;
import com.lee.line.data.Memo;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    List<Memo> datas;
    DetailDialog dialog;

    public ListAdapter(List<Memo> datas,DetailDialog dialog){
        this.datas=datas;
        this.dialog=dialog;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.memo_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView content;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.title=(TextView)itemView.findViewById(R.id.list_title);
            this.content=(TextView) itemView.findViewById(R.id.list_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Memo clicked_memo=datas.get(pos);
                        Log.e("clicked: ",""+pos);
                        dialog.show();

                    }

                }
            });

        }

        public void bind(Memo m) {
            Log.e("bind",m.getTitle());
            this.title.setText(m.getTitle());
            this.content.setText(m.getContent());
        }
    }
}
