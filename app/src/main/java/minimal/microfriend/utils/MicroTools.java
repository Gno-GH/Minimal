package minimal.microfriend.utils;

import android.content.Context;
import android.widget.Toast;

import java.security.MessageDigest;

/**
 * Created by gno on 16/7/17.
 */
public class MicroTools {
    private static Toast mtoast;
    /**
     * Character string encryption
     * @param before
     * @return
     */
    public static String MD5(String before){
        String after = new String();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte [] bytes = digest.digest(before.getBytes());
            StringBuffer buffer = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;//获得低八位信息
                String s = Integer.toHexString(i);
                if(s.length()==1) s = "0"+s;
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
     * @param context
     * @param str
     */
    public static void toast(Context context ,String str ) {
        if(mtoast == null)
        mtoast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        mtoast.setDuration(Toast.LENGTH_SHORT);
        mtoast.setText(str);
        mtoast.show();
    }
}
