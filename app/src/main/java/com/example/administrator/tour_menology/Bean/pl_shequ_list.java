package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/14 11:09
 */

public class pl_shequ_list {
    private String content;
    private String nickName;
    private String posterPath;
    private String imgPath;
    private String unitName;
    private String memberId;
    private String unitId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public pl_shequ_list(String content, String nickName, String posterPath, String imgPath, String unitName, String memberId, String unitId) {
        this.content = content;
        this.nickName = nickName;
        this.posterPath = posterPath;
        this.imgPath = imgPath;
        this.unitName = unitName;
        this.memberId = memberId;
        this.unitId = unitId;
    }
}
