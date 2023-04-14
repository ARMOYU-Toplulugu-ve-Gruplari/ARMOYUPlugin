package armoyuplugin.armoyuplugin.Services.JsonServices;


import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Services.JsonServices.models.Players;
import armoyuplugin.armoyuplugin.Services.JsonServices.utils.JsonUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;

public class JsonService {


private List<Players> jsonCek(Player p){
    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";
    try { JsonUtility.loadNotes(); } catch (IOException err) {    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "notlar yüklenmedi");   }
    return JsonUtility.findAllNotes();
}
public void jsonSave(){
    try {JsonUtility.saveNotes();} catch (IOException ERR) {
        Bukkit.getLogger().info("[ARMOYU] " + "Oyuncu bilgileri Güncellenemedi");}
}
public Players oyuncu(Player p){
    List<Players> findAllNotes = jsonCek(p);
    for (int i = 0; i < findAllNotes.size(); i++) {
        Players oyuncucek = findAllNotes.get(i);
        if (oyuncucek.getOyuncuadi().equals(p.getName())){
            return oyuncucek;
        }
    }
    return null;
}

    public String[] getOyuncuAdiVeParola(Player p){
        Players oyuncu = oyuncu(p);
        if (oyuncu !=null){
                return new String[]{oyuncu.getOyuncuadi(), oyuncu.getOyuncuparola()};
        }

        return new String[]{"bulunamadi","bulunamadi"};
    }


    public void serverDisable(ARMOYUPlugin plugin){
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            double[] oyuncuXYZ = {player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ()};
            Players oyuncu = oyuncu(player);
            if (oyuncu.getHareket()){
                String currentWorld = "world";
                if (player.getLocation().toString().contains("world_nether")){
                    currentWorld = "world_nether";
                }
                else if (player.getLocation().toString().contains("world_the_end")){
                    currentWorld = "world_the_end";
                }
                JsonUtility.updateNotexyz(player.getName(), oyuncu.getPara(),false,player.getFoodLevel(),player.getHealth() ,oyuncuXYZ[0],oyuncuXYZ[1],oyuncuXYZ[2], currentWorld);

                jsonSave();

            }
        }

       List<Players> findAllNotes = JsonUtility.findAllNotes();

        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            Bukkit.getLogger().info(oyuncucek.getOyuncuadi());
            JsonUtility.updateNotexyz(oyuncucek.getOyuncuadi(), oyuncucek.getPara(), false, oyuncucek.getAclik(), oyuncucek.getSaglik(), oyuncucek.getX(), oyuncucek.getY(), oyuncucek.getZ(), oyuncucek.getLocation());

            try {JsonUtility.saveNotes();} catch (IOException ERR) {Bukkit.getLogger().info("[ARMOYU] " + "Oyuncu bilgileri Güncellenemedi");}
        }



    }








}








