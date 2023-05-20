package armoyuplugin.armoyuplugin.Pluginler.Genel;

import armoyuplugin.armoyuplugin.Servisler.JsonFileServices.models.Players;
import armoyuplugin.armoyuplugin.Servisler.JsonFileServices.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.apiService;
import static armoyuplugin.armoyuplugin.ARMOYUPlugin.jsonService;
import static org.bukkit.Bukkit.getServer;

public class GenelKomutlar implements CommandExecutor {


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
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU] ";
        Player p = (Player) sender;

        String[] oyuncuAdiSifresi = jsonService.getOyuncuAdiVeParola(p);

        if (cmd.getName().equalsIgnoreCase("para")) {
            if(args.length == 0){
                try {
                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"0","0","0"};
                    String link = apiService.linkOlustur(arrayLink);
                    JSONObject json = apiService.readJsonFromUrl(link);

                    JSONArray recs = json.getJSONArray("niteliklioyunlar");
                    for (int i = 0; i < recs.length(); ++i) {
                        JSONObject rec = recs.getJSONObject(i);
                        if (rec.get("etkinlikkisaad").equals("minecraft")) {
                            p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + rec.get("oyunbakiye").toString());
                        }
                    }
//                    Players jsonOyuncu = jsonService.oyuncu(p);
//                    jsonOyuncu.setPara(mcpara);
//                    JsonUtility.updatepara(p.getName(), mcpara);
//                    jsonService.jsonSave();
                }catch (Exception E){
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                }
            }else if(args[0].equals("liste")){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Zengin Listesi :\n");
                try{
                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"zenginler","0","0"};
                    String link = apiService.linkOlustur(arrayLink);
                    JSONObject json = apiService.readJsonFromUrl(link);
                    JSONArray recs = json.getJSONArray("zenginler");
                    int sirasay = 0;
                    for (int i = 0; i < recs.length(); ++i) {
                        sirasay++;
                        JSONObject rec = recs.getJSONObject(i);
                        p.sendMessage(ChatColor.AQUA + "" +sirasay +"- "+  ChatColor.WHITE + rec.get("oyuncuadi").toString()+ " " + ChatColor.YELLOW +rec.get("oyuncupara").toString());
                    }
                }catch (Exception e){
                    p.sendMessage("[ARMOYU] Sunucu ile bağlanılamadı(Zenginler).");
                }
            }else{
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Böyle bir komut yok");
            }
        }else if (cmd.getName().equalsIgnoreCase("oturumsaati")) {
            try {
                String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"oturumsaati","0","0"};
                String link = apiService.linkOlustur(arrayLink);
                JSONObject json = apiService.readJsonFromUrl(link);
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());
            } catch (Exception err) {
                Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı (tpa).");
            }
        }else if (cmd.getName().equalsIgnoreCase("baslangicayarla")) {
            //OP KONTROL ET
            if (!p.isOp()){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu komudu kullanmak için OP olmanız gerekir.");
                return true;
            }

            try {
                String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"sunucubilgi","baslangicayarla",String.valueOf(p.getLocation().getX()),String.valueOf(p.getLocation().getY()),String.valueOf(p.getLocation().getZ())};
                String link = apiService.linkOlustur(arrayLink);
                JSONObject json = apiService.readJsonFromUrl(link);

                String sunucuozellik = json.get("sunucuozellik").toString();
                String sunucuadi = json.get("sunucuadi").toString();
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + sunucuadi + "Yeni Başlangıç Noktası: " +sunucuozellik);
            } catch (Exception err) {
                Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
            }
        }else if (cmd.getName().equalsIgnoreCase("tpa")){
            if (args.length != 1){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Hatalı Kullanım Yaptınız");
                return true;
            }
            int oyuncubul = 0;
            Player oyuncuarkadas = null;
            for (Player player : getServer().getOnlinePlayers()) {
                if (player.getName().equals(args[0])) {
                    oyuncubul = 1;
                    oyuncuarkadas = player;
                    break;
                }
            }
            if (oyuncubul == 1){

                try {
                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"tpa",oyuncuarkadas.getName(),"0","0"};
                    String link = apiService.linkOlustur(arrayLink);
                    JSONObject json = apiService.readJsonFromUrl(link);
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + json.get("aciklama").toString());
                    if (json.get("durum").equals("1")){
                        oyuncuarkadas.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + p.getName() + " adlı oyuncu yanına gelmek istiyor.");
                    }
                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı (tpa).");
                }
            }else{
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Oyuncu bulunamadı : " + ChatColor.AQUA + args[0]);
            }
        }else if (cmd.getName().equalsIgnoreCase("tpaccept")){

            if (args.length != 1){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Hatalı Kullanım Yaptınız");
                return true;
            }


            int oyuncubul = 0;
            Player oyuncuarkadas = null;
            for (Player player : getServer().getOnlinePlayers()) {
                if (player.getName().equals(args[0])) {
                    oyuncubul = 1;
                    oyuncuarkadas = player;
                    break;
                }
            }
            if (oyuncubul == 0){
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Oyuncu bulunamadı : " + ChatColor.AQUA + args[0]);
                return true;
            }
            try {
                String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"tpaccept",oyuncuarkadas.getName(),"0","0"};
                String link = apiService.linkOlustur(arrayLink);
                JSONObject json = apiService.readJsonFromUrl(link);
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + json.get("aciklama").toString());
                if (json.get("durum").equals("1")){
                    oyuncuarkadas.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Arkadaşına gidiyorsun : " + ChatColor.AQUA + p.getName());
                    oyuncuarkadas.teleport(new Location(Bukkit.getWorld("world"),p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ()));
                }
            } catch (Exception err) {
                Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı (tpa).");
            }
        }else if (cmd.getName().equalsIgnoreCase("ev")){

            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
            //Oyuncu hiç oyuna girmiş mi kontrol
            List<Players> findAllNotes = JsonUtility.findAllNotes();

            String kordinat="";
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(p.getName())){
                    kordinat = oyuncucek.getEv();
                }
            }
            if (args.length==0){

                try {
                    String[] result = kordinat.split(",");
                    double x = Double.parseDouble(result[0]);
                    double y = Double.parseDouble(result[1]);
                    double z = Double.parseDouble(result[2]);
                    p.teleport(new Location(Bukkit.getWorld("world"),x,y,z));
                }catch (Exception ERR){
                    Bukkit.getLogger().info("Ev Ayarlı Değil");
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Ev Ayarlı Değil!");
                }


            } else if(args[0].equals("ayarla")){
                try { JsonUtility.loadNotes(); } catch (IOException err) {err.printStackTrace(); return true;}

                double x = Math.round(p.getLocation().getX());
                double y = Math.round(p.getLocation().getY());
                double z = Math.round(p.getLocation().getZ());

                JsonUtility.updateevayarla(p.getName(),x + ","+ y + "," + z);
                try {
                    JsonUtility.saveNotes();
                    p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + "Ev ayarlandı!");
                }catch (Exception ERR){
                    Bukkit.getLogger().info("Ev Ayarlanamadı");
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Ev ayarlanamadı!");
                }
            }else {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW+ "BÖYLE BİR KOMUT YOK");
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("toplamoldurme")) {
            try {
                String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"totalkill","0","0"};
                String link = apiService.linkOlustur(arrayLink);
                JSONObject json = apiService.readJsonFromUrl(link);
                p.sendMessage(ChatColor.GREEN + "Leş sayısı: " +json.get("aciklama").toString());
                p.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            }catch (IOException ERR) {
                Bukkit.getLogger().info("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                ERR.printStackTrace();
            }

        }else if (cmd.getName().equalsIgnoreCase("giris")) {
            if (args.length != 1) {
                p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Hatalı Kullanım Yaptınız");
            }else{

                try {
                    String[] arrayLink = {oyuncuAdiSifresi[0],getMd5(args[0]),"0","0","0"};
                    String link = apiService.linkOlustur(arrayLink);
                    JSONObject json = apiService.readJsonFromUrl(link);

                    if (json.get("kontrol").equals("1")){
                        JSONArray recs = json.getJSONArray("niteliklioyunlar");
                        String klanadi = "";
                        String klanrutbe = "";
                        String klanrenk = "";
                        int mcpara = 0;
                        int leslerim=0;
                        float sunucux =0;
                        float sunucuy =76;
                        float sunucuz =-8;

                        float evx=123;
                        float evy=456;
                        float evz=789;
                        String evdunya ="";

                        JSONObject ozellik = (JSONObject)json.get("ozellik");

                        try {
                            evx = Integer.parseInt(ozellik.get("evx").toString());
                            evy = Integer.parseInt(ozellik.get("evy").toString());
                            evz = Integer.parseInt(ozellik.get("evz").toString());
                            evdunya = ozellik.get("evdunya").toString();
                        }catch(Exception e){

                        }
                        try {
                            sunucux = Integer.parseInt(ozellik.get("sunucux").toString());
                            sunucuy = Integer.parseInt(ozellik.get("sunucuy").toString());
                            sunucuz = Integer.parseInt(ozellik.get("sunucuz").toString());
                        }catch(Exception e){

                        }


                        for (int i = 0; i < recs.length(); ++i) {
                            JSONObject rec = recs.getJSONObject(i);
                            if (rec.get("etkinlikkisaad").equals("minecraft")) {
                                klanadi = rec.get("grupkisaad").toString();
                                klanrutbe = rec.get("gruprutbe").toString();
                                klanrenk = rec.get("gruprenk").toString();
                                mcpara = (int) rec.get("oyunbakiye");
                                leslerim = (int) rec.get("skor");

                            }
                        }

                        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

                        try {
                            List<Players> findAllNotes = JsonUtility.findAllNotes();
                            for (int i = 0; i < findAllNotes.size(); i++) {
                                Players oyuncucek = findAllNotes.get(i);
                                if(oyuncucek.getOyuncuadi().equals(p.getName())){

                                    if (!oyuncucek.getHareket()){
                                        if(evx != 123 && evy !=456 && evz != 789){
                                            p.teleport(new Location(Bukkit.getWorld(evdunya),evx,evy,evz));
                                        }
                                        else if (oyuncucek.getX() == 0.0 && oyuncucek.getY() == 0.0 && oyuncucek.getZ() == 0.0){
                                            p.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),sunucux,sunucuy,sunucuz));
                                        }else{
                                            p.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),oyuncucek.getX(),oyuncucek.getY(),oyuncucek.getZ()));
                                        }
                                        int aclik = (int) oyuncucek.getAclik();
                                        p.setFoodLevel(aclik);
                                        p.setHealth(oyuncucek.getSaglik());
                                    }

                                    if (oyuncucek.getHareket() == true){
                                        try {  JsonUtility.updateNote(p.getName() ,getMd5(args[0]),klanadi ,klanrutbe,klanrenk,leslerim,true, mcpara);JsonUtility.saveNotes();  }catch (Exception er){   }
                                    }
                                    try {  JsonUtility.updateNote(p.getName() ,getMd5(args[0]),klanadi ,klanrutbe,klanrenk,leslerim,true, mcpara);JsonUtility.saveNotes();  }catch (Exception er){   }
                                    break;
                                }
                            }
                        } catch (Exception E) {
                            Bukkit.getLogger().info("[ARMOYU] "+"Giriş komutları işlenemed!");

                        }
                        p.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + "Giriş Başarılı");

                    }else {
                        p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Hatalı GİRİŞ");

                    }

                }catch (Exception aa){
                    Bukkit.getLogger().info(ARMOYUMESAJ + "oyuncu giriş yaparken sunucuyla bağlantı kurulamadı!");
                    p.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu ile Bağlantı Kurulamadı");
                }

            }
            return true;
        }

        return true;
    }





}
