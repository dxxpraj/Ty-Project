package com.example.shopaceecommerce.model;

public class product_response_model {
    private String pid;
    private String catid;
    private String pname;
    private String pimage;
    private String pprice;
    private String pdesc;
    private String availability;

    public product_response_model(String pid, String catid, String pname, String pimage, String pprice, String pdesc, String availability) {
        this.pid = pid;
        this.catid = catid;
        this.pname = pname;
        this.pimage = pimage;
        this.pprice = pprice;
        this.pdesc = pdesc;
        this.availability = availability;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
