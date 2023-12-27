package com.example.dentall.beans;

public class Image {
    private int id;
    private  String url;
    private  float alpha1;
    private  float alpha2;
    private  float alpha3;
    private  float beta1;
    private  float beta2;
    private  float beta3;
    private Student student;
    private PW pw;

    public Image() {
    }

    public Image(int id, String url, float alpha1, float alpha2, float alpha3, float beta1, float beta2, float beta3, StudentPW studentPW) {
        this.id = id;
        this.url = url;
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.beta3 = beta3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getAlpha1() {
        return alpha1;
    }

    public void setAlpha1(float alpha1) {
        this.alpha1 = alpha1;
    }

    public float getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(float alpha2) {
        this.alpha2 = alpha2;
    }

    public float getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(float alpha3) {
        this.alpha3 = alpha3;
    }

    public float getBeta1() {
        return beta1;
    }

    public void setBeta1(float beta1) {
        this.beta1 = beta1;
    }

    public float getBeta2() {
        return beta2;
    }

    public void setBeta2(float beta2) {
        this.beta2 = beta2;
    }

    public float getBeta3() {
        return beta3;
    }

    public void setBeta3(float beta3) {
        this.beta3 = beta3;
    }


}
