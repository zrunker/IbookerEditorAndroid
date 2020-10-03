package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

/**
 * Uri管理类
 *
 * @author 邹峰立
 */
public class UriUtil {

    /**
     * 通过Uri获取文件地址
     *
     * @param context 上下文对象
     * @param uri     待处理的uri
     * @return 文件地址
     */
    public static String getFilePathByUri(Context context, Uri uri) {
        String filePath = null;
        if (uri != null) {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 简单查询
                filePath = queryFilePath(context, uri, null, null);
                // 复杂查询
                if (TextUtils.isEmpty(filePath)) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        String docId = DocumentsContract.getDocumentId(uri);
                        String[] split = docId.split(":");
                        String type = split[0];
                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        String selection = MediaStore.Images.Media._ID + "=?";
                        String[] selectionArgs = new String[]{split[1]};

                        // 重新查找
                        filePath = queryFilePath(context, contentUri, selection, selectionArgs);
                    }
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                filePath = uri.getPath();
            }
        }
        return filePath;
    }

    /**
     * 根据Uri查询文件地址
     */
    private static String queryFilePath(Context context, Uri uri, String selection, String[] selectionArgs) {
        String filePath = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
                try {
                    filePath = cursor.getString(cursor.getColumnIndex("_data"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cursor.close();
                }
        }
        return filePath;
    }
}
