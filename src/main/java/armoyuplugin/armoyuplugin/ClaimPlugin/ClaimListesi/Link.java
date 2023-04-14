package armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi;

import java.util.ArrayList;

public class Link {

    public String oyuncu;
    public Link next;
    public String oncekiGecilenAraziSahibi = "";

    public String arsaAciklamasi = "default arsa aciklamasi";

    public ArrayList<TrustLink> trustlar = new ArrayList<TrustLink>();

    public Link(){

    }

}
