package ARMOYU.Genel;

import ARMOYU.ARMOYUPlugin;
import ARMOYU.Listeler.OyuncuBilgiListesi.OyuncuBilgiLink;
import ARMOYU.Listeler.ClaimListesi.ArsaBilgiLink;
import ARMOYU.Servisler.JsonFileServices.models.Players;
import ARMOYU.Servisler.JsonFileServices.utils.JsonUtility;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.inventory.ItemStack;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import java.util.UUID;

import static ARMOYU.ARMOYUPlugin.*;

public class Dinleyici implements Listener {
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


    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU Claim] ";
    String APIKEY = "771df488714111d39138eb60df756e6b";
    private final ARMOYUPlugin plugin = ARMOYUPlugin.getPlugin();
    public Dinleyici(){
        this.cooldown = new HashMap<>();
    }
    private final HashMap<UUID,Long> cooldown;


    @EventHandler
    public void onClick(PlayerInteractEvent event){

        //CLAİM PLUGİNİ
        Player p = event.getPlayer();


        ItemStack stick = new ItemStack(Material.STICK);
        if (stick.equals(p.getInventory().getItemInMainHand())){
            if (event.getAction().toString().equals("RIGHT_CLICK_BLOCK")){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + claimListesi.arsaninSahibiKim(p.getLocation().getChunk().toString(),p.getWorld().toString()));
            }
            else if ("LEFT_CLICK_BLOCK".equals(event.getAction().toString())){
                ArsaBilgiLink temp= claimListesi.listedeAraziBul(p.getWorld().toString(),p.getLocation().getChunk().toString());
                if (temp == null){
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "claimsiz");
                    return;
                }
                for (int i = 0; i < temp.hissedarlar.size(); i++) {
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + temp.hissedarlar.get(i));
                }
            }


        }


        //SCOREBOARD PLUGİNİ

        //BASEPLUGİN
    }









    @EventHandler
    public void onMove(PlayerMoveEvent Event) {
        //CLAİM PLUGİNİ
        Player p = Event.getPlayer();

        if (!this.cooldown.containsKey(p.getUniqueId())){
            this.cooldown.put(p.getUniqueId(),System.currentTimeMillis());
        }

        else {
            long timeElapsed=System.currentTimeMillis() - cooldown.get(p.getUniqueId());

            if (timeElapsed > 2000){
                this.cooldown.put(p.getUniqueId(),System.currentTimeMillis());
                claimListesi.EkranaAraziBilgiYazdirma(p,p.getLocation().getChunk(),p.getWorld().toString(),plugin);
            }

        }


        //BASE PLUGİN
        Players oyuncu = jsonService.oyuncu(Event.getPlayer());

        if (oyuncu.getHareket()){
            Event.setCancelled(false);}
        else{
            Event.setCancelled(true);
            Event.getPlayer().setFoodLevel(20);
            Event.getPlayer().setHealth(20);
        }



    }



    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        //oyuncu listesi
        OyuncuBilgiLink oyuncu = oyuncuListesi.oyuncuBul(event.getPlayer().getName());
        if (oyuncu==null){
            oyuncuListesi.oyuncuEkle(event.getPlayer().getName());
        }


        //CLAİM PLUGİNİ


        //BASE PLUGİN
        Players oyuncucek = jsonService.oyuncu(event.getPlayer());
        if (oyuncucek==null) {
            jsonService.jsonCreate(event.getPlayer());
            jsonService.jsonSave();
        }


        Player player = event.getPlayer();

        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/ana-arama-motoru.php?ozellik=minecraft&deger="+player.getName());

            if (json.get("kontrol").toString().equals("1")){
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Siteye Kayıtlı");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "/giris <sifreniz>");

            }else{
                player.kickPlayer(ChatColor.RED+"!!!!!! Siteye kayıt olmanız gerekli !!!!!!"+ChatColor.YELLOW +"\n\n aramizdakioyuncu.com/kayit-ol");
                return;
            }

        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı (Oyuncu Kontrol)");
            Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
        }


//        player.setGameMode(GameMode.SURVIVAL);


        String dunya = "world";
        event.setJoinMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");

        int cekozellikx = 0;
        int cekozelliky = 0;
        int cekozellikz = 0;
        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/0/0/0/0");
            JSONObject ozellik = json.getJSONObject("ozellik");

            if (!ozellik.isNull("sunucux")) {


                cekozellikx = Integer.parseInt(ozellik.get("sunucux").toString());
                cekozelliky = Integer.parseInt(ozellik.get("sunucuy").toString());
                cekozellikz = Integer.parseInt(ozellik.get("sunucuz").toString());


            }
            if (!ozellik.isNull("sunucudunya")){
                dunya = ozellik.get("sunucudunya").toString();
            }


        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı (Sunucu X Y Z)");
            Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı(Sunucu X Y Z) ");
        }

        player.teleport(new Location(Bukkit.getWorld(dunya),cekozellikx,cekozelliky,cekozellikz));

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
        }







    }




    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Players oyuncucek = jsonService.oyuncu(player);

        //CLAİM PLUGİN

            int deneme = claimListesi.arsaKontrol(player.getName(), event.getBlockPlaced().getChunk().toString(), player.getWorld().toString());

            event.setCancelled(deneme == 2 || !oyuncucek.getHareket());




        //BASE PLUGİN
    }



    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Players oyuncucek = jsonService.oyuncu(p);

        //CLAİM PLUGİN

            int deneme =claimListesi.arsaKontrol(p.getName(), event.getBlock().getChunk().toString(), p.getWorld().toString());

            event.setCancelled(deneme == 2 || !oyuncucek.getHareket());




        //BASE PLUGİN



    }


    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
