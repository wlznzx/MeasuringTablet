package alauncher.cn.measuringtablet.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import alauncher.cn.measuringtablet.R;
import butterknife.OnClick;


public class DialogUtils {

    public static void showDialog(Context context, String title, String msg, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok, listener);
        builder.create().show(); //构建AlertDialog并显示
    }

    public static void showDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.ok, null);
        builder.create().show(); //构建AlertDialog并显示
    }

}
