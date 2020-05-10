package alauncher.cn.measuringtablet.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class BackupTask extends AsyncTask<String, Void, String> {
    private static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restroeDatabase";
    private Context mContext;
    private boolean isBackup;
    private BackupInterface mInterface;

    public BackupTask(Context context, BackupInterface _interface, boolean isBackup) {
        this.mContext = context;
        this.isBackup = isBackup;
        mInterface = _interface;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub

        // 获得正在使用的数据库路径，我的是 sdcard 目录下的 /dlion/db_dlion.db
        // 默认路径是 /data/data/(包名)/databases/*.db
        File dbFile = mContext.getDatabasePath("mi.db");
        File exportDir = new File(Environment.getExternalStorageDirectory(),
                "NTBackup");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        Log.d("backup", dbFile.getAbsolutePath());

        File backup = new File(exportDir, dbFile.getName());

        Log.d("backup", backup.getAbsolutePath());

        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                String serial = android.os.Build.SERIAL;
                backup = new File(exportDir, "mi" + DateUtils.getFileDate(System.currentTimeMillis()) + "_" + serial + ".db");
                backup.createNewFile();
                fileCopy(dbFile, backup);
                return backup.getAbsolutePath();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return null;
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                File shmFile = new File(dbFile.getAbsoluteFile() + "-shm");
                Log.d("wlDebug", "shmFile path = " + shmFile.getAbsolutePath());
                if (shmFile.isFile() && shmFile.exists()) {
                    Log.d("wlDebug", "shmFile Delete");
                    shmFile.delete();
                }
                File walFile = new File(dbFile.getAbsoluteFile() + "-wal");
                Log.d("wlDebug", "walFile path = " + walFile.getAbsolutePath());
                if (walFile.isFile() && walFile.exists()) {
                    Log.d("wlDebug", "walFile Delete");
                    walFile.delete();
                }
                File journalFile = new File(dbFile.getAbsoluteFile() + "-journal");
                Log.d("wlDebug", "journalFile path = " + journalFile.getAbsolutePath());
                if (journalFile.isFile() && journalFile.exists()) {
                    Log.d("wlDebug", "journalFile Delete");
                    journalFile.delete();
                }
                backup = new File(params[1]);
                Log.d("wlDebug", "base path = " + backup.getAbsolutePath());
                Log.d("wlDebug", "to path = " + dbFile.getAbsolutePath());
                fileCopy(backup, dbFile);
                return backup.getAbsolutePath();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private void fileCopy(File dbFile, File backup) throws IOException {
        // TODO Auto-generated method stub
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mInterface.onPreExecute(isBackup ? "导出中." : "导入中.");
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        mInterface.onPostExecute(isBackup, str);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface BackupInterface {
        void onPreExecute(String tips);

        void onPostExecute(boolean isBackup, String str);
    }
}
