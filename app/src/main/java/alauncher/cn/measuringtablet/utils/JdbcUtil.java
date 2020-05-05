package alauncher.cn.measuringtablet.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ParameterBean2;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplatePicBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;

public class JdbcUtil {
    //url
    private static String url = null;
    //user
    private static String user = null;
    //password
    private static String password = null;
    //驱动程序类
    private static String driverClass = null;

    public static String IP = "47.98.58.40";

    /**
     * 获取连接方法
     */
    public static Connection getConnection() throws NoClassDefFoundError {
        try {
            // Connection conn = DriverManager.getConnection(url, user, password);
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection("jdbc:postgresql://" + getIP() + ":5432/NT_CLOUD",
                            "dfqtech", "dfqtech2016");
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIP() {
        return IP;
    }

    /**
     * 释放资源的方法
     */
    public static void close(Statement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    public static int addResult(String factory_code, String machine_code, int prog_id, String serial_number,
                                String result, String ng_reason, String operator, String operate_time) throws Exception {
        Connection con = getConnection();
        if (con == null) return -1;
        // String sql = "insert into aistu values('" + name + "'," + id + ",'" + age + "','" + email + "','" + tel + "','" + salary + "','" + riqi + "')";
        String sql = "insert into ntqc_result (factory_code,machine_code,prog_id,serial_number,result,ng_reason,operator,operate_time) "
                + "values('" + factory_code + "','" + machine_code + "','" + prog_id + "','" + serial_number + "','" + result + "','" + ng_reason + "','" + operator + "','" + operate_time + "')";
        //        + "VALUES ('TEFA', 'Paul002', '32', '28','OK','Not','ADMIN','2019-08-16 15:50:57' );";
        Statement stmt = con.createStatement();//获取statement
        int re = stmt.executeUpdate(sql);
        android.util.Log.d("wlDebug", "result = " + re);
        close(stmt, con);
        return re;
    }

    public static int selectDevice(String machine_code) {
        int count = 0;
        Connection con = getConnection();
        if (con == null) return -1;
        String sql = "select count(*) from ntqc_equipment where machine_code = '" + machine_code + "'";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int addDevice(String factory_code, String factory_name, String machine_code, String machine_name, String manufacturer, String rmk, String operator) {
        Connection con = getConnection();
        if (con == null) return -1;
        String sql = "insert into ntqc_equipment (factory_code,factory_name,machine_code,machine_name,manufacturer,manufacture_date,rmk,operator,operate_time) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, factory_code);
            pstmt.setString(2, factory_name);
            pstmt.setString(3, machine_code);
            pstmt.setString(4, machine_name);
            pstmt.setString(5, manufacturer);
            pstmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setString(7, rmk);
            pstmt.setString(8, operator);
            pstmt.setTimestamp(9, new java.sql.Timestamp(System.currentTimeMillis()));
            return pstmt.executeUpdate();//执行sql
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int updateDevice(String factory_code, String factory_name, String machine_code, String machine_name, String manufacturer, String rmk, String operator) {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "update ntqc_equipment set factory_code=?,factory_name=?,machine_name=?,manufacturer=?,rmk=?,operator=?,operate_time=? where machine_code=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, factory_code);
            pstmt.setString(2, factory_name);
            pstmt.setString(3, machine_name);
            pstmt.setString(4, manufacturer);
            pstmt.setString(5, rmk);
            pstmt.setString(6, operator);
            pstmt.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setString(8, machine_code);
            return pstmt.executeUpdate();//执行sql
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }


    public static int insertOrReplace(String factory_code, String factory_name, String machine_code, String machine_name, String manufacturer, String rmk, String operator) {
        if (selectDevice(machine_code) > 0) {
            updateDevice(factory_code, factory_name, machine_code, machine_name, manufacturer, rmk, operator);
        } else {
            addDevice(factory_code, factory_name, machine_code, machine_name, manufacturer, rmk, operator);
        }
        return 1;
    }


    public static int addResult2(String factory_code, String machine_code, int prog_id, String serial_number,
                                 final ResultBean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into ntqc_result (factory_code,machine_code,prog_id,serial_number,result,ng_reason,operator,operate_time) VALUES (?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        pstmt.setString(2, machine_code);
        pstmt.setInt(3, prog_id);
        pstmt.setString(4, serial_number);
        pstmt.setString(5, _bean.getResult());
        pstmt.setString(6, "");
        pstmt.setString(7, _bean.getHandlerAccout());
        pstmt.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
        pstmt.executeUpdate();//执行sql
        ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
        int autoIncKey = 0;
        if (rs.next()) {
            autoIncKey = rs.getInt(1);//取得ID
            android.util.Log.d("wlDebug", "result = " + autoIncKey);
        } else {
            // throw an exception from here
        }

        String result_detail_sql = "insert into ntqc_result_detail (result_id,name,m_value,r_value,g_value,e_value) VALUES (?,?,?,?,?,?);";
        PreparedStatement m1pstmt = conn.prepareStatement(result_detail_sql, Statement.RETURN_GENERATED_KEYS);
        m1pstmt.setInt(1, autoIncKey);
        m1pstmt.setString(2, "1");
        m1pstmt.setFloat(3, (float) _bean.getM1());
        m1pstmt.setFloat(4, 0);
        m1pstmt.setString(5, _bean.getM1_group());
        m1pstmt.setString(6, _bean.getEvent());
        m1pstmt.executeUpdate();

        PreparedStatement m2pstmt = conn.prepareStatement(result_detail_sql, Statement.RETURN_GENERATED_KEYS);
        m2pstmt.setInt(1, autoIncKey);
        m2pstmt.setString(2, "2");
        m2pstmt.setFloat(3, (float) _bean.getM2());
        m2pstmt.setFloat(4, 0);
        m2pstmt.setString(5, _bean.getM2_group());
        m2pstmt.setString(6, _bean.getEvent());
        m2pstmt.executeUpdate();

        PreparedStatement m3pstmt = conn.prepareStatement(result_detail_sql, Statement.RETURN_GENERATED_KEYS);
        m3pstmt.setInt(1, autoIncKey);
        m3pstmt.setString(2, "3");
        m3pstmt.setFloat(3, (float) _bean.getM3());
        m3pstmt.setFloat(4, 0);
        m3pstmt.setString(5, _bean.getM3_group());
        m3pstmt.setString(6, _bean.getEvent());
        m3pstmt.executeUpdate();

        PreparedStatement m4pstmt = conn.prepareStatement(result_detail_sql, Statement.RETURN_GENERATED_KEYS);
        m4pstmt.setInt(1, autoIncKey);
        m4pstmt.setString(2, "4");
        m4pstmt.setFloat(3, (float) _bean.getM4());
        m4pstmt.setFloat(4, 0);
        m4pstmt.setString(5, _bean.getM4_group());
        m4pstmt.setString(6, _bean.getEvent());
        m4pstmt.executeUpdate();

        m2pstmt.close();
        m3pstmt.close();
        m4pstmt.close();
        close(pstmt, conn);
        return 1;
    }


    public static int addTemplateResultBean(final TemplateResultBean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into template_result " +
                "(factory_code,device_code,code_id,logo_pic,title_list,sign_list,aql_list,rohs_List,header_left,header_mid,header_right,footer_left,footer_mid,footer_right,title,data_num,maximum_enable,minimum_enable,average_enable," +
                "range_enable,judge_enable,all_judge,timestamp,img,remarks,user_name,title_result_list,aql_result_list,rohs_result_list,value_indexs,upper_tolerance_values,lower_tolerance_values,nominal_values) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, _bean.getFactoryCode());
        pstmt.setString(2, _bean.getDeviceCode());
        pstmt.setInt(3, _bean.getCodeID());
        pstmt.setBytes(4, _bean.getLogoPic());
        pstmt.setString(5, StringConverter.convertToDatabaseValueS(_bean.getTitleList()));
        pstmt.setString(6, StringConverter.convertToDatabaseValueS(_bean.getSignList()));
        pstmt.setString(7, StringConverter.convertToDatabaseValueS(_bean.getAQLList()));
        pstmt.setString(8, StringConverter.convertToDatabaseValueS(_bean.getRoHSList()));
        pstmt.setString(9, _bean.getHeaderLeft());
        pstmt.setString(10, _bean.getHeaderMid());
        pstmt.setString(11, _bean.getHeaderRight());
        pstmt.setString(12, _bean.getFooterLeft());
        pstmt.setString(13, _bean.getFooterMid());
        pstmt.setString(14, _bean.getFooterRight());
        pstmt.setString(15, _bean.getTitle());
        pstmt.setInt(16, _bean.getDataNum());

        pstmt.setBoolean(17, _bean.getMaximumEnable());
        pstmt.setBoolean(18, _bean.getMinimumEnable());
        pstmt.setBoolean(19, _bean.getAverageEnable());
        pstmt.setBoolean(20, _bean.getRangeEnable());
        pstmt.setBoolean(21, _bean.getJudgeEnable());
        pstmt.setString(22, _bean.getAllJudge());

        pstmt.setTimestamp(23, new java.sql.Timestamp(System.currentTimeMillis()));
        pstmt.setBytes(24, _bean.getImg());
        pstmt.setString(25, _bean.getRemarks());
        pstmt.setString(26, _bean.getUser());

        pstmt.setString(27, StringConverter.convertToDatabaseValueS(_bean.getTitleResultList()));
        pstmt.setString(28, StringConverter.convertToDatabaseValueS(_bean.getAQLResultList()));
        pstmt.setString(29, StringConverter.convertToDatabaseValueS(_bean.getRoHSList()));
        pstmt.setString(30, StringConverter.convertToDatabaseValueS(_bean.getValueIndexs()));
        pstmt.setString(31, StringConverter.convertToDatabaseValueS(_bean.getUpperToleranceValues()));
        pstmt.setString(32, StringConverter.convertToDatabaseValueS(_bean.getLowerToleranceValues()));
        pstmt.setString(33, StringConverter.convertToDatabaseValueS(_bean.getNominalValues()));

        pstmt.executeUpdate();//执行sql
        ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
        int autoIncKey = 0;
        if (rs.next()) {
            autoIncKey = rs.getInt(1);//取得ID
            android.util.Log.d("wlDebug", "result = " + autoIncKey);
        } else {
            // throw an exception from here
        }
        close(pstmt, conn);
        return autoIncKey;
    }


    public static int addTemplatePic(TemplatePicBean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into template_pic (template_result_id,img) VALUES (?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setLong(1, _bean.getTemplateResultID());
        pstmt.setBytes(2, _bean.getImg());
        pstmt.executeUpdate();//执行sql
        ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
        int autoIncKey = 0;
        if (rs.next()) {
            autoIncKey = rs.getInt(1);//取得ID
            android.util.Log.d("wlDebug", "result = " + autoIncKey);
        } else {
            // throw an exception from here
        }
        close(pstmt, conn);
        return 1;
    }

    public static int addResult3(String factory_code, String machine_code, int prog_id, String serial_number,
                                 final ResultBean3 _bean, int templateResultID) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into ntqc_result (factory_code,machine_code,prog_id,serial_number,result,ng_reason,operator,operate_time,template_result_id) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        pstmt.setString(2, machine_code);
        pstmt.setInt(3, prog_id);
        pstmt.setString(4, serial_number);
        pstmt.setString(5, _bean.getResult());
        pstmt.setString(6, "");
        pstmt.setString(7, _bean.getHandlerAccout());
        pstmt.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
        pstmt.setLong(9, templateResultID);
        pstmt.executeUpdate();//执行sql
        ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
        int autoIncKey = 0;
        if (rs.next()) {
            autoIncKey = rs.getInt(1);//取得ID
            android.util.Log.d("wlDebug", "result = " + autoIncKey);
        } else {
            // throw an exception from here
        }

        for (int i = 0; i < _bean.getMValues().size(); i++) {
            String result_detail_sql = "insert into ntqc_result_detail (result_id,name,m_value,r_value,g_value,e_value,img) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement m1pstmt = conn.prepareStatement(result_detail_sql, Statement.RETURN_GENERATED_KEYS);
            m1pstmt.setInt(1, autoIncKey);
            m1pstmt.setString(2, "" + (i + 1));
            try {
                m1pstmt.setFloat(3, Float.valueOf(_bean.getMValues().get(i)));
            } catch (Exception e) {
                m1pstmt.close();
                continue;
            }
            // m1pstmt.setString(3, _bean.getMValues().get(i));
            m1pstmt.setFloat(4, 0);
            m1pstmt.setString(5, "- -");
            m1pstmt.setString(6, _bean.getEvent());
            m1pstmt.setBytes(7, FileUtils.image2byte(_bean.getMPicPaths().get(i)));
            m1pstmt.executeUpdate();
            m1pstmt.close();
        }
        close(pstmt, conn);
        return 1;
    }

    public static int addParamConfig(String factory_code, String machine_code, int prog_id, String prog_name, String param_key,
                                     String param_name, String type,
                                     float warning_up, float warning_low, String rmk, ParameterBean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into ntqc_param_config (factory_code,machine_code,prog_id," +
                "prog_name,param_key,param_name,type,nominal_value,lower_tolerance,upper_tolerance,warning_up,warning_low,rmk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";

//        String sql = "insert into ntqc_param_config (factory_code,machine_code,prog_id," +
//                "prog_name,param_key,param_name,type,nominal_value,lower_tolerance,upper_tolerance,warning_up,warning_low,rmk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        pstmt.setString(2, machine_code);
        pstmt.setInt(3, (int) App.getSetupBean().getCodeID());
        pstmt.setString(4, App.getCodeName());
        pstmt.setString(5, "M1");
        pstmt.setString(6, _bean.getM1_describe());
        pstmt.setString(7, type);
        pstmt.setFloat(8, Float.valueOf(String.valueOf(_bean.getM1_nominal_value())));
        pstmt.setFloat(9, (float) _bean.getM1_lower_tolerance_value());
        pstmt.setFloat(10, (float) _bean.getM1_upper_tolerance_value());
        pstmt.setFloat(11, warning_up);
        pstmt.setFloat(12, warning_low);
        pstmt.setString(13, rmk);
        pstmt.executeUpdate();//执行sql
//        ResultSet rs = pstmt.getGeneratedKeys(); //获取结果
//        int autoIncKey = 0;
//        if (rs.next()) {
//            autoIncKey = rs.getInt(1);//取得ID
//            android.util.Log.d("wlDebug", "result = " + autoIncKey);
//        } else {
//        }
        /*
        PreparedStatement pstmt2 = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt2.setString(1, factory_code);
        pstmt2.setString(2, machine_code);
        pstmt2.setInt(3, prog_id);
        pstmt2.setString(4, prog_name);
        pstmt2.setString(5, param_key);
        pstmt2.setString(6, param_name);
        pstmt2.setString(7, type);
        pstmt2.setFloat(8, (float) _bean.getM2_nominal_value());
        pstmt2.setFloat(9, (float) _bean.getM2_lower_tolerance_value());
        pstmt2.setFloat(10, (float) _bean.getM2_upper_tolerance_value());
        pstmt2.setFloat(11, warning_up);
        pstmt2.setFloat(12, warning_low);
        pstmt2.setString(13, rmk);
        pstmt2.executeUpdate();//执行sql


        PreparedStatement pstmt3 = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt3.setString(1, factory_code);
        pstmt3.setString(2, machine_code);
        pstmt3.setInt(3, prog_id);
        pstmt3.setString(4, prog_name);
        pstmt3.setString(5, param_key);
        pstmt3.setString(6, param_name);
        pstmt3.setString(7, type);
        pstmt3.setFloat(8, (float) _bean.getM3_nominal_value());
        pstmt3.setFloat(9, (float) _bean.getM3_lower_tolerance_value());
        pstmt3.setFloat(10, (float) _bean.getM3_upper_tolerance_value());
        pstmt3.setFloat(11, warning_up);
        pstmt3.setFloat(12, warning_low);
        pstmt3.setString(13, rmk);
        pstmt3.executeUpdate();//执行sql

        PreparedStatement pstmt4 = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt4.setString(1, factory_code);
        pstmt4.setString(2, machine_code);
        pstmt4.setInt(3, prog_id);
        pstmt4.setString(4, prog_name);
        pstmt4.setString(5, param_key);
        pstmt4.setString(6, param_name);
        pstmt4.setString(7, type);
        pstmt4.setFloat(8, (float) _bean.getM4_nominal_value());
        pstmt4.setFloat(9, (float) _bean.getM4_lower_tolerance_value());
        pstmt4.setFloat(10, (float) _bean.getM4_upper_tolerance_value());
        pstmt4.setFloat(11, warning_up);
        pstmt4.setFloat(12, warning_low);
        pstmt4.setString(13, rmk);
        pstmt4.executeUpdate();//执行sql
        */
        pstmt.setString(5, "M2");
        pstmt.setString(6, _bean.getM2_describe());
        pstmt.setFloat(8, (float) _bean.getM2_nominal_value());
        pstmt.setFloat(9, (float) _bean.getM2_lower_tolerance_value());
        pstmt.setFloat(10, (float) _bean.getM2_upper_tolerance_value());
        pstmt.executeUpdate();//执行sql

        pstmt.setString(5, "M3");
        pstmt.setString(6, _bean.getM3_describe());
        pstmt.setFloat(8, (float) _bean.getM3_nominal_value());
        pstmt.setFloat(9, (float) _bean.getM3_lower_tolerance_value());
        pstmt.setFloat(10, (float) _bean.getM3_upper_tolerance_value());
        pstmt.executeUpdate();//执行sql

        pstmt.setString(5, "M4");
        pstmt.setString(6, _bean.getM4_describe());
        pstmt.setFloat(8, (float) _bean.getM4_nominal_value());
        pstmt.setFloat(9, (float) _bean.getM4_lower_tolerance_value());
        pstmt.setFloat(10, (float) _bean.getM4_upper_tolerance_value());
        pstmt.executeUpdate();//执行sql
        return 1;
    }

    public static int updateParamConfig(String factory_code, String machine_code, int prog_id, String prog_name, String param_key,
                                        String param_name, String type,
                                        float warning_up, float warning_low, String rmk, ParameterBean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "update ntqc_param_config set factory_code=?,prog_name=?,param_name=?,type=?,nominal_value=?,lower_tolerance=?,upper_tolerance=?,warning_up=?,warning_low=?,rmk=? where machine_code=? and prog_id=? and param_key=?";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        //pstmt.setString(2, machine_code);
        // pstmt.setInt(3, App.getSetupBean().getCodeID());
        pstmt.setString(2, App.getCodeName());
        // pstmt.setString(5, "M1");
        pstmt.setString(3, _bean.getM1_describe());
        pstmt.setString(4, type);
        pstmt.setFloat(5, Float.valueOf(String.valueOf(_bean.getM1_nominal_value())));
        pstmt.setFloat(6, (float) _bean.getM1_lower_tolerance_value());
        pstmt.setFloat(7, (float) _bean.getM1_upper_tolerance_value());
        pstmt.setFloat(8, warning_up);
        pstmt.setFloat(9, warning_low);
        pstmt.setString(10, rmk);

        pstmt.setString(11, machine_code);
        pstmt.setInt(12, App.getSetupBean().getCodeID());
        pstmt.setString(13, "M1");
        pstmt.executeUpdate();//执行sql

        // Code M2
        pstmt.setString(3, _bean.getM2_describe());
        pstmt.setFloat(5, Float.valueOf(String.valueOf(_bean.getM2_nominal_value())));
        pstmt.setFloat(6, (float) _bean.getM2_lower_tolerance_value());
        pstmt.setFloat(7, (float) _bean.getM2_upper_tolerance_value());
        pstmt.setString(13, "M2");
        pstmt.executeUpdate();//执行sql

        // Code M3
        pstmt.setString(3, _bean.getM3_describe());
        pstmt.setFloat(5, Float.valueOf(String.valueOf(_bean.getM3_nominal_value())));
        pstmt.setFloat(6, (float) _bean.getM3_lower_tolerance_value());
        pstmt.setFloat(7, (float) _bean.getM3_upper_tolerance_value());
        pstmt.setString(13, "M3");
        pstmt.executeUpdate();//执行sql

        // Code M4
        pstmt.setString(3, _bean.getM4_describe());
        pstmt.setFloat(5, Float.valueOf(String.valueOf(_bean.getM4_nominal_value())));
        pstmt.setFloat(6, (float) _bean.getM4_lower_tolerance_value());
        pstmt.setFloat(7, (float) _bean.getM4_upper_tolerance_value());
        pstmt.setString(13, "M4");
        pstmt.executeUpdate();//执行sql
        return 1;
    }


    public static int updateParamConfigOnce(String factory_code, String machine_code, int prog_id, String prog_name, String param_key,
                                            String param_name, String type,
                                            float warning_up, float warning_low, String rmk, Parameter2Bean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "update ntqc_param_config set factory_code=?,prog_name=?,param_name=?,type=?,nominal_value=?,lower_tolerance=?,upper_tolerance=?,warning_up=?,warning_low=?,rmk=? where machine_code=? and prog_id=? and param_key=?";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        //pstmt.setString(2, machine_code);
        // pstmt.setInt(3, App.getSetupBean().getCodeID());
        pstmt.setString(2, App.getCodeName());
        // pstmt.setString(5, "M1");
        pstmt.setString(3, _bean.getDescribe().substring(0, _bean.getDescribe().indexOf("\n")));
        pstmt.setString(4, type);
        pstmt.setFloat(5, Float.valueOf(String.valueOf(_bean.getNominal_value())));
        pstmt.setFloat(6, (float) _bean.getLower_tolerance_value());
        pstmt.setFloat(7, (float) _bean.getUpper_tolerance_value());
        pstmt.setFloat(8, warning_up);
        pstmt.setFloat(9, warning_low);
        pstmt.setString(10, rmk);

        pstmt.setString(11, machine_code);
        pstmt.setInt(12, App.getSetupBean().getCodeID());
        pstmt.setString(13, "M" + _bean.getIndex());
        pstmt.executeUpdate();//执行sql
        return 1;
    }

    public static int addParamConfigOnce(String factory_code, String machine_code, int prog_id, String prog_name, String param_key,
                                         String param_name, String type,
                                         float warning_up, float warning_low, String rmk, Parameter2Bean _bean) throws Exception {
        Connection conn = getConnection();
        if (conn == null) return -1;

        String sql = "insert into ntqc_param_config (factory_code,machine_code,prog_id," +
                "prog_name,param_key,param_name,type,nominal_value,lower_tolerance,upper_tolerance,warning_up,warning_low,rmk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//传入参数：Statement.RETURN_GENERATED_KEYS
        pstmt.setString(1, factory_code);
        pstmt.setString(2, machine_code);
        pstmt.setInt(3, App.getSetupBean().getCodeID());
        pstmt.setString(4, App.getCodeName());
        pstmt.setString(5, "M" + _bean.getIndex());
        pstmt.setString(6, _bean.getDescribe().substring(0, _bean.getDescribe().indexOf("\n")));
        pstmt.setString(7, type);
        pstmt.setFloat(8, Float.valueOf(String.valueOf(_bean.getNominal_value())));
        pstmt.setFloat(9, (float) _bean.getLower_tolerance_value());
        pstmt.setFloat(10, (float) _bean.getUpper_tolerance_value());
        pstmt.setFloat(11, warning_up);
        pstmt.setFloat(12, warning_low);
        pstmt.setString(13, rmk);
        pstmt.executeUpdate();//执行sql
        return 1;
    }

    public static int selectParamConfig(String machine_code, int prog_id, String param_key) {
        int count = 0;
        Connection con = getConnection();
        if (con == null) return -1;
        String sql = "select count(*) from ntqc_param_config where machine_code = '" + machine_code + "' and " + "prog_id = '" + prog_id + "' and param_key = '" + param_key + "'";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int deleteParam2s(String factory_code, String machine_code) throws SQLException {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "delete from ntqc_param_config where machine_code=? and factory_code=?";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, machine_code);
        pstmt.setString(2, factory_code);
        int ret = pstmt.executeUpdate();//执行sql
        pstmt.close();
        conn.close();
        return ret;
    }

    public static int addParam2Config(String factory_code, String machine_code, List<ParameterBean2> list) throws SQLException {
        Connection conn = getConnection();
        if (conn == null) return -1;
        String sql = "insert into ntqc_param_config (factory_code,machine_code,prog_id," +
                "prog_name,param_key,param_name,type,nominal_value,lower_tolerance,upper_tolerance,warning_up,warning_low,rmk) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        for (int i = 0; i < list.size(); i++) {
            ParameterBean2 _bean2 = list.get(i);
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, factory_code);
            pstmt.setString(2, machine_code);
            pstmt.setInt(3, App.getSetupBean().getCodeID());
            pstmt.setString(4, App.getCodeName());
            pstmt.setString(5, "M" + (_bean2.getSequenceNumber() + 1));
            pstmt.setString(6, _bean2.getDescribe());
            pstmt.setString(7, "0");
            pstmt.setFloat(8, (float) _bean2.getNominalValue());
            pstmt.setFloat(9, (float) _bean2.getLowerToleranceValue());
            pstmt.setFloat(10, (float) _bean2.getUpperToleranceValue());
            pstmt.setFloat(11, 0);
            pstmt.setFloat(12, 0);
            pstmt.setString(13, "rmk");
            pstmt.executeUpdate();//执行sql
            pstmt.close();
        }
        conn.close();
        return 1;
    }
}
