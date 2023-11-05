package armoyuplugin.armoyuplugin.Servisler.CommandServices;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class ClaimCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";


    public void claimAl(Player p,String[] oyuncuAdiVeParola){
            try {
                String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsaal","0","0","0","0","0"};
                JSONObject yollanacaklar = new JSONObject();

                yollanacaklar.put("kordinatX","0");
                yollanacaklar.put("kordinatY","0");
                yollanacaklar.put("kordinatZ","0");
                yollanacaklar.put("kordinatDunya",p.getWorld().toString());
                yollanacaklar.put("arsaChunk",p.getLocation().getChunk().toString());

                claimListesi.arsaAl(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

            } catch (Exception e) {
                System.out.println(e);
            }

    }

    public void hissedarEkle(Player p,String[] oyuncuAdiVeParola,String eklenilen){

                try {
                String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "ekle", "0", "0", "0"};
                JSONObject yollanacaklar = new JSONObject();

                yollanacaklar.put("arsaDunya", p.getWorld().toString());
                yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
                yollanacaklar.put("eklenecekoyuncuAdi", eklenilen);

                claimListesi.hissedarEkle(p,eklenilen,yollanacaklar,apiService.linkOlustur(linkElemanlar));
                } catch (Exception e) {
                    System.out.println(e);
                }
    }


    public void hissedarSil(Player p,String[] oyuncuAdiVeParola,String cikarilan){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "hissedar", "sil", "0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("arsaDunya", p.getWorld().toString());
            yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
            yollanacaklar.put("silinecekoyuncuAdi", cikarilan);

            claimListesi.hissedarSil(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),cikarilan);
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
        }
    }
    public void claimSil(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsasil", "0", "0", "0", "0", "0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("kordinatX","0");
            yollanacaklar.put("kordinatY","0");
            yollanacaklar.put("kordinatZ","0");
            yollanacaklar.put("kordinatDunya",p.getWorld().toString());
            yollanacaklar.put("arsaChunk",p.getLocation().getChunk().toString());

            claimListesi.claimSil(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void claimSilHeryer(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsasil-heryer", "0", "0", "0", "0", "0"};
            JSONObject yollanacaklar = new JSONObject();
            yollanacaklar.put("deneme","deneme");

            JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.claimSilHeryer(p.getName());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void claimAciklama(Player p,String[] oyuncuAdiVeParola,String[] aciklamaArray){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsalar", "aciklama", "0", "0"};
            JSONObject yollanacaklar = new JSONObject();

            String aciklama= "";
            for (int i = 1; i < aciklamaArray.length; i++) {
                aciklama = aciklama + aciklamaArray[i] + " ";
            }

            yollanacaklar.put("arsaAciklamasi", aciklama);
            yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
            yollanacaklar.put("arsaDunya",p.getWorld().toString());

            claimListesi.claimAciklama(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),aciklama);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void claimAciklamaHeryer(Player p,String[] oyuncuAdiVeParola,String[] aciklamaArray){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsalar", "hepsi-aciklama", "0", "0","0"};
            JSONObject yollanacaklar = new JSONObject();

            String aciklama= "";
            for (int i = 1; i < aciklamaArray.length; i++) {
                aciklama = aciklama + aciklamaArray[i] + " ";
            }

            yollanacaklar.put("arsaAciklamasi", aciklama);

            JSONObject json = apiService.postYolla(apiService.linkOlustur(linkElemanlar), yollanacaklar);
            if (json.get("durum").toString().equals("0")) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
                claimListesi.claimAciklamaHeryer(p.getName(),aciklama);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void claimRehin(Player p, String[] oyuncuAdiVeParola){
        try {
            String klanAdi = klanListesi.hangiKlanaUye(p.getName());

            if (!klanAdi.isEmpty()){
                String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsalar", "bahset", "0", "0","0"};
                JSONObject yollanacaklar = new JSONObject();
                yollanacaklar.put("deviredilecenklanAdi", klanAdi);
                yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
                yollanacaklar.put("arsaDunya", p.getWorld().toString());

                claimListesi.claimRehin(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),klanAdi);

            }else
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Önce bir klana üye olmalısın.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void claimDevret(Player p, String[] oyuncuAdiVeParola,String devredilen){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "arsalar", "deviret", "0", "0","0"};
            JSONObject yollanacaklar = new JSONObject();


            yollanacaklar.put("deviredilecenoyuncuAdi", devredilen);
            yollanacaklar.put("arsaChunk", p.getLocation().getChunk().toString());
            yollanacaklar.put("arsaDunya", p.getWorld().toString());

            claimListesi.claimDevret(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),devredilen);


        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
