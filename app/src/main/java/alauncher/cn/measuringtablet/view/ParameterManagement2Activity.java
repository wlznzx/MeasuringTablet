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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.StepBean;
import alauncher.cn.measuringtablet.database.greenDao.db.DaoSession;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.StepBeanDao;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.view.adapter.EnterAdapter;
import alauncher.cn.measuringtablet.view.adapter.ParameterAdapter;
import alauncher.cn.measuringtablet.widget.CalculateDialog;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class ParameterManagement2Activity extends BaseOActivity {

    @BindView(R.id.rv)
    public RecyclerView rv;

    public ParameterAdapter mParameterAdapter;

    private List<Parameter2Bean> beans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_parameter_management2);

    }

    @Override
    protected void initView() {
        beans = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        mParameterAdapter = new ParameterAdapter(ParameterManagement2Activity.this, beans);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ParameterManagement2Activity.this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mParameterAdapter);
    }

    @OnClick(R.id.save_btn)
    public void onSave(View v) {
        for (Parameter2Bean _bean : beans) {
            App.getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
        }
        Toast.makeText(this, "保存成功.", Toast.LENGTH_SHORT).show();
    }

}
