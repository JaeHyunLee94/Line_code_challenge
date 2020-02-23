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
    WriteActivity 에서 이미지를 얻어오는 방법을 묻는 다이얼로그
 */

public class GetImageDialog extends Dialog implements View.OnClickListener {

    Context context;

    Button btn_camera;
    Button btn_album;
    Button btn_url;
    Button btn_cancel;

    int mode;



    public void show(int mode) {
        super.show();
        this.mode=mode;
    }

    public GetImageDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_get_image);

        btn_camera=findViewById(R.id.btn_camera);
        btn_album=findViewById(R.id.btn_album);
        btn_url=findViewById(R.id.btn_url);
        btn_cancel=findViewById(R.id.btn_cancel);


        setClickEvent();



    }

    private void setClickEvent() {
        btn_url.setOnClickListener(this);
        btn_album.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //이미지 추가 클릭이벤트
            case R.id.btn_camera:
                this.dismiss();
                ((WriteActivity)context).dispatchTakePictureIntent();
                break;

            case R.id.btn_album:
                this.dismiss();
                ((WriteActivity)context).goToAlbum();
                break;

            case R.id.btn_url:
                this.dismiss();
                ((WriteActivity)context).getURL();
                break;
            case R.id.btn_cancel:
                this.dismiss();
                break;
        }

    }
}
