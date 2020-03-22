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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.provider.MediaStore;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.ParameterBean2;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;
import alauncher.cn.measuringtablet.database.greenDao.db.ParameterBean2Dao;
import alauncher.cn.measuringtablet.pdf.PDFUtils;
import alauncher.cn.measuringtablet.utils.ColorConstants;
import alauncher.cn.measuringtablet.utils.DateUtils;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.widget.BorderEditView;
import alauncher.cn.measuringtablet.widget.BorderImageView;
import alauncher.cn.measuringtablet.widget.BorderTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class Input2Activity extends BaseOActivity {

    @BindView(R.id.input_main_layout)
    public ViewGroup mainLayout;

    public TemplateBean mTemplateBean;

    public TemplateResultBean mTemplateResultBean;

    // private List<Parameter2Bean> mParameterBean2s;

    private List<ParameterBean2> mParameterBean2s;

    private List<ResultBean3> mResultBean3s = new ArrayList<>();

    // 表头;
    private List<EditText> titleEdts = new ArrayList<>();

    // aql
    // private List<EditText> aqlEdts = new ArrayList<>();

    // aql
    private List<Object> aqlObjects = new ArrayList<>();

    // rosh
    private List<EditText> roshEdts = new ArrayList<>();

    // result;
    private List<List<EditText>> results = new ArrayList<>();

    // resultImg;
    private List<List<ImageView>> resultImgs = new ArrayList<>();

    // max
    private List<TextView> maxEdts = new ArrayList<>();

    // min
    private List<TextView> minEdts = new ArrayList<>();

    // avg
    private List<TextView> avgEdts = new ArrayList<>();

    // range
    private List<TextView> rangeEdts = new ArrayList<>();

    // judge
    private List<TextView> judgeEdts = new ArrayList<>();

    private DeviceInfoBean mDeviceInfoBean;

    // CodeBean;
    private CodeBean mCodeBean;

    private TextView allResultTV;

    private List<Double> maxs = new ArrayList<>();
    private List<Double> mins = new ArrayList<>();
    private List<Double> avgs = new ArrayList<>();
    private List<Double> ranges = new ArrayList<>();
    private List<String> judges = new ArrayList<>();
    private String allJudge = "OK";

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
        // mParameterBean2s = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        mParameterBean2s = App.getDaoSession().getParameterBean2Dao().queryBuilder()
                .where(ParameterBean2Dao.Properties.CodeID.eq(App.getSetupBean().getCodeID()), ParameterBean2Dao.Properties.Enable.eq(true))
                .orderAsc(ParameterBean2Dao.Properties.SequenceNumber).list();
        mCodeBean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
        mDeviceInfoBean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);

        for (int i = 0; i < mTemplateBean.getDataNum(); i++) {
            results.add(new ArrayList<>());
            resultImgs.add(new ArrayList<>());
            maxs.add(Double.valueOf(-100000));
            mins.add(Double.valueOf(1000000));
            avgs.add(Double.valueOf(1000000));
            ranges.add(Double.valueOf(1000000));
            judges.add("OK");
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
                if (titleEdts.size() < mTemplateBean.getTitleList().size()) {
                    EditText _edt = getInputEditView(false);
                    __layout.addView(_edt, getItemLayoutParams(1, 1));
                    titleEdts.add(_edt);
                } else {
                    __layout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(1, 1));
                }
            }
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
        }

        // 添加工件图;
        FrameLayout imgLayout = new FrameLayout(this);
        mainLayout.addView(imgLayout, getItemVLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));

        imgLayout.setBackgroundResource(R.drawable.workspice);
        byte[] _pic = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID()).getWorkpiecePic();
        if (_pic != null) {
            Bitmap map = BitmapFactory.decodeByteArray(_pic, 0, _pic.length);
            imgLayout.setBackground(new BitmapDrawable(map));
        } else {
            imgLayout.setBackgroundResource(R.drawable.workspice);
        }

        // 秀的操作来了，真是可怕;
        //每页显示的记录数
        int pageSize = 3;
        //页数
        int pageSum = (int) Math.ceil((double) mParameterBean2s.size() / (double) pageSize);

        for (int i = 0; i < pageSum; i++) {

            ParameterBean2 rol1Bean = i * 3 + 0 <= mParameterBean2s.size() - 1 ? mParameterBean2s.get(i * 3 + 0) : null;
            ParameterBean2 rol2Bean = i * 3 + 1 <= mParameterBean2s.size() - 1 ? mParameterBean2s.get(i * 3 + 1) : null;
            ParameterBean2 rol3Bean = i * 3 + 2 <= mParameterBean2s.size() - 1 ? mParameterBean2s.get(i * 3 + 2) : null;
            // 绘制数据列第一行
            LinearLayout __layout = new LinearLayout(this);
            __layout.addView(getInfoTV("记号", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            __layout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getSequenceNumber()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getSequenceNumber()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getSequenceNumber()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制上限值;
            LinearLayout upperLayout = new LinearLayout(this);
            upperLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            upperLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominalValue() + rol1Bean.getUpperToleranceValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominalValue() + rol1Bean.getUpperToleranceValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominalValue() + rol1Bean.getUpperToleranceValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(upperLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制下限值;
            LinearLayout lowerLayout = new LinearLayout(this);
            lowerLayout.addView(getInfoTV("下限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            lowerLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominalValue() + rol1Bean.getLowerToleranceValue()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominalValue() + rol1Bean.getLowerToleranceValue()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominalValue() + rol1Bean.getLowerToleranceValue()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            mainLayout.addView(lowerLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));


            // 绘制中间值;
            LinearLayout nominalLayout = new LinearLayout(this);
            nominalLayout.addView(getInfoTV("中间值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            nominalLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominalValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominalValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominalValue()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(nominalLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            for (int j = 0; j < mTemplateBean.getDataNum(); j++) {
                LinearLayout dataLayout = new LinearLayout(this);
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)), ColorConstants.dataHeader), getItemLayoutParams(1, 1));


                if (results.get(j).size() < mParameterBean2s.size()) {
                    EditText _edt1 = getInputEditView(true);
                    dataLayout.addView(_edt1, getItemLayoutParams(2, 1));
                    results.get(j).add(_edt1);
                    // 图片;
                    ImageView img = getImageView();
                    dataLayout.addView(img, getItemLayoutParams(3, 1));
                    resultImgs.get(j).add(img);
                } else {
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(2, 1));
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(3, 1));
                }


                if (results.get(j).size() < mParameterBean2s.size()) {
                    EditText _edt2 = getInputEditView(true);
                    dataLayout.addView(_edt2, getItemLayoutParams(2, 1));
                    results.get(j).add(_edt2);
                    ImageView img = getImageView();
                    dataLayout.addView(img, getItemLayoutParams(3, 1));
                    resultImgs.get(j).add(img);
                } else {
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(2, 1));
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(3, 1));
                }


                if (results.get(j).size() < mParameterBean2s.size()) {
                    EditText _edt3 = getInputEditView(true);
                    dataLayout.addView(_edt3, getItemLayoutParams(2, 1));
                    results.get(j).add(_edt3);
                    ImageView img = getImageView();
                    dataLayout.addView(img, getItemLayoutParams(3, 1));
                    resultImgs.get(j).add(img);
                } else {
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(2, 1));
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(3, 1));
                }
                mainLayout.addView(dataLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }


            // 数据；
            /*
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
            */

            // 最大值;
            if (mTemplateBean.getMaximumEnable()) {
                LinearLayout maxLayout = new LinearLayout(this);
                maxLayout.addView(getInfoTV("最大值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
                TextView maxTV1 = getInfoTV("", ColorConstants.dataLineOneColor);
                maxLayout.addView(maxTV1, getItemLayoutParams(5, 1));
                TextView maxTV2 = getInfoTV("", ColorConstants.dataLineOneColor);
                maxLayout.addView(maxTV2, getItemLayoutParams(5, 1));
                TextView maxTV3 = getInfoTV("", ColorConstants.dataLineOneColor);
                maxLayout.addView(maxTV3, getItemLayoutParams(5, 1));
                if (maxEdts.size() < mParameterBean2s.size()) maxEdts.add(maxTV1);
                if (maxEdts.size() < mParameterBean2s.size()) maxEdts.add(maxTV2);
                if (maxEdts.size() < mParameterBean2s.size()) maxEdts.add(maxTV3);
                mainLayout.addView(maxLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }


            // 最小值；
            if (mTemplateBean.getMinimumEnable()) {
                LinearLayout minLayout = new LinearLayout(this);
                minLayout.addView(getInfoTV("最小值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
                TextView minTV1 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV1, getItemLayoutParams(5, 1));
                TextView minTV2 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV2, getItemLayoutParams(5, 1));
                TextView minTV3 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV3, getItemLayoutParams(5, 1));
                if (minEdts.size() < mParameterBean2s.size()) minEdts.add(minTV1);
                if (minEdts.size() < mParameterBean2s.size()) minEdts.add(minTV2);
                if (minEdts.size() < mParameterBean2s.size()) minEdts.add(minTV3);
                mainLayout.addView(minLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }

            // 平均值；
            if (mTemplateBean.getAverageEnable()) {
                LinearLayout minLayout = new LinearLayout(this);
                minLayout.addView(getInfoTV("平均值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
                TextView minTV1 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV1, getItemLayoutParams(5, 1));
                TextView minTV2 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV2, getItemLayoutParams(5, 1));
                TextView minTV3 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV3, getItemLayoutParams(5, 1));
                if (avgEdts.size() < mParameterBean2s.size()) avgEdts.add(minTV1);
                if (avgEdts.size() < mParameterBean2s.size()) avgEdts.add(minTV2);
                if (avgEdts.size() < mParameterBean2s.size()) avgEdts.add(minTV3);
                mainLayout.addView(minLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }

            // 极差值；
            if (mTemplateBean.getRangeEnable()) {
                LinearLayout minLayout = new LinearLayout(this);
                minLayout.addView(getInfoTV("极差值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
                TextView minTV1 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV1, getItemLayoutParams(5, 1));
                TextView minTV2 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV2, getItemLayoutParams(5, 1));
                TextView minTV3 = getInfoTV("", ColorConstants.dataLineTwoColor);
                minLayout.addView(minTV3, getItemLayoutParams(5, 1));
                if (rangeEdts.size() < mParameterBean2s.size()) rangeEdts.add(minTV1);
                if (rangeEdts.size() < mParameterBean2s.size()) rangeEdts.add(minTV2);
                if (rangeEdts.size() < mParameterBean2s.size()) rangeEdts.add(minTV3);
                mainLayout.addView(minLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }


            /*
            table.addCell(getDataCell("最小值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(i * 3 + 0 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 1 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(i * 3 + 2 < _mins.size() ?
                    String.valueOf(_mins.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));
            */

            // 判定;
            if (mTemplateBean.getJudgeEnable()) {
                LinearLayout judgeLayout = new LinearLayout(this);
                judgeLayout.addView(getInfoTV("判定", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
                TextView judgeTV1 = getInfoTV("", ColorConstants.dataLineOneColor);
                judgeLayout.addView(judgeTV1, getItemLayoutParams(5, 1));
                TextView judgeTV2 = getInfoTV("", ColorConstants.dataLineOneColor);
                judgeLayout.addView(judgeTV2, getItemLayoutParams(5, 1));
                TextView judgeTV3 = getInfoTV("", ColorConstants.dataLineOneColor);
                judgeLayout.addView(judgeTV3, getItemLayoutParams(5, 1));
                if (judgeEdts.size() < mParameterBean2s.size()) judgeEdts.add(judgeTV1);
                if (judgeEdts.size() < mParameterBean2s.size()) judgeEdts.add(judgeTV2);
                if (judgeEdts.size() < mParameterBean2s.size()) judgeEdts.add(judgeTV3);
                mainLayout.addView(judgeLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
            }
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
        bottomLayout.addView(aqlLayout, getItemLayoutParams(2, 1 * bottomRow));

        // aql result
        LinearLayout aqlResultLayout = new LinearLayout(this);
        aqlResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (aqlObjects.size() < mTemplateBean.getAQLList().size()) {
                Spinner _edt = getInputSpinner();
                aqlResultLayout.addView(_edt, getItemVLayoutParams(1, 1));
                aqlObjects.add(_edt);
            } else {
                aqlResultLayout.addView(getInfoTV("", Color.WHITE), getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(aqlResultLayout, getItemLayoutParams(1, 1 * bottomRow));

        bottomLayout.addView(getInfoTV("RoHS相关: ", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        // RoSH;
        LinearLayout roshLayout = new LinearLayout(this);
        roshLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            roshLayout.addView(getInfoTV(mTemplateBean.getRoHSList().size() > j ? mTemplateBean.getRoHSList().get(j) : " ", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
        }
        bottomLayout.addView(roshLayout, getItemLayoutParams(2, 1 * bottomRow));


        // RoSH Result;
        LinearLayout roshResultLayout = new LinearLayout(this);
        roshResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (roshEdts.size() < mTemplateBean.getRoHSList().size()) {
                EditText _edt = getInputEditView(false);
                roshResultLayout.addView(_edt, getItemVLayoutParams(1, 1));
                roshEdts.add(_edt);
            } else {
                roshResultLayout.addView(getInfoTV("", Color.WHITE), getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(roshResultLayout, getItemLayoutParams(1, 1 * bottomRow));
        android.util.Log.d("wlDebug", "roshEdts.size() = " + roshEdts.size());

        // Judge Result;
        LinearLayout judgeResultLayout = new LinearLayout(this);
        judgeResultLayout.setOrientation(LinearLayout.VERTICAL);
        judgeResultLayout.addView(getInfoTV("综合判断", ColorConstants.titleColor), getItemVLayoutParams(4, 1 * (bottomRow - 3)));
        allResultTV = getInfoTV("- -", ColorConstants.titleColor);
        judgeResultLayout.addView(allResultTV, getItemVLayoutParams(4, 1 * 3));
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


    public ImageView currentImg = null;

    public ImageView getImageView() {
        ImageView imgTV = new BorderImageView(this);
        imgTV.setImageResource(R.drawable.add_circle);
        imgTV.setPadding(0, 3, 0, 3);
        imgTV.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImg = imgTV;
                dispatchTakePictureIntent();
            }
        });
        return imgTV;
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
            et.setText("0");
        } else {
            // et.setText("测试");
            et.setHint(R.string.hint);
        }
        return et;
    }

    public Spinner getInputSpinner() {
        Spinner sp = new Spinner(this);
        sp.setGravity(Gravity.CENTER);
        ArrayAdapter array_adapter = new ArrayAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.input_result));
        array_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(array_adapter);
        return sp;
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

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        // 刷新;
        new judgeTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (currentImg != null) {
                        Glide.with(this).load(currentPhotoPath).into(currentImg);
                        currentImg.setTag(currentPhotoPath);
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean getIsEmpty() {
        for (int i = 0; i < mTemplateBean.getDataNum(); i++) {
            for (EditText edt : results.get(i)) {
                if (edt.getText().toString().equals("")) return true;
            }
        }
        return false;
    }

    public class judgeTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Input2Activity.this);
            dialog.setTitle("刷新中.");
            dialog.setMessage("正在刷新数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作
            doJudges();
            return "后台任务执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            for (int i = 0; i < mParameterBean2s.size(); i++) {
                maxEdts.get(i).setText(String.valueOf(maxs.get(i)));
                minEdts.get(i).setText(String.valueOf(mins.get(i)));
                avgEdts.get(i).setText(String.valueOf(avgs.get(i)));
                rangeEdts.get(i).setText(String.valueOf(ranges.get(i)));
                judgeEdts.get(i).setText(judges.get(i));
                judgeEdts.get(i).setBackgroundColor(judges.get(i).equals("OK")
                        ? ColorConstants.dataOKColor : ColorConstants.dataNGColor);
                allResultTV.setText(allJudge);
                allResultTV.setBackgroundColor(allJudge.equals("OK")
                        ? ColorConstants.dataOKColor : ColorConstants.dataNGColor);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    public void doJudges() {
        double sum = 0;
        for (int i = 0; i < mTemplateBean.getDataNum(); i++) {
            maxs.set(i, Double.valueOf(-100000));
            mins.set(i, Double.valueOf(1000000));
            judges.set(i, "OK");
        }
        allJudge = "OK";
        for (int i = 0; i < mParameterBean2s.size(); i++) {
            for (int j = 0; j < mTemplateBean.getDataNum(); j++) {
                try {
                    double _value = Double.valueOf(results.get(j).get(i).getText().toString().trim());
                    android.util.Log.d("wlDebug", "do _value = " + _value);
                    sum += _value;
                    if (_value < mins.get(i)) {
                        mins.set(i, _value);
                    }
                    if (_value > maxs.get(i)) {
                        maxs.set(i, _value);
                    }
                    android.util.Log.d("wlDebug", "do getUpperToleranceValue = " + mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getUpperToleranceValue());
                    android.util.Log.d("wlDebug", "do getLowerToleranceValue = " + mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getLowerToleranceValue());
                    if (_value > mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getUpperToleranceValue()
                            || _value < mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getLowerToleranceValue()) {
                        judges.set(i, "NG");
                        allJudge = "NG";
                    } else {
                        // judge = "OK";
                    }
                } catch (Exception e) {

                }
            }
            for (Object obj : aqlObjects) {
                if (obj instanceof Spinner) {
                    String re = ((Spinner) obj).getSelectedItem().toString();
                    if (re.equals("NG")) {
                        allJudge = "NG";
                    }
                }
            }
            avgs.set(i, BigDecimal.valueOf(sum / mTemplateBean.getDataNum()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            ranges.set(i, BigDecimal.valueOf(maxs.get(i) - mins.get(i)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }

    public class ExportedTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        private String path = "/mnt/sdcard/NTGate/";

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            path = path + "table_" + DateUtils.getFileDate(System.currentTimeMillis()) + ".pdf";
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
            doJudges();
            doSave();

            try {
                byte[] img = null;
                if (mCodeBean.getWorkpiecePic() == null) {
                    Bitmap bitmap = BitmapFactory.decodeResource(Input2Activity.this.getResources(), R.drawable.workspice);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    img = baos.toByteArray();
                } else {
                    img = mCodeBean.getWorkpiecePic();
                }

                mTemplateResultBean = new TemplateResultBean();
                List<String> titleLists = new ArrayList<>();
                for (EditText edt : titleEdts) {
                    titleLists.add(edt.getText().toString().trim());
                }
                mTemplateResultBean.setTitleList(titleLists);

                List<String> aqlLists = new ArrayList<>();
                for (Object obj : aqlObjects) {
                    if (obj instanceof Spinner) {
                        aqlLists.add(((Spinner) obj).getSelectedItem().toString());
                    } else if (obj instanceof EditText) {
                        aqlLists.add(((EditText) obj).getText().toString().trim());
                    }
                }
                mTemplateResultBean.setAQLList(aqlLists);

                List<String> roshLists = new ArrayList<>();
                for (EditText edt : roshEdts) {
                    roshLists.add(edt.getText().toString().trim());
                }
                mTemplateResultBean.setRoHSList(roshLists);

                mTemplateResultBean.setAllJudge(allJudge);

                mResultBean3s.clear();
                for (int i = 0; i < results.size(); i++) {
                    ResultBean3 _bean = new ResultBean3();
                    ArrayList<String> values = new ArrayList<>();
                    ArrayList<String> picPaths = new ArrayList<>();
                    for (EditText edt : results.get(i)) {
                        values.add(edt.getText().toString().trim());
                    }
                    for (int j = 0; j < mParameterBean2s.size(); j++) {
                        picPaths.add((String) resultImgs.get(i).get(j).getTag());
                    }
                    _bean.setMValues(values);
                    _bean.setMPicPaths(picPaths);
                    _bean.setHandlerAccout("hhb");
                    _bean.setResult(judges.get(i));
                    _bean.setWorkid_extra("");
                    mResultBean3s.add(_bean);
                    android.util.Log.d("wlDebug", _bean.toString());
                }

                File file = new File(path);
                if (file.exists()) file.delete();
                file.getParentFile().mkdirs();
                PDFUtils.createNTTable(mTemplateBean, mTemplateResultBean, mParameterBean2s, mResultBean3s, img, path);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "后台任务执行完毕";
        }

        /*这个函数在doInBackground调用publishProgress(int i)时触发，虽然调用时只有一个参数
         但是这里取到的是一个数组,所以要用progesss[0]来取值
         第n个参数就用progress[n]来取值*/
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
        }

        /*doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
        这里的result就是上面doInBackground执行后的返回值，所以这里是"后台任务执行完毕"  */
        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            DialogUtils.showDialog(Input2Activity.this, getResources().getString(R.string.pdf_export_title), getResources().getString(R.string.pdf_export_msg) + path);
            openPDFInNative(Input2Activity.this, path);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    public void doSave() {
        List<ResultBean3> updateBeans = new ArrayList<>();

        for (int i = 0; i < mTemplateBean.getDataNum(); i++) {
            // 1
            ResultBean3 _workpiece1Bean = new ResultBean3();

            _workpiece1Bean.setHandlerAccout(App.handlerName);
            _workpiece1Bean.setCodeID(App.getSetupBean().getCodeID());
            _workpiece1Bean.setResult(judges.get(i));
            _workpiece1Bean.setTimeStamp(System.currentTimeMillis());

            List<String> values = new ArrayList();
            for (int j = 0; j < mParameterBean2s.size(); j++) {
                values.add(results.get(i).get(j).getText().toString().trim());
            }
            List<String> paths = new ArrayList();
            for (int j = 0; j < mParameterBean2s.size(); j++) {
                paths.add((String) resultImgs.get(i).get(j).getTag());
                if (j == 0) paths.set(j, "/mnt/sdcard/e0bfe5aa51336f4508397adc87992263.jpg");
            }
            _workpiece1Bean.setMValues(values);
            _workpiece1Bean.setMPicPaths(paths);
            App.getDaoSession().getResultBean3Dao().insert(_workpiece1Bean);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ResultBean3 _b3 : updateBeans) {
                    try {
                        JdbcUtil.addResult3(mDeviceInfoBean.getFactoryCode(), mDeviceInfoBean.getDeviceCode(), App.getSetupBean().getCodeID(), "", _b3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        // Toast.makeText(this, "保存了" + saveNum + "件工件", Toast.LENGTH_SHORT).show();
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
        Uri uri = FileProvider.getUriForFile(this, "alauncher.cn.measuringtablet.fileProvider", file);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/pdf");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Log.w("URLSpan", "Activity was not found for intent, " + intent.toString(), e);
        }
    }

    static final int REQUEST_TAKE_PHOTO = 2;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "alauncher.cn.measuringtablet.fileProvider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
