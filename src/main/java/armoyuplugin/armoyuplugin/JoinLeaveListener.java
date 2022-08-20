package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class JoinLeaveListener extends Komutlar implements Listener {
    @EventHandler
    public void ondrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);}
                else
                    event.setCancelled(true);
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
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
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
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                if (oyuncucek.getHareket()){
                    event.setCancelled(false);}
                else{
                    event.setCancelled(true);
                }
            }
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.setJoinMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");

        try {
            String url = "https://aramizdakioyuncu.com/botlar/ana-arama-motoru.php?ozellik=tamarama&deger="+player.getDisplayName();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            int kontrol = response.charAt(0);
            if (kontrol != 110){
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Siteye Kayıtlı");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "/giris <sifreniz>");
            }else{
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.DARK_PURPLE + "SİTEYE KAYDOLUN");
                player.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "https://aramizdakioyuncu.com/kayit-ol");
            }
        }catch (Exception ERR){
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
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                oyuncukontrol = true;
            }
        }

        //Yeni oyuncu ise
        if (oyuncukontrol == false){
            System.out.println("[ARMOYU] "+"YENİ OYUNCU GİRDİ");
            JsonUtility.createNote(player, player.getDisplayName(), false);
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
        System.out.println(x + " ," + y + " ," + z);

        e.setQuitMessage(ChatColor.YELLOW +"Aktif Oyuncular: "+ Bukkit.getOnlinePlayers().size() + "/20");


        //Listeyi (oyuncular.json) yeniden çekiyoruz
        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
        try { JsonUtility.loadNotesxyz(); } catch (IOException err) {    err.printStackTrace();   }

        //Oyuncu hiç oyuna girmiş mi kontrol
        List<Players> findAllNotes = JsonUtility.findAllNotes();
        for (int i = 0; i < findAllNotes.size(); i++) {
            Players oyuncucek = findAllNotes.get(i);
            if (oyuncucek.getOyuncuadi().equalsIgnoreCase(player.getDisplayName())){
                if (oyuncucek.getHareket()){

                    JsonUtility.updateNotexyz(player.getDisplayName(),false,x,y,z, player.getLocation().toString());
                    System.out.println(player.getDisplayName() + "Giriş yapmış" +x);
                    try {
                        JsonUtility.saveNotesxyz();
                    } catch (IOException ERR) {
                        System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                        ERR.printStackTrace();
                    }
                }

            }
        }

//        JsonUtility.updateNote(player.getDisplayName(),false);

//        try {
//            JsonUtility.saveNotes();
//        } catch (IOException ERR) {
//            System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
//            ERR.printStackTrace();
//        }
    }

}
