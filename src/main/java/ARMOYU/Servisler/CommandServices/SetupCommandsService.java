package ARMOYU.Servisler.CommandServices;

import ARMOYU.ARMOYUPlugin;
import ARMOYU.Komutlar.ClaimKomutlar;
import ARMOYU.Komutlar.GenelKomutlar;
import ARMOYU.Komutlar.KlanKomutlar;
import ARMOYU.Komutlar.TabTamamlama.ClaimTabTamamlama;
import ARMOYU.Komutlar.TabTamamlama.KlanTabTamamlama;

public class SetupCommandsService {
    public void setupCommands(ARMOYUPlugin plugin){
        KlanKomutlar klanKomutlar = new KlanKomutlar();
        KlanTabTamamlama klanTab = new KlanTabTamamlama();
        ClaimTabTamamlama claimTab = new ClaimTabTamamlama();
        GenelKomutlar genel = new GenelKomutlar();

        plugin.getCommand("giris").setExecutor(genel);
        plugin.getCommand("toplamoldurme").setExecutor(genel);

        plugin.getCommand("ev").setExecutor(genel);
        plugin.getCommand("ev").setTabCompleter(klanTab);

        plugin.getCommand("para").setExecutor(genel);
        plugin.getCommand("para").setTabCompleter(klanTab);

        plugin.getCommand("oturumsaati").setExecutor(genel);

        plugin.getCommand("klan").setExecutor(klanKomutlar);
        plugin.getCommand("klan").setTabCompleter(klanTab);


        plugin.getCommand("baslangicayarla").setExecutor(genel);
        plugin.getCommand("tpa").setExecutor(genel);
        plugin.getCommand("tpaccept").setExecutor(genel);

        plugin.getCommand("claim").setExecutor(new ClaimKomutlar());
        plugin.getCommand("claim").setTabCompleter(claimTab);
    }

}
