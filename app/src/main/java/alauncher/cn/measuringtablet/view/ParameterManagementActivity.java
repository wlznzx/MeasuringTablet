package alauncher.cn.measuringtablet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.StepBean;
import alauncher.cn.measuringtablet.database.greenDao.db.DaoSession;
import alauncher.cn.measuringtablet.database.greenDao.db.StepBeanDao;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.widget.CalculateDialog;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class ParameterManagementActivity extends BaseOActivity implements CalculateDialog.CodeInterface {

    @BindViews({R.id.describe_m1, R.id.describe_m2, R.id.describe_m3, R.id.describe_m4})
    public EditText[] describeEd;

    @BindViews({R.id.nominal_value_m1, R.id.nominal_value_m2, R.id.nominal_value_m3, R.id.nominal_value_m4})
    public EditText[] nominalValueEd;

    @BindViews({R.id.upper_tolerance_m1, R.id.upper_tolerance_m2, R.id.upper_tolerance_m3, R.id.upper_tolerance_m4})
    public EditText[] upperToleranceEd;

    @BindViews({R.id.lower_tolerance_m1, R.id.lower_tolerance_m2, R.id.lower_tolerance_m3, R.id.lower_tolerance_m4})
    public EditText[] lowerToleranceEd;

    @BindViews({R.id.resolution_sp_m1, R.id.resolution_sp_m2, R.id.resolution_sp_m3, R.id.resolution_sp_m4})
    public Spinner[] resolutionSp;

    @BindViews({R.id.deviation_m1, R.id.deviation_m2, R.id.deviation_m3, R.id.deviation_m4})
    public EditText[] offectEd;

    @BindViews({R.id.formula_m1, R.id.formula_m2, R.id.formula_m3, R.id.formula_m4})
    public List<Button> formulaEd;

    @BindViews({R.id.grouping_m1, R.id.grouping_m2, R.id.grouping_m3, R.id.grouping_m4})
    public List<Button> groupingBtn;

    @BindView(R.id.save_btn)
    public TextView saveBtn;

    @BindViews({R.id.ch1_rb, R.id.ch2_rb, R.id.ch3_rb, R.id.ch4_rb})
    public List<CheckBox> mCheckBoxs;

    @BindView(R.id.formula_tv)
    public TextView formulaTv;

    public DaoSession session;

    public String m1_code, m2_code, m3_code, m4_code;

    public ParameterBean mParameterBean;

    private DeviceInfoBean _dBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_parameter_management);
    }

    @Override
    protected void initView() {
        session = App.getDaoSession();
        mParameterBean = session.getParameterBeanDao().load((long) App.getSetupBean().getCodeID());
        if (mParameterBean == null) {
            mParameterBean = new ParameterBean();
            mParameterBean.setCode_id(App.getSetupBean().getCodeID());
        }
        updateUI();
        _dBean = App.getDeviceInfo();
    }

    @OnClick(R.id.save_btn)
    public void onSave(View v) {
        if (!view2Bean()) return;
        App.getDaoSession().getParameterBeanDao().deleteByKey((long) App.getSetupBean().getCodeID());
        App.getDaoSession().getParameterBeanDao().insertOrReplace(mParameterBean);
        // 清空分步信息；
        StepBeanDao _dao = App.getDaoSession().getStepBeanDao();
        List<StepBean> stepBeans = _dao.queryBuilder().where(StepBeanDao.Properties.CodeID.eq(App.getSetupBean().getCodeID())).list();
        for (StepBean _bean : stepBeans) {
            _dao.delete(_bean);
        }
        syncToServer(mParameterBean);
        Toast.makeText(this, "保存成功.", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.formula_m1, R.id.formula_m2, R.id.formula_m3, R.id.formula_m4})
    public void onFormulaEdit(View v) {
        int m_num = -1;
        switch (v.getId()) {
            case R.id.formula_m1:
                m_num = 1;
                break;
            case R.id.formula_m2:
                m_num = 2;
                break;
            case R.id.formula_m3:
                m_num = 3;
                break;
            case R.id.formula_m4:
                m_num = 4;
                break;
            default:
                break;
        }
        // Toast.makeText(this, "go m = " + m_num, Toast.LENGTH_SHORT).show();
        // openActivty(MGroupActivity.class);
        CalculateDialog mCalculateDialog = new CalculateDialog(this);
        // mCalculateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCalculateDialog.setTitle("M" + m_num + "程序设置");
        mCalculateDialog.setM(m_num);
        mCalculateDialog.setCodeInterface(this);
        mCalculateDialog.show();
    }

    @OnClick({R.id.grouping_m1, R.id.grouping_m2, R.id.grouping_m3, R.id.grouping_m4})
    public void onGroupClick(View v) {
        int m_num = -1;
        int title = R.string.grouping;
        switch (v.getId()) {
            case R.id.grouping_m1:
                m_num = 1;
                title = R.string.m1_group;
                break;
            case R.id.grouping_m2:
                title = R.string.m2_group;
                m_num = 2;
                break;
            case R.id.grouping_m3:
                title = R.string.m3_group;
                m_num = 3;
                break;
            case R.id.grouping_m4:
                title = R.string.m4_group;
                m_num = 4;
                break;
            default:
                break;
        }
        Intent intent = new Intent(this, MGroupActivity.class);
        intent.putExtra("M_INDEX", m_num);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    @Override
    public void onCodeSet(int m, String code) {
        android.util.Log.d("wlDebug", "M" + m + " = " + code);
        switch (m) {
            case 1:
                mParameterBean.setM1_code(code);
                formulaEd.get(0).setText(mParameterBean.getM1_code());
                break;
            case 2:
                mParameterBean.setM2_code(code);
                formulaEd.get(1).setText(mParameterBean.getM2_code());
                break;
            case 3:
                mParameterBean.setM3_code(code);
                formulaEd.get(2).setText(mParameterBean.getM3_code());
                break;
            case 4:
                mParameterBean.setM4_code(code);
                formulaEd.get(3).setText(mParameterBean.getM4_code());
                break;
        }
//        updateUI();
    }

    public void updateUI() {
        if (mParameterBean != null) {

            android.util.Log.d("wlDebug", mParameterBean.toString());

            describeEd[0].setText(mParameterBean.getM1_describe());
            describeEd[1].setText(mParameterBean.getM2_describe());
            describeEd[2].setText(mParameterBean.getM3_describe());
            describeEd[3].setText(mParameterBean.getM4_describe());

            nominalValueEd[0].setText(mParameterBean.getM1_nominal_value() + "");
            nominalValueEd[1].setText(mParameterBean.getM2_nominal_value() + "");
            nominalValueEd[2].setText(mParameterBean.getM3_nominal_value() + "");
            nominalValueEd[3].setText(mParameterBean.getM4_nominal_value() + "");

            upperToleranceEd[0].setText(mParameterBean.getM1_upper_tolerance_value() + "");
            upperToleranceEd[1].setText(mParameterBean.getM2_upper_tolerance_value() + "");
            upperToleranceEd[2].setText(mParameterBean.getM3_upper_tolerance_value() + "");
            upperToleranceEd[3].setText(mParameterBean.getM4_upper_tolerance_value() + "");

            lowerToleranceEd[0].setText(mParameterBean.getM1_lower_tolerance_value() + "");
            lowerToleranceEd[1].setText(mParameterBean.getM2_lower_tolerance_value() + "");
            lowerToleranceEd[2].setText(mParameterBean.getM3_lower_tolerance_value() + "");
            lowerToleranceEd[3].setText(mParameterBean.getM4_lower_tolerance_value() + "");

            resolutionSp[0].setSelection(mParameterBean.getM1_resolution());
            resolutionSp[1].setSelection(mParameterBean.getM2_resolution());
            resolutionSp[2].setSelection(mParameterBean.getM3_resolution());
            resolutionSp[3].setSelection(mParameterBean.getM4_resolution());

            offectEd[0].setText(mParameterBean.getM1_offect() + "");
            offectEd[1].setText(mParameterBean.getM2_offect() + "");
            offectEd[2].setText(mParameterBean.getM3_offect() + "");
            offectEd[3].setText(mParameterBean.getM4_offect() + "");

            formulaEd.get(0).setText(mParameterBean.getM1_code());
            formulaEd.get(1).setText(mParameterBean.getM2_code());
            formulaEd.get(2).setText(mParameterBean.getM3_code());
            formulaEd.get(3).setText(mParameterBean.getM4_code());

            mCheckBoxs.get(0).setChecked(mParameterBean.getM1_enable());
            mCheckBoxs.get(1).setChecked(mParameterBean.getM2_enable());
            mCheckBoxs.get(2).setChecked(mParameterBean.getM3_enable());
            mCheckBoxs.get(3).setChecked(mParameterBean.getM4_enable());
        }
    }

    private boolean view2Bean() {
        try {
            mParameterBean.setCode_id(App.getSetupBean().getCodeID());
            // 分辨率
            mParameterBean.setM1_resolution((int) resolutionSp[0].getSelectedItemId());
            mParameterBean.setM2_resolution((int) resolutionSp[1].getSelectedItemId());
            mParameterBean.setM3_resolution((int) resolutionSp[2].getSelectedItemId());
            mParameterBean.setM4_resolution((int) resolutionSp[3].getSelectedItemId());
            // 名义值
            mParameterBean.setM1_nominal_value(Double.valueOf(nominalValueEd[0].getText().toString().trim()));
            mParameterBean.setM2_nominal_value(Double.valueOf(nominalValueEd[1].getText().toString().trim()));
            mParameterBean.setM3_nominal_value(Double.valueOf(nominalValueEd[2].getText().toString().trim()));
            mParameterBean.setM4_nominal_value(Double.valueOf(nominalValueEd[3].getText().toString().trim()));
            // 上公差
            mParameterBean.setM1_upper_tolerance_value(Double.valueOf(upperToleranceEd[0].getText().toString().trim()));
            mParameterBean.setM2_upper_tolerance_value(Double.valueOf(upperToleranceEd[1].getText().toString().trim()));
            mParameterBean.setM3_upper_tolerance_value(Double.valueOf(upperToleranceEd[2].getText().toString().trim()));
            mParameterBean.setM4_upper_tolerance_value(Double.valueOf(upperToleranceEd[3].getText().toString().trim()));
            // 下公差
            mParameterBean.setM1_lower_tolerance_value(Double.valueOf(lowerToleranceEd[0].getText().toString().trim()));
            mParameterBean.setM2_lower_tolerance_value(Double.valueOf(lowerToleranceEd[1].getText().toString().trim()));
            mParameterBean.setM3_lower_tolerance_value(Double.valueOf(lowerToleranceEd[2].getText().toString().trim()));
            mParameterBean.setM4_lower_tolerance_value(Double.valueOf(lowerToleranceEd[3].getText().toString().trim()));
            // 偏差
            mParameterBean.setM1_offect(Double.valueOf(offectEd[0].getText().toString().trim()));
            mParameterBean.setM2_offect(Double.valueOf(offectEd[1].getText().toString().trim()));
            mParameterBean.setM3_offect(Double.valueOf(offectEd[2].getText().toString().trim()));
            mParameterBean.setM4_offect(Double.valueOf(offectEd[3].getText().toString().trim()));

            // 分辨率
//        mParameterBean.setM1_resolution((int) resolutionSp[0].getSelectedItemId());
//        mParameterBean.setM2_resolution((int) resolutionSp[1].getSelectedItemId());
//        mParameterBean.setM3_resolution((int) resolutionSp[2].getSelectedItemId());
//        mParameterBean.setM4_resolution((int) resolutionSp[3].getSelectedItemId());

            // 描述
            mParameterBean.setM1_describe(describeEd[0].getText().toString());
            mParameterBean.setM2_describe(describeEd[1].getText().toString());
            mParameterBean.setM3_describe(describeEd[2].getText().toString());
            mParameterBean.setM4_describe(describeEd[3].getText().toString());


            mParameterBean.setM1_scale(getRbyID((int) resolutionSp[0].getSelectedItemId()));
            mParameterBean.setM2_scale(getRbyID((int) resolutionSp[1].getSelectedItemId()));
            mParameterBean.setM3_scale(getRbyID((int) resolutionSp[2].getSelectedItemId()));
            mParameterBean.setM4_scale(getRbyID((int) resolutionSp[3].getSelectedItemId()));

//        describeEd[0].setText(mParameterBean.getM1_describe());
//        describeEd[1].setText(mParameterBean.getM2_describe());
//        describeEd[2].setText(mParameterBean.getM3_describe());
//        describeEd[3].setText(mParameterBean.getM4_describe());

            //公式
//        mParameterBean.setM1_code(formulaEd.get(0).getText().toString());
//        mParameterBean.setM2_code(code);
//        mParameterBean.setM3_code(code);
//        mParameterBean.setM4_code(code);

            mParameterBean.setM1_enable(mCheckBoxs.get(0).isChecked());
            mParameterBean.setM2_enable(mCheckBoxs.get(1).isChecked());
            mParameterBean.setM3_enable(mCheckBoxs.get(2).isChecked());
            mParameterBean.setM4_enable(mCheckBoxs.get(3).isChecked());
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.number_format_tips, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private double getRbyID(int i) {
        double result = 0.1;
        switch (i) {
            case 0:
                result = 0.1;
                break;
            case 1:
                result = 0.2;
                break;
            case 2:
                result = 0.5;
                break;
            case 3:
                result = 1.0;
                break;
            case 4:
                result = 2.0;
                break;
            case 5:
                result = 5.0;
                break;
            case 6:
                result = 6.0;
                break;
        }
        return result;
    }

    private void syncToServer(final ParameterBean _bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    int ret = JdbcUtil.selectParamConfig(_dBean.getDeviceCode(), App.getSetupBean().getCodeID(), "M1");
                    android.util.Log.d("wlDebug", "" + ret);
                    if (ret > 0) {
                        JdbcUtil.updateParamConfig(_dBean.getFactoryCode(), _dBean.getDeviceCode(), App.getSetupBean().getCodeID(),
                                "程序" + App.getSetupBean().getCodeID(), "", "", "0", 0, 0, "rmk", _bean);
                    } else {
                        JdbcUtil.addParamConfig(_dBean.getFactoryCode(), _dBean.getDeviceCode(), App.getSetupBean().getCodeID(),
                                "程序" + App.getSetupBean().getCodeID(), "", "", "0", 0, 0, "rmk", _bean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
