package com.lee.line.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.lee.line.code.FreeMemoryCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 앱의 전체에서 사용할 수 있는 전역 메소드 선언
 */

public class ResourceManager {


    /*
    메모나 이미지첨부 삭제시 참조만 삭제하면 메모리 누수가 일어나므로
    해당 uri 로 접근하여 memory 를 free 해줌
     */
    public static void free_memory(Context context,ArrayList<String> img_list, int pos, int CODE) {

        if (CODE == FreeMemoryCode.MODE_FREE_ALL) {
            int count = 0;

            for (int i = 0; i < img_list.size(); i++) {

                File fdelete = new File(Uri.parse(img_list.get(i)).getPath());

                if ((fdelete.exists() && fdelete.delete())||Patterns.WEB_URL.matcher(img_list.get(i)).matches()) {
                    count++;
                } else {
                    Log.e("Memory Leak", "there is leaking memory in external storage");
                }

            }
            if (count == img_list.size()) {
                Toast.makeText(context, "성공적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();
            }else {

                Log.e("Memory Leak in clip", "there is leaking memory in external storage");
                Log.e("count ",""+count);
                Log.e("img_list size ", ""+img_list);
            }


        } else if (CODE == FreeMemoryCode.MODE_FREE_ONE) {

            File fdelete = new File(Uri.parse(img_list.get(pos)).getPath());

            if ((fdelete.exists() && fdelete.delete())||Patterns.WEB_URL.matcher(img_list.get(pos)).matches()) {

                Toast.makeText(context, "성공적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();

            } else {
                Log.e("Memory Leak one", "there is leaking memory in external storage");
            }

        }
    }

    /*
    앱 외부 저장소에 겹치지 않는 파일 명을 만들고 파일을 return 해줌
     */
    public static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        return image;
    }


}
