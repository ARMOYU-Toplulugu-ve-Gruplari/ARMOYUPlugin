package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONObject;

public class JoinLeaveListener extends Komutlar implements Listener {
    ////////////////////////////JSON ÇEKME FONKSİYON BAŞLANGIÇ////////////////////////////
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
////////////////////////////JSON ÇEKME FONKSİYON BİTTİ////////////////////////////



    //Giriş yapmadıysa komutları engelleme
    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();


            List<Players> findAllNotes = JsonUtility.findAllNotes();
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){

                    if (oyuncucek.getHareket()){
                        //Giriş yaptığında
                        e.setCancelled(false);
                    }else{
                        //Giriş yapmadığında
                        if (e.getMessage().startsWith("/") && !(e.getMessage().startsWith("/giris")  || e.getMessage().startsWith("/login"))) {
                            e.setCancelled(true);
                            player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "giriş yapmak için /login <sifre>");
                        }else{
                            e.setCancelled(false);
                        }

                    }
                }
            }
        }




    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    e.setFormat( ChatColor.YELLOW +"["+ oyuncucek.getKlan() +"]"+ ChatColor.BLUE + " %s :"+ ChatColor.WHITE + " %s");
                }
                else{
                    e.setFormat("%s : %s");
                }
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        try{
            String killed = e.getEntity().getName();
            String killer = e.getEntity().getKiller().getName();
            e.setDeathMessage(ChatColor.YELLOW + killed + " öldürüldü. Katili: " + ChatColor.RED  + " " + killer);
        }catch (Exception ERR){

        }

    }

    @EventHandler
    public void ondrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);
                }
                else{
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onchat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);}
                else
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onmove(PlayerMoveEvent event) {
        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);}
                else{
                    event.setCancelled(true);
                    player.setFoodLevel(20);
                    player.setHealth(20);
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setGameMode(GameMode.SURVIVAL);

        String dünya = "world";
        e.setJoinMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");
        player.teleport(new Location(Bukkit.getWorld(dünya),-8,76,-8));
        player.setFoodLevel(20);
        player.setHealth(20);


        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/ana-arama-motoru.php?ozellik=tamarama&deger="+player.getDisplayName());

            if (json.get("kontrol").equals("1")){
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Siteye Kayıtlı");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "/giris <sifreniz>");
            }else{
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.DARK_PURPLE + "SİTEYE KAYDOLUN");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "https://aramizdakioyuncu.com/kayit-ol");
            }
        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı");
            System.out.println(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
        }


        try {
            File yourFile = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
            if (!yourFile.exists()){
                yourFile.getParentFile().mkdirs();
                yourFile.createNewFile(); // if file already exists will do nothing
                FileOutputStream oFile = new FileOutputStream(yourFile, false);
            }

        }catch (Exception error){
            System.out.println("[ARMOYU] oyuncular.json dosyası oluşturulamadı!");

        }


        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }



        //Oyuncu hiç oyuna girmiş mi kontrol
        boolean oyuncukontrol = false;
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                oyuncukontrol = true;
            }
        }

        //Yeni oyuncu ise
        if (oyuncukontrol == false){
            System.out.println("[ARMOYU] "+"YENİ OYUNCU GİRDİ");
            JsonUtility.createNote(player,false,20,20);
            try {
                JsonUtility.saveNotes();
            } catch (IOException ERR) {
                System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                ERR.printStackTrace();
            }

        }



    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        e.setQuitMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");


        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
        try { JsonUtility.loadNotesxyz(); } catch (IOException err) {    err.printStackTrace();   }

        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    String k = "world";
                    if (player.getLocation().toString().contains("world_nether")){
                        k = "world_nether";
                    }
                    else if (player.getLocation().toString().contains("world_the_end")){
                        k = "world_the_end";
                    }
                    JsonUtility.updateNotexyz(player.getDisplayName(),"KLAAN",false,player.getFoodLevel(),player.getHealth() ,x,y,z, k);

                    try {
                        JsonUtility.saveNotesxyz();
                    } catch (IOException ERR) {
                        System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                        ERR.printStackTrace();
                    }
                }

            }
        }
    }

}
