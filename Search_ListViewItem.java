package com.superoid.test.seatdot;

import android.graphics.drawable.Drawable;

/**
 * Created by 전희정 on 2016-06-09.
 */
public class Search_ListViewItem {

        private Drawable iconDrawable ;
        private String nameStr ;
        private String addressStr ;
        private String conStr ;
        private String stIdx;

    public void setIcon(Drawable icon) {
            iconDrawable = icon ;
        }
        public void setName(String name) {
            nameStr = name ;
        }
        public void setAddress(String address) {
            addressStr = address ;
        }
        public void setCon(String con) {
            conStr = con ;
        }
        public void setIdx(String idx) {
            stIdx = idx ;
        }


        public Drawable getIcon() {
            return this.iconDrawable ;
        }
        public String getName() {
            return this.nameStr ;
        }
        public String getAddress() {
            return this.addressStr ;
        }
        public String getCon() {
            return this.conStr ;
        }
        public String getIdx() {
            return this.stIdx ;
        }


}
