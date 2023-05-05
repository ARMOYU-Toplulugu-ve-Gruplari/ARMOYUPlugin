package armoyuplugin.armoyuplugin.Servisler.TxtServices;


import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Servisler.TxtServices.models.Players;
import armoyuplugin.armoyuplugin.Servisler.TxtServices.utils.JsonUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;

public class JsonService {


private List<Players> jsonCek(){
    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";
    try { JsonUtility.loadNotes(); } catch (IOException err) {    }
    return JsonUtility.findAllNotes();
}
public void jsonSave(){
    try {JsonUtility.saveNotes();} catch (IOException ERR) {
        Bukkit.getLogger().info("[ARMOYU] " + "Oyuncu bilgileri Güncellenemedi");}
}

public void jsonCreate(Player p){
    JsonUtility.createNote(p,false,20,20);
}
public Players oyuncu(Player p){
    List<Players> findAllNotes = jsonCek();
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

       List<Players> findAllNotes = jsonCek();

        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            JsonUtility.updateNotexyz(oyuncucek.getOyuncuadi(), oyuncucek.getPara(), false, oyuncucek.getAclik(), oyuncucek.getSaglik(), oyuncucek.getX(), oyuncucek.getY(), oyuncucek.getZ(), oyuncucek.getLocation());
            jsonSave();
        }

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.kickPlayer("Sunucu yeniden başlatılıyor");
        }



    }








}








