package alauncher.cn.measuringtablet.database.greenDao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import alauncher.cn.measuringtablet.bean.ForceCalibrationBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FORCE_CALIBRATION_BEAN".
*/
public class ForceCalibrationBeanDao extends AbstractDao<ForceCalibrationBean, Long> {

    public static final String TABLENAME = "FORCE_CALIBRATION_BEAN";

    /**
     * Properties of entity ForceCalibrationBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, long.class, "_id", true, "_id");
        public final static Property ForceMode = new Property(1, int.class, "forceMode", false, "FORCE_MODE");
        public final static Property ForceTime = new Property(2, int.class, "forceTime", false, "FORCE_TIME");
        public final static Property ForceNum = new Property(3, int.class, "forceNum", false, "FORCE_NUM");
        public final static Property RealForceTime = new Property(4, long.class, "realForceTime", false, "REAL_FORCE_TIME");
        public final static Property UsrNum = new Property(5, int.class, "usrNum", false, "USR_NUM");
    }


    public ForceCalibrationBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ForceCalibrationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FORCE_CALIBRATION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: _id
                "\"FORCE_MODE\" INTEGER NOT NULL ," + // 1: forceMode
                "\"FORCE_TIME\" INTEGER NOT NULL ," + // 2: forceTime
                "\"FORCE_NUM\" INTEGER NOT NULL ," + // 3: forceNum
                "\"REAL_FORCE_TIME\" INTEGER NOT NULL ," + // 4: realForceTime
                "\"USR_NUM\" INTEGER NOT NULL );"); // 5: usrNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FORCE_CALIBRATION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ForceCalibrationBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.get_id());
        stmt.bindLong(2, entity.getForceMode());
        stmt.bindLong(3, entity.getForceTime());
        stmt.bindLong(4, entity.getForceNum());
        stmt.bindLong(5, entity.getRealForceTime());
        stmt.bindLong(6, entity.getUsrNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ForceCalibrationBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.get_id());
        stmt.bindLong(2, entity.getForceMode());
        stmt.bindLong(3, entity.getForceTime());
        stmt.bindLong(4, entity.getForceNum());
        stmt.bindLong(5, entity.getRealForceTime());
        stmt.bindLong(6, entity.getUsrNum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public ForceCalibrationBean readEntity(Cursor cursor, int offset) {
        ForceCalibrationBean entity = new ForceCalibrationBean( //
            cursor.getLong(offset + 0), // _id
            cursor.getInt(offset + 1), // forceMode
            cursor.getInt(offset + 2), // forceTime
            cursor.getInt(offset + 3), // forceNum
            cursor.getLong(offset + 4), // realForceTime
            cursor.getInt(offset + 5) // usrNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ForceCalibrationBean entity, int offset) {
        entity.set_id(cursor.getLong(offset + 0));
        entity.setForceMode(cursor.getInt(offset + 1));
        entity.setForceTime(cursor.getInt(offset + 2));
        entity.setForceNum(cursor.getInt(offset + 3));
        entity.setRealForceTime(cursor.getLong(offset + 4));
        entity.setUsrNum(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ForceCalibrationBean entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ForceCalibrationBean entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ForceCalibrationBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
