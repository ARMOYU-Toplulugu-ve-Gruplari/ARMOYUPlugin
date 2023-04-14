package armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi;

import java.util.ArrayList;

public class Link {

    public String oyuncu;
    public String klan;
    public Link next;
    public String oncekiGecilenAraziSahibi = "";
    public String arsaAciklamasi = "default arsa aciklamasi";
    public ArrayList<TrustLink> trustlar = new ArrayList<TrustLink>();
    public Link(){

    }

}
