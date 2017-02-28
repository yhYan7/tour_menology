package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/10/11 17:06
 */

public class zhoubian_list {
    private String marketPrice;
    private String nameCn;
    private String defaulrPhoto;
    private String minPrice;
    private String initCode;

    public zhoubian_list(String marketPrice, String nameCn, String defaulrPhoto, String minPrice, String initCode) {
        this.marketPrice = marketPrice;
        this.nameCn = nameCn;
        this.defaulrPhoto = defaulrPhoto;
        this.minPrice = minPrice;
        this.initCode = initCode;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getDefaulrPhoto() {
        return defaulrPhoto;
    }

    public void setDefaulrPhoto(String defaulrPhoto) {
        this.defaulrPhoto = defaulrPhoto;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getInitCode() {
        return initCode;
    }

    public void setInitCode(String initCode) {
        this.initCode = initCode;
    }


}
