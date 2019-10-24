package alauncher.cn.measuringtablet.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
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

    private List<Parameter2Bean> mParameterBean;

    private List<Integer> mValueIDs = new ArrayList<>();
    private List<Integer> mPicIDs = new ArrayList<>();

    public DataAdapter2(Context context, List<ResultBean3> pDatas, List<Parameter2Bean> parameterBeans) {
        this.mContext = context;
        datas = pDatas;
        mParameterBean = parameterBeans;
        initIDs();
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

        holder.setText(R.id.data_num, "" + (position + 1));
        holder.setText(R.id.data_handler, "" + datas.get(position).getHandlerAccout());
        holder.setText(R.id.data_workpiece_id, "- -");
        holder.setText(R.id.data_event, "- -");
        holder.setText(R.id.data_result, datas.get(position).getResult());
        holder.setText(R.id.data_time, DateUtils.getDate(datas.get(position).getTimeStamp()));
         /*
        holder.setText(R.id.data_m1, "" + datas.get(position).getMValues().get(0));

        Glide.with(mContext).load("file://" + datas.get(position).getMPicPaths().get(0))
                .into((ImageView) holder.getConvertView().findViewById(R.id.data_m1_pic));

        holder.setText(R.id.data_m2, "" + datas.get(position).getMValues().get(1));
        holder.setOnClickListener(R.id.data_m2_pic, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage(datas.get(position).getMPicPaths().get(1));
            }
        });
        Glide.with(mContext).load("file://" + datas.get(position).getMPicPaths().get(1))
                .into((ImageView) holder.getConvertView().findViewById(R.id.data_m2_pic));
        */


        for (int i = 0; i < mValueIDs.size(); i++) {
//            if (mParameterBean.get(i).getEnable()) {
            holder.setText(mValueIDs.get(i), (datas.get(position).getMValues().get(i) == null || datas.get(position).getMValues().get(i).equals("null")) ? "- -" : datas.get(position).getMValues().get(i));
            try {
                if (datas.get(position).getMPicPaths().get(i) != null && !datas.get(position).getMPicPaths().get(i).equals("")) {
                    Glide.with(mContext).load("file://" + datas.get(position).getMPicPaths().get(i))
                            .into((ImageView) holder.getConvertView().findViewById(mPicIDs.get(i)));
                    final int _i = i;
                    holder.setOnClickListener(mPicIDs.get(i), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openImage(datas.get(position).getMPicPaths().get(_i));
                        }
                    });
                } else {

                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        /*
        ;
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

    public void openImage(String path) {
        File file = new File(path);
        if (file.exists()) {
            Uri uri = FileProvider.getUriForFile(mContext, "alauncher.cn.measuringtablet.fileProvider", file);
            // Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "image/jpeg");

            mContext.startActivity(intent);
        } else {
            // Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
        }

    }


    private void initIDs() {
        mValueIDs.add(R.id.data_m1);
        mValueIDs.add(R.id.data_m2);
        mValueIDs.add(R.id.data_m3);
        mValueIDs.add(R.id.data_m4);
        mValueIDs.add(R.id.data_m5);
        mValueIDs.add(R.id.data_m6);
        mValueIDs.add(R.id.data_m7);
        mValueIDs.add(R.id.data_m8);
        mValueIDs.add(R.id.data_m9);
        mValueIDs.add(R.id.data_m10);
        mValueIDs.add(R.id.data_m11);
        mValueIDs.add(R.id.data_m12);
        mValueIDs.add(R.id.data_m13);
        mValueIDs.add(R.id.data_m14);
        mValueIDs.add(R.id.data_m15);
        mValueIDs.add(R.id.data_m16);
        mValueIDs.add(R.id.data_m17);
        mValueIDs.add(R.id.data_m18);
        mValueIDs.add(R.id.data_m19);
        mValueIDs.add(R.id.data_m20);
        mValueIDs.add(R.id.data_m21);
        mValueIDs.add(R.id.data_m22);

        mPicIDs.add(R.id.data_m1_pic);
        mPicIDs.add(R.id.data_m2_pic);
        mPicIDs.add(R.id.data_m3_pic);
        mPicIDs.add(R.id.data_m4_pic);
        mPicIDs.add(R.id.data_m5_pic);
        mPicIDs.add(R.id.data_m6_pic);
        mPicIDs.add(R.id.data_m7_pic);
        mPicIDs.add(R.id.data_m8_pic);
        mPicIDs.add(R.id.data_m9_pic);
        mPicIDs.add(R.id.data_m10_pic);
        mPicIDs.add(R.id.data_m11_pic);
        mPicIDs.add(R.id.data_m12_pic);
        mPicIDs.add(R.id.data_m13_pic);
        mPicIDs.add(R.id.data_m14_pic);
        mPicIDs.add(R.id.data_m15_pic);
        mPicIDs.add(R.id.data_m16_pic);
        mPicIDs.add(R.id.data_m17_pic);
        mPicIDs.add(R.id.data_m18_pic);
        mPicIDs.add(R.id.data_m19_pic);
        mPicIDs.add(R.id.data_m20_pic);
        mPicIDs.add(R.id.data_m21_pic);
        mPicIDs.add(R.id.data_m22_pic);
    }
}
