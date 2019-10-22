package alauncher.cn.measuringtablet.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOLandscapeActivity;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.view.adapter.EnterAdapter;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class InputActivity extends BaseOLandscapeActivity {

    @BindView(R.id.rv)
    public RecyclerView rv;

    public EnterAdapter mEnterAdapter;

    private List<Parameter2Bean> mParameter2Beans;

    private List<InputBean> datas = new ArrayList<>();

    private int currentPosPic;
    private int workpieceNum;

    @BindViews({R.id.judge_all, R.id.judge_1, R.id.judge_2, R.id.judge_3, R.id.judge_4, R.id.judge_5})
    public TextView[] judgeTVs;

    private String[] judges = {"- -", "- -", "- -", "- -", "- -", "- -"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_input);

    }

    @Override
    protected void initView() {
        mParameter2Beans = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        for (Parameter2Bean _bean : mParameter2Beans) {
            if (_bean.getEnable()) {
                InputBean _inputBean = new InputBean();
                _inputBean.describeStr = _bean.getDescribe();
                _inputBean.measuringStr = "M" + _bean.getIndex();
                _inputBean.isEnable = _bean.getEnable();
                _inputBean.upperValue = _bean.getNominal_value() + _bean.getUpper_tolerance_value();
                _inputBean.lowerValue = _bean.getNominal_value() + _bean.getLower_tolerance_value();
                datas.add(_inputBean);
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

                String judge = "NG";
                try {
                    double _curValue = Double.valueOf(s);
                    if (_curValue > datas.get(pos).upperValue || _curValue < datas.get(pos).lowerValue) {
                        judge = "NG";
                    } else {
                        judge = "OK";
                    }
//                    if (datas.get(pos).judge == null) {
//                        datas.get(pos).judge = judge;
//                        isModify = true;
//                    } else {
//                        if (!datas.get(pos).judge.equals(judge)) {
//                            datas.get(pos).judge = judge;
//                            isModify = true;
//                        }
//                    }
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
                        if (_bean.judge.equals("NG")) {
                            judges[index] = "NG";
                            break;
                        } else if (_bean.judge.equals("OK")) {
                            judges[index] = "OK";
                        }
                    }
                    judgeTVs[index].setText(judges[index]);
                    judgeTVs[index].setTextColor(judges[index].equals("OK") ? Color.GREEN : Color.RED);
                } catch (NumberFormatException e) {

                }

                if (isModify) mEnterAdapter.notifyDataSetChanged();
            }
        });
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

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        // 1
        ResultBean3 _workpiece1Bean = new ResultBean3();
        _workpiece1Bean.setMValues(new ArrayList<String>());
        _workpiece1Bean.setMPicPaths(new ArrayList<String>());
        _workpiece1Bean.setHandlerAccout(App.handlerAccout);
        _workpiece1Bean.setCodeID(App.getSetupBean().getCodeID());
        _workpiece1Bean.setResult(judges[1]);
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

        // 2
        ResultBean3 _workpiece2Bean = new ResultBean3();
        _workpiece2Bean.setMValues(new ArrayList<String>());
        _workpiece2Bean.setMPicPaths(new ArrayList<String>());
        _workpiece2Bean.setHandlerAccout(App.handlerAccout);
        _workpiece2Bean.setCodeID(App.getSetupBean().getCodeID());
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

        // 3
        ResultBean3 _workpiece3Bean = new ResultBean3();
        _workpiece3Bean.setMValues(new ArrayList<String>());
        _workpiece3Bean.setMPicPaths(new ArrayList<String>());
        _workpiece3Bean.setHandlerAccout(App.handlerAccout);
        _workpiece3Bean.setCodeID(App.getSetupBean().getCodeID());
        _workpiece3Bean.setResult(judges[3]);
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

        // 1
        ResultBean3 _workpiece4Bean = new ResultBean3();
        _workpiece4Bean.setMValues(new ArrayList<String>());
        _workpiece4Bean.setMPicPaths(new ArrayList<String>());
        _workpiece4Bean.setHandlerAccout(App.handlerAccout);
        _workpiece4Bean.setCodeID(App.getSetupBean().getCodeID());
        _workpiece4Bean.setResult(judges[4]);
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

        // 1
        ResultBean3 _workpiece5Bean = new ResultBean3();
        _workpiece5Bean.setMValues(new ArrayList<String>());
        _workpiece5Bean.setMPicPaths(new ArrayList<String>());
        _workpiece5Bean.setHandlerAccout(App.handlerAccout);
        _workpiece5Bean.setCodeID(App.getSetupBean().getCodeID());
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

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }


    public class InputBean {
        public boolean isEnable;
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
    }

}
