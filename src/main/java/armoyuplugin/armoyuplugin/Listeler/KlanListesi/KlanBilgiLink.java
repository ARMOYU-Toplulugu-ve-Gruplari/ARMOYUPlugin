package armoyuplugin.armoyuplugin.Listeler.KlanListesi;

import java.util.ArrayList;

public class KlanBilgiLink {
    public String klanAdi = "";
    public String klanKurucu = "";
    public String arsaAciklamasi = "";
    public ArrayList<KlanRutbeleri> klanRutbeleri = new ArrayList<KlanRutbeleri>();
    public ArrayList<KlanOyuncuBilgi> klanUyeleri = new ArrayList<KlanOyuncuBilgi>();
    public KlanBilgiLink next;
}
