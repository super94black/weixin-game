package org.cet.pojo.menu;

/**
 * @Author zhang
 * @Date 2017/9/26 13:58
 * @Content 菜单类型为view时的封装
 */
public class CommonButtonFix extends Button{

    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
