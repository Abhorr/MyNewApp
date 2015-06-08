package com.abhorrent.prj.collectioner.Items;

public class MenuItem {

    public String id;
    public String title;
    public boolean checked;
    public int icon;

    public MenuItem(String id,String title,boolean checked,int icon)
    {
        this.id = id;
        this.title=title;
        this.checked=checked;
        this.icon=icon;
    }
}
