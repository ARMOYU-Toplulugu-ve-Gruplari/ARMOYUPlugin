package armoyuplugin.armoyuplugin.Pluginler.Klan.KlanListesi;

import java.util.ArrayList;

public class KlanBilgiLink {
    public String klanAdi;
    public String klanKisaltma;
    public String klanKurucu;
    public int[] klanBaslangicNoktasi;  //x,y,z
    public String klanEvDunya;
    public ArrayList<KlanRutbeleri> klanRutbeleri = new ArrayList<KlanRutbeleri>();
    public ArrayList<KlanOyuncuBilgi> klanUyeleri = new ArrayList<KlanOyuncuBilgi>();
    public KlanBilgiLink next;
}
