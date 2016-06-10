package com.superoid.test.seatdot;

/**
 * Created by 전희정 on 2016-06-10.
 */
public class Manage_ListViewItem {

    private String storenameStr ;
    private String branchnameStr ;


    public void setStorename(String storename) {
        storenameStr = storename ;
    }
    public void setBranchname(String branchname) {
        branchnameStr = branchname ;
    }


    public String getStorename() {
        return this.storenameStr ;
    }
    public String getBranchname() {
        return this.branchnameStr ;
    }


}
