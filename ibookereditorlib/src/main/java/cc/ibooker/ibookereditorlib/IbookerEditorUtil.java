package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 书客编辑器 - 工具类
 * Created by 邹峰立 on 2018/2/12.
 */
public class IbookerEditorUtil {
    /**
     * dp转换成px
     *
     * @param value 待转换值
     */
    static int dpToPx(Context context, float value) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
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
     * 加粗
     */
    void bold() {
        // 初始化
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
    }

    /**
     * 斜体
     */
    void italic() {
        // 初始化
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
    }

    /**
     * 删除线
     */
    void strikeout() {
        // 初始化
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
    }

    /**
     * 下划线
     */
    void underline() {
        // 初始化
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
    }

    /**
     * 单词首字母大写
     */
    void capitals() {
        // 初始化
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
//      String finalTxt = text.substring(0, start) + selectTxt.toLowerCase().replaceAll("\\b[a-z]/g", "$1".toUpperCase()) + text.substring(end, text.length());

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
    }

    /**
     * 字母转大写
     */
    void uppercase() {
        // 初始化
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
    }

    /**
     * 字母转小写
     */
    void lowercase() {
        // 初始化
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
    }

    /**
     * 一级标题
     */
    void h1() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^#\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^#\\s(.*)$", "$1");
        } else {
            thisline = "# " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 二级标题
     */
    void h2() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^##\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^##\\s(.*)$", "$1");
        } else {
            thisline = "## " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 三级标题
     */
    void h3() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^###\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^###\\s(.*)$", "$1");
        } else {
            thisline = "### " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 四级标题
     */
    void h4() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^####\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^####\\s(.*)$", "$1");
        } else {
            thisline = "#### " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 五级标题
     */
    void h5() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^#####\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^#####\\s(.*)$", "$1");
        } else {
            thisline = "##### " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 六级标题
     */
    void h6() {
        // 初始化
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        // 赋值
        Pattern pattern = Pattern.compile("^######\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^######\\s(.*)$", "$1");
        } else {
            thisline = "###### " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 超链接
     */
    void link() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        thisline += "\n[链接描述](http://www.ibooker.cc)";
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 引用
     */
    void quote() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");

        Pattern pattern = Pattern.compile("^>.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^>(.*)$", "$1");
        } else {
            thisline = '>' + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 代码
     */
    void code() {
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
    }

    /**
     * 图片
     */
    void imgu() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        thisline += "\n![图片描述](http://ibooker.cc/favicon.ico/)";
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 数字列表
     */
    void ol() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        int j = 1;
        Pattern pattern = Pattern.compile("^\\d+\\.\\s([^\\s]*)$");
        for (int i = 0; i <= line; i++) {
            if (i == line && pattern.matcher(allLine[i]).matches()) {
                allLine[i] = allLine[i].replaceAll("^\\d+\\.\\s([^\\s]*)$", "$1");
                continue;
            }
            if (pattern.matcher(allLine[i].trim()).matches()) {
                allLine[i] = allLine[i].replaceAll("^\\d+\\.\\s([^\\s]*)$", (j++) + ". " + "$1");
                continue;
            }
            if (i == line) {
                allLine[i] = (j++) + ". " + thisline;
                continue;
            }
            if ((i - 1) >= 0 && TextUtils.isEmpty(allLine[i - 1]) && !TextUtils.isEmpty(allLine[i]) && !pattern.matcher(allLine[i]).matches()) {
                j = 1;
            }
        }
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 普通列表
     */
    void ul() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        Pattern pattern = Pattern.compile("^-\\s.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^-\\s(.*)$", "$1");
        } else {
            thisline = "- " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 复选框未选中
     */
    void tasklistsUnChecked() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");

        Pattern pattern = Pattern.compile("^-\\s+\\[\\s?]\\s+.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^-\\s+\\[\\s?]\\s+(.*)$", "$1");
        } else {
            thisline = "- [ ] " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 复选框选中
     */
    void tasklistsChecked() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        int end = rangeData.end;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        Pattern pattern = Pattern.compile("^-\\s+\\[x]\\s+.*$");
        if (pattern.matcher(thisline).matches()) {
            thisline = thisline.replaceAll("^-\\s+\\[x]\\s+(.*)$", "$1");
        } else {
            thisline = "- [x] " + thisline;
        }
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * 表格
     */
    void tables() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        thisline += "\n|  h1   |    h2   |    h3   |"
                + "\n|:------|:-------:|--------:|"
                + "\n| 100   | [a][1]  | ![b][2] |"
                + "\n| *foo* | **bar** | ~~baz~~ |";
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = rangeData.end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
    }

    /**
     * HTML
     */
    void html() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        thisline += "\n<html>\n<!--在这里插入内容-->\n</html>";
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = rangeData.end + finalTxt.length() - text.length() - 9;
        setSelectionInfo(rangeData);
    }

    /**
     * 分割线
     */
    void hr() {
        RangeData rangeData = getSelectionInfo();
        int start = rangeData.start;
        String text = ibookerEd.getText().toString();
        String temp = text.substring(0, start);
        int line = temp.split("\n").length - 1;
        if (line < 0) line = 0;
        String thisline = text.split("\n")[line];
        String[] allLine = text.split("\n");
        thisline = thisline + "\n***";
        allLine[line] = thisline;
        StringBuilder finalTxt = new StringBuilder();
        for (String str : allLine) {
            finalTxt.append(str).append("\n");
        }
        ibookerEd.setText(finalTxt.toString());
        // 设置光标位置
        rangeData.start = rangeData.end = rangeData.end + finalTxt.length() - text.length();
        setSelectionInfo(rangeData);
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
