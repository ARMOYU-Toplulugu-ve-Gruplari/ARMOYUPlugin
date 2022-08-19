package pluginogreniyorum.pluginogreniyorum;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public final class Pluginogreniyorum extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("AKTİFFFFFF");
    //OĞUZ
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
    }



}
