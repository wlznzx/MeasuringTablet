package alauncher.cn.measuringtablet.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.bean.Parameter2Bean;
import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplateBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;


public class PDFUtils {

    public static final String FOX = "/mnt/sdcard/timg.jpg";

    /**
     * 表格各种属性综合使用
     *
     * @throws IOException
     * @throws DocumentException
     */


    public static final String DEST = "/mnt/sdcard/table_android_table.pdf";


    // E4F2F6
    private static BaseColor titleColor = new BaseColor(228, 242, 246);

    private static BaseColor dataTitleColor = new BaseColor(92, 167, 188);

    private static BaseColor dataLineOneColor = new BaseColor(176, 217, 239);

    private static BaseColor dataLineTwoColor = new BaseColor(225, 240, 249);

    private static BaseColor dataOKColor = new BaseColor(0, 153, 102);

    private static BaseColor dataNGColor = new BaseColor(238, 64, 0);

    private static BaseColor bottomColor = new BaseColor(210, 234, 240);

    private static BaseColor judgeColor = new BaseColor(244, 250, 251);

    private static BaseColor dataHeader = new BaseColor(158, 224, 219);

    //  0, 153, 102 合格

    private static ArrayList<String> signatureTitle = new ArrayList<String>();

    static {
        signatureTitle.add("课长");
        signatureTitle.add("系长");
        signatureTitle.add("担当");
    }

    private static ArrayList<String> titles = new ArrayList<String>();

    static {
        titles.add("部品名称");
        titles.add("进货批量");
        titles.add("进货日期");
        titles.add("检查日期");
    }

    // private static ArrayList<Parameter2Bean> mParameter2Beans = new ArrayList<>();

    // private static ArrayList<ResultBean3> pResultBean3s = new ArrayList<>();

    // private static TemplateBean mTemplateBean = new TemplateBean();

    public static void initDatas() {
//        for (int j = 1; j <= 7; j++) {
//            Parameter2Bean _bean = new Parameter2Bean();
//            _bean.setCode_id(1);
//            _bean.setIndex(j);
//            _bean.setEnable(true);
//            _bean.setDescribe("内径" + j);
//            _bean.setNominal_value(24.0);
//            _bean.setUpper_tolerance_value(0.04);
//            _bean.setLower_tolerance_value(0.0);
//            mParameter2Beans.add(_bean);
//        }

        for (int i = 0; i < 6; i++) {
            ResultBean3 _bean = new ResultBean3();
            _bean.setCodeID(1);
            _bean.setHandlerAccout("hhb");
            _bean.setResult("OK");
            _bean.setWorkid_extra("");
            ArrayList<String> values = new ArrayList<>();
            ArrayList<String> picPaths = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                values.add(String.valueOf(j));
                picPaths.add(String.valueOf(j));
            }
            _bean.setMValues(values);
            _bean.setMPicPaths(picPaths);
            // pResultBean3s.add(_bean);
        }

