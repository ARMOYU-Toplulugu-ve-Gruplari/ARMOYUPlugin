package armoyuplugin.armoyuplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {


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
