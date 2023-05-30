package armoyuplugin.armoyuplugin.Servisler.CommandService;

import armoyuplugin.armoyuplugin.Servisler.JsonFileServices.JsonService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;

public class ClaimCommandsService {
    private String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";


    public void claimAl(Player p,String oyuncuAdiVeParola[]){
        p.sendMessage(oyuncuAdiVeParola[0] + " " + oyuncuAdiVeParola[1]);
        String[] linkElemanlar = {oyuncuAdiVeParola[0],oyuncuAdiVeParola[1],"arsaal","0","0","0","0","0"};

        JSONObject yollanacaklar = new JSONObject();

        yollanacaklar.put("kordinatX","0");
        yollanacaklar.put("kordinatY","0");
        yollanacaklar.put("kordinatZ","0");
        yollanacaklar.put("kordinatDunya",p.getWorld().toString());
        yollanacaklar.put("arsaChunk",p.getLocation().getChunk().toString());

        if (claimListesi.listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString()) == null){
            try {
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
            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Arazi zaten alınmış.");
    }
}
