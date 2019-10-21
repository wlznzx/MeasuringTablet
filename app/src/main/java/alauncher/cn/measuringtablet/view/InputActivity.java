package alauncher.cn.measuringtablet.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
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
import butterknife.OnClick;


public class InputActivity extends BaseOLandscapeActivity {

    @BindView(R.id.rv)
    public RecyclerView rv;

    public EnterAdapter mEnterAdapter;

    private List<Parameter2Bean> mParameter2Beans;

    private List<InputBean> datas = new ArrayList<>();

    private int currentPosPic;
    private int workpieceNum;

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
        ResultBean3 _workpiece1Bean = new ResultBean3();
        _workpiece1Bean.setMValues(new ArrayList<String>());
        _workpiece1Bean.setMPicPaths(new ArrayList<String>());
        _workpiece1Bean.setHandlerAccout(App.handlerAccout);
        _workpiece1Bean.setCodeID(App.getSetupBean().getCodeID());
//        for (InputBean _bean : datas) {
//            _workpiece1Bean.getMValues().add(_bean.workspace1Value);
//            _workpiece1Bean.getMPicPaths().add(_bean.workspace1PicPath);
//        }
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
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }


    public class InputBean {
        public boolean isEnable;
        public String measuringStr;
        public String describeStr;
        public String maxValue;
        public String minValue;
        public String judge;
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
    }

}
