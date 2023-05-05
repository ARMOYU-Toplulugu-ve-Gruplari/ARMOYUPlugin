package armoyuplugin.armoyuplugin.BasePlugin.KlanListesi;

import java.util.ArrayList;

public class KlanBilgiLink {
    public String klanAdi;
    public int[] klanBaslangicNoktasi;  //x,y,z
    public ArrayList<KlanOyuncuBilgi> klanUyeleri= new ArrayList<KlanOyuncuBilgi>();
    public KlanBilgiLink next;
}
