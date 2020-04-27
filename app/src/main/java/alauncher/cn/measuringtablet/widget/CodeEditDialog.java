package alauncher.cn.measuringtablet.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.view.activity_view.DataUpdateInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CodeEditDialog extends Dialog {

    private Context mContext;

    DataUpdateInterface dataUpdateInterface;

    private CodeBean mCodeBean;

    @BindView(R.id.code_name_edt)
    public EditText codeNameEdt;

    public CodeEditDialog(Context context, CodeBean pCodeBean) {
        super(context, R.style.Dialog);
        mContext = context;
        mCodeBean = pCodeBean;
    }

    public void setDataUpdateInterface(DataUpdateInterface pDataUpdateInterface) {
        dataUpdateInterface = pDataUpdateInterface;
    }

    @OnClick({R.id.no, R.id.yes})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no:
                dismiss();
                break;
            case R.id.yes:
                if (doCodeAdd()) {
                    if (dataUpdateInterface != null) dataUpdateInterface.dataUpdate();
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_edit_dialog_layout);
        ButterKnife.bind(this);
    }

    public boolean doCodeAdd() {
        if (mCodeBean == null) {
            mCodeBean = new CodeBean();
            mCodeBean.setMachineTool(getContext().getResources().getString(R.string.machine_tool));
            mCodeBean.setParts(getContext().getResources().getString(R.string.spare_parts));
            mCodeBean.setName(codeNameEdt.getText().toString().trim());
            mCodeBean.setDefaultTitles(new ArrayList<>());
            mCodeBean.setUseTemplateID(1L);
        }
        long id = App.getDaoSession().getCodeBeanDao().insertOrReplace(mCodeBean);
        // doTemplateAdd((int) id); // 不用新建模板了;
        return true;
    }

    public void doTemplateAdd(int codeID) {
        // 默认模板;
        if (App.getDaoSession().getTemplateBeanDao().load((long) codeID) == null) {
            TemplateBean mTemplateBean = App.getDefaultTemplateBean();
            // mTemplateBean.setCodeID(codeID);
            App.getDaoSession().getTemplateBeanDao().insert(mTemplateBean);
        }
    }

}
