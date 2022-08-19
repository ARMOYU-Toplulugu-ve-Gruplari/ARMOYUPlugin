package armoyuplugin.armoyuplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ARMOYUPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[ARMOYU] ----Aktif----");
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);

        Komutlar komutbilgi = new Komutlar();
        getCommand("heal").setExecutor(komutbilgi);
        getCommand("feed").setExecutor(komutbilgi);
        getCommand("giris").setExecutor(komutbilgi);

    }

    @Override
    public void onDisable() {
        System.out.println("[ARMOYU] ----Devre Dışı----");
    }
}
