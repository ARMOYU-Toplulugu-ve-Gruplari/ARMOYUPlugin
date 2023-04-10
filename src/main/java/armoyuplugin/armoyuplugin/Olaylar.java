package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import com.google.gson.Gson;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.*;
import org.json.JSONException;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.JSONObject;

public class Olaylar extends Komutlar implements org.bukkit.event.Listener {

    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU] ";


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

    ////////////////////////////MD5 HASH KOD BAŞLANGIÇ////////////////////////////
    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    ////////////////////////////MD5 HASH KOD BİTİŞ////////////////////////////

    //Giriş yapmadıysa komutları engelleme
    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();


            List<Players> findAllNotes = JsonUtility.findAllNotes();
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if (oyuncucek.getOyuncuadi().equals(player.getName())){

                    if (oyuncucek.getHareket()){
                        //Giriş yaptığında
                        e.setCancelled(false);
                    }else{
                        //Giriş yapmadığında
                        if (e.getMessage().startsWith("/") && !(e.getMessage().startsWith("/giris")  || e.getMessage().startsWith("/login"))) {
                            player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "giriş yapmak için /giris <sifre>");
                            e.setCancelled(true);
                        }else{
                            e.setCancelled(false);
                        }

                    }
                }
            }
        }




    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        e.setFormat("%s :" + ChatColor.WHITE + " %s");
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
        try {
            Player player;
            Player oyuncu = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {

                player = (Player) e.getDamager();

                //Oyuncu hiç oyuna girmiş mi kontrol
                List<Players> findAllNotes = JsonUtility.findAllNotes();
                String kontroloyuncu1 = "1";
                String kontroloyuncu2 = "2";
                for (int i = 0; i < findAllNotes.size(); i++) {

                    Players oyuncucek = findAllNotes.get(i);
                    if (oyuncucek.getOyuncuadi().equals(player.getName())){
                        kontroloyuncu1 = oyuncucek.getKlan();
                    }
                    if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                        kontroloyuncu2 = oyuncucek.getKlan();
                    }

                    if (kontroloyuncu1.equals(kontroloyuncu2)){
                        e.setCancelled(true);
                    }
                }

            } else {         return;        }

        }catch (Exception ERR){

        }




    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {


        LivingEntity entity = e.getEntity();


        if (entity.getType() == EntityType.PLAYER){  return; }

        String killer = "";
        EntityType killed = EntityType.PLAYER;

        try {  killer = entity.getKiller().getName();   }catch (Exception E){  return;   }
        try {  killed = entity.getType();  }catch (Exception E){  return;  }

        List<Players> findAllNotes = JsonUtility.findAllNotes();
        String oyuncuparola = "";
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(killer)){
                oyuncuparola = oyuncucek.getOyuncuparola();
            }
        }

        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+killer+"/"+oyuncuparola+"/mobolumbilgi/"+killed+"/0/");
            if (json.get("durum").equals("0")){
                entity.getKiller().sendMessage("Öldürme bilgisi işlenemedi!");
            }else{
                entity.getKiller().sendMessage(ChatColor.GREEN +"+" + json.get("ortayakalanpara"));
            }
        }catch (Exception E){
            Bukkit.getLogger().info("Öldürme bilgisi işlenemedi");
        }


    }

    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {
        e.setDeathMessage("");

        String killed="";
        String killer="";

        try {killed = e.getEntity().getName();}catch (Exception E1){ return; }
        try {killer = e.getEntity().getKiller().getName();}catch (Exception E1){return;}

        try { JsonUtility.loadNotes(); } catch (IOException err) {Bukkit.getLogger().info("Veriler Çekilemedi");}

        try{
            //Oyuncu hiç oyuna girmiş mi kontrol
            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola = "";
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(e.getEntity().getKiller().getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();

                }
            }


            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+killer+"/"+oyuncuparola+"/olumbilgi/"+killed+"/0/");
            if (json.get("durum").equals("0")){
                e.setDeathMessage("");
            }else{
                e.setDeathMessage(ChatColor.YELLOW + killed + " öldürüldü. Katili: " + ChatColor.RED  + " " + killer);
                e.getEntity().sendMessage(ChatColor.RED + "-" + json.get("ortayakalanpara") + "₺");
                e.getEntity().getKiller().sendMessage(ChatColor.GREEN +"+" + json.get("ortayakalanpara") + "₺");

            }

        }catch (IOException ERR) {
            Bukkit.getLogger().info("[ARMOYU] "+"Öldürme verileri işlenemedi");
            e.setDeathMessage("");

        }


    }


    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
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
    public void blockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
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
    public void ondrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
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
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
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
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
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

        int cekozellikx = 0;
        int cekozelliky = 0;
        int cekozellikz = 0;
        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/0/0/0/0");
            JSONObject ozellik = (JSONObject)json.get("ozellik");

            cekozellikx = Integer.parseInt(ozellik.get("sunucux").toString());
            cekozelliky = Integer.parseInt(ozellik.get("sunucuy").toString());
            cekozellikz = Integer.parseInt(ozellik.get("sunucuz").toString());

        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı");
            Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
        }
        player.teleport(new Location(Bukkit.getWorld(dünya),cekozellikx,cekozelliky,cekozellikz));

        player.setFoodLevel(20);
        player.setHealth(20);


        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/ana-arama-motoru.php?ozellik=tamarama&deger="+player.getName());

            if (json.get("kontrol").equals("1")){
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Siteye Kayıtlı");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "/giris <sifreniz>");

            }else{
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.DARK_PURPLE + "SİTEYE KAYDOLUN");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "https://aramizdakioyuncu.com/kayit-ol");
            }
        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı");
            Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
        }


        try {
            File yourFile = new File(ARMOYUPlugin.getPlugin().getDataFolder().getAbsolutePath() + "/oyuncular.json");
            if (!yourFile.exists()){
                Gson gson = new Gson();
                yourFile.getParentFile().mkdirs();
                yourFile.createNewFile(); // if file already exists will do nothing
                FileWriter fileWriter = new FileWriter(yourFile, false);
                BufferedWriter bWriter = new BufferedWriter(fileWriter);
                bWriter.write("[]");
                bWriter.close();
            }

        }catch (Exception error){
            Bukkit.getLogger().info("[ARMOYU] oyuncular.json dosyası oluşturulamadı!");
            player.kickPlayer("Sunucuya Bağlanılamadı tekrar dene!");
            return;
        }


        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }



        //Oyuncu hiç oyuna girmiş mi kontrol
        boolean oyuncukontrol = false;
        List<Players> findAllNotes = JsonUtility.findAllNotes();


            String oyuncuklanadi= "";
            String oyuncuklanrutbe= "";
            String oyuncupara= "";
            String oyuncuadi = "";

        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
                oyuncukontrol = true;
                oyuncuklanadi = oyuncucek.getKlan();
                oyuncuklanrutbe = oyuncucek.getKlanrutbe();
                oyuncupara = oyuncucek.getPara();
                oyuncuadi = oyuncucek.getOyuncuadi();
                break;
            }
        }

        //Yeni oyuncu ise
        if (oyuncukontrol == false){
            Bukkit.getLogger().info("[ARMOYU] "+"YENİ OYUNCU GİRDİ");
            JsonUtility.createNote(player,false,20,20);
            try {
                JsonUtility.saveNotes();
            } catch (IOException ERR) {
                Bukkit.getLogger().info("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                ERR.printStackTrace();
            }

        }




        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();

        Objective o = b.registerNewObjective("ARMOYU TEST SERVER", "ANA","");


        o.setDisplaySlot(DisplaySlot.SIDEBAR);

        o.setDisplayName(ChatColor.DARK_AQUA + o.getName());

        Score slotoyuncuadi = o.getScore(ChatColor.WHITE + "Oyuncu Adı: " + ChatColor.WHITE + oyuncuadi );

        Score slotklanadi = o.getScore(ChatColor.WHITE + "Klan: " + ChatColor.YELLOW + oyuncuklanadi );

        Score slotklanrutbe = o.getScore(ChatColor.WHITE + "Rütbe: " + ChatColor.GREEN + oyuncuklanrutbe );

        Score slotbosluk = o.getScore("");

        Score slotreklam = o.getScore(ChatColor.YELLOW + "§laramizdakioyuncu.com");

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Score slotzaman = o.getScore(ChatColor.YELLOW + formatter.format(date));



        slotoyuncuadi.setScore(10);
        slotbosluk.setScore(9);
        slotklanadi.setScore(8);
        slotklanrutbe.setScore(8);
        slotbosluk.setScore(7);
        slotzaman.setScore(6);
        slotreklam.setScore(5);




        player.setScoreboard(b);


    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        int aktifoyuncusayisi = Bukkit.getOnlinePlayers().size();
        aktifoyuncusayisi--;
        e.setQuitMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ aktifoyuncusayisi + "/20");




        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();

        String oyuncuparola = "";
        String k = "world";
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equals(player.getName())){
                if (oyuncucek.getHareket()){

                    if (player.getLocation().toString().contains("world_nether")){
                        k = "world_nether";
                    }
                    else if (player.getLocation().toString().contains("world_the_end")){
                        k = "world_the_end";
                    }

                    oyuncuparola = oyuncucek.getOyuncuparola();
                    JsonUtility.updateNotexyz(player.getName(), oyuncucek.getPara(),false,player.getFoodLevel(),player.getHealth() ,x,y,z, k);

                    try {
                        JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+player.getName()+"/"+oyuncuparola+"/sunucudancik/"+Math.round(x)+"/"+Math.round(y)+"/"+Math.round(z)+"/"+k+"/"+player.isDead());

                        if (json.get("durum").equals(0)){
                            Bukkit.getLogger().info(json.get("aciklama").toString());
                        }
                    }catch (Exception aa){
                        Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +player.getName() + " verileri sunucuya kaydedilemedi.");
                    }

                    try { JsonUtility.saveNotes(); } catch (IOException ERR) {Bukkit.getLogger().info("[ARMOYU] "+ChatColor.RED+"Json Dosyasına Kayıt yapılamadı!");}
                }
                break;
            }
        }





    }

}
