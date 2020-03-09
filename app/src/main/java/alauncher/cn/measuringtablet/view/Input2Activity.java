package alauncher.cn.measuringtablet.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.App;
import alauncher.cn.measuringtablet.R;
import alauncher.cn.measuringtablet.base.BaseOActivity;
import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.database.greenDao.db.Parameter2BeanDao;
import alauncher.cn.measuringtablet.utils.ColorConstants;
import alauncher.cn.measuringtablet.widget.BorderTextView;
import butterknife.BindView;

public class Input2Activity extends BaseOActivity {

    @BindView(R.id.input_main_layout)
    public ViewGroup mainLayout;

    public TemplateBean mTemplateBean;

    private List<Parameter2Bean> mParameter2Beans;

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
                __layout.addView(getInfoTV("", Color.WHITE), getItemLayoutParams(1, 1));
            }
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1, 1));
        }

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
            __layout.addView(getInfoTV("记号", ColorConstants.dataTitleColor), getItemLayoutParams(1, 0.6));
            __layout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 0.6));
            __layout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 0.6));
            __layout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getIndex()) : " ", ColorConstants.dataTitleColor), getItemLayoutParams(5, 0.6));
            mainLayout.addView(__layout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6, 1));

            // 绘制上限值;
            LinearLayout upperLayout = new LinearLayout(this);
            upperLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 0.6));
            upperLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            upperLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            upperLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            mainLayout.addView(upperLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6, 1));

            // 绘制下限值;
            LinearLayout lowerLayout = new LinearLayout(this);
            lowerLayout.addView(getInfoTV("上限值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 0.6));
            lowerLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 0.6));
            lowerLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 0.6));
            lowerLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", ColorConstants.dataLineTwoColor), getItemLayoutParams(5, 0.6));
            mainLayout.addView(lowerLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6, 1));


            // 绘制中间值;
            LinearLayout nominalLayout = new LinearLayout(this);
            nominalLayout.addView(getInfoTV("中间值", ColorConstants.dataTitleColor), getItemLayoutParams(1, 0.6));
            nominalLayout.addView(getInfoTV(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            nominalLayout.addView(getInfoTV(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            nominalLayout.addView(getInfoTV(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value()) : " ", ColorConstants.dataLineOneColor), getItemLayoutParams(5, 0.6));
            mainLayout.addView(nominalLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6, 1));

            for (int j = 0; j < 5; j++) {
                LinearLayout dataLayout = new LinearLayout(this);
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)), ColorConstants.dataHeader), getItemLayoutParams(1, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(2, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(3, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(2, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(3, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(2, 0.6));
                dataLayout.addView(getInfoTV(String.valueOf((j + 1)),
                        j % 2 == 1 ? ColorConstants.dataLineOneColor : ColorConstants.dataLineTwoColor), getItemLayoutParams(3, 0.6));

                mainLayout.addView(dataLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6, 1));
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
        bottomLayout.addView(getInfoTV("外观检查一般I级\nAQL=0.15",ColorConstants.bottomColor),getItemLayoutParams(1,0.6 * bottomRow));
        bottomLayout.addView(getInfoTV("RoHS相关: ",ColorConstants.bottomColor),getItemLayoutParams(1,0.6 * bottomRow));

        mainLayout.addView(bottomLayout, getLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0.6 * bottomRow, 1));
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
        tv.setText(msg);
        tv.setTextSize(18);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(color);
        return tv;
    }
}
