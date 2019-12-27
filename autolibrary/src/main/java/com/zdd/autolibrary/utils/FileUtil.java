package com.zdd.autolibrary.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zdd on 2019/10/23
 */
public class FileUtil {
    private FileUtil(){

    }

    public static void copyAssetsToFile(Context ctx, String fileName, String filePath) {
        try {
            File file;
            if ((file = new File(filePath)).exists()) {
                if (!file.delete()) {
                    return;
                }
            } else if ((file = file.getParentFile()) != null && !file.exists() && !file.mkdirs()) {
                return;
            }

            InputStream inputStream = ctx.getApplicationContext().getAssets().open(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] byteData = new byte[1024];

            for(int ret = inputStream.read(byteData); ret > 0; ret = inputStream.read(byteData)) {
                fileOutputStream.write(byteData, 0, ret);
            }

            inputStream.close();
            fileOutputStream.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}
