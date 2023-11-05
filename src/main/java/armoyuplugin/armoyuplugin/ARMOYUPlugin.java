package armoyuplugin.armoyuplugin;
import armoyuplugin.armoyuplugin.Listeler.KlanListesi.Yetkiler;
import armoyuplugin.armoyuplugin.Listeler.OyuncuBilgiListesi.OyuncuLinkList;
import armoyuplugin.armoyuplugin.Listeler.ClaimListesi.ArsaLinkList;
import armoyuplugin.armoyuplugin.Listeler.KlanListesi.KlanLinkList;
import armoyuplugin.armoyuplugin.Genel.Dinleyici;
import armoyuplugin.armoyuplugin.Servisler.ApiServices.ApiService;
import armoyuplugin.armoyuplugin.Servisler.CommandServices.ClaimCommandsService;
import armoyuplugin.armoyuplugin.Servisler.CommandServices.ClanCommandsService;
import armoyuplugin.armoyuplugin.Servisler.CommandServices.SetupCommandsService;
import armoyuplugin.armoyuplugin.Servisler.JsonFileServices.JsonService;
import armoyuplugin.armoyuplugin.Genel.ScoreBoard.ArmoyuScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.kodysimpson.simpapi.menu.MenuManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

import java.util.List;


public final class ARMOYUPlugin extends JavaPlugin {

    private BukkitAudiences adventure;
    public static KlanLinkList klanListesi = new KlanLinkList();
    public static ArsaLinkList claimListesi = new ArsaLinkList();
    public static OyuncuLinkList oyuncuListesi = new OyuncuLinkList();
    private static ARMOYUPlugin plugin;
    public static ARMOYUPlugin getPlugin(){
        return plugin;
    }
    public static JsonService jsonService = new JsonService();
    public static final ApiService apiService = new ApiService();
    private static final ArmoyuScoreBoard tablo = new ArmoyuScoreBoard();
    private static final SetupCommandsService commandsService = new SetupCommandsService();
    public static final ClaimCommandsService claimCommandsService = new ClaimCommandsService();
    public static final ClanCommandsService clanCommandsService = new ClanCommandsService();
    String ARMOYUMESAJ = "[ARMOYU] ";


    @Override
    public void onEnable() {

        plugin = this;
        this.adventure = BukkitAudiences.create(plugin);

        apiService.claimListesiniDoldur();
        apiService.klanListesiniDoldur();

        commandsService.setupCommands(plugin);


        MenuManager.setup(getServer(),plugin);
        getServer().getPluginManager().registerEvents(new Dinleyici(), plugin);
        Bukkit.getLogger().info(ARMOYUMESAJ + "----Aktif---- aramizdakioyuncu.com");


        getServer().getScheduler().runTaskTimer(this, () -> tablo.setScoreBoard(plugin),0,100);
    }
    @Override
    public void onDisable() {


        if (this.adventure!=null){
            this.adventure.close();
            this.adventure = null;
        }
        jsonService.serverDisable(plugin);

        Bukkit.getLogger().info("[ARMOYU] ----Devre Dışı----");

    }



    public BukkitAudiences getAdventure() {
        if (this.adventure == null){
            throw new IllegalStateException("adventure ilk sayfa");
        }
        return this.adventure;
    }
}



////OZEL ESYALAR BASLANGIC
//
//        esyayonetim.init();
//        getCommand("poseidonunmizragi").setExecutor(new esyakomutlar());
//        getServer().getPluginManager().registerEvents(new Poseidonunmizragi(this),this);
////        getConfig().options().copyDefaults(true);
////        saveDefaultConfig();
//
////OZEL ESYALAR BITIS




//        SONRADAN AKTİF EDİLMELİ
//        for (Player player : getServer().getOnlinePlayers()) {
//            player.setGameMode(GameMode.SURVIVAL);
//            player.kickPlayer("Sunucu yeniden başlatılıyor");
//        }