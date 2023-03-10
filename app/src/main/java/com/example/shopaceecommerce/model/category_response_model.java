package com.example.shopaceecommerce.model;

public class category_response_model {
    String catid;
    String catname;
    String catimage;

    public category_response_model(String catid, String catname, String catimage) {
        this.catid = catid;
        this.catname = catname;
        this.catimage = catimage;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimage() {
        return catimage;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }
}
