package com.lee.line;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.lee.line.adapter.ListAdapter;
import com.lee.line.code.FreeMemoryCode;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;
import com.lee.line.data.Memo;
import com.lee.line.dialog.SelectActionDialog;

import java.util.ArrayList;
import java.util.Arrays;

import static com.lee.line.util.ResourceManager.free_memory;

/*
main activity 에서는 메모 리스트를 보여줌
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ListAdapter.OnitemClickInterface, ListAdapter.OnitemLongClickInterface {

    private final long FINISH_INTERVAL_TIME = 2000; //뒤로가기 2초 연속 연타시 앱 종료
    private long   backPressedTime = 0;


    ArrayList<Memo> memo_list;
    RecyclerView main_rv;

    String FILE_NAME = "memo_file"; //sharedPreference 에 저장할 파일 이름
    String SP_KEY_NAME = "memo_list";//파일 key name
    SelectActionDialog selectdialog;
    SharedPreferences sp;
    ListAdapter adapter; //메모 리스트 adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);

        main_rv = findViewById(R.id.rv);


        memo_list = load_memo();

        adapter = new ListAdapter(this, memo_list, this, this);


        fab.setOnClickListener(this);


        main_rv.setAdapter(adapter);
        main_rv.setLayoutManager(new LinearLayoutManager(this));


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        save_memo();
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }


    }

    private void save_memo() {
    /*
        메모 리스트 정보를 json 문자열로 변환하여 sharedpreference에 저장
     */
        Gson gson = new Gson(); //json 라이브러리

        String json_string = gson.toJson(memo_list);

        sp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SP_KEY_NAME, json_string);
        editor.apply();

    }



    private ArrayList<Memo> load_memo() {
        /*
        저장해놓은 메모가 있다면 불러오고 아니라면 새로운 메모리스트 객체생성후 반환
         */

        sp = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String json_string = sp.getString(SP_KEY_NAME, "");

        if (json_string.isEmpty()) {
            Log.e("load_new_memo", "load_memo and new array created");
            return new ArrayList<>();

        } else {
            Gson gson = new Gson();
            Memo[] m_array = gson.fromJson(json_string, Memo[].class);
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

            case ResultCode.RESULT_EDIT_COMPLETED: //메모 편집이 끝났을 경우 data update

                title = (String) data.getStringExtra("title");
                content = (String) data.getStringExtra("content");

                img_list = (ArrayList<String>) data.getExtras().get("img_list");
                pos = data.getIntExtra("pos", -1);

                Memo m = new Memo(title, content);
                m.setImglist(img_list);

                memo_list.set(pos, m);

                adapter.notifyItemChanged(pos);//뒤로가기 버튼 누르기

                break;

            case ResultCode.RESULT_NEW_COMPLETED: //새로운 메모가 생성되었을 경우 data update


                title = (String) data.getExtras().get("title");
                content = (String) data.getExtras().get("content");
                img_list = (ArrayList<String>) data.getExtras().get("img_list");

                Memo a = new Memo(title, content);
                a.setImglist(img_list);

                memo_list.add(a);
                adapter.notifyDataSetChanged();
                break;

            case ResultCode.RESULT_DELETE_MEMO: //메모가 삭제되었을 경우 data update

                pos = data.getIntExtra("pos", -1);

                free_memory(this,memo_list.get(pos).getImglist(), -1, FreeMemoryCode.MODE_FREE_ALL);
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

        //memo item click 콜백 메서드
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("REQUEST_CODE", RequestCode.REQUEST_DETAIL);
        intent.putExtra("title", memo_list.get(pos).getTitle());
        intent.putExtra("content", memo_list.get(pos).getContent());
        intent.putExtra("pos", pos);
        intent.putExtra("img_list", memo_list.get(pos).getImglist());

        startActivityForResult(intent, RequestCode.REQUEST_DETAIL);

    }

    @Override
    public void onItemLongClick(View v, int pos) {
       //memo item long click 콜백 메서드
        selectdialog = new SelectActionDialog(this, pos, memo_list, adapter);
        selectdialog.show();

    }




}
