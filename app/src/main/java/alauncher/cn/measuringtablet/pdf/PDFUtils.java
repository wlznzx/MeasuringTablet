package alauncher.cn.measuringtablet.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import alauncher.cn.measuringtablet.bean.ResultBean3;
import alauncher.cn.measuringtablet.bean.TemplatePicBean;
import alauncher.cn.measuringtablet.bean.TemplateResultBean;


public class PDFUtils {

    /**
     * 表格各种属性综合使用
     *
     * @throws IOException
     * @throws DocumentException
     */


    // public static final String DEST = "/mnt/sdcard/table_android_table.pdf";


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

    private static ArrayList<String> signatureTitle = new ArrayList<>();

    static {
        signatureTitle.add("课长");
        signatureTitle.add("系长");
        signatureTitle.add("部长");
        signatureTitle.add("担当");
    }

    public static void createNTTable(TemplateResultBean pTemplateResultBean, List<ResultBean3> pResultBean3s, List<TemplatePicBean> pTemplatePicBeans, String path) throws IOException, DocumentException {

        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bf, 8, Font.NORMAL);
        font.setColor(BaseColor.BLACK);

        Document document = new Document(PageSize.A4, -12F, -12F, 36F, 0);
        // 创建PdfWriter对象
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        PDFBuilder builder = new PDFBuilder();
        builder.setHeaders(pTemplateResultBean.headerLeft == null ? "" : pTemplateResultBean.headerLeft,
                pTemplateResultBean.headerMid == null ? "" : pTemplateResultBean.headerMid,
                pTemplateResultBean.headerRight == null ? "" : pTemplateResultBean.headerRight);
        builder.setFooters(pTemplateResultBean.footerLeft == null ? "" : pTemplateResultBean.footerLeft,
                pTemplateResultBean.footerMid == null ? "" : pTemplateResultBean.footerMid,
                pTemplateResultBean.footerRight == null ? "" : pTemplateResultBean.footerRight);
        writer.setPageEvent(builder);
        // 打开文档
        document.open();
        // 添加表格，4列
        PdfPTable table = new PdfPTable(16);


        // 添加Logo;
        if (pTemplateResultBean.getLogoPic() != null) {
            PdfPCell logoCell = new PdfPCell(Image.getInstance(pTemplateResultBean.getLogoPic()), true);
            logoCell.setRowspan(2);
            logoCell.setColspan(2);
            logoCell.setBorderColor(BaseColor.BLACK);
            logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            logoCell.setPaddingLeft(5);
            logoCell.setPaddingRight(5);
            logoCell.setPaddingTop(5);
            logoCell.setPaddingBottom(5);
            table.addCell(logoCell);
        }

