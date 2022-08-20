package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.commands.CreateNoteCommand;
import armoyuplugin.armoyuplugin.models.Note;
import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import armoyuplugin.armoyuplugin.utils.NoteStorageUtility;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.*;
import java.util.List;

public class JoinLeaveListener extends Komutlar implements Listener {
    @EventHandler
    public void onmove(PlayerMoveEvent event) {
        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                System.out.println("[ARMOYU] "+oyuncucek.getOyuncuadi() + ": " +oyuncucek.getMesaj() +","+ oyuncucek.getHareket());
                event.setCancelled(false);}
                else
                    event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        try {
            File yourFile = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
            if (!yourFile.exists()){
                yourFile.getParentFile().mkdirs();
                yourFile.createNewFile(); // if file already exists will do nothing
                FileOutputStream oFile = new FileOutputStream(yourFile, false);
            }

        }catch (Exception error){
            System.out.println("[ARMOYU] oyuncular.json dosyası oluşturulamadı!");
        }

        Player player = e.getPlayer();

        e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " Sunucumuza Hoşgeldiniz...");

        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }


        //Oyuncu hiç oyuna girmiş mi kontrol
        boolean oyuncukontrol = false;
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                oyuncukontrol = true;
                System.out.println("[ARMOYU] "+oyuncucek.getOyuncuadi() + ": " +oyuncucek.getMesaj() +","+ oyuncucek.getHareket());
            }
        }

        //Yeni oyuncu ise
        if (oyuncukontrol == false){
            System.out.println("[ARMOYU] "+"YENİ OYUNCU GİRDİ");
            JsonUtility.createNote(player, player.getDisplayName(), false);
            try {
                JsonUtility.saveNotes();
                System.out.println("[ARMOYU] "+"Kaydettik");
            } catch (IOException ERR) {
                System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                ERR.printStackTrace();
            }

        }

    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " isimli oyuncu sunucudan çıktı");
        JsonUtility.updateNote(player.getDisplayName(),false);
        try {
            JsonUtility.saveNotes();
            System.out.println("[ARMOYU] "+"Kaydettik");
        } catch (IOException ERR) {
            System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
            ERR.printStackTrace();
        }
    }

}
