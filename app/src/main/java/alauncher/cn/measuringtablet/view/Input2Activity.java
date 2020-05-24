package alauncher.cn.measuringtablet.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.provider.Browser;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.cuiweiyou.numberpickerdialog.NumberPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.CodeBean;
import alauncher.cn.measuringtablet.bean.DeviceInfoBean;
import alauncher.cn.measuringtablet.bean.ParameterBean2;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.TemplatePicBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;
import alauncher.cn.measuringtablet.bean.User;
import alauncher.cn.measuringtablet.database.greenDao.db.ParameterBean2Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBean3Dao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplatePicBeanDao;
import alauncher.cn.measuringtablet.database.greenDao.db.TemplateResultBeanDao;
import alauncher.cn.measuringtablet.utils.ColorConstants;
import alauncher.cn.measuringtablet.utils.DateUtils;
import alauncher.cn.measuringtablet.utils.DialogUtils;
import alauncher.cn.measuringtablet.utils.FileUtils;
import alauncher.cn.measuringtablet.utils.JdbcUtil;
import alauncher.cn.measuringtablet.utils.NumberUtils;
import alauncher.cn.measuringtablet.widget.BorderEditView;
import alauncher.cn.measuringtablet.widget.BorderImageView;
import alauncher.cn.measuringtablet.widget.BorderSpinner;
import alauncher.cn.measuringtablet.widget.BorderTextView;
import butterknife.BindView;
import butterknife.OnClick;

public class Input2Activity extends BaseOActivity {

    @BindView(R.id.input_main_layout)
    public ViewGroup mainLayout;

    @BindView(R.id.data_num_btn)
    public TextView dataNumBtn;

    public TemplateBean mTemplateBean;

    public TemplateResultBean mTemplateResultBean;

    // private List<Parameter2Bean> mParameterBean2s;

    private List<ParameterBean2> mParameterBean2s;

    private List<ResultBean3> mResultBean3s = new ArrayList<>();

    // 表头;
    private List<Object> titleEdts = new ArrayList<>();

    // aql
    // private List<EditText> aqlEdts = new ArrayList<>();

    // aql
    private List<Object> aqlObjects = new ArrayList<>();

    // rosh
    private List<Object> roshEdts = new ArrayList<>();

    // result;
    private List<List<View>> results = new ArrayList<>();

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

    private EditText remarkEdt;

    // 添加备注下面图片的img;
    @BindView(R.id.scroll_view)
    public ScrollView scrollView;

    private List<Double> maxs = new ArrayList<>();
    private List<Double> mins = new ArrayList<>();
    private List<Double> avgs = new ArrayList<>();
    private List<Double> ranges = new ArrayList<>();
    private List<String> judges = new ArrayList<>();
    private List<String> dataJudges = new ArrayList<>();
    private String allJudge = "OK";

    private User user;
    private static int dataNumber = 1;

    ImageView imgView;

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

        mCodeBean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
        mTemplateBean = App.getDaoSession().getTemplateBeanDao().load(mCodeBean.getUseTemplateID());
        if (mTemplateBean == null) {
            DialogUtils.showDialog(this, "未设置模板", "未设置模板，无法测量.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            return;
        }
        // mParameterBean2s = App.getDaoSession().getParameter2BeanDao().queryBuilder().where(Parameter2BeanDao.Properties.Code_id.eq((long) App.getSetupBean().getCodeID())).list();
        mParameterBean2s = App.getDaoSession().getParameterBean2Dao().queryBuilder()
                .where(ParameterBean2Dao.Properties.CodeID.eq(App.getSetupBean().getCodeID()), ParameterBean2Dao.Properties.Enable.eq(true))
                .orderAsc(ParameterBean2Dao.Properties.SequenceNumber).list();
        mCodeBean = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID());
        mDeviceInfoBean = App.getDaoSession().getDeviceInfoBeanDao().load(App.SETTING_ID);

        for (int i = 0; i < dataNumber; i++) {
            results.add(new ArrayList<>());
            resultImgs.add(new ArrayList<>());
            dataJudges.add("OK");
        }

        for (int i = 0; i < mParameterBean2s.size(); i++) {
            maxs.add(Double.valueOf(-100000));
            mins.add(Double.valueOf(1000000));
            avgs.add(Double.valueOf(1000000));
            ranges.add(Double.valueOf(1000000));
            judges.add("OK");
        }

        dataNumBtn.setText(getResources().getString(R.string.data_num) + dataNumber);

