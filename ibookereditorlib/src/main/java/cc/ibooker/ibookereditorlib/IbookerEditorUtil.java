package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 书客编辑器 - 工具类
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^#\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^#\\s(.*)$", "$1");
            } else {
                thisline = "# " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^##\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^##\\s(.*)$", "$1");
            } else {
                thisline = "## " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^###\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^###\\s(.*)$", "$1");
            } else {
                thisline = "### " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^####\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^####\\s(.*)$", "$1");
            } else {
                thisline = "#### " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^#####\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^#####\\s(.*)$", "$1");
            } else {
                thisline = "##### " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
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
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts.length == 0 ? "" : texts[line];
            String[] allLine = text.split("\n");
            // 赋值
            Pattern pattern = Pattern.compile("^######\\s.*$");
            if (pattern.matcher(thisline).matches()) {
                thisline = thisline.replaceAll("^######\\s(.*)$", "$1");
            } else {
                thisline = "###### " + thisline;
            }
            StringBuilder finalTxt = new StringBuilder();
            if (allLine.length > 0) {
                if (line < allLine.length)
                    allLine[line] = thisline;
                for (String str : allLine) {
                    finalTxt.append(str).append("\n");
                }
            } else
                finalTxt.append(thisline).append("\n");
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = end + finalTxt.length() - text.length() - 1;
            rangeData.start = rangeData.end = (position > 0 && position > thisline.length()) ? position : thisline.length();
            setSelectionInfo(rangeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 超链接
     */
    public void link(String link) {
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.getStart();
            int end = rangeData.getEnd();
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
            String[] allLine = text.split("\n");
            thisline += "\n" + link;
            allLine[line] = thisline;
            StringBuilder finalTxt = new StringBuilder();
            for (String str : allLine) {
                finalTxt.append(str).append("\n");
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = finalTxt.indexOf(thisline) + thisline.length() + 1;
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
        try {
            RangeData rangeData = getSelectionInfo();
            int start = rangeData.start;
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
            String[] allLine = text.split("\n");
            thisline += "\n" + imgPath;
            allLine[line] = thisline;
            StringBuilder finalTxt = new StringBuilder();
            for (String str : allLine) {
                finalTxt.append(str).append("\n");
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = finalTxt.indexOf(thisline) + thisline.length() + 1;
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
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
            int position = finalTxt.indexOf(thisline) + thisline.length() + 1;
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
            String[] allLine = text.split("\n");
            thisline += "\n<html>\n<!--在这里插入内容-->\n</html>";
            allLine[line] = thisline;
            StringBuilder finalTxt = new StringBuilder();
            for (String str : allLine) {
                finalTxt.append(str).append("\n");
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = finalTxt.indexOf(thisline) + thisline.length() - 8;
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
            String text = ibookerEd.getText().toString();
            String temp = text.substring(0, start);
            int line = temp.split("\n").length - 1;
            String[] texts = text.split("\n");
            if (line >= texts.length) line = texts.length - 1;
            if (line < 0) line = 0;
            String thisline = texts[line];
            String[] allLine = text.split("\n");
            thisline = thisline + "\n***";
            allLine[line] = thisline;
            StringBuilder finalTxt = new StringBuilder();
            for (String str : allLine) {
                finalTxt.append(str).append("\n");
            }
            ibookerEd.setText(finalTxt.toString());
            // 设置光标位置
            int position = finalTxt.indexOf(thisline) + thisline.length() + 1;
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
