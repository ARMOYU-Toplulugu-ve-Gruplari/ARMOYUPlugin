package armoyuplugin.armoyuplugin.Listener;

import armoyuplugin.armoyuplugin.ARMOYUPlugin;
import armoyuplugin.armoyuplugin.ClaimPlugin.ClaimListesi.Link;
import armoyuplugin.armoyuplugin.Services.JsonServices.models.Players;
import armoyuplugin.armoyuplugin.Services.JsonServices.utils.JsonUtility;
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
import java.util.List;
import java.util.UUID;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.jsonService;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.yeniListe;

public class GenelListener implements Listener {
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
    public GenelListener(){
        this.cooldown = new HashMap<>();
    }
    private final HashMap<UUID,Long> cooldown;








    private String tiklanilanblock = "x";
    @EventHandler
    public void onClick(PlayerInteractEvent event){

        //CLAİM PLUGİNİ
        Player p = event.getPlayer();
        if (event.getAction().name().equals("RIGHT_CLICK_AIR")){
            tiklanilanblock = "x";
        } else if (event.getAction().name().equals("LEFT_CLICK_AIR")) {
            tiklanilanblock = "x";
        } else
            tiklanilanblock = event.getClickedBlock().getChunk().toString();

        ItemStack stick = new ItemStack(Material.STICK);
        if (stick.equals(p.getInventory().getItemInMainHand())){
            if (event.getAction().toString().equals("RIGHT_CLICK_BLOCK")){
                p.sendMessage(yeniListe.claimSahipKim(p.getLocation().getChunk(),p.getWorld().toString()));
            }
            else if ("LEFT_CLICK_BLOCK".equals(event.getAction().toString())){
                Link temp = yeniListe.head;
                while (temp != null) {
                    if (temp.oyuncu.equals(p.getName())){
                        for (int i = 0; i < temp.trustlar.size(); i++) {
                            if (temp.trustlar.get(i).arsaKonum.equals(event.getClickedBlock().getLocation().getChunk().toString())){
                                for (int j = 0; j < temp.trustlar.get(i).trustVerilenler.size(); j++) {
                                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + temp.trustlar.get(i).trustVerilenler.get(j));
                                }

                            }

                        }
                    }
                    temp = temp.next;
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
                yeniListe.chunkControlEkranaYaziYazdirma(p,p.getLocation().getChunk(),p.getWorld().toString(),plugin);
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
        //CLAİM PLUGİNİ
        yeniListe.insertToHead(event.getPlayer().getName());

        //BASE PLUGİN
        Player player = event.getPlayer();

        try {
            JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/ana-arama-motoru.php?ozellik=tamarama&deger="+player.getName());

            if (json.get("kontrol").equals("1")){
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Siteye Kayıtlı");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "/giris <sifreniz>");

            }else{
                //player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.DARK_PURPLE + "SİTEYE KAYDOLUN");
                player.kickPlayer(ChatColor.RED+"!!!!!! Siteye kayıt olmanız gerekli !!!!!!"+ChatColor.YELLOW +"\n\n aramizdakioyuncu.com/kayit-ol");
                // player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "https://aramizdakioyuncu.com/kayit-ol");
                return;
            }
        }catch (Exception aa){
            player.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.RED + " Sunucu Bağlanısı Kurulamadı");
            Bukkit.getLogger().info(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
        }


//        player.setGameMode(GameMode.SURVIVAL);
        String dünya = "world";
        event.setJoinMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");

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


        Players oyuncucek = jsonService.oyuncu(event.getPlayer());
        if (oyuncucek==null) {
            jsonService.jsonCreate(event.getPlayer());
            jsonService.jsonSave();
        }




    }




    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Players oyuncucek = jsonService.oyuncu(player);

        //CLAİM PLUGİN
        if (!tiklanilanblock.equals("x")) {
            Player p = event.getPlayer();
            int deneme = yeniListe.chunkControl(p, tiklanilanblock, p.getWorld().toString());

            if (deneme != 2 && oyuncucek.getHareket()) {
                event.setCancelled(false);
            } else
                event.setCancelled(true);

        }


        //BASE PLUGİN
    }



    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        Players oyuncucek = jsonService.oyuncu(p);

        //CLAİM PLUGİN
        if (!tiklanilanblock.equals("x")) {

            int deneme =yeniListe.chunkControl(p, tiklanilanblock, p.getWorld().toString());

            if (deneme!=2 && oyuncucek.getHareket()) {
                event.setCancelled(false);
            } else{
                event.setCancelled(true);
            }

        }


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
                    jsonService.jsonSave();
                }

        }



    @EventHandler
    public void onchat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        //BASE PLUGİN
        //Oyuncu hiç oyuna girmiş mi kontrol

            Players oyuncucek = jsonService.oyuncu(player);
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);}
                else
                    event.setCancelled(true);
        }

    @EventHandler
    public void ondrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        //BASE PLUGİN
        //Oyuncu hiç oyuna girmiş mi kontrol

            Players oyuncucek = jsonService.oyuncu(player);
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);
                }
                else{
                    event.setCancelled(true);
                }

    }

    @EventHandler
    public void onKill(PlayerDeathEvent e)
    {

        //BASE PLUGİN
        e.setDeathMessage("");

        String killed="";
        String killer="";

        try {killed = e.getEntity().getName();}catch (Exception E1){ return; }
        try {killer = e.getEntity().getKiller().getName();}catch (Exception E1){return;}

        try{
            //Oyuncu hiç oyuna girmiş mi kontrol

            String oyuncuparola = jsonService.getOyuncuAdiVeParola(e.getEntity().getKiller())[1];

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
        try {
            Player player;
            Player oyuncu = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {

                player = (Player) e.getDamager();

                //Oyuncu hiç oyuna girmiş mi kontrol

                String kontroloyuncu1 = jsonService.oyuncu(player).getKlan();
                String kontroloyuncu2 = jsonService.oyuncu(oyuncu).getKlan();

                    if (kontroloyuncu1.equals(kontroloyuncu2)){
                        e.setCancelled(true);
                    }


            }

        }catch (Exception ERR){

        }




    }









}








