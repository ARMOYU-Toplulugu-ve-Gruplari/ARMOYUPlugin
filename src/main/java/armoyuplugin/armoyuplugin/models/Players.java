package armoyuplugin.armoyuplugin.models;



public class Players {
    public Players(String id, String oyuncuadi, boolean harket) {
        this.id = id;
        this.oyuncuadi = oyuncuadi;
        this.harket = harket;
    }

    private String id;
    private String oyuncuadi;
    private boolean harket;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOyuncuadi() {
        return oyuncuadi;
    }

    public void setOyuncuadi(String oyuncuadi) {
        this.oyuncuadi = oyuncuadi;
    }

    public boolean isHarket() {
        return harket;
    }

    public void setHarket(boolean harket) {
        this.harket = harket;
    }




}
