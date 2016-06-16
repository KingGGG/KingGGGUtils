package com.KinFourGUtils.bean;

/**
 * 作者：KingGGG on 16/1/20 13:59
 * 描述：
 */
public class ContactsMemberBean {
    private String name;
    private String phone;
    private String full_PinYin;
    private String first_PinYin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFull_PinYin(String full_PinYin) {
        this.full_PinYin = full_PinYin;
    }

    public void setFirst_PinYin(String first_PinYin) {
        this.first_PinYin = first_PinYin;
    }

    public String getFull_PinYin() {
        return full_PinYin;
    }

    public String getFirst_PinYin() {
        return first_PinYin;
    }
}

