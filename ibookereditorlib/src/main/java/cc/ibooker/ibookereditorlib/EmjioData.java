package cc.ibooker.ibookereditorlib;

/**
 * Emjio相关数据
 */
public class EmjioData {
    private String text;
    private String emjioStr;

    public EmjioData() {
        super();
    }

    public EmjioData(String text, String emjioStr) {
        this.text = text;
        this.emjioStr = emjioStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmjioStr() {
        return emjioStr;
    }

    public void setEmjioStr(String emjioStr) {
        this.emjioStr = emjioStr;
    }

    @Override
    public String toString() {
        return "EmjioData{" +
                "text='" + text + '\'' +
                ", emjioStr='" + emjioStr + '\'' +
                '}';
    }
}
