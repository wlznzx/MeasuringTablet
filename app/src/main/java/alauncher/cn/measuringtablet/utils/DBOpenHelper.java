package alauncher.cn.measuringtablet.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wenld.greendaoupgradehelper.DBMigrationHelper;

import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.database.greenDao.db.DaoMaster;
import alauncher.cn.measuringtablet.database.greenDao.db.GroupBean2Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ParameterBean2Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean2Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean3Dao;


public class DBOpenHelper extends DaoMaster.DevOpenHelper {

    public DBOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.d("wlDebug", "oldVersion = " + oldVersion + " newVersion = " + newVersion);
        try {
            DBMigrationHelper migratorHelper = new DBMigrationHelper();
            //判断版本， 设置需要修改得表  我这边设置一个 FileInfo
            if (oldVersion == 27 && newVersion == 28) {
                // migratorHelper.onUpgrade(db, ParameterBean2Dao.class);
                ParameterBean2Dao.createTable(this.wrap(db), true);
            } else if (oldVersion == 28 && newVersion == 29) {
                ResultBean2Dao.createTable(this.wrap(db), true);
            } else if (oldVersion == 29 && newVersion == 30) {
                GroupBean2Dao.createTable(this.wrap(db), true);
            } else if (oldVersion == 36 && newVersion == 37) {
//                migratorHelper.onUpgrade(db, ResultBean3Dao.class);
                db.execSQL("ALTER TABLE \"RESULT_BEAN3\" ADD \"IS_UPLOADED\" INTEGER");
            } else if (oldVersion == 37 && newVersion == 38) {
                db.execSQL("ALTER TABLE \"TEMPLATE_RESULT_BEAN\" ADD \"IS_UPLOAD\" INTEGER");
            } else if (oldVersion == 38 && newVersion == 39) {
                db.execSQL("ALTER TABLE \"TEMPLATE_RESULT_BEAN\" ADD \"VALUE_TYPES\" TEXT");
            }else {
                super.onUpgrade(db, oldVersion, newVersion);
            }
        } catch (ClassCastException e) {
        }
    }
}
