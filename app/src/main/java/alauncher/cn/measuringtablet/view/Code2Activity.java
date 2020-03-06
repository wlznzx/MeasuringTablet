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
import alauncher.cn.measuringtablet.view.adapter.CodeListAdapter;
import butterknife.BindView;
import butterknife.OnClick;


public class Code2Activity extends BaseOActivity {

    @BindView(R.id.lv_text_view)
    public ListView listView;

    private List<String> listText;

    int codeID = 1;

    CodeListAdapter adapter;

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
        HashMap<String, Boolean> states = new HashMap<String, Boolean>();
        List<CodeBean> codeBeans = App.getDaoSession().getCodeBeanDao().loadAll();
        codeID = App.getSetupBean().getCodeID();
        states.put(String.valueOf(codeID - 1), true);
        adapter = new CodeListAdapter(codeBeans, states, codeID, this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick({R.id.set_btn, R.id.set_as_btn})
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
                InputActivity.datas.clear();
                InputActivity.updates.clear();
                break;
        }
    }

    private int getCodeID() {
        return 1;
    }

}
