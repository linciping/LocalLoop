package com.mengyou.localloop.model;

import com.avos.avoscloud.AVUser;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/3/14.
 */
public class User extends AVUser {

    private String nickName;
    private String sign;
    private int rank;
    private int integralb;
    private int sex;
    private String addr;
    private String certificationinfo;
    private int integrala;
    private String area;
    private String pinyinindex;
    private String mobilePhoneNumber;
    private String iconUrl;

    public User() {
        init();
    }

    public void init()
    {

    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPinyinindex() {
        return getString("pinyinindex");
    }
    public String getPinyinindexMy(){return pinyinindex;};

    public void setPinyinindex(String pinyinindex) {
        this.pinyinindex = pinyinindex;
    }

    public String getArea() {
        return getString("area");
    }
    public String getAreaMy() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCertificationinfo() {
        return getString("certificationinfo");
    }
    public String getCertificationinfoMy() {
        return certificationinfo;
    }

    public void setCertificationinfo(String certificationinfo) {
        this.certificationinfo = certificationinfo;
    }

    public int getSex() {
        return getInt("sex");
    }
    public int getSexMy() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getIntegrala() {
        return getInt("integrala");
    }
    public int getIntegralaMy() {
        return integrala;
    }

    public void setIntegrala(int integrala) {
        this.integrala = integrala;
    }

    public String getNickName() {
        return getString("nickname");
    }
    public String getNickNameMy() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRank() {
        return getInt("rank");
    }
    public int getRankMy() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getIntegralb() {
        return getInt("integrala");
    }
    public int getIntegralbMy() {
        return integralb;
    }

    public void setIntegralb(int integralb) {
        this.integralb = integralb;
    }

    public String getAddr() {
        return getString("addr");
    }
    public String getAddrMy() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSign() {
        return getString("sign");
    }
    public String getSignMy() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
