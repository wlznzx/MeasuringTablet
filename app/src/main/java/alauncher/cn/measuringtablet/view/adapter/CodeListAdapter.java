package alauncher.cn.measuringtablet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.utils.DateUtils;

/**
 * Created by guohao on 2017/9/6.
 */

public class CodeListAdapter extends BaseAdapter {
    private List<CodeBean> listText;
    private Context context;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    public HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public int currentCodeID;

    public CodeListAdapter(List<CodeBean> listText, HashMap<String, Boolean> pstates, int code, Context context) {
        this.listText = listText;
        this.states = pstates;
        this.context = context;
        currentCodeID = code;
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
        TextView radioText = (TextView) view.findViewById(R.id.tv_radio_text);
        //单选按钮
        RadioButton radioButton = view.findViewById(R.id.rb_radio_button);
        radioButton.setText(String.valueOf(listText.get(position).getCodeID()));
        radioText.setText(listText.get(position).getName());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(position), true);
                currentCodeID = position + 1;
                CodeListAdapter.this.notifyDataSetChanged();
            }
        });
        boolean res = false;
        if (states.get(String.valueOf(position)) == null || states.get(String.valueOf(position)) == false) {
            res = false;
            states.put(String.valueOf(position), false);
        } else
            res = true;

        radioButton.setChecked(res);
        return view;
    }

}
