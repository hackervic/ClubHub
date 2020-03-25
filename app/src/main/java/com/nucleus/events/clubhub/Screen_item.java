package com.nucleus.events.clubhub;

public class Screen_item {

String stitle ,sdescription;
int screenimg;

    public Screen_item(String stitle, String sdescription, int screenimg) {
        this.stitle = stitle;
        this.sdescription = sdescription;
        this.screenimg = screenimg;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public String getSdescription() {
        return sdescription;
    }

    public void setSdescription(String sdescription) {
        this.sdescription = sdescription;
    }

    public int getScreenimg() {
        return screenimg;
    }

    public void setScreenimg(int screenimg) {
        this.screenimg = screenimg;
    }
}
