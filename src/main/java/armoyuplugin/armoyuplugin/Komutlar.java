package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.bukkit.Bukkit.*;

public class Komutlar  implements CommandExecutor {

    String APIKEY = "771df488714111d39138eb60df756e6b";
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



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }


        Player oyuncu = (Player) sender;



        if (cmd.getName().equalsIgnoreCase("para")) {
            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola ="";
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();
                    break;
                }
            }




            if(args.length == 0){

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/0/0/0/");

                    JSONArray recs = json.getJSONArray("niteliklioyunlar");
                    String mcpara = "0";
                    for (int i = 0; i < recs.length(); ++i) {
                        JSONObject rec = recs.getJSONObject(i);
                        if (rec.get("etkinlikkisaad").equals("minecraft")) {
                            mcpara = rec.get("oyunbakiye").toString();
                        }
                    }

                    for (int i = 0; i < findAllNotes.size(); i++) {
                        Players oyuncucek = findAllNotes.get(i);
                        if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                            oyuncucek.setPara(mcpara);
                            JsonUtility.updatepara(oyuncu.getName(),mcpara);
                        }
                    }
                    try {  JsonUtility.saveNotes();   } catch (IOException ERR) {   Bukkit.getLogger().info("[ARMOYU] "+"Para kaydetme işlemi yapılamadı");}

                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + mcpara);

                }catch (Exception E){
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                }
            }else if(args[0].equals("liste")){
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Zengin Listesi :\n");


                try{
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/zenginler/0/0/");
                    JSONArray recs = json.getJSONArray("zenginler");

                    int sirasay =0;
                    for (int i = 0; i < recs.length(); ++i) {
                        sirasay++;
                        JSONObject rec = recs.getJSONObject(i);

                        oyuncu.sendMessage(ChatColor.AQUA + "" +sirasay +"- "+  ChatColor.WHITE + rec.get("oyuncuadi").toString()+ " " + ChatColor.YELLOW +rec.get("oyuncupara").toString());

                    }


                }catch (Exception e){
                    oyuncu.sendMessage("[ARMOYU] Sunucu ile bağlanılamadı(Zenginler).");
                }

            }else{
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Böyle bir komut yok");
            }

        }else if(cmd.getName().equalsIgnoreCase("zenginler")){


        }else if (cmd.getName().equalsIgnoreCase("oturumsaati")) {


            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola ="";

            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();
                }
            }

            try {
                JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/oturumsaati/0/0/0");

                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString());


            } catch (Exception err) {
                Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı (tpa).");
            }


        }else if (cmd.getName().equalsIgnoreCase("klan")) {

            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola ="";

            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();
                    break;
                }
            }

            if(args.length == 0){

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/klanlar/0/0/");
                    JSONArray recs = json.getJSONArray("klanlar");
                    int sirasay=0;
                    for (int i = 0; i < recs.length(); ++i) {
                        sirasay++;
                        JSONObject rec = recs.getJSONObject(i);
                        if (rec.get("klandavarmi").equals("1")) {
                            oyuncu.sendMessage(sirasay+") "+ChatColor.GREEN+rec.get("klanadi").toString() + ChatColor.LIGHT_PURPLE + " (" +ChatColor.RED+ rec.get("klanpuani") + ChatColor.LIGHT_PURPLE + ")");
                            oyuncu.sendMessage(" Kurucu : "+ ChatColor.RED + rec.get("klankurucu").toString());
                            oyuncu.sendMessage(" Üye sayısı: "+rec.get("klanuyesayisi").toString());

                        }
                        else{
                            oyuncu.sendMessage(sirasay+") "+ChatColor.YELLOW+rec.get("klanadi").toString() + ChatColor.LIGHT_PURPLE +" (" +ChatColor.RED+ rec.get("klanpuani") + ChatColor.LIGHT_PURPLE + ")");
                            oyuncu.sendMessage(" Kurucu: "+ ChatColor.RED + rec.get("klankurucu").toString());
                            oyuncu.sendMessage(" Üye sayısı: "+rec.get("klanuyesayisi").toString());

                        }
                        oyuncu.sendMessage(ChatColor.GREEN + "----------------------------");

                    }
                }catch (IOException ERR) {
                    Bukkit.getLogger().info("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                    ERR.printStackTrace();
                }

            }else if(args[0].equals("ayril")){
                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/klan/ayril/");

                    String durum = json.get("durum").toString();
                    String aciklama = json.get("aciklama").toString();

                    if(durum.equals("1")){

                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );
                        JsonUtility.updateklan(oyuncu.getName(),"","");
                        try {JsonUtility.saveNotes();}catch (Exception e){Bukkit.getLogger().info("Değişiklik Kayıt Edilemedi:"+ e);}

                    }else{
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );

                    }

                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                }


            }else if (args[0].equals("olustur")){
                if (args.length == 1){
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN +"Klan Adı yazmadın!");
                    return true;
                }



                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/klan/olustur/" + args[1] + "/" + args[1]);

                    String durum = json.get("durum").toString();
                    String aciklama = json.get("aciklama").toString();

                    if(durum.equals("1")){
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );

                        JsonUtility.updateklan(oyuncu.getName(),args[1],"Kurucu");
                        try {JsonUtility.saveNotes();}catch (Exception e){Bukkit.getLogger().info("Değişiklik Kayıt Edilemedi:"+ e);}


