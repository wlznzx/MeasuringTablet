package alauncher.cn.measuringtablet.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.utils.DateUtils;
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
