package alauncher.cn.measuringtablet.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Max;
import org.apache.commons.math3.stat.descriptive.rank.Min;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.ParameterBean;
import alauncher.cn.measuringtablet.bean.ResultBean;
import alauncher.cn.measuringtablet.bean.SetupBean;
import alauncher.cn.measuringtablet.database.greenDao.db.ResultBeanDao;
import alauncher.cn.measuringtablet.utils.CommonUtil;
import alauncher.cn.measuringtablet.utils.Constants;
import alauncher.cn.measuringtablet.utils.DateUtils;
import alauncher.cn.measuringtablet.utils.Format;
import butterknife.BindView;
import butterknife.OnClick;

import static alauncher.cn.measuringtablet.App.getDaoSession;


public class TemplateActivity extends BaseOActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_template);
    }

    @Override
    protected void initView() {

    }


}
