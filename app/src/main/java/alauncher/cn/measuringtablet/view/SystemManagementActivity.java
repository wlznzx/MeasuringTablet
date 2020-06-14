package alauncher.cn.measuringtablet.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.base.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.bugly.beta.Beta;

import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplatePicBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean3Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplatePicBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplateResultBeanDao;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import butterknife.BindView;

public class SystemManagementActivity extends BaseOActivity {

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
        _datas.add(new MainInfo(R.string.communication, R.drawable.settings_ethernet));
        _datas.add(new MainInfo(R.string.io, R.drawable.settings_input_svideo));
        _datas.add(new MainInfo(R.string.system_backup, R.drawable.archive));
        // _datas.add(new MainInfo(R.string.force_calibration, R.drawable.straighten));
        _datas.add(new MainInfo(R.string.set, R.drawable.settings));
        _datas.add(new MainInfo(R.string.wifi_str, R.drawable.wifi));
        _datas.add(new MainInfo(R.string.upgrade, R.drawable.upgrade));
        _datas.add(new MainInfo(R.string.clean_uploaded, R.drawable.clear));
        MainLayoutAdapter _adapter = new MainLayoutAdapter(_datas);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rv.addItemDecoration(new RecyclerItemDecoration(24, 3));
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(_adapter);
    }

    class MainInfo {
        public int strID;
        public int drawableID;

        public MainInfo(int strID, int drawableID) {
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
            return ViewHolder.createViewHolder(SystemManagementActivity.this, parent, R.layout.main_layout_item);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.setImageResource(R.id.main_item_iv, datas.get(position).drawableID);
            holder.setText(R.id.main_item_tv, datas.get(position).strID);
            holder.setOnClickListener(R.id.main_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:
                            Intent _i = new Intent(SystemManagementActivity.this, CommunicationActivity.class);
                            _i.putExtra("Title", R.string.communication);
                            startActivity(_i);
                            break;
                        case 1:
                            startActivity(new Intent(SystemManagementActivity.this, IOActivity.class).putExtra("Title", R.string.io));
                            break;
                        case 2:
                            startActivity(new Intent(SystemManagementActivity.this, BackupActivity.class).putExtra("Title", R.string.system_backup));
                            break;
                        case 3:
                            startActivity(new Intent(SystemManagementActivity.this, SetActivity.class).putExtra("Title", R.string.set));
                            break;
                        case 4:
                            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                            break;
                        case 5:
//                            ComponentName cn = new ComponentName("com.tencent.bugly.beta.ui", "com.tencent.bugly.beta.ui.BetaActivity");
//                            Intent intent = new Intent();
//                            intent.setComponent(cn);
//                            startActivity(intent);
                            Beta.checkUpgrade();
                            break;
                        case 6:
                            List<TemplateResultBean> templateResultBeans = App.getDaoSession().getTemplateResultBeanDao().queryBuilder().where(TemplateResultBeanDao.Properties.IsUpload.eq(true)).list();
                            DialogUtils.showDialog(SystemManagementActivity.this,"清除数据","共有" + templateResultBeans.size() + "条已上传模板数据，是否删除？", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new SyncTask().execute();
                                }
                            });
                            break;
                        default:
                            break;
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

    public class SyncTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SystemManagementActivity.this);
            dialog.setTitle("删除中.");
            dialog.setMessage("正在删除数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作
            int sum = 0, uploadSum = 0;
            List<TemplateResultBean> templateResultBeans = App.getDaoSession().getTemplateResultBeanDao().queryBuilder().where(TemplateResultBeanDao.Properties.IsUpload.eq(true)).list();
            for (int i = 0; i < templateResultBeans.size(); i++) {
                List<ResultBean3> bean3s = App.getDaoSession().getResultBean3Dao().queryBuilder().where(ResultBean3Dao.Properties.TemplateID.eq(templateResultBeans.get(i).getId())).list();
                App.getDaoSession().getResultBean3Dao().deleteInTx(bean3s);
                List<TemplatePicBean> picBeans = App.getDaoSession().getTemplatePicBeanDao().queryBuilder().where(TemplatePicBeanDao.Properties.TemplateResultID.eq(templateResultBeans.get(i).getId())).list();
                App.getDaoSession().getTemplatePicBeanDao().deleteInTx(picBeans);
            }
            // publishProgress(sum, uploadSum);
            App.getDaoSession().getTemplateResultBeanDao().deleteInTx(templateResultBeans);
            return "后台任务执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
            dialog.setMessage("共有" + progresses[0] + "条数据待上传，已经上传" + progresses[1] + "条数据.");
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
        }

        @Override
        protected void onCancelled() {

        }
    }
}
