package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Note;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

public class JoinLeaveListener extends Komutlar implements Listener {
    @EventHandler
    public void onmove(PlayerMoveEvent event) {
        try {

         File input = new File("plugins/user.json");
          JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
           JsonObject fileObject = fileElement.getAsJsonObject();


            if (fileObject.get("hareket").getAsBoolean()){
                event.setCancelled(true);
                System.out.println(fileObject.get("hareket"));
            }
            else {
                event.setCancelled(false);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        System.out.println(player.getDisplayName());


        List<Note> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Note note = findAllNotes.get(i);
            System.out.println(note.getPlayerName());
            if (note.getPlayerName().equalsIgnoreCase("berkaytikenoglu")){
                System.out.println(note.getMessage());
            }

        }

    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " isimli oyuncu sunucudan çıktı");
    }

}