        // 添加title
        PdfPCell cell1 = new PdfPCell(new Paragraph(pTemplateResultBean.getTitle(), font));
        cell1.setBorderColor(BaseColor.BLACK);
        cell1.setBackgroundColor(titleColor);
        cell1.setRowspan(2);
        cell1.setColspan(pTemplateResultBean.getLogoPic() != null ? 6 : 8);
        cell1.setPaddingLeft(10);
        cell1.setFixedHeight(20);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell1);

        for (int i = 0; i < pTemplateResultBean.getSignList().size(); i++) {
            PdfPCell cell = getTitleCell(pTemplateResultBean.getSignList().get(i), titleColor);
            cell.setFixedHeight(20);
            // 设置内容水平居中显示
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置垂直居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }
        // 绘制签名栏;
        for (int i = 0; i < signatureTitle.size(); i++) {
            PdfPCell cell;
            if (i == 3) {
                cell = new PdfPCell(new Paragraph(pTemplateResultBean.getUser(), getFont(8)));
            } else {
                cell = new PdfPCell(new Paragraph(""));
            }
            cell.setRowspan(1);
            cell.setColspan(2);
            // cell.setPaddingLeft(10);
            // 设置内容水平居中显示
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            // 设置垂直居中
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        // 每行显示个数;
        int colSize = 4;
        int colTitleSize = (int) Math.ceil((double) pTemplateResultBean.getTitleList().size() / (double) colSize);

        // 绘制标题栏;
        for (int i = 0; i < colTitleSize; i++) {
            for (int j = 0; j < colSize; j++) {
                table.addCell(getTitleCell((i * 4 + j) <
                        pTemplateResultBean.getTitleList().size() ? pTemplateResultBean.getTitleList().get(i * 4 + j) : "", titleColor));
                table.addCell(getTitleCell((i * 4 + j) < pTemplateResultBean.getTitleResultList().size() ? pTemplateResultBean.getTitleResultList().get(i * 4 + j) : "", BaseColor.WHITE));
            }
        }

        // Image cellIMG = Image.getInstance(FOX);
        try {
            Image cellIMG = Image.getInstance(pTemplateResultBean.getImg());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        for (int i = 0; i < titles.size(); i++) {
            table.addCell(getDataCell(titles.get(i), 2, 2));
            table.addCell(getDataCell(" ", 2, 2));
        }
         */

        //每页显示的记录数
        int pageSize = 3;
        //页数
        int pageSum = (int) Math.ceil((double) pTemplateResultBean.getValueIndexs().size() / (double) pageSize);

        android.util.Log.d("wlDebug", "pTemplateResultBean = " + pTemplateResultBean.toString());
        // 计算数据的最大、最小、判定值;
        ArrayList<Double> _maxs = new ArrayList<>();
        ArrayList<Double> _mins = new ArrayList<>();
        ArrayList<Double> _avgs = new ArrayList<>();
        ArrayList<Double> _difs = new ArrayList<>();
        ArrayList<String> _results = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < pTemplateResultBean.getValueIndexs().size(); i++) {
            sum = 0;
            String type = "0";
            if (pTemplateResultBean.getValueTypes() != null) {
                type = pTemplateResultBean.getValueTypes().get(i);
            }
            if (i >= pResultBean3s.get(0).getMValues().size()) {

            } else {
                Double _max = Double.valueOf(-100000);
                Double _min = Double.valueOf(1000000);
                String _result = "OK";
                for (int j = 0; j < pResultBean3s.size(); j++) {
                    ResultBean3 _bean = pResultBean3s.get(j);
                    String _value = _bean.getMValues().get(i);

                    android.util.Log.d("wlDebug", "_bean = " + _bean.toString());
                    android.util.Log.d("wlDebug", "_value = " + _value);

                    boolean isBool = _bean.getIsBoolList().size() > 0 ? _bean.getIsBoolList().get(i).equals("true") : false;
                    if (_value.equals("")) {
                        _result = "";
                        continue;
                    }
                    if (isBool) {
                        if (_value.equals("0")) _result = "NG";
                        continue;
                    }
                    sum += Double.valueOf(_value);
                    if (Double.valueOf(_value) < _min) {
                        _min = Double.valueOf(_value);
                    }
                    if (Double.valueOf(_value) > _max) {
                        _max = Double.valueOf(_value);
                    }
                    try {
                        switch (type) {
                            case "0":
                                if (Double.valueOf(_value) > Double.valueOf(pTemplateResultBean.getUpperToleranceValues().get(i))
                                        || Double.valueOf(_value) < Double.valueOf(pTemplateResultBean.getLowerToleranceValues().get(i))) {
                                    _result = "NG";
                                }
                                break;
                            case "1":
                                if (Double.valueOf(_value) > Double.valueOf(pTemplateResultBean.getUpperToleranceValues().get(i))) {
                                    _result = "NG";
                                }
                                break;
                            case "2":
                                if (Double.valueOf(_value) < Double.valueOf(pTemplateResultBean.getLowerToleranceValues().get(i))) {
                                    _result = "NG";
                                }
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {

                    }

                }
                if (_max == -100000) _max = 0D;
                if (_min == 1000000) _min = 0D;
                _maxs.add(_max);
                _mins.add(_min);
                _avgs.add(BigDecimal.valueOf(sum / pResultBean3s.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                _difs.add(BigDecimal.valueOf(_max - _min).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                _results.add(_result);
            }
        }

        for (int i = 0; i < pageSum; i++) {
            // i * 3 + 0 > mParameter2Beans.size() -1  ? String.valueOf(mParameter2Beans.get(i * 3 + 0).getIndex()) : " ";

            // ParameterBean2 rol1Bean = i * 3 + 0 <= pResultBean3s.get(0).getMValueIndexs().size() - 1 ? pResultBean3s.get(0).getMValueIndexs().get(i * 3 + 0) : null;
            // ParameterBean2 rol2Bean = i * 3 + 1 <= pResultBean3s.get(0).getMValueIndexs().size() - 1 ? pResultBean3s.get(0).getMValueIndexs().get(i * 3 + 1) : null;
            // ParameterBean2 rol3Bean = i * 3 + 2 <= pResultBean3s.get(0).getMValueIndexs().size() - 1 ? pResultBean3s.get(0).getMValueIndexs().get(i * 3 + 2) : null;
            // 绘制数据列第一行
            table.addCell(getDataCell("记号", 1, 1, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getMarkList().size() > i * 3 + 0 ? pTemplateResultBean.getMarkList().get(i * 3 + 0) : " ", 1, 5, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getMarkList().size() > i * 3 + 1 ? pTemplateResultBean.getMarkList().get(i * 3 + 1) : " ", 1, 5, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getMarkList().size() > i * 3 + 2 ? pTemplateResultBean.getMarkList().get(i * 3 + 2) : " ", 1, 5, dataTitleColor));

            // 绘制上限值;
            table.addCell(getDataCell("上限值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getUpperToleranceValues().size() > i * 3 + 0 ?
                    pTemplateResultBean.getUpperToleranceValues().get(i * 3 + 0) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(pTemplateResultBean.getUpperToleranceValues().size() > i * 3 + 1 ?
                    pTemplateResultBean.getUpperToleranceValues().get(i * 3 + 1) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(pTemplateResultBean.getUpperToleranceValues().size() > i * 3 + 2 ?
                    pTemplateResultBean.getUpperToleranceValues().get(i * 3 + 2) : " ", 1, 5, dataLineOneColor));


            // 绘制下限值;
            table.addCell(getDataCell("下限值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getLowerToleranceValues().size() > i * 3 + 0 ?
                    pTemplateResultBean.getLowerToleranceValues().get(i * 3 + 0) : " ", 1, 5, dataLineTwoColor));
            table.addCell(getDataCell(pTemplateResultBean.getLowerToleranceValues().size() > i * 3 + 1 ?
                    pTemplateResultBean.getLowerToleranceValues().get(i * 3 + 1) : " ", 1, 5, dataLineTwoColor));
            table.addCell(getDataCell(pTemplateResultBean.getLowerToleranceValues().size() > i * 3 + 2 ?
                    pTemplateResultBean.getLowerToleranceValues().get(i * 3 + 2) : " ", 1, 5, dataLineTwoColor));

            // 绘制中间值;
            table.addCell(getDataCell("中间值", 1, 1, dataTitleColor));
            table.addCell(getDataCell(pTemplateResultBean.getNominalValues().size() > i * 3 + 0 ?
                    pTemplateResultBean.getNominalValues().get(i * 3 + 0) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(pTemplateResultBean.getNominalValues().size() > i * 3 + 1 ?
                    pTemplateResultBean.getNominalValues().get(i * 3 + 1) : " ", 1, 5, dataLineOneColor));
            table.addCell(getDataCell(pTemplateResultBean.getNominalValues().size() > i * 3 + 2 ?
                    pTemplateResultBean.getNominalValues().get(i * 3 + 2) : " ", 1, 5, dataLineOneColor));

            // 数据；
            for (int j = 0; j < pResultBean3s.size(); j++) {
                String value1 = "", value2 = "", value3 = "";
                if (pResultBean3s.get(j).getIsBoolList() != null) {
                    if (i * 3 + 0 <= (pResultBean3s.get(j).getMValues().size() - 1)) {
                        if (pResultBean3s.get(j).getIsBoolList().get(i * 3 + 0).equals("false")) {
                            value1 = pResultBean3s.get(j).getMValues().get(i * 3 + 0);
                        } else {
                            value1 = pResultBean3s.get(j).getMValues().get(i * 3 + 0).equals("1") ? "OK" : "NG";
                        }
                    } else {
                        value1 = " ";
                    }
                    if (i * 3 + 1 <= (pResultBean3s.get(j).getMValues().size() - 1)) {
                        if (pResultBean3s.get(j).getIsBoolList().get(i * 3 + 1).equals("false")) {
                            value2 = pResultBean3s.get(j).getMValues().get(i * 3 + 1);
                        } else {
                            value2 = pResultBean3s.get(j).getMValues().get(i * 3 + 1).equals("1") ? "OK" : "NG";
                        }
                    } else {
                        value2 = " ";
                    }
                    if (i * 3 + 2 <= (pResultBean3s.get(j).getMValues().size() - 1)) {
                        if (pResultBean3s.get(j).getIsBoolList().get(i * 3 + 2).equals("false")) {
                            value3 = pResultBean3s.get(j).getMValues().get(i * 3 + 2);
                        } else {
                            value3 = pResultBean3s.get(j).getMValues().get(i * 3 + 2).equals("1") ? "OK" : "NG";
                        }
                    } else {
                        value3 = " ";
                    }
                } else {
                    value1 = i * 3 + 0 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 0) : " ";
                    value2 = i * 3 + 1 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 1) : " ";
                    value3 = i * 3 + 2 <= (pResultBean3s.get(j).getMValues().size() - 1) ? pResultBean3s.get(j).getMValues().get(i * 3 + 2) : " ";
                }

                String path1 = i * 3 + 0 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 0) : " ";
                String path2 = i * 3 + 1 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 1) : " ";
                String path3 = i * 3 + 2 <= (pResultBean3s.get(j).getMPicPaths().size() - 1) ? pResultBean3s.get(j).getMPicPaths().get(i * 3 + 2) : " ";
                table.addCell(getDataCell(String.valueOf((j + 1)), 1, 1, dataHeader));
                table.addCell(getDataCell(value1, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                if (path1 == null || path1.equals(" ") || path1.equals("null")) {
                    table.addCell(getDataCell("", 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                } else {
                    table.addCell(getDataImgCell(path1, 1, 3));
                }
                table.addCell(getDataCell(value2, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                if (path2 == null || path2.equals(" ") || path2.equals("null")) {
                    table.addCell(getDataCell("", 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                } else {
                    table.addCell(getDataImgCell(path2, 1, 3));
                }
                table.addCell(getDataCell(value3, 1, 2, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                if (path3 == null || path3.equals(" ") || path3.equals("null")) {
                    table.addCell(getDataCell("", 1, 3, j % 2 == 1 ? dataLineOneColor : dataLineTwoColor));
                } else {
                    table.addCell(getDataImgCell(path3, 1, 3));
                }
            }

            // 最大值；
            if (pTemplateResultBean.getMaximumEnable()) {
                table.addCell(getDataCell("最大值", 1, 1, dataTitleColor));
                table.addCell(getDataCell(i * 3 + 0 < _maxs.size() ?
                        String.valueOf(_maxs.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 1 < _maxs.size() ?
                        String.valueOf(_maxs.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 2 < _maxs.size() ?
                        String.valueOf(_maxs.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));
            }

            // 最小值；
            if (pTemplateResultBean.getMinimumEnable()) {
                table.addCell(getDataCell("最小值", 1, 1, dataTitleColor));
                table.addCell(getDataCell(i * 3 + 0 < _mins.size() ?
                        String.valueOf(_mins.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 1 < _mins.size() ?
                        String.valueOf(_mins.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 2 < _mins.size() ?
                        String.valueOf(_mins.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));
            }

            // 平均值；
            if (pTemplateResultBean.getAverageEnable()) {
                table.addCell(getDataCell("平均值", 1, 1, dataTitleColor));
                table.addCell(getDataCell(i * 3 + 0 < _avgs.size() ?
                        String.valueOf(_avgs.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 1 < _avgs.size() ?
                        String.valueOf(_avgs.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 2 < _avgs.size() ?
                        String.valueOf(_avgs.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));
            }

            // 极差值；
            if (pTemplateResultBean.getRangeEnable()) {
                table.addCell(getDataCell("极差值", 1, 1, dataTitleColor));
                table.addCell(getDataCell(i * 3 + 0 < _difs.size() ?
                        String.valueOf(_difs.get(i * 3 + 0)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 1 < _difs.size() ?
                        String.valueOf(_difs.get(i * 3 + 1)) : " ", 1, 5, dataLineOneColor));
                table.addCell(getDataCell(i * 3 + 2 < _difs.size() ?
                        String.valueOf(_difs.get(i * 3 + 2)) : " ", 1, 5, dataLineOneColor));
            }

            // 判定;
            if (pTemplateResultBean.getJudgeEnable()) {
                table.addCell(getDataCell("判定", 1, 1, dataTitleColor));
                String judge1 = i * 3 + 0 < _results.size() ?
                        String.valueOf(_results.get(i * 3 + 0)) : " ";
                String judge2 = i * 3 + 1 < _results.size() ?
                        String.valueOf(_results.get(i * 3 + 1)) : " ";
                String judge3 = i * 3 + 2 < _results.size() ?
                        String.valueOf(_results.get(i * 3 + 2)) : " ";
                table.addCell(getDataCell(judge1, 1, 5, judge1.equals("OK") ? dataOKColor : judge1.equals("NG") ? dataNGColor : dataTitleColor));
                table.addCell(getDataCell(judge2, 1, 5, judge2.equals("OK") ? dataOKColor : judge2.equals("NG") ? dataNGColor : dataTitleColor));
                table.addCell(getDataCell(judge3, 1, 5, judge3.equals("OK") ? dataOKColor : judge3.equals("NG") ? dataNGColor : dataTitleColor));
            }
        }
        // 底部 6 ，6 ，4；
        int bottomRow = Math.max(Math.max(pTemplateResultBean.getAQLList().size(), pTemplateResultBean.getRoHSList().size()), 5);

//        android.util.Log.d("wlDebug", "bottomRow = " + bottomRow);

        table.addCell(getBottomCell("外观检查一般I级\nAQL=0.15", bottomRow, 2, bottomColor));
        table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > 0 ? pTemplateResultBean.getAQLList().get(0) : " ", 1, 4, titleColor));
        table.addCell(getBottomCell(pTemplateResultBean.getAQLResultList().size() > 0 ? pTemplateResultBean.getAQLResultList().get(0) : " ", 1, 1, BaseColor.WHITE));
        table.addCell(getBottomCell("RoHS相关: ", bottomRow, 2, bottomColor));
        table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > 0 ? pTemplateResultBean.getRoHSList().get(0) : " ", 1, 4, titleColor));
        table.addCell(getBottomCell(pTemplateResultBean.getRoHSResultList().size() > 0 ? pTemplateResultBean.getRoHSResultList().get(0) : " ", 1, 1, BaseColor.WHITE));
        table.addCell(getBottomCell("综合判断", bottomRow - 2, 2, titleColor));

        for (int i = 1; i <= bottomRow - 2; i++) {
            // android.util.Log.d("wlDebug", "i = " + i + " " + pTemplateResultBean.getAQLList().get(i));
            android.util.Log.d("wlDebug", "i = " + i + " " + (pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " "));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > i ? pTemplateResultBean.getAQLList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLResultList().size() > i ? pTemplateResultBean.getAQLResultList().get(i) : " ", 1, 1, BaseColor.WHITE));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSResultList().size() > i ? pTemplateResultBean.getRoHSResultList().get(i) : " ", 1, 1, BaseColor.WHITE));
        }

        table.addCell(getBottomCell(pTemplateResultBean.getAllJudge(), 2, 2,
                pTemplateResultBean.getAllJudge().equals("OK") ? dataOKColor : pTemplateResultBean.getAllJudge().equals("NG") ? dataNGColor : dataTitleColor));

        for (int i = bottomRow - 1; i < bottomRow; i++) {
            android.util.Log.d("wlDebug", "i = " + i + " " + (pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " "));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLList().size() > i ? pTemplateResultBean.getAQLList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getAQLResultList().size() > i ? pTemplateResultBean.getAQLResultList().get(i) : " ", 1, 1, BaseColor.WHITE));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSList().size() > i ? pTemplateResultBean.getRoHSList().get(i) : " ", 1, 4, titleColor));
            table.addCell(getBottomCell(pTemplateResultBean.getRoHSResultList().size() > i ? pTemplateResultBean.getRoHSResultList().get(i) : " ", 1, 1, BaseColor.WHITE));
        }

        // 添加备注
        PdfPCell remarkCell = new PdfPCell(new Paragraph(pTemplateResultBean.getRemarks(), font));
        remarkCell.setBorderColor(BaseColor.BLACK);
        remarkCell.setRowspan(3);
        remarkCell.setColspan(16);
        remarkCell.setPaddingLeft(10);
        remarkCell.setFixedHeight(20);
        remarkCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        remarkCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(remarkCell);

        // 添加备注图片;
        if (pTemplatePicBeans != null) {
            for (TemplatePicBean _bean : pTemplatePicBeans) {
                Image _img = Image.getInstance(_bean.getImg());
                PdfPCell _cell = new PdfPCell(_img, true);
                _cell.setRowspan(8);
                _cell.setColspan(16);
                _cell.setBorderColor(BaseColor.BLACK);
//                _cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                _cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                _cell.setPaddingLeft(2);
                _cell.setPaddingRight(2);
                _cell.setPaddingTop(2);
                _cell.setPaddingBottom(2);
                table.addCell(_cell);
            }
        }

        document.add(table);
        // 关闭文档
        document.close();
    }

    private static PdfPCell getDataImgCell(String path, int row, int col) throws IOException, BadElementException {
        Image cellIMG = Image.getInstance(path);
        PdfPCell cell4 = new PdfPCell(cellIMG, true);
        cell4.setRowspan(row);
        cell4.setColspan(col);
        cell4.setBorderColor(BaseColor.BLACK);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell4.setPaddingLeft(5);
        cell4.setPaddingRight(5);
        cell4.setPaddingTop(5);
        cell4.setPaddingBottom(5);
        return cell4;
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
}
