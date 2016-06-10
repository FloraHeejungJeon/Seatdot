package com.superoid.test.seatdot;

import android.graphics.drawable.Drawable;

/**
 * Created by 전희정 on 2016-05-27.
 */
public class Main_ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }


    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }

}
