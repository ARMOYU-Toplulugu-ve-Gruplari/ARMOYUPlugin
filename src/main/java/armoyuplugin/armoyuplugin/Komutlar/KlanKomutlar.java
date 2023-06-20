package armoyuplugin.armoyuplugin.Komutlar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

import static armoyuplugin.armoyuplugin.ARMOYUPlugin.*;
import static org.bukkit.Bukkit.*;

public class KlanKomutlar implements CommandExecutor {

    String ARMOYUMESAJ = ChatColor.RED + "[ARMOYU] ";


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }


        Player p = (Player) sender;

        String[] oyuncuAdiVeParola = jsonService.getOyuncuAdiVeParola(p);

        if (cmd.getName().equalsIgnoreCase("klan")) {
            if (args.length==0){

            }else if (args[0].equals("katil")){
                if (args.length == 2){
                    clanCommandsService.klanKatil(p,oyuncuAdiVeParola,args[1]);
                    return true;
                }
            }else if (args[0].equals("olustur")) {
                if (args.length == 2){
                    clanCommandsService.klanOlustur(p,oyuncuAdiVeParola,args[1]);
                    return true;
                }
            }else if (args[0].equals("ayril")) {
                clanCommandsService.klanAyril(p,oyuncuAdiVeParola);
                return true;
            }else if (args[0].equals("dagit")) {
                clanCommandsService.klanDagit(p,oyuncuAdiVeParola);
                return true;
            }else if (args[0].equals("git")) {
                clanCommandsService.klanGit(p,oyuncuAdiVeParola);
                return true;
            }else if (args[0].equals("baslangicnoktasi")) {
                clanCommandsService.klanBaslangicNoktasi(p,oyuncuAdiVeParola);
                return true;
            }else if (args[0].equals("acil")) {

            }else if (args[0].equals("devret")) {

            }else if (args[0].equals("davet")) {
                if (args.length == 2){
                    clanCommandsService.klanDavet(p,oyuncuAdiVeParola,args[1]);
                    return true;
                }

            }else if(args[0].equals("arsa")){
                if (args[1].equals("devret")){

                }else if (args[1].equals("sil")) {

                }else if (args[1].equals("liste")){

                }
            }else if (args[0].equals("aciklama")) {
                if (args.length!=1){
                    clanCommandsService.klanAciklama(p,oyuncuAdiVeParola,args);
                }
                return true;
            }


//            if(args.length == 0){
////                try {
////                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"klanlar","0","0"};
////                    String link = apiService.linkOlustur(arrayLink);
////                    JSONObject json = apiService.readJsonFromUrl(link);
////                    JSONArray recs = json.getJSONArray("klanlar");
////                    int sirasay=0;
////                    for (int i = 0; i < recs.length(); ++i) {
////                        sirasay++;
////                        JSONObject rec = recs.getJSONObject(i);
////                        if (rec.get("klandavarmi").equals("1")) {
////                            oyuncu.sendMessage(sirasay+") "+ChatColor.GREEN+rec.get("klanadi").toString() + ChatColor.LIGHT_PURPLE + " (" +ChatColor.RED+ rec.get("klanpuani") + ChatColor.LIGHT_PURPLE + ")");
////                            oyuncu.sendMessage(" Kurucu : "+ ChatColor.RED + rec.get("klankurucu").toString());
////                            oyuncu.sendMessage(" Üye sayısı: "+rec.get("klanuyesayisi").toString());
////                        }
////                        else{
////                            oyuncu.sendMessage(sirasay+") "+ChatColor.YELLOW+rec.get("klanadi").toString() + ChatColor.LIGHT_PURPLE +" (" +ChatColor.RED+ rec.get("klanpuani") + ChatColor.LIGHT_PURPLE + ")");
////                            oyuncu.sendMessage(" Kurucu: "+ ChatColor.RED + rec.get("klankurucu").toString());
////                            oyuncu.sendMessage(" Üye sayısı: "+rec.get("klanuyesayisi").toString());
////
////                        }
////                        oyuncu.sendMessage(ChatColor.GREEN + "----------------------------");
////
////                    }
////                }catch (IOException ERR) {
////                    Bukkit.getLogger().info("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
////                    ERR.printStackTrace();
////                }
//
//            }else if(args[0].equals("ayril")){
//                try {
//                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"klan","ayril"};
//                    JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(arrayLink));
//                    if(json.get("durum").toString().equals("1")){
//                        klanListesi.klandanAyril(oyuncu.getName());
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString() );
//                    }else{
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString() );
//                    }
//                } catch (Exception err) {
//                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
//                }
//            }else if (args[0].equals("olustur")){
//                if (args.length == 1){
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN +"Klan Adı yazmadın!");
//                    return true;
//                }
//                try {
//                    String[] linkElemanlar = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"klan","olustur",args[1],args[1]};
//                    JSONObject json = apiService.readJsonFromUrl(apiService.linkOlustur(linkElemanlar));
//                    if(json.get("durum").toString().equals("1")){
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + json.get("aciklama").toString() );
//                        klanListesi.klanOlustur(oyuncu.getName(),args[1]);
//
////                        //OP KONTROL ET
////                        if (!oyuncu.isOp()){
////                            oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Bu komudu kullanmak için OP olmanız gerekir.");
////                            return true;
////                        }
////
////                        int x = oyuncu.getLocation().getBlockX(), y = oyuncu.getLocation().getBlockY(), z = oyuncu.getLocation().getBlockZ();
////
////                        Location loc = new Location((Bukkit.getWorld("world")), x, y, z);
////                        loc.getBlock().setType(Material.RED_BANNER);
////
////                        for (int i = 0; i < 10; i++) {
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+9);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z+9);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+9);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z+9);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-9);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z-9);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-9);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z-9);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+9, y, z+i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+9, y+1, z+i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+9, y, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+9, y+1, z-i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-9, y, z+i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-9, y+1, z+i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-9, y, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-9, y+1, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                        }
////
////                        for (int i = 0; i <11; i++) {
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+10);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+10);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-10);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-10);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////
////                            loc = new Location((Bukkit.getWorld("world")), x+10, y, z+i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+10, y, z-i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-10, y, z+i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-10, y, z-i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////                        }
////
////                        for (int i = 0; i <12; i++) {
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+11);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+11);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-11);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-11);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////
////                            loc = new Location((Bukkit.getWorld("world")), x+11, y, z+i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+11, y, z-i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-11, y, z+i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-11, y, z-i);
////                            loc.getBlock().setType(Material.WHITE_WOOL);
////                        }
////
////                        for (int i = 0; i <13; i++) {
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z+12);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z+12);
////                            loc.getBlock().setType(Material.STONE);
////
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z+12);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z+12);
////                            loc.getBlock().setType(Material.STONE);
////
////
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y, z-12);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+i, y+1, z-12);
////                            loc.getBlock().setType(Material.STONE);
////
////
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y, z-12);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-i, y+1, z-12);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+12, y, z+i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+12, y+1, z+i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x+12, y, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x+12, y+1, z-i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-12, y, z+i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-12, y+1, z+i);
////                            loc.getBlock().setType(Material.STONE);
////
////                            loc = new Location((Bukkit.getWorld("world")), x-12, y, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                            loc = new Location((Bukkit.getWorld("world")), x-12, y+1, z-i);
////                            loc.getBlock().setType(Material.STONE);
////                        }
//
//                    }else{
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + json.get("aciklama").toString() );
//
//                    }
//
//                } catch (Exception err) {
//                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucuya bağlanılamadı.");
//                }
//
//
//            }else if (args[0].equals("davet")){
//
//
//            }else if(args[0].equals("acil")){
//
//                String oyuncuKlan = klanListesi.hangiKlanaUye(oyuncu.getName());
//                if (!oyuncuKlan.isEmpty()){
//
//                    int klanuyesay=0;
//                    for (Player player : getServer().getOnlinePlayers()) {
//                        if (player.getDisplayName().contains("["+oyuncuKlan+"]") && player.getName()!=oyuncu.getName()){
//                            klanuyesay++;
//                            player.teleport(new Location(Bukkit.getWorld("world"),oyuncu.getLocation().getX(),oyuncu.getLocation().getY(),oyuncu.getLocation().getZ()));
//                        }
//                        org.bukkit.inventory.PlayerInventory inv = player.getInventory();
//
//                        ItemStack kask = new ItemStack(Material.LEATHER_HELMET);
//                        ItemStack gogusluk = new ItemStack(Material.LEATHER_CHESTPLATE);
//                        ItemStack dizlik = new ItemStack(Material.LEATHER_LEGGINGS);
//                        ItemStack bot = new ItemStack(Material.LEATHER_BOOTS);
//                        ItemStack kilic = new ItemStack(Material.STONE_SWORD);
//                        ItemStack kalkan = new ItemStack(Material.SHIELD);
//                        ItemStack hava = new ItemStack(Material.AIR);
//
//                        if(inv.getBoots() == null){
//                            player.getInventory().setBoots(bot);
//                        }
//
//                        if(inv.getLeggings() == null){
//                            player.getInventory().setLeggings(dizlik);
//                        }
//
//                        if(inv.getChestplate() == null){
//                            player.getInventory().setChestplate(gogusluk);
//                        }
//
//                        if(inv.getHelmet() == null){
//                            player.getInventory().setHelmet(kask);
//                        }
//
//                        if (inv.getItemInMainHand().getType().equals(hava.getType())){
//                            player.getInventory().setItemInMainHand(kilic);
//                        }
//
//                        if (inv.getItemInOffHand().getType().equals(hava.getType())){
//                            player.getInventory().setItemInOffHand(kalkan);
//                        }
//                    }
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + klanuyesay +  ChatColor.YELLOW +" [" + oyuncuKlan +"] " +ChatColor.GREEN +"üyeleri çağrılıyor!");
//                }else{
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Klanınız YOK!");
//                }
//
//            }else if (args[0].equals("katil")){
//                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Davet YOK");
//
//            }else if (args[0].equals("git")){
//
//                try {
//                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"klan","git"};
//                    String link = apiService.linkOlustur(arrayLink);
//                    JSONObject json = apiService.readJsonFromUrl(link);
//                    String durum = json.get("durum").toString();
//                    String aciklama = json.get("aciklama").toString();
//
//                    if(durum.equals("1")){
//
//                        String klandunya = json.get("klandunya").toString();
//
//                        String kordinat = json.get("klankordinat").toString();
//                        String[] result = kordinat.split(",");
//                        double x = Double.parseDouble(result[0]);
//                        double y = Double.parseDouble(result[1]);
//                        double z = Double.parseDouble(result[2]);
//                        oyuncu.teleport(new Location(Bukkit.getWorld(klandunya),x,y,z));
//
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );
//
//                    }else{
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );
//                    }
//
//
//                } catch (Exception err) {
//                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Sunucuya bağlanılamadı.");
//                }
//
//
//            }else if (args[0].equals("baslangicnoktasi")){
//
//                try {
//                    String[] arrayLink = {oyuncuAdiSifresi[0],oyuncuAdiSifresi[1],"klan","baslangicnoktasi",String.valueOf(oyuncu.getLocation().getX()),String.valueOf(oyuncu.getLocation().getY()),String.valueOf(oyuncu.getLocation().getZ()),oyuncu.getLocation().getWorld().getName() };
//                    String link = apiService.linkOlustur(arrayLink);
//                    JSONObject json = apiService.readJsonFromUrl(link);
//                    String durum = json.get("durum").toString();
//                    String aciklama = json.get("aciklama").toString();
//
//                    if(durum.equals("1")){
//                        wait(1000);
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.GREEN + aciklama );
//                    }else{
//                        oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + aciklama );
//                    }
//                } catch (Exception err) {
//                    Bukkit.getLogger().info("[ARMOYU] Sunucuya bağlanılamadı.");
//                }
//            }else if(args[0].equals("arsa")){
//                if (args[1].equals("devret")){
//
//                } else if (args[1].equals("sil")) {
//
//                } else if (args[1].equals("liste")){
//
//                } else {
//                    oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Böyle bir komut yok");
//                }
//            } else if (args[0].equals("aciklama")) {
//                String aciklama= "";
//                for (int i = 1; i < args.length; i++) {
//                    aciklama = aciklama + args[i] + " ";
//                }
//                klanListesi.klanAciklamaDegis(oyuncu.getName(),aciklama);
//
//            } else{
//                oyuncu.sendMessage(ARMOYUMESAJ + ChatColor.YELLOW + "Böyle bir komut yok");
//            }
        return true;

        }
        return true;

    }
}