package com.nucleus.events.clubhub.Model;

public class ClubsModel {

String ccollage,cname,clogo,ccatagory,crating,cmembers,clubuid;

    public ClubsModel(String ccollage, String cname, String clogo, String ccatagory, String crating, String cmembers, String clubuid) {
        this.ccollage = ccollage;
        this.cname = cname;
        this.clogo = clogo;
        this.ccatagory = ccatagory;
        this.crating = crating;
        this.cmembers = cmembers;
        this.clubuid = clubuid;
    }

    public ClubsModel() {
    }

    public String getClubuid() {
        return clubuid;
    }

    public void setClubuid(String clubuid) {
        this.clubuid = clubuid;
    }

    public String getCcollage() {
        return ccollage;
    }

    public void setCcollage(String ccollage) {
        this.ccollage = ccollage;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getClogo() {
        return clogo;
    }

    public void setClogo(String clogo) {
        this.clogo = clogo;
    }

    public String getCcatagory() {
        return ccatagory;
    }

    public void setCcatagory(String ccatagory) {
        this.ccatagory = ccatagory;
    }

    public String getCrating() {
        return crating;
    }

    public void setCrating(String crating) {
        this.crating = crating;
    }

    public String getCmembers() {
        return cmembers;
    }

    public void setCmembers(String cmembers) {
        this.cmembers = cmembers;
    }
}
