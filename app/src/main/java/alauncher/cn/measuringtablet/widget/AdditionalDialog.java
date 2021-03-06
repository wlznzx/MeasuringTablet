package alauncher.cn.measuringtablet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.bean.AddInfoBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdditionalDialog extends Dialog {

    private Context mContext;

    @BindView(R.id.workpieceid_edt)
    public EditText workpieceidEdt;

    @BindView(R.id.eventid_edt)
    public EditText eventidEdt;

    @BindView(R.id.workpieceid_sp)
    public Spinner workpieceidSP;

    private boolean isFrist = true;

    @BindView(R.id.evnet_sp)
    public Spinner eventSP;

    @BindView(R.id.additional_show_ch)
    public CheckBox isShowCB;


    AdditionDialogInterface mAdditionDialogInterface;

    public AdditionalDialog(Context context) {
        super(context);
        mContext = context;
    }

    public AdditionalDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public void setDialogInterface(AdditionDialogInterface pAdditionDialogInterface) {
        mAdditionDialogInterface = pAdditionDialogInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_dialog_layout);
        ButterKnife.bind(this);
        isShowCB.setChecked(App.getSetupBean().getIsAutoPopUp());
        eventSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                android.util.Log.d("wlDebug", "pos = " + eventSP.getItemAtPosition(position));
                if (!isFrist) {
                    eventidEdt.setText((String) eventSP.getItemAtPosition(position));
                } else {
                    isFrist = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface AdditionDialogInterface {
        void onAdditionSet(AddInfoBean pBean);
    }

    @OnClick({R.id.no, R.id.yes})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no:
                break;
            case R.id.yes:
                if (mAdditionDialogInterface != null) {
                    AddInfoBean _bean = new AddInfoBean();
                    _bean.setAutoShow(isShowCB.isChecked());
                    _bean.setWorkid(workpieceidEdt.getText().toString());
                    _bean.setWork(mContext.getResources().getStringArray(R.array.workids)[(int) workpieceidSP.getSelectedItemId()]);
                    _bean.setEvent(eventidEdt.getText().toString().trim());
                    _bean.setEventid(eventidEdt.getText().toString().trim());
                    mAdditionDialogInterface.onAdditionSet(_bean);
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void dismiss() {
        //避免闪屏 提高用户体验
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdditionalDialog.super.dismiss();
            }
        }, 500);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(workpieceidEdt.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(eventidEdt.getWindowToken(), 0);
    }

}
