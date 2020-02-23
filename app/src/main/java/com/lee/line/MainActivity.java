package com.lee.line;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.lee.line.adapter.ListAdapter;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;
import com.lee.line.data.Memo;
import com.lee.line.dialog.SelectActionDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListAdapter.OnitemClickInterface, ListAdapter.OnitemLongClickInterface {


    ArrayList<Memo> memo_list;
    RecyclerView main_rv;

    String FILE_NAME = "memo_file";
    String SP_KEY_NAME = "memo_list";
    SelectActionDialog selectdialog;
    SharedPreferences sp;
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FloatingActionButton fab = findViewById(R.id.fab);

        main_rv = findViewById(R.id.rv);


        memo_list = load_memo();

        adapter = new ListAdapter(memo_list, this,this);


        fab.setOnClickListener(this);


        main_rv.setAdapter(adapter);
        main_rv.setLayoutManager(new LinearLayoutManager(this));




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save_memo();
    }

    private void save_memo() {
        Gson gson = new Gson();

        String json_string = gson.toJson(memo_list);

        sp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY_NAME, json_string);
        editor.apply();

    }

    private ArrayList<Memo> load_memo() {

        sp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String json_string = sp.getString(SP_KEY_NAME, "");

        if (json_string.isEmpty()) {
            Log.e("load_new_memo", "load_memo and new array created");
            return new ArrayList<>();

        } else {
            Gson gson = new Gson();
            Memo[] m_array = gson.fromJson(json_string, Memo[].class);
            for (int i = 0; i < m_array.length; i++) {
                Log.e("load_exist_memo", "title: " + m_array[i].getTitle() + "content: " + m_array[i].getContent());
            }
            return new ArrayList<>(Arrays.asList(m_array));

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String title;
        String content;
        ArrayList<String> img_list;
        int pos;
        switch (resultCode) {

            case ResultCode.RESULT_EDIT_COMPLETED:

                title = (String) data.getStringExtra("title");
                content = (String) data.getStringExtra("content");

                img_list=(ArrayList<String>) data.getExtras().get("img_list");
                pos = data.getIntExtra("pos", -1);

                Memo m=new Memo(title,content);
                m.setImglist(img_list);

                memo_list.set(pos, m);// 객체 생성 않고 바꾸기??

                adapter.notifyItemChanged(pos);//뒤로가기 버튼 누르기

                break;

            case ResultCode.RESULT_NEW_COMPLETED:


                title = (String) data.getExtras().get("title");
                content = (String) data.getExtras().get("content");
                img_list=(ArrayList<String>) data.getExtras().get("img_list");

                Memo a = new Memo(title, content);
                a.setImglist(img_list);

                memo_list.add(a);
                adapter.notifyDataSetChanged();
                break;
            case ResultCode.RESULT_DELETE_MEMO:
                pos=data.getIntExtra("pos",-1);
                memo_list.remove(pos);
                adapter.notifyItemRemoved(pos);
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        //new memo click
        Intent intent = new Intent(this, WriteActivity.class);
        intent.putExtra("REQUEST_CODE", RequestCode.REQUEST_NEW_MEMO);

        startActivityForResult(intent, RequestCode.REQUEST_NEW_MEMO);


    }


    @Override
    public void onItemClick(View v, int pos) {

        //item click
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("REQUEST_CODE", RequestCode.REQUEST_DETAIL);
        intent.putExtra("title", memo_list.get(pos).getTitle());
        intent.putExtra("content", memo_list.get(pos).getContent());
        intent.putExtra("pos", pos);
        intent.putExtra("img_list",memo_list.get(pos).getImglist());

        startActivityForResult(intent, RequestCode.REQUEST_DETAIL);

    }

    @Override
    public void onItemLongClick(View v, int pos) {

        selectdialog=new SelectActionDialog(this,pos,memo_list,adapter);
        selectdialog.show();

    }


}