package armoyuplugin.armoyuplugin.Servisler.CommandService;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Pluginler.Genel.GenelKomutlar;
import armoyuplugin.armoyuplugin.Pluginler.Klan.Komutlar;
import armoyuplugin.armoyuplugin.Pluginler.Klan.Tabtamamlama;
import armoyuplugin.armoyuplugin.Pluginler.Claim.claimKomutlar;

public class SetupCommandsService {
    public void setupCommands(ARMOYUPlugin plugin){
        Komutlar komutlar = new Komutlar();
        Tabtamamlama tab = new Tabtamamlama();
        GenelKomutlar genel = new GenelKomutlar();

        plugin.getCommand("giris").setExecutor(genel);
        plugin.getCommand("toplamoldurme").setExecutor(genel);

        plugin.getCommand("ev").setExecutor(genel);
        plugin.getCommand("ev").setTabCompleter(tab);

        plugin.getCommand("para").setExecutor(genel);
        plugin.getCommand("para").setTabCompleter(tab);

        plugin.getCommand("oturumsaati").setExecutor(genel);

        plugin.getCommand("klan").setExecutor(komutlar);
        plugin.getCommand("klan").setTabCompleter(tab);


        plugin.getCommand("baslangicayarla").setExecutor(genel);
        plugin.getCommand("tpa").setExecutor(genel);
        plugin.getCommand("tpaccept").setExecutor(genel);

        plugin.getCommand("claim").setExecutor(new claimKomutlar());
    }

}
