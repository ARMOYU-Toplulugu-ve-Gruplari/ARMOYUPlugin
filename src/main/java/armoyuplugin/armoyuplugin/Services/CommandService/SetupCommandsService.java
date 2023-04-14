package armoyuplugin.armoyuplugin.Services.CommandService;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.BasePlugin.Komutlar;
import armoyuplugin.armoyuplugin.BasePlugin.Tabtamamlama;
import armoyuplugin.armoyuplugin.ClaimPlugin.Komutlar.claimKomutlar;

public class SetupCommandsService {
    public void setupCommands(ARMOYUPlugin plugin){
        Komutlar komutlar = new Komutlar();
        Tabtamamlama tab = new Tabtamamlama();

        plugin.getCommand("giris").setExecutor(komutlar);
        plugin.getCommand("toplamoldurme").setExecutor(komutlar);

        plugin.getCommand("ev").setExecutor(komutlar);
        plugin.getCommand("ev").setTabCompleter(tab);

        plugin.getCommand("para").setExecutor(komutlar);
        plugin.getCommand("para").setTabCompleter(tab);

        plugin.getCommand("oturumsaati").setExecutor(komutlar);

        plugin.getCommand("klan").setExecutor(komutlar);
        plugin.getCommand("klan").setTabCompleter(tab);


        plugin.getCommand("baslangicayarla").setExecutor(komutlar);
        plugin.getCommand("tpa").setExecutor(komutlar);
        plugin.getCommand("tpaccept").setExecutor(komutlar);

        plugin.getCommand("claim").setExecutor(new claimKomutlar());
    }

}
