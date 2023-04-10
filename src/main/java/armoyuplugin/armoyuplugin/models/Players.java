package armoyuplugin.armoyuplugin.models;


import java.util.UUID;

public class Players {

    private String oyuncuID;
    private String oyuncuadi;
    private String oyuncuparola;



    private int para;
    private String ev;
    private String klan;
    private String klanrutbe;
    private String klanrenk;
    private int leslerim;
    private boolean hareket;
    private double aclik;
    private double saglik;
    private double x;
    private double y;
    private double z;
    private String location;



    public Players(String oyuncuadi, String oyuncuparola, int para, String ev, String klan, String klanrutbe, String klanrenk, int leslerim, boolean hareket, double aclik, double saglik, double x, double y, double z, String location) {
        this.oyuncuID = UUID.randomUUID().toString();
        this.oyuncuadi = oyuncuadi;
        this.para = para;
        this.ev = ev;
        this.klan = klan;
        this.klanrutbe = klanrutbe;
        this.klanrenk = klanrenk;
        this.leslerim=leslerim;
        this.hareket = hareket;
        this.aclik = aclik;
        this.saglik = saglik;
        this.location = location;
    }

    public String getoyuncuID() {
        return oyuncuID;
    }


    public String getOyuncuadi() {
        return oyuncuadi;
    }

    public void setOyuncuadi(String oyuncuadi) {
        this.oyuncuadi = oyuncuadi;
    }

    public String getOyuncuparola() {
        return oyuncuparola;
    }

    public void setOyuncuparola(String oyuncuparola) {
        this.oyuncuparola = oyuncuparola;
    }

    public int getPara() {return para;}

    public void setPara(int para) {this.para = para;}

    public String getEv() {return ev;}

    public void setEv(String ev) {this.ev = ev;}

    public String getKlan() {return klan;}

    public void setKlan(String klan) {this.klan = klan;}

    public String getKlanrutbe() {  return klanrutbe;}

    public void setKlanrutbe(String klanrutbe) { this.klanrutbe = klanrutbe;}


    public String getKlanrenk() {
        return klanrenk;
    }

    public void setKlanrenk(String klanrenk) {
        this.klanrenk = klanrenk;
    }

    public int getLeslerim() {return leslerim;}

    public void setLeslerim(int leslerim) {this.leslerim = leslerim;}

    public boolean getHareket() {
        return hareket;
    }

    public void setHareket(boolean hareket) {
        this.hareket = hareket;
    }

    public double getAclik() {
        return aclik;
    }

    public void setAclik(double aclik) {
        this.aclik = aclik;
    }

    public double getSaglik() {
        return saglik;
    }

    public void setSaglik(double saglik) {
        this.saglik = saglik;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
