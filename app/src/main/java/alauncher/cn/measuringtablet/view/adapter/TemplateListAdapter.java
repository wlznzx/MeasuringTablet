package alauncher.cn.measuringtablet.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.database.greenDao.db.CodeBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplateBeanDao;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.view.activity_view.DataUpdateInterface;

/**
 * Created by guohao on 2017/9/6.
 */

public class TemplateListAdapter extends BaseAdapter {
    private List<TemplateBean> listText;
    private Context context;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    public HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public long currentCodeID;

    private DataUpdateInterface mDataUpdateInterface;

    public TemplateListAdapter(List<TemplateBean> listText, HashMap<String, Boolean> pstates,
                               DataUpdateInterface pDataUpdateInterface, Context context, int currentID) {
        this.listText = listText;
        this.states = pstates;
        this.context = context;
        mDataUpdateInterface = pDataUpdateInterface;
        currentCodeID = currentID;
    }

    public void setList(List<TemplateBean> pList) {
        listText = pList;
    }

    @Override
    public int getCount() {
        //return返回的是int类型，也就是页面要显示的数量。
        return listText.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            //通过一个打气筒 inflate 可以把一个布局转换成一个view对象
            view = View.inflate(context, R.layout.list_code_item, null);
        } else {
            view = convertView;//复用历史缓存对象
        }
        //单选按钮的文字
        TextView radioText = view.findViewById(R.id.tv_radio_text);
        //单选按钮
        RadioButton radioButton = view.findViewById(R.id.rb_radio_button);
        radioButton.setText(String.valueOf(position + 1));
        radioText.setText(listText.get(position).getTitle());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), true);
                currentCodeID = listText.get(position).getTemplateID().intValue();
                TemplateListAdapter.this.notifyDataSetChanged();
            }
        });
        boolean res;
        /*
        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
        }
        */

        if (currentCodeID == listText.get(position).getTemplateID()) {
            res = true;
        } else {
            res = false;
        }


        radioButton.setChecked(res);

        radioButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (App.getSetupBean().getCodeID() == listText.get(position).getTemplateID()) {
                    DialogUtils.showDialog(context, context.getResources().getString(R.string.cannot_delete),
                            context.getResources().getString(R.string.cannot_delete_msg));
                    return false;
                }

                final AlertDialog builder = new AlertDialog.Builder(context)
                        .create();
                builder.show();
                if (builder.getWindow() == null) return false;
                builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                TextView msg = builder.findViewById(R.id.tv_msg);
                Button cancel = builder.findViewById(R.id.btn_cancle);
                Button sure = builder.findViewById(R.id.btn_sure);
                msg.setText("是否删除 " + listText.get(position).getTitle() + " ?");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.getDaoSession().getTemplateBeanDao().queryBuilder().where(TemplateBeanDao.Properties.TemplateID.eq(listText.get(position).getTemplateID())).buildDelete().executeDeleteWithoutDetachingEntities();
                        builder.dismiss();
                        if (mDataUpdateInterface != null) {
                            mDataUpdateInterface.dataUpdate();
                        }
                    }
                });
                return false;
            }
        });
        return view;
    }

}
