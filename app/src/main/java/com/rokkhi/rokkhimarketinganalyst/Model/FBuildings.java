package com.rokkhi.rokkhimarketinganalyst.Model;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class FBuildings {

    @Exclude
    private String id;

    private String b_address;
    private String b_caretakernam;
    private String b_caretakernmbr;
    private String b_code;
    private String b_flatfrmt;
    private String b_flatperfloor;
    private String b_followupdate;
    private String b_guardname;
    private String b_guardnmbr;
    private String b_guards;
    private String b_housename;
    private String b_ownername;
    private String b_ownernmbr;
    private String b_totalfloor;
    private String b_visiteddate;
    private String b_status;
    private List<String> b_array_code;
    private String b_lat;
    private String b_long;


    public FBuildings() {
    }


    public FBuildings(String b_address, String b_caretakernam, String b_caretakernmbr, String b_code,
                      String b_flatfrmt, String b_flatperfloor, String b_followupdate, String b_guardname,
                      String b_guardnmbr, String b_guards, String b_housename, String b_ownername,
                      String b_ownernmbr, String b_totalfloor, String b_visiteddate, String b_status
                      ,String b_lat, String b_long,List<String> b_array_code) {
        this.b_address = b_address;
        this.b_caretakernam = b_caretakernam;
        this.b_caretakernmbr = b_caretakernmbr;
        this.b_code = b_code;
        this.b_flatfrmt = b_flatfrmt;
        this.b_flatperfloor = b_flatperfloor;
        this.b_followupdate = b_followupdate;
        this.b_guardname = b_guardname;
        this.b_guardnmbr = b_guardnmbr;
        this.b_guards = b_guards;
        this.b_housename = b_housename;
        this.b_ownername = b_ownername;
        this.b_ownernmbr = b_ownernmbr;
        this.b_totalfloor = b_totalfloor;
        this.b_visiteddate = b_visiteddate;
        this.b_status=b_status;
        this.b_lat=b_lat;
        this.b_long=b_long;
        this.b_array_code=b_array_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getB_array_code() {
        return b_array_code;
    }

    public void setB_array_code(List<String> b_array_code) {
        this.b_array_code = b_array_code;
    }

    public String getB_lat() {
        return b_lat;
    }

    public void setB_lat(String b_lat) {
        this.b_lat = b_lat;
    }

    public String getB_long() {
        return b_long;
    }

    public void setB_long(String b_long) {
        this.b_long = b_long;
    }

    public String getB_address() {
        return b_address;
    }

    public void setB_address(String b_address) {
        this.b_address = b_address;
    }

    public String getB_caretakernam() {
        return b_caretakernam;
    }

    public void setB_caretakernam(String b_caretakernam) {
        this.b_caretakernam = b_caretakernam;
    }

    public String getB_caretakernmbr() {
        return b_caretakernmbr;
    }

    public void setB_caretakernmbr(String b_caretakernmbr) {
        this.b_caretakernmbr = b_caretakernmbr;
    }

    public String getB_code() {
        return b_code;
    }

    public void setB_code(String b_code) {
        this.b_code = b_code;
    }

    public String getB_flatfrmt() {
        return b_flatfrmt;
    }

    public void setB_flatfrmt(String b_flatfrmt) {
        this.b_flatfrmt = b_flatfrmt;
    }

    public String getB_flatperfloor() {
        return b_flatperfloor;
    }

    public void setB_flatperfloor(String b_flatperfloor) {
        this.b_flatperfloor = b_flatperfloor;
    }

    public String getB_followupdate() {
        return b_followupdate;
    }

    public void setB_followupdate(String b_followupdate) {
        this.b_followupdate = b_followupdate;
    }

    public String getB_guardname() {
        return b_guardname;
    }

    public void setB_guardname(String b_guardname) {
        this.b_guardname = b_guardname;
    }

    public String getB_guardnmbr() {
        return b_guardnmbr;
    }

    public void setB_guardnmbr(String b_guardnmbr) {
        this.b_guardnmbr = b_guardnmbr;
    }

    public String getB_guards() {
        return b_guards;
    }

    public void setB_guards(String b_guards) {
        this.b_guards = b_guards;
    }

    public String getB_housename() {
        return b_housename;
    }

    public void setB_housename(String b_housename) {
        this.b_housename = b_housename;
    }

    public String getB_ownername() {
        return b_ownername;
    }

    public void setB_ownername(String b_ownername) {
        this.b_ownername = b_ownername;
    }

    public String getB_ownernmbr() {
        return b_ownernmbr;
    }

    public void setB_ownernmbr(String b_ownernmbr) {
        this.b_ownernmbr = b_ownernmbr;
    }

    public String getB_totalfloor() {
        return b_totalfloor;
    }

    public void setB_totalfloor(String b_totalfloor) {
        this.b_totalfloor = b_totalfloor;
    }

    public String getB_visiteddate() {
        return b_visiteddate;
    }

    public void setB_visiteddate(String b_visiteddate) {
        this.b_visiteddate = b_visiteddate;
    }

    public String getB_status() {
        return b_status;
    }

    public void setB_status(String b_status) {
        this.b_status = b_status;
    }
}
