package armoyuplugin.armoyuplugin;


import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.ozelesyalar.Poseidonunmizragi;
import armoyuplugin.armoyuplugin.ozelesyalar.esyayonetim;
import armoyuplugin.armoyuplugin.ozelesyalar.esyakomutlar;
import armoyuplugin.armoyuplugin.utils.JsonUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class ARMOYUPlugin extends JavaPlugin {

    private static ARMOYUPlugin plugin;
    String ARMOYUMESAJ = "[ARMOYU] ";


    @Override
    public void onEnable() {

        Bukkit.getLogger().info(ARMOYUMESAJ + "----Aktif---- aramizdakioyuncu.com");
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        plugin = this;



        List<Players> findAllNotes = JsonUtility.findAllNotes();

        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            Bukkit.getLogger().info(ARMOYUMESAJ + oyuncucek.getOyuncuadi());
            JsonUtility.updateNotexyz(oyuncucek.getOyuncuadi(), oyuncucek.getPara().toString(), false, oyuncucek.getAclik(), oyuncucek.getSaglik(), oyuncucek.getX(), oyuncucek.getY(), oyuncucek.getZ(), oyuncucek.getLocation());

            try {JsonUtility.saveNotes();} catch (IOException ERR) {Bukkit.getLogger().info("[ARMOYU] " + "Oyuncu bilgileri Güncellenemedi");}
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

        Komutlar komutlar = new Komutlar();

        getCommand("giris").setExecutor(komutlar);
        getCommand("toplamoldurme").setExecutor(komutlar);

        getCommand("ev").setExecutor(komutlar);
        getCommand("ev").setTabCompleter(new Tabtamamlama());

        getCommand("para").setExecutor(komutlar);
        getCommand("para").setTabCompleter(new Tabtamamlama());

        getCommand("oturumsaati").setExecutor(komutlar);

        getCommand("klan").setExecutor(komutlar);
        getCommand("klan").setTabCompleter(new Tabtamamlama());


        getCommand("baslangicayarla").setExecutor(komutlar);
        getCommand("tpa").setExecutor(komutlar);
        getCommand("tpaccept").setExecutor(komutlar);



        //SONRADAN AKTİF EDİLMELİ
        for (Player player : getServer().getOnlinePlayers()) {
//            player.setGameMode(GameMode.SURVIVAL);
//            player.kickPlayer("Sunucu yeniden başlatılıyor");
        }

    getServer().getScheduler().runTaskTimer(this, new Runnable() {
        @Override
        public void run() {
            Scoreboard scoreboard = getServer().getScoreboardManager().getMainScoreboard();





            for (Player player : getServer().getOnlinePlayers()){

                Team team = scoreboard.getTeam(player.getName());


                if (team == null) {
                    team = scoreboard.registerNewTeam(player.getName());
                }



                   String klanad = "";
                    List<Players> findAllNotes = JsonUtility.findAllNotes();
                    for (int i = 0; i < findAllNotes.size(); i++) {
                        Players oyuncucek = findAllNotes.get(i);

                        if(oyuncucek.getOyuncuadi().equals(player.getName())){

                            if(oyuncucek.getHareket() == false){
                                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "giriş yapmak için /giris <sifre>");
                                return;
                            }

                            if (oyuncucek.getKlan().equals("")){
                                klanad = "";

                            }else {

                                if (oyuncucek.getKlanrenk().equalsIgnoreCase("GREEN")){
                                    klanad = ChatColor.GREEN + "[" + oyuncucek.getKlan() + "] ";

                                }else if(oyuncucek.getKlanrenk().equalsIgnoreCase("RED")){
                                    klanad = ChatColor.RED + "[" + oyuncucek.getKlan() + "] ";

                                }else if(oyuncucek.getKlanrenk().equalsIgnoreCase("LIGHT_PURPLE")){
                                    klanad = ChatColor.LIGHT_PURPLE + "[" + oyuncucek.getKlan() + "] ";

                                }else if(oyuncucek.getKlanrenk().equalsIgnoreCase("GOLD")){
                                    klanad = ChatColor.GOLD + "[" + oyuncucek.getKlan() + "] ";

                                }else{
                                    klanad = ChatColor.YELLOW + "[" + oyuncucek.getKlan() + "] ";
                                }

                            }

                            team.setSuffix(ChatColor.GREEN +" [MÜTTEFİK]");
                            team.setPrefix(klanad);

                            player.setDisplayName(klanad + ChatColor.WHITE + player.getName());
                            player.setPlayerListName(klanad + ChatColor.WHITE + player.getName());

                            team.addEntry(player.getName());
                        }
                    }
                }


        }
    },0,200);



    }
    @Override
    public void onDisable() {


        for (Player player : getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);


            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();
            //Listeyi (oyuncular.json) yeniden çekiyoruz
            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

            List<Players> findAllNotes = JsonUtility.findAllNotes();
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(player.getName())){
                    if (oyuncucek.getHareket()){
                        String k = "world";
                        if (player.getLocation().toString().contains("world_nether")){
                            k = "world_nether";
                        }
                        else if (player.getLocation().toString().contains("world_the_end")){
                            k = "world_the_end";
                        }
                        JsonUtility.updateNotexyz(player.getName(), oyuncucek.getPara().toString(),false,player.getFoodLevel(),player.getHealth() ,x,y,z, k);

                        try { JsonUtility.saveNotes();   } catch (IOException ERR) {   Bukkit.getLogger().info("[ARMOYU] "+"Oyuncu bilgileri Güncellenemedi");

                        }
                    }

                }
            }
        }

        try { JsonUtility.loadNotes(); } catch (IOException e) {  Bukkit.getLogger().info("[ARMOYU] Oyuncu hareketleri okunamadı!");   }

        try { JsonUtility.updatereload();  JsonUtility.saveNotes();  }catch (Exception ERR){  Bukkit.getLogger().info("[ARMOYU] Json Dosyasındaki Verileri Güncellenmedi");    }

        Bukkit.getLogger().info("[ARMOYU] ----Devre Dışı----");

    }

    public static ARMOYUPlugin getPlugin(){
        return plugin;
    }
}
