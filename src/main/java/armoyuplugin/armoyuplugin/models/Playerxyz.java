package armoyuplugin.armoyuplugin.models;

import org.bukkit.World;

public class Playerxyz {

    private String oyuncuID;
    private String oyuncuadi;
    private String klan;
    private boolean hareket;
    private double aclik;
    private double saglik;
    private double x;
    private double y;
    private double z;
    private String location;

    public Playerxyz(String oyuncuadi, String klan,boolean hareket,double aclik,double saglik, double x, double y, double z,String location) {
        this.oyuncuID = oyuncuID;
        this.oyuncuadi = oyuncuadi;
        this.klan = klan;
        this.hareket = hareket;
        this.aclik = aclik;
        this.saglik = saglik;
        this.x = x;
        this.y = y;
        this.z = z;
        this.location = location;
    }

    public String getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(String oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuadi() {
        return oyuncuadi;
    }

    public void setOyuncuadi(String oyuncuadi) {
        this.oyuncuadi = oyuncuadi;
    }

    public String getKlan() {
        return klan;
    }

    public void setKlan(String klan) {
        this.klan = klan;
    }
    public boolean isHareket() {
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
