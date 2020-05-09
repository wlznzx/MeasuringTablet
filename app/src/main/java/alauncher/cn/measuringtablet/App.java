package alauncher.cn.measuringtablet;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.tencent.bugly.beta.upgrade.UpgradeListener;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.ArrayList;

import alauncher.cn.measuringtablet.bean.CalibrationBean;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.ForceCalibrationBean;
import alauncher.cn.measuringtablet.bean.GroupBean;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.RememberPasswordBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.bean.RoleBean;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.User;
import alauncher.cn.measuringtablet.database.greenDao.db.DaoMaster;
import alauncher.cn.measuringtablet.database.greenDao.db.DaoSession;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.utils.Constants;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.utils.SPUtils;
import alauncher.cn.measuringtablet.utils.SystemPropertiesProxy;
import alauncher.cn.measuringtablet.view.UpgradeActivity;

/**
 * 日期：2019/4/25 0025 10:27
 * 包名：alauncher.cn.measuringtablet
 * 作者： wlznzx
 * 描述：
 */
public class App extends MultiDexApplication {


    private static DaoSession mDaoSession;

    // public static int codeID = 1;

    public static long SETTING_ID = 1;

    public static String handlerAccout = "恩梯";
    public static String handlerName = "";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "mi.db", null);
        // DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "/storage/emulated/0/NTBackup/mi2020-05-09_15-59-53_R4CBB20226100635.db", null);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession(IdentityScopeType.None);
        // 初始化默认的数值;
        new Thread(new Runnable() {
            @Override
            public void run() {
                initDefaultDate();
            }
        }).start();

