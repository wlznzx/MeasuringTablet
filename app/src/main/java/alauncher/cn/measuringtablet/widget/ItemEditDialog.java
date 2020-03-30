package alauncher.cn.measuringtablet.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

import alauncher.cn.measuringtablet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemEditDialog extends Dialog {

    private final Context mContext;

    public OnStringDialogCallBack mOnStringDialogCallBack;

    public String defaultStr;

    @BindView(R.id.str_edt)
    public EditText strEDT;

    @BindView(R.id.input_type_spinner)
    public Spinner inputTypeSP;

    private String typeStr;

    public ItemEditDialog(Context context, OnStringDialogCallBack pOnStringDialogCallBack, String str, String type) {
        super(context);
        mContext = context;
        mOnStringDialogCallBack = pOnStringDialogCallBack;
        defaultStr = str;
        typeStr = type;
        initView();
    }

    private void initView() {
        View contentView = View.inflate(mContext, R.layout.title_item_dialog, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        strEDT.setText(defaultStr);
        inputTypeSP.setSelection(Integer.valueOf(typeStr));
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
                if (mOnStringDialogCallBack != null)
                    mOnStringDialogCallBack.doString(strEDT.getText().toString().trim(), String.valueOf(inputTypeSP.getSelectedItemPosition()));
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnStringDialogCallBack {
        void doString(String str, String type);
    }

    private String getInputType(String index) {
        return mContext.getResources().getStringArray(R.array.input_type)[Integer.valueOf(index)];
    }
}
