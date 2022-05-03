package com.resume.maker.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Util {
    public static File getOutputMediaFile(int i) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        if (i == 1) {
            return new File(file.getPath() + File.separator + "IMG_" + format + ".jpg");
        } else if (i != 3) {
            return null;
        } else {
            return new File(file.getPath() + File.separator + "VID_" + format + ".mp4");
        }
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
            int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            return cursor.getString(columnIndexOrThrow);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
