package com.lee.line.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lee.line.R;
import com.lee.line.WriteActivity;

public class ChangeImageDialog extends Dialog implements View.OnClickListener {

    Button change_img;
    Button cancel;
    Button delete;

    Context context;

    int pos;




    public ChangeImageDialog(@NonNull Context context) {
        super(context);
        this.context=context;

    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_image);

//        change_img=findViewById(R.id.dialog_replace_img);
        cancel=findViewById(R.id.dialog_canel);
        delete=findViewById(R.id.dialog_remove_img);

//        change_img.setOnClickListener(this);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.dialog_replace_img:
//
//
//                this.dismiss();
//
//                break;

            case R.id.dialog_canel:

                this.dismiss();
                break;

            case R.id.dialog_remove_img:

                ((WriteActivity)context).remove_img(pos);

                this.dismiss();

                break;
        }
    }
}
