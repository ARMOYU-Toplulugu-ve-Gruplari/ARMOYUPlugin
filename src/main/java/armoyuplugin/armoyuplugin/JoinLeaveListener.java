package armoyuplugin.armoyuplugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
                System.out.println("naber");
                event.setCancelled(false);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
       }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

            if (player.hasPlayedBefore()){
                e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " Sunucumuza Hoşgeldiniz...");
            }else{
                e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + "Seni Yeniden");
            }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " arkadaş sunucudan çıktı");
    }

}
