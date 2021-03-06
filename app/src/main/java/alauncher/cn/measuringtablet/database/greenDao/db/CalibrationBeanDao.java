package alauncher.cn.measuringtablet.database.greenDao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import alauncher.cn.measuringtablet.bean.CalibrationBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CALIBRATION_BEAN".
*/
public class CalibrationBeanDao extends AbstractDao<CalibrationBean, Long> {

    public static final String TABLENAME = "CALIBRATION_BEAN";

    /**
     * Properties of entity CalibrationBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Code_id = new Property(0, long.class, "code_id", true, "_id");
        public final static Property Ch1CalibrationType = new Property(1, int.class, "ch1CalibrationType", false, "CH1_CALIBRATION_TYPE");
        public final static Property Ch2CalibrationType = new Property(2, int.class, "ch2CalibrationType", false, "CH2_CALIBRATION_TYPE");
        public final static Property Ch3CalibrationType = new Property(3, int.class, "ch3CalibrationType", false, "CH3_CALIBRATION_TYPE");
        public final static Property Ch4CalibrationType = new Property(4, int.class, "ch4CalibrationType", false, "CH4_CALIBRATION_TYPE");
        public final static Property Ch1SmallPartStandard = new Property(5, double.class, "ch1SmallPartStandard", false, "CH1_SMALL_PART_STANDARD");
        public final static Property Ch2SmallPartStandard = new Property(6, double.class, "ch2SmallPartStandard", false, "CH2_SMALL_PART_STANDARD");
        public final static Property Ch3SmallPartStandard = new Property(7, double.class, "ch3SmallPartStandard", false, "CH3_SMALL_PART_STANDARD");
        public final static Property Ch4SmallPartStandard = new Property(8, double.class, "ch4SmallPartStandard", false, "CH4_SMALL_PART_STANDARD");
        public final static Property Ch1BigPartStandard = new Property(9, double.class, "ch1BigPartStandard", false, "CH1_BIG_PART_STANDARD");
        public final static Property Ch2BigPartStandard = new Property(10, double.class, "ch2BigPartStandard", false, "CH2_BIG_PART_STANDARD");
        public final static Property Ch3BigPartStandard = new Property(11, double.class, "ch3BigPartStandard", false, "CH3_BIG_PART_STANDARD");
        public final static Property Ch4BigPartStandard = new Property(12, double.class, "ch4BigPartStandard", false, "CH4_BIG_PART_STANDARD");
        public final static Property Ch1UpperLimitRate = new Property(13, double.class, "ch1UpperLimitRate", false, "CH1_UPPER_LIMIT_RATE");
        public final static Property Ch2UpperLimitRate = new Property(14, double.class, "ch2UpperLimitRate", false, "CH2_UPPER_LIMIT_RATE");
        public final static Property Ch3UpperLimitRate = new Property(15, double.class, "ch3UpperLimitRate", false, "CH3_UPPER_LIMIT_RATE");
        public final static Property Ch4UpperLimitRate = new Property(16, double.class, "ch4UpperLimitRate", false, "CH4_UPPER_LIMIT_RATE");
        public final static Property Ch1LowerLimitRate = new Property(17, double.class, "ch1LowerLimitRate", false, "CH1_LOWER_LIMIT_RATE");
        public final static Property Ch2LowerLimitRate = new Property(18, double.class, "ch2LowerLimitRate", false, "CH2_LOWER_LIMIT_RATE");
        public final static Property Ch3LowerLimitRate = new Property(19, double.class, "ch3LowerLimitRate", false, "CH3_LOWER_LIMIT_RATE");
        public final static Property Ch4LowerLimitRate = new Property(20, double.class, "ch4LowerLimitRate", false, "CH4_LOWER_LIMIT_RATE");
        public final static Property Ch1CompensationValue = new Property(21, double.class, "ch1CompensationValue", false, "CH1_COMPENSATION_VALUE");
        public final static Property Ch2CompensationValue = new Property(22, double.class, "ch2CompensationValue", false, "CH2_COMPENSATION_VALUE");
        public final static Property Ch3CompensationValue = new Property(23, double.class, "ch3CompensationValue", false, "CH3_COMPENSATION_VALUE");
        public final static Property Ch4CompensationValue = new Property(24, double.class, "ch4CompensationValue", false, "CH4_COMPENSATION_VALUE");
        public final static Property Ch1KValue = new Property(25, double.class, "ch1KValue", false, "CH1_KVALUE");
        public final static Property Ch2KValue = new Property(26, double.class, "ch2KValue", false, "CH2_KVALUE");
        public final static Property Ch3KValue = new Property(27, double.class, "ch3KValue", false, "CH3_KVALUE");
        public final static Property Ch4KValue = new Property(28, double.class, "ch4KValue", false, "CH4_KVALUE");
    }


    public CalibrationBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CalibrationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CALIBRATION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: code_id
                "\"CH1_CALIBRATION_TYPE\" INTEGER NOT NULL ," + // 1: ch1CalibrationType
                "\"CH2_CALIBRATION_TYPE\" INTEGER NOT NULL ," + // 2: ch2CalibrationType
                "\"CH3_CALIBRATION_TYPE\" INTEGER NOT NULL ," + // 3: ch3CalibrationType
                "\"CH4_CALIBRATION_TYPE\" INTEGER NOT NULL ," + // 4: ch4CalibrationType
                "\"CH1_SMALL_PART_STANDARD\" REAL NOT NULL ," + // 5: ch1SmallPartStandard
                "\"CH2_SMALL_PART_STANDARD\" REAL NOT NULL ," + // 6: ch2SmallPartStandard
                "\"CH3_SMALL_PART_STANDARD\" REAL NOT NULL ," + // 7: ch3SmallPartStandard
                "\"CH4_SMALL_PART_STANDARD\" REAL NOT NULL ," + // 8: ch4SmallPartStandard
                "\"CH1_BIG_PART_STANDARD\" REAL NOT NULL ," + // 9: ch1BigPartStandard
                "\"CH2_BIG_PART_STANDARD\" REAL NOT NULL ," + // 10: ch2BigPartStandard
                "\"CH3_BIG_PART_STANDARD\" REAL NOT NULL ," + // 11: ch3BigPartStandard
                "\"CH4_BIG_PART_STANDARD\" REAL NOT NULL ," + // 12: ch4BigPartStandard
                "\"CH1_UPPER_LIMIT_RATE\" REAL NOT NULL ," + // 13: ch1UpperLimitRate
                "\"CH2_UPPER_LIMIT_RATE\" REAL NOT NULL ," + // 14: ch2UpperLimitRate
                "\"CH3_UPPER_LIMIT_RATE\" REAL NOT NULL ," + // 15: ch3UpperLimitRate
                "\"CH4_UPPER_LIMIT_RATE\" REAL NOT NULL ," + // 16: ch4UpperLimitRate
                "\"CH1_LOWER_LIMIT_RATE\" REAL NOT NULL ," + // 17: ch1LowerLimitRate
                "\"CH2_LOWER_LIMIT_RATE\" REAL NOT NULL ," + // 18: ch2LowerLimitRate
                "\"CH3_LOWER_LIMIT_RATE\" REAL NOT NULL ," + // 19: ch3LowerLimitRate
                "\"CH4_LOWER_LIMIT_RATE\" REAL NOT NULL ," + // 20: ch4LowerLimitRate
                "\"CH1_COMPENSATION_VALUE\" REAL NOT NULL ," + // 21: ch1CompensationValue
                "\"CH2_COMPENSATION_VALUE\" REAL NOT NULL ," + // 22: ch2CompensationValue
                "\"CH3_COMPENSATION_VALUE\" REAL NOT NULL ," + // 23: ch3CompensationValue
                "\"CH4_COMPENSATION_VALUE\" REAL NOT NULL ," + // 24: ch4CompensationValue
                "\"CH1_KVALUE\" REAL NOT NULL ," + // 25: ch1KValue
                "\"CH2_KVALUE\" REAL NOT NULL ," + // 26: ch2KValue
                "\"CH3_KVALUE\" REAL NOT NULL ," + // 27: ch3KValue
                "\"CH4_KVALUE\" REAL NOT NULL );"); // 28: ch4KValue
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CALIBRATION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CalibrationBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCode_id());
        stmt.bindLong(2, entity.getCh1CalibrationType());
        stmt.bindLong(3, entity.getCh2CalibrationType());
        stmt.bindLong(4, entity.getCh3CalibrationType());
        stmt.bindLong(5, entity.getCh4CalibrationType());
        stmt.bindDouble(6, entity.getCh1SmallPartStandard());
        stmt.bindDouble(7, entity.getCh2SmallPartStandard());
        stmt.bindDouble(8, entity.getCh3SmallPartStandard());
        stmt.bindDouble(9, entity.getCh4SmallPartStandard());
        stmt.bindDouble(10, entity.getCh1BigPartStandard());
        stmt.bindDouble(11, entity.getCh2BigPartStandard());
        stmt.bindDouble(12, entity.getCh3BigPartStandard());
        stmt.bindDouble(13, entity.getCh4BigPartStandard());
        stmt.bindDouble(14, entity.getCh1UpperLimitRate());
        stmt.bindDouble(15, entity.getCh2UpperLimitRate());
        stmt.bindDouble(16, entity.getCh3UpperLimitRate());
        stmt.bindDouble(17, entity.getCh4UpperLimitRate());
        stmt.bindDouble(18, entity.getCh1LowerLimitRate());
        stmt.bindDouble(19, entity.getCh2LowerLimitRate());
        stmt.bindDouble(20, entity.getCh3LowerLimitRate());
        stmt.bindDouble(21, entity.getCh4LowerLimitRate());
        stmt.bindDouble(22, entity.getCh1CompensationValue());
        stmt.bindDouble(23, entity.getCh2CompensationValue());
        stmt.bindDouble(24, entity.getCh3CompensationValue());
        stmt.bindDouble(25, entity.getCh4CompensationValue());
        stmt.bindDouble(26, entity.getCh1KValue());
        stmt.bindDouble(27, entity.getCh2KValue());
        stmt.bindDouble(28, entity.getCh3KValue());
        stmt.bindDouble(29, entity.getCh4KValue());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CalibrationBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCode_id());
        stmt.bindLong(2, entity.getCh1CalibrationType());
        stmt.bindLong(3, entity.getCh2CalibrationType());
        stmt.bindLong(4, entity.getCh3CalibrationType());
        stmt.bindLong(5, entity.getCh4CalibrationType());
        stmt.bindDouble(6, entity.getCh1SmallPartStandard());
        stmt.bindDouble(7, entity.getCh2SmallPartStandard());
        stmt.bindDouble(8, entity.getCh3SmallPartStandard());
        stmt.bindDouble(9, entity.getCh4SmallPartStandard());
        stmt.bindDouble(10, entity.getCh1BigPartStandard());
        stmt.bindDouble(11, entity.getCh2BigPartStandard());
        stmt.bindDouble(12, entity.getCh3BigPartStandard());
        stmt.bindDouble(13, entity.getCh4BigPartStandard());
        stmt.bindDouble(14, entity.getCh1UpperLimitRate());
        stmt.bindDouble(15, entity.getCh2UpperLimitRate());
        stmt.bindDouble(16, entity.getCh3UpperLimitRate());
        stmt.bindDouble(17, entity.getCh4UpperLimitRate());
        stmt.bindDouble(18, entity.getCh1LowerLimitRate());
        stmt.bindDouble(19, entity.getCh2LowerLimitRate());
        stmt.bindDouble(20, entity.getCh3LowerLimitRate());
        stmt.bindDouble(21, entity.getCh4LowerLimitRate());
        stmt.bindDouble(22, entity.getCh1CompensationValue());
        stmt.bindDouble(23, entity.getCh2CompensationValue());
        stmt.bindDouble(24, entity.getCh3CompensationValue());
        stmt.bindDouble(25, entity.getCh4CompensationValue());
        stmt.bindDouble(26, entity.getCh1KValue());
        stmt.bindDouble(27, entity.getCh2KValue());
        stmt.bindDouble(28, entity.getCh3KValue());
        stmt.bindDouble(29, entity.getCh4KValue());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public CalibrationBean readEntity(Cursor cursor, int offset) {
        CalibrationBean entity = new CalibrationBean( //
            cursor.getLong(offset + 0), // code_id
            cursor.getInt(offset + 1), // ch1CalibrationType
            cursor.getInt(offset + 2), // ch2CalibrationType
            cursor.getInt(offset + 3), // ch3CalibrationType
            cursor.getInt(offset + 4), // ch4CalibrationType
            cursor.getDouble(offset + 5), // ch1SmallPartStandard
            cursor.getDouble(offset + 6), // ch2SmallPartStandard
            cursor.getDouble(offset + 7), // ch3SmallPartStandard
            cursor.getDouble(offset + 8), // ch4SmallPartStandard
            cursor.getDouble(offset + 9), // ch1BigPartStandard
            cursor.getDouble(offset + 10), // ch2BigPartStandard
            cursor.getDouble(offset + 11), // ch3BigPartStandard
            cursor.getDouble(offset + 12), // ch4BigPartStandard
            cursor.getDouble(offset + 13), // ch1UpperLimitRate
            cursor.getDouble(offset + 14), // ch2UpperLimitRate
            cursor.getDouble(offset + 15), // ch3UpperLimitRate
            cursor.getDouble(offset + 16), // ch4UpperLimitRate
            cursor.getDouble(offset + 17), // ch1LowerLimitRate
            cursor.getDouble(offset + 18), // ch2LowerLimitRate
            cursor.getDouble(offset + 19), // ch3LowerLimitRate
            cursor.getDouble(offset + 20), // ch4LowerLimitRate
            cursor.getDouble(offset + 21), // ch1CompensationValue
            cursor.getDouble(offset + 22), // ch2CompensationValue
            cursor.getDouble(offset + 23), // ch3CompensationValue
            cursor.getDouble(offset + 24), // ch4CompensationValue
            cursor.getDouble(offset + 25), // ch1KValue
            cursor.getDouble(offset + 26), // ch2KValue
            cursor.getDouble(offset + 27), // ch3KValue
            cursor.getDouble(offset + 28) // ch4KValue
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CalibrationBean entity, int offset) {
        entity.setCode_id(cursor.getLong(offset + 0));
        entity.setCh1CalibrationType(cursor.getInt(offset + 1));
        entity.setCh2CalibrationType(cursor.getInt(offset + 2));
        entity.setCh3CalibrationType(cursor.getInt(offset + 3));
        entity.setCh4CalibrationType(cursor.getInt(offset + 4));
        entity.setCh1SmallPartStandard(cursor.getDouble(offset + 5));
        entity.setCh2SmallPartStandard(cursor.getDouble(offset + 6));
        entity.setCh3SmallPartStandard(cursor.getDouble(offset + 7));
        entity.setCh4SmallPartStandard(cursor.getDouble(offset + 8));
        entity.setCh1BigPartStandard(cursor.getDouble(offset + 9));
        entity.setCh2BigPartStandard(cursor.getDouble(offset + 10));
        entity.setCh3BigPartStandard(cursor.getDouble(offset + 11));
        entity.setCh4BigPartStandard(cursor.getDouble(offset + 12));
        entity.setCh1UpperLimitRate(cursor.getDouble(offset + 13));
        entity.setCh2UpperLimitRate(cursor.getDouble(offset + 14));
        entity.setCh3UpperLimitRate(cursor.getDouble(offset + 15));
        entity.setCh4UpperLimitRate(cursor.getDouble(offset + 16));
        entity.setCh1LowerLimitRate(cursor.getDouble(offset + 17));
        entity.setCh2LowerLimitRate(cursor.getDouble(offset + 18));
        entity.setCh3LowerLimitRate(cursor.getDouble(offset + 19));
        entity.setCh4LowerLimitRate(cursor.getDouble(offset + 20));
        entity.setCh1CompensationValue(cursor.getDouble(offset + 21));
        entity.setCh2CompensationValue(cursor.getDouble(offset + 22));
        entity.setCh3CompensationValue(cursor.getDouble(offset + 23));
        entity.setCh4CompensationValue(cursor.getDouble(offset + 24));
        entity.setCh1KValue(cursor.getDouble(offset + 25));
        entity.setCh2KValue(cursor.getDouble(offset + 26));
        entity.setCh3KValue(cursor.getDouble(offset + 27));
        entity.setCh4KValue(cursor.getDouble(offset + 28));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CalibrationBean entity, long rowId) {
        entity.setCode_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CalibrationBean entity) {
        if(entity != null) {
            return entity.getCode_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CalibrationBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
