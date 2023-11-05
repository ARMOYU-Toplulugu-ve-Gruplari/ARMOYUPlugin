package armoyuplugin.armoyuplugin.Servisler.CommandServices;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.Collection;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;
import static org.bukkit.Bukkit.getServer;

public class ClanCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Klan] ";

    public void klanKatil(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","katil","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            klanListesi.klanKatil(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanOlustur(Player p,String[] oyuncuAdiVeParola,String klanAdi){
        try {

            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","olustur","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("klanadi",klanAdi);

            klanListesi.klanOlustur(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),klanAdi);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanAyril(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","ayril","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanAyril(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanDagit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","dagit","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanDagit(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void klanGit(Player p,String[] oyuncuAdiVeParola){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","git","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            klanListesi.klanGit(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
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

            klanListesi.klanBaslangicNoktasi(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanDavet(Player p, String[] oyuncuAdiVeParola, String davetEdilen, Server server){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","davet-et","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("kullaniciadi",davetEdilen);

            klanListesi.klanDavet(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),server);
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

            klanListesi.klanAciklama(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void klanAt(Player p,String[] oyuncuAdiVeParola,String atilan){
        try {
            String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"klan","at","0","0","0","0"};
            JSONObject yollanacaklar = new JSONObject();

            yollanacaklar.put("atilacakoyuncuadi",atilan);

            klanListesi.klanAt(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
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

            klanListesi.klanArsaDevret(p,yollanacaklar,apiService.linkOlustur(linkElemanlar),p.getName(), devredilen);
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

            klanListesi.klanArsaSil(p,yollanacaklar,apiService.linkOlustur(linkElemanlar));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
