package alauncher.cn.measuringtablet.database.greenDao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import alauncher.cn.measuringtablet.bean.DeviceInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICE_INFO_BEAN".
*/
public class DeviceInfoBeanDao extends AbstractDao<DeviceInfoBean, Long> {

    public static final String TABLENAME = "DEVICE_INFO_BEAN";

    /**
     * Properties of entity DeviceInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FactoryCode = new Property(1, String.class, "factoryCode", false, "FACTORY_CODE");
        public final static Property FactoryName = new Property(2, String.class, "factoryName", false, "FACTORY_NAME");
        public final static Property DeviceCode = new Property(3, String.class, "deviceCode", false, "DEVICE_CODE");
        public final static Property DeviceName = new Property(4, String.class, "deviceName", false, "DEVICE_NAME");
        public final static Property Manufacturer = new Property(5, String.class, "manufacturer", false, "MANUFACTURER");
        public final static Property Rmk = new Property(6, String.class, "rmk", false, "RMK");
        public final static Property StartHour = new Property(7, int.class, "startHour", false, "START_HOUR");
        public final static Property StopHour = new Property(8, int.class, "stopHour", false, "STOP_HOUR");
        public final static Property StartMin = new Property(9, int.class, "startMin", false, "START_MIN");
        public final static Property StopMin = new Property(10, int.class, "stopMin", false, "STOP_MIN");
    }


    public DeviceInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DeviceInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICE_INFO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FACTORY_CODE\" TEXT," + // 1: factoryCode
                "\"FACTORY_NAME\" TEXT," + // 2: factoryName
                "\"DEVICE_CODE\" TEXT," + // 3: deviceCode
                "\"DEVICE_NAME\" TEXT," + // 4: deviceName
                "\"MANUFACTURER\" TEXT," + // 5: manufacturer
                "\"RMK\" TEXT," + // 6: rmk
                "\"START_HOUR\" INTEGER NOT NULL ," + // 7: startHour
                "\"STOP_HOUR\" INTEGER NOT NULL ," + // 8: stopHour
                "\"START_MIN\" INTEGER NOT NULL ," + // 9: startMin
                "\"STOP_MIN\" INTEGER NOT NULL );"); // 10: stopMin
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICE_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeviceInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String factoryCode = entity.getFactoryCode();
        if (factoryCode != null) {
            stmt.bindString(2, factoryCode);
        }
 
        String factoryName = entity.getFactoryName();
        if (factoryName != null) {
            stmt.bindString(3, factoryName);
        }
 
        String deviceCode = entity.getDeviceCode();
        if (deviceCode != null) {
            stmt.bindString(4, deviceCode);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(5, deviceName);
        }
 
        String manufacturer = entity.getManufacturer();
        if (manufacturer != null) {
            stmt.bindString(6, manufacturer);
        }
 
        String rmk = entity.getRmk();
        if (rmk != null) {
            stmt.bindString(7, rmk);
        }
        stmt.bindLong(8, entity.getStartHour());
        stmt.bindLong(9, entity.getStopHour());
        stmt.bindLong(10, entity.getStartMin());
        stmt.bindLong(11, entity.getStopMin());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeviceInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String factoryCode = entity.getFactoryCode();
        if (factoryCode != null) {
            stmt.bindString(2, factoryCode);
        }
 
        String factoryName = entity.getFactoryName();
        if (factoryName != null) {
            stmt.bindString(3, factoryName);
        }
 
        String deviceCode = entity.getDeviceCode();
        if (deviceCode != null) {
            stmt.bindString(4, deviceCode);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(5, deviceName);
        }
 
        String manufacturer = entity.getManufacturer();
        if (manufacturer != null) {
            stmt.bindString(6, manufacturer);
        }
 
        String rmk = entity.getRmk();
        if (rmk != null) {
            stmt.bindString(7, rmk);
        }
        stmt.bindLong(8, entity.getStartHour());
        stmt.bindLong(9, entity.getStopHour());
        stmt.bindLong(10, entity.getStartMin());
        stmt.bindLong(11, entity.getStopMin());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeviceInfoBean readEntity(Cursor cursor, int offset) {
        DeviceInfoBean entity = new DeviceInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // factoryCode
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // factoryName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // deviceCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // deviceName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // manufacturer
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // rmk
            cursor.getInt(offset + 7), // startHour
            cursor.getInt(offset + 8), // stopHour
            cursor.getInt(offset + 9), // startMin
            cursor.getInt(offset + 10) // stopMin
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeviceInfoBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFactoryCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFactoryName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDeviceCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDeviceName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setManufacturer(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRmk(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStartHour(cursor.getInt(offset + 7));
        entity.setStopHour(cursor.getInt(offset + 8));
        entity.setStartMin(cursor.getInt(offset + 9));
        entity.setStopMin(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeviceInfoBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeviceInfoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DeviceInfoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
