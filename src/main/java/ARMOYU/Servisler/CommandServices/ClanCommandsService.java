package ARMOYU.Servisler.CommandServices;

import ARMOYU.ARMOYUPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.json.JSONObject;

public class ClanCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Klan] ";

    public void klanKatil(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","katil","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            ARMOYUPlugin.klanListesi.klanKatil(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanOlustur(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","olustur","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            ARMOYUPlugin.klanListesi.klanOlustur(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanAyril(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","ayril","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            ARMOYUPlugin.klanListesi.klanAyril(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanDagit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","dagit","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            ARMOYUPlugin.klanListesi.klanDagit(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanGit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","git","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            ARMOYUPlugin.klanListesi.klanGit(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanBaslangicNoktasi(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","baslangicnoktasi","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("Xnoktasi",p.getLocation().getX());
            yollanacaklar.put("Ynoktasi",p.getLocation().getY());
            yollanacaklar.put("Znoktasi",p.getLocation().getZ());
            yollanacaklar.put("dunya",p.getLocation().getWorld().getName());

            ARMOYUPlugin.klanListesi.klanBaslangicNoktasi(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanDavet(Player p, String[] oyuncuAdiVeParola, String davetEdilen, Server server){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","davet-et","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("kullaniciadi",davetEdilen);

            ARMOYUPlugin.klanListesi.klanDavet(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar),server);
        } catch (Exception e) {
            System.out.println(e);
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

            ARMOYUPlugin.klanListesi.klanAciklama(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanAt(Player p,String[] oyuncuAdiVeParola,String atilan){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","at","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("atilacakoyuncuadi",atilan);

            ARMOYUPlugin.klanListesi.klanAt(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanArsaDevret(Player p, String[] oyuncuAdiVeParola, String devredilen) {
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","arsadevret","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("yeniarsasahibiadi",devredilen);
            yollanacaklar.put("arsakordinat",p.getLocation().getChunk().toString());
            yollanacaklar.put("arsadunya",p.getWorld().toString());

            ARMOYUPlugin.klanListesi.klanArsaDevret(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar),p.getName(), devredilen);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanArsaSil(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0], oyuncuAdiVeParola[1], "klan", "arsasil", "0", "0", "0", "0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("kordinatX","0");
            yollanacaklar.put("kordinatY","0");
            yollanacaklar.put("kordinatZ","0");
            yollanacaklar.put("kordinatDunya",p.getWorld().toString());
            yollanacaklar.put("arsaChunk",p.getLocation().getChunk().toString());

            ARMOYUPlugin.klanListesi.klanArsaSil(p,yollanacaklar, ARMOYUPlugin.apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
