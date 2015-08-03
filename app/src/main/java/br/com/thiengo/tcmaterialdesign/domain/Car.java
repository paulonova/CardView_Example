package br.com.thiengo.tcmaterialdesign.domain;

/**
 * Created by viniciusthiengo on 4/5/15.
 */
public class Car {
    private String model;
    private String brand;
    private int photo;


    public Car(){}
    public Car(String m, String b, int p){
        model = m;
        brand = b;
        photo = p;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
