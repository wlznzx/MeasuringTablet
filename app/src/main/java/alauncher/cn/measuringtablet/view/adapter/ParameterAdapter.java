package alauncher.cn.measuringtablet.view.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.nfunk.jep.function.Str;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean;

/**
 * Created by guohao on 2017/9/6.
 */

public class ParameterAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    List<Parameter2Bean> datas;

    public ParameterAdapter(Context context, List<Parameter2Bean> parameterBean) {
        mContext = context;
        datas = parameterBean;
    }


    public void notifyAdapter(List<Parameter2Bean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.datas = myLiveList;
        } else {
            this.datas.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<Parameter2Bean> getMyLiveList() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.parameter_list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Parameter2Bean _bean = datas.get(position);

        holder.setText(R.id.m_index_tv, "M" + _bean.getIndex());
        holder.setText(R.id.parameter_name_edt, _bean.getDescribe());
        holder.setChecked(R.id.m_enable, _bean.getEnable());
        holder.setText(R.id.nominal_value_edt, String.valueOf(_bean.getNominal_value()));
        holder.setText(R.id.upper_tolerance_edt, String.valueOf(_bean.getUpper_tolerance_value()));
        holder.setText(R.id.lower_tolerance_edt, String.valueOf(_bean.getLower_tolerance_value()));

        CheckBox _box = holder.getConvertView().findViewById(R.id.m_enable);
        _box.setTag(position);
        _box.setOnCheckedChangeListener(checkboxListener);

        EditText _parameter_name_edt = holder.getConvertView().findViewById(R.id.parameter_name_edt);
        _parameter_name_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                datas.get(position).setDescribe(s.toString());
            }
        });

        EditText _nominalEdt = holder.getConvertView().findViewById(R.id.nominal_value_edt);
        _nominalEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    datas.get(position).setNominal_value(Double.valueOf(s.toString().trim()));
                } catch (Exception e) {

                }
            }
        });

        EditText _upper_tolerance_edt = holder.getConvertView().findViewById(R.id.upper_tolerance_edt);
        _upper_tolerance_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    datas.get(position).setUpper_tolerance_value(Double.valueOf(s.toString().trim()));
                } catch (Exception e) {

                }
            }
        });

        EditText _lower_tolerance_edt = holder.getConvertView().findViewById(R.id.lower_tolerance_edt);
        _lower_tolerance_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    datas.get(position).setLower_tolerance_value(Double.valueOf(s.toString().trim()));
                } catch (Exception e) {

                }
            }
        });

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<ResultBean> myLiveList);

        void onItemLongClickListener(int pos, List<ResultBean> myLiveList);
    }

    public void setEditMode(int editMode) {
        notifyDataSetChanged();
    }


    public CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            datas.get((Integer) buttonView.getTag()).setEnable(isChecked);
        }
    };
}
