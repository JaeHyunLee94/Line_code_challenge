package com.lee.line.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.lee.line.R;
import com.lee.line.WriteActivity;

/*
 이미지 첨부 취소를 묻는 다이럴로그
 */

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

        cancel=findViewById(R.id.dialog_canel);
        delete=findViewById(R.id.dialog_remove_img);


        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){


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
