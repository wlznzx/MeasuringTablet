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
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.view.activity_view.DataUpdateInterface;
import alauncher.cn.measuringtablet.view.adapter.TemplateListAdapter;
import butterknife.BindView;
import butterknife.OnClick;


public class TemplateManagerActivity extends BaseOActivity implements DataUpdateInterface {

    @BindView(R.id.lv_text_view)
    public ListView listView;

    public long codeID = 1;

    public TemplateListAdapter adapter;

    public List<TemplateBean> codeBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_template_manager);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap<String, Boolean> states = new HashMap<String, Boolean>();
        codeBeans = App.getDaoSession().getTemplateBeanDao().loadAll();
        for (TemplateBean bean : codeBeans) {
            android.util.Log.d("wlDebug", "bean = " + bean.toString());
        }
        codeID = App.getDaoSession().getCodeBeanDao().load(Long.valueOf(App.getSetupBean().getCodeID())).getUseTemplateID();
        android.util.Log.d("wlDebug", "codeID = " + codeID);
        states.put(String.valueOf(codeID - 1), true);
        adapter = new TemplateListAdapter(codeBeans, states, this, this, (int) codeID);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @OnClick({R.id.edit_template_btn, R.id.set_as_btn, R.id.add_template_btn, R.id.copy_add_template_btn})
    public void onSetBtnClick(View v) {
        android.util.Log.d("wlDebug", "adapter.currentCodeID = " + adapter.currentCodeID);
        switch (v.getId()) {
            case R.id.edit_template_btn:
                Intent edit_TemplateIntent = new Intent(this, TemplateActivity.class);
                edit_TemplateIntent.putExtra("TEMPLATE_ID", adapter.currentCodeID);
                edit_TemplateIntent.putExtra("IS_COPY", false);
                edit_TemplateIntent.putExtra("Title", R.string.edit_template);
                startActivity(edit_TemplateIntent);
                break;
            case R.id.set_as_btn:
                CodeBean _bean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
                _bean.setUseTemplateID((long) adapter.currentCodeID);
                App.getDaoSession().insertOrReplace(_bean);
                codeID = adapter.currentCodeID;
                InputActivity.datas.clear();
                InputActivity.updates.clear();
                DialogUtils.showDialog(this, "设为当前", "设置成功.");
                break;
            case R.id.add_template_btn:
                Intent intent = new Intent(this, TemplateActivity.class);
                intent.putExtra("Title", R.string.add_template);
                intent.putExtra("TEMPLATE_ID", -1);
                startActivity(intent);
                break;
            case R.id.copy_add_template_btn:
                Intent addTemplateIntent = new Intent(this, TemplateActivity.class);
                addTemplateIntent.putExtra("Title", R.string.copy_add_template);
                addTemplateIntent.putExtra("TEMPLATE_ID", adapter.currentCodeID);
                addTemplateIntent.putExtra("IS_COPY", true);
                startActivity(addTemplateIntent);
                break;
        }
    }

    @Override
    public void dataUpdate() {
        codeBeans = App.getDaoSession().getTemplateBeanDao().loadAll();
        adapter.setList(codeBeans);
        adapter.notifyDataSetChanged();
    }
}
