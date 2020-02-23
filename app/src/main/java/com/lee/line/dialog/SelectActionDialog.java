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

/*
메인 액티비티에서
메모를 삭제할 건지 묻는 다이얼로그
 */

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
