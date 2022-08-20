package armoyuplugin.armoyuplugin.models;

public class Playerxyz {
    private String oyuncuadi;
    private double x;
    private double y;
    private double z;

    public Playerxyz(String oyuncuadi, double x, double y, double z) {
        this.oyuncuadi = oyuncuadi;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public String getOyuncuadi() {
        return oyuncuadi;
    }

    public void setOyuncuadi(String oyuncuadi) {
        this.oyuncuadi = oyuncuadi;
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


}
