package com.lee.line;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {

    int EDIT_COMPLETED=5;
    int NEW_WRITE_COMPLTED=7;
    int NOTHING_CHANGED=9;


    Button btn_save;
    Button btn_cancel;
    EditText et_title;
    EditText et_content;


    String title="";
    String content="";

    //setResult,finish로 결과 저


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btn_save=findViewById(R.id.btn_save);
        btn_cancel=findViewById(R.id.btn_cancel);
        et_title=findViewById(R.id.write_title);
        et_content=findViewById(R.id.write_content);



        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:

                Toast.makeText(this,"save clicked",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case  R.id.btn_cancel:

                Toast.makeText(this,"cancel clicked",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
