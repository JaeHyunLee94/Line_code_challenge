package com.lee.line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button btn_edit;
    Button btn_delete;

    TextView title_box;
    TextView content_box;

    String title;
    String content;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_delete = findViewById(R.id.detail_btn_delete);
        btn_edit = findViewById(R.id.detail_btn_edit);
        title_box = findViewById(R.id.detail_title);
        content_box = findViewById(R.id.detail_content);

        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        Intent arrived_intent = getIntent();

        title = arrived_intent.getStringExtra("title");
        content = arrived_intent.getStringExtra("content");
        pos = arrived_intent.getIntExtra("pos", -1); //에러 처

        title_box.setText(title);
        content_box.setText(content);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ResultCode.RESULT_EDIT_COMPLETED) {

            title = data.getStringExtra("title");
            content = data.getStringExtra("content");

            title_box.setText(title);
            content_box.setText(content);


            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("pos", pos);
            setResult(resultCode, intent);
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.detail_btn_edit:

                Toast.makeText(this, "clock", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, WriteActivity.class);

                intent.putExtra("REQUEST_CODE", RequestCode.REQUEST_EDIT_MEMO);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                startActivityForResult(intent, RequestCode.REQUEST_EDIT_MEMO);
                break;


            case R.id.detail_btn_delete:

                intent=new Intent();
                intent.putExtra("pos", pos);
                setResult(ResultCode.RESULT_DELETE_MEMO,intent);
                finish();
                break;


        }


    }
}
