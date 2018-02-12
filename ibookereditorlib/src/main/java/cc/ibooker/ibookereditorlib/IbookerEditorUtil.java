package cc.ibooker.ibookereditorlib;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

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
    public IbookerEditorUtil(IbookerEditorEditView ibookerEditorEditView) {
        ibookerEd = ibookerEditorEditView.getIbookerEd();
    }

    // 内部类 - 保存光标相关信息
    private class RangeData {
        private int start, end;
        private String text;

        public RangeData() {
            super();
        }

        public RangeData(int start, int end, String text) {
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
        ibookerEd.setSelection(data.getStart(), data.getEnd());
    }

    /**
     * 加粗
     */
    protected void bold() {
        // 初始化
        String finalTxt;
        RangeData rangeData = getSelectionInfo();
        String selectTxt = rangeData.getText();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        // 赋值
        Pattern pattern = Pattern.compile("^[\\*]+.*[\\*]+$");
        if (pattern.matcher(selectTxt).matches()) {
            // 如果已加粗，去掉
            selectTxt = selectTxt.replaceAll("^[\\*]+([^\\*]*)[\\*]+$", "$1");
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
    protected void italic() {
        // 初始化
        String finalTxt;
        RangeData rangeData = getSelectionInfo();
        String selectTxt = rangeData.getText();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        // 赋值
        Pattern pattern = Pattern.compile("^[\\*]+.*[\\*]+$");
        if (pattern.matcher(selectTxt).matches()) {
            // 如果已加粗，去掉
            selectTxt = selectTxt.replaceAll("^[\\*]+([^\\*]*)[\\*]+$", "$1");
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
    protected void strikeout() {
        // 初始化
        String finalTxt;
        RangeData rangeData = getSelectionInfo();
        String selectTxt = rangeData.getText();
        int start = rangeData.getStart();
        int end = rangeData.getEnd();
        String text = ibookerEd.getText().toString();
        // 赋值
        Pattern pattern = Pattern.compile("^[\\~]+.*[\\~]+$");
        if (pattern.matcher(selectTxt).matches()) {
            // 如果已加粗，去掉
            selectTxt = selectTxt.replaceAll("^[\\~]+([^\\~]*)[\\~]+$", "$1");
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
