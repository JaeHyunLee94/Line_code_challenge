package com.lee.line;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lee.line.memo.Memo;

public class WriteActivity extends AppCompatActivity {

    Memo writing_mem0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
    }
}
