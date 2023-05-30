package armoyuplugin.armoyuplugin.Servisler.CommandService;

import armoyuplugin.armoyuplugin.Servisler.JsonFileServices.JsonService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class ClaimCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";


    public void claimAl(Player p,String[] oyuncuAdiVeParola){
        if (claimListesi.listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString()) == null){
            try {

                String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsaal","0","0","0","0","0"};

                JSONObject yollanacaklar = new JSONObject();

                yollanacaklar.put("kordinatX","0");
                yollanacaklar.put("kordinatY","0");
                yollanacaklar.put("kordinatZ","0");
                yollanacaklar.put("kordinatDunya",p.getWorld().toString());
                yollanacaklar.put("arsaChunk",p.getLocation().getChunk().toString());
                JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar),yollanacaklar);

                if (json.get("durum").toString().equals("0")) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                } else {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    claimListesi.arsaAl(p.getLocation().getChunk().toString(), p.getName(), p.getWorld().toString());
                }
            } catch (Exception e) {
                System.out.println("claim al hatası");
            }
        }else
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "arsa zaten alınmış.");
    }

    public void hissedarEkle(Player p,String[] oyuncuAdiVeParola,String eklenilen){

            if (!claimListesi.kontrolTrustuVarmi(eklenilen, p.getWorld().toString(), p.getLocation().getChunk().toString())) {
                try {
                String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "ekle", "0", "0", "0"};
                JSONObject yollanacaklar = new JSONObject();

                yollanacaklar.put("arsaDunya", p.getWorld().toString());
                yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
                yollanacaklar.put("eklenecekoyuncuAdi", eklenilen);

                    JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
                    if (json.get("durum").toString().equals("0")) {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                    } else {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                        claimListesi.hissedarEkle(p.getLocation().getChunk().toString(), p.getName(), eklenilen, p.getWorld().toString());
                    }
                } catch (Exception e) {
                    System.out.println("hissedar ekle hatası");
                }

            } else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Kullanıcı zaten hissedarlara ekli.");

    }


    public void hissedarSil(Player p,String[] oyuncuAdiVeParola,String cikarilan){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "sil", "0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("arsaDunya", p.getWorld().toString());
            yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
            yollanacaklar.put("silinecekoyuncuAdi", cikarilan);


            JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.hissedarSil(p.getLocation().getChunk().toString(), p.getName(), cikarilan, p.getWorld().toString());
            }
        } catch (Exception e) {
            System.out.println("hissedar sil hatası");
        }
    }

    public void hissedarEkleHeryer(Player p,String[] oyuncuAdiVeParola,String eklenilen){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "ekle-heryer", "0", "0", "0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("eklenecekoyuncuAdi", eklenilen);


            JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.hissedarEkleHeryer(p.getName(),eklenilen);
            }
        } catch (Exception e) {
            System.out.println("hissedar sil hatası");
        }
    }

    public void hissedarSilHeryer(Player p,String[] oyuncuAdiVeParola,String cikarilan){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "sil-heryer", "", "", ""};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("silinecekoyuncuAdi", cikarilan);


            JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.hissedarSilHeryer(p.getName(),cikarilan);
            }
        } catch (Exception e) {
            System.out.println("hissedar sil hatası");
        }
    }
}
