package com.abhorrent.prj.collectioner.Items;

public class MenuItem {

    public String title;
    public boolean checked;
    public int icon;

    public MenuItem(String title,boolean checked,int icon)
    {
        this.title=title;
        this.checked=checked;
        this.icon=icon;
    }
}
