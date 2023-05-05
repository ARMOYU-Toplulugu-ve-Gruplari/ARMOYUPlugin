package armoyuplugin.armoyuplugin;
import armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi.LinkList;
import armoyuplugin.armoyuplugin.Listener.GenelListener;
import armoyuplugin.armoyuplugin.Services.ApiServices.ApiService;
import armoyuplugin.armoyuplugin.Services.CommandService.SetupCommandsService;
import armoyuplugin.armoyuplugin.Services.TxtServices.JsonService;
import armoyuplugin.armoyuplugin.ScoreBoardPlugin.ArmoyuScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import me.kodysimpson.simpapi.menu.MenuManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;



public final class ARMOYUPlugin extends JavaPlugin {

    private BukkitAudiences adventure;
    public static LinkList claimListesi = new LinkList();
    private static ARMOYUPlugin plugin;
    public static ARMOYUPlugin getPlugin(){
        return plugin;
    }
    public static JsonService jsonService = new JsonService();
    public static final ApiService apiService = new ApiService();
    private static final ArmoyuScoreBoard tablo = new ArmoyuScoreBoard();
    private static final SetupCommandsService commandsService = new SetupCommandsService();
    String ARMOYUMESAJ = "[ARMOYU] ";


    @Override
    public void onEnable() {

        plugin = this;
        this.adventure = BukkitAudiences.create(plugin);


        apiService.claimListesiniDoldur();
        commandsService.setupCommands(plugin);


        MenuManager.setup(getServer(),plugin);
        getServer().getPluginManager().registerEvents(new GenelListener(), plugin);
        Bukkit.getLogger().info(ARMOYUMESAJ + "----Aktif---- aramizdakioyuncu.com");


        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
               tablo.setScoreBoard(plugin);
            }
        },0,100);
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