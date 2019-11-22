package alauncher.cn.measuringtablet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.view.InputActivity;

/**
 * Created by guohao on 2017/9/6.
 */

public class EnterAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public List<InputActivity.InputBean> datas;

    public EnterAdapter(Context context, List<InputActivity.InputBean> parameterBean) {
        mContext = context;
        datas = parameterBean;
    }


    public void notifyAdapter(List<InputActivity.InputBean> myLiveList, boolean isAdd) {

        if (!isAdd) {
            this.datas = myLiveList;
        } else {
            this.datas.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<InputActivity.InputBean> getMyLiveList() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.enter_list_item);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        InputActivity.InputBean _bean = datas.get(position);
        holder.setText(R.id.m_id_tv, _bean.measuringStr);
        holder.setText(R.id.m_info_tv, _bean.describeStr);

        if (datas.get(position).workspace1PicPath != null && !(datas.get(position).workspace1PicPath.equals(""))) {
            File file = new File(datas.get(position).workspace1PicPath);
            Glide.with(mContext).load(file).into((ImageView) holder.getConvertView().findViewById(R.id.m_workspace1_iv));
        }
        if (datas.get(position).workspace2PicPath != null && !(datas.get(position).workspace2PicPath.equals(""))) {
            File file = new File(datas.get(position).workspace2PicPath);
            Glide.with(mContext).load(file).into((ImageView) holder.getConvertView().findViewById(R.id.m_workspace2_iv));
        }
        if (datas.get(position).workspace3PicPath != null && !(datas.get(position).workspace3PicPath.equals(""))) {
            File file = new File(datas.get(position).workspace3PicPath);
            Glide.with(mContext).load(file).into((ImageView) holder.getConvertView().findViewById(R.id.m_workspace3_iv));
        }
        if (datas.get(position).workspace4PicPath != null && !(datas.get(position).workspace4PicPath.equals(""))) {
            File file = new File(datas.get(position).workspace4PicPath);
            Glide.with(mContext).load(file).into((ImageView) holder.getConvertView().findViewById(R.id.m_workspace4_iv));
        }
        if (datas.get(position).workspace5PicPath != null && !(datas.get(position).workspace5PicPath.equals(""))) {
            File file = new File(datas.get(position).workspace5PicPath);
            Glide.with(mContext).load(file).into((ImageView) holder.getConvertView().findViewById(R.id.m_workspace5_iv));
        }
        holder.setOnClickListener(R.id.m_workspace1_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, datas, 1);
            }
        });
        holder.setOnClickListener(R.id.m_workspace2_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, datas, 2);
            }
        });
        holder.setOnClickListener(R.id.m_workspace3_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, datas, 3);
            }
        });
        holder.setOnClickListener(R.id.m_workspace4_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, datas, 4);
            }
        });
        holder.setOnClickListener(R.id.m_workspace5_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(position, datas, 5);
            }
        });


        EditText workpiece1Edt = holder.getConvertView().findViewById(R.id.m_workspace1_tv);
        EditText workpiece2Edt = holder.getConvertView().findViewById(R.id.m_workspace2_tv);
        EditText workpiece3Edt = holder.getConvertView().findViewById(R.id.m_workspace3_tv);
        EditText workpiece4Edt = holder.getConvertView().findViewById(R.id.m_workspace4_tv);
        EditText workpiece5Edt = holder.getConvertView().findViewById(R.id.m_workspace5_tv);
        Spinner workspace1SP = holder.getConvertView().findViewById(R.id.m_workspace1_sp);
        Spinner workspace2SP = holder.getConvertView().findViewById(R.id.m_workspace2_sp);
        Spinner workspace3SP = holder.getConvertView().findViewById(R.id.m_workspace3_sp);
        Spinner workspace4SP = holder.getConvertView().findViewById(R.id.m_workspace4_sp);
        Spinner workspace5SP = holder.getConvertView().findViewById(R.id.m_workspace5_sp);
        workpiece1Edt.setText(_bean.workspace1Value);
        workpiece2Edt.setText(_bean.workspace2Value);
        workpiece3Edt.setText(_bean.workspace3Value);
        workpiece4Edt.setText(_bean.workspace4Value);
        workpiece5Edt.setText(_bean.workspace5Value);
        workpiece1Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnItemClickListener.onTextWatch(position, 1, s.toString());
            }
        });

        workpiece2Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnItemClickListener.onTextWatch(position, 2, s.toString());
            }
        });

        workpiece3Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnItemClickListener.onTextWatch(position, 3, s.toString());
            }
        });

        workpiece4Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnItemClickListener.onTextWatch(position, 4, s.toString());
            }
        });

        workpiece5Edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mOnItemClickListener.onTextWatch(position, 5, s.toString());
            }
        });

        workspace1SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        workpiece1Edt.setText(null);
                        break;
                    case 1:
                        workpiece1Edt.setText("1");
                        break;
                    case 2:
                        workpiece1Edt.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workspace2SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        workpiece2Edt.setText(null);
                        break;
                    case 1:
                        workpiece2Edt.setText("1");
                        break;
                    case 2:
                        workpiece2Edt.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workspace3SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        workpiece3Edt.setText(null);
                        break;
                    case 1:
                        workpiece3Edt.setText("1");
                        break;
                    case 2:
                        workpiece3Edt.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workspace4SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        workpiece4Edt.setText(null);
                        break;
                    case 1:
                        workpiece4Edt.setText("1");
                        break;
                    case 2:
                        workpiece4Edt.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        workspace5SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        workpiece5Edt.setText(null);
                        break;
                    case 1:
                        workpiece5Edt.setText("1");
                        break;
                    case 2:
                        workpiece5Edt.setText("0");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (_bean.isSp) {
            workpiece1Edt.setVisibility(View.GONE);
            workpiece2Edt.setVisibility(View.GONE);
            workpiece3Edt.setVisibility(View.GONE);
            workpiece4Edt.setVisibility(View.GONE);
            workpiece5Edt.setVisibility(View.GONE);
            workspace1SP.setVisibility(View.VISIBLE);
            workspace2SP.setVisibility(View.VISIBLE);
            workspace3SP.setVisibility(View.VISIBLE);
            workspace4SP.setVisibility(View.VISIBLE);
            workspace5SP.setVisibility(View.VISIBLE);
            if (_bean.workspace1Value == null || _bean.workspace1Value.equals("")) {
                workspace1SP.setSelection(0);
            } else {
                workspace1SP.setSelection(_bean.workspace1Value.equals("1") ? 1 : 2);
            }

            if (_bean.workspace2Value == null || _bean.workspace2Value.equals("")) {
                workspace2SP.setSelection(0);
            } else {
                workspace2SP.setSelection(_bean.workspace2Value.equals("1") ? 1 : 2);
            }

            if (_bean.workspace1Value == null || _bean.workspace3Value.equals("")) {
                workspace3SP.setSelection(0);
            } else {
                workspace3SP.setSelection(_bean.workspace3Value.equals("1") ? 1 : 2);
            }

            if (_bean.workspace4Value == null || _bean.workspace4Value.equals("")) {
                workspace4SP.setSelection(0);
            } else {
                workspace4SP.setSelection(_bean.workspace4Value.equals("1") ? 1 : 2);
            }

            if (_bean.workspace5Value == null || _bean.workspace5Value.equals("")) {
                workspace5SP.setSelection(0);
            } else {
                workspace5SP.setSelection(_bean.workspace5Value.equals("1") ? 1 : 2);
            }
        } else {
            workpiece1Edt.setVisibility(View.VISIBLE);
            workpiece2Edt.setVisibility(View.VISIBLE);
            workpiece3Edt.setVisibility(View.VISIBLE);
            workpiece4Edt.setVisibility(View.VISIBLE);
            workpiece5Edt.setVisibility(View.VISIBLE);
            workspace1SP.setVisibility(View.GONE);
            workspace2SP.setVisibility(View.GONE);
            workspace3SP.setVisibility(View.GONE);
            workspace4SP.setVisibility(View.GONE);
            workspace5SP.setVisibility(View.GONE);
        }

        holder.setText(R.id.m_max_tv, _bean.maxValue == null ? "- -" : _bean.maxValue);
        holder.setText(R.id.m_min_tv, _bean.maxValue == null ? "- -" : _bean.minValue);

        holder.setText(R.id.m_judge_tv, _bean.judge == null ? "- -" : _bean.judge);
        TextView judgeTv = holder.getConvertView().findViewById(R.id.m_judge_tv);
        if (_bean.judge != null) {
            holder.setTextColor(R.id.m_judge_tv, _bean.judge.equals("OK") ? Color.GREEN : Color.RED);
        }
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
        void onItemClickListener(int pos, List<InputActivity.InputBean> myLiveList, int workpiece_num);

        void onItemLongClickListener(int pos, List<InputActivity.InputBean> myLiveList);

        void onTextWatch(int pos, int index, String s);
    }

    public void setEditMode(int editMode) {
        notifyDataSetChanged();
    }

}
