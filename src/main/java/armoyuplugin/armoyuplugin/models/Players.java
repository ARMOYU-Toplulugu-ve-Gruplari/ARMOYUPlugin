package armoyuplugin.armoyuplugin.models;


import java.util.UUID;

public class Players {

    private String oyuncuID;
    private String oyuncuadi;
    private boolean hareket;
    private double x;
    private double y;
    private double z;
    private String location;



    public Players(String id, String oyuncuadi, boolean harket,double x, double y, double z,String location) {
        this.oyuncuID = UUID.randomUUID().toString();
        this.oyuncuadi = oyuncuadi;
        this.hareket = hareket;
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


    public boolean getHareket() {
        return hareket;
    }

    public void setHareket(boolean hareket) {
        this.hareket = hareket;
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