        // 标题 + 签名栏;
        LinearLayout _layout = new LinearLayout(this);
        _layout.setOrientation(LinearLayout.HORIZONTAL);
        ImageView logoIV = getImageView();
        logoIV.setPadding(2, 2, 2, 2);
        logoIV.setOnClickListener(null);
        if (mTemplateBean.getLogoPic() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mTemplateBean.getLogoPic(), 0, mTemplateBean.getLogoPic().length, null);
            logoIV.setScaleType(ImageView.ScaleType.FIT_XY);
            logoIV.setImageBitmap(bitmap);
        } else {
            // logoIV.setImageResource(R.drawable.et_logo);
            logoIV.setVisibility(View.GONE);
        }
        _layout.addView(logoIV, getLayoutParams(0, 2, 2));
        _layout.addView(getInfoTV(mTemplateBean.getTitle(), ColorConstants.titleColor), getLayoutParams(0, 2, 10));
        user = App.getDaoSession().getUserDao().load(App.handlerAccout);
        for (int i = 0; i < mTemplateBean.getSignList().size(); i++) {
            LinearLayout signLayout = new LinearLayout(this);
            signLayout.setOrientation(LinearLayout.VERTICAL);
            signLayout.addView(getInfoTV(mTemplateBean.getSignList().get(i), ColorConstants.titleColor), getItemVLayoutParams(1, 2));
            if (user != null) {
                signLayout.addView(getInfoTV(mTemplateBean.getSignList().get(i).equals("担当") ? user.getName() : "", Color.WHITE), getItemVLayoutParams(1, 2));
            } else {
                signLayout.addView(getInfoTV(mTemplateBean.getSignList().get(i).equals("担当") ? "" : "", Color.WHITE), getItemVLayoutParams(1, 2));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, (int) getResources().getDimension(R.dimen.item_height) * 2);
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
                if ((i * 4 + j) < mTemplateBean.getTitleList().size()) {
                    View view = (View) getInputViewByType(mTemplateBean.getTitleTypeList().get(i * 4 + j), false);
                    __layout.addView(view, getItemLayoutParams(1, 1));

                    if (mCodeBean.getDefaultTitles().size() > (i * 4 + j) && view instanceof EditText) {
                        ((EditText) view).setText(mCodeBean.getDefaultTitles().get(i * 4 + j));
                    }
                    titleEdts.add(view);
                } else {
                    __layout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(1, 1));
                }
            }
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
        }

        // 添加工件图;
        imgView = new ImageView(this);
        mainLayout.addView(imgView, getItemVLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 15));
        imgView.setImageResource(R.drawable.workspice);
        byte[] _pic = App.getDaoSession().getCodeBeanDao().load((long) App.getSetupBean().getCodeID()).getWorkpiecePic();
        if (_pic != null) {
            Bitmap map = BitmapFactory.decodeByteArray(_pic, 0, _pic.length);
            imgView.setImageBitmap(map);
        } else {
            imgView.setImageResource(R.drawable.workspice);
        }
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_pic != null) {
                    String path = bytesToImageFile(_pic);
                    if (path != null) openImage(path);
                }
            }
        });

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
                    String.valueOf(rol1Bean.getDescribe()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getDescribe()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            __layout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getDescribe()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 1));
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制上限值;
            LinearLayout upperLayout = new LinearLayout(this);
            upperLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            upperLayout.addView(getInfoTV(getUpperToleranceValue(rol1Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(getUpperToleranceValue(rol2Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            upperLayout.addView(getInfoTV(getUpperToleranceValue(rol3Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(upperLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            // 绘制下限值;
            LinearLayout lowerLayout = new LinearLayout(this);
            lowerLayout.addView(getInfoTV("下限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            lowerLayout.addView(getInfoTV(getLowerToleranceValue(rol1Bean), ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(getLowerToleranceValue(rol2Bean), ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            lowerLayout.addView(getInfoTV(getLowerToleranceValue(rol3Bean), ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 1));
            mainLayout.addView(lowerLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));


            // 绘制中间值;
            LinearLayout nominalLayout = new LinearLayout(this);
            nominalLayout.addView(getInfoTV("中间值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 1));
            nominalLayout.addView(getInfoTV(getNominalValue(rol1Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(getNominalValue(rol2Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            nominalLayout.addView(getInfoTV(getNominalValue(rol3Bean), ColorConstants.dataLineOneColor), getItemLayoutParams(5, 1));
            mainLayout.addView(nominalLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));

            for (int j = 0; j < dataNumber; j++) {
                LinearLayout dataLayout = new LinearLayout(this);
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)), ColorConstants.dataHeader), getItemLayoutParams(1, 1));

                if (results.get(j).size() < mParameterBean2s.size()) {
                    View view = (View) getInputViewByType(rol1Bean.getType() == 3 ? "1" : "0", true);
                    dataLayout.addView(view, getItemLayoutParams(2, 1));
                    results.get(j).add(view);
                    // 图片;
                    ImageView img = getImageView();
                    dataLayout.addView(img, getItemLayoutParams(3, 1));
                    resultImgs.get(j).add(img);
                } else {
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(2, 1));
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(3, 1));
                }


                if (results.get(j).size() < mParameterBean2s.size()) {
                    View view = (View) getInputViewByType(rol2Bean.getType() == 3 ? "1" : "0", true);
                    dataLayout.addView(view, getItemLayoutParams(2, 1));
                    results.get(j).add(view);
                    ImageView img = getImageView();
                    dataLayout.addView(img, getItemLayoutParams(3, 1));
                    resultImgs.get(j).add(img);
                } else {
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(2, 1));
                    dataLayout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(3, 1));
                }


                if (results.get(j).size() < mParameterBean2s.size()) {
                    View view = (View) getInputViewByType(rol3Bean.getType() == 3 ? "1" : "0", true);
                    dataLayout.addView(view, getItemLayoutParams(2, 1));
                    results.get(j).add(view);
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
        if (mTemplateBean.getAqlEnable()) {
            bottomLayout.addView(getInfoTV("外观检查一般I级AQL=0.15", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        } else {
            bottomLayout.addView(getInfoTV("", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        }
        // aql title
        LinearLayout aqlLayout = new LinearLayout(this);
        aqlLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (mTemplateBean.getAqlEnable()) {
                aqlLayout.addView(getInfoTV(mTemplateBean.getAQLList().size() > j ? mTemplateBean.getAQLList().get(j) : " ", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
            } else {
                TextView tv = getInfoTV("", ColorConstants.titleColor);
                tv.setEnabled(false);
                aqlLayout.addView(tv, getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(aqlLayout, getItemLayoutParams(2, 1 * bottomRow));

        // aql result
        LinearLayout aqlResultLayout = new LinearLayout(this);
        aqlResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (aqlObjects.size() < mTemplateBean.getAQLList().size()) {
                View view = (View) getInputViewByType(mTemplateBean.getAqlEnable() ? mTemplateBean.getAQLTypeList().get(j) : "4", false);
                aqlResultLayout.addView(view, getItemVLayoutParams(1, 1));
                aqlObjects.add(view);
            } else {
                aqlResultLayout.addView(getInfoTV("", Color.WHITE), getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(aqlResultLayout, getItemLayoutParams(1, 1 * bottomRow));

        if (mTemplateBean.getRoshEnable()) {
            bottomLayout.addView(getInfoTV("RoHS相关: ", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        } else {
            bottomLayout.addView(getInfoTV("", ColorConstants.bottomColor), getItemLayoutParams(1, 1 * bottomRow));
        }
        // RoSH;
        LinearLayout roshLayout = new LinearLayout(this);
        roshLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (mTemplateBean.getRoshEnable()) {
                roshLayout.addView(getInfoTV(mTemplateBean.getRoHSList().size() > j ? mTemplateBean.getRoHSList().get(j) : " ", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
            } else {
                roshLayout.addView(getInfoTV("", ColorConstants.titleColor), getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(roshLayout, getItemLayoutParams(2, 1 * bottomRow));


        // RoSH Result;
        LinearLayout roshResultLayout = new LinearLayout(this);
        roshResultLayout.setOrientation(LinearLayout.VERTICAL);
        for (int j = 0; j < bottomRow; j++) {
            if (roshEdts.size() < mTemplateBean.getRoHSList().size()) {
                View view = (View) getInputViewByType(mTemplateBean.getRoshEnable() ? mTemplateBean.getRoHSTypeList().get(j) : "4", false);
                roshResultLayout.addView(view, getItemVLayoutParams(1, 1));
                roshEdts.add(view);
                if (mTemplateBean.getRoshEnable()) {
                    if (j == 0) {
                        ((EditText) view).setText(mTemplateBean.getConfirmationFrequency() + getResources().getString(R.string.day));
                        ((EditText) view).setEnabled(false);
                    } else if (j == 1) {
                        if (getIsNeedConfirm()) {

                        } else {
                            view.setVisibility(View.INVISIBLE);
                        }
                    } else if (j == 2) {
                        ((TextView) view).setText(DateUtils.getDate(mTemplateBean.getLastConfirmTimeStamp()));
                        ((TextView) view).setEnabled(false);
                    }
                }
            } else {
                roshResultLayout.addView(getInfoTV("", Color.WHITE), getItemVLayoutParams(1, 1));
            }
        }
        bottomLayout.addView(roshResultLayout, getItemLayoutParams(1, 1 * bottomRow));
//        android.util.Log.d("wlDebug", "roshEdts.size() = " + roshEdts.size());

        // Judge Result;
        LinearLayout judgeResultLayout = new LinearLayout(this);
        judgeResultLayout.setOrientation(LinearLayout.VERTICAL);
        judgeResultLayout.addView(getInfoTV("综合判断", ColorConstants.titleColor), getItemVLayoutParams(4, 1 * (bottomRow - 3)));
        allResultTV = getInfoTV("- -", ColorConstants.titleColor);
        judgeResultLayout.addView(allResultTV, getItemVLayoutParams(4, 1 * 3));
        bottomLayout.addView(judgeResultLayout, getItemLayoutParams(1, 1 * bottomRow));

        mainLayout.addView(bottomLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1 * bottomRow, 1));
        remarkEdt = getInputEditView();
        mainLayout.addView(remarkEdt, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2, 1));

        // 添加底部添加图片按钮;
        View view = getLayoutInflater().inflate(R.layout.add_pic_item, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addImgs.size() >= 20) {
                    DialogUtils.showDialog(Input2Activity.this, "超限制", "已经20张了，无法添加照片.");
                } else {
                    dispatchTakePictureIntent(BACKUP_TAKE_PHOTO);
                }
            }
        });
        mainLayout.addView(view, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2, 1));
    }

    private String getUpperToleranceValue(ParameterBean2 pBean2) {
        if (pBean2 == null) return "";
        switch (pBean2.getType()) {
            case 0:
                return String.valueOf(pBean2.getNominalValue() + pBean2.getUpperToleranceValue());
            case 1:
                return String.valueOf(pBean2.getUpperToleranceValue());
            default:
                return "";
        }
    }

    private String getLowerToleranceValue(ParameterBean2 pBean2) {
        if (pBean2 == null) return "";
        switch (pBean2.getType()) {
            case 0:
                return String.valueOf(pBean2.getNominalValue() + pBean2.getLowerToleranceValue());
            case 2:
                return String.valueOf(pBean2.getLowerToleranceValue());
            default:
                return "";
        }
    }

    private String getNominalValue(ParameterBean2 pBean2) {
        if (pBean2 == null) return "";
        switch (pBean2.getType()) {
            case 0:
                return String.valueOf(pBean2.getNominalValue());
            case 3:
                return pBean2.getName();
            default:
                return "";
        }
    }

    private LinearLayout bottomImageLayout;
    private List<String> addImgs = new ArrayList<>();

    private void updateBottomImageLayout() {
        if (bottomImageLayout == null) {
            bottomImageLayout = new LinearLayout(this);
            bottomImageLayout.setOrientation(LinearLayout.VERTICAL);
        }
        mainLayout.removeView(bottomImageLayout);
        bottomImageLayout.removeAllViews();
        for (int i = 0; i < addImgs.size(); i++) {
            ImageView img = new BorderImageView(this);
            img.setPadding(0, 3, 0, 3);
            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            final String path = addImgs.get(i);
            Glide.with(this).load(path).into(img);
            img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DialogUtils.showDialog(Input2Activity.this, "删除图片", "确认删除此图片?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            addImgs.remove(path);
                            updateBottomImageLayout();
                        }
                    });
                    return true;
                }
            });
            bottomImageLayout.addView(img, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        mainLayout.addView(bottomImageLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 800);
        mainLayout.invalidate();
    }

    public boolean getIsNeedConfirm() {
        if (mTemplateBean.getConfirmationFrequency() == 0) {
            return true;
        }
        if (System.currentTimeMillis() - mTemplateBean.getLastConfirmTimeStamp() > (mTemplateBean.getConfirmationFrequency() * 1000 * 60 * 60 * 24)) {
            return true;
        }
        return false;
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

    public Object getInputViewByType(String type, boolean numOnly) {
        Object view = null;
        switch (type) {
            case "0":
                view = getInputEditView(numOnly);
                break;
            case "1":
                view = getInputSpinner();
                break;
            case "2":
                view = getDataTV("", Color.WHITE);
                break;
            case "3":
                view = getInputSpinner2();
                break;
            case "4":
                view = getInfoTV("", -1);
                break;
            default:
                break;
        }
        return view;
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
                if (imgTV.getTag() != null) {
                    openImage(String.valueOf(imgTV.getTag()));
                } else {
                    currentImg = imgTV;
                    dispatchTakePictureIntent(RESULT_TAKE_PHOTO);
                }
            }
        });
        imgTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog builder = new AlertDialog.Builder(Input2Activity.this)
                        .create();
                builder.show();
                if (builder.getWindow() == null) return false;
                builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
                TextView msg = builder.findViewById(R.id.tv_msg);
                Button cancel = builder.findViewById(R.id.btn_cancle);
                Button sure = builder.findViewById(R.id.btn_sure);
                msg.setText("是否清除图片？");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgTV.setTag(null);
                        imgTV.setImageResource(R.drawable.add_circle);
                        builder.dismiss();
                    }
                });
                return false;
            }
        });
        return imgTV;
    }

    private String bytesToImageFile(byte[] bytes) {
        try {
            String imageFileName = "workpiece";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg", /* suffix */
                    storageDir      /* directory */
            );
            if (image.exists()) image.delete();
            image.createNewFile();
            FileOutputStream fos = new FileOutputStream(image);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
            return image.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void openImage(String path) {
        File file = new File(path);
        if (file.exists()) {
            Uri uri = FileProvider.getUriForFile(Input2Activity.this, "alauncher.cn.measuringtablet.fileProvider", file);
            // Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "image/jpeg");
            startActivity(intent);
        } else {
            // Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public TextView getInfoTV(String msg, int color) {
        TextView tv = new BorderTextView(this);
        tv.setMaxLines(1);
        tv.setText(msg);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        if (color != -1) tv.setBackgroundColor(color);
        return tv;
    }

    public TextView getDataTV(String msg, int color) {
        TextView tv = new BorderTextView(this);
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int day = ca.get(Calendar.DAY_OF_MONTH);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Input2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        String days;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("/").append("0").
                                        append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("/").append("0").
                                        append(mMonth + 1).append("/").append(mDay).append("").toString();
                            }
                        } else {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("/").
                                        append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("/").
                                        append(mMonth + 1).append("/").append(mDay).append("").toString();
                            }

                        }
                        tv.setText(days);
                    }
                }, year, month, day).show();
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String t = format.format(new Date());
        tv.setText(t);
        tv.setMaxLines(1);
//        tv.setText(msg);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(color);
        return tv;
    }

    public EditText getInputEditView(boolean numOnly) {
        EditText et = new BorderEditView(this);
        et.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        et.setPadding(2, 2, 2, 2);
        et.setGravity(Gravity.CENTER);
        et.setBackground(null);
        et.setMaxLines(1);
        et.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        if (numOnly) {
            DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false, true);
            et.setKeyListener(numericOnlyListener);
            et.setText("");
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable edt) {
                    android.util.Log.d("wlDebug", "afterTextChanged");
                    String temp = edt.toString();
                    int posDot = temp.indexOf(".");
                    if (temp.startsWith("00")) {
                        edt.delete(0, 1);
                        return;
                    }
                    if (posDot < 0) {
                        return;
                    }
                    if (temp.length() - posDot - 1 > 8) {
                        edt.delete(posDot + 9, posDot + 10);
                    }
                }
            });
            et.setHint(R.string.hint);
        } else {
            et.setHint(R.string.hint);
        }
        return et;
    }

    public EditText getInputEditView() {
        EditText et = new BorderEditView(this);

        // et.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        et.setBackground(null);
        et.setMaxLines(1);
        et.setHint(R.string.remarks);
        return et;
    }

    public Spinner getInputSpinner() {
        Spinner sp = new BorderSpinner(this);
        sp.setPadding(2, 2, 2, 2);
        sp.setGravity(Gravity.CENTER);
        ArrayAdapter array_adapter = new ArrayAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.input_result));
        array_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(array_adapter);
        return sp;
    }

    public Spinner getInputSpinner2() {
        Spinner sp = new BorderSpinner(this);
        sp.setPadding(2, 2, 2, 2);
        sp.setGravity(Gravity.CENTER);
        ArrayAdapter array_adapter = new ArrayAdapter(this, R.layout.spinner_item, getResources().getStringArray(R.array.quantity_items));
        array_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(array_adapter);
        return sp;
    }

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        if (mParameterBean2s.size() == 0) {
            DialogUtils.showDialog(this, "无参数", "无参数，无法保存.");
            return;
        }

        if (getLineEmpty() >= 0) {
            DialogUtils.showDialog(this, "无法保存.", "数据" + (getLineEmpty() + 1) + "所有参数为空，无法保存.");
            return;
        }

        if (getIsEmpty()) {
            final AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(Input2Activity.this);
            normalDialog.setIcon(R.drawable.add_circle);
            normalDialog.setTitle("确认保存");
            normalDialog.setMessage("还有测量值未输入，是否确认保存。");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // doSave();
                            new ExportedTask().execute();
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
        android.util.Log.d("wlDebug", "(" + imgView.getWidth() + "," + imgView.getHeight() + ")");
        new judgeTask().execute();
    }

    @OnClick(R.id.data_num_btn)
    public void dataNum() {
        new NumberPickerDialog(
                Input2Activity.this,
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (newVal > 0 && oldVal != newVal) {
                            dataNumber = newVal;
                            clear();
                        }
                    }
                },
                20, // 最大值
                1, // 最小值
                5) // 默认值
                .setCurrentValue(dataNumber > 0 ? dataNumber : 1) // 更新默认值
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (curMode == RESULT_TAKE_PHOTO) {
                        if (currentImg != null) {
                            Glide.with(this).load(currentPhotoPath).into(currentImg);
                            currentImg.setTag(currentPhotoPath);
                            currentImg = null;
                        }
                    } else if (curMode == BACKUP_TAKE_PHOTO) {
                        addImgs.add(currentPhotoPath);
                        updateBottomImageLayout();
                    }
                    curMode = 0;
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btn_clear)
    public void clear() {
        // 刷新;
        recreate();
    }

    @OnClick({R.id.swap_btn})
    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.swap_btn:
                showChooseDialog(0);
                break;
        }
    }

    @OnClick({R.id.sync_btn})
    public void syncClick(View view) {
        new SyncTask().execute();
    }

    private List<CodeBean> lists;

    private void showChooseDialog(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.gridview_dialog, null);
        GridView gridview = view.findViewById(R.id.gridview);
        final Dialog dialog = new Dialog(this, R.style.common_dialog);
        dialog.setContentView(view);
        dialog.show();
        if (index == 0) {
            lists = App.getDaoSession().getCodeBeanDao().loadAll();
        }
        Set<String> fristSet = new HashSet<>();
        for (CodeBean _bean : lists) {
            String[] strArray = _bean.getName().split("-");
            fristSet.add(strArray[index]);
        }

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

        for (String key : fristSet) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("itemName", key);
            items.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, items, R.layout.gridview_item, new String[]{"itemName"}, new int[]{R.id.grid_name});
        gridview.setAdapter(simpleAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int j, long arg3) {
                TextView tv = view.findViewById(R.id.grid_name);
                String str = tv.getText().toString();
                dialog.dismiss();
                if (index < 3) {
                    for (int i = lists.size() - 1; i >= 0; i--) {
                        CodeBean item = lists.get(i);
                        if (!item.getName().contains(str)) {
                            lists.remove(item);
                        }
                    }
                    showChooseDialog(index + 1);
                } else {
                    CodeBean _CodeBean = lists.get(j);
                    SetupBean _bean = App.getDaoSession().getSetupBeanDao().load(App.SETTING_ID);
                    _bean.setCodeID(_CodeBean.getCodeID().intValue());
                    App.getDaoSession().getSetupBeanDao().update(_bean);
                    clear();
                }
            }
        });
    }


    private boolean getIsEmpty() {
        for (int i = 0; i < dataNumber; i++) {
            for (View edt : results.get(i)) {
                if (edt instanceof EditText) {
                    if (((EditText) edt).getText().toString().equals("")) return true;
                }
            }
        }
        return false;
    }

    private int getLineEmpty() {
        for (int i = 0; i < dataNumber; i++) {
            boolean isEmpty = true;
            for (View edt : results.get(i)) {
                if (edt instanceof EditText) {
                    if (!((EditText) edt).getText().toString().equals("")) isEmpty = false;
                } else if (edt instanceof Spinner) {
                    isEmpty = false;
                }
            }
            if (isEmpty) return i;
        }
        return -1;
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
                if (mTemplateBean.getMaximumEnable())
                    maxEdts.get(i).setText(String.valueOf(maxs.get(i)));
                if (mTemplateBean.getMinimumEnable())
                    minEdts.get(i).setText(String.valueOf(mins.get(i)));
                if (mTemplateBean.getAverageEnable())
                    avgEdts.get(i).setText(NumberUtils.notScience(avgs.get(i)));
                if (mTemplateBean.getRangeEnable()) {
                    rangeEdts.get(i).setText(NumberUtils.notScience(ranges.get(i)));
                }
                if (mTemplateBean.getJudgeEnable()) {
                    judgeEdts.get(i).setText(judges.get(i));
                    judgeEdts.get(i).setBackgroundColor(judges.get(i).equals("OK")
                            ? ColorConstants.dataOKColor : ColorConstants.dataNGColor);
                }
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
        for (int i = 0; i < mParameterBean2s.size(); i++) {
            maxs.set(i, Double.valueOf(-100000));
            mins.set(i, Double.valueOf(1000000));
            judges.set(i, "OK");
        }
        android.util.Log.d("wlDebug", "judges.size = " + judges.size());
        for (int i = 0; i < dataNumber; i++) {
            dataJudges.set(i, "OK");
        }
        allJudge = "OK";

        for (int j = 0; j < dataNumber; j++) {
            for (int i = 0; i < mParameterBean2s.size(); i++) {
                View _view = results.get(j).get(i);
                if (_view instanceof Spinner) {
                    if (((Spinner) _view).getSelectedItemPosition() == 1) {
                        dataJudges.set(j, "NG");
                    }
                    continue;
                }
                if (((EditText) _view).getText().toString().trim().equals("")) {
                    // dataJudges.set(j, "NG");
                    continue;
                }
                int type = mParameterBean2s.get(i).getType();
                double _value = Double.valueOf(((EditText) _view).getText().toString().trim());
                switch (type) {
                    case 0:
                        if (_value > mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getUpperToleranceValue()
                                || _value < mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getLowerToleranceValue()) {
                            dataJudges.set(j, "NG");
                        }
                        break;
                    case 1:
                        if (_value > mParameterBean2s.get(i).getUpperToleranceValue()) {
                            dataJudges.set(j, "NG");
                        }
                        break;
                    case 2:
                        if (_value < mParameterBean2s.get(i).getLowerToleranceValue()) {
                            dataJudges.set(j, "NG");
                        }
                        break;
                    case 3:
                        if (_value == 0) {
                            dataJudges.set(j, "NG");
                        }
                        break;
                    default:
                        break;
                }

            }
        }

        for (int i = 0; i < mParameterBean2s.size(); i++) {
            sum = 0;
            for (int j = 0; j < dataNumber; j++) {
                try {
                    View _view = results.get(j).get(i);
                    if (_view instanceof Spinner) {
                        if (((Spinner) _view).getSelectedItemPosition() == 1) {
                            judges.set(i, "NG");
                            allJudge = "NG";
                        }
                        continue;
                    }
                    if (((EditText) _view).getText().toString().trim().equals("")) {
                        // judges.set(i, "NG");
                        // allJudge = "NG";
                        continue;
                    }
                    double _value = Double.valueOf(((EditText) _view).getText().toString().trim());
                    sum += _value;
                    if (_value < mins.get(i)) {
                        mins.set(i, _value);
                    }
                    if (_value > maxs.get(i)) {
                        maxs.set(i, _value);
                    }
//                    if (_value > mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getUpperToleranceValue()
//                            || _value < mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getLowerToleranceValue()) {
//                        judges.set(i, "NG");
//                        allJudge = "NG";
//                    } else {
//
//                    }
                    int type = mParameterBean2s.get(i).getType();
                    switch (type) {
                        case 0:
                            if (_value > mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getUpperToleranceValue()
                                    || _value < mParameterBean2s.get(i).getNominalValue() + mParameterBean2s.get(i).getLowerToleranceValue()) {
                                judges.set(i, "NG");
                                allJudge = "NG";
                            }
                            break;
                        case 1:
                            if (_value > mParameterBean2s.get(i).getUpperToleranceValue()) {
                                judges.set(i, "NG");
                                allJudge = "NG";
                            }
                            break;
                        case 2:
                            if (_value < mParameterBean2s.get(i).getLowerToleranceValue()) {
                                judges.set(i, "NG");
                                allJudge = "NG";
                            }
                            break;
                        case 3:
                            if (_value == 0) {
                                judges.set(i, "NG");
                                allJudge = "NG";
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    judges.set(i, "NG");
                    allJudge = "NG";
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
            if (maxs.get(i) == -100000) {
                maxs.set(i, 0d);
            }
            if (mins.get(i) == 1000000) {
                mins.set(i, 0d);
            }
            avgs.set(i, BigDecimal.valueOf(sum / dataNumber).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
            ranges.set(i, BigDecimal.valueOf(maxs.get(i) - mins.get(i)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        for (int i = 0; i < judges.size(); i++) {
            android.util.Log.d("wlDebug", "string" + i + " = " + judges.get(i));
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
            dialog.setTitle("保存");
            dialog.setMessage("保存数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {

            doJudges();
            // doSave();
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
                mTemplateResultBean.setImg(img);
                if (mTemplateBean.getLogoPic() != null) {
                    mTemplateResultBean.setLogoPic(new byte[mTemplateBean.getLogoPic().length]);
                    System.arraycopy(mTemplateBean.getLogoPic(), 0, mTemplateResultBean.getLogoPic(), 0, mTemplateBean.getLogoPic().length);
                }
                mTemplateResultBean.setTitle(mTemplateBean.getTitle());
                mTemplateResultBean.setTitleList(mTemplateBean.getTitleList());
                mTemplateResultBean.setSignList(mTemplateBean.getSignList());
                mTemplateResultBean.setAQLList(mTemplateBean.getAQLList());
                mTemplateResultBean.setRoHSList(mTemplateBean.getRoHSList());
                mTemplateResultBean.setHeaderLeft(mTemplateBean.getHeaderLeft());
                mTemplateResultBean.setHeaderMid(mTemplateBean.getHeaderMid());
                mTemplateResultBean.setHeaderRight(mTemplateBean.getHeaderRight());
                mTemplateResultBean.setFooterLeft(mTemplateBean.getFooterLeft());
                mTemplateResultBean.setFooterMid(mTemplateBean.getFooterMid());
                mTemplateResultBean.setFooterRight(mTemplateBean.getFooterRight());

                mTemplateResultBean.setMaximumEnable(mTemplateBean.getMaximumEnable());
                mTemplateResultBean.setMinimumEnable(mTemplateBean.getMinimumEnable());
                mTemplateResultBean.setAverageEnable(mTemplateBean.getAverageEnable());
                mTemplateResultBean.setRangeEnable(mTemplateBean.getRangeEnable());
                mTemplateResultBean.setJudgeEnable(mTemplateBean.getJudgeEnable());
                mTemplateResultBean.setFactoryCode(mDeviceInfoBean.getFactoryCode());
                mTemplateResultBean.setDeviceCode(mDeviceInfoBean.getDeviceCode());
                mTemplateResultBean.setCodeID(App.getSetupBean().getCodeID());
                mTemplateResultBean.setRemarks(remarkEdt.getText().toString().trim());
                mTemplateResultBean.setTimeStamp(System.currentTimeMillis());
                mTemplateResultBean.setUser(user.getName());
                if (getIsNeedConfirm()) {
                    mTemplateBean.setLastConfirmTimeStamp(System.currentTimeMillis());
                    App.getDaoSession().getTemplateBeanDao().insertOrReplace(mTemplateBean);
                }

                List<String> indexLists = new ArrayList<>();
                List<String> nominalValue = new ArrayList<>();
                List<String> upperLists = new ArrayList<>();
                List<String> lowerLists = new ArrayList<>();
                List<String> markLists = new ArrayList<>();
                for (int j = 0; j < mParameterBean2s.size(); j++) {
                    ParameterBean2 _bean = mParameterBean2s.get(j);
                    indexLists.add(String.valueOf(_bean.getSequenceNumber() + 1));
                    nominalValue.add(getNominalValue(_bean));
                    upperLists.add(getUpperToleranceValue(_bean));
                    lowerLists.add(getLowerToleranceValue(_bean));
                    markLists.add(_bean.getDescribe());
                }
                mTemplateResultBean.setValueIndexs(indexLists);
                mTemplateResultBean.setNominalValues(nominalValue);
                mTemplateResultBean.setUpperToleranceValues(upperLists);
                mTemplateResultBean.setLowerToleranceValues(lowerLists);
                mTemplateResultBean.setMarkList(markLists);
                List<String> titleLists = new ArrayList<>();
                for (Object obj : titleEdts) {
                    // titleLists.add(edt.getText().toString().trim());
                    titleLists.add(getTextByInputType(obj));
                }
                mTemplateResultBean.setTitleResultList(titleLists);

                List<String> aqlLists = new ArrayList<>();
                for (Object obj : aqlObjects) {
                    aqlLists.add(getTextByInputType(obj));
                }
                mTemplateResultBean.setAQLResultList(aqlLists);

                List<String> roshLists = new ArrayList<>();
                for (Object obj : roshEdts) {
                    roshLists.add(getTextByInputType(obj));
                }
                mTemplateResultBean.setRoHSResultList(roshLists);
                mTemplateResultBean.setAllJudge(allJudge);
                Long templateLocalID = App.getDaoSession().getTemplateResultBeanDao().insert(mTemplateResultBean);

                // 插入图片;
                List<TemplatePicBean> templatePicBeans = new ArrayList<>();
                for (String _path : addImgs) {
                    android.util.Log.d("wlDebug", "_path = " + _path);
                    byte[] fileByte = FileUtils.image2byte(_path);
                    if (fileByte != null) {
                        TemplatePicBean _bean = new TemplatePicBean();
                        _bean.setTemplateResultID(templateLocalID);
                        byte[] imgByte = new byte[fileByte.length];
                        _bean.setImg(imgByte);
                        System.arraycopy(fileByte, 0, imgByte, 0, fileByte.length);
                        templatePicBeans.add(_bean);
                        App.getDaoSession().getTemplatePicBeanDao().insert(_bean);
                    }
                }

                mResultBean3s.clear();
                for (int i = 0; i < results.size(); i++) {
                    ResultBean3 _bean = new ResultBean3();
                    _bean.setTemplateID(templateLocalID);
                    _bean.setCodeID(App.getSetupBean().getCodeID());
                    ArrayList<String> values = new ArrayList<>();
                    ArrayList<String> picPaths = new ArrayList<>();
                    ArrayList<String> isBools = new ArrayList<>();
                    for (View edt : results.get(i)) {
                        if (edt instanceof EditText) {
                            values.add(((EditText) edt).getText().toString().trim());
                            isBools.add("false");
                        } else if (edt instanceof Spinner) {
                            Spinner sp = (Spinner) edt;
                            values.add(sp.getSelectedItemPosition() == 0 ? "1" : "0");
                            isBools.add("true");
                        }
                    }
                    for (int j = 0; j < mParameterBean2s.size(); j++) {
                        picPaths.add((String) resultImgs.get(i).get(j).getTag());
                    }
                    _bean.setMItems(indexLists);
                    _bean.setMValues(values);
                    _bean.setMPicPaths(picPaths);
                    _bean.setIsBoolList(isBools);
                    _bean.setHandlerAccout(App.handlerAccout);
                    _bean.setResult(dataJudges.get(i));
                    _bean.setWorkid_extra("");
                    _bean.setTimeStamp(System.currentTimeMillis());
                    mResultBean3s.add(_bean);
                    App.getDaoSession().getResultBean3Dao().insert(_bean);
                    // android.util.Log.d("wlDebug", _bean.toString());
                }

                try {
                    // 添加result模板到数据库;
                    int template_id = JdbcUtil.addTemplateResultBean(mTemplateResultBean);
                    for (ResultBean3 _b3 : mResultBean3s) {
                        JdbcUtil.addResult3(mDeviceInfoBean.getFactoryCode(), mDeviceInfoBean.getDeviceCode(), App.getSetupBean().getCodeID(), "", _b3, template_id);
                    }
                    for (TemplatePicBean _bean : templatePicBeans) {
                        _bean.setTemplateResultID(template_id);
                        JdbcUtil.addTemplatePic(_bean);
                    }
                    mTemplateResultBean.setIsUpload(true);
                    App.getDaoSession().getTemplateResultBeanDao().insertOrReplace(mTemplateResultBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showDialog(Input2Activity.this, "上传失败", "上传服务器失败，请检查网络.");
                        }
                    });
                } catch (Throwable e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showDialog(Input2Activity.this, "上传失败", "上传服务器失败，请检查网络.");
                        }
                    });
                }
            } catch (
                    Exception e) {
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
            DialogUtils.showDialog(Input2Activity.this, getResources().getString(R.string.save), getResources().getString(R.string.save_success));
            // openPDFInNative(Input2Activity.this, path);
        }

        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    private String getTextByInputType(Object obj) {
        String str = "";
        if (obj instanceof Spinner) {
            str = String.valueOf(((Spinner) obj).getSelectedItem());
        } else if (obj instanceof EditText) {
            str = ((EditText) obj).getText().toString().trim();
        } else if (obj instanceof TextView) {
            str = ((TextView) obj).getText().toString().trim();
        }
        return str;
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

    private final static int REQUEST_TAKE_PHOTO = 2;

    private final static int RESULT_TAKE_PHOTO = 1;
    private final static int BACKUP_TAKE_PHOTO = 2;
    private int curMode = 0;

    private void dispatchTakePictureIntent(int mode) {
        curMode = mode;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
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

    private String currentPhotoPath;

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

    public class SyncTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog;

        //执行的第一个方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Input2Activity.this);
            dialog.setTitle("续传中.");
            dialog.setMessage("正在刷新数据 , 请稍等.");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        //第二个执行方法,在onPreExecute()后执行，用于后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            //处理耗时操作
            int sum = 0, uploadSum = 0;
            List<TemplateResultBean> templateResultBeans = App.getDaoSession().getTemplateResultBeanDao().queryBuilder().where(TemplateResultBeanDao.Properties.IsUpload.eq(false)).list();
            for (int i = 0; i < templateResultBeans.size(); i++) {
                List<ResultBean3> bean3s = App.getDaoSession().getResultBean3Dao().queryBuilder().where(ResultBean3Dao.Properties.TemplateID.eq(templateResultBeans.get(i).getId())).list();
                sum += bean3s.size();
                // List<TemplatePicBean> picBeans = App.getDaoSession().getTemplatePicBeanDao().queryBuilder().where(TemplatePicBeanDao.Properties.TemplateResultID.eq(templateResultBeans.get(i).getId())).list();
                // templateResultBeans.get(i).setIsUpload(true);
                // App.getDaoSession().getTemplateResultBeanDao().insertOrReplace(templateResultBeans.get(i));
            }
            publishProgress(sum, uploadSum);
            /*            */
            for (TemplateResultBean resultBean : templateResultBeans) {
                try {
                    // 添加result模板到数据库;
                    int template_id = JdbcUtil.addTemplateResultBean(resultBean);
                    List<ResultBean3> bean3s = App.getDaoSession().getResultBean3Dao().queryBuilder().where(ResultBean3Dao.Properties.TemplateID.eq(resultBean.getId())).list();
                    for (ResultBean3 _b3 : bean3s) {
                        JdbcUtil.addResult3(mDeviceInfoBean.getFactoryCode(), mDeviceInfoBean.getDeviceCode(), App.getSetupBean().getCodeID(), "", _b3, template_id);
                    }
                    List<TemplatePicBean> picBeans = App.getDaoSession().getTemplatePicBeanDao().queryBuilder().where(TemplatePicBeanDao.Properties.TemplateResultID.eq(resultBean.getId())).list();
                    for (TemplatePicBean _bean : picBeans) {
                        _bean.setTemplateResultID(template_id);
                        JdbcUtil.addTemplatePic(_bean);
                    }
                    uploadSum += bean3s.size();
                    resultBean.setIsUpload(true);
                    App.getDaoSession().getTemplateResultBeanDao().insertOrReplace(resultBean);
                    publishProgress(sum, uploadSum);
                } catch (Exception e) {
                    android.util.Log.d("wlDebug", "Exception", e);
                    break;
                } catch (Throwable e) {
                    android.util.Log.d("wlDebug", "Throwable", e);
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "后台任务执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
            //"loading..." + progresses[0] + "%"
            dialog.setMessage("共有" + progresses[0] + "条数据待上传，已经上传" + progresses[1] + "条数据.");
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
        }

        @Override
        protected void onCancelled() {

        }
    }
}