        ArrayList<String> Titles = new ArrayList<>();
        Titles.add("部品名称");
        Titles.add("进货批量");
        Titles.add("进货日期");
        Titles.add("检查日期");
        Titles.add("部品代号");
        Titles.add("尺寸检查");
        Titles.add("生产日期");
        Titles.add("部品发送到");
        Titles.add("材料名称");
        Titles.add("订单No");
        Titles.add("检查目的");
        // Titles.add("供货方名称");
        ArrayList<String> AQLList = new ArrayList<>();
        AQLList.add("无毛刺、无异物、无气孔");
        AQLList.add("无收缩、无裂缝、无缺陷");
        AQLList.add("毛刺高度控制在0.3以下");
        AQLList.add("外包装无破损、无变形、无潮湿");
        AQLList.add("月份标签确认");
        AQLList.add("供应商数据确认");
        ArrayList<String> RoHSList = new ArrayList<>();
        RoHSList.add("RoHS确认频率");
        RoHSList.add("本批确认");
        RoHSList.add("上回RoHS确认日");
        RoHSList.add("确认结果(RoHS检查数据以检查日期追溯)");
        RoHSList.add("模号:");
//        mTemplateBean.setAQLList(AQLList);
//        mTemplateBean.setRoHSList(RoHSList);
//        mTemplateBean.setTitleList(Titles);
    }

    public static void createNTTable(TemplateBean mTemplateBean, TemplateResultBean pTemplateResultBean,
                                     List<Parameter2Bean> mParameter2Beans, List<ResultBean3> pResultBean3s, byte[] img) throws IOException, DocumentException {

        initDatas();

        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bf, 8, Font.NORMAL);
        font.setColor(BaseColor.BLACK);

        Document document = new Document();
        // 创建PdfWriter对象
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));

        // writer.setPageEvent(new PDFBuilder());
        // 打开文档
        document.open();
        // 添加表格，4列
        PdfPTable table = new PdfPTable(16);
        // 构建每个单元格
        PdfPCell cell1 = new PdfPCell(new Paragraph(mTemplateBean.getTitle(), font));
        // 边框颜色
        cell1.setBorderColor(BaseColor.BLACK);
        // 设置背景颜色
        cell1.setBackgroundColor(titleColor);
        // 设置跨两行
        cell1.setRowspan(2);
        // 列数;
        cell1.setColspan(10);
        // 设置距左边的距离
        cell1.setPaddingLeft(10);
        // 设置高度
        cell1.setFixedHeight(20);
        // 设置内容水平居中显示
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell1);

        for (int i = 0; i < signatureTitle.size(); i++) {
            PdfPCell cell = getTitleCell(signatureTitle.get(i), titleColor);
            cell.setFixedHeight(20);
            // 设置内容水平居中显示
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置垂直居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }
        // 绘制签名栏;
        for (int i = 0; i < signatureTitle.size(); i++) {
            PdfPCell cell = new PdfPCell(new Paragraph(" "));
            cell.setRowspan(1);
            cell.setColspan(2);
            cell.setPaddingLeft(10);
            // 设置内容水平居中显示
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置垂直居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        // 每行显示个数;
        int colSize = 4;
        int colTitleSize = (int) Math.ceil((double) mTemplateBean.getTitleList().size() / (double) colSize);

        // 绘制标题栏;
        for (int i = 0; i < colTitleSize; i++) {
            for (int j = 0; j < colSize; j++) {
                table.addCell(getTitleCell((i * 4 + j) <
                        mTemplateBean.getTitleList().size() ? mTemplateBean.getTitleList().get(i * 4 + j) : "", titleColor));
                table.addCell(getTitleCell((i * 4 + j) < pTemplateResultBean.getTitleList().size() ? pTemplateResultBean.getTitleList().get(i * 4 + j) : "", BaseColor.WHITE));
            }
        }

        // Image cellIMG = Image.getInstance(FOX);
        Image cellIMG = Image.getInstance(img);
        PdfPCell cell4 = new PdfPCell(cellIMG, true);
        cell4.setRowspan(10);
        cell4.setColspan(16);
        cell4.setBorderColor(BaseColor.BLACK);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell4.setPaddingLeft(5);
        cell4.setPaddingRight(5);
        cell4.setPaddingTop(5);
        cell4.setPaddingBottom(5);
        table.addCell(cell4);

        /*
        for (int i = 0; i < titles.size(); i++) {
            table.addCell(getDataCell(titles.get(i), 2, 2));
            table.addCell(getDataCell(" ", 2, 2));
        }
         */

        //每页显示的记录数
        int pageSize = 3;
        //页数
        int pageSum = (int) Math.ceil((double) mParameter2Beans.size() / (double) pageSize);


        // 计算数据的最大、最小、判定值;
        ArrayList<Double> _maxs = new ArrayList<>();
        ArrayList<Double> _mins = new ArrayList<>();
        ArrayList<String> _results = new ArrayList<>();
        for (int i = 0; i < mParameter2Beans.size(); i++) {
            if (i >= pResultBean3s.get(0).getMValues().size()) {

            } else {
                Double _max = Double.valueOf(-100000);
                Double _min = Double.valueOf(1000000);
                String _result = "OK";
                for (int j = 0; j < pResultBean3s.size(); j++) {
                    ResultBean3 _bean = pResultBean3s.get(j);
                    if (Double.valueOf(_bean.getMValues().get(i)) < _min) {
                        _min = Double.valueOf(_bean.getMValues().get(i));
                    }
                    if (Double.valueOf(_bean.getMValues().get(i)) > _max) {
                        _max = Double.valueOf(_bean.getMValues().get(i));
                    }
                    if (_bean.getResult().equals("NG")) {
                        _result = "NG";
                    }
                }
                _maxs.add(_max);
                _mins.add(_min);
                _results.add(_result);
            }
        }

        android.util.Log.d("wlDebug", "_maxs = " + _maxs);
        android.util.Log.d("wlDebug", "_mins = " + _mins);
        android.util.Log.d("wlDebug", "_results = " + _results);
        for (int i = 0; i < pageSum; i++) {
            // i * 3 + 0 > mParameter2Beans.size() -1  ? String.valueOf(mParameter2Beans.get(i * 3 + 0).getIndex()) : " ";

            Parameter2Bean rol1Bean = i * 3 + 0 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 0) : null;
            Parameter2Bean rol2Bean = i * 3 + 1 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 1) : null;
            Parameter2Bean rol3Bean = i * 3 + 2 <= mParameter2Beans.size() - 1 ? mParameter2Beans.get(i * 3 + 2) : null;
            // 绘制数据列第一行
            table.addCell(getDataCell("记号", 1, 1, dataTitleColor));
            table.addCell(getDataCell(rol1Bean != null ? String.valueOf(rol1Bean.getIndex()) : " ", 1, 5, dataTitleColor));
            table.addCell(getDataCell(rol2Bean != null ? String.valueOf(rol2Bean.getIndex()) : " ", 1, 5, dataTitleColor));
            table.addCell(getDataCell(rol3Bean != null ? String.valueOf(rol3Bean.getIndex()) : " ", 1, 5, dataTitleColor));

            // 绘制上限值;
            table.addCell(getDataCell("上限值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getUpper_tolerance_value()) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol2Bean.getUpper_tolerance_value()) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol3Bean.getUpper_tolerance_value()) : " ", 1, 5, dataLineOneColor));

            // 绘制下限值;
            table.addCell(getDataCell("下限值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value() + rol1Bean.getLower_tolerance_value()) : " ", 1, 5, dataLineTwoColor));
            table.addCell(getDataCell(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value() + rol2Bean.getLower_tolerance_value()) : " ", 1, 5, dataLineTwoColor));
            table.addCell(getDataCell(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value() + rol3Bean.getLower_tolerance_value()) : " ", 1, 5, dataLineTwoColor));

            // 绘制中间值;
            table.addCell(getDataCell("中间值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(rol1Bean != null ?
                    String.valueOf(rol1Bean.getNominal_value()) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(rol2Bean != null ?
                    String.valueOf(rol2Bean.getNominal_value()) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(rol3Bean != null ?
                    String.valueOf(rol3Bean.getNominal_value()) : " ", 1, 5, dataLineOneColor));

            // 数据；
            for (int j = 0; j < pResultBean3s.size(); j++) {
                String value1 = i * 3 + 0 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 0) : " ";
                String path1 = i * 3 + 0 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 0) : " ";
                String value2 = i * 3 + 1 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 1) : " ";
                String path2 = i * 3 + 1 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 1) : " ";
                String value3 = i * 3 + 2 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 2) : " ";
                String path3 = i * 3 + 2 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 2) : " ";
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
        }
        // 底部 6 ，6 ，4；
        int bottomRow = Math.max(Math.max(mTemplateBean.getAQLList().size(), mTemplateBean.getRoHSList().size()), 5);

        android.util.Log.d("wlDebug", "bottomRow = " + bottomRow);

        table.addCell(getBottomCell("外观检查一般I级\nAQL=0.15", bottomRow, 2, bottomColor));
        table.addCell(getBottomCell(mTemplateBean.getAQLList().size() > 0 ? mTemplateBean.getAQLList().get(0) : " ", 1, 4, titleColor));
        table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > 0 ? pTemplateResultBean.getAQLList().get(0) : " ", 1, 1, BaseColor.WHITE));
        table.addCell(getBottomCell("RoHS相关: ", bottomRow, 2, bottomColor));
        table.addCell(getBottomCell(mTemplateBean.getRoHSList().size() > 0 ? mTemplateBean.getRoHSList().get(0) : " ", 1, 4, titleColor));
        table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > 0 ? pTemplateResultBean.getRoHSList().get(0) : " ", 1, 1, BaseColor.WHITE));
        table.addCell(getBottomCell("综合判断", bottomRow - 2, 2, titleColor));

        for (int i = 1; i <= bottomRow - 2; i++) {
            // android.util.Log.d("wlDebug", "i = " + i + " " + mTemplateBean.getAQLList().get(i));
            android.util.Log.d("wlDebug", "i = " + i + " " + (mTemplateBean.getRoHSList().size() > i ? mTemplateBean.getRoHSList().get(i) : " "));
            table.addCell(getBottomCell(mTemplateBean.getAQLList().size() > i ? mTemplateBean.getAQLList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > i ? pTemplateResultBean.getAQLList().get(i) : " ", 1, 1, BaseColor.WHITE));
            table.addCell(getBottomCell(mTemplateBean.getRoHSList().size() > i ? mTemplateBean.getRoHSList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " ", 1, 1, BaseColor.WHITE));
        }

        table.addCell(getBottomCell("不合格", 2, 2, judgeColor));

        for (int i = bottomRow - 1; i < bottomRow; i++) {
            android.util.Log.d("wlDebug", "i = " + i + " " + (mTemplateBean.getRoHSList().size() > i ? mTemplateBean.getRoHSList().get(i) : " "));
            table.addCell(getBottomCell(mTemplateBean.getAQLList().size() > i ? mTemplateBean.getAQLList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > i ? pTemplateResultBean.getAQLList().get(i) : " ", 1, 1, BaseColor.WHITE));
            table.addCell(getBottomCell(mTemplateBean.getRoHSList().size() > i ? mTemplateBean.getRoHSList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " ", 1, 1, BaseColor.WHITE));
        }
        document.add(table);
        // 关闭文档
        document.close();
    }


    private static PdfPCell getTitleCell(String msg, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(msg, getFont(8)));
        cell.setBackgroundColor(backgroundColor);
        cell.setRowspan(1);
        cell.setColspan(2);
        // 设置距左边的距离
        // 设置高度
        cell.setFixedHeight(20);
        // 设置内容水平居中显示
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static PdfPCell getDataCell(String msg, int row, int col, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(msg, getFont(6, BaseColor.BLACK)));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setBackgroundColor(backgroundColor);
        cell.setRowspan(row);
        cell.setColspan(col);
        // 设置距左边的距离
        // 设置高度
        cell.setFixedHeight(10);
        // 设置内容水平居中显示
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static PdfPCell getBottomCell(String msg, int row, int col, BaseColor backgroundColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(msg, getBlackFont(4)));
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBackgroundColor(backgroundColor);
        cell.setRowspan(row);
        cell.setColspan(col);
        // 设置距左边的距离
        // 设置高度
        cell.setFixedHeight(10);
        // 设置内容水平居中显示
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private static Font getFont(int size) {
        Font _font = null;
        try {
            if (_font == null) {
                BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                _font = new Font(bf, 8, Font.NORMAL);
                _font.setColor(BaseColor.BLACK);
            }
            _font.setSize(size);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _font;
    }

    private static Font font = null;

    private static Font getFont(int size, BaseColor color) {
        try {
            if (font == null) {
                BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                font = new Font(bf, 8, Font.NORMAL);
                font.setColor(BaseColor.BLACK);
            }
            font.setSize(size);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }

    private static Font blackFont = null;

    private static Font getBlackFont(int size) {
        try {
            if (blackFont == null) {
                BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                blackFont = new Font(bf, size, Font.NORMAL);
                blackFont.setColor(BaseColor.BLACK);
            }
            font.setSize(size);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blackFont;
    }

    // 模板
    public PdfTemplate total;

    // 基础字体对象
    public BaseFont bf = null;

    // 利用基础字体生成的字体对象，一般用于生成中文文字
    public Font fontDetail = null;

    public int presentFontSize = 8;

    public String header = "itext测试页眉";

    public void addPage(PdfWriter writer, Document document) {
        //设置分页页眉页脚字体
        try {
            if (bf == null) {
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            }
            if (fontDetail == null) {
                fontDetail = new Font(bf, presentFontSize, Font.NORMAL);// 数据体字体
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1.写入页眉
        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_LEFT, new Phrase(header, fontDetail),
                document.left(), document.top() + 20, 0);
        // 2.写入前半部分的 第 X页/共
        int pageS = writer.getPageNumber();
        String foot1 = "第 " + pageS + " 页 /共";
        Phrase footer = new Phrase(foot1, fontDetail);

        // 3.计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
        float len = bf.getWidthPoint(foot1, presentFontSize);

        // 4.拿到当前的PdfContentByte
        PdfContentByte cb = writer.getDirectContent();

        // 5.写入页脚1，x轴就是(右margin+左margin + right() -left()- len)/2.0F
        // 再给偏移20F适合人类视觉感受，否则肉眼看上去就太偏左了
        // ,y轴就是底边界-20,否则就贴边重叠到数据体里了就不是页脚了；注意Y轴是从下往上累加的，最上方的Top值是大于Bottom好几百开外的。
        ColumnText.showTextAligned(
                cb,
                Element.ALIGN_CENTER,
                footer,
                (document.rightMargin() + document.right()
                        + document.leftMargin() - document.left() - len) / 2.0F + 20F,
                document.bottom() - 20, 0);

        // 6.写入页脚2的模板（就是页脚的Y页这俩字）添加到文档中，计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F +
        // len ， y 轴和之前的保持一致，底边界-20
        cb.addTemplate(total, (document.rightMargin() + document.right()
                        + document.leftMargin() - document.left()) / 2.0F + 20F,
                document.bottom() - 20); // 调节模版显示的位置

    }
}
