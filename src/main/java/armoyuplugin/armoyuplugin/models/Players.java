package armoyuplugin.armoyuplugin.models;


import java.util.UUID;

public class Players {

    private String oyuncuID;
    private String oyuncuadi;
    private boolean hareket;
    private String mesaj;



    public Players(String id, String oyuncuadi, boolean harket) {
        this.oyuncuadi = oyuncuadi;
        this.hareket = hareket;
        this.mesaj = mesaj;
        this.oyuncuID = UUID.randomUUID().toString();
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


    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }


}
