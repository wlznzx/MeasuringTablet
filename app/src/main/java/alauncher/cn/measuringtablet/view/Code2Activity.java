package alauncher.cn.measuringtablet.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.bean.User;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.view.activity_view.DataUpdateInterface;
import alauncher.cn.measuringtablet.view.adapter.CodeListAdapter;
import alauncher.cn.measuringtablet.widget.CodeEditDialog;
import butterknife.BindView;
import butterknife.OnClick;


public class Code2Activity extends BaseOActivity implements DataUpdateInterface {

    @BindView(R.id.lv_text_view)
    public ListView listView;

    public int codeID = 1;

    public CodeListAdapter adapter;

    public List<CodeBean> codeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_code2);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, Boolean> states = new HashMap<String, Boolean>();
        codeBeans = App.getDaoSession().getCodeBeanDao().loadAll();
        for (CodeBean bean : codeBeans) {
            android.util.Log.d("wlDebug", "bean = " + bean.toString());
        }
        codeID = App.getSetupBean().getCodeID();
        states.put(String.valueOf(codeID - 1), true);
        if(adapter == null){
            adapter = new CodeListAdapter(codeBeans, states, this, this);
            listView.setAdapter(adapter);
        }
        adapter.setList(codeBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick({R.id.set_btn, R.id.set_as_btn, R.id.add_code_btn})
    public void onSetBtnClick(View v) {
        switch (v.getId()) {
            case R.id.set_btn:
                Intent intent = new Intent(this, CodeDetailActivity.class);
                intent.putExtra("Title", R.string.code_set);
                startActivity(intent);
                break;
            case R.id.set_as_btn:
                SetupBean _sbean = App.getDaoSession().getSetupBeanDao().load(App.SETTING_ID);
                _sbean.setCodeID(adapter.currentCodeID);
                App.getDaoSession().getSetupBeanDao().insertOrReplace(_sbean);
                CodeBean _bean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
                User user = App.getDaoSession().getUserDao().load(App.handlerAccout);
                String _name = App.handlerAccout;
                if (user != null) {
                    _name = user.getName();
                }
                if (_bean != null) {
                    actionTips.setText(_name + " " + _bean.getName());
                } else {
                    actionTips.setText(_name + " 程序" + App.getSetupBean().getCodeID());
                }
                // InputActivity.datas.clear();
                // InputActivity.updates.clear();
                DialogUtils.showDialog(this, "设为当前", "设置成功.");
                break;
            case R.id.add_code_btn:
                CodeEditDialog codeEditDialog = new CodeEditDialog(Code2Activity.this, null);
                codeEditDialog.setDataUpdateInterface(this);
                codeEditDialog.show();
                break;
        }
    }

    @Override
    public void dataUpdate() {
        codeBeans = App.getDaoSession().getCodeBeanDao().loadAll();
        adapter.setList(codeBeans);
        adapter.notifyDataSetChanged();
    }
}
