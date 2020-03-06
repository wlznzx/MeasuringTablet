package alauncher.cn.measuringtablet.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.FilterBean;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean3Dao;
import alauncher.cn.measuringtablet.utils.CommonUtil;
import alauncher.cn.measuringtablet.utils.DateUtils;
import alauncher.cn.measuringtablet.utils.ExcelUtil;
import alauncher.cn.measuringtablet.view.adapter.DataAdapter2;
import alauncher.cn.measuringtablet.widget.FilterDialog;
import butterknife.BindView;
import butterknife.BindViews;

import static alauncher.cn.measuringtablet.view.adapter.DataAdapter.MYLIVE_MODE_CHECK;
import static alauncher.cn.measuringtablet.view.adapter.DataAdapter.MYLIVE_MODE_EDIT;

public class Data2Activity extends BaseOActivity implements View.OnClickListener, DataAdapter2.OnItemClickListener, FilterDialog.FilterInterface {

    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @BindView(R.id.btn_quit)
    TextView quitBtn;
    @BindView(R.id.btn_excel)
    TextView excelBtn;
    @BindView(R.id.btn_filter)
    TextView filterBtn;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;

    public DataAdapter2 mDataAdapter;

    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;

    private ParameterBean mParameterBean;

    private DeviceInfoBean mDeviceInfoBean;

    private String[] title = {"操作员", "时间", "工件号", "事件", "结果", "M1", "M1分组", "M2", "M2分组", "M3", "M3分组", "M4", "M4分组"};

    private LinearLayout titleLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_data2);
    }

    @Override
    protected void initView() {
//        List<ResultData> _datas = new ArrayList();
//        _datas.add(new ResultData(1, "操作员", System.currentTimeMillis(), 123456, "换刀", 1, 0.7023, 0.7023, 0.7023, 0.7023));

        mDeviceInfoBean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);

        mParameterBean = App.getDaoSession().getParameterBeanDao().load((long) App.getSetupBean().getCodeID());

        List<Parameter2Bean> _datas = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        mDataAdapter = new DataAdapter2(Data2Activity.this, App.getDaoSession().getResultBean3Dao().queryBuilder().where(ResultBean3Dao.Properties.CodeID.eq(App.getSetupBean().getCodeID())).orderDesc(ResultBean3Dao.Properties.Id).list(),
                _datas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Data2Activity.this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mDataAdapter);

