package alauncher.cn.measuringtablet;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import alauncher.cn.measuringtablet.utils.JdbcUtil;

import static android.content.Context.ALARM_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    public final static long HEARTBEAT_SPAN = 5 * 60 * 1000;

    public static boolean isLogin = false;

    @Override
    public void onReceive(Context context, Intent i) {
        if (i.getAction().equals("repeating")) {
            // android.util.Log.d("wlDebug", "repeating...");
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.setAction("repeating");
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            am.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmReceiver.HEARTBEAT_SPAN, sender);
            /**/
            if (isLogin) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int id = JdbcUtil.selectEquipmentID(App.getDeviceInfo().getFactoryCode(), App.getDeviceInfo().getDeviceCode());
                            // android.util.Log.d("wlDebug", "id = " + id);
                            if (id != -1) {
                                JdbcUtil.insertDevcieStatus(id, 0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
