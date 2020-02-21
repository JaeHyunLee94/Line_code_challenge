package com.lee.line;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.line.adapter.ImageAdapter;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_save;
    Button btn_cancel;
    EditText et_title;
    EditText et_content;
    RecyclerView rv_img;

    int CODE;


    String title = "";
    String content = "";

    ImageAdapter adapter;
    GridLayoutManager manager;



    ArrayList<String> img_list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_title = findViewById(R.id.write_title);
        et_content = findViewById(R.id.write_content);
        rv_img=findViewById(R.id.rv_img);

        img_list=new ArrayList<>();

        img_list.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");
        img_list.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");
        img_list.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");
        img_list.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");
        img_list.add("https://t1.daumcdn.net/cfile/tistory/2542444858F57E0B33");




        adapter=new ImageAdapter(getApplicationContext(),img_list);
        manager=new GridLayoutManager(getApplicationContext(),3);

        rv_img.setAdapter(adapter);
        rv_img.setLayoutManager(manager);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);



        load_memo_if_edit_mode();




    }

    private void load_memo_if_edit_mode() {
        Intent intent = getIntent();
        CODE = (int) intent.getExtras().get("REQUEST_CODE");
        if (CODE == RequestCode.REQUEST_EDIT_MEMO) {
            et_title.setText(intent.getExtras().getString("title"));
            et_content.setText(intent.getExtras().getString("content"));

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        title = et_title.getText().toString();
        content = et_content.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:

                title = et_title.getText().toString();
                content = et_content.getText().toString();


                Intent intent = new Intent();


                intent.putExtra("title", title);
                intent.putExtra("content", content);

                if(CODE==RequestCode.REQUEST_NEW_MEMO){
                    if(title.isEmpty()&&content.isEmpty()) {
                        Toast.makeText(this, "빈 메모입니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        setResult(ResultCode.RESULT_NEW_COMPLETED,intent);
                    }

                }else if(CODE==RequestCode.REQUEST_EDIT_MEMO){
                    if(title.isEmpty()&&content.isEmpty()){
                        Toast.makeText(this, "빈 메모입니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        setResult(ResultCode.RESULT_EDIT_COMPLETED,intent);
                    }
                }


                finish();
                break;


            case R.id.btn_cancel:

                Toast.makeText(this, "cancel clicked", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
