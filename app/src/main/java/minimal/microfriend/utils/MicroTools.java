package minimal.microfriend.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Date;

import minimal.microfriend.entry.User;

/**
 * Created by gno on 16/7/17.
 */
public class MicroTools {
    private static Toast mtoast;

    /**
     * Character string encryption
     *
     * @param before
     * @return
     */
    public static String MD5(String before) {
        String after = new String();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(before.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;//获得低八位信息
                String s = Integer.toHexString(i);
                if (s.length() == 1)
                    s = "0" + s;
                buffer.append(s);
            }
            after = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return after;
    }

    /***
     * 静态吐司
     *
     * @param context
     * @param str
     */
    public static void toast(Context context, String str) {
        if (mtoast == null)
            mtoast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        mtoast.setDuration(Toast.LENGTH_SHORT);
        mtoast.setText(str);
        mtoast.show();
    }

    /**
     * 根据uri生成本地唯一文件
     *
     * @param uri
     * @param activity
     * @param user
     * @return
     */
    public static File uriToFile(Uri uri, Activity activity, User user) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);

        File mdfile = new File(activity.getCacheDir() + "/" +
                MD5(user.getObjectId() + new Date()) +
                file.getPath().substring(file.getPath().indexOf("."),
                        file.getPath().length()));
        fileCopy(file, mdfile);
        return mdfile;
    }

    /**
     * 文件复制
     *
     * @param source
     * @param target
     */
    public static void fileCopy(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件读取字符串
     *
     * @param file
     * @return
     */
    public static String fileToString(File file) {
        StringBuffer sb = new StringBuffer();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line = null;
            while((line= br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
