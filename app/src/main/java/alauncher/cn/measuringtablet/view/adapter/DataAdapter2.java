package alauncher.cn.measuringtablet.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.utils.DateUtils;

/**
 * Created by guohao on 2017/9/6.
 */

public class DataAdapter2 extends RecyclerView.Adapter<ViewHolder> {

    public static final int MYLIVE_MODE_CHECK = 0;
    public static final int MYLIVE_MODE_EDIT = 1;
    private static final String TAG = DataAdapter2.class.getSimpleName();
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public List<ResultBean3> datas;

    private ParameterBean mParameterBean;

    public DataAdapter2(Context context, List<ResultBean3> pDatas, ParameterBean parameterBean) {
        this.mContext = context;
        datas = pDatas;
        mParameterBean = parameterBean;
    }


    public void notifyAdapter(List<ResultBean3> myLiveList, boolean isAdd) {

        if (!isAdd) {
            this.datas = myLiveList;
        } else {
            this.datas.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<ResultBean3> getMyLiveList() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.createViewHolder(mContext, parent, R.layout.data2_list_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.setVisible(R.id.check_box, false);
        } else {
            holder.setVisible(R.id.check_box, true);

            if (datas.get(position).isSelect()) {
                holder.setImageResource(R.id.check_box, R.mipmap.ic_checked);
            } else {
                holder.setImageResource(R.id.check_box, R.mipmap.ic_uncheck);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), datas);
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // setEditMode(MYLIVE_MODE_EDIT);
                mOnItemClickListener.onItemLongClickListener(holder.getAdapterPosition(), datas);
                return false;
            }
        });

        holder.setText(R.id.data_handler, "" + datas.get(position).getHandlerAccout());
        holder.setText(R.id.data_workpiece_id, "" + datas.get(position).getWorkid());
        holder.setText(R.id.data_event, datas.get(position).getEvent());
        holder.setText(R.id.data_m1, "" + datas.get(position).getMValues().get(0));
        ImageView m1Iv = holder.getConvertView().findViewById(R.id.data_m1_pic);
        holder.setOnClickListener(R.id.data_m1_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        Glide.with(mContext).load("file://" + datas.get(position).getMPicPaths().get(0))
                .into((ImageView) holder.getConvertView().findViewById(R.id.data_m1_pic));

        /*
        holder.setText(R.id.data_m1_group, "" + datas.get(position).getM1_group());
        holder.setText(R.id.data_m2, "" + datas.get(position).m2);
        holder.setText(R.id.data_m2_group, "" + datas.get(position).getM2_group());
        holder.setText(R.id.data_m3, "" + datas.get(position).m3);
        holder.setText(R.id.data_m3_group, "" + datas.get(position).getM3_group());
        holder.setText(R.id.data_m4, "" + datas.get(position).m4);
        holder.setText(R.id.data_m4_group, "" + datas.get(position).getM4_group());
        holder.setText(R.id.data_time, DateUtils.getDate(datas.get(position).getTimeStamp()));
        holder.setText(R.id.data_result, "" + datas.get(position).getResult());
        holder.setText(R.id.data_num, "" + (position + 1));
        */

        /*
        if (mParameterBean != null) {
            holder.setVisible(R.id.data_m1, mParameterBean.getM1_enable() ? true : false);
            holder.setVisible(R.id.data_m1_group, mParameterBean.getM1_enable() ? true : false);
            holder.setVisible(R.id.data_m2, mParameterBean.getM2_enable() ? true : false);
            holder.setVisible(R.id.data_m2_group, mParameterBean.getM2_enable() ? true : false);
            holder.setVisible(R.id.data_m3, mParameterBean.getM3_enable() ? true : false);
            holder.setVisible(R.id.data_m3_group, mParameterBean.getM3_enable() ? true : false);
            holder.setVisible(R.id.data_m4, mParameterBean.getM4_enable() ? true : false);
            holder.setVisible(R.id.data_m4_group, mParameterBean.getM4_enable() ? true : false);
        }
        */

        if (position % 2 == 0) {
            // holder.setBackgroundColor(R.id.data_layout, Color.argb(250, 255, 255, 255));
            holder.setBackgroundColor(R.id.data_layout, Color.argb(100, 69, 90, 100));
        } else {
            holder.setBackgroundColor(R.id.data_layout, Color.argb(50, 69, 90, 100));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<ResultBean3> myLiveList);

        void onItemLongClickListener(int pos, List<ResultBean3> myLiveList);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

}
