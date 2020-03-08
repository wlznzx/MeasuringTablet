package alauncher.cn.measuringtablet.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import butterknife.BindView;

public class Input2Activity extends BaseOActivity {

    @BindView(R.id.input_main_layout)
    public ViewGroup mainLayout;

    public TemplateBean mTemplateBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_input2);
    }

    @Override
    protected void initView() {

        mTemplateBean = App.getDaoSession().getTemplateBeanDao().load((long) App.getSetupBean().getCodeID());

        LinearLayout _layout = new LinearLayout(this);
        _layout.setOrientation(LinearLayout.HORIZONTAL);
        _layout.addView(getInfoTV(mTemplateBean.getTitle()), getLayoutParams(0, 2, 10));
        for (int i = 0; i < mTemplateBean.getSignList().size(); i++) {
            LinearLayout signLayout = new LinearLayout(this);
            signLayout.setOrientation(LinearLayout.VERTICAL);
            signLayout.addView(getInfoTV(mTemplateBean.getSignList().get(i)), getItemLayoutParams(1, 2));
            signLayout.addView(getInfoTV(""), getItemLayoutParams(1, 2));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    (int) getResources().getDimension(R.dimen.item_height) * 2);
            params.weight = 2;
            _layout.addView(signLayout, params);
        }
        mainLayout.addView(_layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2, 1));
    }

    public LinearLayout.LayoutParams getLayoutParams(int width, int col, int weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                (int) (getResources().getDimension(R.dimen.item_height) * col));
        params.weight = weight;
        return params;
    }

    public LinearLayout.LayoutParams getItemLayoutParams(int weight, int col) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.item_height) * col);
        params.weight = weight;
        return params;
    }

    public TextView getInfoTV(String msg) {
        TextView tv = new TextView(this);
        tv.setText(msg);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
