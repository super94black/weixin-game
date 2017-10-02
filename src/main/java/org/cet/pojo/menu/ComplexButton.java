package org.cet.pojo.menu;

/**
 * 对 包含有二级菜单的一级菜单进行的封装
 * 该类菜单有两个属性 name sub_button
 * 其中sub_button 就以数组形式展现
 *
 "name":"菜单",
 "sub_button":[
 {
 "type":"view",
 "name":"搜索",
 "url":"http://www.soso.com/"
 },
 *
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
