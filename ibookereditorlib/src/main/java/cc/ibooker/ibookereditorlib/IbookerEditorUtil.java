package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 书客编辑器 - 工具类
 * <p>
 * Created by 邹峰立 on 2018/2/12.
 */
public class IbookerEditorUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 操作的EditText
    private EditText ibookerEd;

    // 构造方法
    IbookerEditorUtil(IbookerEditorEditView ibookerEditorEditView) {
        ibookerEd = ibookerEditorEditView.getIbookerEd();
    }

    // 内部类 - 保存光标相关信息
    private class RangeData {
        private int start, end;
        private String text;

        public RangeData() {
            super();
        }

        RangeData(int start, int end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "RangeData{" +
                    "start=" + start +
                    ", end=" + end +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    /**
     * 获取EditText光标相关信息
     */
    private RangeData getSelectionInfo() {
        int start = ibookerEd.getSelectionStart();
        int end = ibookerEd.getSelectionEnd();
        String text = start == end ? "" : ibookerEd.getText().toString().substring(start, end);
        return new RangeData(start, end, text);
    }

    /**
     * 设置EditText光标相关信息
     */
    private void setSelectionInfo(RangeData data) {
        ibookerEd.setSelection(data.getStart() >= 0 ? data.getStart() : 0, data.getEnd() >= 0 ? data.getEnd() : 0);
    }

    /**
     * 在尾部添加text
     */
    public void addEnd(String addStr) {
        try {
            RangeData rangeData = getSelectionInfo();
            String text = ibookerEd.getText().toString();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String finalTxt = text.substring(0, start) + addStr + text.substring(end, text.length());
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加粗
     */
    public void bold() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            Pattern pattern = Pattern.compile("^[*]+.*[*]+$");
            if (pattern.matcher(selectTxt).matches()) {
                // 如果已加粗，去掉
                selectTxt = selectTxt.replaceAll("^[*]+([^*]*)[*]+$", "$1");
                finalTxt = text.substring(0, start) + selectTxt + text.substring(end, text.length());
            } else {
                finalTxt = text.substring(0, start) + "**" + (TextUtils.isEmpty(selectTxt) ? "加粗" : text.substring(start, end)) + "**" + text.substring(end, text.length());
            }
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 斜体
     */
    public void italic() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            Pattern pattern = Pattern.compile("^[*]+.*[*]+$");
            if (pattern.matcher(selectTxt).matches()) {
                // 如果已加粗，去掉
                selectTxt = selectTxt.replaceAll("^[*]+([^*]*)[*]+$", "$1");
                finalTxt = text.substring(0, start) + selectTxt + text.substring(end, text.length());
            } else {
                finalTxt = text.substring(0, start) + "*" + (TextUtils.isEmpty(selectTxt) ? "斜体" : text.substring(start, end)) + "*" + text.substring(end, text.length());
            }
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除线
     */
    public void strikeout() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            Pattern pattern = Pattern.compile("^[~]+.*[~]+$");
            if (pattern.matcher(selectTxt).matches()) {
                // 如果已删除线，去掉
                selectTxt = selectTxt.replaceAll("^[~]+([^~]*)[~]+$", "$1");
                finalTxt = text.substring(0, start) + selectTxt + text.substring(end, text.length());
            } else {
                finalTxt = text.substring(0, start) + "~~" + (TextUtils.isEmpty(selectTxt) ? "删除线" : text.substring(start, end)) + "~~" + text.substring(end, text.length());
            }
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下划线
     */
    public void underline() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            Pattern pattern = Pattern.compile("^[_]+.*[_]+$");
            if (pattern.matcher(selectTxt).matches()) {
                // 如果已下划线，去掉
                selectTxt = selectTxt.replaceAll("^[_]+([^_]*)[_]+$", "$1");
                finalTxt = text.substring(0, start) + selectTxt + text.substring(end, text.length());
            } else {
                finalTxt = text.substring(0, start) + "__" + (TextUtils.isEmpty(selectTxt) ? "下划线" : text.substring(start, end)) + "__" + text.substring(end, text.length());
            }
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单词首字母大写
     */
    public void capitals() {
        try {
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();

            // 赋值
//        String finalTxt = text;
//        if (selectTxt.length() == 1)
//            finalTxt = text.substring(0, start) + selectTxt.substring(0, 1).toUpperCase() + text.substring(end, text.length());
//        else if (selectTxt.length() > 1)
//            finalTxt = text.substring(0, start) + selectTxt.substring(0, 1).toUpperCase() + selectTxt.substring(1) + text.substring(end, text.length());

            // 赋值 - 无效
//        String finalTxt = text.substring(0, start) + selectTxt.toLowerCase().replaceAll("\\b[a-z]/g", "$1".toUpperCase()) + text.substring(end, text.length());

            // 赋值
            StringBuffer finalTxt = new StringBuffer();
            Pattern p = Pattern.compile("\\b\\w");
            Matcher m = p.matcher(selectTxt.toLowerCase());
            while (m.find()) {
                m.appendReplacement(finalTxt, m.group().toUpperCase());
            }
            m.appendTail(finalTxt);
            ibookerEd.setText((text.substring(0, start) + finalTxt + text.substring(end, text.length())));
            // 设置光标位置
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字母转大写
     */
    public void uppercase() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            finalTxt = text.substring(0, start) + selectTxt.toUpperCase() + text.substring(end, text.length());
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字母转小写
     */
    public void lowercase() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            String selectTxt = rangeData.getText();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            // 赋值
            finalTxt = text.substring(0, start) + selectTxt.toLowerCase() + text.substring(end, text.length());
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一级标题
     */
    public void h1() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("# ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^#\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^#\\s(.*)$", "$1");
                } else {
                    thisLine = "# " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 二级标题
     */
    public void h2() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("## ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^##\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^##\\s(.*)$", "$1");
                } else {
                    thisLine = "## " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 三级标题
     */
    public void h3() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("### ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^###\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^###\\s(.*)$", "$1");
                } else {
                    thisLine = "### " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 四级标题
     */
    public void h4() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("#### ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^####\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^####\\s(.*)$", "$1");
                } else {
                    thisLine = "#### " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 五级标题
     */
    public void h5() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("##### ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^#####\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^#####\\s(.*)$", "$1");
                } else {
                    thisLine = "##### " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 六级标题
     */
    public void h6() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("###### ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^######\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^######\\s(.*)$", "$1");
                } else {
                    thisLine = "###### " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 超链接
     */
    public void link(String link) {
        link(link, "链接描述");
    }

    /**
     * 超链接
     */
    public void link(String link, String desc) {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            StringBuilder finalTxt = new StringBuilder();
            if (TextUtils.isEmpty(link))
                link = "链接地址";
            String tagStr = "\n[" + desc + "](" + link + ")";
            String thisLine;
            if (TextUtils.isEmpty(text)) {
                thisLine = tagStr;
                finalTxt.append(thisLine);
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 重新组织待显示数据
                thisLine += tagStr;
                allLine[line - 1] = thisLine;
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + tagStr.length();
            rangeData.start = rangeData.end = position >= finalTxt.length() ? finalTxt.length() : position;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 引用
     */
    public void quote() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append(">");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^>.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^>(.*)$", "$1");
                } else {
                    thisLine = ">" + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 代码
     */
    public void code() {
        try {
            String finalTxt;
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            String selectTxt = rangeData.text;
            Pattern pattern = Pattern.compile("^`{3}[\\s\\S]*`{3}$");
            if (pattern.matcher(selectTxt).matches()) {
                finalTxt = text.substring(0, start) + selectTxt.replaceAll("^`{3}[\\n]([\\s\\S]*)[\\n]`{3}$", "$1") + text.substring(end, text.length());
            } else {
                finalTxt = text.substring(0, start) + "\n```\n" + selectTxt + "\n```\n" + text.substring(end, text.length());
            }
            ibookerEd.setText(finalTxt);
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length() - 5;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片
     */
    public void imgu(String imgPath) {
        imgu(imgPath, "图片描述");
    }

    /**
     * 图片
     */
    public void imgu(String imgPath, String des) {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            StringBuilder finalTxt = new StringBuilder();
            if (TextUtils.isEmpty(imgPath))
                imgPath = "图片地址";
            String tagStr = "\n![" + des + "](" + imgPath + ")\n";
            String thisLine;
            if (TextUtils.isEmpty(text)) {
                thisLine = tagStr;
                finalTxt.append(thisLine);
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 重新组织待显示数据
                thisLine += tagStr;
                allLine[line - 1] = thisLine;
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + tagStr.length();
            rangeData.start = rangeData.end = position >= finalTxt.length() ? finalTxt.length() : position;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 数字列表
     */
    public void ol() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("1. ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                int newLine = line - 1;
                int j = 1;
                Pattern pattern = Pattern.compile("^\\d+\\.\\s([^\\s]*)$");
                for (int i = 0; i <= newLine; i++) {
                    if (i == newLine && pattern.matcher(allLine[i]).matches()) {
                        allLine[i] = allLine[i].replaceAll("^\\d+\\.\\s([^\\s]*)$", "$1");
                        continue;
                    }
                    if (pattern.matcher(allLine[i]).matches()) {
                        allLine[i] = allLine[i].replaceAll("^\\d+\\.\\s([^\\s]*)$", (j++) + ". " + "$1");
                        continue;
                    }
                    if (i == newLine) {
                        allLine[i] = (j++) + ". " + thisLine;
                        continue;
                    }
                    if ((i - 1) >= 0 && TextUtils.isEmpty(allLine[i - 1]) && !TextUtils.isEmpty(allLine[i]) && !pattern.matcher(allLine[i]).matches()) {
                        j = 1;
                    }
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 普通列表
     */
    public void ul() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("- ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^-\\s.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^-\\s(.*)$", "$1");
                } else {
                    thisLine = "- " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复选框未选中
     */
    public void tasklistsUnChecked() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("- [ ] ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^-\\s+\\[\\s?]\\s+.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^-\\s+\\[\\s?]\\s+(.*)$", "$1");
                } else {
                    thisLine = "- [ ] " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复选框选中
     */
    public void tasklistsChecked() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            StringBuilder finalTxt = new StringBuilder();
            String text = ibookerEd.getText().toString();
            if (TextUtils.isEmpty(text)) {
                finalTxt.append("- [x] ");
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                String thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 处理数据
                Pattern pattern = Pattern.compile("^-\\s+\\[x]\\s+.*$");
                if (pattern.matcher(thisLine).matches()) {
                    thisLine = thisLine.replaceAll("^-\\s+\\[x]\\s+(.*)$", "$1");
                } else {
                    thisLine = "- [x] " + thisLine;
                }

                // 重新组织待显示数据
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }

            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 表格
     */
    public void tables() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            StringBuilder finalTxt = new StringBuilder();
            String tagStr = "\n|  h1   |    h2   |    h3   |"
                    + "\n|:------|:-------:|--------:|"
                    + "\n| 100   | [a][1]  | ![b][2] |"
                    + "\n| *foo* | **bar** | ~~baz~~ |\n";
            String thisLine;
            if (TextUtils.isEmpty(text)) {
                thisLine = tagStr;
                finalTxt.append(thisLine);
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 重新组织待显示数据
                thisLine += tagStr;
                allLine[line - 1] = thisLine;
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + tagStr.length();
            rangeData.start = rangeData.end = position >= finalTxt.length() ? finalTxt.length() : position;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HTML
     */
    public void html() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            StringBuilder finalTxt = new StringBuilder();
            String tagStr = "\n<html>\n<!--在这里插入内容-->\n</html>\n";
            String thisLine;
            if (TextUtils.isEmpty(text)) {
                thisLine = tagStr;
                finalTxt.append(thisLine);
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 重新组织待显示数据
                thisLine += tagStr;
                allLine[line - 1] = thisLine;
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + tagStr.length() - 9;
            rangeData.start = rangeData.end = position >= finalTxt.length() ? finalTxt.length() : position;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分割线
     */
    public void hr() {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            int end = rangeData.end;
            String text = ibookerEd.getText().toString();
            StringBuilder finalTxt = new StringBuilder();
            String tagStr = "\n***\n";
            String thisLine;
            if (TextUtils.isEmpty(text)) {
                thisLine = tagStr;
                finalTxt.append(thisLine);
            } else {
                // 标记每行内容
                int allLineCount = (new IbookerEditorStrUtil()).countStr(text, "\n") + 1;
                String[] allLine = new String[allLineCount];
                String[] allLineTemp = text.split("\n");
                for (int i = 0; i < allLineCount; i++) {
                    if (i < allLineTemp.length)
                        allLine[i] = allLineTemp[i];
                    else
                        allLine[i] = "";
                }

                // 计算当前游标的精准行数-即换行符的个数+1
                String temp = text.substring(0, start);
                int line = (new IbookerEditorStrUtil()).countStr(temp, "\n") + 1;

                // 排除异常情况
                if (line > allLineCount) line = allLineCount;
                if (line < 1) line = 1;

                // 计算当前行的数据
                thisLine = allLine.length <= 0 ? "" : allLine[line - 1];

                // 重新组织待显示数据
                thisLine += tagStr;
                allLine[line - 1] = thisLine;
                if (allLine.length <= 0)
                    finalTxt.append(thisLine);
                else {
                    allLine[line - 1] = thisLine;
                    for (int i = 0; i < allLine.length; i++) {
                        String str = allLine[i];
                        finalTxt.append(str);
                        if (i != allLine.length - 1) {
                            finalTxt.append("\n");
                        }
                    }
                }
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + tagStr.length();
            rangeData.start = rangeData.end = position >= finalTxt.length() ? finalTxt.length() : position;
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取EditText光标所在的位置
     */
    private int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    /**
     * 向EditText指定光标位置插入字符串
     */
    private void insertText(EditText mEditText, String mText) {
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }

    /**
     * 向EditText指定光标位置删除字符串
     */
    private void deleteText(EditText mEditText) {
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1, getEditTextCursorIndex(mEditText));
        }
    }
}
