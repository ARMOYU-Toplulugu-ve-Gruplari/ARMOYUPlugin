package armoyuplugin.armoyuplugin.Servisler.CommandServices;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class ClanCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Klan] ";

    public void klanKatil(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","katil","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            klanListesi.klanKatil(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println("klan katil hatası");
        }
    }
    public void klanOlustur(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","olustur","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            klanListesi.klanOlustur(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println("klan oluştur hatası");
        }
    }
    public void klanAyril(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","ayril","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanAyril(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println("klan Ayrıl hatası");
        }
    }
    public void klanDagit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","dagit","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanDagit(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println("klan Dagit hatası");
        }
    }
    public void klanGit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","git","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanGit(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println("klan Git hatası");
        }
    }

    public void klanBaslangicNoktasi(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","baslangicnoktasi","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("Xnoktasi",p.getLocation().getX());
            yollanacaklar.put("Ynoktasi",p.getLocation().getY());
            yollanacaklar.put("Znoktasi",p.getLocation().getZ());
            yollanacaklar.put("dunya",p.getWorld().toString());

            klanListesi.klanBaslangicNoktasi(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println("klan Baslangic hatası");
        }
    }

    public void klanDavet(Player p,String[] oyuncuAdiVeParola,String davetEdilen){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","davet-et","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("kullaniciadi",davetEdilen);

            klanListesi.klanDavet(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println("klan Davet hatası");
        }
    }

    public void klanAciklama(Player p,String[] oyuncuAdiVeParola,String[] aciklamaArray){
        try {

            String aciklama= "";
            for (int i = 1; i < aciklamaArray.length; i++) {
                aciklama = aciklama + aciklamaArray[i] + " ";
            }

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","aciklama","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("aciklama",aciklama);

            klanListesi.klanAciklama(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println("klan Aciklama hatası");
        }
    }

}
