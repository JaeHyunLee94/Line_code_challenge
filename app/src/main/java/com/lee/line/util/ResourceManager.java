package com.lee.line.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.lee.line.code.FreeMemoryCode;

import java.io.File;
import java.util.ArrayList;

public class ResourceManager {

    public static void free_memory(Context context,ArrayList<String> img_list, int pos, int CODE) {

        if (CODE == FreeMemoryCode.MODE_FREE_ALL) {
            int count = 0;

            for (int i = 0; i < img_list.size(); i++) {

                File fdelete = new File(Uri.parse(img_list.get(i)).getPath());

                if (fdelete.exists() && fdelete.delete()) {
                    count++;
                } else {
                    Log.e("Memory Leak", "there is leaking memory in external storage");
                }

            }
            if (count == img_list.size()) {
                Toast.makeText(context, "성공적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();
            }


        } else if (CODE == FreeMemoryCode.MODE_FREE_ONE) {

            File fdelete = new File(Uri.parse(img_list.get(pos)).getPath());

            if (fdelete.exists() && fdelete.delete()) {

                Toast.makeText(context, "성공적으로 삭제되었습니다.", Toast.LENGTH_LONG).show();

            } else {
                Log.e("Memory Leak", "there is leaking memory in external storage");
            }

        }
    }


}
