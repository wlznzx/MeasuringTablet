package alauncher.cn.measuringtablet;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.base.ViewHolder;
import alauncher.cn.measuringtablet.view.LoginActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

public class MainActivity extends BaseOActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        List<MainInfo> _datas = new ArrayList();
        _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.InputActivity", R.string.measuring, R.drawable.equalizer));
        try {
            if (App.getDaoSession().getUserDao().load(App.handlerAccout).getLimit() < 2) {
                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.Data2Activity", R.string.data_query, R.drawable.find_in_page));
                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.ParameterManagement2Activity", R.string.parameter_management, R.drawable.functions));
//                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.CalibrationActivity", R.string.calibration, R.drawable.straighten));
                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.CodeActivity", R.string.program_management, R.drawable.code));
                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.AccoutManagementActivity", R.string.user_management, R.drawable.account_box));
                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.SystemManagementActivity", R.string.system_management, R.drawable.phonelink_setup));
//                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.StoreActivity", R.string.store, R.drawable.archive));
//                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.SPCStatisticalActivity", R.string.spc_analysis, R.drawable.show_chart));
//                _datas.add(new MainInfo("alauncher.cn.measuringtablet.view.StatisticalActivity", R.string.statistical_report, R.drawable.assignment));
                _datas.add(new MainInfo("", R.string.logout, R.drawable.logout_96));
            }
        } catch (NullPointerException e) {

        }
        MainLayoutAdapter _adapter = new MainLayoutAdapter(_datas);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rv.addItemDecoration(new RecyclerItemDecoration(24, 3));
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(_adapter);

        actionIV.setImageResource(R.drawable.power_button);
        actionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        actionTitleTV.setOnClickListener(new View.OnClickListener() {
            final static int COUNTS = 5;//点击次数
            final static long DURATION = 2 * 1000;//规定有效时间
            long[] mHits = new long[COUNTS];

            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于DURATION，即连续5次点击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
                    // String tips = "您已在[" + DURATION + "]ms内连续点击【" + mHits.length + "】次了！！！";
                    // Toast.makeText(MainActivity.this, tips, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.android.launcher3", "com.android.launcher3.Launcher");
                    intent.setComponent(cn);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, "未找到主界面.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void logout() {
        final AlertDialog builder = new AlertDialog.Builder(MainActivity.this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;
        msg.setText("确认注销当前账号？");
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    class MainInfo {

        public String className;
        public int strID;
        public int drawableID;

        public MainInfo(String className, int strID, int drawableID) {
            this.className = className;
            this.strID = strID;
            this.drawableID = drawableID;
        }
    }

    class MainLayoutAdapter extends RecyclerView.Adapter<ViewHolder> {

        List<MainInfo> datas;

        public MainLayoutAdapter(List<MainInfo> pDatas) {
            datas = pDatas;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return ViewHolder.createViewHolder(MainActivity.this, parent, R.layout.main_layout_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setImageResource(R.id.main_item_iv, datas.get(position).drawableID);
            holder.setText(R.id.main_item_tv, datas.get(position).strID);
            holder.setOnClickListener(R.id.main_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        openActivty(Class.forName(datas.get(position).className), datas.get(position).strID);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        exitDialog();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
        private int itemSpace;
        private int itemNum;

        /**
         * @param itemSpace item间隔
         * @param itemNum   每行item的个数
         */
        public RecyclerItemDecoration(int itemSpace, int itemNum) {
            this.itemSpace = itemSpace;
            this.itemNum = itemNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = itemSpace;
            if (parent.getChildLayoutPosition(view) % itemNum == 0) {  //parent.getChildLayoutPosition(view) 获取view的下标
                outRect.left = 0;
            } else {
                // outRect.left = itemSpace;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }


    private AlertDialog builder;

    private void exitDialog() {
        // new AlertDialog.Builder(this).setTitle("列表框").setItems(new String[]{"Item1", "Item2"}, null).show();
        builder = new AlertDialog.Builder(MainActivity.this).create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.exit_dialog);//设置弹出框加载的布局
        TextView cancellationTV = (TextView) builder.findViewById(R.id.cancellation_btn);
        TextView exitTV = (TextView) builder.findViewById(R.id.exit_btn);
        TextView quitTV = (TextView) builder.findViewById(R.id.quit_btn);
        cancellationTV.setOnClickListener(this::onClick);
        exitTV.setOnClickListener(this::onClick);
        quitTV.setOnClickListener(this::onClick);
    }

    public void onClick(View v) {
        builder.dismiss();
        switch (v.getId()) {
            case R.id.cancellation_btn:
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.exit_btn:
                finish();
                break;
            case R.id.quit_btn:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.launcher3", "com.android.launcher3.Launcher");
                intent.setComponent(cn);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "未找到主界面.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
