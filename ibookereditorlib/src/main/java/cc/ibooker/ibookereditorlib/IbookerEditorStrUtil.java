package cc.ibooker.ibookereditorlib;

/**
 * 字符串的管理类
 */
public class IbookerEditorStrUtil {
    private int count = 0;

    /**
     * 判断content中包含text的个数
     *
     * @param content 内容字符串
     * @param text    待判断字符串
     */
    public int countStr(String content, String text) {
        if (content.contains(text)) {
            count++;
            countStr(content.substring(content.indexOf(text) + text.length()), text);
            return count;
        }
        return 0;
    }

}
