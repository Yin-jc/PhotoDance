package com.yjc.photodance.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class Links {

    private String self;
    private String html;
    private String download;
    @SerializedName("download_location")
    private String downloadLocation;

    public void setSelf(String self) {
        this.self = self;
    }
    public String getSelf() {
        return self;
    }

    public void setHtml(String html) {
        this.html = html;
    }
    public String getHtml() {
        return html;
    }

    public void setDownload(String download) {
        this.download = download;
    }
    public String getDownload() {
        return download;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }
    public String getDownloadLocation() {
        return downloadLocation;
    }
}
