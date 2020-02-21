package com.lee.line.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lee.line.R;
import com.lee.line.WriteActivity;

public class SelectDialog extends Dialog {

    Context context;

    Button btn_camera;
    Button btn_album;
    Button btn_url;



    public SelectDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select);

        btn_camera=findViewById(R.id.btn_camera);
        btn_album=findViewById(R.id.btn_album);
        btn_url=findViewById(R.id.btn_url);

        setClickEvent();



    }

    private void setClickEvent() {
        btn_url.setOnClickListener((WriteActivity)context);
        btn_album.setOnClickListener((WriteActivity)context);
        btn_camera.setOnClickListener((WriteActivity)context);

    }





}
