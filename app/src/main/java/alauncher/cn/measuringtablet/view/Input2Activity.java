package alauncher.cn.measuringtablet.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.Browser;
import android.text.Layout;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.MainActivity;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.pdf.PDFUtils;
import alauncher.cn.measuringtablet.utils.ColorConstants;
import alauncher.cn.measuringtablet.widget.BorderEditView;
import alauncher.cn.measuringtablet.widget.BorderTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class Input2Activity extends BaseOActivity {

    @BindView(R.id.input_main_layout)
    public ViewGroup mainLayout;

    public TemplateBean mTemplateBean;

    public TemplateResultBean mTemplateResultBean;

    private List<Parameter2Bean> mParameter2Beans;

    private List<ResultBean3> mResultBean3s = new ArrayList<>();

    // 表头;
    private List<EditText> titleEdts = new ArrayList<>();

    // aql
    private List<EditText> aqlEdts = new ArrayList<>();

    // aql
    private List<EditText> roshEdts = new ArrayList<>();

    // result;
    private List<List<EditText>> results = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_input2);
    }

    @Override
    protected void initView() {

        mTemplateBean = App.getDaoSession().getTemplateBeanDao().load((long) App.getSetupBean().getCodeID());
        mParameter2Beans = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();

        for (int i = 0; i < 5; i++) {
            results.add(new ArrayList<>());
        }

        // 标题 + 签名栏;
        LinearLayout _layout = new LinearLayout(this);
        _layout.setOrientation(LinearLayout.HORIZONTAL);
        _layout.addView(getInfoTV(mTemplateBean.getTitle(), ColorConstants.titleColor), getLayoutParams(0, 2, 10));
        for (int i = 0; i < mTemplateBean.getSignList().size(); i++) {
            LinearLayout signLayout = new LinearLayout(this);
            signLayout.setOrientation(LinearLayout.VERTICAL);
            signLayout.addView(getInfoTV(mTemplateBean.getSignList().get(i), ColorConstants.titleColor), getItemVLayoutParams(1, 2));
            signLayout.addView(getInfoTV("", Color.WHITE), getItemVLayoutParams(1, 2));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    (int) getResources().getDimension(R.dimen.item_height) * 2);
            params.weight = 2;
            _layout.addView(signLayout, params);
        }
        mainLayout.addView(_layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2, 1));

        // 表头;
        // 每行显示个数;
        int colSize = 4;
        int colTitleSize = (int) Math.ceil((double) mTemplateBean.getTitleList().size() / (double) colSize);
        // 绘制标题栏;
        for (int i = 0; i < colTitleSize; i++) {
            LinearLayout __layout = new LinearLayout(this);
            for (int j = 0; j < colSize; j++) {
                __layout.addView(getInfoTV((i * 4 + j) < mTemplateBean.getTitleList().size() ?
                        mTemplateBean.getTitleList().get(i * 4 + j) : "", ColorConstants.titleColor), getItemLayoutParams(1, 1));
                EditText _edt = getInputEditView(false);
                __layout.addView(_edt, getItemLayoutParams(1, 1));
                if (titleEdts.size() < mTemplateBean.getTitleList().size()) titleEdts.add(_edt);
            }
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
        }

        // 添加工件图;
        FrameLayout imgLayout = new FrameLayout(this);
        mainLayout.addView(imgLayout, getItemVLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
        imgLayout.setBackgroundResource(R.drawable.workspice);

        // 秀的操作来了，真是可怕;
        //每页显示的记录数
        int pageSize = 3;
        //页数
        int pageSum = (int) Math.ceil((double) mParameter2Beans.size() / (double) pageSize);

        for (int i = 0; i < pageSum; i++) {

            Parameter2Bean rol1Bean = i * 3 + 0 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 0) : null;
            Parameter2Bean rol2Bean = i * 3 + 1 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 1) : null;
            Parameter2Bean rol3Bean = i * 3 + 2 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 2) : null;
            // 绘制数据列第一行
            LinearLayout __layout = new LinearLayout(this);
            __layout.addView(getInfoTV("记号", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            __layout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制上限值;
            LinearLayout upperLayout = new LinearLayout(this);
            upperLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            upperLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(upperLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制下限值;
            LinearLayout lowerLayout = new LinearLayout(this);
            lowerLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            lowerLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            mainLayout.addView(lowerLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));


            // 绘制中间值;
            LinearLayout nominalLayout = new LinearLayout(this);
            nominalLayout.addView(getInfoTV("中间值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            nominalLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(nominalLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            for (int j = 0; j < 5; j++) {
                LinearLayout dataLayout = new LinearLayout(this);
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)), ColorConstants.dataHeader), getItemLayoutParams(1, 1));
                EditText _edt1 = getInputEditView(true);
                dataLayout.addView(_edt1, getItemLayoutParams(2, 1));
                if (results.get(j).size() < mParameter2Beans.size()) results.get(j).add(_edt1);

                dataLayout.addView(getInputEditView(true), getItemLayoutParams(3, 1));

                EditText _edt2 = getInputEditView(true);
                dataLayout.addView(_edt2, getItemLayoutParams(2, 1));
                if (results.get(j).size() < mParameter2Beans.size()) results.get(j).add(_edt2);

                dataLayout.addView(getInputEditView(true), getItemLayoutParams(3, 1));

                EditText _edt3 = getInputEditView(true);
                dataLayout.addView(_edt3, getItemLayoutParams(2, 1));
                if (results.get(j).size() < mParameter2Beans.size()) results.get(j).add(_edt3);

                dataLayout.addView(getInputEditView(true), getItemLayoutParams(3, 1));
                mainLayout.addView(dataLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }

            /*
            // 数据；
            for (int j = 0; j < mResultBean3s.size(); j++) {
                String value1 = i * 3 + 0 <= (mResultBean3s.get(j).getMValues().size() - 1) ? mResultBean3s.get(j).getMValues().get(i * 3 + 0) : " ";
                String path1 = i * 3 + 0 <= (mResultBean3s.get(j).getMPicPaths().size() - 1) ? mResultBean3s.get(j).getMPicPaths().get(i * 3 + 0) : " ";
                String value2 = i * 3 + 1 <= (mResultBean3s.get(j).getMValues().size() - 1) ? mResultBean3s.get(j).getMValues().get(i * 3 + 1) : " ";
                String path2 = i * 3 + 1 <= (mResultBean3s.get(j).getMPicPaths().size() - 1) ? mResultBean3s.get(j).getMPicPaths().get(i * 3 + 1) : " ";
                String value3 = i * 3 + 2 <= (mResultBean3s.get(j).getMValues().size() - 1) ? mResultBean3s.get(j).getMValues().get(i * 3 + 2) : " ";
                String path3 = i * 3 + 2 <= (mResultBean3s.get(j).getMPicPaths().size() - 1) ? mResultBean3s.get(j).getMPicPaths().get(i * 3 + 2) : " ";
                table.addCell(getDataCell(String.valueOf((j + 1)), 1, 1, dataHeader));
                table.addCell(getDataCell(value1, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                table.addCell(getDataCell(path1, 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                table.addCell(getDataCell(value2, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                table.addCell(getDataCell(path2, 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                table.addCell(getDataCell(value3, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                table.addCell(getDataCell(path3, 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
            }

            // 最大值；
            table.addCell(getDataCell("最大值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(i * 3 + 0 < _maxs.size() ?
                    String.valueOf(_maxs.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 1 < _maxs.size() ?
                    String.valueOf(_maxs.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 2 < _maxs.size() ?
                    String.valueOf(_maxs.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));

            // 最小值；
            table.addCell(getDataCell("最小值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(i * 3 + 0 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 1 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 2 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));

            // 判定;
            table.addCell(getDataCell("判定", 1, 1, dataTitleColor));
            String judge1 = i * 3 + 0 < _results.size() ?
                    String.valueOf(_results.get(i * 3 + 0)) : " ";
            String judge2 = i * 3 + 1 < _results.size() ?
                    String.valueOf(_results.get(i * 3 + 1)) : " ";
            String judge3 = i * 3 + 2 < _results.size() ?
                    String.valueOf(_results.get(i * 3 + 2)) : " ";
            table.addCell(getDataCell(judge1, 1, 5, judge1.equals("OK") ? dataOKColor : dataNGColor));
            table.addCell(getDataCell(judge2, 1, 5, judge2.equals("OK") ? dataOKColor : dataNGColor));
            table.addCell(getDataCell(judge3, 1, 5, judge3.equals("OK") ? dataOKColor : dataNGColor));
             */
        }

        int bottomRow = Math.max(Math.max(mTemplateBean.getAQLList().size(), mTemplateBean.getRoHSList().size()), 5);

        LinearLayout bottomLayout = new LinearLayout(this);
        bottomLayout.addView(getInfoTV("外观检查一般I级AQL=0.15", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        // aql title
        LinearLayout aqlLayout = new LinearLayout(this);
        aqlLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            aqlLayout.addView(getInfoTV(mTemplateBean.getAQLList().size() > j ? mTemplateBean.getAQLList().get(j) : " ", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
        }
        bottomLayout.addView(aqlLayout, getItemLayoutParams(1, 1 * bottomRow));
        // aql result
        LinearLayout aqlResultLayout = new LinearLayout(this);
        aqlResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            EditText _edt = getInputEditView(false);
            aqlResultLayout.addView(_edt, getItemVLayoutParams(1, 1));
            if (aqlEdts.size() < mTemplateBean.getAQLList().size()) aqlEdts.add(_edt);
        }
        bottomLayout.addView(aqlResultLayout, getItemLayoutParams(1, 1 * bottomRow));

        bottomLayout.addView(getInfoTV("RoHS相关: ", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        // RoSH;
        LinearLayout roshLayout = new LinearLayout(this);
        roshLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            roshLayout.addView(getInfoTV(mTemplateBean.getRoHSList().size() > j ? mTemplateBean.getRoHSList().get(j) : " ", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
        }
        bottomLayout.addView(roshLayout, getItemLayoutParams(1, 1 * bottomRow));


        // RoSH Result;
        LinearLayout roshResultLayout = new LinearLayout(this);
        roshResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            EditText _edt = getInputEditView(false);
            roshResultLayout.addView(_edt, getItemVLayoutParams(1, 1));
            if (roshEdts.size() < mTemplateBean.getRoHSList().size()) roshEdts.add(_edt);
        }
        bottomLayout.addView(roshResultLayout, getItemLayoutParams(1, 1 * bottomRow));
        // Judge Result;
        LinearLayout judgeResultLayout = new LinearLayout(this);
        judgeResultLayout.setOrientation(LinearLayout.VERTICAL);
        judgeResultLayout.addView(getInfoTV("综合判断", ColorConstants.titleColor), getItemVLayoutParams(4, 1 * (bottomRow - 2)));
        judgeResultLayout.addView(getInfoTV("合格", ColorConstants.titleColor), getItemVLayoutParams(4, 1 * 2));
        bottomLayout.addView(judgeResultLayout, getItemLayoutParams(1, 1 * bottomRow));

        mainLayout.addView(bottomLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1 * bottomRow, 1));
    }

    public LinearLayout.LayoutParams getLayoutParams(int width, double row, int weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
                (int) (getResources().getDimension(R.dimen.item_height) * row));
        params.weight = weight;
        return params;
    }

    public LinearLayout.LayoutParams getItemLayoutParams(int weight, double col) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                (int) (getResources().getDimension(R.dimen.item_height) * col));
        params.weight = weight;
        return params;
    }

    public LinearLayout.LayoutParams getItemVLayoutParams(int weight, double col) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (getResources().getDimension(R.dimen.item_height) * col));
        params.weight = weight;
        return params;
    }

    public TextView getInfoTV(String msg, int color) {
        TextView tv = new BorderTextView(this);
        tv.setMaxLines(1);
        tv.setText(msg);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(color);
        return tv;
    }

    public EditText getInputEditView(boolean numOnly) {
        EditText et = new BorderEditView(this);
        et.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        et.setGravity(Gravity.CENTER);
        et.setBackground(null);
        et.setMaxLines(1);
        if (numOnly) {
            DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false, true);
            et.setKeyListener(numericOnlyListener);
            et.setText("1");
        } else {
            et.setText("测试");
        }
        return et;
    }

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        if (getIsEmpty()) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(Input2Activity.this);
            normalDialog.setIcon(R.drawable.add_circle);
            normalDialog.setTitle("无法保存");
            normalDialog.setMessage("还有测量值未输入，无法保存。");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //...To-do
                            // doSave();
                        }
                    });
            normalDialog.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            // 显示
            normalDialog.show();
        } else {
            // doSave();

            new ExportedTask().execute();
        }
    }

    private boolean getIsEmpty() {
        for (int i = 0; i < 5; i++) {
            for (EditText edt : results.get(i)) {
                if (edt.getText().toString().equals("")) return true;
            }
        }
        return false;
    }

    public class ExportedTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        private String path = Environment.getExternalStorageDirectory() + "/ETGate/";

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Input2Activity.this);
            dialog.setTitle("导出");
            dialog.setMessage("正在导出数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作
//            try {
//                Thread.sleep(5 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(Input2Activity.this.getResources(), R.drawable.workspice);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] img = baos.toByteArray();

                mTemplateResultBean = new TemplateResultBean();
                List<String> titleLists = new ArrayList<>();
                for (EditText edt : titleEdts) {
                    titleLists.add(edt.getText().toString().trim());
                }
                mTemplateResultBean.setTitleList(titleLists);

                List<String> aqlLists = new ArrayList<>();
                for (EditText edt : aqlEdts) {
                    aqlLists.add(edt.getText().toString().trim());
                }
                mTemplateResultBean.setAQLList(aqlLists);

                List<String> roshLists = new ArrayList<>();
                for (EditText edt : roshEdts) {
                    roshLists.add(edt.getText().toString().trim());
                }
                mTemplateResultBean.setRoHSList(roshLists);

                mResultBean3s.clear();
                for (int i = 0; i < results.size(); i++) {
                    ResultBean3 _bean = new ResultBean3();
                    ArrayList<String> values = new ArrayList<>();
                    ArrayList<String> picPaths = new ArrayList<>();
                    for (EditText edt : results.get(i)) {
                        values.add(edt.getText().toString().trim());
                    }
                    _bean.setMValues(values);
                    _bean.setMPicPaths(picPaths);
                    _bean.setHandlerAccout("hhb");
                    _bean.setResult("OK");
                    _bean.setWorkid_extra("");
                    mResultBean3s.add(_bean);
                    android.util.Log.d("wlDebug",_bean.toString());
                }

                File file = new File(PDFUtils.DEST);
                if (file.exists()) file.delete();
                file.getParentFile().mkdirs();
                PDFUtils.createNTTable(mTemplateBean, mTemplateResultBean, mParameter2Beans, mResultBean3s, img);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "后台任务执行完毕";
        }

        /*这个函数在doInBackground调用publishProgress(int i)时触发，虽然调用时只有一个参数
         但是这里取到的是一个数组,所以要用progesss[0]来取值
         第n个参数就用progress[n]来取值   */
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
        }

        /*doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
        这里的result就是上面doInBackground执行后的返回值，所以这里是"后台任务执行完毕"  */
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            openPDFInNative(Input2Activity.this, PDFUtils.DEST);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    public void openPDFInBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("error", "Activity was not found for intent, " + intent.toString());
        }
    }

    public void openPDFInNative(Context context, String FILE_NAME) {
        File file = new File(context.getExternalCacheDir(), FILE_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/pdf");
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.w("URLSpan", "Activity was not found for intent, " + intent.toString());
        }
    }
}
