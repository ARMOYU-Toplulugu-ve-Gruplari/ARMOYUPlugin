package pluginogreniyorum.pluginogreniyorum;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {


//oĞUZXHAN ORADA MISIN
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

            if (player.hasPlayedBefore()){
                e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " Hoşgeldin");
            }else{
                e.setJoinMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + "Tekrar Wellcome Back");
            }
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        
        e.setQuitMessage(ChatColor.YELLOW + player.getDisplayName()+ ChatColor.RED + " arkadaş sunucudan çıktı");
    }

}
