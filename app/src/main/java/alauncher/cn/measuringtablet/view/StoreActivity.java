package alauncher.cn.measuringtablet.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.StoreBean;
import alauncher.cn.measuringtablet.database.greenDao.db.StoreBeanDao;
import butterknife.BindView;
import butterknife.OnClick;


public class StoreActivity extends BaseOActivity {

    @BindView(R.id.store_mode_rg)
    RadioGroup storeModeRG;

    @BindView(R.id.store_value_sp)
    Spinner storeValueSP;

    @BindView(R.id.store_upper_limit_edt)
    EditText storeUpperLimitEdt;

    @BindView(R.id.store_lower_limit_edt)
    EditText storeLowerLimitEdt;

    @BindView(R.id.store_time)
    EditText storeDelayTime;

    StoreBeanDao mStoreBeanDao;

    StoreBean mStoreBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_store);
    }

    @Override
    protected void initView() {
        mStoreBeanDao = App.getDaoSession().getStoreBeanDao();
        mStoreBean = mStoreBeanDao.load(App.SETTING_ID);

        storeModeRG.check(getModeID(mStoreBean.storeMode));
        storeUpperLimitEdt.setText("" + mStoreBean.getUpLimitValue());
        storeLowerLimitEdt.setText("" + mStoreBean.getLowLimitValue());
        storeDelayTime.setText("" + mStoreBean.getDelayTime());
        storeValueSP.setSelection(mStoreBean.getMValue());
    }

    private StoreBean view2Bean() {
        StoreBean _bean = new StoreBean();
        _bean.setId(App.SETTING_ID);
        _bean.setMValue(storeValueSP.getSelectedItemPosition());
        _bean.setStoreMode(storeModeRG.getCheckedRadioButtonId() == R.id.store_auto ? 1 : 2);
        try{
            _bean.setUpLimitValue(Double.valueOf(storeUpperLimitEdt.getText().toString().trim()));
            _bean.setLowLimitValue(Double.valueOf(storeLowerLimitEdt.getText().toString().trim()));
        }catch (NumberFormatException e){
            Toast.makeText(this, R.string.input_fail, Toast.LENGTH_SHORT).show();
            return null;
        }
        _bean.setDelayTime(Integer.valueOf(storeDelayTime.getText().toString().trim()));
        return _bean;
    }

    @OnClick(R.id.save_btn)
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                StoreBean _bean = view2Bean();
                if(_bean != null){
                    mStoreBeanDao.update(_bean);
                    Toast.makeText(this, "保存成功.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private int getModeID(int code) {
        switch (code) {
            case 1:
                return R.id.store_auto;
            case 2:
                return R.id.store_manual;
        }
        return R.id.store_auto;
    }

}