//        List<ResultBean3> _results = ;
//
//        for(ResultBean3 _bean : _results){
//            android.util.Log.d("wlDebug","" + _bean.getMValues().get(0));
//            android.util.Log.d("wlDebug","pic = " + _bean.getMPicPaths().get(0));
//        }

        mDataAdapter.setOnItemClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        excelBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(this);

        titleLinearLayout = findViewById(R.id.data_title_layout);
        final float scale = getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (70 * scale + 0.5f), ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(10, 15, 10, 15);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        for (int i = 0; i < _datas.size(); i++) {
            TextView titleView = new TextView(this);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextAppearance(this, R.style.TextStyle);
            titleView.setText("M" + (i + 1));
            titleView.setLayoutParams(layoutParams);
            TextView picView = new TextView(this);
            picView.setGravity(Gravity.CENTER);
            picView.setTextAppearance(this, R.style.TextStyle);
            picView.setText("M" + (i + 1) + "图片");
            titleLinearLayout.addView(titleView);
            titleLinearLayout.addView(picView, layoutParams);
        }
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mDataAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mDataAdapter.getMyLiveList().size(); i < j; i++) {
                mDataAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = mDataAdapter.getMyLiveList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mDataAdapter.getMyLiveList().size(); i < j; i++) {
                mDataAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mDataAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = mDataAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
//                    ResultBean _baen = mDataAdapter.getMyLiveList().get(i - 1);
//                    if (_baen.isSelect()) {
//                        mDataAdapter.getMyLiveList().remove(_baen);
//                        index--;
//                    }
//                }

//                index = 0;
//                mTvSelectNum.setText(String.valueOf(0));
//                setBtnBackground(index);
//                if (mDataAdapter.getMyLiveList().size() == 0) {
//                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
//                }
//                mDataAdapter.notifyDataSetChanged();
                new DeleteTask().execute();
                builder.dismiss();
            }
        });
    }

    /**
     * 删除逻辑
     */
    private void excelDatas() {
        if (index == 0) {
            excelBtn.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("是否导出这个条目");
        } else {
            msg.setText("是否导出这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExcelTask().execute();
                builder.dismiss();
            }
        });
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            // mBtnEditor.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            filterBtn.setVisibility(View.GONE);
            editorStatus = true;
        } else {
            // mBtnEditor.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            filterBtn.setVisibility(View.VISIBLE);
            editorStatus = false;
            clearAll();
        }
        mDataAdapter.setEditMode(mEditMode);
    }


    private void clearAll() {
        // 清除选中状态
        for (int i = mDataAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
            ResultBean3 _baen = mDataAdapter.getMyLiveList().get(i - 1);
            if (_baen.isSelect()) {
                _baen.setSelect(false);
            }
        }
        index = 0;
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }

    @Override
    public void onItemClickListener(int pos, List<ResultBean3> myLiveList) {
        android.util.Log.d("wlDebug", "pos = " + pos);
        if (editorStatus) {
            ResultBean3 _bean = myLiveList.get(pos);
            boolean isSelect = _bean.isSelect();
            if (!isSelect) {
                index++;
                _bean.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    mSelectAll.setText("取消全选");
                }
            } else {
                _bean.setSelect(false);
                index--;
                isSelectAll = false;
                mSelectAll.setText("全选");
            }
            setBtnBackground(index);
            mTvSelectNum.setText(String.valueOf(index));
            mDataAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemLongClickListener(int pos, List<ResultBean3> myLiveList) {
        updataEditMode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.btn_quit:
                updataEditMode();
                break;
            case R.id.btn_excel:
                excelDatas();
                break;
            case R.id.btn_filter:
                FilterDialog mFilterDialog = new FilterDialog(this, this);
                mFilterDialog.show();
                break;
            default:
                break;
        }
    }


    public class DeleteTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Data2Activity.this);
            dialog.setTitle("删除");
            dialog.setMessage("正在删除数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作
            for (int i = mDataAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                ResultBean3 _baen = mDataAdapter.getMyLiveList().get(i - 1);
                if (_baen.isSelect()) {
                    mDataAdapter.getMyLiveList().remove(_baen);
                    App.getDaoSession().getResultBean3Dao().delete(_baen);
                    index--;
                }
            }
            return "后台任务执行完毕";
        }

        /*这个函数在doInBackground调用publishProgress(int i)时触发，虽然调用时只有一个参数
         但是这里取到的是一个数组,所以要用progesss[0]来取值
         第n个参数就用progress[n]来取值   */
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
        }

        /*doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
        这里的result就是上面doInBackground执行后的返回值，所以这里是"后台任务执行完毕"  */
        @Override
        protected void onPostExecute(String result) {
            index = 0;
            mTvSelectNum.setText(String.valueOf(0));
            setBtnBackground(index);
            if (mDataAdapter.getMyLiveList().size() == 0) {
                mLlMycollectionBottomDialog.setVisibility(View.GONE);
            }
            mDataAdapter.notifyDataSetChanged();
            dialog.dismiss();
            updataEditMode();
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    /*
     *
     * 导出Excel Task.
     *
     * */
    public class ExcelTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;
        private String path = Environment.getExternalStorageDirectory() + "/ETGate/";

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Data2Activity.this);
            dialog.setTitle("导出Excel");
            dialog.setMessage("正在将数据导出Excel中 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作

            File destDir = new File(path);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            List<ResultBean3> selectedList = new ArrayList<>();
            for (int i = mDataAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                ResultBean3 _baen = mDataAdapter.getMyLiveList().get(i - 1);
                if (_baen.isSelect()) {
                    selectedList.add(_baen);
                    index--;
                }
            }

            path = path + "datas_" + DateUtils.getFileDate(System.currentTimeMillis()) + ".xls";
            ExcelUtil.initExcel(path, "data", title);
            ExcelUtil.writeObjListToExcel(selectedList, path, Data2Activity.this);
            return "后台任务执行完毕";
        }

        /*这个函数在doInBackground调用publishProgress(int i)时触发，虽然调用时只有一个参数
         但是这里取到的是一个数组,所以要用progesss[0]来取值
         第n个参数就用progress[n]来取值   */
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
        }

        /*doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
        这里的result就是上面doInBackground执行后的返回值，所以这里是"后台任务执行完毕"  */
        @Override
        protected void onPostExecute(String result) {
            index = 0;
            mTvSelectNum.setText(String.valueOf(0));
            setBtnBackground(index);
            if (mDataAdapter.getMyLiveList().size() == 0) {
                mLlMycollectionBottomDialog.setVisibility(View.GONE);
            }
            mDataAdapter.notifyDataSetChanged();
            dialog.dismiss();
            updataEditMode();
            Toast.makeText(Data2Activity.this, "导出至 : " + path, Toast.LENGTH_LONG).show();
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    @Override
    public void dataFilterUpdate(FilterBean bean) {
        /*
        Query query = mResultBean3Dao.queryBuilder().where(
                new WhereCondition.StringCondition(
                        "SELECT * FROM RESULT_BEAN WHERE HANDLER_ACCOUT = '吴工'")).build();
        List<ResultBean> _datas = query.list();
        mDataAdapter.notifyAdapter(_datas, false);
        */
        //请求参数
        ArrayList<String> strParamLt = new ArrayList<String>();

        String queryString = "";
        if (bean.getStartTime() == 0 && bean.getEndTime() == 0) {
            queryString = "SELECT * FROM " + ResultBean3Dao.TABLENAME + " where 1==1 ";
        } else {
            queryString = "SELECT * FROM " + ResultBean3Dao.TABLENAME + " where " + ResultBean3Dao.Properties.TimeStamp.columnName + " between " + bean.getStartTime() + " and " + bean.getEndTime();
        }

        //用户
        if (!CommonUtil.isNull(bean.getHandler())) {
            queryString = queryString + " and "
                    + ResultBean3Dao.Properties.HandlerAccout.columnName + " =  ?";
            strParamLt.add(bean.getHandler());
        }

        // Event
        if (!CommonUtil.isNull(bean.getEvent())) {
            queryString = queryString + " and "
                    + ResultBean3Dao.Properties.Event.columnName + " =  ?";
            strParamLt.add(bean.getEvent());
        }

        // 工件号
        if (!CommonUtil.isNull(bean.getWorkid())) {
            queryString = queryString + " and "
                    + ResultBean3Dao.Properties.Workid.columnName + " =  ?";
            strParamLt.add(bean.getWorkid());
        }

        //
        if (!CommonUtil.isNull(bean.getResult())) {
            queryString = queryString + " and "
                    + ResultBean3Dao.Properties.Result.columnName + " =  ?";
            strParamLt.add(bean.getResult());
        }

        Object[] objs = strParamLt.toArray();
        String[] strs = new String[objs.length];

        for (int i = 0; i < objs.length; i++) {
            strs[i] = objs[i].toString();
        }

//        Cursor cursor = mResultBean3Dao.getDatabase().rawQuery("SELECT * FROM RESULT_BEAN WHERE HANDLER_ACCOUT = '工'", null);
        Cursor cursor = App.getDaoSession().getResultBean3Dao().getDatabase().rawQuery(queryString, strs);


        int HandlerAccout = cursor.getColumnIndex(ResultBean3Dao.Properties.HandlerAccout.columnName);
        int TimeStamp = cursor.getColumnIndex(ResultBean3Dao.Properties.TimeStamp.columnName);
        int Workid = cursor.getColumnIndex(ResultBean3Dao.Properties.Workid.columnName);
        int Event = cursor.getColumnIndex(ResultBean3Dao.Properties.Event.columnName);
        int Result = cursor.getColumnIndex(ResultBean3Dao.Properties.Result.columnName);
        int MValues = cursor.getColumnIndex(ResultBean3Dao.Properties.MValues.columnName);
        int MPicPaths = cursor.getColumnIndex(ResultBean3Dao.Properties.MPicPaths.columnName);

        List<ResultBean3> _datas = new ArrayList<>();

        while (cursor.moveToNext()) {
            ResultBean3 rBean = new ResultBean3();
            rBean.setHandlerAccout(cursor.getString(HandlerAccout));
            rBean.setWorkid(cursor.getString(Workid));
            rBean.setTimeStamp(cursor.getLong(TimeStamp));
            rBean.setEvent(cursor.getString(Event));
            rBean.setResult(cursor.getString(Result));
            rBean.setMValues(convertToEntityProperty(cursor.getString(MValues)));
            rBean.setMPicPaths(convertToEntityProperty(cursor.getString(MPicPaths)));

            Date date = new Date(rBean.getTimeStamp());

            if (bean.getClassType() == 0) {
                _datas.add(rBean);
            } else if (bean.getClassType() == 1) {
                if (getIsMorningClass(date.getHours(), date.getSeconds())) _datas.add(rBean);
            } else if (bean.getClassType() == 2) {
                if (!getIsMorningClass(date.getHours(), date.getSeconds())) _datas.add(rBean);
            }
        }
        mDataAdapter.notifyAdapter(_datas, false);
    }

    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        } else {
            List<String> list = Arrays.asList(databaseValue.split(","));
            return list;
        }
    }

    public boolean getIsMorningClass(int hour, int min) {
        if ((hour > mDeviceInfoBean.getStartHour() || (hour == mDeviceInfoBean.getStartHour() && min > mDeviceInfoBean.getStartMin()))
                &&
                hour < mDeviceInfoBean.getStopHour() || (hour == mDeviceInfoBean.getStopHour() && min < mDeviceInfoBean.getStopMin())) {
            return true;
        }
        return false;
    }
}
