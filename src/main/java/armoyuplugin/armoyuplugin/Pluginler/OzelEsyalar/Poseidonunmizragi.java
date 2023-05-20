package armoyuplugin.armoyuplugin.Pluginler.OzelEsyalar;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Poseidonunmizragi implements Listener {
    ARMOYUPlugin plugin;

    public Poseidonunmizragi(ARMOYUPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getInventory() != null && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getLore() !=null
        && event.getCurrentItem().getItemMeta().getLore().contains("Damage Multipler")){
            String zombie= plugin.getConfig().getString("poseidonunmizragi.zombie");
            String skeleton= plugin.getConfig().getString("poseidonunmizragi.skeleton");
            ItemMeta meta = event.getCurrentItem().getItemMeta();
            List<String> lore = new ArrayList<>(meta.getLore());

        }
    }
}
