package com.lee.line;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.lee.line.adapter.ImageAdapter;
import com.lee.line.code.RequestCode;
import com.lee.line.code.ResultCode;
import com.lee.line.dialog.ChangeImageDialog;
import com.lee.line.dialog.GetImageDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WriteActivity extends AppCompatActivity implements View.OnClickListener, ImageAdapter.OnitemClickInterface, ImageAdapter.OnitemLongClickInterface {


    Button btn_save;
    Button btn_cancel;
    Button btn_add;
    EditText et_title;
    EditText et_content;
    RecyclerView rv_img;


    int CODE;


    GetImageDialog add_dialog;
    ChangeImageDialog change_dialog;

    String title = "";
    String content = "";

    ImageAdapter adapter;
    GridLayoutManager manager;


    ArrayList<String> img_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        add_dialog = new GetImageDialog(this);
        change_dialog = new ChangeImageDialog(this);

        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_add = findViewById(R.id.btn_img_add);

        et_title = findViewById(R.id.write_title);
        et_content = findViewById(R.id.write_content);
        rv_img = findViewById(R.id.rv_img);


        load_memo();

        adapter = new ImageAdapter(getApplicationContext(), img_list, this, this);
        manager = new GridLayoutManager(getApplicationContext(), 3);

        adapter.notifyDataSetChanged();

        rv_img.setAdapter(adapter);
        rv_img.setLayoutManager(manager);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_add.setOnClickListener(this);


    }


    @Override
    protected void onPause() {
        super.onPause();
        title = et_title.getText().toString();
        content = et_content.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RequestCode.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            img_list.add(uri.toString());
            Log.e("camera:", uri.toString());
            adapter.notifyDataSetChanged();

        } else if (requestCode == RequestCode.REQUEST_ALBUM) {

            Uri uri = data.getData();
            ClipData clipData = data.getClipData();

            if (clipData != null) {

                for (int i = 0; i < clipData.getItemCount(); i++) {

                    Uri urione = clipData.getItemAt(i).getUri();


                    String new_path = "";
                    try {
                        new_path = save_to_app(urione);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!new_path.isEmpty()) {
                        Log.e("album: ", new_path);


                    }
                    img_list.add(new_path);
                }
            } else if (uri != null) {
                String new_path = "";
                try {
                    new_path = save_to_app(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!new_path.isEmpty()) {
                    Log.e("album: ", new_path);
                }

                img_list.add(new_path);
            }
            adapter.notifyDataSetChanged();

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_save:
                save_memo();
                break;

            case R.id.btn_cancel:

                Toast.makeText(this, "cancel clicked", Toast.LENGTH_SHORT).show();
                finish();
                break;


            case R.id.btn_img_add:
                checkPermissions();
                add_dialog.show();
                break;


        }
    }


    private void load_memo() {
        Intent intent = getIntent();
        CODE = (int) intent.getExtras().get("REQUEST_CODE");
        if (CODE == RequestCode.REQUEST_EDIT_MEMO) {
            et_title.setText(intent.getExtras().getString("title"));
            et_content.setText(intent.getExtras().getString("content"));//img list 넣
            img_list = (ArrayList<String>) intent.getExtras().get("img_list");


        } else {
            img_list = new ArrayList<String>();
        }
    }

    private void save_memo() {

        title = et_title.getText().toString();
        content = et_content.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent();


        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("img_list", img_list);

        if (CODE == RequestCode.REQUEST_NEW_MEMO) {
            setResult(ResultCode.RESULT_NEW_COMPLETED, intent);

        } else if (CODE == RequestCode.REQUEST_EDIT_MEMO) {

            setResult(ResultCode.RESULT_EDIT_COMPLETED, intent);

        }
        finish();
    }

    public void getURL() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("이미지 URL을 입력하세요");
        alert.setMessage("http:// 로 시작하는 이미지 주소");


        final EditText name = new EditText(this);
        alert.setView(name);

        alert.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.e("sdf", name.getText().toString());

                //add_or_replace_img(-1,name.getText().toString(),RequestCode.RE);
                img_list.add(name.getText().toString());
                adapter.notifyDataSetChanged(); //위치 중요
            }
        });


        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        alert.show();


    }


    public void goToAlbum() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);


        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RequestCode.REQUEST_ALBUM);
    }

    public String save_to_app(Uri uri) throws IOException {

        InputStream in = getContentResolver().openInputStream(uri);

        Bitmap img = BitmapFactory.decodeStream(in);
        in.close();

        File file = createImageFile();
        OutputStream out = null;

        try {

            out = new FileOutputStream(file);


            img.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

        return Uri.fromFile(file).toString();

    }


    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.lee.line",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, RequestCode.REQUEST_TAKE_PHOTO);
            }
        }
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(), "권한이 확인되었습니다.", Toast.LENGTH_SHORT).show();
            add_dialog.show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }


    };

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) { // 마시멜로(안드로이드 6.0) 이상 권한 체크
            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage("이미지를 다루기 위해서는 접근 권한이 필요합니다")
                    .setDeniedMessage("앱에서 요구하는 권한설정이 필요합니다...\n [설정] > [권한] 에서 사용으로 활성화해주세요.")
                    .setPermissions(new String[]{Manifest.permission.INTERNET,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA})
                    .check();


        }


    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        return image;
    }

    public void remove_img(int pos) {
        img_list.remove(pos);
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void onItemClick(View v, int pos) {
        Intent intent = new Intent(this, FullScreenActivity.class);
        intent.putExtra("imguri", img_list.get(pos));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View v, int pos) {

        change_dialog.setPos(pos);
        change_dialog.show();

    }


}