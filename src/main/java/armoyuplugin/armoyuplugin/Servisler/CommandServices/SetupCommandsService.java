package armoyuplugin.armoyuplugin.Servisler.CommandServices;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.Komutlar.GenelKomutlar;
import armoyuplugin.armoyuplugin.Komutlar.KlanKomutlar;
import armoyuplugin.armoyuplugin.Komutlar.TabTamamlama.KlanTabTamamlama;
import armoyuplugin.armoyuplugin.Komutlar.ClaimKomutlar;

public class SetupCommandsService {
    public void setupCommands(ARMOYUPlugin plugin){
        KlanKomutlar klanKomutlar = new KlanKomutlar();
        KlanTabTamamlama tab = new KlanTabTamamlama();
        GenelKomutlar genel = new GenelKomutlar();

        plugin.getCommand("giris").setExecutor(genel);
        plugin.getCommand("toplamoldurme").setExecutor(genel);

        plugin.getCommand("ev").setExecutor(genel);
        plugin.getCommand("ev").setTabCompleter(tab);

        plugin.getCommand("para").setExecutor(genel);
        plugin.getCommand("para").setTabCompleter(tab);

        plugin.getCommand("oturumsaati").setExecutor(genel);

        plugin.getCommand("klan").setExecutor(klanKomutlar);
        plugin.getCommand("klan").setTabCompleter(tab);


        plugin.getCommand("baslangicayarla").setExecutor(genel);
        plugin.getCommand("tpa").setExecutor(genel);
        plugin.getCommand("tpaccept").setExecutor(genel);

        plugin.getCommand("claim").setExecutor(new ClaimKomutlar());
    }

}
