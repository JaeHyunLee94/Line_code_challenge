package com.lee.line;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullScreenActivity extends AppCompatActivity {

    ImageView img_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        img_view=findViewById(R.id.full_screen_img);
        Intent intent=getIntent();
        String uri=intent.getStringExtra("imguri");
        Glide.with(this).load(Uri.parse(uri)).into(img_view);
    }
}