//        Beta.autoCheckUpgrade = true;
        // Beta.canShowUpgradeActs.add(SystemManagementActivity.class);
        Beta.enableNotification = false;
        Beta.upgradeListener = new UpgradeListener() {
            @Override
            public void onUpgrade(int ret, UpgradeInfo strategy, boolean isManual, boolean isSilence) {
                if (strategy != null) {
                    Intent i = new Intent();
                    i.setClass(getApplicationContext(), UpgradeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } else {
                    // Toast.makeText(App.this, R.string.no_more_version, Toast.LENGTH_LONG).show();
                }
            }
        };
        Bugly.init(getApplicationContext(), "be2b337540", true);

        android.util.Log.d("wlDebug", getDatabasePath("..").getAbsolutePath());
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static SetupBean getSetupBean() {
        return getDaoSession().getSetupBeanDao().load(SETTING_ID);
    }

    public static void setSetupPopUp(boolean isPopUp) {
        SetupBean _bean = getSetupBean();
        _bean.setIsAutoPopUp(isPopUp);
        getDaoSession().getSetupBeanDao().update(_bean);
    }

    public static String getCodeName() {
        String codeName = "";
        CodeBean _CodeBean = App.getDaoSession().getCodeBeanDao().load((long) getSetupBean().getCodeID());
        if (_CodeBean != null) {
            codeName = _CodeBean.getName();
        } else {
            codeName = "程序" + App.getSetupBean().getCodeID();
        }
        return codeName;
    }

    public static DeviceInfoBean getDeviceInfo() {
        return getDaoSession().getDeviceInfoBeanDao().load(SETTING_ID);
    }

    public void initDefaultDate() {

        JdbcUtil.IP = String.valueOf(SPUtils.get(this, Constants.IP_KEY, "47.98.58.40"));

        if (getDaoSession().getSetupBeanDao().load(SETTING_ID) == null) {
            SetupBean _bean = new SetupBean();
            _bean.setCodeID(1);
            _bean.setAccout("wl");
            _bean.setIsAutoPopUp(false);
            _bean.setXUpperLine(0.007);
            _bean.setXLowerLine(-0.007);
            getDaoSession().getSetupBeanDao().insert(_bean);
        }

        if (getDaoSession().getUserDao().loadAll().size() == 0) {
            User _user = new User();
            _user.setAccout("xgy");
            _user.setPassword("1");
            _user.setName("薛国毅");
            _user.setStatus(0);
            _user.setId("1");
            _user.setLimit(0);
            _user.setEmail("");
            getDaoSession().getUserDao().insert(_user);

            User _manager = new User();
            _manager.setAccout("zj");
            _manager.setPassword("1");
            _manager.setName("张杰");
            _manager.setStatus(0);
            _manager.setId("2");
            _manager.setLimit(0);
            _manager.setEmail("");
            getDaoSession().getUserDao().insert(_manager);

            User _monitor = new User();
            _monitor.setAccout("zxz");
            _monitor.setPassword("1");
            _monitor.setName("周秀珍");
            _monitor.setStatus(0);
            _monitor.setId("3");
            _monitor.setLimit(0);
            _monitor.setEmail("");
            getDaoSession().getUserDao().insert(_monitor);

            User _operator = new User();
            _operator.setAccout("dh");
            _operator.setPassword("123456");
            _operator.setName("邓华");
            _operator.setStatus(0);
            _operator.setId("4");
            _operator.setLimit(0);
            _operator.setEmail("");
            getDaoSession().getUserDao().insert(_operator);

            User _wmg = new User();
            _wmg.setAccout("wmg");
            _wmg.setPassword("1");
            _wmg.setName("王美干");
            _wmg.setStatus(0);
            _wmg.setId("5");
            _wmg.setLimit(0);
            _wmg.setEmail("");
            getDaoSession().getUserDao().insert(_wmg);

            User _hhb = new User();
            _hhb.setAccout("hhb");
            _hhb.setPassword("1");
            _hhb.setName("胡海波");
            _hhb.setStatus(0);
            _hhb.setId("6");
            _hhb.setLimit(0);
            _hhb.setEmail("");
            getDaoSession().getUserDao().insert(_hhb);

            User _yxh = new User();
            _yxh.setAccout("yxh");
            _yxh.setPassword("1");
            _yxh.setName("尹学慧");
            _yxh.setStatus(0);
            _yxh.setId("7");
            _yxh.setLimit(0);
            _yxh.setEmail("");
            getDaoSession().getUserDao().insert(_yxh);
        }

        if (getDaoSession().getRememberPasswordBeanDao().load(App.SETTING_ID) == null) {
            RememberPasswordBean _bean = new RememberPasswordBean();
            _bean.setId(SETTING_ID);
            _bean.setAccount("");
            _bean.setPassowrd("");
            _bean.setIsRemeber(false);
            _bean.setLogined(false);
            getDaoSession().getRememberPasswordBeanDao().insertOrReplace(_bean);
        }

        if (getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID) == null) {
            DeviceInfoBean _bean = new DeviceInfoBean();
            _bean.setId(SETTING_ID);
            _bean.setFactoryCode(getResources().getString(R.string.default_factory_code));
            _bean.setFactoryName(getResources().getString(R.string.default_factory_name));
            _bean.setManufacturer(getResources().getString(R.string.manufacturer));
            String serial = android.os.Build.SERIAL;
            _bean.setDeviceCode(serial);
            _bean.setDeviceName(getResources().getString(R.string.default_device_name) + "_" + serial);
            _bean.setRmk("rmk");
            _bean.setStartHour(9);
            _bean.setStartMin(0);
            _bean.setStopHour(18);
            _bean.setStartMin(0);
            android.util.Log.d("wlDebug", "_bean = " + _bean.toString());
            getDaoSession().getDeviceInfoBeanDao().insertOrReplace(_bean);
        }

        if (getDaoSession().getForceCalibrationBeanDao().load(SETTING_ID) == null) {
            ForceCalibrationBean _bean = new ForceCalibrationBean();
            _bean.set_id(SETTING_ID);
            _bean.setForceMode(0);
            _bean.setForceNum(50);
            _bean.setForceTime(60);
            getDaoSession().getForceCalibrationBeanDao().insert(_bean);
        }

        /*
        if (getDaoSession().getStoreBeanDao().load(SETTING_ID) == null) {
            for (int i = 1; i < Constants.defaultCodeBeans.size(); i++) {
                CodeBean _bean = Constants.defaultCodeBeans.get(i);
                _bean.setCodeID(i);
                _bean.setMachineTool(getResources().getString(R.string.machine_tool) + i);
                _bean.setParts(getResources().getString(R.string.spare_parts) + i);
                getDaoSession().getCodeBeanDao().insert(_bean);
            }

            StoreBean _bean = new StoreBean();
            _bean.setId(SETTING_ID);
            _bean.setStoreMode(2);
            _bean.setUpLimitValue(30.045);
            _bean.setLowLimitValue(29.995);
            _bean.setMValue(0);
            _bean.setDelayTime(1);
            getDaoSession().getStoreBeanDao().insert(_bean);
        }
         */
        //
        for (int i = 1; i < Constants.defaultCodeBeans.size(); i++) {
            if (getDaoSession().getCalibrationBeanDao().load((long) i) == null) {
                CalibrationBean _bean = new CalibrationBean();
                _bean.setCode_id(i);
                if (1 == i) {
                    _bean.setCh1BigPartStandard(30.059);
                    _bean.setCh1SmallPartStandard(30.0168);

                    _bean.setCh2BigPartStandard(30.059);
                    _bean.setCh2SmallPartStandard(30.0168);

                    _bean.setCh3BigPartStandard(30.059);
                    _bean.setCh3SmallPartStandard(30.0168);

                    _bean.setCh4BigPartStandard(30.059);
                    _bean.setCh4SmallPartStandard(30.0168);
                } else {
                    _bean.setCh1BigPartStandard(30.04);
                    _bean.setCh1SmallPartStandard(30);

                    _bean.setCh2BigPartStandard(30.04);
                    _bean.setCh2SmallPartStandard(30);

                    _bean.setCh3BigPartStandard(30.04);
                    _bean.setCh3SmallPartStandard(30);

                    _bean.setCh4BigPartStandard(30.04);
                    _bean.setCh4SmallPartStandard(30);
                }
                _bean.setCh1CalibrationType(1);
                _bean.setCh1CompensationValue(0.007);
                _bean.setCh1KValue(0.01);

                _bean.setCh2BigPartStandard(30.059);
                _bean.setCh2SmallPartStandard(30.0168);
                _bean.setCh2CalibrationType(1);
                _bean.setCh2CompensationValue(0.007);
                _bean.setCh2KValue(0.01);

                _bean.setCh3BigPartStandard(30.059);
                _bean.setCh3SmallPartStandard(30.0168);
                _bean.setCh3CalibrationType(1);
                _bean.setCh3CompensationValue(0.007);
                _bean.setCh3KValue(0.01);

                _bean.setCh4BigPartStandard(30.059);
                _bean.setCh4SmallPartStandard(30.0168);
                _bean.setCh4CalibrationType(1);
                _bean.setCh4CompensationValue(0.007);
                _bean.setCh4KValue(0.01);
                getDaoSession().getCalibrationBeanDao().insert(_bean);
            }

            // 初始化分组;
            if (getDaoSession().getParameterBeanDao().load((long) i) == null) {
                for (int j = 1; j <= 4; j++) {
                    GroupBean _bean = new GroupBean();
                    _bean.setCode_id(i);
                    _bean.setM_index(j);
                    _bean.setA_describe("恩");
                    _bean.setA_upper_limit(30.04);
                    _bean.setA_lower_limit(30.03);

                    _bean.setB_describe("梯");
                    _bean.setB_upper_limit(30.03);
                    _bean.setB_lower_limit(30.02);

                    _bean.setC_describe("科");
                    _bean.setC_upper_limit(30.02);
                    _bean.setC_lower_limit(30.01);

                    _bean.setD_describe("技");
                    _bean.setD_upper_limit(30.01);
                    _bean.setD_lower_limit(30);
                    getDaoSession().getGroupBeanDao().insert(_bean);
                }
            }

            // 初始化参数;
            if (getDaoSession().getParameterBeanDao().load((long) i) == null) {
                ParameterBean _bean = new ParameterBean();
                _bean.setCode_id(i);
                _bean.setM1_enable(true);
                _bean.setM1_describe("内径1");
                _bean.setM1_nominal_value(30.0);
                _bean.setM1_upper_tolerance_value(0.04);
                _bean.setM1_lower_tolerance_value(0.0);
                _bean.setM1_scale(0.1);
                _bean.setM1_offect(0.0);
                _bean.setM1_code("ch1");

                _bean.setM2_enable(true);
                _bean.setM2_describe("内径2");
                _bean.setM2_nominal_value(30.0);
                _bean.setM2_upper_tolerance_value(0.04);
                _bean.setM2_lower_tolerance_value(0.0);
                _bean.setM2_scale(0.1);
                _bean.setM2_offect(0.0);
                _bean.setM2_code("ch2");

                _bean.setM3_enable(true);
                _bean.setM3_describe("内径3");
                _bean.setM3_nominal_value(30.0);
                _bean.setM3_upper_tolerance_value(0.04);
                _bean.setM3_lower_tolerance_value(0.0);
                _bean.setM3_scale(0.1);
                _bean.setM3_offect(0.0);
                _bean.setM3_code("ch3");

                _bean.setM4_enable(true);
                _bean.setM4_describe("内径4");
                _bean.setM4_nominal_value(30.0);
                _bean.setM4_upper_tolerance_value(0.04);
                _bean.setM4_lower_tolerance_value(0.0);
                _bean.setM4_scale(0.1);
                _bean.setM4_offect(0.0);
                _bean.setM4_code("ch4");
                getDaoSession().getParameterBeanDao().insert(_bean);
            }

            // 默认模板;
            if (getDaoSession().getTemplateBeanDao().load((long) i) == null) {
                TemplateBean mTemplateBean = getDefaultTemplateBean();
                // mTemplateBean.setCodeID(i);
                getDaoSession().getTemplateBeanDao().insert(mTemplateBean);
            }

            /*
            if (getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) i)).list().size() <= 0) {
                for (int j = 1; j <= 22; j++) {
                    Parameter2Bean _bean = new Parameter2Bean();
                    _bean.setCode_id(i);
                    _bean.setIndex(j);
                    _bean.setEnable(true);
                    _bean.setDescribe("内径" + j);
                    _bean.setNominal_value(24.0);
                    _bean.setUpper_tolerance_value(0.04);
                    _bean.setLower_tolerance_value(0.0);
                    getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                }
            }
            */
            if (getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) i)).list().size() <= 0) {
                android.util.Log.d("wlDebug", "i = " + i);
                if (i <= 15) {
                    android.util.Log.d("wlDebug", "i = 1");
                    for (int j = 1; j < Constants.m1_15DefaultParameterBeanList.size(); j++) {
                        Parameter2Bean _bean = new Parameter2Bean();
                        _bean.setCode_id(i);
                        _bean.setIndex(j);
                        _bean.setEnable(true);
                        _bean.setDescribe(Constants.m1_15DefaultParameterBeanList.get(j).name);
                        _bean.setNominal_value(Constants.m1_15DefaultParameterBeanList.get(j).normalValue);
                        _bean.setUpper_tolerance_value(Constants.m1_15DefaultParameterBeanList.get(j).upperLimit);
                        _bean.setLower_tolerance_value(Constants.m1_15DefaultParameterBeanList.get(j).lowerLimit);
                        getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                    }
                } else if (16 <= i && i <= 27) {
                    android.util.Log.d("wlDebug", "i = 2");
                    for (int j = 1; j < Constants.m16_27DefaultParameterBeanList.size(); j++) {
                        Parameter2Bean _bean = new Parameter2Bean();
                        _bean.setCode_id(i);
                        _bean.setIndex(j);
                        _bean.setEnable(true);
                        _bean.setDescribe(Constants.m16_27DefaultParameterBeanList.get(j).name);
                        _bean.setNominal_value(Constants.m16_27DefaultParameterBeanList.get(j).normalValue);
                        _bean.setUpper_tolerance_value(Constants.m16_27DefaultParameterBeanList.get(j).upperLimit);
                        _bean.setLower_tolerance_value(Constants.m16_27DefaultParameterBeanList.get(j).lowerLimit);
                        getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                    }
                } else if (28 <= i && i <= 30) {
                    for (int j = 1; j < Constants.m28_30DefaultParameterBeanList.size(); j++) {
                        Parameter2Bean _bean = new Parameter2Bean();
                        _bean.setCode_id(i);
                        _bean.setIndex(j);
                        _bean.setEnable(true);
                        _bean.setDescribe(Constants.m28_30DefaultParameterBeanList.get(j).name);
                        _bean.setNominal_value(Constants.m28_30DefaultParameterBeanList.get(j).normalValue);
                        _bean.setUpper_tolerance_value(Constants.m28_30DefaultParameterBeanList.get(j).upperLimit);
                        _bean.setLower_tolerance_value(Constants.m28_30DefaultParameterBeanList.get(j).lowerLimit);
                        getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                    }
                } else if (31 <= i && i <= 42) {
                    android.util.Log.d("wlDebug", "i = 3");
                    for (int j = 1; j < Constants.m31_45DefaultParameterBeanList.size(); j++) {
                        Parameter2Bean _bean = new Parameter2Bean();
                        _bean.setCode_id(i);
                        _bean.setIndex(j);
                        _bean.setEnable(true);
                        _bean.setDescribe(Constants.m31_45DefaultParameterBeanList.get(j).name);
                        _bean.setNominal_value(Constants.m31_45DefaultParameterBeanList.get(j).normalValue);
                        _bean.setUpper_tolerance_value(Constants.m31_45DefaultParameterBeanList.get(j).upperLimit);
                        _bean.setLower_tolerance_value(Constants.m31_45DefaultParameterBeanList.get(j).lowerLimit);
                        getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                    }
                } else {
                    android.util.Log.d("wlDebug", "i = 4");
                    for (int j = 1; j < Constants.m43_45DefaultParameterBeanList.size(); j++) {
                        Parameter2Bean _bean = new Parameter2Bean();
                        _bean.setCode_id(i);
                        _bean.setIndex(j);
                        _bean.setEnable(true);
                        _bean.setDescribe(Constants.m43_45DefaultParameterBeanList.get(j).name);
                        _bean.setNominal_value(Constants.m43_45DefaultParameterBeanList.get(j).normalValue);
                        _bean.setUpper_tolerance_value(Constants.m43_45DefaultParameterBeanList.get(j).upperLimit);
                        _bean.setLower_tolerance_value(Constants.m43_45DefaultParameterBeanList.get(j).lowerLimit);
                        getDaoSession().getParameter2BeanDao().insertOrReplace(_bean);
                    }
                }
            }

            if (getDaoSession().getRoleBeanDao().loadAll().size() > 0) {
                RoleBean _bean = new RoleBean();
                _bean.setRoleName("系长");
                getDaoSession().getRoleBeanDao().insert(_bean);
                _bean.setRoleName("课长");
                getDaoSession().getRoleBeanDao().insert(_bean);
                _bean.setRoleName("部长");
                getDaoSession().getRoleBeanDao().insert(_bean);
            }

            /**/
            if (getDaoSession().getCodeBeanDao().load((long) (i)) == null) {
                CodeBean _bean = Constants.defaultCodeBeans.get(i);
                _bean.setMachineTool(getResources().getString(R.string.machine_tool));
                _bean.setParts(getResources().getString(R.string.spare_parts));
                _bean.setDefaultTitles(new ArrayList<>());
                getDaoSession().getCodeBeanDao().insert(_bean);
            }

        }
        // initTestDatas();
    }

    public static TemplateBean getDefaultTemplateBean() {
        TemplateBean mTemplateBean = new TemplateBean();
        mTemplateBean.setName("模板");
        mTemplateBean.setDataNum(5);
        ArrayList<String> Titles = new ArrayList<>();
        ArrayList<String> TitleTypes = new ArrayList<>();
        Titles.add("部品名称");
        TitleTypes.add("0");
        Titles.add("进货批量");
        TitleTypes.add("0");
        Titles.add("进货日期");
        TitleTypes.add("2");
        Titles.add("检查日期");
        TitleTypes.add("2");
        Titles.add("部品代号");
        TitleTypes.add("0");
        Titles.add("尺寸检查");
        TitleTypes.add("0");
        Titles.add("生产日期");
        TitleTypes.add("2");
        Titles.add("部品发送到");
        TitleTypes.add("0");
        Titles.add("材料名称");
        TitleTypes.add("0");
        Titles.add("订单No");
        TitleTypes.add("0");
        Titles.add("检查目的");
        TitleTypes.add("0");
        // Titles.add("供货方名称");
        ArrayList<String> AQLList = new ArrayList<>();
        ArrayList<String> AQLTypeList = new ArrayList<>();
        AQLList.add("无毛刺、无异物、无气孔");
        AQLTypeList.add("1");
        AQLList.add("无收缩、无裂缝、无缺陷");
        AQLTypeList.add("1");
        AQLList.add("毛刺高度控制在0.3以下");
        AQLTypeList.add("1");
        AQLList.add("外包装无破损、无变形、无潮湿");
        AQLTypeList.add("1");
        AQLList.add("月份标签确认");
        AQLTypeList.add("1");
        AQLList.add("供应商数据确认");
        AQLTypeList.add("1");
        ArrayList<String> RoHSList = new ArrayList<>();
        ArrayList<String> RoHSTypeList = new ArrayList<>();
        RoHSList.add("RoHS确认频率");
        RoHSTypeList.add("0");
        RoHSList.add("本批确认");
        RoHSTypeList.add("1");
        RoHSList.add("上回RoHS确认日");
        RoHSTypeList.add("2");
        RoHSList.add("确认结果(RoHS检查数据以检查日期追溯)");
        RoHSTypeList.add("1");
        RoHSList.add("模号:");
        RoHSTypeList.add("0");
        ArrayList<String> signList = new ArrayList<>();
        signList.add("课长");
        signList.add("系长");
        signList.add("部长");
        signList.add("担当");
        mTemplateBean.setAverageEnable(true);
        mTemplateBean.setMaximumEnable(true);
        mTemplateBean.setMinimumEnable(true);
        mTemplateBean.setRangeEnable(true);
        mTemplateBean.setJudgeEnable(true);
        mTemplateBean.setAqlEnable(true);
        mTemplateBean.setRoshEnable(true);

        mTemplateBean.setTitle("量产受入品检查表E*W");
        mTemplateBean.setAQLList(AQLList);
        mTemplateBean.setRoHSList(RoHSList);
        mTemplateBean.setTitleList(Titles);
        mTemplateBean.setSignList(signList);
        mTemplateBean.setTitleTypeList(TitleTypes);
        mTemplateBean.setAQLTypeList(AQLTypeList);
        mTemplateBean.setRoHSTypeList(RoHSTypeList);
        return mTemplateBean;
    }

    public void initTestDatas() {
        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9914, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.992, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));
        // 6
        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9914, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9906, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        // 11
        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9912, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9922, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9912, 24, 24, 24, "m1", "m2", "m3", "m4"));

        // 21
        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9912, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9914, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.991, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        // 31
        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.99, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9894, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9896, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9898, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9902, 24, 24, 24, "m1", "m2", "m3", "m4"));

        App.getDaoSession().getResultBeanDao().insert(new ResultBean(null, 1, "吴工",
                System.currentTimeMillis(), "wkid", "wkex", "eventid", "ev",
                "合格", 23.9912, 24, 24, 24, "m1", "m2", "m3", "m4"));
    }

}
