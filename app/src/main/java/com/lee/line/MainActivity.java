package com.lee.line;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lee.line.adapter.ListAdapter;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;
import com.lee.line.data.Memo;
import com.lee.line.dialog.DetailDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{


    List<Memo> memo_list = new ArrayList<>();
    View list_item;
    RecyclerView main_rv;
    DetailDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);

        main_rv = findViewById(R.id.rv);
        dialog = new DetailDialog(this);


        fab.setOnClickListener(this);

        //onActivityResult(); 로 결과 받기


        memo_list.add(new Memo("title", "saersaer"));
        memo_list.add(new Memo("title", "saersaer"));
        memo_list.add(new Memo("title", "saersaer"));
        memo_list.add(new Memo("title", "saersaer"));
        memo_list.add(new Memo("title", "saersaer"));

        main_rv.setAdapter(new ListAdapter(memo_list, dialog));
        main_rv.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ResultCode.RESULT_EDIT_COMPLETED:

                break;
            case ResultCode.RESULT_EMPTY:

                break;
            case ResultCode.RESULT_NEW_COMPLETED:

                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, WriteActivity.class);

        switch (v.getId()){
            case R.id.fab:

                startActivityForResult(intent, RequestCode.REQUEST_NEW_MEMO);

                break;

            case R.id.btn_edit:
                startActivityForResult(intent, RequestCode.REQUEST_EDIT_MEMO);
                break;
            case R.id.btn_delete:

                break;

            default:
                break;
        }

    }


}
