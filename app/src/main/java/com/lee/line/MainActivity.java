package com.lee.line;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lee.line.adapter.ListAdapter;
import com.lee.line.memo.Memo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Memo> memo_list=new ArrayList<>();
    View list_item;
    RecyclerView main_rv;
    DetailDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        main_rv=findViewById(R.id.rv);
        dialog=new DetailDialog(this);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,WriteActivity.class);
                startActivity(intent);

            }
        });



        memo_list.add(new Memo("title","saersaer"));
        memo_list.add(new Memo("title","saersaer"));
        memo_list.add(new Memo("title","saersaer"));
        memo_list.add(new Memo("title","saersaer"));
        memo_list.add(new Memo("title","saersaer"));

        main_rv.setAdapter(new ListAdapter(memo_list,dialog));
        main_rv.setLayoutManager(new LinearLayoutManager(this));


    }


















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
