package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/12 14:48
 */

public class shangcheng_list  {
    private String integral;
    private String name;
    private String poster;
    private String remoteProId;
    private String remainingCount;
    private String exchangeNote;
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public shangcheng_list(String integral, String name, String poster, String remoteProId, String remainingCount, String exchangeNote, String productId) {

        this.integral = integral;
        this.name = name;
        this.poster = poster;
        this.remoteProId = remoteProId;
        this.remainingCount = remainingCount;
        this.exchangeNote = exchangeNote;
        this.productId = productId;
    }

    public shangcheng_list(String integral, String name, String poster, String remoteProId, String remainingCount, String exchangeNote) {
        this.integral = integral;
        this.name = name;
        this.poster = poster;
        this.remoteProId = remoteProId;
        this.remainingCount = remainingCount;
        this.exchangeNote = exchangeNote;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRemoteProId() {
        return remoteProId;
    }

    public void setRemoteProId(String remoteProId) {
        this.remoteProId = remoteProId;
    }

    public String getRemainingCount() {
        return remainingCount;
    }

    public void setRemainingCount(String remainingCount) {
        this.remainingCount = remainingCount;
    }

    public String getExchangeNote() {
        return exchangeNote;
    }

    public void setExchangeNote(String exchangeNote) {
        this.exchangeNote = exchangeNote;
    }
}
