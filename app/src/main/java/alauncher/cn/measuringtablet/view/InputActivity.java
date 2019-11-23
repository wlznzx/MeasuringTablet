package alauncher.cn.measuringtablet.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOLandscapeActivity;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.utils.Constants;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.utils.SPUtils;
import alauncher.cn.measuringtablet.view.adapter.EnterAdapter;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class InputActivity extends BaseOLandscapeActivity {

    @BindView(R.id.rv)
    public RecyclerView rv;


    @BindView(R.id.workpiece_num_sp)
    public Spinner workpieceSP;

    public EnterAdapter mEnterAdapter;

    private List<Parameter2Bean> mParameter2Beans;

    public static List<InputBean> datas = new ArrayList<>();

    public static List<UpdateBean> updates = new ArrayList<>();

    private int currentPosPic;
    private int workpieceNum;

    long _stableTime = 800;

    int startHour, stopHour, startMin, stopMin;

    @BindViews({R.id.judge_all, R.id.judge_1, R.id.judge_2, R.id.judge_3, R.id.judge_4, R.id.judge_5})
    public TextView[] judgeTVs;

    private String[] judges = {"- -", "- -", "- -", "- -", "- -", "- -"};


    private DeviceInfoBean mDeviceInfoBean;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    doUpdate(currentPos, currentIndex, updateValue);
                    break;
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // mHandler.sendEmptyMessage(EDIT_OK);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        _stableTime = (long) SPUtils.get(InputActivity.this, Constants.INPUT_TIME_KEY, Long.valueOf(800));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_input);
    }

    private int currentPos = -1;
    private int currentIndex = -1;
    private String updateValue;

    @Override
    protected void initView() {
        mParameter2Beans = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        mDeviceInfoBean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);
        for (TextView _v : judgeTVs) {
            _v.setText("");
        }
        // datas.clear();
        if (datas.size() <= 0) {
            for (Parameter2Bean _bean : mParameter2Beans) {
                if (_bean.getEnable()) {
                    InputBean _inputBean = new InputBean();
                    _inputBean.describeStr = _bean.getDescribe();
                    _inputBean.measuringStr = "M" + _bean.getIndex();
                    _inputBean.isEnable = _bean.getEnable();
                    _inputBean.upperValue = _bean.getNominal_value() + _bean.getUpper_tolerance_value();
                    _inputBean.lowerValue = _bean.getNominal_value() + _bean.getLower_tolerance_value();
                    if (_bean.getDescribe().startsWith("外观检查")) {
                        _inputBean.isSp = true;
                    } else {
                        _inputBean.isSp = false;
                    }
                    datas.add(_inputBean);
                }
            }
        }

        mEnterAdapter = new EnterAdapter(InputActivity.this, datas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(InputActivity.this);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mEnterAdapter);

        mEnterAdapter.setOnItemClickListener(new EnterAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int pos, List<InputActivity.InputBean> myLiveList, int workpiece_num) {
                dispatchTakePictureIntent();
                currentPosPic = pos;
                workpieceNum = workpiece_num;
            }

            @Override
            public void onItemLongClickListener(int pos, List<InputActivity.InputBean> myLiveList) {

            }

            @Override
            public void onTextWatch(int pos, int index, String s) {
                android.util.Log.d("wlDebug", "pos = " + pos + " index = " + index + " s = " + s);
                currentPos = pos;
                currentIndex = index;
                updateValue = s;
                switch (index) {
                    case 1:
                        datas.get(pos).workspace1Value = s;
                        break;
                    case 2:
                        datas.get(pos).workspace2Value = s;
                        break;
                    case 3:
                        datas.get(pos).workspace3Value = s;
                        break;
                    case 4:
                        datas.get(pos).workspace4Value = s;
                        break;
                    case 5:
                        datas.get(pos).workspace5Value = s;
                        break;
                }

                updates.add(new UpdateBean(pos, index, s));
                /*
                mHandler.removeMessages(1);
                mHandler.sendEmptyMessageDelayed(1, _stableTime);
                */
            }
        });
        workpieceSP.setSelection(4);
    }

    private void doUpdate(int pos, int index, String s) {

        if (pos == -1) {
            return;
        }

        switch (index) {
            case 1:
                datas.get(pos).workspace1Value = s;
                break;
            case 2:
                datas.get(pos).workspace2Value = s;
                break;
            case 3:
                datas.get(pos).workspace3Value = s;
                break;
            case 4:
                datas.get(pos).workspace4Value = s;
                break;
            case 5:
                datas.get(pos).workspace5Value = s;
                break;
        }


        boolean isModify = false;
        // 设定最大值;

        List<Double> _tempList = new ArrayList<>();
        if (datas.get(pos).workspace1Value != null) {
            try {
                _tempList.add(Double.valueOf(datas.get(pos).workspace1Value));
            } catch (NumberFormatException e) {

            }
        }
        if (datas.get(pos).workspace2Value != null) {
            try {
                _tempList.add(Double.valueOf(datas.get(pos).workspace2Value));
            } catch (NumberFormatException e) {

            }
        }
        if (datas.get(pos).workspace3Value != null) {
            try {
                _tempList.add(Double.valueOf(datas.get(pos).workspace3Value));
            } catch (NumberFormatException e) {

            }
        }
        if (datas.get(pos).workspace4Value != null) {
            try {
                _tempList.add(Double.valueOf(datas.get(pos).workspace4Value));
            } catch (NumberFormatException e) {

            }
        }
        if (datas.get(pos).workspace5Value != null) {
            try {
                _tempList.add(Double.valueOf(datas.get(pos).workspace5Value));
            } catch (NumberFormatException e) {

            }
        }
        Collections.sort(_tempList);
        if (_tempList.size() > 0) {
            android.util.Log.d("wlDebug", "max = " + _tempList.get(_tempList.size() - 1));
            android.util.Log.d("wlDebug", "min = " + _tempList.get(0));
            datas.get(pos).maxValue = _tempList.get(_tempList.size() - 1).toString().trim();
            datas.get(pos).minValue = _tempList.get(0).toString().trim();
            isModify = true;
        }


        /*
        if (datas.get(pos).maxValue == null) {
            datas.get(pos).maxValue = s;
            isModify = true;
        } else {
            try {
                double _max = Double.valueOf(datas.get(pos).maxValue);
                double _curValue = Double.valueOf(s);
                if (_curValue > _max) {
                    datas.get(pos).maxValue = s;
                    isModify = true;
                }
            } catch (NumberFormatException e) {

            }
        }
        if (datas.get(pos).minValue == null) {
            datas.get(pos).minValue = s;
            isModify = true;
        } else {
            try {
                double _min = Double.valueOf(datas.get(pos).minValue);
                double _curValue = Double.valueOf(s);
                if (_curValue < _min) {
                    datas.get(pos).minValue = s;
                    isModify = true;
                }
            } catch (NumberFormatException e) {

            }
        }
        */

        String judge = "NG";
        try {
            double _curValue = Double.valueOf(s);
            if (_curValue > datas.get(pos).upperValue || _curValue < datas.get(pos).lowerValue) {
                judge = "NG";
            } else {
                judge = "OK";
            }
            datas.get(pos).workspaceJudges[index] = judge;

            String _tempJudge = "- -";
            for (int i = 1; i < datas.get(pos).workspaceJudges.length; i++) {
                String _s = datas.get(pos).workspaceJudges[i];
                if (_s.equals("NG")) {
                    _tempJudge = "NG";
                    break;
                } else if (_s.equals("OK")) {
                    _tempJudge = "OK";
                }
            }
            if (!_tempJudge.equals(datas.get(pos).judge)) {
                datas.get(pos).judge = _tempJudge;
                isModify = true;
            }

            for (InputBean _bean : datas) {
                if (datas.get(pos).workspaceJudges[index].equals("NG")) {
                    judges[index] = "NG";
                    break;
                } else if (datas.get(pos).workspaceJudges[index].equals("OK")) {
                    judges[index] = "OK";
                }
            }
            judgeTVs[index].setText(judges[index]);
            judgeTVs[index].setTextColor(judges[index].equals("OK") ? Color.GREEN : Color.RED);
        } catch (NumberFormatException e) {

        }

        for (int i = 1; i < judges.length; i++) {
            if (judges[i].equals("NG")) {
                judges[0] = "NG";
                break;
            } else if (judges[i].equals("OK")) {
                judges[0] = "OK";
            }
        }
        judgeTVs[0].setText("综合判定 " + judges[0]);
        judgeTVs[0].setTextColor(judges[0].equals("OK") ? Color.GREEN : Color.RED);


        if (isModify) mEnterAdapter.notifyDataSetChanged();

        // InputMethodManager m = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
        // m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    static final int REQUEST_TAKE_PHOTO = 2;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "alauncher.cn.measuringtablet.fileProvider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        System.out.println(currentPhotoPath);
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    switch (workpieceNum) {
                        case 1:
                            datas.get(currentPosPic).workspace1PicPath = currentPhotoPath;
                            break;
                        case 2:
                            datas.get(currentPosPic).workspace2PicPath = currentPhotoPath;
                            break;
                        case 3:
                            datas.get(currentPosPic).workspace3PicPath = currentPhotoPath;
                            break;
                        case 4:
                            datas.get(currentPosPic).workspace4PicPath = currentPhotoPath;
                            break;
                        case 5:
                            datas.get(currentPosPic).workspace5PicPath = currentPhotoPath;
                            break;
                    }
                    mEnterAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_refresh)
    public void onRefresh() {
        // doUpdate(currentPos, currentIndex, updateValue);
        for (UpdateBean _bean : updates) {
            doUpdate(_bean.pos, _bean.index, _bean.value);
        }
        updates.clear();
    }

    @OnClick(R.id.btn_clear)
    public void onClear() {
        updates.clear();
        datas.clear();
        initView();
    }

    @OnClick({R.id.swap_btn})
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.swap_btn:
                /*
                if (curMode < 4) curMode++;
                else curMode = 0;
                setMode(curMode);
                */
                // new ChooseCodeDialog(this).show();
                for (int i = 0; i < App.getDaoSession().getCodeBeanDao().loadAll().size(); i++) {
                    CodeBean _bean = App.getDaoSession().getCodeBeanDao().load((long) (i + 1));
                    if (_bean != null) {
                        province[i] = _bean.getName();
                    } else {
                        province[i] = "程序 " + (i + 1);
                    }
                }
                // showSingleChoiceButton();
                showGridDialog();
                break;
        }
    }

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        if (getIsEmpty()) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(InputActivity.this);
            normalDialog.setIcon(R.drawable.add_circle);
            normalDialog.setTitle("确认保存");
            normalDialog.setMessage("还有测量值未输入，是否保存？");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                            doSave();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            // 显示
            normalDialog.show();
        } else {
            doSave();
        }
    }


    public void doSave() {
        List<ResultBean3> updateBeans = new ArrayList<>();

        int saveNum = workpieceSP.getSelectedItemPosition() + 1;
        // 1
        ResultBean3 _workpiece1Bean = new ResultBean3();
        _workpiece1Bean.setMValues(new ArrayList<String>());
        _workpiece1Bean.setMPicPaths(new ArrayList<String>());
        _workpiece1Bean.setHandlerAccout(App.handlerName);
        _workpiece1Bean.setCodeID(App.getSetupBean().getCodeID());
        _workpiece1Bean.setResult(judges[1]);
        _workpiece1Bean.setTimeStamp(System.currentTimeMillis());
        for (int i = 0, j = 0; i < mParameter2Beans.size(); i++) {
            if (mParameter2Beans.get(i).getEnable()) {
                _workpiece1Bean.getMValues().add(datas.get(j).workspace1Value);
                _workpiece1Bean.getMPicPaths().add(datas.get(j).workspace1PicPath);
                j++;
            } else {
                _workpiece1Bean.getMValues().add("- -");
                _workpiece1Bean.getMPicPaths().add("");
            }
        }

        for (int i = 0; i < _workpiece1Bean.getMValues().size(); i++) {
            android.util.Log.d("wlDebug", "index = " + i + " value = " + _workpiece1Bean.getMValues().get(i));
        }
        App.getDaoSession().getResultBean3Dao().insert(_workpiece1Bean);
        updateBeans.add(_workpiece1Bean);

        // 2
        if (saveNum > 1) {
            ResultBean3 _workpiece2Bean = new ResultBean3();
            _workpiece2Bean.setMValues(new ArrayList<String>());
            _workpiece2Bean.setMPicPaths(new ArrayList<String>());
            _workpiece2Bean.setHandlerAccout(App.handlerName);
            _workpiece2Bean.setCodeID(App.getSetupBean().getCodeID());
            _workpiece2Bean.setTimeStamp(System.currentTimeMillis());
            _workpiece2Bean.setResult(judges[2]);
            for (int i = 0, j = 0; i < mParameter2Beans.size(); i++) {
                if (mParameter2Beans.get(i).getEnable()) {
                    _workpiece2Bean.getMValues().add(datas.get(j).workspace2Value);
                    _workpiece2Bean.getMPicPaths().add(datas.get(j).workspace2PicPath);
                    j++;
                } else {
                    _workpiece2Bean.getMValues().add("- -");
                    _workpiece2Bean.getMPicPaths().add("");
                }
            }

            for (int i = 0; i < _workpiece2Bean.getMValues().size(); i++) {
                android.util.Log.d("wlDebug", "index = " + i + " value = " + _workpiece2Bean.getMValues().get(i));
            }
            App.getDaoSession().getResultBean3Dao().insert(_workpiece2Bean);
            updateBeans.add(_workpiece2Bean);
        }


        // 3
        if (saveNum > 2) {
            ResultBean3 _workpiece3Bean = new ResultBean3();
            _workpiece3Bean.setMValues(new ArrayList<String>());
            _workpiece3Bean.setMPicPaths(new ArrayList<String>());
            _workpiece3Bean.setHandlerAccout(App.handlerName);
            _workpiece3Bean.setCodeID(App.getSetupBean().getCodeID());
            _workpiece3Bean.setResult(judges[3]);
            _workpiece3Bean.setTimeStamp(System.currentTimeMillis());
            for (int i = 0, j = 0; i < mParameter2Beans.size(); i++) {
                if (mParameter2Beans.get(i).getEnable()) {
                    _workpiece3Bean.getMValues().add(datas.get(j).workspace3Value);
                    _workpiece3Bean.getMPicPaths().add(datas.get(j).workspace3PicPath);
                    j++;
                } else {
                    _workpiece3Bean.getMValues().add("- -");
                    _workpiece3Bean.getMPicPaths().add("");
                }
            }

            for (int i = 0; i < _workpiece3Bean.getMValues().size(); i++) {
                android.util.Log.d("wlDebug", "index = " + i + " value = " + _workpiece3Bean.getMValues().get(i));
            }
            App.getDaoSession().getResultBean3Dao().insert(_workpiece3Bean);
            updateBeans.add(_workpiece3Bean);
        }

        // 4
        if (saveNum > 3) {
            ResultBean3 _workpiece4Bean = new ResultBean3();
            _workpiece4Bean.setMValues(new ArrayList<String>());
            _workpiece4Bean.setMPicPaths(new ArrayList<String>());
            _workpiece4Bean.setHandlerAccout(App.handlerName);
            _workpiece4Bean.setCodeID(App.getSetupBean().getCodeID());
            _workpiece4Bean.setResult(judges[4]);
            _workpiece4Bean.setTimeStamp(System.currentTimeMillis());
            for (int i = 0, j = 0; i < mParameter2Beans.size(); i++) {
                if (mParameter2Beans.get(i).getEnable()) {
                    _workpiece4Bean.getMValues().add(datas.get(j).workspace4Value);
                    _workpiece4Bean.getMPicPaths().add(datas.get(j).workspace4PicPath);
                    j++;
                } else {
                    _workpiece4Bean.getMValues().add("- -");
                    _workpiece4Bean.getMPicPaths().add("");
                }
            }

            for (int i = 0; i < _workpiece4Bean.getMValues().size(); i++) {
                android.util.Log.d("wlDebug", "index = " + i + " value = " + _workpiece4Bean.getMValues().get(i));
            }
            App.getDaoSession().getResultBean3Dao().insert(_workpiece4Bean);
            updateBeans.add(_workpiece4Bean);
        }


        // 5
        if (saveNum > 4) {
            ResultBean3 _workpiece5Bean = new ResultBean3();
            _workpiece5Bean.setMValues(new ArrayList<String>());
            _workpiece5Bean.setMPicPaths(new ArrayList<String>());
            _workpiece5Bean.setHandlerAccout(App.handlerName);
            _workpiece5Bean.setCodeID(App.getSetupBean().getCodeID());
            _workpiece5Bean.setTimeStamp(System.currentTimeMillis());
            _workpiece5Bean.setResult(judges[5]);
            for (int i = 0, j = 0; i < mParameter2Beans.size(); i++) {
                if (mParameter2Beans.get(i).getEnable()) {
                    _workpiece5Bean.getMValues().add(datas.get(j).workspace5Value);
                    _workpiece5Bean.getMPicPaths().add(datas.get(j).workspace5PicPath);
                    j++;
                } else {
                    _workpiece5Bean.getMValues().add("- -");
                    _workpiece5Bean.getMPicPaths().add("");
                }
            }

            for (int i = 0; i < _workpiece5Bean.getMValues().size(); i++) {
                android.util.Log.d("wlDebug", "index = " + i + " value = " + _workpiece5Bean.getMValues().get(i));
            }
            App.getDaoSession().getResultBean3Dao().insert(_workpiece5Bean);
            updateBeans.add(_workpiece5Bean);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
//                JdbcUtil.insertOrReplace(mDeviceInfoBean.getFactoryCode(), mDeviceInfoBean.getFactoryName(), mDeviceInfoBean.getDeviceCode(), mDeviceInfoBean.getDeviceName(), mDeviceInfoBean.getManufacturer(),
//                        "rmk3", App.handlerName);
                for (ResultBean3 _b3 : updateBeans) {
                    try {
                        JdbcUtil.addResult3(mDeviceInfoBean.getFactoryCode(), mDeviceInfoBean.getDeviceCode(), App.getSetupBean().getCodeID(), "", _b3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Toast.makeText(this, "保存了" + saveNum + "件工件", Toast.LENGTH_SHORT).show();
    }

    private boolean getIsEmpty() {
        int saveNum = workpieceSP.getSelectedItemPosition() + 1;
        for (InputBean _bean : datas) {
            if (_bean.workspace1Value == null || _bean.workspace1Value.equals("")) return true;
            if (saveNum > 1 && (_bean.workspace2Value == null || _bean.workspace2Value.equals("")))
                return true;
            if (saveNum > 2 && (_bean.workspace3Value == null || _bean.workspace3Value.equals("")))
                return true;
            if (saveNum > 3 && (_bean.workspace4Value == null || _bean.workspace4Value.equals("")))
                return true;
            if (saveNum > 4 && (_bean.workspace5Value == null || _bean.workspace5Value.equals("")))
                return true;
        }
        return false;
    }

    /**
     * 将数据ArrayList中
     *
     * @return
     */
    private String[] province = new String[45];

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < province.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("itemName", province[i]);
            items.add(item);
        }
        return items;
    }

    private void showGridDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.gridview_dialog, null);
        // 设置style 控制默认dialog带来的边距问题
        final Dialog dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        final List<Map<String, Object>> item = getData();
        // SimpleAdapter对象，匹配ArrayList中的元素
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, item, R.layout.gridview_item, new String[]{"itemName"}, new int[]{R.id.grid_name});
        gridview.setAdapter(simpleAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                SetupBean _bean = App.getDaoSession().getSetupBeanDao().load(App.SETTING_ID);
                _bean.setCodeID(arg2 + 1);
                App.getDaoSession().getSetupBeanDao().update(_bean);
                //((MeasuringPresenterImpl) mMeasuringPresenter).initParameter();
                // initParameters();
                CodeBean _CodeBean = App.getDaoSession().getCodeBeanDao().load((long) (arg2 + 1));
                if (_CodeBean != null) {
                    actionTips.setText(App.handlerName + " " + _CodeBean.getName());
                } else {
                    actionTips.setText(App.handlerName + " 程序" + App.getSetupBean().getCodeID());
                }
                dialog.dismiss();
                updates.clear();
                datas.clear();
                initView();
            }
        });
    }

    class UpdateBean {
        public int pos;
        public int index;
        public String value;

        public UpdateBean(int pos, int index, String value) {
            this.pos = pos;
            this.index = index;
            this.value = value;
        }
    }


    public class InputBean {
        public boolean isEnable;
        public boolean isSp;
        public String measuringStr;
        public String describeStr;
        public String maxValue;
        public String minValue;
        public String judge = "- -";
        public String workspace1Value;
        public String workspace1PicPath;
        public String workspace2Value;
        public String workspace2PicPath;
        public String workspace3Value;
        public String workspace3PicPath;
        public String workspace4Value;
        public String workspace4PicPath;
        public String workspace5Value;
        public String workspace5PicPath;

        public double upperValue;
        public double lowerValue;

        public String[] workspaceJudges = {"无意义", "- -", "- -", "- -", "- -", "- -"};

        @Override
        public String toString() {
            return "InputBean{" +
                    "isEnable=" + isEnable +
                    ", measuringStr='" + measuringStr + '\'' +
                    ", describeStr='" + describeStr + '\'' +
                    ", maxValue='" + maxValue + '\'' +
                    ", minValue='" + minValue + '\'' +
                    ", judge='" + judge + '\'' +
                    ", workspace1Value='" + workspace1Value + '\'' +
                    ", workspace1PicPath='" + workspace1PicPath + '\'' +
                    ", workspace2Value='" + workspace2Value + '\'' +
                    ", workspace2PicPath='" + workspace2PicPath + '\'' +
                    ", workspace3Value='" + workspace3Value + '\'' +
                    ", workspace3PicPath='" + workspace3PicPath + '\'' +
                    ", workspace4Value='" + workspace4Value + '\'' +
                    ", workspace4PicPath='" + workspace4PicPath + '\'' +
                    ", workspace5Value='" + workspace5Value + '\'' +
                    ", workspace5PicPath='" + workspace5PicPath + '\'' +
                    ", upperValue=" + upperValue +
                    ", lowerValue=" + lowerValue +
                    ", workspaceJudges=" + Arrays.toString(workspaceJudges) +
                    '}';
        }
    }

}
