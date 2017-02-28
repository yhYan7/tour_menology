package com.example.administrator.tour_menology.Bean;

/**
 * name：yhyan
 * date：2016/11/16 13:53
 */

public class friend_list {
    private String addStatus;
    private String headIcon;
    private String nickName;
    private String inviterId;
    private String addressBookId;

    public String getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(String addStatus) {
        this.addStatus = addStatus;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    public String getAddressBookId() {
        return addressBookId;
    }

    public void setAddressBookId(String addressBookId) {
        this.addressBookId = addressBookId;
    }

    public friend_list(String addStatus, String headIcon, String nickName, String inviterId, String addressBookId) {

        this.addStatus = addStatus;
        this.headIcon = headIcon;
        this.nickName = nickName;
        this.inviterId = inviterId;
        this.addressBookId = addressBookId;
    }
}
