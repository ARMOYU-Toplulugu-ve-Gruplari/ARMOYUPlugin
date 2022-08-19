package armoyuplugin.armoyuplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ARMOYUPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[ARMOYU] ----Aktif----");
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);

    }
    //commit test
    ///dursun ali plugin
    //ben buraya birseyler yazıyom işte

    @Override
    public void onDisable() {
        System.out.println("[ARMOYU] ----Devre Dışı----");
    }
}
