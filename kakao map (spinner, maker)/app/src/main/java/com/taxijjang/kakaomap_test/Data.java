package com.taxijjang.kakaomap_test;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Data implements Serializable {
    private static final long serialVersionUID = 100L;
    String si, gun, gu;
    Double latx, laty;
    public Data(){

    }
    public Data(Data d){
        this.si = d.si;
        this.gun = d.gun;
        this.gu = d.gu;
        this.latx = d.latx;
        this.laty = d.laty;
    }
    public Data(String si, String gun, String gu, double latx , double laty){
        this.si = si;
        this.gun = gun;
        this.gu = gu;
        this.latx = latx;
        this.laty = laty;
    }

    public String getSi(){
        return si;
    }
    public void setSi(String si){
        this.si = si;
    }

    public String getGun(){
        return gun;
    }
    public void setGun(String gun){
        this.gun = gun;
    }

    public String getGu(){
        return gu;
    }
    public void setGu(String gu){
        this.gu = gu;
    }

    public Double getLatx(){
        return getLatx();
    }
    public void setLatx(Double latx){
        this.latx = latx;
    }
    public Double getLaty(){
        return laty;
    }
    public void setLaty(Double laty){
        this.laty = laty;
    }

    public String toString(){
        return "추가된 위치 " + si + " " + gun + " " + gu + "\n " + latx + " " + laty;
    }
}
