package com.lee.line.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lee.line.R;
import com.lee.line.adapter.ListAdapter;
import com.lee.line.data.Memo;

import java.util.ArrayList;

public class SelectActionDialog extends Dialog implements View.OnClickListener {
    Context context;
    Button delete_btn;
    Button cancel_btn;
    ArrayList<Memo> memo_list;
    ListAdapter adapter;

    int pos;
    public SelectActionDialog(@NonNull Context context, int pos, ArrayList<Memo> memo_list, ListAdapter adapter) {
        super(context);
        this.context=context;
        this.pos=pos;
        this.memo_list=memo_list;
        this.adapter=adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_action);
        delete_btn=findViewById(R.id.dialog_btn_delete);
        cancel_btn=findViewById(R.id.dialog_btn_canel);

        delete_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.dialog_btn_delete) {

            memo_list.remove(pos);
            adapter.notifyItemRemoved(pos);

        }

        this.dismiss();
    }
}
