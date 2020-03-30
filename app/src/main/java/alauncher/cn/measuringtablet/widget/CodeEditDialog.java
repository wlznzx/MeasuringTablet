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
        }
        long id = App.getDaoSession().getCodeBeanDao().insertOrReplace(mCodeBean);
        doTemplateAdd((int) id);
        return true;
    }

    public void doTemplateAdd(int codeID) {
        // 默认模板;
        if (App.getDaoSession().getTemplateBeanDao().load((long) codeID) == null) {
            TemplateBean mTemplateBean = new TemplateBean();
            mTemplateBean.setCodeID(codeID);
            mTemplateBean.setDataNum(5);
            ArrayList<String> Titles = new ArrayList<>();
            ArrayList<String> TitleTypes = new ArrayList<>();
            Titles.add("部品名称");
            TitleTypes.add("0");
            Titles.add("进货批量");
            TitleTypes.add("0");
            Titles.add("进货日期");
            TitleTypes.add("2");
            Titles.add("检查日期");
            TitleTypes.add("2");
            Titles.add("部品代号");
            TitleTypes.add("0");
            Titles.add("尺寸检查");
            TitleTypes.add("0");
            Titles.add("生产日期");
            TitleTypes.add("2");
            Titles.add("部品发送到");
            TitleTypes.add("0");
            Titles.add("材料名称");
            TitleTypes.add("0");
            Titles.add("订单No");
            TitleTypes.add("0");
            Titles.add("检查目的");
            TitleTypes.add("0");
            // Titles.add("供货方名称");
            ArrayList<String> AQLList = new ArrayList<>();
            ArrayList<String> AQLTypeList = new ArrayList<>();
            AQLList.add("无毛刺、无异物、无气孔");
            AQLTypeList.add("1");
            AQLList.add("无收缩、无裂缝、无缺陷");
            AQLTypeList.add("1");
            AQLList.add("毛刺高度控制在0.3以下");
            AQLTypeList.add("1");
            AQLList.add("外包装无破损、无变形、无潮湿");
            AQLTypeList.add("1");
            AQLList.add("月份标签确认");
            AQLTypeList.add("1");
            AQLList.add("供应商数据确认");
            AQLTypeList.add("1");
            ArrayList<String> RoHSList = new ArrayList<>();
            ArrayList<String> RoHSTypeList = new ArrayList<>();
            RoHSList.add("RoHS确认频率");
            RoHSTypeList.add("0");
            RoHSList.add("本批确认");
            RoHSTypeList.add("0");
            RoHSList.add("上回RoHS确认日");
            RoHSTypeList.add("2");
            RoHSList.add("确认结果(RoHS检查数据以检查日期追溯)");
            RoHSTypeList.add("1");
            RoHSList.add("模号:");
            RoHSTypeList.add("0");
            ArrayList<String> signList = new ArrayList<>();
            signList.add("课长");
            signList.add("系长");
            signList.add("部长");
            mTemplateBean.setAverageEnable(true);
            mTemplateBean.setMaximumEnable(true);
            mTemplateBean.setMinimumEnable(true);
            mTemplateBean.setRangeEnable(true);
            mTemplateBean.setJudgeEnable(true);

            mTemplateBean.setTitle("量产受入品检查表E*W");
            mTemplateBean.setAQLList(AQLList);
            mTemplateBean.setRoHSList(RoHSList);
            mTemplateBean.setTitleList(Titles);
            mTemplateBean.setSignList(signList);
            mTemplateBean.setTitleTypeList(TitleTypes);
            mTemplateBean.setAQLTypeList(AQLTypeList);
            mTemplateBean.setRoHSTypeList(RoHSTypeList);
            App.getDaoSession().getTemplateBeanDao().insert(mTemplateBean);
        }
    }

}
