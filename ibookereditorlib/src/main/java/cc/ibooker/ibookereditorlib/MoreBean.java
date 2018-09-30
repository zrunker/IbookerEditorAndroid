package cc.ibooker.ibookereditorlib;

/**
 * 更多PopupWindow相关数据
 */
public class MoreBean {
    private int res;
    private String name;

    public MoreBean(int res, String name) {
        this.res = res;
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
