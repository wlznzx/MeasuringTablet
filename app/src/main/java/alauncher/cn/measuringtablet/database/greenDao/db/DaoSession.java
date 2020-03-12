package alauncher.cn.measuringtablet.database.greenDao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import alauncher.cn.measuringtablet.bean.CalibrationBean;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.ForceCalibrationBean;
import alauncher.cn.measuringtablet.bean.GroupBean;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.RememberPasswordBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.bean.ResultBean2;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.ResultData;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.bean.StepBean;
import alauncher.cn.measuringtablet.bean.StoreBean;
import alauncher.cn.measuringtablet.bean.User;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.RoleBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;

import alauncher.cn.measuringtablet.database.greenDao.db.CalibrationBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.CodeBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.DeviceInfoBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.ForceCalibrationBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.GroupBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.ParameterBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.RememberPasswordBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean2Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean3Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultDataDao;
import alauncher.cn.measuringtablet.database.greenDao.db.SetupBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.StepBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.StoreBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.UserDao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplateBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.RoleBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplateResultBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig calibrationBeanDaoConfig;
    private final DaoConfig codeBeanDaoConfig;
    private final DaoConfig deviceInfoBeanDaoConfig;
    private final DaoConfig forceCalibrationBeanDaoConfig;
    private final DaoConfig groupBeanDaoConfig;
    private final DaoConfig parameter2BeanDaoConfig;
    private final DaoConfig parameterBeanDaoConfig;
    private final DaoConfig rememberPasswordBeanDaoConfig;
    private final DaoConfig resultBeanDaoConfig;
    private final DaoConfig resultBean2DaoConfig;
    private final DaoConfig resultBean3DaoConfig;
    private final DaoConfig resultDataDaoConfig;
    private final DaoConfig setupBeanDaoConfig;
    private final DaoConfig stepBeanDaoConfig;
    private final DaoConfig storeBeanDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig templateBeanDaoConfig;
    private final DaoConfig roleBeanDaoConfig;
    private final DaoConfig templateResultBeanDaoConfig;

    private final CalibrationBeanDao calibrationBeanDao;
    private final CodeBeanDao codeBeanDao;
    private final DeviceInfoBeanDao deviceInfoBeanDao;
    private final ForceCalibrationBeanDao forceCalibrationBeanDao;
    private final GroupBeanDao groupBeanDao;
    private final Parameter2BeanDao parameter2BeanDao;
    private final ParameterBeanDao parameterBeanDao;
    private final RememberPasswordBeanDao rememberPasswordBeanDao;
    private final ResultBeanDao resultBeanDao;
    private final ResultBean2Dao resultBean2Dao;
    private final ResultBean3Dao resultBean3Dao;
    private final ResultDataDao resultDataDao;
    private final SetupBeanDao setupBeanDao;
    private final StepBeanDao stepBeanDao;
    private final StoreBeanDao storeBeanDao;
    private final UserDao userDao;
    private final TemplateBeanDao templateBeanDao;
    private final RoleBeanDao roleBeanDao;
    private final TemplateResultBeanDao templateResultBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        calibrationBeanDaoConfig = daoConfigMap.get(CalibrationBeanDao.class).clone();
        calibrationBeanDaoConfig.initIdentityScope(type);

        codeBeanDaoConfig = daoConfigMap.get(CodeBeanDao.class).clone();
        codeBeanDaoConfig.initIdentityScope(type);

        deviceInfoBeanDaoConfig = daoConfigMap.get(DeviceInfoBeanDao.class).clone();
        deviceInfoBeanDaoConfig.initIdentityScope(type);

        forceCalibrationBeanDaoConfig = daoConfigMap.get(ForceCalibrationBeanDao.class).clone();
        forceCalibrationBeanDaoConfig.initIdentityScope(type);

        groupBeanDaoConfig = daoConfigMap.get(GroupBeanDao.class).clone();
        groupBeanDaoConfig.initIdentityScope(type);

        parameter2BeanDaoConfig = daoConfigMap.get(Parameter2BeanDao.class).clone();
        parameter2BeanDaoConfig.initIdentityScope(type);

        parameterBeanDaoConfig = daoConfigMap.get(ParameterBeanDao.class).clone();
        parameterBeanDaoConfig.initIdentityScope(type);

        rememberPasswordBeanDaoConfig = daoConfigMap.get(RememberPasswordBeanDao.class).clone();
        rememberPasswordBeanDaoConfig.initIdentityScope(type);

        resultBeanDaoConfig = daoConfigMap.get(ResultBeanDao.class).clone();
        resultBeanDaoConfig.initIdentityScope(type);

        resultBean2DaoConfig = daoConfigMap.get(ResultBean2Dao.class).clone();
        resultBean2DaoConfig.initIdentityScope(type);

        resultBean3DaoConfig = daoConfigMap.get(ResultBean3Dao.class).clone();
        resultBean3DaoConfig.initIdentityScope(type);

        resultDataDaoConfig = daoConfigMap.get(ResultDataDao.class).clone();
        resultDataDaoConfig.initIdentityScope(type);

        setupBeanDaoConfig = daoConfigMap.get(SetupBeanDao.class).clone();
        setupBeanDaoConfig.initIdentityScope(type);

        stepBeanDaoConfig = daoConfigMap.get(StepBeanDao.class).clone();
        stepBeanDaoConfig.initIdentityScope(type);

        storeBeanDaoConfig = daoConfigMap.get(StoreBeanDao.class).clone();
        storeBeanDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        templateBeanDaoConfig = daoConfigMap.get(TemplateBeanDao.class).clone();
        templateBeanDaoConfig.initIdentityScope(type);

        roleBeanDaoConfig = daoConfigMap.get(RoleBeanDao.class).clone();
        roleBeanDaoConfig.initIdentityScope(type);

        templateResultBeanDaoConfig = daoConfigMap.get(TemplateResultBeanDao.class).clone();
        templateResultBeanDaoConfig.initIdentityScope(type);

        calibrationBeanDao = new CalibrationBeanDao(calibrationBeanDaoConfig, this);
        codeBeanDao = new CodeBeanDao(codeBeanDaoConfig, this);
        deviceInfoBeanDao = new DeviceInfoBeanDao(deviceInfoBeanDaoConfig, this);
        forceCalibrationBeanDao = new ForceCalibrationBeanDao(forceCalibrationBeanDaoConfig, this);
        groupBeanDao = new GroupBeanDao(groupBeanDaoConfig, this);
        parameter2BeanDao = new Parameter2BeanDao(parameter2BeanDaoConfig, this);
        parameterBeanDao = new ParameterBeanDao(parameterBeanDaoConfig, this);
        rememberPasswordBeanDao = new RememberPasswordBeanDao(rememberPasswordBeanDaoConfig, this);
        resultBeanDao = new ResultBeanDao(resultBeanDaoConfig, this);
        resultBean2Dao = new ResultBean2Dao(resultBean2DaoConfig, this);
        resultBean3Dao = new ResultBean3Dao(resultBean3DaoConfig, this);
        resultDataDao = new ResultDataDao(resultDataDaoConfig, this);
        setupBeanDao = new SetupBeanDao(setupBeanDaoConfig, this);
        stepBeanDao = new StepBeanDao(stepBeanDaoConfig, this);
        storeBeanDao = new StoreBeanDao(storeBeanDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        templateBeanDao = new TemplateBeanDao(templateBeanDaoConfig, this);
        roleBeanDao = new RoleBeanDao(roleBeanDaoConfig, this);
        templateResultBeanDao = new TemplateResultBeanDao(templateResultBeanDaoConfig, this);

        registerDao(CalibrationBean.class, calibrationBeanDao);
        registerDao(CodeBean.class, codeBeanDao);
        registerDao(DeviceInfoBean.class, deviceInfoBeanDao);
        registerDao(ForceCalibrationBean.class, forceCalibrationBeanDao);
        registerDao(GroupBean.class, groupBeanDao);
        registerDao(Parameter2Bean.class, parameter2BeanDao);
        registerDao(ParameterBean.class, parameterBeanDao);
        registerDao(RememberPasswordBean.class, rememberPasswordBeanDao);
        registerDao(ResultBean.class, resultBeanDao);
        registerDao(ResultBean2.class, resultBean2Dao);
        registerDao(ResultBean3.class, resultBean3Dao);
        registerDao(ResultData.class, resultDataDao);
        registerDao(SetupBean.class, setupBeanDao);
        registerDao(StepBean.class, stepBeanDao);
        registerDao(StoreBean.class, storeBeanDao);
        registerDao(User.class, userDao);
        registerDao(TemplateBean.class, templateBeanDao);
        registerDao(RoleBean.class, roleBeanDao);
        registerDao(TemplateResultBean.class, templateResultBeanDao);
    }
    
    public void clear() {
        calibrationBeanDaoConfig.clearIdentityScope();
        codeBeanDaoConfig.clearIdentityScope();
        deviceInfoBeanDaoConfig.clearIdentityScope();
        forceCalibrationBeanDaoConfig.clearIdentityScope();
        groupBeanDaoConfig.clearIdentityScope();
        parameter2BeanDaoConfig.clearIdentityScope();
        parameterBeanDaoConfig.clearIdentityScope();
        rememberPasswordBeanDaoConfig.clearIdentityScope();
        resultBeanDaoConfig.clearIdentityScope();
        resultBean2DaoConfig.clearIdentityScope();
        resultBean3DaoConfig.clearIdentityScope();
        resultDataDaoConfig.clearIdentityScope();
        setupBeanDaoConfig.clearIdentityScope();
        stepBeanDaoConfig.clearIdentityScope();
        storeBeanDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
        templateBeanDaoConfig.clearIdentityScope();
        roleBeanDaoConfig.clearIdentityScope();
        templateResultBeanDaoConfig.clearIdentityScope();
    }

    public CalibrationBeanDao getCalibrationBeanDao() {
        return calibrationBeanDao;
    }

    public CodeBeanDao getCodeBeanDao() {
        return codeBeanDao;
    }

    public DeviceInfoBeanDao getDeviceInfoBeanDao() {
        return deviceInfoBeanDao;
    }

    public ForceCalibrationBeanDao getForceCalibrationBeanDao() {
        return forceCalibrationBeanDao;
    }

    public GroupBeanDao getGroupBeanDao() {
        return groupBeanDao;
    }

    public Parameter2BeanDao getParameter2BeanDao() {
        return parameter2BeanDao;
    }

    public ParameterBeanDao getParameterBeanDao() {
        return parameterBeanDao;
    }

    public RememberPasswordBeanDao getRememberPasswordBeanDao() {
        return rememberPasswordBeanDao;
    }

    public ResultBeanDao getResultBeanDao() {
        return resultBeanDao;
    }

    public ResultBean2Dao getResultBean2Dao() {
        return resultBean2Dao;
    }

    public ResultBean3Dao getResultBean3Dao() {
        return resultBean3Dao;
    }

    public ResultDataDao getResultDataDao() {
        return resultDataDao;
    }

    public SetupBeanDao getSetupBeanDao() {
        return setupBeanDao;
    }

    public StepBeanDao getStepBeanDao() {
        return stepBeanDao;
    }

    public StoreBeanDao getStoreBeanDao() {
        return storeBeanDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TemplateBeanDao getTemplateBeanDao() {
        return templateBeanDao;
    }

    public RoleBeanDao getRoleBeanDao() {
        return roleBeanDao;
    }

    public TemplateResultBeanDao getTemplateResultBeanDao() {
        return templateResultBeanDao;
    }

}
