package alauncher.cn.measuringtablet.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.List;

import alauncher.cn.measuringtablet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StringEditDialog extends Dialog {

    private final Context mContext;

    public OnStringDialogCallBack mOnStringDialogCallBack;

    public String defaultStr;

    @BindView(R.id.str_edt)
    public EditText strEDT;

    public StringEditDialog(Context context, OnStringDialogCallBack pOnStringDialogCallBack, String str) {
        super(context);
        mContext = context;
        mOnStringDialogCallBack = pOnStringDialogCallBack;
        defaultStr = str;
        initView();
    }

    private void initView() {
        View contentView = View.inflate(mContext, R.layout.string_edit_dialog, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        strEDT.setText(defaultStr);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            return;
        }
        // setHeight();
    }

    private void setHeight() {
        Window window = getWindow();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (window.getDecorView().getHeight() >= (int) (displayMetrics.heightPixels * 0.6)) {
            attributes.height = (int) (displayMetrics.heightPixels * 0.6);
        }
        window.setAttributes(attributes);
    }

    @OnClick({R.id.no, R.id.yes})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no:
                dismiss();
                break;
            case R.id.yes:
                // doAddUser();
                if (mOnStringDialogCallBack != null)
                    mOnStringDialogCallBack.doString(strEDT.getText().toString().trim());
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnStringDialogCallBack {
        void doString(String str);
    }
}