//CLAİM PLUGİN

//BASE PLUGİN
            Players oyuncucek = jsonService.oyuncu(player);

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

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        //BASE PLUGİN
        e.setFormat("%s :" + ChatColor.WHITE + " %s");
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



        String oyuncuparola = "";
        String k = "world";

            Players oyuncucek = jsonService.oyuncu(player);
                if (oyuncucek.getHareket()) {

                    if (player.getLocation().toString().contains("world_nether")) {
                        k = "world_nether";
                    } else if (player.getLocation().toString().contains("world_the_end")) {
                        k = "world_the_end";
                    }

                    oyuncuparola = oyuncucek.getOyuncuparola();
                    JsonUtility.updateNotexyz(player.getName(), oyuncucek.getPara(), false, player.getFoodLevel(), player.getHealth(), x, y, z, k);

                    try {
                        JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + player.getName() + "/" + oyuncuparola + "/sunucudancik/" + Math.round(x) + "/" + Math.round(y) + "/" + Math.round(z) + "/" + k + "/" + player.isDead());

                        if (json.get("durum").equals(0)) {
                            Bukkit.getLogger().info(json.get("aciklama").toString());
                        }
                    } catch (Exception aa) {
                        Bukkit.getLogger().info(ChatColor.RED + "[ARMOYU] " + player.getName() + " verileri sunucuya kaydedilemedi.");
                    }
                    jsonService.jsonSave();
                }

        }



    @EventHandler
    public void onchat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        //BASE PLUGİN
        //Oyuncu hiç oyuna girmiş mi kontrol

            Players oyuncucek = jsonService.oyuncu(player);
            event.setCancelled(!oyuncucek.getHareket());
        }

    @EventHandler
    public void ondrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        //BASE PLUGİN
        //Oyuncu hiç oyuna girmiş mi kontrol

            Players oyuncucek = jsonService.oyuncu(player);
            event.setCancelled(!oyuncucek.getHareket());

    }

    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {

        //BASE PLUGİN
        e.setDeathMessage("");
//asd
        String killed="";
        String killer="";

        try {killed = e.getEntity().getName();}catch (Exception E1){ return; }
        try {killer = e.getEntity().getKiller().getName();}catch (Exception E1){return;}

        try{
            //Oyuncu hiç oyuna girmiş mi kontrol
//Kontroller
            String oyuncuparola = jsonService.getOyuncuAdiVeParola(e.getEntity().getKiller())[1];

            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+killer+"/"+oyuncuparola+"/olumbilgi/"+killed+"/0/");
            if (json.get("durum").equals("0")){
                e.setDeathMessage("");
            }else{
                JSONObject rec = json.getJSONObject("icerik");
                e.setDeathMessage(ChatColor.YELLOW + killed + " öldürüldü. Katili: " + ChatColor.RED  + " " + killer);
                e.getEntity().sendMessage(ChatColor.RED + "-" + rec.get("ortayakalanpara") + "₺");
                e.getEntity().getKiller().sendMessage(ChatColor.GREEN +"+" + rec.get("ortayakalanpara") + "₺");
            }

        }catch (IOException ERR) {
            Bukkit.getLogger().info("[ARMOYU] "+"Öldürme verileri işlenemedi");
            e.setDeathMessage("");
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

//BASE PLUGİN
        LivingEntity entity = e.getEntity();


        if (entity.getType() == EntityType.PLAYER){  return; }

        String killer = "";
        EntityType killed = EntityType.PLAYER;

        try {  killer = entity.getKiller().getName();   }catch (Exception E){  return;   }
        try {  killed = entity.getType();  }catch (Exception E){  return;  }


        String oyuncuparola = jsonService.getOyuncuAdiVeParola(e.getEntity().getKiller())[1];


        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+killer+"/"+oyuncuparola+"/mobolumbilgi/"+killed+"/0/");
            if (json.get("durum").equals("0")){
                entity.getKiller().sendMessage("Öldürme bilgisi işlenemedi!");
            }else{
                entity.getKiller().sendMessage(ChatColor.GREEN +"+" + json.get("ortayakalanpara"));

                int para= (int) json.get("oldurenpara");
                int les=(int) json.get("oyuncuskor");

                JsonUtility.updateles(entity.getKiller().getName(),les);
                JsonUtility.updatepara(entity.getKiller().getName(),para);

                jsonService.jsonSave();
            }
        }catch (Exception E){
            Bukkit.getLogger().info("Öldürme bilgisi işlenemedi");
        }


    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
//BASE PLUGİN


    }









}








