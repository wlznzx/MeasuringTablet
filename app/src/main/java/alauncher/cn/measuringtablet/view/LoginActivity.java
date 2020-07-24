package alauncher.cn.measuringtablet.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

import alauncher.cn.measuringtablet.AlarmReceiver;
import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.MainActivity;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOLandscapeActivity;
import alauncher.cn.measuringtablet.bean.RememberPasswordBean;
import alauncher.cn.measuringtablet.bean.User;
import alauncher.cn.measuringtablet.database.greenDao.db.UserDao;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class LoginActivity extends BaseOLandscapeActivity {

    @BindView(R.id.login_user_name_edt)
    public EditText loginUserNameEdt;

    @BindView(R.id.login_user_password_edt)
    public EditText loginUserPasswordEdt;

    @BindView(R.id.login_btn)
    public Button loginBtn;

    @BindView(R.id.is_remember_cb)
    public CheckBox isRemeberCB;

    private UserDao mUserDao;

    private List<User> users;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_login_new);
    }

    @Override
    protected void initView() {
        mUserDao = App.getDaoSession().getUserDao();
        users = mUserDao.loadAll();
//        for (User user : users) {
//            android.util.Log.d("wlDebug", user.toString());
//        }
        actionTips.setVisibility(View.INVISIBLE);

        RememberPasswordBean _bean = App.getDaoSession().getRememberPasswordBeanDao().load(App.SETTING_ID);
        if (_bean.getIsRemeber() && _bean.getLogined()) {
            loginUserNameEdt.setText(_bean.getAccount());
            loginUserPasswordEdt.setText(_bean.getPassowrd());
        }


        isRemeberCB.setChecked(_bean.getIsRemeber());

        /*
        DeviceInfoBean _dBean = App.getDeviceInfo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                JdbcUtil.insertOrReplace(_dBean.getFactoryCode(), _dBean.getFactoryName(), _dBean.getDeviceCode(), _dBean.getDeviceName(), _dBean.getManufacturer(),
                        _dBean.getRmk(), App.handlerAccout);
                Log.d("wlDebug", "count = " + JdbcUtil.selectDevice("SXFA1011"));
            }
        }).start();
        */
        verifyStoragePermissions(this);
    }

    @OnClick(R.id.login_btn)
    public void onLogin(View v) {
        String accoutStr = loginUserNameEdt.getText().toString().trim();
        if (accoutStr == null || accoutStr.equals("")) {
            Toast.makeText(this, R.string.username_notnull, Toast.LENGTH_SHORT).show();
            return;
        }
        String passwordStr = loginUserPasswordEdt.getText().toString().trim();
        if (passwordStr == null || passwordStr.equals("")) {
            Toast.makeText(this, R.string.password_notnull, Toast.LENGTH_SHORT).show();
            return;
        }

        User _user;
        _user = mUserDao.load(accoutStr);
        if (_user == null) {
            Toast.makeText(this, R.string.user_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!_user.getPassword().equals(passwordStr)) {
            Toast.makeText(this, R.string.password_error, Toast.LENGTH_SHORT).show();
            return;
        }
        App.handlerAccout = accoutStr;
        App.handlerName = _user.getName();

        RememberPasswordBean _bean = App.getDaoSession().getRememberPasswordBeanDao().load(App.SETTING_ID);
        if (_bean.getIsRemeber()) {
            _bean.setLogined(true);
            _bean.setAccount(accoutStr);
            _bean.setPassowrd(passwordStr);
            App.getDaoSession().getRememberPasswordBeanDao().insertOrReplace(_bean);
        }
        startActivity(new Intent(this, MainActivity.class));
        AlarmReceiver.isLogin = true;
        finish();
    }

    @OnClick(R.id.quick_login_btn)
    public void onQuickBtn(View v) {
        App.handlerAccout = "operator";
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnCheckedChanged(R.id.is_remember_cb)
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        RememberPasswordBean _bean = App.getDaoSession().getRememberPasswordBeanDao().load(App.SETTING_ID);
        _bean.setIsRemeber(isChecked);
        App.getDaoSession().getRememberPasswordBeanDao().insertOrReplace(_bean);
    }

    @Override
    public void onBackPressed() {
    }

}