//                        //OP KONTROL ET
//                        if (!oyuncu.isOp()){
//                            oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu komudu kullanmak için OP olmanız gerekir.");
//                            return true;
//                        }

                        int x = oyuncu.getLocation().getBlockX(), y = oyuncu.getLocation().getBlockY(), z = oyuncu.getLocation().getBlockZ();

                        Location loc = new Location((Bukkit.getWorld("world")), x, y, z);
                        loc.getBlock().setType(Material.RED_BANNER);

                        for (int i = 0; i < 10; i++) {
                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+9);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z+9);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+9);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z+9);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-9);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z-9);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-9);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z-9);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x+9, y, z+i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+9, y+1, z+i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x+9, y, z-i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+9, y+1, z-i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-9, y, z+i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-9, y+1, z+i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-9, y, z-i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-9, y+1, z-i);
                            loc.getBlock().setType(Material.STONE);
                        }

                        for (int i = 0; i <11; i++) {
                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+10);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+10);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-10);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-10);
                            loc.getBlock().setType(Material.WHITE_WOOL);


                            loc = new Location((Bukkit.getWorld("world")), x+10, y, z+i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x+10, y, z-i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-10, y, z+i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-10, y, z-i);
                            loc.getBlock().setType(Material.WHITE_WOOL);
                        }

                        for (int i = 0; i <12; i++) {
                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+11);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+11);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-11);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-11);
                            loc.getBlock().setType(Material.WHITE_WOOL);


                            loc = new Location((Bukkit.getWorld("world")), x+11, y, z+i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x+11, y, z-i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-11, y, z+i);
                            loc.getBlock().setType(Material.WHITE_WOOL);

                            loc = new Location((Bukkit.getWorld("world")), x-11, y, z-i);
                            loc.getBlock().setType(Material.WHITE_WOOL);
                        }

                        for (int i = 0; i <13; i++) {
                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+12);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z+12);
                            loc.getBlock().setType(Material.STONE);


                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+12);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z+12);
                            loc.getBlock().setType(Material.STONE);


                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-12);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z-12);
                            loc.getBlock().setType(Material.STONE);


                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-12);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z-12);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x+12, y, z+i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+12, y+1, z+i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x+12, y, z-i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x+12, y+1, z-i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-12, y, z+i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-12, y+1, z+i);
                            loc.getBlock().setType(Material.STONE);

                            loc = new Location((Bukkit.getWorld("world")), x-12, y, z-i);
                            loc.getBlock().setType(Material.STONE);
                            loc = new Location((Bukkit.getWorld("world")), x-12, y+1, z-i);
                            loc.getBlock().setType(Material.STONE);
                        }





                    }else{
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );

                    }

                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucuya bağlanılamadı.");
                }


            }else if (args[0].equals("davet")){


            }else if(args[0].equals("acil")){

                String oyuncuklan = "";
                for (int i = 0; i < findAllNotes.size(); i++) {
                    Players oyuncucek = findAllNotes.get(i);
                    if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                        oyuncuklan = oyuncucek.getKlan();
                    }
                }
                if (!oyuncuklan.equals("")){

                    int klanuyesay=0;
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getDisplayName().contains("["+oyuncuklan+"]") && player.getName()!=oyuncu.getName()){
                            klanuyesay++;
                            player.teleport(new Location(Bukkit.getWorld("world"),oyuncu.getLocation().getX(),oyuncu.getLocation().getY(),oyuncu.getLocation().getZ()));

                        }
                        org.bukkit.inventory.PlayerInventory inv = player.getInventory();

                        ItemStack kask = new ItemStack(Material.LEATHER_HELMET);
                        ItemStack gogusluk = new ItemStack(Material.LEATHER_CHESTPLATE);
                        ItemStack dizlik = new ItemStack(Material.LEATHER_LEGGINGS);
                        ItemStack bot = new ItemStack(Material.LEATHER_BOOTS);
                        ItemStack kilic = new ItemStack(Material.STONE_SWORD);
                        ItemStack kalkan = new ItemStack(Material.SHIELD);
                        ItemStack hava = new ItemStack(Material.AIR);

                        if(inv.getBoots() == null){
                            player.getInventory().setBoots(bot);
                        }

                        if(inv.getLeggings() == null){
                            player.getInventory().setLeggings(dizlik);
                        }

                        if(inv.getChestplate() == null){
                            player.getInventory().setChestplate(gogusluk);
                        }

                        if(inv.getHelmet() == null){
                            player.getInventory().setHelmet(kask);
                        }

                        if (inv.getItemInMainHand().getType().equals(hava.getType())){
                            player.getInventory().setItemInMainHand(kilic);
                        }

                        if (inv.getItemInOffHand().getType().equals(hava.getType())){
                            player.getInventory().setItemInOffHand(kalkan);
                        }
                    }
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + klanuyesay +  ChatColor.YELLOW +" [" + oyuncuklan +"] " +ChatColor.GREEN +"üyeleri çağrılıyor!");
                }else{
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Klanınız YOK!");
                }

            }else if (args[0].equals("katil")){
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Davet YOK");

            }else if (args[0].equals("git")){


                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/klan/git/");

                    String durum = json.get("durum").toString();
                    String aciklama = json.get("aciklama").toString();

                    if(durum.equals("1")){

                        String kordinat = json.get("klankordinat").toString();
                        String[] result = kordinat.split(",");
                        double x = Double.parseDouble(result[0]);
                        double y = Double.parseDouble(result[1]);
                        double z = Double.parseDouble(result[2]);
                        oyuncu.teleport(new Location(Bukkit.getWorld("world"),x,y,z));

                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );

                    }else{
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );
                    }


                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucuya bağlanılamadı.");
                }


            }else if (args[0].equals("baslangicnoktasi")){

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/klan/baslangicnoktasi/"+ Math.round(oyuncu.getLocation().getX()) +"/"+ Math.round(oyuncu.getLocation().getY()) +"/"+ Math.round(oyuncu.getLocation().getZ())+"/"+oyuncu.getLocation().getWorld().getName());

                    String durum = json.get("durum").toString();
                    String aciklama = json.get("aciklama").toString();

                    if(durum.equals("1")){
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );
                    }else{
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );

                    }



                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
                }


            }else{
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Böyle bir komut yok");
            }


        return true;

        }else if (cmd.getName().equalsIgnoreCase("baslangicayarla")) {
            //OP KONTROL ET
            if (!oyuncu.isOp()){
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu komudu kullanmak için OP olmanız gerekir.");
                return true;
            }
            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola ="";

            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);

                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                  oyuncuparola = oyuncucek.getOyuncuparola();
                }
            }


            try {
                JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/sunucubilgi/baslangicayarla/"+ Math.round(oyuncu.getLocation().getX()) +"/"+ Math.round(oyuncu.getLocation().getY()) +"/"+ Math.round(oyuncu.getLocation().getZ()));

                String sunucuozellik = json.get("sunucuozellik").toString();
                String sunucuadi = json.get("sunucuadi").toString();
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + sunucuadi + "Yeni Başlangıç Noktası: " +sunucuozellik);


            } catch (Exception err) {
                Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
            }

        }else if (cmd.getName().equalsIgnoreCase("tpa")){

            if (args.length != 1){
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Hatalı Kullanım Yaptınız");
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


                try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

                List<Players> findAllNotes = JsonUtility.findAllNotes();
                String oyuncuparola ="";

                for (int i = 0; i < findAllNotes.size(); i++) {
                    Players oyuncucek = findAllNotes.get(i);

                    if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                        oyuncuparola = oyuncucek.getOyuncuparola();
                    }
                }

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/tpa/"+ oyuncuarkadas.getName() +"/0/0");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + json.get("aciklama").toString());
                    if (json.get("durum").equals("1")){
                        oyuncuarkadas.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + oyuncu.getName() + " adlı oyuncu yanına gelmek istiyor.");
                    }

                } catch (Exception err) {
                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı (tpa).");
                }

            }else{
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Oyuncu bulunamadı : " + ChatColor.AQUA + args[0]);

            }




        }else if (cmd.getName().equalsIgnoreCase("tpaccept")){

            if (args.length != 1){
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Hatalı Kullanım Yaptınız");
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
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Oyuncu bulunamadı : " + ChatColor.AQUA + args[0]);

                return true;
            }

            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola ="";

            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();
                }
            }


            try {
                JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/tpaccept/"+ oyuncuarkadas.getName() +"/0/0");
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + json.get("aciklama").toString());
                if (json.get("durum").equals("1")){
                    oyuncuarkadas.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW  + "Arkadaşına gidiyorsun : " + ChatColor.AQUA + oyuncu.getName());
                    oyuncuarkadas.teleport(new Location(Bukkit.getWorld("world"),oyuncu.getLocation().getBlockX(),oyuncu.getLocation().getBlockY(),oyuncu.getLocation().getBlockZ()));
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
                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    kordinat = oyuncucek.getEv();
                }
            }

            if (args.length==0){


                try {
                    String[] result = kordinat.split(",");
                    double x = Double.parseDouble(result[0]);
                    double y = Double.parseDouble(result[1]);
                    double z = Double.parseDouble(result[2]);
                    oyuncu.teleport(new Location(Bukkit.getWorld("world"),x,y,z));
                }catch (Exception ERR){
                    Bukkit.getLogger().info("Ev Ayarlı Değil");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Ev Ayarlı Değil!");
                }


            } else if(args[0].equals("ayarla")){
                try { JsonUtility.loadNotes(); } catch (IOException err) {err.printStackTrace(); return true;}

                double x = Math.round(oyuncu.getLocation().getX());
                double y = Math.round(oyuncu.getLocation().getY());
                double z = Math.round(oyuncu.getLocation().getZ());

                JsonUtility.updateevayarla(oyuncu.getName(),x + ","+ y + "," + z);
                try {
                    JsonUtility.saveNotes();
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + "Ev ayarlandı!");
                }catch (Exception ERR){
                    Bukkit.getLogger().info("Ev Ayarlanamadı");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Ev ayarlanamadı!");
                }

            }else {

                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW+ "BÖYLE BİR KOMUT YOK");
            }

            return true;

        } else if (cmd.getName().equalsIgnoreCase("toplamoldurme")) {

            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
            //Oyuncu hiç oyuna girmiş mi kontrol
            List<Players> findAllNotes = JsonUtility.findAllNotes();
            String oyuncuparola = "";
            for (int i = 0; i < findAllNotes.size(); i++) {
                Players oyuncucek = findAllNotes.get(i);
                if (oyuncucek.getOyuncuadi().equals(oyuncu.getName())){
                    oyuncuparola = oyuncucek.getOyuncuparola();
                    if (!oyuncucek.getHareket()){
                        break;
                    }
                }
            }

            try {
                JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/" + APIKEY + "/" + oyuncu.getName() + "/" + oyuncuparola + "/totalkill/0/0/");
                oyuncu.sendMessage(ChatColor.GREEN + "Leş sayısı: " +json.get("aciklama").toString());
                oyuncu.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            }catch (IOException ERR) {
                Bukkit.getLogger().info("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                ERR.printStackTrace();
            }

        }else if (cmd.getName().equalsIgnoreCase("giris")) {

            if (args.length != 1) {
                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Hatalı Kullanım Yaptınız");
            }else{

                try {
                    JSONObject json = readJsonFromUrl("https://aramizdakioyuncu.com/botlar/"+APIKEY+"/"+oyuncu.getName()+"/"+getMd5(args[0])+"/0/0/0/");

                    if (json.get("kontrol").equals("1")){
                        JSONArray recs = json.getJSONArray("niteliklioyunlar");
                        String klanadi = "";
                        String klanrutbe = "";
                        String klanrenk = "";
                        String mcpara = "0";

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
                                mcpara = rec.get("oyunbakiye").toString();
                            }
                        }

                        try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }

                        try {
                            List<Players> findAllNotes = JsonUtility.findAllNotes();
                            for (int i = 0; i < findAllNotes.size(); i++) {
                                Players oyuncucek = findAllNotes.get(i);
                                if(oyuncucek.getOyuncuadi().equals(oyuncu.getName())){



                                    if (oyuncucek.getHareket() == false){
                                        if(evx != 123 && evy !=456 && evz != 789){
                                            oyuncu.teleport(new Location(Bukkit.getWorld(evdunya),evx,evy,evz));
                                        }
                                        else if (oyuncucek.getX() == 0.0 && oyuncucek.getY() == 0.0 && oyuncucek.getZ() == 0.0){
                                            oyuncu.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),sunucux,sunucuy,sunucuz));
                                        }else{
                                            oyuncu.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),oyuncucek.getX(),oyuncucek.getY(),oyuncucek.getZ()));
                                        }
                                        int aclik = (int) oyuncucek.getAclik();
                                        oyuncu.setFoodLevel(aclik);
                                        oyuncu.setHealth(oyuncucek.getSaglik());
                                    }

                                    if (oyuncucek.getHareket() == true){
                                        try {  JsonUtility.updateNote(oyuncu.getName() ,getMd5(args[0]),klanadi ,klanrutbe,klanrenk,true, mcpara);JsonUtility.saveNotes();  }catch (Exception er){   }
                                    }
                                    try {  JsonUtility.updateNote(oyuncu.getName() ,getMd5(args[0]),klanadi ,klanrutbe,klanrenk,true, mcpara);JsonUtility.saveNotes();  }catch (Exception er){   }
                                    break;
                                }
                            }
                        } catch (Exception E) {
                            Bukkit.getLogger().info("[ARMOYU] "+"Giriş komutları işlenemed!");

                        }
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + "Giriş Başarılı");

                    }else {
                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Hatalı GİRİŞ");

                    }

                }catch (Exception aa){
                    Bukkit.getLogger().info(ARMOYUMESAJ + "oyuncu giriş yaparken sunucuyla bağlantı kurulamadı!");
                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucu ile Bağlantı Kurulamadı");
                }

            }
            return true;
        }
        return true;

    }
}