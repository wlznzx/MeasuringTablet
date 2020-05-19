package alauncher.cn.measuringtablet.view.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.utils.BuildUtils;
import alauncher.cn.measuringtablet.utils.Constants;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.utils.SPUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InfoFragment extends Fragment {

    private Unbinder unbinder;
    private boolean resumed = false;

    @BindView(R.id.software_version_edt)
    public EditText softwareVersionEdt;

    @BindView(R.id.kernel_version_edt)
    public EditText kernelVersionEdt;

    @BindView(R.id.device_code_edt)
    public EditText deviceCodeEdt;

    @BindView(R.id.base_version_edt)
    public EditText baseVersionEdt;

    @BindView(R.id.factory_code_edt)
    public EditText factoryCodeEdt;

    @BindView(R.id.factory_name_edt)
    public EditText factoryNameEdt;

    @BindView(R.id.manufacturer_edt)
    public EditText manufacturerEdt;

    @BindView(R.id.device_name_edt)
    public EditText deviceNameEdt;

    @BindView(R.id.ip_edt)
    public EditText ipEdt;

    @BindView(R.id.text_input_time_edt)
    public EditText tInputTimeEdt;

    @BindView(R.id.start_tp)
    public Button startTP;

    @BindView(R.id.stop_tp)
    public Button stopTP;

    public int startHour, startMin, stopHour, stopMin;

    SimpleDateFormat formatter = new SimpleDateFormat("HH时 : mm分");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            resumed = savedInstanceState.getBoolean("resumed");
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick(R.id.save_btn)
    public void onSave(View v) {
        DeviceInfoBean _bean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);
        android.util.Log.d("wlDebug", "info = " + _bean.toString());
        if (_bean == null) {

        } else {
            _bean.setFactoryCode(factoryCodeEdt.getText().toString().trim());
            _bean.setFactoryName(factoryNameEdt.getText().toString().trim());
            _bean.setManufacturer(manufacturerEdt.getText().toString().trim());
            _bean.setDeviceName(deviceNameEdt.getText().toString().trim());
            _bean.setDeviceCode(deviceCodeEdt.getText().toString().trim());
            _bean.setStartHour(startHour);
            _bean.setStartMin(startMin);
            _bean.setStopHour(stopHour);
            _bean.setStopMin(stopMin);
        }
        App.getDaoSession().getDeviceInfoBeanDao().insertOrReplace(_bean);
        // Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
        DialogUtils.showDialog(getContext(), "保存", "保存成功.");
        SPUtils.put(getContext(), Constants.IP_KEY, ipEdt.getText().toString().trim());
        android.util.Log.d("wlDebug", "info = " + _bean.toString());
        SPUtils.put(getContext(), Constants.INPUT_TIME_KEY, Long.valueOf(tInputTimeEdt.getText().toString().trim()));
        JdbcUtil.IP = String.valueOf(SPUtils.get(getContext(), Constants.IP_KEY, "47.98.58.40"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JdbcUtil.insertOrReplace(_bean.getFactoryCode(), _bean.getFactoryName(), _bean.getDeviceCode(), _bean.getDeviceName(), _bean.getManufacturer(),
                            "rmk3", App.handlerAccout);
                } catch (Exception e) {
                    android.util.Log.d("wlDebug", "catch by this ? ");
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showDialog(getActivity(), "上传失败", "上传服务器失败，请检查网络.");
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (resumed) {
            resumed = false;
        }
    }

    @Override
    public void onPause() {
        resumed = true;
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        DeviceInfoBean _bean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);

        if (_bean != null) {
            factoryCodeEdt.setText(_bean.getFactoryCode());
            factoryNameEdt.setText(_bean.getFactoryName());
            manufacturerEdt.setText(_bean.getManufacturer());
            deviceNameEdt.setText(_bean.getDeviceName());
        } else {

        }
        ipEdt.setText(String.valueOf(SPUtils.get(getContext(), Constants.IP_KEY, "47.98.58.40")));
        softwareVersionEdt.setText(BuildUtils.packageName(getContext()));
        kernelVersionEdt.setText(BuildUtils.getLinuxCore_Ver());
        deviceCodeEdt.setText(_bean.getDeviceCode());
        baseVersionEdt.setText(BuildUtils.getInner_Ver());

        startHour = _bean.getStartHour();
        startMin = _bean.getStartMin();
        stopHour = _bean.getStopHour();
        stopMin = _bean.getStopMin();

        tInputTimeEdt.setText(String.valueOf(SPUtils.get(getContext(), Constants.INPUT_TIME_KEY, Long.valueOf(800))));
        startTP.setText("" + _bean.getStartHour() + "时:" + _bean.getStartMin() + "分");
        Date startTime = new Date();
        startTime.setHours(_bean.getStartHour());
        startTime.setMinutes(_bean.getStartMin());
        startTP.setText(formatter.format(startTime));
        startTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (stopHour < hourOfDay || (stopHour == hourOfDay && stopMin <= minute)) {
                            Toast.makeText(getContext(), "结束时间不能小于开始时间.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startHour = hourOfDay;
                        startMin = minute;
                        Date currentTime = new Date();
                        currentTime.setHours(startHour);
                        currentTime.setMinutes(startMin);
                        String dateString = formatter.format(currentTime);
                        startTP.setText(dateString);
                    }
                }, _bean.getStartHour(), _bean.getStartMin(), true).show();
            }
        });

        Date stopTime = new Date();
        stopTime.setHours(_bean.getStopHour());
        stopTime.setMinutes(_bean.getStopMin());
        stopTP.setText(formatter.format(stopTime));
        stopTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < startHour || (hourOfDay == startHour && minute <= startMin)) {
                            Toast.makeText(getContext(), "结束时间不能小于开始时间.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        stopHour = hourOfDay;
                        stopMin = minute;
                        Date stopTime = new Date();
                        stopTime.setHours(stopHour);
                        stopTime.setMinutes(stopMin);
                        stopTP.setText(formatter.format(stopTime));
                    }
                }, _bean.getStopHour(), _bean.getStopMin(), true).show();
            }
        });
    }

}
