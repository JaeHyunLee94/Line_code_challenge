package com.lee.line;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.line.adapter.ImageAdapter;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,ImageAdapter.OnitemLongClickInterface,ImageAdapter.OnitemClickInterface{

    Context context;
    Button btn_edit;
    Button btn_delete;
    Button btn_back;

    TextView title_box;
    TextView content_box;

    RecyclerView detail_rv;

    GridLayoutManager manager;
    ImageAdapter adapter;



    String title;
    String content;
    ArrayList<String> img_list;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn_delete = findViewById(R.id.detail_btn_delete);
        btn_edit = findViewById(R.id.detail_btn_edit);
        title_box = findViewById(R.id.detail_title);
        content_box = findViewById(R.id.detail_content);
        detail_rv=findViewById(R.id.rv_detail_img);
        btn_back=findViewById(R.id.detail_btn_back);

        title_box.setMovementMethod(new ScrollingMovementMethod());
        content_box.setMovementMethod(new ScrollingMovementMethod());

        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);





        Intent arrived_intent = getIntent();

        title = arrived_intent.getStringExtra("title");
        content = arrived_intent.getStringExtra("content");
        pos = arrived_intent.getIntExtra("pos", -1); //에러 처
        img_list=(ArrayList<String>) arrived_intent.getExtras().get("img_list");

        adapter=new ImageAdapter(getApplicationContext(),img_list,this,this);
        manager=new GridLayoutManager(getApplicationContext(),3);
        detail_rv.setAdapter(adapter);
        detail_rv.setLayoutManager(manager);

        title_box.setText(title);
        content_box.setText(content);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ResultCode.RESULT_EDIT_COMPLETED) {

            title = data.getStringExtra("title");
            content = data.getStringExtra("content");
            img_list.clear();
            img_list.addAll((ArrayList<String>) data.getExtras().get("img_list"));
            adapter.notifyDataSetChanged();

            title_box.setText(title);
            content_box.setText(content);



            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("pos", pos);
            intent.putExtra("img_list",img_list);
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
                intent.putExtra("img_list",img_list);
                startActivityForResult(intent, RequestCode.REQUEST_EDIT_MEMO);
                break;


            case R.id.detail_btn_delete:

                intent=new Intent();
                intent.putExtra("pos", pos);
                setResult(ResultCode.RESULT_DELETE_MEMO,intent);
                finish();
                break;

            case R.id.detail_btn_back:
                finish();

        }


    }

    @Override
    public void onItemClick(View v, int pos) {
        Intent intent=new Intent(this,FullScreenActivity.class);
        intent.putExtra("imguri",img_list.get(pos));

        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View v, int pos) {
        return;
    }
}
