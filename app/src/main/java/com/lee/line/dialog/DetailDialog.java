package com.lee.line.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lee.line.MainActivity;
import com.lee.line.R;



public class DetailDialog extends Dialog  {
    public DetailDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    Context context;
    Button btn_edit;
    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);

        btn_delete = findViewById(R.id.btn_delete);
        btn_edit = findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener((MainActivity)context);


    }


}
